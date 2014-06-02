package net.geral.essomerie.server.db.areas;

import java.sql.ResultSet;
import java.sql.SQLException;

import net.geral.essomerie._shared.contagem.ContagemAlteracaoQuantidade;
import net.geral.essomerie._shared.contagem.ContagemMotivo;
import net.geral.essomerie._shared.contagem.ContagemMotivos;
import net.geral.essomerie._shared.contagem.Inventory;
import net.geral.essomerie._shared.contagem.InventoryGroup;
import net.geral.essomerie._shared.contagem.InventoryItem;
import net.geral.essomerie._shared.contagem.InventoryItemReport;
import net.geral.essomerie._shared.contagem.InventoryLog;
import net.geral.essomerie._shared.contagem.InventoryLogEntry;
import net.geral.essomerie.server.db.Database;
import net.geral.essomerie.server.db.DatabaseArea;
import net.geral.essomerie.server.db.PreparedResultSet;
import net.geral.lib.jodatime.GNJoda;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

//TODO translate & check
public class InventoryDB extends DatabaseArea {
    public InventoryDB(final Database database) {
	super(database);
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
	db.insert(sql, userid, a.tipo, a.idmotivo, a.iditem, a.iditem,
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
	db.update(sql, a.quantidade, a.iditem);

	sql = "SELECT `quantidade` FROM `contagem_itens` WHERE `id`=?";
	final float novaQuantidade = db.selectFirstField_float(sql, a.iditem);
	return novaQuantidade;
    }

    public InventoryLogEntry[] getContagemAcertos(final LocalDate de,
	    final LocalDate ate) throws SQLException {
	final String sql = "SELECT * FROM `contagem_log` WHERE datahora>=? AND datahora <? ORDER BY datahora ASC";
	final LocalDateTime dt_de = new LocalDateTime(de.toDateMidnight());
	final LocalDateTime dt_ate = dt_de.plusDays(2);
	final PreparedResultSet p = db.select(sql, dt_de, dt_ate);
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
	    final LocalDateTime datahora = GNJoda.sqlLocalDateTime(
		    p.rs.getString("datahora"), false);
	    final String observacoes = p.rs.getString("observacoes");
	    res[i++] = new InventoryLogEntry(id, iditem, idusuario, tipo,
		    idmotivo, quantidadeInicial, variacao, datahora,
		    observacoes);
	}
	return res;
    }

    private InventoryGroup[] getContagemGrupos() throws SQLException {
	return getContagemGrupos(0);
    }

    private InventoryGroup[] getContagemGrupos(final int pai)
	    throws SQLException {
	final String sql = "SELECT `id`,`nome` "// select
		+ "FROM `contagem_grupos` "// from
		+ " WHERE `subgrupo_de`=? AND `excluido`='N' "// where
		+ " ORDER BY `ordem` ASC, `nome` ASC";
	try (final PreparedResultSet p = db.select(sql, pai)) {
	    final InventoryGroup[] cg = new InventoryGroup[p.getRowCount()];
	    for (int i = 0; i < cg.length; i++) {
		p.rs.next();
		final int id = p.rs.getInt("id");
		final String nome = p.rs.getString("nome");
		final InventoryGroup[] subgrupos = (pai == 0) ? getContagemGrupos(id)
			: null; // apenas um nivel de subgrupos
		cg[i] = new InventoryGroup(id, nome, subgrupos);
	    }
	    return cg;
	}
    }

    private InventoryItem[] getContagemItens() throws SQLException {
	final String sql = "SELECT `id`,`nome`,`unidade`,`quantidade` "// select
		+ "FROM `contagem_itens` "// from
		+ " WHERE `excluido`='N' "// where
		+ " ORDER BY `nome` ASC";
	try (final PreparedResultSet p = db.select(sql)) {
	    final InventoryItem[] ci = new InventoryItem[p.getRowCount()];
	    for (int i = 0; i < ci.length; i++) {
		p.rs.next();
		final int id = p.rs.getInt("id");
		final String nome = p.rs.getString("nome");
		final String unidade = p.rs.getString("unidade");
		final float quantidade = p.rs.getFloat("quantidade");
		final int[] grupos = getContagemItensGrupos(id);
		ci[i] = new InventoryItem(id, nome, unidade, quantidade, grupos);
	    }
	    return ci;
	}
    }

