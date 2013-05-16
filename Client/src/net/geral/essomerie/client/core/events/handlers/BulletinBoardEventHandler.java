package net.geral.essomerie.client.core.events.handlers;

import javax.swing.event.EventListenerList;

import net.geral.essomerie.client.core.events.listeners.BulletionBoardListener;
import net.geral.essomerie.shared.bulletinboard.BulletinBoardEntry;
import net.geral.essomerie.shared.bulletinboard.BulletinBoardTitle;
import net.geral.lib.edt.Edt;

import org.apache.log4j.Logger;

public class BulletinBoardEventHandler extends EventListenerList {
  private static final long   serialVersionUID = 1L;
  private static final Logger logger           = Logger
                                                   .getLogger(BulletinBoardEventHandler.class);

  public void addListener(final BulletionBoardListener l) {
    add(BulletionBoardListener.class, l);
  }

  public void fireAdded(final BulletinBoardTitle title) {
    Edt.run(false, new Runnable() {
      @Override
      public void run() {
        logger.debug("Fired: fireAdded");
        for (final BulletionBoardListener l : getListeners(BulletionBoardListener.class)) {
          l.bulletinBoardEntryAdded(title);
        }
      }
    });
  }

  public void fireChanged(final int from, final BulletinBoardTitle to) {
    Edt.run(false, new Runnable() {
      @Override
      public void run() {
        logger.debug("Fired: fireChanged");
        for (final BulletionBoardListener l : getListeners(BulletionBoardListener.class)) {
          l.bulletinBoardEntryChanged(from, to);
        }
      }
    });
  }

  public void fireContentsReceived(final BulletinBoardEntry entry) {
    Edt.run(false, new Runnable() {
      @Override
      public void run() {
        logger.debug("Fired: fireContentsReceived");
        for (final BulletionBoardListener l : getListeners(BulletionBoardListener.class)) {
          l.bulletinBoardEntryReceived(entry);
        }
      }
    });
  }

  public void fireDeleted(final int identry) {
    Edt.run(false, new Runnable() {
      @Override
      public void run() {
        logger.debug("Fired: fireDeleted");
        for (final BulletionBoardListener l : getListeners(BulletionBoardListener.class)) {
          l.bulletinBoardEntryDeleted(identry);
        }
      }
    });
  }

  public void fireSaveSuccessful(final int oldId, final int newId) {
    Edt.run(false, new Runnable() {
      @Override
      public void run() {
        logger.debug("Fired: fireSaveSuccessful");
        for (final BulletionBoardListener l : getListeners(BulletionBoardListener.class)) {
          l.bulletinBoardSaveSuccessful(oldId, newId);
        }
      }
    });
  }

  public void fireTitleListReceived(final BulletinBoardTitle[] newTitles) {
    Edt.run(false, new Runnable() {
      @Override
      public void run() {
        logger.debug("Fired: fireTitleListReceived");
        for (final BulletionBoardListener l : getListeners(BulletionBoardListener.class)) {
          l.bulletinBoardTitleListReceived(newTitles);
        }
      }
    });
  }

  public void removeListener(final BulletionBoardListener l) {
    remove(BulletionBoardListener.class, l);
  }
}
