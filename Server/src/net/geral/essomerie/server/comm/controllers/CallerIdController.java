package net.geral.essomerie.server.comm.controllers;

import java.io.IOException;
import java.sql.SQLException;

import net.geral.essomerie.server.Server;
import net.geral.essomerie.server.comm.Connection;
import net.geral.essomerie.shared.communication.MessageData;
import net.geral.essomerie.shared.communication.MessageSubSystem;
import net.geral.essomerie.shared.communication.types.CallerIdMessageType;
import net.geral.essomerie.shared.person.Person;
import net.geral.essomerie.shared.person.Telephone;

import org.apache.log4j.Logger;

public class CallerIdController {
    private static final Logger logger = Logger
	    .getLogger(CallerIdController.class);

    public static void callReceived(final String line, final String type,
	    final String number) throws IOException, SQLException {
	CallerIdMessageType message;
	if ("IN".equals(type)) {
	    message = CallerIdMessageType.CallReceived;
	} else {
	    logger.warn("Invalid type: " + type);
	    return;
	}
	final Telephone t = Telephone.fromFormatted(number);
	final Person p = Server.db().person().getForTelephone(t);
	final int pid = (p == null) ? 0 : p.getId();
	Server.db().caller().register(line, message, number, pid);
	Server.broadcast(MessageSubSystem.CallerId, message, line, t, p);
    }

    public CallerIdController(final Connection c) {
	// TODO if connection used ...
	// connection = c;
    }

    public void process(final MessageData md) throws IOException, SQLException {
	final CallerIdMessageType type = (CallerIdMessageType) md.getType();
	switch (type) {
	default:
	    logger.warn("Invalid type: " + type.name());
	}
    }
}
