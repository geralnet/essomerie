package net.geral.essomerie.client.communication.controllers;

import java.io.IOException;

import net.geral.essomerie.client.core.events.Events;
import net.geral.essomerie.shared.calendar.CalendarEvent;
import net.geral.essomerie.shared.communication.ConnectionController;
import net.geral.essomerie.shared.communication.ICommunication;
import net.geral.essomerie.shared.communication.MessageData;
import net.geral.essomerie.shared.communication.MessageSubSystem;
import net.geral.essomerie.shared.communication.types.CalendarMessageType;
import net.geral.essomerie.shared.roster.Roster;

import org.apache.log4j.Logger;
import org.joda.time.LocalDate;

public class CalendarController extends
    ConnectionController<CalendarMessageType> {
  private static final Logger logger = Logger
                                         .getLogger(CalendarController.class);

  public CalendarController(final ICommunication comm) {
    super(comm, Events.system(), MessageSubSystem.Calendar);
  }

  @Override
  protected void process(final CalendarMessageType type, final MessageData md) {
    switch (type) {
      case InformEventAddedChanged:
        Events.calendar().fireEventAddedChanged((CalendarEvent) md.get());
        break;
      case InformEventDeleted:
        Events.calendar().fireEventDeleted(md.getInt());
        break;
      case InformEventDetails:
        Events.calendar().fireEventDetailsReceived((CalendarEvent) md.get());
        break;
      case InformEvents:
        Events.calendar().fireEventsReceived((LocalDate) md.get(),
            (CalendarEvent[]) md.get());
        break;
      case InformRoster:
        Events.calendar().fireRosterReceived((Roster) md.get());
        break;
      default:
        logger.warn("Invalid type: " + type.name());
    }
  }

  public void requestEventAddChange(final int idevent, final LocalDate newDate,
      final String newMessage) throws IOException {
    send(CalendarMessageType.RequestEventAddChange, idevent, newDate,
        newMessage);
  }

  public void requestEventDelete(final int idevt) throws IOException {
    send(CalendarMessageType.RequestEventDelete, idevt);
  }

  public void requestEventDetails(final int idevt) throws IOException {
    send(CalendarMessageType.RequestEventDetails, idevt);
  }

  public void requestEvents(final LocalDate date) throws IOException {
    send(CalendarMessageType.RequestEvents, date);
  }

  public void requestRoster(final LocalDate date, final boolean dayShift)
      throws IOException {
    send(CalendarMessageType.RequestRoster, date, dayShift);
  }

  public void requestRosterSave(final Roster roster) throws IOException {
    send(CalendarMessageType.RequestRosterSave, roster);
  }
}
