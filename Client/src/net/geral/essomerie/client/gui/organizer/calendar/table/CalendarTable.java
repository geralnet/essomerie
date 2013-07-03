package net.geral.essomerie.client.gui.organizer.calendar.table;

import java.io.IOException;

import net.geral.essomerie.client.core.Client;
import net.geral.essomerie.client.core.configuration.CoreConfiguration;
import net.geral.essomerie.client.resources.S;
import net.geral.essomerie.shared.calendar.CalendarEvent;
import net.geral.lib.datepicker.DatePickerPanel;
import net.geral.lib.table.GNTable;

import org.apache.log4j.Logger;
import org.joda.time.format.DateTimeFormat;

public class CalendarTable extends GNTable<CalendarModel> {
  private static final Logger logger           = Logger
                                                   .getLogger(CalendarTable.class);
  private static final long   serialVersionUID = 1L;

  public CalendarTable(final DatePickerPanel datePicker) {
    super(new CalendarModel(datePicker));
    initialSort(1, false);
  }

  @Override
  protected void createColumns() {
    final CoreConfiguration c = Client.config();
    createColumn(S.ORGANIZER_CALENDAR_HEADER_DATE.s(),
        c.TableColumnWidth_Organizer_Calendar_Date,
        c.TableColumnWidth_Default_Date);
    createColumn(S.ORGANIZER_CALENDAR_HEADER_MESSAGE.s(),
        c.TableColumnWidth_Organizer_Calendar_Message,
        c.TableColumnWidth_Default);
    createColumn(S.ORGANIZER_CALENDAR_HEADER_USERNAME.s(),
        c.TableColumnWidth_Organizer_Calendar_Username,
        c.TableColumnWidth_Default_Username);
  }

  @Override
  protected boolean deleteClicked(final int viewRow, final int tableColumn) {
    try {
      Client.connection().calendar().requestEventDelete(getSelected().getId());
    } catch (final IOException e) {
      logger.warn(e, e);
    }
    return false;
  }

  @Override
  public String getNewEntryText(final int columnIndex) {
    switch (columnIndex) {
      case 0:
        return getModel().getDate().toString(
            DateTimeFormat.forPattern(S.FORMAT_DATE_SIMPLE.s()));
      case 1:
        return S.ORGANIZER_CALENDAR_NEW_EVENT_MESSAGE.s();
      case 2:
        return Client.cache().users().getLogged().getUsername().toUpperCase();
      default:
        return "";
    }
  }

  public CalendarEvent getSelected() {
    final int s = getSelectedRow();
    if (s == -1) {
      return null;
    }
    return getModel().get(convertRowIndexToModel(s));
  }
}
