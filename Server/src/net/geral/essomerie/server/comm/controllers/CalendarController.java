package net.geral.essomerie.server.comm.controllers;

import java.io.IOException;
import java.sql.SQLException;

import net.geral.essomerie._shared.calendario.CalendarEvent;
import net.geral.essomerie._shared.communication.ConnectionController;
import net.geral.essomerie._shared.communication.MessageData;
import net.geral.essomerie._shared.communication.MessageSubSystem;
import net.geral.essomerie._shared.communication.types.CalendarMessageType;
import net.geral.essomerie._shared.roster.RosterInfo;
import net.geral.essomerie.server.Server;
import net.geral.essomerie.server.comm.Connection;

import org.apache.log4j.Logger;
import org.joda.time.LocalDate;

public class CalendarController extends
	ConnectionController<CalendarMessageType> {
    private static final Logger logger = Logger
	    .getLogger(CalendarController.class);
    private final Connection connection;

    public CalendarController(final Connection c) {
	super(c, null, MessageSubSystem.Calendar);
	connection = c;
    }

    @Override
    protected void process(final CalendarMessageType type, final MessageData md)
	    throws IOException, SQLException {
	switch (type) {
	case RequestEventAddChange:
	    requestEventAddChange(md.getInt(), (LocalDate) md.get(),
		    md.getString());
	    break;
	case RequestEventDelete:
	    requestEventDelete(md.getInt());
	    break;
	case RequestEventDetails:
	    requestEventDetails(md.getInt());
	    break;
	case RequestEvents:
	    requestEvents((LocalDate) md.get());
	    break;
	case RequestRoster:
	    requestRoster((LocalDate) md.get(), md.getBoolean());
	    break;
	case RequestRosterSave:
	    requestRosterSave((RosterInfo) md.get());
	    break;
	default:
	    logger.warn("Invalid type: " + type.name());
	}
    }

    private void requestEventAddChange(final int id, final LocalDate date,
	    final String message) throws SQLException, IOException {
	final CalendarEvent e = Server.db().calendar()
		.save(id, date, message, connection.getUserId());
	Server.broadcast(MessageSubSystem.Calendar,
		CalendarMessageType.InformEventAddedChanged, e);
    }

    private void requestEventDelete(final int idevent) throws SQLException,
	    IOException {
	Server.db().calendar().delete(idevent, connection.getUserId());
	Server.broadcast(MessageSubSystem.Calendar,
		CalendarMessageType.InformEventDeleted, idevent);
    }

    private void requestEventDetails(final int idevent) throws IOException,
	    SQLException {
	final CalendarEvent e = Server.db().calendar().get(idevent, true);
	send(CalendarMessageType.InformEventDetails, e);
    }

    private void requestEvents(final LocalDate date) throws SQLException,
	    IOException {
	final CalendarEvent[] ces = Server.db().calendar().get(date);
	send(CalendarMessageType.InformEvents, date, ces);
    }

    private void requestRoster(final LocalDate date, final boolean dayShift)
	    throws SQLException, IOException {
	final RosterInfo ri = Server.db().calendar().getRoster(date, dayShift);
	send(CalendarMessageType.InformRoster, ri);
    }

    private void requestRosterSave(final RosterInfo ri) throws SQLException,
	    IOException {
	Server.db().calendar().saveRoster(ri, connection.getUserId());
	Server.broadcast(MessageSubSystem.Calendar,
		CalendarMessageType.InformRoster, ri);
    }
}
