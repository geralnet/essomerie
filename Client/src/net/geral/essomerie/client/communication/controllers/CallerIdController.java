package net.geral.essomerie.client.communication.controllers;

import net.geral.essomerie.client.core.events.Events;
import net.geral.essomerie.shared.communication.ConnectionController;
import net.geral.essomerie.shared.communication.ICommunication;
import net.geral.essomerie.shared.communication.MessageData;
import net.geral.essomerie.shared.communication.MessageSubSystem;
import net.geral.essomerie.shared.communication.types.CallerIdMessageType;
import net.geral.essomerie.shared.person.PersonData;
import net.geral.essomerie.shared.person.Telephone;

import org.apache.log4j.Logger;

public class CallerIdController extends
    ConnectionController<CallerIdMessageType> {
  private static final Logger logger = Logger
                                         .getLogger(CallerIdController.class);

  public CallerIdController(final ICommunication comm) {
    super(comm, Events.system(), MessageSubSystem.Users);
  }

  @Override
  protected void process(final CallerIdMessageType type, final MessageData md) {
    switch (type) {
      case CallReceived:
        Events.callerid().fireCallReceived(md.getString(),
            (Telephone) md.get(), (PersonData) md.get());
        break;
      default:
        logger.warn("Invalid type: " + type.name());
    }
  }
}
