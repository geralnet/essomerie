package net.geral.essomerie.server.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import net.geral.essomerie._shared.CEP;
import net.geral.essomerie._shared.CPF;
import net.geral.essomerie._shared.Dinheiro;
import net.geral.essomerie._shared.cardapio.Cardapio;
import net.geral.essomerie._shared.cardapio.CardapioCategoria;
import net.geral.essomerie._shared.cardapio.CardapioItem;
import net.geral.essomerie._shared.cliente.ClienteDadosExtras;
import net.geral.essomerie._shared.cliente.ClienteData;
import net.geral.essomerie._shared.cliente.ClientesDataSet;
import net.geral.essomerie._shared.cliente.Customer;
import net.geral.essomerie._shared.contagem.ContagemAlteracaoQuantidade;
import net.geral.essomerie._shared.contagem.InventoryLog;
import net.geral.essomerie._shared.contagem.InventoryLogEntry;
import net.geral.essomerie._shared.dispositivos.Dispositivo;
import net.geral.essomerie._shared.dispositivos.DispositivoMonitor;
import net.geral.essomerie._shared.funcionario.Funcionario;
import net.geral.essomerie._shared.funcionario.FuncionarioBasico;
import net.geral.essomerie._shared.funcionario.FuncionarioPessoal;
import net.geral.essomerie._shared.funcionario.FuncionarioProfissional;
import net.geral.essomerie._shared.pedido.ResumoPedido;
import net.geral.essomerie.server.core.Configuration;
import net.geral.essomerie.server.db.areas.BulletionBoardDB;
import net.geral.essomerie.server.db.areas.CalendarDB;
import net.geral.essomerie.server.db.areas.InventoryDB;
import net.geral.essomerie.server.db.areas.MessageDB;
import net.geral.essomerie.server.db.areas.PersonDB;
import net.geral.essomerie.server.db.areas.UsersDB;
import net.geral.essomerie.shared.person.Address;
import net.geral.essomerie.shared.person.Telephone;
import net.geral.jodatime.JodaTimeUtils;

import org.apache.log4j.Logger;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

//TODO padronizar nomes de metodos -- tudo para 'areas'
public class Database extends MySQL {
    private static final Logger logger = Logger.getLogger(Database.class);

    private int execution_id = 0;

    // TODO create getters, not public
    private final BulletionBoardDB bulletinBoard = new BulletionBoardDB(this);
    public final CalendarDB calendar = new CalendarDB(this);
    public final InventoryDB inventory = new InventoryDB(this);
    private final PersonDB person = new PersonDB(this);
    private final MessageDB message = new MessageDB(this);
    private final UsersDB users = new UsersDB(this);

    public Database() {
	super();
    }

    public float alterarContagem(final int userid,
	    final ContagemAlteracaoQuantidade alteracao) throws SQLException {

	String sql = "INSERT INTO `contagem_log` "// insert
		+ " (`idusuario`,`tipo`,`idmotivo`,`produto`,`quantidade_inicial`,`variacao`,`datahora`,`observacoes`) "// field
															// list
		+ " VALUES (?,?,?,?,(" // v1
		+ "    SELECT `quantidade` FROM `contagem_itens` WHERE `id`=?" // subquery
		+ "),?,NOW(),?)"; // v2
	final ContagemAlteracaoQuantidade a = alteracao;
	insert(sql, userid, a.tipo, a.idmotivo, a.iditem, a.iditem,
		a.quantidade, a.observacoes);

	switch (a.tipo) {
	case '+':
	    sql = "`quantidade`=GREATEST(`quantidade`+?,0)";
	    break;
	case '-':
	    sql = "`quantidade`=GREATEST(`quantidade`-?,0)";
	    break;
	case '=':
	    sql = "`quantidade`=GREATEST(?,0)";
	    break;
	default:
	    throw new SQLException("Tipo invalido: " + a.tipo);
	}

	sql = "UPDATE `contagem_itens` SET " + sql + " WHERE `id`=? LIMIT 1";
	update(sql, a.quantidade, a.iditem);

	sql = "SELECT `quantidade` FROM `contagem_itens` WHERE `id`=?";
	final float novaQuantidade = selectFirstField_float(sql, a.iditem);
	return novaQuantidade;
    }

    public BulletionBoardDB bulletinBoard() {
	return bulletinBoard;
    }

    public Customer cadastrarCliente(final int userid, final Customer c) {
	return null;
    }

