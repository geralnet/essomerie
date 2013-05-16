package net.geral.essomerie.server.comm.controllers;

import java.io.IOException;
import java.sql.SQLException;

import net.geral.essomerie._shared.communication.ConnectionController;
import net.geral.essomerie._shared.communication.MessageData;
import net.geral.essomerie._shared.communication.MessageSubSystem;
import net.geral.essomerie._shared.communication.types.InventoryMessageType;
import net.geral.essomerie._shared.contagem.ContagemAlteracaoQuantidade;
import net.geral.essomerie._shared.contagem.Inventory;
import net.geral.essomerie._shared.contagem.InventoryLog;
import net.geral.essomerie._shared.contagem.InventoryLogEntry;
import net.geral.essomerie.server.Server;
import net.geral.essomerie.server.comm.Connection;

import org.apache.log4j.Logger;
import org.joda.time.LocalDate;

//TODO translate & check
public class InventoryController extends
	ConnectionController<InventoryMessageType> {
    private static final Logger logger = Logger
	    .getLogger(InventoryController.class);
    private final Connection connection;

    public InventoryController(final Connection c) {
	super(c, null, MessageSubSystem.Inventory);
	connection = c;
    }

    @Override
    protected void process(final InventoryMessageType type, final MessageData md)
	    throws IOException, SQLException {

	switch (type) {
	case RequestFullData:
	    requestFullData();
	    break;
	case RequestQuantityChange:
	    requestQuantityChange((ContagemAlteracaoQuantidade) md.get());
	    break;
	case RequestLogByItem:
	    requestLogByItem(md.getInt());
	    break;
	case RequestLogByDate:
	    requestLogByDate((LocalDate) md.get(), (LocalDate) md.get());
	    break;
	default:
	    logger.warn("Invalid type: " + type.name());
	}
    }

    private void requestFullData() throws SQLException, IOException {
	final Inventory i = Server.db().inventory().getFullData();
	send(InventoryMessageType.InformFullData, i);
    }

    private void requestLogByDate(final LocalDate from, final LocalDate until)
	    throws SQLException, IOException {
	final InventoryLogEntry[] entries = Server.db().getContagemAcertos(
		from, until);
	send(InventoryMessageType.InformLogByDate, from, until, entries);
    }

    private void requestLogByItem(final int iditem) throws IOException,
	    SQLException {
	final InventoryLog historico = Server.db().obterContagemHistorico(
		iditem);
	send(InventoryMessageType.InformLogByItem, historico);
    }

    private void requestQuantityChange(
	    final ContagemAlteracaoQuantidade alteracao) throws SQLException,
	    IOException {

	final float novaQuantidade = Server.db().alterarContagem(
		connection.getUserId(), alteracao);

	Server.broadcast(MessageSubSystem.Inventory,
		InventoryMessageType.InformQuantityChange, alteracao.iditem,
		novaQuantidade);
    }
}
