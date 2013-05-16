package net.geral.essomerie.client.core.events.handlers;

import javax.swing.event.EventListenerList;

import net.geral.essomerie.client.core.events.listeners.CallerIdListener;
import net.geral.essomerie.shared.person.PersonData;
import net.geral.essomerie.shared.person.Telephone;
import net.geral.lib.edt.Edt;

import org.apache.log4j.Logger;

public class CallerIdEventHandler extends EventListenerList {
  private static final Logger logger           = Logger
                                                   .getLogger(CallerIdEventHandler.class);
  private static final long   serialVersionUID = 1L;

  public void addListener(final CallerIdListener l) {
    add(CallerIdListener.class, l);
  }

  public void fireCallReceived(final String line, final Telephone telephone,
      final PersonData person) {
    Edt.run(false, new Runnable() {
      @Override
      public void run() {
        logger.debug("Fired: fireCallReceived");
        for (final CallerIdListener l : getListeners(CallerIdListener.class)) {
          l.callerIdCallReceived(line, telephone, person);
        }
      }
    });
  }

  public void removeListener(final CallerIdListener l) {
    remove(CallerIdListener.class, l);
  }
}
