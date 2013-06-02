package net.geral.essomerie.client.gui.tools.salesregister;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.IOException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.geral.essomerie._shared.Dinheiro;
import net.geral.essomerie.client._gui.shared.textfield.DinheiroTextField;
import net.geral.essomerie.client.core.Client;
import net.geral.essomerie.client.core.events.Events;
import net.geral.essomerie.client.core.events.listeners.PersonsListener;
import net.geral.essomerie.client.core.events.listeners.SalesListener;
import net.geral.essomerie.client.gui.main.TabPanel;
import net.geral.essomerie.client.gui.shared.tables.addresses.AddressesTable;
import net.geral.essomerie.client.gui.tools.salesregister.log.LogTable;
import net.geral.essomerie.client.resources.S;
import net.geral.essomerie.shared.person.Address;
import net.geral.essomerie.shared.person.Addresses;
import net.geral.essomerie.shared.person.Person;
import net.geral.essomerie.shared.person.PersonData;
import net.geral.essomerie.shared.person.PersonSale;
import net.geral.essomerie.shared.person.PersonSaleExtended;
import net.geral.jodatime.GNJoda;
import net.geral.lib.actiondelay.ActionDelay;
import net.geral.lib.actiondelay.ActionDelayListener;
import net.geral.lib.datepicker.DatePickerListener;
import net.geral.lib.datepicker.DatePickerPanel;
import net.geral.lib.strings.GNStrings;
import net.geral.lib.textfieds.ErrorFieldSetter;

import org.apache.log4j.Logger;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;

