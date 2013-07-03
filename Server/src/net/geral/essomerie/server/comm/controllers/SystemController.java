package net.geral.essomerie.server.comm.controllers;

import java.io.IOException;
import java.sql.SQLException;

import net.geral.essomerie._shared.CRUtil;
import net.geral.essomerie._shared.User;
import net.geral.essomerie._shared.UserPermissions;
import net.geral.essomerie.server.Server;
import net.geral.essomerie.server.comm.Connection;
import net.geral.essomerie.shared.BuildInfo;
import net.geral.essomerie.shared.communication.ConnectionController;
import net.geral.essomerie.shared.communication.MessageData;
import net.geral.essomerie.shared.communication.MessageSubSystem;
import net.geral.essomerie.shared.communication.types.SystemMessageType;

import org.apache.log4j.Logger;

public class SystemController extends ConnectionController<SystemMessageType> {
    private static final Logger logger = Logger
	    .getLogger(SystemController.class);
    private final Connection connection;

    public SystemController(final Connection c) {
	super(c, null, MessageSubSystem.System);
	connection = c;
    }

    @Override
    protected void process(final SystemMessageType type, final MessageData md)
	    throws IOException, SQLException {
	switch (type) {
	case Ping:
	    send(SystemMessageType.Pong, md.getLong());
	    break;
	case Pong:
	    connection.comm().pong(md.getLong());
	    break;
	case InformVersion:
	    receivedInformVersion((BuildInfo) md.get());
	    break;
	case RequestLogin:
	    requestLogin(md.getInt(), (char[]) md.get());
	    break;
	default:
	    logger.warn("Invalid type: " + type.name());
	}
    }

    private void receivedInformVersion(final BuildInfo version)
	    throws IOException {
	// TODO check version!
	connection.setRemoteVersion(version);
	send(SystemMessageType.InformVersion, BuildInfo.CURRENT);
    }

    private void requestLogin(final int informedUserId, final char[] cs)
	    throws IOException, SQLException {
	// small delay to avoid brute force attacks
	try {
	    final int delay = Server.config().LoginBaseDelay;
	    Thread.sleep(CRUtil.Random.nextInt(delay) + delay);
	} catch (final InterruptedException e) {
	    logger.warn("Interrupted while delayed: ", e);
	}

	// check acceptance
	final User user = Server.db().users()
		.getUserWithPassword(informedUserId, cs);
	if (user != null) {
	    final int iduser = user.getId();
	    final UserPermissions permissions = Server.db().users()
		    .getUserPermissions(iduser);
	    connection.setLoggedUser(iduser, permissions);
	    send(SystemMessageType.InformLoginAccepted, iduser, permissions);
	} else {
	    send(SystemMessageType.InformLoginFailed);
	}
    }
}
