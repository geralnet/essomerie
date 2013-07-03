package net.geral.essomerie.client.communication.controllers;

import java.io.IOException;

import net.geral.essomerie.client.core.Client;
import net.geral.essomerie.client.core.events.Events;
import net.geral.essomerie.shared.communication.ConnectionController;
import net.geral.essomerie.shared.communication.ICommunication;
import net.geral.essomerie.shared.communication.MessageData;
import net.geral.essomerie.shared.communication.MessageSubSystem;
import net.geral.essomerie.shared.communication.types.PersonsMessageType;
import net.geral.essomerie.shared.person.Person;
import net.geral.essomerie.shared.person.PersonData;
import net.geral.essomerie.shared.person.PersonFullData;

import org.apache.log4j.Logger;

public class PersonsController extends ConnectionController<PersonsMessageType> {
  private static final Logger logger = Logger
                                         .getLogger(PersonsController.class);

  public PersonsController(final ICommunication comm) {
    super(comm, Events.system(), MessageSubSystem.Persons);
  }

  @Override
  protected void process(final PersonsMessageType type, final MessageData md) {
    switch (type) {
      case InformList:
        Client.cache().persons().informList((Person[]) md.get());
        break;
      case InformSaved:
        Client.cache().persons().informSaved((Person) md.get());
        break;
      case InformDeleted:
        Client.cache().persons().informDeleted(md.getInt());
        break;
      case InformPersonData:
        Client.cache().persons().informPersonData((PersonData) md.get());
        break;
      case InformFullData:
        Client.cache().persons().informFullData((PersonFullData) md.get());
        break;
      default:
        logger.warn("Invalid type: " + type.name());
    }
  }

  public void requestDelete(final int idperson) throws IOException {
    send(PersonsMessageType.RequestDelete, idperson);
  }

  public void requestDelete(final Person p) throws IOException {
    requestDelete(p.getId());
  }

  public void requestFullData() throws IOException {
    send(PersonsMessageType.RequestFullData);
  }

  public void requestList() throws IOException {
    send(PersonsMessageType.RequestList);
  }

  public void requestPersonData(final int id) throws IOException {
    if (id <= 0) {
      throw new IllegalArgumentException("id must be >0 : " + id);
    }
    send(PersonsMessageType.RequestPersonData, id);
  }

  public MessageData requestSave(final Person p) throws IOException {
    return send(PersonsMessageType.RequestSave, p);
  }
}