    // throws SQLException {
    // String sql = "INSERT INTO `clientes` " // insert
    // +
    // " (`nome`,`cpf`,`nota_paulista`,`observacoes`,`log_criado`,`log_alterado`,`log_por`) "//
    // fields
    // + " VALUES (?,?,?,?,NOW(),NOW(),?)";
    // final int idcliente = insertLastId(sql, c.nome, c.cpf.getNumero(),
    // (c.notaPaulista ? "Y" : "N"), c.observacoes, userid);
    //
    // for (final Telephone t : c.telefones) {
    // sql = "INSERT INTO `clientes_telefones` "// insert
    // + " (`idcliente`,`tipo`,`telefone`,`observacoes`) "// fields
    // + " VALUES (?,?,?,?)";
    // // insert(sql, idcliente, t.tipo, t.telefone, t.observacoes);
    // }
    //
    // for (final Address e : c.enderecos) {
    // sql = "INSERT INTO `clientes_enderecos` "// insert
    // +
    // " (`idcliente`,`cep`,`logradouro`,`nr_apto_bl`,`bairro`,`observacoes`) "//
    // fields
    // + " VALUES (?,?,?,?,?,?)";
    // insert(sql, idcliente, e.cep.getNumero(), e.logradouro,
    // e.numeroAptoBloco, e.bairro, e.observacoes);
    // }
    //
    // final Customer novo = getCliente(idcliente);
    // sql = "INSERT INTO `clientes_log` "// insert
    // + " (`idcliente`,`idusuario`,`acao`,`datahora`,`de`,`para`) "// fields
    // + " VALUES (?,?,'CADASTRADO',NOW(),NULL,?)";
    // insert(sql, novo.idcliente, userid, novo.debug());
    //
    // return novo;

    public CalendarDB calendar() {
	return calendar;
    }

    @Override
    public void close() {
	if (execution_id > 0) {
	    try {
		update("UPDATE `log_execution` SET `stopped`=NOW() WHERE `id`=?",
			execution_id);
	    } catch (final SQLException e) {
		logger.warn("Could not log execution stopped.", e);
	    }
	    execution_id = 0;
	}
	super.close();
    }

    public Cardapio getCardapio() throws SQLException {
	final Vector<CardapioCategoria> categorias = getCardapioCategorias();
	final Vector<CardapioItem> items = getCardapioItems();
	return new Cardapio(categorias, items);
    }

    private Vector<CardapioCategoria> getCardapioCategorias()
	    throws SQLException {
	final Vector<CardapioCategoria> ccs = new Vector<CardapioCategoria>();
	final String sql = "SELECT `id`,`idbase`,`ordem`,`codigo`,`nome` "// select
		+ " FROM `cardapio_categorias` ORDER BY `ordem` ASC";
	final PreparedResultSet p = select(sql);
	final ResultSet rs = p.rs;
	while (rs.next()) {
	    ccs.add(new CardapioCategoria(rs.getInt(1), rs.getInt(2), rs
		    .getInt(3), rs.getString(4), rs.getString(5)));
	}
	p.close();
	return ccs;
    }

    private Vector<CardapioItem> getCardapioItems() throws SQLException {
	final Vector<CardapioItem> cis = new Vector<CardapioItem>();
	final String sql = "SELECT `id`,`ordem`,`idcategoria`,`meia`,`inteira`,`codigo`,`nome`,`nome_navegacao`,`nome_cardapio`,`descricao_portugues`,`descricao_ingles` "// select
		+ " FROM `cardapio_items` ORDER BY `ordem` ASC";
	final PreparedResultSet p = select(sql);
	final ResultSet rs = p.rs;
	while (rs.next()) {
	    int c = 1;
	    final int id = rs.getInt(c++);
	    final int ordem = rs.getInt(c++);
	    final int idcategoria = rs.getInt(c++);
	    final Dinheiro meia = new Dinheiro(rs.getLong(c++));
	    final Dinheiro inteira = new Dinheiro(rs.getLong(c++));
	    final String codigo = rs.getString(c++);
	    final String nome = rs.getString(c++);
	    final String nomeNavegacao = rs.getString(c++);
	    final String nomeCardapio = rs.getString(c++);
	    final String descricaoPortugues = rs.getString(c++);
	    final String descricaoIngles = rs.getString(c++);
	    cis.add(new CardapioItem(id, ordem, idcategoria, meia, inteira,
		    codigo, nome, nomeNavegacao, nomeCardapio,
		    descricaoPortugues, descricaoIngles));
	}
	p.close();
	return cis;
    }

