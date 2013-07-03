package net.geral.essomerie.server.comm.controllers;

import java.io.IOException;
import java.sql.SQLException;

import net.geral.essomerie.server.Server;
import net.geral.essomerie.server.comm.Connection;
import net.geral.essomerie.server.comm.ServerConnectionController;
import net.geral.essomerie.shared.communication.Communication;
import net.geral.essomerie.shared.communication.MessageData;
import net.geral.essomerie.shared.communication.MessageSubSystem;
import net.geral.essomerie.shared.communication.types.MessagesMessageType;
import net.geral.essomerie.shared.messages.Message;

import org.apache.log4j.Logger;

public class MessagesController extends
	ServerConnectionController<MessagesMessageType> {
    private static final Logger logger = Logger
	    .getLogger(MessagesController.class);

    public MessagesController(final Connection c) {
	super(c, MessageSubSystem.Messages);
    }

    @Override
    protected void process(final MessagesMessageType type, final MessageData md)
	    throws IOException, SQLException {
	switch (type) {
	case RequestMessagesToUser:
	    requestMessagesToUser();
	    break;
	case RequestDelete:
	    requestDelete((int[]) md.get());
	    break;
	case RequestRead:
	    requestRead(md.getInt());
	    break;
	case RequestSend:
	    requestSend((int[]) md.get(), md.getString());
	    break;
	default:
	    logger.warn("Invalid type: " + type.name());
	}
    }

    private void requestDelete(int[] idmsgs) throws IOException, SQLException {
	idmsgs = Server.db().message().delete(idmsgs, connection.getUserId());
	send(MessagesMessageType.InformDeleted, idmsgs);
    }

    private void requestMessagesToUser() throws IOException, SQLException {
	final Message[] msgs = Server.db().message()
		.getToUser(connection.getUserId());
	send(MessagesMessageType.InformMessagesToUser, (Object) msgs);
    }

    private void requestRead(final int idmessage) throws SQLException,
	    IOException {
	Server.db().message()
		.setMessageStatusRead(idmessage, connection.getUserId());
	final boolean has = Server.db().message()
		.getHasUnread(connection.getUserId());
	send(MessagesMessageType.InformRead, idmessage, has);
    }

    private void requestSend(final int[] tos, final String message)
	    throws SQLException {
	final Message[] msg = Server.db().message()
		.createNew(connection.getUserId(), tos, message);
	// notify users
	for (final Message m : msg) {
	    final MessageData md = new MessageData(MessageSubSystem.Messages,
		    MessagesMessageType.InformReceived, m);
	    if (m.isBroadcast()) {
		Server.broadcast(md);
	    } else {
		final Communication[] cs = Server.getClientComm(m.getTo());
		for (final Communication c : cs) {
		    if (c != null) {
			c.send(md);
		    }
		}
	    }
	}
    }
}
