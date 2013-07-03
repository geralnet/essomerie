package net.geral.essomerie.client.core.events.handlers;

import javax.swing.event.EventListenerList;

import net.geral.essomerie.client.core.events.listeners.MessagesListener;
import net.geral.essomerie.shared.messages.Message;
import net.geral.lib.edt.Edt;

import org.apache.log4j.Logger;

public class MessagesEventHandler extends EventListenerList {
  private static final long   serialVersionUID = 1L;
  private static final Logger logger           = Logger
                                                   .getLogger(MessagesEventHandler.class);

  public void addListener(final MessagesListener l) {
    add(MessagesListener.class, l);
  }

  public void fireCacheReloaded(final boolean hasUnread) {
    Edt.run(false, new Runnable() {
      @Override
      public void run() {
        logger.debug("Fired: fireCacheReloaded");
        for (final MessagesListener l : getListeners(MessagesListener.class)) {
          l.messageCacheReloaded(hasUnread);
        }
      }
    });
  }

  public void fireDeleted(final int[] ids, final boolean hasMoreUnread) {
    Edt.run(false, new Runnable() {
      @Override
      public void run() {
        logger.debug("Fired: fireDeleted");
        for (final MessagesListener l : getListeners(MessagesListener.class)) {
          l.messageDeleted(ids, hasMoreUnread);
        }
      }
    });
  }

  public void fireRead(final int idmsg, final boolean hasMoreUnread) {
    Edt.run(false, new Runnable() {
      @Override
      public void run() {
        logger.debug("Fired: fireRead");
        for (final MessagesListener l : getListeners(MessagesListener.class)) {
          l.messageRead(idmsg, hasMoreUnread);
        }
      }
    });
  }

  public void fireReceived(final Message m) {
    Edt.run(false, new Runnable() {
      @Override
      public void run() {
        logger.debug("Fired: fireReceived");
        for (final MessagesListener l : getListeners(MessagesListener.class)) {
          l.messageReceived(m);
        }
      }
    });
  }

  public void fireSent(final int idmsg) {
    Edt.run(false, new Runnable() {
      @Override
      public void run() {
        logger.debug("Fired: fireSent");
        for (final MessagesListener l : getListeners(MessagesListener.class)) {
          l.messageSent();
        }
      }
    });
  }

  public void removeListener(final MessagesListener l) {
    remove(MessagesListener.class, l);
  }
}