    public Customer getCliente(final int idcliente) throws SQLException {
	final ClienteData cd = getClienteData(idcliente);
	final Telephone[] telefones = getTelefones(idcliente);
	final Address[] enderecos = getEnderecos(idcliente);
	// return new Customer(cd, enderecos, telefones);
	return null;
    }

    private ClienteData getClienteData(final int idcliente) throws SQLException {
	final String sql = "SELECT `nome`,`cpf`,`nota_paulista`,`observacoes` "// select
		+ " FROM `clientes` "// from
		+ " WHERE `id`=? AND `log_excluido` IS NULL";
	final PreparedResultSet r = select(sql, idcliente);
	ClienteData cd = null;
	while (r.rs.next()) {
	    final String nome = r.rs.getString("nome");
	    final CPF cpf = CPF.createOrZero(r.rs.getLong("cpf"));
	    final boolean notaPaulista = "Y".equals(r.rs
		    .getString("nota_paulista"));
	    final String observacoes = r.rs.getString("observacoes");
	    cd = new ClienteData(idcliente, nome, cpf, notaPaulista,
		    observacoes);
	}
	r.close();
	return cd;
    }

    private Vector<ClienteData> getClienteRawVector() throws SQLException {
	final Vector<ClienteData> clientes = new Vector<>();
	final PreparedResultSet r = select("SELECT `id`,`nome`,`cpf`,`nota_paulista`,`observacoes` FROM `clientes` WHERE `log_excluido` IS NULL");
	while (r.rs.next()) {
	    final int id = r.rs.getInt("id");
	    final String nome = r.rs.getString("nome");
	    final CPF cpf = CPF.createOrZero(r.rs.getLong("cpf"));
	    final boolean notaPaulista = "Y".equals(r.rs
		    .getString("nota_paulista"));
	    final String observacoes = r.rs.getString("observacoes");
	    final ClienteData c = new ClienteData(id, nome, cpf, notaPaulista,
		    observacoes);
	    clientes.add(c);
	}
	r.close();
	return clientes;
    }

    public ClienteDadosExtras getClientesDadosExtras(final int idcliente)
	    throws SQLException {
	final LocalDateTime cadastrado = selectFirstField_LocalDateTime(
		"SELECT `log_criado` FROM `clientes` WHERE `id`=?", idcliente);

	final String sql = "SELECT COUNT(`id`), SUM(`consumo`)*100, MAX(`datahora`) " // select
		+ " FROM `delivery_pedidos` "// from
		+ " WHERE `idcliente`=? ";
	final PreparedResultSet prs = select(sql, idcliente);
	prs.rs.next(); // go to first result
	final int numeroPedidos = prs.rs.getInt(1);
	final Dinheiro consumoPedidos = new Dinheiro(prs.rs.getLong(2));
	LocalDateTime ultimoPedido = JodaTimeUtils.parseLocalDateTime(prs.rs
		.getString(3));
	prs.rs.close();

	if (ultimoPedido == null) {
	    ultimoPedido = new LocalDateTime(0);
	}
	return new ClienteDadosExtras(idcliente, cadastrado, ultimoPedido,
		numeroPedidos, consumoPedidos);
    }

    public ClientesDataSet getClientesDataSet() throws SQLException {
	final Vector<ClienteData> clientes = getClienteRawVector();
	final Vector<Telephone> telefones = null;// getTelefones();
	final Vector<Address> enderecos = getEnderecos();
	return new ClientesDataSet(clientes, telefones, enderecos);
    }

