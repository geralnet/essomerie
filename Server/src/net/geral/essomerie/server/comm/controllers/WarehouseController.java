package net.geral.essomerie.server.comm.controllers;

import java.io.IOException;
import java.sql.SQLException;

import net.geral.essomerie.server.Server;
import net.geral.essomerie.server.comm.Connection;
import net.geral.essomerie.shared.communication.ConnectionController;
import net.geral.essomerie.shared.communication.MessageData;
import net.geral.essomerie.shared.communication.MessageSubSystem;
import net.geral.essomerie.shared.communication.types.WarehouseMessageType;
import net.geral.essomerie.shared.warehouse.Warehouse;
import net.geral.essomerie.shared.warehouse.WarehouseChangeLog;
import net.geral.essomerie.shared.warehouse.WarehouseChangeLogEntry;
import net.geral.essomerie.shared.warehouse.WarehouseGroup;
import net.geral.essomerie.shared.warehouse.WarehouseItem;
import net.geral.essomerie.shared.warehouse.WarehouseQuantityChange;
import net.geral.essomerie.shared.warehouse.report.MonthlyFlowReport;

import org.apache.log4j.Logger;
import org.joda.time.LocalDate;

public class WarehouseController extends
	ConnectionController<WarehouseMessageType> {
    private static final Logger logger = Logger
	    .getLogger(WarehouseController.class);
    private final Connection connection;

    public WarehouseController(final Connection c) {
	super(c, null, MessageSubSystem.Warehouse);
	connection = c;
    }

    @Override
    protected void process(final WarehouseMessageType type, final MessageData md)
	    throws IOException, SQLException {

	switch (type) {
	case RequestFullData:
	    requestFullData();
	    break;
	case RequestQuantityChange:
	    requestQuantityChange((WarehouseQuantityChange) md.get());
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
	case RequestDeleteItem:
	    requestDeleteItem(md.getInt());
	    break;
	case RequestChangeItem:
	    requestChangeItem((WarehouseItem) md.get());
	    break;
	default:
	    logger.warn("Invalid type: " + type.name());
	}
    }

    private void requestChangeItem(WarehouseItem wi) throws SQLException,
	    IOException {
	final int iduser = connection.getUserId();
	if (wi.id == 0) {
	    final int id = Server.db().warehouse().createItem(iduser, wi.name);
	    wi = wi.withId(id);
	    send(WarehouseMessageType.InformItemCreated, wi.id);
	}
	Server.db().warehouse().updateItem(iduser, wi);
	Server.broadcast(MessageSubSystem.Warehouse,
		WarehouseMessageType.InformChangeItem, wi);
    }

    private void requestDeleteItem(final int iditem) throws SQLException {
	if (Server.db().warehouse().deleteItem(connection.getUserId(), iditem)) {
	    Server.broadcast(MessageSubSystem.Warehouse,
		    WarehouseMessageType.InformItemDeleted, iditem);
	}
    }

    private void requestFullData() throws SQLException, IOException {
	final Warehouse w = Server.db().warehouse().getFullData();
	send(WarehouseMessageType.InformFullData, w);
    }

    private void requestGroupAdd(final int idparent, final String newName)
	    throws SQLException {
	Server.db().warehouse()
		.addGroup(connection.getUserId(), idparent, newName);
	final WarehouseGroup[] groups = Server.db().warehouse().getGroups();
	Server.broadcast(MessageSubSystem.Warehouse,
		WarehouseMessageType.InformGroups, (Object) groups);
    }

    private void requestGroupDelete(final int idgroup) throws SQLException {
	Server.db().warehouse().deleteGroup(connection.getUserId(), idgroup);
	final WarehouseGroup[] groups = Server.db().warehouse().getGroups();
	Server.broadcast(MessageSubSystem.Warehouse,
		WarehouseMessageType.InformGroups, (Object) groups);
    }

    private void requestGroupParentOrderChange(final int idgroup,
	    final int idparent, final int order) throws IOException,
	    SQLException {
	Server.db().warehouse().updateGroupParent(idgroup, idparent);
	Server.db().warehouse().updateGroupOrder(idgroup, order);
	final WarehouseGroup[] groups = Server.db().warehouse().getGroups();
	Server.broadcast(MessageSubSystem.Warehouse,
		WarehouseMessageType.InformGroups, (Object) groups);
    }

    private void requestGroupRename(final int idgroup, final String newName)
	    throws SQLException {
	Server.db().warehouse()
		.renameGroup(connection.getUserId(), idgroup, newName);
	final WarehouseGroup[] groups = Server.db().warehouse().getGroups();
	Server.broadcast(MessageSubSystem.Warehouse,
		WarehouseMessageType.InformGroups, (Object) groups);
    }

    private void requestItemReport(final int iditem) throws SQLException,
	    IOException {
	final MonthlyFlowReport r = Server.db().warehouse()
		.getItemReport(iditem);
	send(WarehouseMessageType.InformItemReport, r);
    }

    private void requestLogByDate(final LocalDate from, final LocalDate until)
	    throws SQLException, IOException {
	final WarehouseChangeLogEntry[] entries = Server.db().warehouse()
		.getQuantityLog(from, until);
	send(WarehouseMessageType.InformLogByDate, from, until, entries);
    }

    private void requestLogByItem(final int iditem) throws IOException,
	    SQLException {
	final WarehouseChangeLog historico = Server.db().warehouse()
		.getQuantityHistory(iditem);
	send(WarehouseMessageType.InformLogByItem, historico);
    }

    private void requestQuantityChange(final WarehouseQuantityChange change)
	    throws SQLException, IOException {

	final float novaQuantidade = Server.db().warehouse()
		.change(connection.getUserId(), change);

	Server.broadcast(MessageSubSystem.Warehouse,
		WarehouseMessageType.InformQuantityChange, change.iditem,
		novaQuantidade);
    }
}
