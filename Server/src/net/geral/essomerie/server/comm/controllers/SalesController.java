package net.geral.essomerie.server.comm.controllers;

import java.io.IOException;
import java.sql.SQLException;

import net.geral.essomerie._shared.communication.MessageData;
import net.geral.essomerie._shared.communication.MessageSubSystem;
import net.geral.essomerie._shared.communication.types.SalesMessageType;
import net.geral.essomerie.server.Server;
import net.geral.essomerie.server.comm.Connection;
import net.geral.essomerie.server.comm.ServerConnectionController;
import net.geral.essomerie.shared.person.PersonSale;
import net.geral.essomerie.shared.person.PersonSaleExtended;

import org.apache.log4j.Logger;

public class SalesController extends
	ServerConnectionController<SalesMessageType> {
    private static final Logger logger = Logger
	    .getLogger(SalesController.class);

    public SalesController(final Connection c) {
	super(c, MessageSubSystem.Sales);
    }

    @Override
    protected void process(final SalesMessageType type, final MessageData md)
	    throws IOException, SQLException {
	switch (type) {
	case RequestLatestRegistration:
	    requestLatestRegistration(md.getInt());
	    break;
	case RequestRegister:
	    requestRegister(md.getInt(), (PersonSale) md.get());
	    break;
	default:
	    logger.warn("Invalid type: " + type.name());
	}
    }

    private void requestLatestRegistration(final int requestEntries)
	    throws IOException, SQLException {
	final PersonSaleExtended[] sales = Server.db().person()
		.getLatestRegistrationExtendedSales(requestEntries);
	send(SalesMessageType.InformLatestRegistration, requestEntries, sales);
    }

    private void requestRegister(final int idperson, final PersonSale sale)
	    throws SQLException {
	final PersonSaleExtended saved = Server.db().person()
		.registerSale(connection.getUserId(), idperson, sale);
	Server.broadcast(MessageSubSystem.Sales,
		SalesMessageType.InformRegistered, saved);
    }
}