public class ToolsSalesRegister extends TabPanel implements PersonsListener,
    ActionDelayListener<Integer>, SalesListener {
  private static final long          serialVersionUID = 1L;
  private static final Logger        logger           = Logger
                                                          .getLogger(ToolsSalesRegister.class);

  private final LogTable             logTable;
  private final JTextField           txtCustomTime;
  private final JTextField           txtId;
  private final JTextField           txtDateTime;
  private final DinheiroTextField    txtPrice;
  private final JTextField           txtComments;
  private final JTextField           txtPerson;
  private final AddressesTable       addressTable;
  private final DatePickerPanel      datePicker;
  private final ErrorFieldSetter     efs              = new ErrorFieldSetter();

  private final ActionDelay<Integer> actionDelay      = new ActionDelay<>(
                                                          "ToolsSalesRegisterIdDelay",
                                                          this, this);
  private final ButtonGroup          buttonGroup      = new ButtonGroup();
  private JRadioButton               rdbtnDay;
  private JRadioButton               rdbtnNight;
  private JRadioButton               rdbtnCustom;

  // FIXME dinheiro text field should not accept zero (become red)

  public ToolsSalesRegister() {
    setLayout(new BorderLayout(0, 0));

    final JSplitPane splitPane = new JSplitPane();
    splitPane.setResizeWeight(0.5);
    splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
    add(splitPane, BorderLayout.CENTER);

    final JPanel splitTop = new JPanel();
    splitPane.setLeftComponent(splitTop);
    splitTop.setLayout(new BorderLayout(0, 0));

    final JLabel lblLog = new JLabel(S.TOOLS_SALESREGISTER_LOG.s());
    splitTop.add(lblLog, BorderLayout.NORTH);

    logTable = new LogTable();
    splitTop.add(logTable.getScroll(), BorderLayout.CENTER);

    final JPanel splitBottom = new JPanel();
    splitPane.setRightComponent(splitBottom);
    splitBottom.setLayout(new BorderLayout(0, 0));

    final JPanel bottomCenter = new JPanel();
    splitBottom.add(bottomCenter, BorderLayout.CENTER);
    final GridBagLayout gbl_bottomCenter = new GridBagLayout();
    gbl_bottomCenter.columnWidths = new int[] { 0, 0 };
    gbl_bottomCenter.rowHeights = new int[] { 0, 0, 0, 0, 0 };
    gbl_bottomCenter.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
    gbl_bottomCenter.rowWeights = new double[] { 0.0, 0.0, 0.0, 1.0,
        Double.MIN_VALUE };
    bottomCenter.setLayout(gbl_bottomCenter);

    final JPanel bottomCenterL1 = new JPanel();
    final GridBagConstraints gbc_bottomCenterL1 = new GridBagConstraints();
    gbc_bottomCenterL1.fill = GridBagConstraints.BOTH;
    gbc_bottomCenterL1.insets = new Insets(0, 0, 5, 0);
    gbc_bottomCenterL1.gridx = 0;
    gbc_bottomCenterL1.gridy = 0;
    bottomCenter.add(bottomCenterL1, gbc_bottomCenterL1);
    final GridBagLayout gbl_bottomCenterL1 = new GridBagLayout();
    gbl_bottomCenterL1.columnWidths = new int[] { 0, 65, 100, 0, 0 };
    gbl_bottomCenterL1.rowHeights = new int[] { 0, 0, 0 };
    gbl_bottomCenterL1.columnWeights = new double[] { 0.0, 0.0, 0.0, 1.0,
        Double.MIN_VALUE };
    gbl_bottomCenterL1.rowWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
    bottomCenterL1.setLayout(gbl_bottomCenterL1);

    final JLabel lblId = new JLabel(S.TOOLS_SALESREGISTER_ID.s());
    final GridBagConstraints gbc_lblId = new GridBagConstraints();
    gbc_lblId.fill = GridBagConstraints.HORIZONTAL;
    gbc_lblId.insets = new Insets(0, 0, 5, 5);
    gbc_lblId.gridx = 0;
    gbc_lblId.gridy = 0;
    bottomCenterL1.add(lblId, gbc_lblId);

    final JLabel lblPrice = new JLabel(S.TOOLS_SALESREGISTER_PRICE.s());
    final GridBagConstraints gbc_lblPrice = new GridBagConstraints();
    gbc_lblPrice.fill = GridBagConstraints.HORIZONTAL;
    gbc_lblPrice.insets = new Insets(0, 0, 5, 5);
    gbc_lblPrice.gridx = 1;
    gbc_lblPrice.gridy = 0;
    bottomCenterL1.add(lblPrice, gbc_lblPrice);

    final JLabel lblDateTime = new JLabel(S.TOOLS_SALESREGISTER_DATETIME.s());
    final GridBagConstraints gbc_lblDateTime = new GridBagConstraints();
    gbc_lblDateTime.fill = GridBagConstraints.HORIZONTAL;
    gbc_lblDateTime.insets = new Insets(0, 0, 5, 5);
    gbc_lblDateTime.gridx = 2;
    gbc_lblDateTime.gridy = 0;
    bottomCenterL1.add(lblDateTime, gbc_lblDateTime);

    txtId = new JTextField();
    txtId.setColumns(7);
    txtId.setHorizontalAlignment(SwingConstants.TRAILING);
    txtId.getDocument().addDocumentListener(new DocumentListener() {
      @Override
      public void changedUpdate(final DocumentEvent e) {
        changedId();
      }

      @Override
      public void insertUpdate(final DocumentEvent e) {
        changedId();
      }

      @Override
      public void removeUpdate(final DocumentEvent e) {
        changedId();
      }
    });
    final GridBagConstraints gbc_txtId = new GridBagConstraints();
    gbc_txtId.fill = GridBagConstraints.HORIZONTAL;
    gbc_txtId.insets = new Insets(0, 0, 0, 5);
    gbc_txtId.gridx = 0;
    gbc_txtId.gridy = 1;
    bottomCenterL1.add(txtId, gbc_txtId);

    txtPrice = new DinheiroTextField(false, false);
    txtPrice.setColumns(10);
    txtPrice.setHorizontalAlignment(SwingConstants.TRAILING);
    final GridBagConstraints gbc_txtPrice = new GridBagConstraints();
    gbc_txtPrice.fill = GridBagConstraints.HORIZONTAL;
    gbc_txtPrice.insets = new Insets(0, 0, 0, 5);
    gbc_txtPrice.gridx = 1;
    gbc_txtPrice.gridy = 1;
    bottomCenterL1.add(txtPrice, gbc_txtPrice);

    txtDateTime = new JTextField();
    txtDateTime.setColumns(10);
    final GridBagConstraints gbc_txtDateTime = new GridBagConstraints();
    gbc_txtDateTime.fill = GridBagConstraints.HORIZONTAL;
    gbc_txtDateTime.insets = new Insets(0, 0, 0, 5);
    gbc_txtDateTime.gridx = 2;
    gbc_txtDateTime.gridy = 1;
    bottomCenterL1.add(txtDateTime, gbc_txtDateTime);
    txtDateTime.getDocument().addDocumentListener(new DocumentListener() {
      @Override
      public void changedUpdate(final DocumentEvent e) {
        changedDateTime();
      }

      @Override
      public void insertUpdate(final DocumentEvent e) {
        changedDateTime();
      }

      @Override
      public void removeUpdate(final DocumentEvent e) {
        changedDateTime();
      }
    });
    txtDateTime.addFocusListener(new FocusListener() {
      @Override
      public void focusGained(final FocusEvent e) {
      }

      @Override
      public void focusLost(final FocusEvent e) {
        final LocalDateTime dt = getFieldDateTime();
        if (dt == null) {
          return;
        }
        final String current = txtDateTime.getText();
        final String updated = dt.toString(DateTimeFormat
            .forPattern(S.FORMAT_DATETIME_SIMPLE_NO_SECONDS.s()));
        if (updated.equals(current)) {
          return;
        }
        txtDateTime.setText(updated);
      }
    });

    final JPanel bottomCenterL2 = new JPanel();
    final GridBagConstraints gbc_bottomCenterL2 = new GridBagConstraints();
    gbc_bottomCenterL2.insets = new Insets(0, 0, 5, 0);
    gbc_bottomCenterL2.fill = GridBagConstraints.BOTH;
    gbc_bottomCenterL2.gridx = 0;
    gbc_bottomCenterL2.gridy = 1;
    bottomCenter.add(bottomCenterL2, gbc_bottomCenterL2);
    bottomCenterL2.setLayout(new BorderLayout(0, 0));

    final JLabel lblComments = new JLabel(S.TOOLS_SALESREGISTER_COMMENTS.s());
    bottomCenterL2.add(lblComments, BorderLayout.NORTH);

    txtComments = new JTextField();
    bottomCenterL2.add(txtComments, BorderLayout.SOUTH);
    txtComments.getDocument().addDocumentListener(new DocumentListener() {
      @Override
      public void changedUpdate(final DocumentEvent e) {
        changedComments();
      }

      @Override
      public void insertUpdate(final DocumentEvent e) {
        changedComments();
      }

      @Override
      public void removeUpdate(final DocumentEvent e) {
        changedComments();
      }
    });

    final JPanel bottomCenterL3 = new JPanel();
    final GridBagConstraints gbc_bottomCenterL3 = new GridBagConstraints();
    gbc_bottomCenterL3.insets = new Insets(0, 0, 5, 0);
    gbc_bottomCenterL3.fill = GridBagConstraints.BOTH;
    gbc_bottomCenterL3.gridx = 0;
    gbc_bottomCenterL3.gridy = 2;
    bottomCenter.add(bottomCenterL3, gbc_bottomCenterL3);
    bottomCenterL3.setLayout(new BorderLayout(0, 0));

    final JLabel lblPerson = new JLabel(S.TOOLS_SALESREGISTER_PERSON.s());
    bottomCenterL3.add(lblPerson, BorderLayout.NORTH);

    txtPerson = new JTextField();
    txtPerson.setEditable(false);
    txtPerson.setEnabled(false);
    bottomCenterL3.add(txtPerson, BorderLayout.SOUTH);

    final JPanel bottomCenterL4 = new JPanel();
    final GridBagConstraints gbc_bottomCenterL4 = new GridBagConstraints();
    gbc_bottomCenterL4.fill = GridBagConstraints.BOTH;
    gbc_bottomCenterL4.gridx = 0;
    gbc_bottomCenterL4.gridy = 3;
    bottomCenter.add(bottomCenterL4, gbc_bottomCenterL4);
    bottomCenterL4.setLayout(new BorderLayout(0, 0));

    final JLabel lblAddress = new JLabel(S.TOOLS_SALESREGISTER_ADDRESS.s());
    bottomCenterL4.add(lblAddress, BorderLayout.NORTH);

    addressTable = new AddressesTable(false);
    bottomCenterL4.add(addressTable.getScroll(), BorderLayout.CENTER);
    addressTable.getSelectionModel().addListSelectionListener(
        new ListSelectionListener() {
          @Override
          public void valueChanged(final ListSelectionEvent e) {
            changedAddress();
          }
        });

    final JPanel bottomRight = new JPanel();
    splitBottom.add(bottomRight, BorderLayout.EAST);
    bottomRight.setLayout(new BorderLayout(0, 0));

    final JPanel bottomRightTop = new JPanel();
    bottomRight.add(bottomRightTop, BorderLayout.NORTH);
    bottomRightTop.setLayout(new BorderLayout(0, 0));

    final JPanel calendarPanel = new JPanel();
    bottomRightTop.add(calendarPanel, BorderLayout.NORTH);
    calendarPanel.setLayout(new BorderLayout(0, 0));

    datePicker = new DatePickerPanel();
    datePicker.addDatePickerListener(new DatePickerListener() {
      @Override
      public void datePickerDateChanged(final LocalDate newDate) {
        changedDateXorTime(false, true);
      }
    });
    calendarPanel.add(datePicker, BorderLayout.NORTH);

    final JPanel timePanel = new JPanel();
    calendarPanel.add(timePanel, BorderLayout.SOUTH);
    timePanel.setLayout(new GridLayout(0, 1, 0, 0));

    final ActionListener rdAction = new ActionListener() {
      @Override
      public void actionPerformed(final ActionEvent e) {
        changedDateXorTime(false, false);
      }
    };

    rdbtnDay = new JRadioButton(S.TOOLS_SALESREGISTER_TIME_DAY.s());
    rdbtnDay.addActionListener(rdAction);
    buttonGroup.add(rdbtnDay);
    timePanel.add(rdbtnDay);

    rdbtnNight = new JRadioButton(S.TOOLS_SALESREGISTER_TIME_NIGHT.s());
    rdbtnNight.addActionListener(rdAction);
    buttonGroup.add(rdbtnNight);
    timePanel.add(rdbtnNight);

    final JPanel panelBottomRightTopCustom = new JPanel();
    timePanel.add(panelBottomRightTopCustom);
    panelBottomRightTopCustom.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

    rdbtnCustom = new JRadioButton(S.TOOLS_SALESREGISTER_TIME_CUSTOM.s(": "));
    rdbtnCustom.addActionListener(rdAction);
    buttonGroup.add(rdbtnCustom);
    panelBottomRightTopCustom.add(rdbtnCustom);

    txtCustomTime = new JTextField();
    txtCustomTime.setText(Client.config().ToolsSalesRegisterDayTime);
    panelBottomRightTopCustom.add(txtCustomTime);
    txtCustomTime.setColumns(10);
    txtCustomTime.setText(Client.config().ToolsSalesRegisterDefaultTime);
    txtCustomTime.getDocument().addDocumentListener(new DocumentListener() {
      @Override
      public void changedUpdate(final DocumentEvent e) {
        changedDateXorTime(true, false);
      }

      @Override
      public void insertUpdate(final DocumentEvent e) {
        changedDateXorTime(true, false);
      }

      @Override
      public void removeUpdate(final DocumentEvent e) {
        changedDateXorTime(true, false);
      }
    });

    final JPanel panelRightBottom = new JPanel();
    bottomRight.add(panelRightBottom, BorderLayout.SOUTH);
    panelRightBottom.setLayout(new BorderLayout(0, 0));

    final JPanel panelButtons = new JPanel();
    panelRightBottom.add(panelButtons, BorderLayout.EAST);
    panelButtons.setLayout(new GridLayout(1, 0, 0, 0));

    final JButton btnSave = new JButton("Save");
    btnSave.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(final ActionEvent e) {
        save();
      }
    });
    panelButtons.add(btnSave);

    final JButton btnClear = new JButton("Clear");
    btnClear.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(final ActionEvent e) {
        clear();
      }
    });
    panelButtons.add(btnClear);

    changedId();
    changedComments();
    changedDateXorTime(true, false);
  }

  private void changedAddress() {
    if (!Client.config().ToolsSalesAutoAddressComment) {
      return;
    }

    final Address a = addressTable.getSelected();
    if (a == null) {
      txtComments.setText("");
    } else {
      txtComments.setText(S.TOOLS_SALESREGISTER_AUTOCOMMENT.s(a.getAddress()));
    }
  }

  private void changedComments() {
    if (Client.config().ToolsSalesCommentsRequired) {
      efs.set(txtComments, GNStrings.trim(txtComments.getText()).length() > 0);
    }
  }

  private void changedDateTime() {
    final LocalDateTime dt = getFieldDateTime();
    efs.set(txtDateTime, dt != null);
    if (dt == null) {
      return;
    }
    final LocalDate d = dt.toLocalDate();
    if (!d.equals(datePicker.getDate())) {
      datePicker.setDate(d);
    }
    final LocalTime t = dt.toLocalTime();
    if (!t.equals(getRadioButtonTime())) {
      setRadioButtonTime(t);
    }
  }

  private void changedDateXorTime(final boolean selectCustom,
      final boolean forceDateTimeFieldChange) {
    if (selectCustom) {
      rdbtnCustom.setSelected(true);
    }
    final LocalDate date = datePicker.getDate();
    final LocalTime time = getRadioButtonTime();
    if ((date == null) || (time == null)) {
      return;
    }
    final LocalDateTime dt = date.toLocalDateTime(time);
    final String sdt = dt.toString(DateTimeFormat
        .forPattern(S.FORMAT_DATETIME_SIMPLE_NO_SECONDS.s()));

    if (forceDateTimeFieldChange || (!txtDateTime.hasFocus())) {
      txtDateTime.setText(sdt);
    }
  }

  private void changedId() {
    logger.debug("Changed ID: " + txtId.getText());
    final Integer id = Person.getIdOrNull(txtId.getText());
    efs.error(txtId); // will change latter if found
    actionDelay.prepare(id);
  }

  private void clear() {
    txtId.setText("");
    txtPrice.setValue(null);
    txtComments.setText("");
    txtPerson.setText("");
    addressTable.getModel().setAddresses(null);
    txtId.requestFocusInWindow();
  }

  private PersonSale createSale() {
    return new PersonSale(0, getFieldDateTime(), txtPrice.getValue(false),
        txtComments.getText());
  }

  @Override
  public void delayedAction(final Integer action) {
    if (action == null) {
      return;
    }
    final int id = action.intValue();
    setPerson(Client.cache().persons().get(id));
  }

  private LocalDateTime getFieldDateTime() {
    // TODO create in GeralNET Library field DateTimeTextField
    final String txt = GNStrings.trim(txtDateTime.getText());
    if (txt.length() == 0) {
      return null;
    }
    // try parsing datetime
    LocalDateTime dt = GNJoda.parseLocalDateTime(txt,
        S.FORMAT_DATETIME_SIMPLE_NO_SECONDS.s());
    if (dt == null) {
      LocalDate d = GNJoda.parseLocalDate(txt, S.FORMAT_DATE_SIMPLE.s());
      LocalTime t = GNJoda.parseLocalTime(txt, S.FORMAT_TIME_NO_SECONDS.s());
      if ((d == null) && (t == null)) {
        return null;
      }
      if (d == null) {
        d = datePicker.getDate();
      }
      if (t == null) {
        t = getRadioButtonTime();
      }
      if ((d == null) || (t == null)) {
        return null;
      }
      dt = d.toLocalDateTime(t);
    }
    // FIXME configure year ranges, not hardcoded
    // year Before Christ not valid
    if (dt.getYear() < 0) {
      return null;
    }
    // year 00~30 to 2000~2030
    if (dt.getYear() <= 30) {
      dt = dt.withYear(dt.getYear() + 2000);
    }
    // year 31-99 to 1931~1999
    if (dt.getYear() < 100) {
      dt = dt.withYear(dt.getYear() + 1900);
    }
    // year 100-999 invalid
    if (dt.getYear() < 1000) {
      return null;
    }

    return dt;
  }

  private LocalTime getRadioButtonTime() {
    String time;
    if (rdbtnDay.isSelected()) {
      time = Client.config().ToolsSalesRegisterDayTime;
    } else if (rdbtnNight.isSelected()) {
      time = Client.config().ToolsSalesRegisterNightTime;
    } else {
      time = txtCustomTime.getText();
    }
    final LocalTime lt = GNJoda.parseLocalTime(time,
        S.FORMAT_TIME_NO_SECONDS.s());
    efs.set(txtCustomTime, lt != null);
    return lt;
  }

  @Override
  public String getTabText() {
    return S.TOOLS_SALESREGISTER.s();
  }

  @Override
  public void personsCacheReloaded(final boolean fullData) {
    // force reload
    delayedAction(Person.getIdOrNull(txtId.getText()));
    logTable.getModel().refreshNames();
  }

  @Override
  public void personsDeleted(final int idperson) {
    updatePersonIfId(idperson, null);
    logTable.getModel().refreshNames();
  }

  @Override
  public void personsFullDataReceived(final PersonData p) {
    updatePersonIfId(p.getId(), p);
    logTable.getModel().refreshNames();
  }

  @Override
  public void personsSalesChanged(final int idperson) {
  }

  @Override
  public void personsSaved(final Person p) {
    updatePersonIfId(p.getId(), p);
    logTable.getModel().refreshNames();
  }

  @Override
  public void salesCacheReloaded(final int requested, final int received) {
    logTable.getModel().setData(Client.cache().sales().getAll());

  }

  @Override
  public void salesRegistered(final PersonSaleExtended sale) {
    logTable.addAndSelect(sale);
  }

  private void save() {
    if (!validFields()) {
      JOptionPane.showMessageDialog(this, S.ERROR_INVALID_FIELD,
          S.TITLE_ERROR.s(), JOptionPane.ERROR_MESSAGE);
      return;
    }

    final Integer idperson = Person.getIdOrNull(txtId.getText());
    if (idperson == null) {
      logger.warn("Should never happen.", new NullPointerException("idperson"));
    } else {
      try {
        Client.connection().sales()
            .requestRegister(idperson.intValue(), createSale());
        clear();
      } catch (final IOException e) {
        logger.warn(e, e);
      }
    }
  }

  private void setPerson(final Person p) {
    txtPerson.setText((p == null) ? "" : p.getNameAlias());
    final Addresses a = (p instanceof PersonData) ? ((PersonData) p)
        .getAddresses() : null;
    addressTable.getModel().setAddresses(a);
    if (p != null) {
      efs.valid(txtId);
    }
  }

  private void setRadioButtonTime(final LocalTime t) {
    // set text/selected only when needed
    // to not trigger events
    final String st = t.toString(DateTimeFormat
        .forPattern(S.FORMAT_TIME_NO_SECONDS.s()));
    if (st.equals(Client.config().ToolsSalesRegisterDayTime)) {
      if (!rdbtnDay.isSelected()) {
        rdbtnDay.setSelected(true);
      }
    } else if (st.equals(Client.config().ToolsSalesRegisterNightTime)) {
      if (!rdbtnNight.isSelected()) {
        rdbtnNight.setSelected(true);
      }
    } else {
      if (!rdbtnCustom.isSelected()) {
        rdbtnCustom.setSelected(true);
      }
      if (!st.equals(txtCustomTime.getText())) {
        if (!txtCustomTime.hasFocus()) {
          txtCustomTime.setText(st);
        }
      }
    }
  }

  @Override
  public void tabClosed() {
    Events.persons().removeListener(this);
    Events.sales().removeListener(this);
  }

  @Override
  public boolean tabCloseRequest() {
    return true;
  }

  @Override
  public void tabCreated() {
    Client.cache().persons().preload();
    Client.cache().sales().preload();
    Events.persons().addListener(this);
    Events.sales().addListener(this);
  }

  private void updatePersonIfId(final int idperson, final Person p) {
    final Integer id = Person.getIdOrNull(txtId.getText());
    if (id == null) {
      return;
    }
    if (id.intValue() != idperson) {
      return;
    }
    setPerson(p);
  }

  private boolean validFields() {
    // person
    final Integer pid = Person.getIdOrNull(txtId.getText());
    if ((pid == null) || (Client.cache().persons().get(pid.intValue()) == null)) {
      return false;
    }
    // price
    final Dinheiro d = txtPrice.getValue(true);
    if ((d == null) || d.isNonPositive()) {
      return false;
    }
    // date time
    final LocalDateTime field = getFieldDateTime();
    final LocalDate picker = datePicker.getDate();
    final LocalTime radio = getRadioButtonTime();
    if ((field == null) || (picker == null) || (radio == null)) {
      return false;
    }
    // dates match (should always match if not null)
    if (!field.equals(picker.toLocalDateTime(radio))) {
      logger.warn("Different dates to save: F=" + field + " P=" + picker
          + " R=" + radio);
      return false;
    }
    // comments
    if (Client.config().ToolsSalesCommentsRequired
        && (GNStrings.trim(txtComments.getText()).length() == 0)) {
      return false;
    }
    // all ok
    return true;
  }
}
