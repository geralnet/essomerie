package net.geral.essomerie.client.communication.controllers;

import java.io.IOException;

import net.geral.essomerie.client.core.Client;
import net.geral.essomerie.client.core.events.Events;
import net.geral.essomerie.shared.communication.ConnectionController;
import net.geral.essomerie.shared.communication.ICommunication;
import net.geral.essomerie.shared.communication.MessageData;
import net.geral.essomerie.shared.communication.MessageSubSystem;
import net.geral.essomerie.shared.communication.types.SalesMessageType;
import net.geral.essomerie.shared.person.PersonSale;
import net.geral.essomerie.shared.person.PersonSaleExtended;

import org.apache.log4j.Logger;

public class SalesController extends ConnectionController<SalesMessageType> {
  private static final Logger logger = Logger.getLogger(SalesController.class);

  public SalesController(final ICommunication comm) {
    super(comm, Events.system(), MessageSubSystem.Sales);
  }

  @Override
  protected void process(final SalesMessageType type, final MessageData md) {
    switch (type) {
      case InformLatestRegistration:
        Client.cache().sales()
            .informLatest(md.getInt(), (PersonSaleExtended[]) md.get());
        break;
      case InformRegistered:
        Client.cache().sales().informRegistered((PersonSaleExtended) md.get());
        break;
      default:
        logger.warn("Invalid type: " + type.name());
    }
  }

  public void requestLatest(final int entries) throws IOException {
    send(SalesMessageType.RequestLatestRegistration, entries);
  }

  public void requestRegister(final int idperson, final PersonSale sale)
      throws IOException {
    send(SalesMessageType.RequestRegister, idperson, sale);
  }
}
