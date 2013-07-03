package net.geral.essomerie.client.gui.organizer.calendar.table;

import java.io.IOException;
import java.util.ArrayList;

import net.geral.essomerie.client.core.Client;
import net.geral.essomerie.client.resources.S;
import net.geral.essomerie.shared.calendar.CalendarEvent;
import net.geral.lib.datepicker.DatePickerPanel;
import net.geral.lib.table.GNTableModel;

import org.apache.log4j.Logger;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;

public class CalendarModel extends GNTableModel<CalendarEvent> {
  private static final Logger   logger           = Logger
                                                     .getLogger(CalendarModel.class);
  private static final long     serialVersionUID = 1L;
  private final DatePickerPanel picker;
  private LocalDate             date             = LocalDate.now();

  public CalendarModel(final DatePickerPanel datePicker) {
    super(true, true, true);
    picker = datePicker;
    date = picker.getDate();
  }

  @Override
  protected void automaticAddReplace(final CalendarEvent oldData,
      final CalendarEvent newData) {
    // do not do it, wait for new info
    return;
  }

  @Override
  protected CalendarEvent changeEntry(final CalendarEvent evt,
      final int columnIndex, final Object aValue) {
    logger.debug("Changed: " + evt + " (C" + columnIndex + ") to "
        + aValue.getClass() + " " + aValue);
    final int id = evt.getId();
    LocalDate toDate = evt.getDate();
    String toMessage = evt.getMessage();
    switch (columnIndex) {
      case 0:
        toDate = LocalDate.parse((String) aValue,
            DateTimeFormat.forPattern(S.FORMAT_DATE_SIMPLE.s()));
        if (evt.getDate().equals(toDate)) {
          return null;
        }
        break;
      case 1:
        toMessage = (String) aValue;
        break;
      default:
        return null;
    }
    try {
      Client.connection().calendar()
          .requestEventAddChange(id, toDate, toMessage);
    } catch (final IOException e) {
      logger.warn(e, e);
    }
    if (columnIndex == 0) {
      picker.setDate(toDate);
    }
    return new CalendarEvent(id, toDate, toMessage, 0, null, null);
  }

  @Override
  public CalendarEvent createNewEntry() {
    return new CalendarEvent(date);
  }

  public LocalDate getDate() {
    return date;
  }

  @Override
  protected Object getValueFor(final CalendarEvent evt, final int columnIndex) {
    switch (columnIndex) {
      case 0:
        return evt.getDate().toString(
            DateTimeFormat.forPattern(S.FORMAT_DATE_SIMPLE.s()));
      case 1:
        return evt.getMessage();
      case 2:
        return Client.cache().users().get(evt.getCreatedBy()).getUsername()
            .toUpperCase();
      default:
        logger.warn("Invalid column: " + columnIndex);
        return "n/a";
    }
  }

  @Override
  public boolean isCellEditable(final int rowIndex, final int columnIndex) {
    // if parent says not editable, its not!
    if (!super.isCellEditable(rowIndex, columnIndex)) {
      return false;
    }
    // extra checks...
    if (columnIndex == 1) {
      return true; // can always change an event
    }
    if (columnIndex != 0) {
      return false; // only event date can be changed
    }
    // can only change the date if not a new event
    return rowIndex < super.getEntriesCount();
  }

  public void removeEvent(final CalendarEvent evt) {
    if (evt == null) {
      return;
    }
    removeEvent(evt.getId());
  }

  public void removeEvent(final int idevent) {
    final ArrayList<CalendarEvent> es = getAll();
    for (int i = 0; i < es.size(); i++) {
      if (es.get(i).getId() == idevent) {
        remove(i);
        return;
      }
    }
    logger.debug("Remove, not found ID#" + idevent);
  }

  @Override
  @Deprecated
  public void setData(final CalendarEvent[] data) {
    throw new IllegalArgumentException("Use 'setEvents' instead.'");
  }

  public void setEvents(final LocalDate newDate, CalendarEvent[] newEvents) {
    if (newEvents == null) {
      newEvents = new CalendarEvent[0];
    }
    date = newDate;
    super.setData(newEvents);
  }
}
