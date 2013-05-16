package net.geral.essomerie.client.communication.controllers;

import java.io.IOException;

import net.geral.essomerie._shared.communication.ConnectionController;
import net.geral.essomerie._shared.communication.ICommunication;
import net.geral.essomerie._shared.communication.MessageData;
import net.geral.essomerie._shared.communication.MessageSubSystem;
import net.geral.essomerie._shared.communication.types.BulletinBoardMessageType;
import net.geral.essomerie.client.core.Client;
import net.geral.essomerie.client.core.events.Events;
import net.geral.essomerie.shared.bulletinboard.BulletinBoardEntry;
import net.geral.essomerie.shared.bulletinboard.BulletinBoardTitle;

import org.apache.log4j.Logger;

public class BulletinBoardController extends
    ConnectionController<BulletinBoardMessageType> {
  private static final Logger logger = Logger
                                         .getLogger(BulletinBoardController.class);

  public BulletinBoardController(final ICommunication comm) {
    super(comm, Events.system(), MessageSubSystem.BulletinBoard);
  }

  @Override
  protected void process(final BulletinBoardMessageType type,
      final MessageData md) {
    switch (type) {
      case InformTitleList:
        Client.cache().bulletinBoard()
            .setTitleList((BulletinBoardTitle[]) md.get());
        break;
      case InformFullContents:
        Client.cache().bulletinBoard()
            .setFullContents((BulletinBoardEntry) md.get());
        break;
      case InformSaveSuccessful:
        Client.cache().bulletinBoard()
            .informSaveSuccessful(md.getInt(), md.getInt());
        break;
      case InformChanged:
        Client.cache().bulletinBoard()
            .changed(md.getInt(), (BulletinBoardTitle) md.get());
        break;
      case InformDeleted:
        Client.cache().bulletinBoard().deleted(md.getInt());
        break;
      default:
        logger.warn("Invalid type: " + type.name());
    }
  }

  public void requestDelete(final int identry) throws IOException {
    send(BulletinBoardMessageType.RequestDelete, identry);
  }

  public void requestFullContents(final int identry) throws IOException {
    send(BulletinBoardMessageType.RequestFullContents, identry);
  }

  public void requestList() throws IOException {
    send(BulletinBoardMessageType.RequestTitleList);
  }

  public void requestSave(final BulletinBoardEntry entry) throws IOException {
    send(BulletinBoardMessageType.RequestSave, entry);
  }
}
