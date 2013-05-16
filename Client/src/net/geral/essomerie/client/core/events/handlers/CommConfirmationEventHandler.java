package net.geral.essomerie.client.core.events.handlers;

import javax.swing.event.EventListenerList;

import net.geral.essomerie.client.core.events.listeners.CommConfirmationListener;
import net.geral.lib.edt.Edt;

import org.apache.log4j.Logger;

public class CommConfirmationEventHandler extends EventListenerList {
  private static final long   serialVersionUID = 1L;
  private static final Logger logger           = Logger
                                                   .getLogger(CommConfirmationEventHandler.class);

  public void addListener(final CommConfirmationListener l) {
    add(CommConfirmationListener.class, l);
  }

  public void fireConfirm(final long messageId) {
    Edt.run(false, new Runnable() {
      @Override
      public void run() {
        logger.debug("Fired: fireConfirm");
        for (final CommConfirmationListener l : getListeners(CommConfirmationListener.class)) {
          l.commConfirm(messageId);
        }
      }
    });
  }

  public void removeListener(final CommConfirmationListener l) {
    remove(CommConfirmationListener.class, l);
  }
}
