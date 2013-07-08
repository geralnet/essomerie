package net.geral.essomerie.server.comm;

import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;

import net.geral.essomerie._shared.UserPermissions;
import net.geral.essomerie.server.Server;
import net.geral.essomerie.server.comm.controllers.BulletinBoardController;
import net.geral.essomerie.server.comm.controllers.CalendarController;
import net.geral.essomerie.server.comm.controllers.CallerIdController;
import net.geral.essomerie.server.comm.controllers.CatalogController;
import net.geral.essomerie.server.comm.controllers.InventoryController;
import net.geral.essomerie.server.comm.controllers.MessagesController;
import net.geral.essomerie.server.comm.controllers.PersonsController;
import net.geral.essomerie.server.comm.controllers.SalesController;
import net.geral.essomerie.server.comm.controllers.SystemController;
import net.geral.essomerie.server.comm.controllers.UsersController;
import net.geral.essomerie.shared.BuildInfo;
import net.geral.essomerie.shared.communication.Communication;
import net.geral.essomerie.shared.communication.ICommunication;
import net.geral.essomerie.shared.communication.IMessageType;
import net.geral.essomerie.shared.communication.MessageData;
import net.geral.essomerie.shared.communication.MessageSubSystem;
import net.geral.essomerie.shared.communication.types.SystemMessageType;

import org.apache.log4j.Logger;

//TODO set all fields private
public class Connection extends Thread implements ICommunication {
    private static final Logger logger = Logger.getLogger(Connection.class);
    private static int lastId = 0;

    private final int id;

    private BuildInfo remoteVersion = null;
    protected int userId = 0;
    protected UserPermissions userPermissions = new UserPermissions();
    protected Communication comm;

    private final BulletinBoardController bulletinBoard = new BulletinBoardController(
	    this);
    private final CalendarController calendar = new CalendarController(this);
    private final InventoryController inventory = new InventoryController(this);
    private final MessagesController messages = new MessagesController(this);
    private final PersonsController persons = new PersonsController(this);
    private final SystemController system = new SystemController(this);
    private final UsersController users = new UsersController(this);
    private final CallerIdController callerid = new CallerIdController(this);
    private final SalesController sales = new SalesController(this);
    private final CatalogController catalog = new CatalogController(this);

    public Connection(final Socket socket) throws IOException {
	id = ++lastId;
	comm = new Communication(socket, id);
	setName("Client#" + id);
	logger.info(toString() + " connected.");
    }

    private boolean checkAccess(final MessageData md) throws IOException {
	if (remoteVersion == null) {
	    // must receive a version request with the remote's version
	    // if ok allow access
	    if ((md.getSubSystem() == MessageSubSystem.System)
		    && (md.getType() == SystemMessageType.InformVersion)) {
		return true;
	    }
	    // invalid, disconnect without warning
	    logger.error("Invalid message, dropping connection.");
	    close();
	    return false;
	}

	final IMessageType mt = (IMessageType) md.getType();
	if (!userPermissions.get(mt.requires())) {
	    final String msg = "Access Denied: required=" + mt.requires()
		    + ", permissions=" + userPermissions;
	    logger.warn(msg);
	    comm.send(MessageSubSystem.System, SystemMessageType.InformError,
		    msg);
	    return false;
	}

	return true;
    }

    public void close() {
	// notify
	logger.debug(toString() + " closing...");
	// close comm
	if (comm != null) {
	    comm.close();
	    comm = null;
	}
    }

    @Override
    public Communication comm() {
	return comm;
    }

    public int getUserId() {
	return userId;
    }

    private void processMessage(final MessageData md) throws SQLException,
	    IOException {
	switch (md.getSubSystem()) {
	case BulletinBoard:
	    bulletinBoard.process(md);
	    break;
	case Calendar:
	    calendar.process(md);
	    break;
	case Inventory:
	    inventory.process(md);
	    break;
	case Messages:
	    messages.process(md);
	    break;
	case Persons:
	    persons.process(md);
	    break;
	case System:
	    system.process(md);
	    break;
	case Users:
	    users.process(md);
	    break;
	case CallerId:
	    callerid.process(md);
	    break;
	case Sales:
	    sales.process(md);
	    break;
	case Catalog:
	    catalog.process(md);
	    break;
	default:
	    logger.warn("Invalid subsystem: " + md.getSubSystem());
	}
	comm().send(MessageSubSystem.System, SystemMessageType.Processed,
		md.getId());
    }

    // main
    @Override
    public void run() {
	try {
	    logger.debug("Client run() started!");
	    Communication c = comm;
	    while ((c != null) && (c.isWorking())) {
		c.loop();
		final MessageData md = c.recv();
		if (md == null) {
		    Thread.sleep(Server.config().ConnectionNoMessageSleep);
		} else {
		    if (checkAccess(md)) {
			processMessage(md);
		    }
		    yield();
		}
		c = comm; // comm can change to null at any time (close)
	    }
	    logger.debug("Client run() finished (normal)!");
	} catch (final IOException e) {
	    // connection closed
	    logger.debug("Client run() finished (IOException)!", e);
	} catch (final Exception e) {
	    logger.warn("Client run() finished (General Exception)!", e);
	} finally {
	    close();
	}
    }

    public void setLoggedUser(final int iduser,
	    final UserPermissions permissions) {
	userId = iduser;
	userPermissions = permissions;
    }

    public void setRemoteVersion(final BuildInfo version) {
	remoteVersion = version;
    }
}
