package net.geral.essomerie.client.core.events.handlers;

import javax.swing.event.EventListenerList;

import net.geral.essomerie.client.core.events.listeners.SalesListener;
import net.geral.essomerie.shared.person.PersonSaleExtended;
import net.geral.lib.edt.Edt;

import org.apache.log4j.Logger;

public class SalesEventHandler extends EventListenerList {
  private static final Logger logger           = Logger
                                                   .getLogger(SalesEventHandler.class);
  private static final long   serialVersionUID = 1L;

  public void addListener(final SalesListener l) {
    add(SalesListener.class, l);
  }

  public void fireCacheReloaded(final int requested, final int received) {
    Edt.run(false, new Runnable() {
      @Override
      public void run() {
        logger.debug("Fired: fireCacheReloaded");
        for (final SalesListener l : getListeners(SalesListener.class)) {
          l.salesCacheReloaded(requested, received);
        }
      }
    });
  }

  public void fireRegistered(final PersonSaleExtended sale) {
    Edt.run(false, new Runnable() {
      @Override
      public void run() {
        logger.debug("Fired: fireRegistered");
        for (final SalesListener l : getListeners(SalesListener.class)) {
          l.salesRegistered(sale);
        }
      }
    });
  }

  public void removeListener(final SalesListener l) {
    remove(SalesListener.class, l);
  }
}
