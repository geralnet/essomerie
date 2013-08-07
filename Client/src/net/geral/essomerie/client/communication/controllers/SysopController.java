package net.geral.essomerie.client.communication.controllers;

import java.io.IOException;
import java.sql.SQLException;

import net.geral.essomerie.client.core.events.Events;
import net.geral.essomerie.shared.communication.ConnectionController;
import net.geral.essomerie.shared.communication.ICommunication;
import net.geral.essomerie.shared.communication.MessageData;
import net.geral.essomerie.shared.communication.MessageSubSystem;
import net.geral.essomerie.shared.communication.types.SysopMessageType;
import net.geral.essomerie.shared.system.DevicesInfo;

import org.apache.log4j.Logger;

public class SysopController extends ConnectionController<SysopMessageType> {
  private final Logger logger = Logger.getLogger(SysopController.class);

  public SysopController(final ICommunication comm) {
    super(comm, Events.system(), MessageSubSystem.Sysop);
  }

  @Override
  protected void process(final SysopMessageType type, final MessageData md)
      throws IOException, SQLException {
    switch (type) {
      case InformDevicesInfo:
        Events.sysop().fireInformedDevicesInfo((DevicesInfo) md.get());
        break;
      default:
        logger.warn("Invalid type: " + type.name());
    }
  }

  public void requestDevicesInfo() throws IOException {
    send(SysopMessageType.RequestDevicesInfo);
  }
}