    public InventoryLogEntry[] getContagemAcertos(final LocalDate de,
	    final LocalDate ate) throws SQLException {
	final String sql = "SELECT * FROM `contagem_log` WHERE datahora>=? AND datahora <? ORDER BY datahora ASC";
	final LocalDateTime dt_de = new LocalDateTime(de.toDateMidnight());
	final LocalDateTime dt_ate = dt_de.plusDays(2);
	final PreparedResultSet p = select(sql, dt_de, dt_ate);
	final InventoryLogEntry[] res = new InventoryLogEntry[p.getRowCount()];
	int i = 0;
	while (p.rs.next()) {
	    final int id = p.rs.getInt("id");
	    final int iditem = p.rs.getInt("produto");
	    final int idusuario = p.rs.getInt("idusuario");
	    final char tipo = p.rs.getString("tipo").charAt(0);
	    final int idmotivo = p.rs.getInt("idmotivo");
	    final float quantidadeInicial = p.rs.getFloat("quantidade_inicial");
	    final float variacao = p.rs.getFloat("variacao");
	    final LocalDateTime datahora = JodaTimeUtils
		    .parseLocalDateTime(p.rs.getString("datahora"));
	    final String observacoes = p.rs.getString("observacoes");
	    res[i++] = new InventoryLogEntry(id, iditem, idusuario, tipo,
		    idmotivo, quantidadeInicial, variacao, datahora,
		    observacoes);
	}
	return res;
    }

    public DispositivoMonitor[] getDispositivoMonitor(final int iddispositivo)
	    throws SQLException {
	final String sql = "SELECT `id`,`datahora`,`uptime`,IF(`screen` IS NULL,'N','Y') AS `screen` "// select
		+ " FROM `sistema`.`dispositivos_monitor` "// from
		+ " WHERE `iddispositivo`=? "// where
		+ " ORDER BY `datahora` ASC";
	final PreparedResultSet p = select(sql, iddispositivo);
	final DispositivoMonitor[] dm = new DispositivoMonitor[p.getRowCount()];
	for (int i = 0; i < dm.length; i++) {
	    p.rs.next();
	    final int id = p.rs.getInt("id");
	    final LocalDateTime datahora = JodaTimeUtils
		    .parseLocalDateTime(p.rs.getString("datahora"));
	    final long uptime = p.rs.getLong("uptime");
	    final boolean hasScreen = p.rs.getString("screen").equals("Y");
	    dm[i] = new DispositivoMonitor(id, datahora, uptime, hasScreen);
	}
	p.close();
	return dm;
    }

    public byte[] getDispositivoMonitorScreen(final int idDispositivoMonitor)
	    throws SQLException {
	final String sql = "SELECT `screen` FROM `sistema`.`dispositivos_monitor` WHERE `id`=?";
	final PreparedResultSet p = select(sql, idDispositivoMonitor);
	if (!p.rs.next()) {
	    p.close();
	    return null;
	}
	final byte[] screen = p.rs.getBytes(1);
	p.close();
	return screen;
    }

    private Vector<Address> getEnderecos() throws SQLException {
	final Vector<Address> enderecos = new Vector<>();
	final PreparedResultSet r = select("SELECT `id`,`idcliente`,`cep`,`logradouro`,`nr_apto_bl`,`bairro`,`observacoes` FROM `clientes_enderecos` WHERE `log_excluido` IS NULL");
	while (r.rs.next()) {
	    final int id = r.rs.getInt("id");
	    final int idcliente = r.rs.getInt("idcliente");
	    final CEP cep = new CEP(r.rs.getInt("cep"));
	    final String logradouro = r.rs.getString("logradouro");
	    final String nr_apto_bl = r.rs.getString("nr_apto_bl");
	    final String bairro = r.rs.getString("bairro");
	    final String observacoes = r.rs.getString("observacoes");
	    // final Address t = new Address(id, idcliente, logradouro,
	    // nr_apto_bl, bairro, cep, observacoes);
	    // enderecos.add(t);
	}
	r.close();
	return enderecos;
    }

    private Address[] getEnderecos(final int idcliente) throws SQLException {
	final String sql = "SELECT `id`,`cep`,`logradouro`,`nr_apto_bl`,`bairro`,`observacoes` "// select
		+ " FROM `clientes_enderecos` "// from
		+ " WHERE `log_excluido` IS NULL AND `idcliente`=?";
	final PreparedResultSet r = select(sql, idcliente);
	final Address[] enderecos = new Address[r.getRowCount()];
	final int i = 0;
	while (r.rs.next()) {
	    final int id = r.rs.getInt("id");
	    final CEP cep = new CEP(r.rs.getInt("cep"));
	    final String logradouro = r.rs.getString("logradouro");
	    final String nr_apto_bl = r.rs.getString("nr_apto_bl");
	    final String bairro = r.rs.getString("bairro");
	    final String observacoes = r.rs.getString("observacoes");
	    // enderecos[i++] = new Address(id, idcliente, logradouro,
	    // nr_apto_bl,
	    // bairro, cep, observacoes);
	}
	r.close();
	return enderecos;
    }

