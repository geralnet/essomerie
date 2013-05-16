package net.geral.essomerie.client.communication.controllers;

import java.io.IOException;

import net.geral.essomerie._shared.User;
import net.geral.essomerie._shared.communication.ConnectionController;
import net.geral.essomerie._shared.communication.ICommunication;
import net.geral.essomerie._shared.communication.MessageData;
import net.geral.essomerie._shared.communication.MessageSubSystem;
import net.geral.essomerie._shared.communication.types.UsersMessageType;
import net.geral.essomerie.client.core.Client;
import net.geral.essomerie.client.core.events.Events;

import org.apache.log4j.Logger;

public class UsersController extends ConnectionController<UsersMessageType> {
  private static final Logger logger = Logger.getLogger(UsersController.class);

  public UsersController(final ICommunication comm) {
    super(comm, Events.system(), MessageSubSystem.Users);
  }

  @Override
  protected void process(final UsersMessageType type, final MessageData md) {
    switch (type) {
      case InformList:
        Client.cache().users().informList((User[]) md.get());
        break;
      case InformCreated:
        Client.cache().users().informCreated((User) md.get());
        break;
      case InformChanged:
        Client.cache().users().informChanged((User) md.get());
        break;
      case InformDeleted:
        Client.cache().users().informDeleted(md.getInt());
        break;
      default:
        logger.warn("Invalid type: " + type.name());
    }
  }

  public void requestChange(final User u) throws IOException {
    send(UsersMessageType.RequestChange, u);
  }

  public void requestCreate(final User u) throws IOException {
    send(UsersMessageType.RequestCreate, u);
  }

  public void requestDelete(final int iduser) throws IOException {
    send(UsersMessageType.RequestDelete, iduser);
  }

  public void requestDelete(final User u) throws IOException {
    requestDelete(u.getId());
  }

  public void requestList() throws IOException {
    send(UsersMessageType.RequestList);
  }
}
