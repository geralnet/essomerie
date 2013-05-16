package net.geral.essomerie.client.communication.controllers;

import java.io.IOException;

import net.geral.essomerie._shared.communication.ConnectionController;
import net.geral.essomerie._shared.communication.ICommunication;
import net.geral.essomerie._shared.communication.MessageData;
import net.geral.essomerie._shared.communication.MessageSubSystem;
import net.geral.essomerie._shared.communication.types.MessagesMessageType;
import net.geral.essomerie._shared.mensagens.Message;
import net.geral.essomerie.client.core.Client;
import net.geral.essomerie.client.core.events.Events;

import org.apache.log4j.Logger;

public class MessagesController extends
    ConnectionController<MessagesMessageType> {
  private static final Logger logger = Logger
                                         .getLogger(MessagesController.class);

  public MessagesController(final ICommunication comm) {
    super(comm, Events.system(), MessageSubSystem.Messages);
  }

  @Override
  protected void process(final MessagesMessageType type, final MessageData md) {
    switch (type) {
      case InformMessagesToUser:
        Client.cache().messages().informMessagesToUser((Message[]) md.get());
        break;
      case InformSent:
        Client.cache().messages().informSent(md.getInt());
        break;
      case InformReceived:
        Client.cache().messages().informReceived((Message) md.get());
        break;
      case InformRead:
        Client.cache().messages().informRead(md.getInt(), md.getBoolean());
        break;
      case InformDeleted:
        Client.cache().messages().informDeleted((int[]) md.get());
        break;
      default:
        logger.warn("Invalid type: " + type.name());
    }
  }

  public void requestDelete(final int[] ids) throws IOException {
    send(MessagesMessageType.RequestDelete, ids);
  }

  public void requestMessagesToUser() throws IOException {
    send(MessagesMessageType.RequestMessagesToUser);
  }

  public void requestRead(final int idmsg) throws IOException {
    send(MessagesMessageType.RequestRead, idmsg);
  }

  public void requestSend(final int[] to, final String msg) throws IOException {
    send(MessagesMessageType.RequestSend, to, msg);
  }
}