    public Funcionario getFuncionario(final int idfuncionario)
	    throws SQLException {
	// check if exists and not deleted
	final PreparedResultSet prs = select(
		"SELECT `datahora_cadastrado` FROM `funcionarios` WHERE `id`=? AND `datahora_excluido` IS NULL",
		idfuncionario);
	if (!prs.rs.next()) {
	    prs.close();
	    return null;
	}
	final LocalDateTime cadastradoEm = JodaTimeUtils
		.parseLocalDateTime(prs.rs.getString("datahora_cadastrado"));
	prs.close();
	// fetch data
	final FuncionarioPessoal pessoal = getFuncionarioPessoal(idfuncionario);
	final FuncionarioProfissional profissional = getFuncionarioProfissional(idfuncionario);
	return new Funcionario(idfuncionario, cadastradoEm, pessoal,
		profissional);
    }

    private FuncionarioPessoal getFuncionarioPessoal(final int idfuncionario)
	    throws SQLException {
	// final String sql =
	// "SELECT * FROM `funcionarios_pessoal` WHERE `idfuncionario`=?";
	// final PreparedResultSet p = select(sql, idfuncionario);
	// if (!p.rs.next()) {
	// p.close();
	// return null;
	// }
	// final ResultSet rs = p.rs;
	// final int id = rs.getInt("id");
	// final String nome = rs.getString("nome");
	// final String apelido = rs.getString("apelido");
	// final String sSexo = rs.getString("sexo");
	// final char sexo = ("M".equals(sSexo) ? 'M' : ("F".equals(sSexo) ? 'F'
	// : '?'));
	// final LocalDate nascimento = JodaTimeUtils.parseLocalDate(rs
	// .getString("nascimento"));
	// final String nacionalidade = rs.getString("nacionalidade");
	// final String naturalidade_cidade =
	// rs.getString("naturalidade_cidade");
	// final Address.BrasilUF naturalidade_uf = Address.BrasilUF.fromSigla(
	// rs.getString("naturalidade_uf"), Address.BrasilUF.SP);
	// final Escolaridade escolaridade = Escolaridade.fromId(rs
	// .getString("escolaridade"));
	// final EstadoCivil estadoCivil = EstadoCivil.fromString(rs
	// .getString("estado_civil"));
	// final String nomeConjuge = rs.getString("nome_conjuge");
	// final String nomeMae = rs.getString("nome_mae");
	// final String nomePai = rs.getString("nome_pai");
	// final CEP residenciaCEP =
	// CEP.createOrZero(rs.getInt("residencia_cep"));
	// final String residenciaEndereco =
	// rs.getString("residencia_endereco");
	// final String residenciaBairro = rs.getString("residencia_bairro");
	// final String residenciaCidade = rs.getString("residencia_cidade");
	// final Address.BrasilUF residenciaUF = Address.BrasilUF.fromSigla(
	// rs.getString("residencia_uf"), Address.BrasilUF.SP);
	// final String filhos = rs.getString("filhos");
	// final String observacoes = rs.getString("observacoes");
	// final LocalDateTime alteradoEm = JodaTimeUtils.parseLocalDateTime(rs
	// .getString("datahora_alterado"));
	// final int alteradoPor = rs.getInt("alterado_por");
	//
	// p.close();
	// final Telephone[] telefones = getFuncionarioTelefones(idfuncionario);
	// return new FuncionarioPessoal(id, idfuncionario, nome, apelido, sexo,
	// nascimento, nacionalidade, naturalidade_cidade,
	// naturalidade_uf, escolaridade, estadoCivil, nomeConjuge,
	// nomeMae, nomePai, residenciaCEP, residenciaEndereco,
	// residenciaBairro, residenciaCidade, residenciaUF, telefones,
	// filhos, observacoes, alteradoEm, alteradoPor);
	return null;
    }

