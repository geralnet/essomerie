package net.geral.essomerie.server.comm.controllers;

import java.io.IOException;
import java.sql.SQLException;

import net.geral.essomerie._shared.User;
import net.geral.essomerie._shared.communication.MessageData;
import net.geral.essomerie._shared.communication.MessageSubSystem;
import net.geral.essomerie._shared.communication.types.UsersMessageType;

import org.apache.log4j.Logger;

import net.geral.essomerie.server.Server;
import net.geral.essomerie.server.comm.Connection;

// TODO extends ConnectionController and change send methods....
public class UsersController {
	private static final Logger	logger	= Logger.getLogger(UsersController.class);
	private final Connection	connection;

	public UsersController(final Connection c) {
		connection = c;
	}

	public void proccess(final MessageData md) throws IOException, SQLException {
		final UsersMessageType type = (UsersMessageType)md.getType();
		switch (type) {
			case RequestList:
				requestList();
				break;
			default:
				logger.warn("Invalid type: " + type.name());
		}
	}

	public void requestList() throws IOException, SQLException {
		final User[] users = Server.db().users().getList();
		connection.comm().send(MessageSubSystem.Users, UsersMessageType.InformList, (Object)users);
	}
}
