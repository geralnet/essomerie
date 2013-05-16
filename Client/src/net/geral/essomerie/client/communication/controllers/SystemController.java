package net.geral.essomerie.client.communication.controllers;

import java.io.IOException;

import net.geral.essomerie._shared.BuildInfo;
import net.geral.essomerie._shared.UserPermissions;
import net.geral.essomerie._shared.communication.ConnectionController;
import net.geral.essomerie._shared.communication.ICommunication;
import net.geral.essomerie._shared.communication.MessageData;
import net.geral.essomerie._shared.communication.MessageSubSystem;
import net.geral.essomerie._shared.communication.types.SystemMessageType;
import net.geral.essomerie.client.core.Client;
import net.geral.essomerie.client.core.events.Events;

import org.apache.log4j.Logger;

public class SystemController extends ConnectionController<SystemMessageType> {
  private static final Logger logger = Logger.getLogger(SystemController.class);

  public SystemController(final ICommunication comm) {
    super(comm, Events.system(), MessageSubSystem.System);
  }

  private void errorReceived(final boolean fatal, final String message) {
    // TODO any better way to handle it?
    logger.error("Error (fatal=" + fatal + "): " + message);
  }

  public void informVersion() throws IOException {
    send(SystemMessageType.InformVersion, BuildInfo.CURRENT);
  }

  @Override
  protected void process(final SystemMessageType type, final MessageData md)
      throws IOException {
    switch (type) {
      case Ping:
        send(SystemMessageType.Pong, md.getLong());
        break;
      case Pong:
        final long ms = md.getLong();
        Client.connection().comm().pong(ms);
        Events.system().firePongReceived(System.currentTimeMillis() - ms);
        break;
      case Processed:
        Events.comm().fireConfirm(md.getLong());
        break;
      case InformError:
        errorReceived(md.getBoolean(), md.getString());
        break;
      case InformLoginAccepted:
        Client.cache().users()
            .setLogged(md.getInt(), (UserPermissions) md.get());
        Events.system().fireLoginAccepted();
        break;
      case InformLoginFailed:
        Events.system().fireLoginFailed();
        break;
      case InformVersion:
        Events.system().fireVersionReceived((BuildInfo) md.get());
        break;
      default:
        logger.warn("Invalid type: " + type.name());
    }
  }

  public void requestLogin(final int iduser, final char[] password)
      throws IOException {
    send(SystemMessageType.RequestLogin, iduser, password);
  }

  public void requestPing() throws IOException {
    send(SystemMessageType.Ping, System.currentTimeMillis());
  }
}
