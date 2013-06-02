package net.geral.essomerie.client.core.events.handlers;

import javax.swing.event.EventListenerList;

import net.geral.essomerie.client.core.events.listeners.PersonsListener;
import net.geral.essomerie.shared.person.Person;
import net.geral.essomerie.shared.person.PersonData;
import net.geral.lib.edt.Edt;

import org.apache.log4j.Logger;

public class PersonsEventHandler extends EventListenerList {
  private static final long   serialVersionUID = 1L;
  private static final Logger logger           = Logger
                                                   .getLogger(PersonsEventHandler.class);

  public void addListener(final PersonsListener l) {
    add(PersonsListener.class, l);
  }

  public void fireCacheReloaded(final boolean fullData) {
    Edt.run(false, new Runnable() {
      @Override
      public void run() {
        logger.debug("Fired: fireCacheReloaded");
        for (final PersonsListener l : getListeners(PersonsListener.class)) {
          l.personsCacheReloaded(fullData);
        }
      }
    });
  }

  public void fireDeleted(final int idperson) {
    Edt.run(false, new Runnable() {
      @Override
      public void run() {
        logger.debug("Fired: fireDeleted");
        for (final PersonsListener l : getListeners(PersonsListener.class)) {
          l.personsDeleted(idperson);
        }
      }
    });
  }

  public void firePersonDataInformed(final PersonData p) {
    Edt.run(false, new Runnable() {
      @Override
      public void run() {
        logger.debug("Fired: firePersonDataInformed");
        for (final PersonsListener l : getListeners(PersonsListener.class)) {
          l.personsFullDataReceived(p);
        }
      }
    });
  }

  public void fireSalesChanged(final int idperson) {
    Edt.run(false, new Runnable() {
      @Override
      public void run() {
        logger.debug("Fired: fireSalesChanged");
        for (final PersonsListener l : getListeners(PersonsListener.class)) {
          l.personsSalesChanged(idperson);
        }
      }
    });
  }

  public void fireSaved(final Person p) {
    Edt.run(false, new Runnable() {
      @Override
      public void run() {
        logger.debug("Fired: fireSaved");
        for (final PersonsListener l : getListeners(PersonsListener.class)) {
          l.personsSaved(p);
        }
      }
    });
  }

  public void removeListener(final PersonsListener l) {
    remove(PersonsListener.class, l);
  }
}