    private int[] getContagemItensGrupos(final int iditem) throws SQLException {
	final String sql = "SELECT `idgrupo` "// select
		+ "FROM `contagem_itemXgrupo` "// from
		+ " WHERE `iditem`=? ";// where
	try (final PreparedResultSet p = db.select(sql, iditem)) {
	    final int[] is = new int[p.getRowCount()];
	    for (int i = 0; i < is.length; i++) {
		p.rs.next();
		is[i] = p.rs.getInt(1);
	    }
	    return is;
	}
    }

    private ContagemMotivos getContagemMotivos() throws SQLException {
	final String sql = "SELECT `id`,`tipo`,`motivo` "// select
		+ "FROM `contagem_motivos` ORDER BY `tipo` ASC, `id` ASC";
	try (final PreparedResultSet p = db.select(sql)) {
	    final ContagemMotivo[] cm = new ContagemMotivo[p.getRowCount()];
	    for (int i = 0; i < cm.length; i++) {
		p.rs.next();
		final int id = p.rs.getInt("id");
		final char tipo = p.rs.getString("tipo").charAt(0);
		final String motivo = p.rs.getString("motivo");
		cm[i] = new ContagemMotivo(id, tipo, motivo);
	    }
	    return new ContagemMotivos(cm);
	}
    }

    public Inventory getFullData() throws SQLException {
	final Inventory c = new Inventory();
	c.setMotivos(getContagemMotivos());
	c.setGrupos(getContagemGrupos());
	c.setItens(getContagemItens());
	return c;
    }

    public InventoryItem getItem(final int iditem) throws SQLException {
	final String sql = "SELECT `id`,`nome`,`unidade`,`quantidade` "// select
		+ "FROM `contagem_itens` "// from
		+ " WHERE `id`=? "// where
	;
	try (final PreparedResultSet p = db.select(sql, iditem)) {
	    if (!p.rs.next()) {
		return null;
	    }
	    final int id = p.rs.getInt("id");
	    final String nome = p.rs.getString("nome");
	    final String unidade = p.rs.getString("unidade");
	    final float quantidade = p.rs.getFloat("quantidade");
	    final int[] grupos = getContagemItensGrupos(id);
	    return new InventoryItem(id, nome, unidade, quantidade, grupos);
	}
    }

    public InventoryItemReport getItemReport(final int iditem)
	    throws SQLException {
	final InventoryItem item = getItem(iditem);
	final String sql = "SELECT "// select
		+ " CONCAT(`m`.`motivo`,' (',`m`.`tipo`,')') AS `reason`, " // reason
		+ " `l`.`quantidade_inicial`, " // month started with
		+ " DATE(`l`.`datahora`) AS `date`," // date
		+ " IF(`l`.`tipo`='-',-1,1)*SUM(IF(`l`.`tipo`='=',ROUND(`l`.`variacao`-`l`.`quantidade_inicial`,2),`l`.`variacao`)) AS `delta`" // delta
		+ " FROM `contagem_log` AS `l` " // from
		+ " INNER JOIN `contagem_motivos` AS `m` ON (`m`.`id`=`l`.`idmotivo`) AND (`l`.`tipo`=`m`.`tipo`) " // join
		+ " WHERE `l`.`produto`=?" // where
		+ " GROUP BY YEAR(`l`.`datahora`) DESC, MONTH(`l`.`datahora`) DESC, `l`.`tipo` ASC, `m`.`motivo` ASC" // group
		+ " ORDER BY `l`.`datahora` ASC"; // order
	final InventoryItemReport r = new InventoryItemReport(item);
	final PreparedResultSet p = db.select(sql, iditem);
	final ResultSet rs = p.rs;
	while (rs.next()) {
	    final LocalDate date = GNJoda.sqlLocalDate(rs.getString("date"),
		    false).withDayOfMonth(1);
	    final String reason = rs.getString("reason");
	    final float delta = rs.getFloat("delta");
	    final float initial = rs.getFloat("quantidade_inicial");
	    r.addEntry(date, initial, reason, delta);
	}
	r.block();
	return r;
    }

    public InventoryLog obterContagemHistorico(final int iditem)
	    throws SQLException {
	final String sql = "SELECT * FROM `contagem_log` WHERE `produto`=? "// select
		+ " ORDER BY `datahora` DESC LIMIT 10";
	final PreparedResultSet p = db.select(sql, iditem);
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
	    final LocalDateTime datahora = GNJoda.sqlLocalDateTime(
		    rs.getString("datahora"), false);
	    final String observacoes = rs.getString("observacoes");
	    final InventoryLogEntry hr = new InventoryLogEntry(id, iditem,
		    idusuario, tipo, idmotivo, quantidadeInicial, variacao,
		    datahora, observacoes);
	    h.set(i++, hr);
	}
	return h;
    }
}
