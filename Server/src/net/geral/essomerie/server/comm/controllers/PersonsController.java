package net.geral.essomerie.server.comm.controllers;

import java.io.IOException;
import java.sql.SQLException;

import net.geral.essomerie.server.Server;
import net.geral.essomerie.server.comm.Connection;
import net.geral.essomerie.server.comm.ServerConnectionController;
import net.geral.essomerie.shared.communication.MessageData;
import net.geral.essomerie.shared.communication.MessageSubSystem;
import net.geral.essomerie.shared.communication.types.PersonsMessageType;
import net.geral.essomerie.shared.person.Person;
import net.geral.essomerie.shared.person.PersonData;
import net.geral.essomerie.shared.person.PersonFullData;

import org.apache.log4j.Logger;

public class PersonsController extends
	ServerConnectionController<PersonsMessageType> {
    private static final Logger logger = Logger
	    .getLogger(PersonsController.class);

    public PersonsController(final Connection c) {
	super(c, MessageSubSystem.Persons);
    }

    @Override
    protected void process(final PersonsMessageType type, final MessageData md)
	    throws IOException, SQLException {
	switch (type) {
	case RequestList:
	    requestList();
	    break;
	case RequestPersonData:
	    requestPersonData(md.getInt());
	    break;
	case RequestDelete:
	    requestDelete(md.getInt());
	    break;
	case RequestSave:
	    requestSave((PersonData) md.get());
	    break;
	case RequestFullData:
	    requestFullData();
	    break;
	default:
	    logger.warn("Invalid type: " + type.name());
	}
    }

    private void requestDelete(final int idperson) throws SQLException,
	    IOException {
	final boolean deleted = Server.db().person()
		.delete(connection.getUserId(), idperson);
	if (deleted) {
	    Server.broadcast(MessageSubSystem.Persons,
		    PersonsMessageType.InformDeleted, idperson);
	}
    }

    private void requestFullData() throws SQLException, IOException {
	final PersonFullData pfd = Server.db().person()
		.getFullData(false, false);
	send(PersonsMessageType.InformFullData, (Object) pfd);
    }

    private void requestList() throws SQLException, IOException {
	final Person[] ps = Server.db().person().getAll(false);
	send(PersonsMessageType.InformList, (Object) ps);
    }

    private void requestPersonData(final int idperson) throws SQLException,
	    IOException {
	final PersonData person = Server.db().person().get(idperson);
	if (person == null) {
	    send(PersonsMessageType.InformIdNotFound, idperson);
	} else {
	    send(PersonsMessageType.InformPersonData, person);
	}
    }

    private void requestSave(final PersonData person) throws SQLException,
	    IOException {
	final PersonData newData = Server.db().person()
		.save(connection.getUserId(), person);
	Server.broadcast(MessageSubSystem.Persons,
		PersonsMessageType.InformSaved, newData);
    }
}
