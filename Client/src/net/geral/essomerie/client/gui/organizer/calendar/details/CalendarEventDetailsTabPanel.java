package net.geral.essomerie.client.gui.organizer.calendar.details;

import java.awt.BorderLayout;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import net.geral.essomerie.client.core.Client;
import net.geral.essomerie.client.core.events.Events;
import net.geral.essomerie.client.core.events.listeners.CalendarListener;
import net.geral.essomerie.client.gui.main.TabPanel;
import net.geral.essomerie.client.gui.shared.label.TitleLabel;
import net.geral.essomerie.client.resources.S;
import net.geral.essomerie.shared.calendar.CalendarEvent;
import net.geral.essomerie.shared.roster.Roster;

import org.apache.log4j.Logger;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;

public class CalendarEventDetailsTabPanel extends TabPanel implements
    CalendarListener {
  private static final Logger logger           = Logger
                                                   .getLogger(CalendarEventDetailsTabPanel.class);
  private static final long   serialVersionUID = 1L;

  private static String createDetails(final CalendarEvent e) {
    final StringBuilder s = new StringBuilder();
    createDetails(s, e);
    return s.toString();
  }

  private static void createDetails(final StringBuilder s, final CalendarEvent e) {
    final String createdChanged = e.getPrevious() == null ? S.ORGANIZER_CALENDAR_DETAILS_CREATED
        .s() : S.ORGANIZER_CALENDAR_DETAILS_CHANGED.s();

    s.append(S.ORGANIZER_CALENDAR_DETAILS_ENTRY_LINE_1.s(e.getCreatedWhen()
        .toString(DateTimeFormat.forPattern(S.FORMAT_DATETIME_SIMPLE.s())),
        Client.cache().users().get(e.getCreatedBy()).getName(), Client.cache()
            .users().get(e.getCreatedBy()).getUsername(), createdChanged, e
            .getId()));
    s.append(S.ORGANIZER_CALENDAR_DETAILS_ENTRY_LINE_2.s(e.getDate().toString(
        DateTimeFormat.forPattern(S.FORMAT_DATE_SIMPLE.s()))));
    s.append(S.ORGANIZER_CALENDAR_DETAILS_ENTRY_LINE_3.s(e.getMessage()));

    if (e.getPrevious() != null) {
      createDetails(s, e.getPrevious());
    }
  }

  private final int       idevent;

  private final String    tabTitle;
  private final JTextPane txt;

  public CalendarEventDetailsTabPanel(final int idevent) {
    this.idevent = idevent;
    setLayout(new BorderLayout(0, 0));
    tabTitle = S.ORGANIZER_CALENDAR_DETAILS_TITLE_SHORT.s(idevent);

    final JLabel lblTitle = new TitleLabel(
        S.ORGANIZER_CALENDAR_DETAILS_TITLE_LONG.s(idevent));
    add(lblTitle, BorderLayout.NORTH);

    final JScrollPane scrollPane = new JScrollPane();
    add(scrollPane, BorderLayout.CENTER);

    txt = new JTextPane();
    scrollPane.setViewportView(txt);
    txt.setEditable(false);
    txt.setText(S.ORGANIZER_CALENDAR_DETAILS_LOADING.s(idevent));
  }

  @Override
  public void calendarEventAddedChanged(final CalendarEvent e) {
  }

  @Override
  public void calendarEventDeleted(final int idevent) {
  }

  @Override
  public void calendarEventDetailsReceived(final CalendarEvent e) {
    txt.setText(createDetails(e));
  }

  @Override
  public void calendarEventsReceived(final LocalDate date,
      final CalendarEvent[] events) {
  }

  @Override
  public void calendarRosterReceived(final Roster roster) {
  }

  @Override
  public String getTabText() {
    return tabTitle;
  }

  @Override
  public void tabClosed() {
    Events.calendar().removeListener(this);
  }

  @Override
  public boolean tabCloseRequest() {
    return true;
  }

  @Override
  public void tabCreated() {
    Events.calendar().addListener(this);
    try {
      Client.connection().calendar().requestEventDetails(idevent);
    } catch (final IOException e) {
      txt.setText(e.getMessage());
      logger.warn(e, e);
    }
  }
}
