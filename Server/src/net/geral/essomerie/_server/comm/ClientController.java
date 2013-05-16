package net.geral.essomerie._server.comm;

import java.io.IOException;
import java.net.Socket;

import net.geral.essomerie.server.comm.Connection;

import org.apache.log4j.Logger;

@Deprecated
public class ClientController extends Connection {
    public static final Logger logger = Logger
	    .getLogger(ClientController.class);

    public ClientController(final Socket socket) throws IOException {
	super(socket);
    }

    // private RunResult requestClientesAlterar(final Customer cliente) throws
    // IOException, SQLException {
    // if (cliente.idcliente == 0) {
    // comm.send(MessageSubSystem.ERROR,
    // "Alterar cliente com ID=0 não permitido.");
    // }
    // else {
    // //TODO Server.db().Customers.change(userId, cliente);
    // Server.broadcast(MessageSubSystem.INFORM_CLIENTES_ALTERADO,
    // cliente.idcliente);
    // }
    // return RunResult.NO_YIELD;
    // }

    // private RunResult requestClientesCadastrar(final Customer cliente)
    // throws IOException, SQLException {
    // if (cliente.idcliente != 0) {
    // comm.send(MessageSubSystem.ERROR, "Alterar cliente com ID="
    // + cliente.idcliente + " não permitido.");
    // } else {
    // final Customer novo = Server.db().cadastrarCliente(userId, cliente);
    // Server.broadcast(MessageSubSystem.INFORM_CLIENTES_CADASTRADO,
    // novo.idcliente);
    // }
    // return RunResult.NO_YIELD;
    // }

    // private RunResult requestClientesDados(final int idcliente)
    // throws IOException, SQLException {
    // comm.send(MessageSubSystem.INFORM_CLIENTES_DADOS, Server.db()
    // .getCliente(idcliente));
    // return RunResult.NO_YIELD;
    // }

    // private RunResult requestClientesDadosExtras(final int idcliente)
    // throws IOException, SQLException {
    // final ClienteDadosExtras extras = Server.db().getClientesDadosExtras(
    // idcliente);
    // comm.send(MessageSubSystem.INFORM_CLIENTES_DADOS_EXTRAS, extras);
    // return RunResult.NO_YIELD;
    // }

    // private RunResult requestClientesDataSet() throws SQLException,
    // IOException {
    // final ClientesDataSet clientes = Server.db().getClientesDataSet();
    // comm.send(MessageSubSystem.INFORM_CLIENTES_DATASET, clientes);
    // return RunResult.NO_YIELD;
    // }

    // private RunResult requestFuncionariosDados(final int idfuncionario)
    // throws IOException, SQLException {
    // final Funcionario f = Server.db().getFuncionario(idfuncionario);
    // comm.send(MessageSubSystem.INFORM_FUNCIONARIOS_DADOS, f);
    // return RunResult.NO_YIELD;
    // }

    // private RunResult requestFuncionariosListegem() throws SQLException,
    // IOException {
    // final String[] cargos = Server.db().getFuncionariosCargos();
    // final String[] bancos = Server.db().getFuncionariosBancos();
    // final FuncionarioBasico[] funcionarios = Server.db()
    // .getFuncionariosListagem();
    // comm.send(MessageSubSystem.INFORM_FUNCIONARIOS_LISTAGEM, cargos,
    // bancos, funcionarios);
    // return RunResult.NO_YIELD;
    // }

    // private RunResult requestInformacoesInformacao(final int id)
    // throws IOException, SQLException {
    // final BulletinBoardEntry info = Server.db().bulletinBoard.get(id);
    // comm.send(MessageSubSystem.INFORM_INFORMACOES_INFORMACAO, info);
    // return RunResult.NO_YIELD;
    // }

    // private RunResult requestPedidosLancar(final int idcliente,
    // final ResumoPedido resumo) throws IOException, SQLException {
    // Server.db().lancarPedido(userId, idcliente, resumo);
    // Server.broadcast(MessageSubSystem.INFORM_PEDIDO_LANCADO, idcliente);
    // return RunResult.NO_YIELD;
    // }

    // private RunResult requestPedidosResumo(final int idcliente)
    // throws SQLException, IOException {
    // final ResumoPedido[] pedidos = Server.db().getPedidosResumo(idcliente);
    // comm.send(MessageSubSystem.INFORM_PEDIDOS_RESUMO, idcliente, pedidos);
    // return RunResult.NO_YIELD;
    // }

    // private RunResult requestSysadminDispositivos() throws SQLException,
    // IOException {
    // final Dispositivo[] dispositivos = Server.db().getSistemaDispositivos();
    // comm.send(MessageSubSystem.INFORM_SYSADMIN_DISPOSITIVOS,
    // (Object) dispositivos);
    // return RunResult.NO_YIELD;
    // }

    // private RunResult requestSysadminMonitor(final int iddispositivo)
    // throws SQLException, IOException {
    // final DispositivoMonitor[] monitor = Server.db().getDispositivoMonitor(
    // iddispositivo);
    // comm.send(MessageSubSystem.INFORM_SYSADMIN_MONITOR, iddispositivo,
    // monitor);
    // return RunResult.NO_YIELD;
    // }

    // private RunResult requestSysadminMonitorScreen(
    // final int idDispositivoMonitor) throws SQLException, IOException {
    // final byte[] screen = Server.db().getDispositivoMonitorScreen(
    // idDispositivoMonitor);
    // comm.send(MessageSubSystem.INFORM_SYSADMIN_MONITOR_SCREEN,
    // idDispositivoMonitor, screen);
    // return RunResult.NO_YIELD;
    // }
}
