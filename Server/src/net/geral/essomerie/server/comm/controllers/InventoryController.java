package net.geral.essomerie.server.comm.controllers;

import java.io.IOException;
import java.sql.SQLException;

import net.geral.essomerie._shared.contagem.ContagemAlteracaoQuantidade;
import net.geral.essomerie._shared.contagem.Inventory;
import net.geral.essomerie._shared.contagem.InventoryGroup;
import net.geral.essomerie._shared.contagem.InventoryItemReport;
import net.geral.essomerie._shared.contagem.InventoryLog;
import net.geral.essomerie._shared.contagem.InventoryLogEntry;
import net.geral.essomerie.server.Server;
import net.geral.essomerie.server.comm.Connection;
import net.geral.essomerie.shared.communication.ConnectionController;
import net.geral.essomerie.shared.communication.MessageData;
import net.geral.essomerie.shared.communication.MessageSubSystem;
import net.geral.essomerie.shared.communication.types.InventoryMessageType;

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
	case RequestItemReport:
	    requestItemReport(md.getInt());
	    break;
	case RequestGroupParentOrderChange:
	    requestGroupParentOrderChange(md.getInt(), md.getInt(), md.getInt());
	    break;
	case RequestGroupDelete:
	    requestGroupDelete(md.getInt());
	    break;
	case RequestGroupRename:
	    requestGroupRename(md.getInt(), md.getString());
	    break;
	case RequestGroupAdd:
	    requestGroupAdd(md.getInt(), md.getString());
	    break;
	default:
	    logger.warn("Invalid type: " + type.name());
	}
    }

    private void requestFullData() throws SQLException, IOException {
	final Inventory i = Server.db().inventory().getFullData();
	send(InventoryMessageType.InformFullData, i);
    }

    private void requestGroupAdd(final int idparent, final String newName)
	    throws SQLException {
	Server.db().inventory()
		.addGroup(connection.getUserId(), idparent, newName);
	final InventoryGroup[] groups = Server.db().inventory().getGroups();
	Server.broadcast(MessageSubSystem.Inventory,
		InventoryMessageType.InformGroups, (Object) groups);
    }

    private void requestGroupDelete(final int idgroup) throws SQLException {
	Server.db().inventory().deleteGroup(connection.getUserId(), idgroup);
	final InventoryGroup[] groups = Server.db().inventory().getGroups();
	Server.broadcast(MessageSubSystem.Inventory,
		InventoryMessageType.InformGroups, (Object) groups);
    }

    private void requestGroupParentOrderChange(final int idgroup,
	    final int idparent, final int order) throws IOException,
	    SQLException {
	Server.db().inventory().updateGroupParent(idgroup, idparent);
	Server.db().inventory().updateGroupOrder(idgroup, order);
	final InventoryGroup[] groups = Server.db().inventory().getGroups();
	Server.broadcast(MessageSubSystem.Inventory,
		InventoryMessageType.InformGroups, (Object) groups);
    }

    private void requestGroupRename(final int idgroup, final String newName)
	    throws SQLException {
	Server.db().inventory()
		.renameGroup(connection.getUserId(), idgroup, newName);
	final InventoryGroup[] groups = Server.db().inventory().getGroups();
	Server.broadcast(MessageSubSystem.Inventory,
		InventoryMessageType.InformGroups, (Object) groups);
    }

    private void requestItemReport(final int iditem) throws SQLException,
	    IOException {
	final InventoryItemReport r = Server.db().inventory()
		.getItemReport(iditem);
	send(InventoryMessageType.InformItemReport, r);
    }

    private void requestLogByDate(final LocalDate from, final LocalDate until)
	    throws SQLException, IOException {
	final InventoryLogEntry[] entries = Server.db().inventory()
		.getContagemAcertos(from, until);
	send(InventoryMessageType.InformLogByDate, from, until, entries);
    }

    private void requestLogByItem(final int iditem) throws IOException,
	    SQLException {
	final InventoryLog historico = Server.db().inventory()
		.obterContagemHistorico(iditem);
	send(InventoryMessageType.InformLogByItem, historico);
    }

    private void requestQuantityChange(
	    final ContagemAlteracaoQuantidade alteracao) throws SQLException,
	    IOException {

	final float novaQuantidade = Server.db().inventory()
		.alterarContagem(connection.getUserId(), alteracao);

	Server.broadcast(MessageSubSystem.Inventory,
		InventoryMessageType.InformQuantityChange, alteracao.iditem,
		novaQuantidade);
    }
}