    private FuncionarioProfissional getFuncionarioProfissional(
	    final int idfuncionario) throws SQLException {
	final String sql = "SELECT * FROM `funcionarios_profissional` WHERE `idfuncionario`=?";
	final PreparedResultSet p = select(sql, idfuncionario);
	if (!p.rs.next()) {
	    p.close();
	    return null;
	}
	final ResultSet rs = p.rs;
	final int id = rs.getInt("id");
	final LocalDateTime alteradoEm = JodaTimeUtils.parseLocalDateTime(rs
		.getString("datahora_alterado"));
	final int alteradoPor = rs.getInt("alterado_por");
	final LocalDate admissao = JodaTimeUtils.parseLocalDate(rs
		.getString("admissao"));
	final String cargo = rs.getString("cargo");
	final String funcao = rs.getString("funcao");
	final Dinheiro salario = new Dinheiro(rs.getLong("salario"));
	final Dinheiro extra = new Dinheiro(rs.getLong("extra"));
	final String extraObservacoes = rs.getString("extra_observacoes");
	final String pagamentoBanco = rs.getString("pagamento_banco");
	final int pagamentoAgencia = rs.getInt("pagamento_agencia");
	final int pagamentoConta = rs.getInt("pagamento_conta");
	final String responsabilidades = rs.getString("responsabilidades");
	p.close();
	return new FuncionarioProfissional(id, idfuncionario, alteradoEm,
		alteradoPor, admissao, cargo, funcao, salario, extra,
		extraObservacoes, pagamentoBanco, pagamentoAgencia,
		pagamentoConta, responsabilidades);
    }

    public String[] getFuncionariosBancos() throws SQLException {
	final String sql = "SELECT DISTINCT `pagamento_banco` " // select
		+ " FROM `funcionarios_profissional` "// from
		+ " WHERE `datahora_excluido` IS NULL "// where
		+ " ORDER BY `pagamento_banco` ASC ";
	final PreparedResultSet p = select(sql);
	final String[] bancos = new String[p.getRowCount()];
	int i = 0;
	while (p.rs.next()) {
	    bancos[i++] = p.rs.getString(1);
	}
	p.close();
	return bancos;
    }

    public String[] getFuncionariosCargos() throws SQLException {
	final String sql = "SELECT DISTINCT `cargo` " // select
		+ " FROM `funcionarios_profissional` "// from
		+ " WHERE `datahora_excluido` IS NULL "// where
		+ " ORDER BY `cargo` ASC ";
	final PreparedResultSet p = select(sql);
	final String[] cargos = new String[p.getRowCount()];
	int i = 0;
	while (p.rs.next()) {
	    cargos[i++] = p.rs.getString(1);
	}
	p.close();
	return cargos;
    }

    public FuncionarioBasico[] getFuncionariosListagem() throws SQLException {
	final String sql = "SELECT `f`.`id`,`p`.`nome`,`p`.`apelido` " // select
		+ " FROM `funcionarios` AS `f` "// from
		+ " JOIN `funcionarios_pessoal` AS `p` ON `f`.`id`=`p`.`idfuncionario` "// join
		+ " WHERE `f`.`datahora_excluido` IS NULL "// where
		+ " ORDER BY `p`.`nome` ASC ";
	final PreparedResultSet p = select(sql);
	final FuncionarioBasico[] fs = new FuncionarioBasico[p.getRowCount()];
	int i = 0;
	while (p.rs.next()) {
	    final int id = p.rs.getInt("id");
	    final String nome = p.rs.getString("nome");
	    final String apelido = p.rs.getString("apelido");
	    fs[i++] = new FuncionarioBasico(id, nome, apelido);
	}
	p.close();
	return fs;
    }

    private Telephone[] getFuncionarioTelefones(final int idfuncionario)
	    throws SQLException {
	final String sql = "SELECT * FROM `funcionarios_telefones` WHERE `idfuncionario`=?";
	final PreparedResultSet p = select(sql, idfuncionario);
	final Telephone[] telefones = new Telephone[p.getRowCount()];
	final int i = 0;
	while (p.rs.next()) {
	    final int id = p.rs.getInt("id");
	    final String tipo = p.rs.getString("tipo");
	    final String telefone = p.rs.getString("telefone");
	    final String observacoes = p.rs.getString("observacoes");
	    // telefones[i++] = new Telephone(id, idfuncionario, tipo, telefone,
	    // observacoes);
	}
	p.close();
	return telefones;
    }

    public ResumoPedido[] getPedidosResumo(final int idcliente)
	    throws SQLException {
	final PreparedResultSet r = select(
		"SELECT ROUND(`consumo`*100) AS `valor`,`datahora`,`idendereco` FROM `delivery_pedidos` WHERE `idcliente`=? ORDER BY datahora DESC",
		idcliente);
	final ResumoPedido[] pedidos = new ResumoPedido[r.getRowCount()];
	int i = 0;
	while (r.rs.next()) {
	    final int idendereco = r.rs.getInt("idendereco");
	    final Dinheiro consumo = new Dinheiro(r.rs.getLong("valor"));
	    final LocalDateTime datahora = JodaTimeUtils
		    .parseLocalDateTime(r.rs.getString("datahora"));
	    pedidos[i++] = new ResumoPedido(idendereco, consumo, datahora);
	}
	r.close();
	return pedidos;
    }

