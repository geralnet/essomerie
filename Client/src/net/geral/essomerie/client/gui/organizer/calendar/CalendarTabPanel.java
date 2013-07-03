package net.geral.essomerie.client.gui.organizer.calendar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.print.PrinterException;
import java.io.IOException;

import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.geral.essomerie.client._printing.CalendarioPrint;
import net.geral.essomerie.client.core.Client;
import net.geral.essomerie.client.core.events.Events;
import net.geral.essomerie.client.core.events.listeners.CalendarListener;
import net.geral.essomerie.client.gui.main.TabPanel;
import net.geral.essomerie.client.gui.organizer.calendar.details.CalendarEventDetailsTabPanel;
import net.geral.essomerie.client.gui.organizer.calendar.roster.RosterPanel;
import net.geral.essomerie.client.gui.organizer.calendar.table.CalendarModel;
import net.geral.essomerie.client.gui.organizer.calendar.table.CalendarTable;
import net.geral.essomerie.client.resources.S;
import net.geral.essomerie.shared.calendar.CalendarEvent;
import net.geral.essomerie.shared.roster.Roster;
import net.geral.gui.button.ActionButton;
import net.geral.lib.datepicker.DatePickerListener;
import net.geral.lib.datepicker.DatePickerPanel;
import net.geral.printing.PrintSupport;

import org.apache.log4j.Logger;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

