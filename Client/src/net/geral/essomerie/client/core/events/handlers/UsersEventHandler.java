package net.geral.essomerie.client.core.events.handlers;

import javax.swing.event.EventListenerList;

import net.geral.essomerie._shared.User;
import net.geral.essomerie.client.core.events.listeners.UsersListener;
import net.geral.lib.edt.Edt;

import org.apache.log4j.Logger;

public class UsersEventHandler extends EventListenerList {
  private static final Logger logger           = Logger
                                                   .getLogger(UsersEventHandler.class);
  private static final long   serialVersionUID = 1L;

  public void addListener(final UsersListener l) {
    add(UsersListener.class, l);
  }

  public void fireCacheReloaded() {
    Edt.run(false, new Runnable() {
      @Override
      public void run() {
        logger.debug("Fired: fireCacheReloaded");
        for (final UsersListener l : getListeners(UsersListener.class)) {
          l.usersCacheReloaded();
        }
      }
    });
  }

  public void fireChanged(final User u) {
    Edt.run(false, new Runnable() {
      @Override
      public void run() {
        logger.debug("Fired: fireChanged");
        for (final UsersListener l : getListeners(UsersListener.class)) {
          l.usersChanged(u);
        }
      }
    });
  }

  public void fireCreated(final User u) {
    Edt.run(false, new Runnable() {
      @Override
      public void run() {
        logger.debug("Fired: fireCreated");
        for (final UsersListener l : getListeners(UsersListener.class)) {
          l.usersCreated(u);
        }
      }
    });
  }

  public void fireDeleted(final int iduser) {
    Edt.run(false, new Runnable() {
      @Override
      public void run() {
        logger.debug("Fired: fireDeleted");
        for (final UsersListener l : getListeners(UsersListener.class)) {
          l.usersDeleted(iduser);
        }
      }
    });
  }

  public void removeListener(final UsersListener l) {
    remove(UsersListener.class, l);
  }
}