    public Dispositivo[] getSistemaDispositivos() throws SQLException {
	final String sql = "SELECT `id`,`nome` "// select
		+ " FROM `sistema`.`dispositivos` "// from
		+ " ORDER BY `nome` ASC";// order
	final PreparedResultSet r = select(sql);
	final Dispositivo[] dispositivos = new Dispositivo[r.getRowCount()];
	int i = 0;
	while (r.rs.next()) {
	    final int id = r.rs.getInt("id");
	    final String nome = r.rs.getString("nome");
	    dispositivos[i++] = new Dispositivo(id, nome);
	}
	r.close();
	return dispositivos;
    }

    private Telephone[] getTelefones(final int idcliente) throws SQLException {
	final PreparedResultSet r = select(
		"SELECT `id`,`tipo`,`telefone`,`observacoes` FROM `clientes_telefones` WHERE `idcliente`=? AND `log_excluido` IS NULL",
		idcliente);
	final Telephone[] telefones = new Telephone[r.getRowCount()];
	final int i = 0;
	while (r.rs.next()) {
	    final int id = r.rs.getInt("id");
	    final String tipo = r.rs.getString("tipo");
	    final String telefone = r.rs.getString("telefone");
	    final String observacoes = r.rs.getString("observacoes");
	    // telefones[i++] = new Telephone(id, idcliente, tipo, telefone,
	    // observacoes);
	}
	r.close();
	return telefones;
    }

    public InventoryDB inventory() {
	return inventory;
    }

    public void keepAlive() throws SQLException {
	selectFirstField_int("SELECT 1");
    }

    public int lancarPedido(final int userid, final int idcliente,
	    final ResumoPedido resumo) throws SQLException {
	final String sql = "INSERT INTO `delivery_pedidos` "// insert
		+ " (`idcliente`,`idendereco`,`consumo`,`datahora`,`log_criado_em`,`log_criado_por`) " // fields
		+ " VALUES(?,?,?,?,NOW(),?)";
	return insertLastId(sql, idcliente, resumo.idendereco, resumo.consumo,
		resumo.datahora, userid);
    }

    public MessageDB message() {
	return message;
    }

    public InventoryLog obterContagemHistorico(final int iditem)
	    throws SQLException {
	final String sql = "SELECT * FROM `contagem_log` WHERE `produto`=? "// select
		+ " ORDER BY `datahora` DESC LIMIT 10";
	final PreparedResultSet p = select(sql, iditem);
	final InventoryLog h = new InventoryLog(iditem, p.getRowCount());
	int i = 0;
	final ResultSet rs = p.rs;
	while (rs.next()) {
	    final int id = rs.getInt("id");
	    final int idusuario = rs.getInt("idusuario");
	    final String tipo_s = rs.getString("tipo");
	    final char tipo = tipo_s.length() > 0 ? tipo_s.charAt(0) : '?';
	    final int idmotivo = rs.getInt("idmotivo");
	    final float quantidadeInicial = rs.getFloat("quantidade_inicial");
	    final float variacao = rs.getFloat("variacao");
	    final LocalDateTime datahora = JodaTimeUtils.parseLocalDateTime(rs
		    .getString("datahora"));
	    final String observacoes = rs.getString("observacoes");
	    final InventoryLogEntry hr = new InventoryLogEntry(id, iditem,
		    idusuario, tipo, idmotivo, quantidadeInicial, variacao,
		    datahora, observacoes);
	    h.set(i++, hr);
	}
	return h;
    }

    @Override
    public boolean open(final Configuration config) {
	// TODO add timer or something to keep trying
	if (!super.open(config)) {
	    return false;
	}
	try {
	    execution_id = insertLastId("INSERT INTO `log_execution` (`started`) VALUES (NOW())");
	    return true;
	} catch (final SQLException e) {
	    logger.debug("Error inserting row. Closing database...", e);
	    close();
	    return false;
	}
    }

    public PersonDB person() {
	return person;
    }

    public UsersDB users() {
	return users;
    }
}