public class CalendarTabPanel extends TabPanel implements CalendarListener,
    ActionListener, DatePickerListener, ListSelectionListener {
  private static final Logger   logger           = Logger
                                                     .getLogger(CalendarTabPanel.class);
  private static final long     serialVersionUID = 1L;
  private final DatePickerPanel datePicker       = new DatePickerPanel();
  private final ButtonGroup     rosterRadioGroup = new ButtonGroup();
  private final CalendarTable   calendarTable    = new CalendarTable(datePicker);
  private final JRadioButton    rdbtnRosterDay;
  private final JRadioButton    rdbtnRosterNight;
  private final RosterPanel     rosterPanel;
  private final JPanel          panel;
  private final JPanel          panelButtons;
  private final ActionButton    btnDetails;
  private final ActionButton    btnDelete;
  private final ActionButton    btnPrint;

  public CalendarTabPanel() {
    setLayout(new BorderLayout());
    final JPanel top = new JPanel(new BorderLayout());
    top.add(datePicker, BorderLayout.EAST);
    datePicker.addDatePickerListener(this);

    add(top, BorderLayout.NORTH);

    final JPanel rosterMainPanel = new JPanel();
    top.add(rosterMainPanel, BorderLayout.CENTER);
    rosterMainPanel.setLayout(new BorderLayout(0, 0));

    final JPanel escalaTituloPanel = new JPanel();
    escalaTituloPanel.setBackground(Color.BLACK);
    escalaTituloPanel.setForeground(Color.WHITE);
    rosterMainPanel.add(escalaTituloPanel, BorderLayout.NORTH);
    escalaTituloPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 0));

    rdbtnRosterDay = new JRadioButton(S.ORGANIZER_CALENDAR_ROSTER_DAY.s());
    rdbtnRosterDay.addActionListener(this);
    rosterRadioGroup.add(rdbtnRosterDay);
    rdbtnRosterDay.setFont(rdbtnRosterDay.getFont().deriveFont(
        rdbtnRosterDay.getFont().getStyle() | Font.BOLD,
        rdbtnRosterDay.getFont().getSize() + 2f));
    rdbtnRosterDay.setForeground(Color.WHITE);
    rdbtnRosterDay.setBackground(Color.BLACK);
    escalaTituloPanel.add(rdbtnRosterDay);

    rdbtnRosterNight = new JRadioButton(S.ORGANIZER_CALENDAR_ROSTER_NIGHT.s());
    rdbtnRosterNight.addActionListener(this);
    rosterRadioGroup.add(rdbtnRosterNight);
    rdbtnRosterNight.setFont(rdbtnRosterNight.getFont().deriveFont(
        rdbtnRosterNight.getFont().getStyle() | Font.BOLD,
        rdbtnRosterNight.getFont().getSize() + 2f));
    rdbtnRosterNight.setForeground(Color.WHITE);
    rdbtnRosterNight.setBackground(Color.BLACK);
    escalaTituloPanel.add(rdbtnRosterNight);

    rosterPanel = new RosterPanel();
    rosterMainPanel.add(rosterPanel);
    add(calendarTable.getScroll(), BorderLayout.CENTER);

    panel = new JPanel();
    final FlowLayout flowLayout = (FlowLayout) panel.getLayout();
    flowLayout.setVgap(2);
    flowLayout.setHgap(2);
    flowLayout.setAlignment(FlowLayout.RIGHT);
    add(panel, BorderLayout.SOUTH);

    panelButtons = new JPanel();
    panel.add(panelButtons);
    panelButtons.setLayout(new GridLayout(1, 0, 0, 0));

    btnDelete = new ActionButton(S.BUTTON_DELETE.s(), KeyEvent.VK_DELETE, 0,
        "delete");
    panelButtons.add(btnDelete);
    btnDelete.addActionListener(this);

    btnDetails = new ActionButton(S.BUTTON_DETAILS.s(), 'D', "details");
    panelButtons.add(btnDetails);
    btnDetails.addActionListener(this);

    btnPrint = new ActionButton(S.BUTTON_PRINT.s(), 'P', "print");
    panelButtons.add(btnPrint);
    btnPrint.addActionListener(this);
    calendarTable.getSelectionModel().addListSelectionListener(this);

    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        updateButtons();
      }
    });
  }

  @Override
  public void actionPerformed(final ActionEvent e) {
    final Object src = e.getSource();

    if (src == rdbtnRosterDay) {
      loadRoster(true);
    } else if (src == rdbtnRosterNight) {
      loadRoster(false);
    } else if (src == btnDetails) {
      final CalendarEvent ev = calendarTable.getSelected();
      Client.window().openTab(new CalendarEventDetailsTabPanel(ev.getId()));
    } else if (src == btnDelete) {
      delete();
    } else if (src == btnPrint) {
      print();
    }
  }

  @Override
  public void calendarEventAddedChanged(final CalendarEvent ce) {
    // the old event might be the current date, so try removing it
    calendarTable.getModel().removeEvent(ce.getPrevious());

    if (ce.getDate().equals(datePicker.getDate())) {
      // if the current date, add it
      calendarTable.getModel().add(ce);
    }
  }

  @Override
  public void calendarEventDeleted(final int idevent) {
    calendarTable.getModel().removeEvent(idevent);

  }

  @Override
  public void calendarEventDetailsReceived(final CalendarEvent e) {
  }

  @Override
  public void calendarEventsReceived(final LocalDate date,
      final CalendarEvent[] events) {
    if (date.equals(datePicker.getDate())) {
      calendarTable.getModel().setEvents(date, events);
    }
  }

  @Override
  public void calendarRosterReceived(final Roster re) {
    System.out.println(this + " crrecv: " + re.countEntries());
    setRoster(re);
  }

  @Override
  public void datePickerDateChanged(final LocalDate date) {
    try {
      Client.connection().calendar().requestEvents(date);
      requestRoster(date);
    } catch (final IOException e) {
      logger.warn(e, e);
    }
  }

  private void delete() {
    final int i = JOptionPane.showConfirmDialog(this,
        S.ORGANIZER_CALENDAR_DELETE_EVENT, S.TITLE_CONFIRM.s(),
        JOptionPane.YES_NO_OPTION);
    if (i == JOptionPane.YES_OPTION) {
      try {
        Client.connection().calendar()
            .requestEventDelete(calendarTable.getSelected().getId());
      } catch (final IOException e) {
        logger.warn(e, e);
      }
    }
  }

  @Override
  public String getTabText() {
    return S.ORGANIZER_CALENDAR_TITLE.s();
  }

  private boolean isRosterSelectedDay() {
    return rdbtnRosterDay.isSelected();
  }

  private void loadRoster(final boolean dayShift) {
    try {
      Client.connection().calendar()
          .requestRoster(datePicker.getDate(), dayShift);
    } catch (final Exception e) {
      logger.warn(e, e);
    }
  }

  private void print() {
    final CalendarModel m = calendarTable.getModel();
    final CalendarioPrint p = new CalendarioPrint(m.getDate(), m.getAll());
    try {
      PrintSupport.print(p);
    } catch (final PrinterException e) {
      logger.warn(e, e);
    }
  }

  private void requestRoster(final LocalDate date) throws IOException {
    if (rdbtnRosterDay.isSelected()) {
      Client.connection().calendar().requestRoster(date, true);
    } else if (rdbtnRosterNight.isSelected()) {
      Client.connection().calendar().requestRoster(date, false);
    } else {
      throw new IllegalStateException("no shift selected in roster.");
    }
  }

  private void setRoster(final Roster re) {
    if (re.getDate().equals(datePicker.getDate())) {
      if (re.isDayShift() == isRosterSelectedDay()) {
        rosterPanel.set(re);
      }
    }
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

    final boolean day = LocalTime.now().getHourOfDay() < Client.config().TimeRosterShiftChanges;
    if (day) {
      rdbtnRosterDay.setSelected(true);
    } else {
      rdbtnRosterNight.setSelected(true);
    }

    datePickerDateChanged(datePicker.getDate());
  }

  private void updateButtons() {
    final CalendarEvent e = calendarTable.getSelected();
    final boolean s = e != null;
    btnDelete.setEnabled(s);
    btnDetails.setEnabled(s);
    btnPrint.setEnabled(true);// always able to print
  }

  @Override
  public void valueChanged(final ListSelectionEvent e) {
    if (!e.getValueIsAdjusting()) {
      updateButtons();
    }
  }
}
