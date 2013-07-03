package net.geral.essomerie.client.core.events.handlers;

import javax.swing.event.EventListenerList;

import net.geral.essomerie.client.core.events.listeners.CalendarListener;
import net.geral.essomerie.shared.calendar.CalendarEvent;
import net.geral.essomerie.shared.roster.Roster;
import net.geral.lib.edt.Edt;

import org.apache.log4j.Logger;
import org.joda.time.LocalDate;

public class CalendarEventHandler extends EventListenerList {
  private static final long   serialVersionUID = 1L;
  private static final Logger logger           = Logger
                                                   .getLogger(CalendarEventHandler.class);

  public void addListener(final CalendarListener l) {
    add(CalendarListener.class, l);
  }

  public void fireEventAddedChanged(final CalendarEvent e) {
    Edt.run(false, new Runnable() {
      @Override
      public void run() {
        logger.debug("Fired: fireEventAddedChanged");
        for (final CalendarListener l : getListeners(CalendarListener.class)) {
          l.calendarEventAddedChanged(e);
        }
      }
    });
  }

  public void fireEventDeleted(final int idevent) {
    Edt.run(false, new Runnable() {
      @Override
      public void run() {
        logger.debug("Fired: fireEventDeleted");
        for (final CalendarListener l : getListeners(CalendarListener.class)) {
          l.calendarEventDeleted(idevent);
        }
      }
    });
  }

  public void fireEventDetailsReceived(final CalendarEvent e) {
    Edt.run(false, new Runnable() {
      @Override
      public void run() {
        logger.debug("Fired: fireEventDetailsReceived");
        for (final CalendarListener l : getListeners(CalendarListener.class)) {
          l.calendarEventDetailsReceived(e);
        }
      }
    });
  }

  public void fireEventsReceived(final LocalDate date,
      final CalendarEvent[] events) {
    Edt.run(false, new Runnable() {
      @Override
      public void run() {
        logger.debug("Fired: fireEventsReceived");
        for (final CalendarListener l : getListeners(CalendarListener.class)) {
          l.calendarEventsReceived(date, events);
        }
      }
    });
  }

  public void fireRosterReceived(final Roster roster) {
    Edt.run(false, new Runnable() {
      @Override
      public void run() {
        logger.debug("Fired: fireRosterReceived");
        for (final CalendarListener l : getListeners(CalendarListener.class)) {
          l.calendarRosterReceived(roster);
        }
      }
    });
  }

  public void removeListener(final CalendarListener l) {
    remove(CalendarListener.class, l);
  }
}
