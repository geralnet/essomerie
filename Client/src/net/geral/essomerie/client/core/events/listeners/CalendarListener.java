package net.geral.essomerie.client.core.events.listeners;

import java.util.EventListener;

import net.geral.essomerie.shared.calendar.CalendarEvent;
import net.geral.essomerie.shared.roster.Roster;

import org.joda.time.LocalDate;

public interface CalendarListener extends EventListener {
    public void calendarEventAddedChanged(CalendarEvent e);

    public void calendarEventDeleted(int idevent);

    public void calendarEventDetailsReceived(CalendarEvent e);

    public void calendarEventsReceived(LocalDate date, CalendarEvent[] events);

    public void calendarRosterReceived(Roster roster);
}
