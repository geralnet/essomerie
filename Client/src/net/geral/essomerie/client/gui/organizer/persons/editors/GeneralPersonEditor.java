package net.geral.essomerie.client.gui.organizer.persons.editors;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.border.MatteBorder;

import net.geral.essomerie.client.core.Client;
import net.geral.essomerie.client.gui.organizer.persons.PersonEditorPanel;
import net.geral.essomerie.client.gui.shared.tables.addresses.AddressesPanel;
import net.geral.essomerie.client.gui.shared.tables.telephones.TelephonesPanel;
import net.geral.essomerie.client.resources.S;
import net.geral.essomerie.shared.person.Addresses;
import net.geral.essomerie.shared.person.Person;
import net.geral.essomerie.shared.person.PersonData;
import net.geral.essomerie.shared.person.PersonLogDetails;
import net.geral.essomerie.shared.person.PersonType;
import net.geral.essomerie.shared.person.Telephones;
import net.geral.lib.textfieds.TextLabelField;

import org.apache.log4j.Logger;
import org.joda.time.format.DateTimeFormat;

public class GeneralPersonEditor extends PersonEditorPanel implements
    ItemListener {
  private static final Logger   logger           = Logger
                                                     .getLogger(GeneralPersonEditor.class);
  private static final long     serialVersionUID = 1L;
  private final JLabel          lblID;
  private final JLabel          lblRegistered;
  private final TextLabelField  txtName;
  private final TextLabelField  txtAlias;
  private final JLabel          lblAlias;
  private final JLabel          lblUpdated;
  private final JTextArea       txtComments;
  private final JLabel          lblTUpdated;
  private final ButtonGroup     buttonGroup      = new ButtonGroup();
  private final JRadioButton    rdbtnLegalPerson;
  private final JRadioButton    rdbtnNaturalPerson;
  private final TelephonesPanel panelTelephones;
  private final AddressesPanel  panelAddresses;
  private final JSplitPane      splitPane;

  public GeneralPersonEditor() {
    final GridBagLayout gridBagLayout = new GridBagLayout();
    gridBagLayout.columnWidths = new int[] { 1, 0 };
    gridBagLayout.rowHeights = new int[] { 0, 0, 0, 180, 0, 0 };
    gridBagLayout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
    gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 1.0,
        Double.MIN_VALUE };
    setLayout(gridBagLayout);

    final JPanel panel1 = new JPanel();
    final GridBagConstraints gbc_panel1 = new GridBagConstraints();
    gbc_panel1.insets = new Insets(0, 0, 5, 0);
    gbc_panel1.fill = GridBagConstraints.BOTH;
    gbc_panel1.gridx = 0;
    gbc_panel1.gridy = 0;
    add(panel1, gbc_panel1);
    final GridBagLayout gbl_panel1 = new GridBagLayout();
    gbl_panel1.columnWidths = new int[] { 60, 0, 0, 0 };
    gbl_panel1.rowHeights = new int[] { 0, 0, 0 };
    gbl_panel1.columnWeights = new double[] { 0.0, 1.0, 1.0, Double.MIN_VALUE };
    gbl_panel1.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
    panel1.setLayout(gbl_panel1);

    final JLabel lblTId = new JLabel(S.ORGANIZER_PERSONS_GENERAL_ID.s());
    lblTId.setFont(lblTId.getFont().deriveFont(
        lblTId.getFont().getStyle() | Font.BOLD));
    final GridBagConstraints gbc_lblTId = new GridBagConstraints();
    gbc_lblTId.fill = GridBagConstraints.BOTH;
    gbc_lblTId.insets = new Insets(0, 0, 5, 6);
    gbc_lblTId.gridx = 0;
    gbc_lblTId.gridy = 0;
    panel1.add(lblTId, gbc_lblTId);

    final JLabel lblTRegistered = new JLabel(
        S.ORGANIZER_PERSONS_GENERAL_REGISTERED.s());
    lblTRegistered.setFont(lblTRegistered.getFont().deriveFont(
        lblTRegistered.getFont().getStyle() | Font.BOLD));
    final GridBagConstraints gbc_lblTRegistered = new GridBagConstraints();
    gbc_lblTRegistered.fill = GridBagConstraints.BOTH;
    gbc_lblTRegistered.insets = new Insets(0, 0, 5, 6);
    gbc_lblTRegistered.gridx = 1;
    gbc_lblTRegistered.gridy = 0;
    panel1.add(lblTRegistered, gbc_lblTRegistered);

    lblTUpdated = new JLabel(S.ORGANIZER_PERSONS_GENERAL_UPDATED.s());
    lblTUpdated.setFont(lblTUpdated.getFont().deriveFont(
        lblTUpdated.getFont().getStyle() | Font.BOLD));
    final GridBagConstraints gbc_lblTUpdated = new GridBagConstraints();
    gbc_lblTUpdated.fill = GridBagConstraints.BOTH;
    gbc_lblTUpdated.insets = new Insets(0, 0, 5, 6);
    gbc_lblTUpdated.gridx = 2;
    gbc_lblTUpdated.gridy = 0;
    panel1.add(lblTUpdated, gbc_lblTUpdated);

    lblID = new JLabel("-");
    final GridBagConstraints gbc_lblID = new GridBagConstraints();
    gbc_lblID.fill = GridBagConstraints.BOTH;
    gbc_lblID.insets = new Insets(0, 0, 0, 5);
    gbc_lblID.gridx = 0;
    gbc_lblID.gridy = 1;
    panel1.add(lblID, gbc_lblID);

    lblRegistered = new JLabel("-");
    final GridBagConstraints gbc_lblRegistered = new GridBagConstraints();
    gbc_lblRegistered.fill = GridBagConstraints.BOTH;
    gbc_lblRegistered.insets = new Insets(0, 0, 0, 5);
    gbc_lblRegistered.gridx = 1;
    gbc_lblRegistered.gridy = 1;
    panel1.add(lblRegistered, gbc_lblRegistered);

    lblUpdated = new JLabel("-");
    final GridBagConstraints gbc_lblUpdated = new GridBagConstraints();
    gbc_lblUpdated.fill = GridBagConstraints.BOTH;
    gbc_lblUpdated.gridx = 2;
    gbc_lblUpdated.gridy = 1;
    panel1.add(lblUpdated, gbc_lblUpdated);

    final JPanel panel2 = new JPanel();
    panel2.setBorder(new MatteBorder(1, 0, 1, 0, new Color(0, 0, 0)));
    final GridBagConstraints gbc_panel2 = new GridBagConstraints();
    gbc_panel2.insets = new Insets(0, 0, 5, 0);
    gbc_panel2.fill = GridBagConstraints.BOTH;
    gbc_panel2.gridx = 0;
    gbc_panel2.gridy = 1;
    add(panel2, gbc_panel2);

    rdbtnNaturalPerson = new JRadioButton(S.ORGANIZER_PERSONS_NATURAL.s());
    rdbtnNaturalPerson.addItemListener(this);
    buttonGroup.add(rdbtnNaturalPerson);
    panel2.add(rdbtnNaturalPerson);

    rdbtnLegalPerson = new JRadioButton(S.ORGANIZER_PERSONS_LEGAL.s());
    rdbtnLegalPerson.addItemListener(this);
    buttonGroup.add(rdbtnLegalPerson);
    panel2.add(rdbtnLegalPerson);

    final JPanel panel3 = new JPanel();
    final GridBagConstraints gbc_panel3 = new GridBagConstraints();
    gbc_panel3.insets = new Insets(0, 0, 5, 0);
    gbc_panel3.fill = GridBagConstraints.BOTH;
    gbc_panel3.gridx = 0;
    gbc_panel3.gridy = 2;
    add(panel3, gbc_panel3);
    final GridBagLayout gbl_panel3 = new GridBagLayout();
    gbl_panel3.columnWidths = new int[] { 0, 0, 0 };
    gbl_panel3.rowHeights = new int[] { 0, 0, 0 };
    gbl_panel3.columnWeights = new double[] { 2.0, 1.0, Double.MIN_VALUE };
    gbl_panel3.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
    panel3.setLayout(gbl_panel3);

    final JLabel lblName = new JLabel(S.ORGANIZER_PERSONS_GENERAL_NAME.s());
    lblName.setFont(lblName.getFont().deriveFont(
        lblName.getFont().getStyle() | Font.BOLD));
    final GridBagConstraints gbc_lblName = new GridBagConstraints();
    gbc_lblName.fill = GridBagConstraints.BOTH;
    gbc_lblName.insets = new Insets(0, 0, 2, 6);
    gbc_lblName.gridx = 0;
    gbc_lblName.gridy = 0;
    panel3.add(lblName, gbc_lblName);

    lblAlias = new JLabel(S.ORGANIZER_PERSONS_GENERAL_ALIAS.s());
    lblAlias.setFont(lblAlias.getFont().deriveFont(
        lblAlias.getFont().getStyle() | Font.BOLD));
    final GridBagConstraints gbc_lblAlias = new GridBagConstraints();
    gbc_lblAlias.fill = GridBagConstraints.BOTH;
    gbc_lblAlias.insets = new Insets(0, 0, 2, 0);
    gbc_lblAlias.gridx = 1;
    gbc_lblAlias.gridy = 0;
    panel3.add(lblAlias, gbc_lblAlias);

    txtName = new TextLabelField();
    final GridBagConstraints gbc_txtName = new GridBagConstraints();
    gbc_txtName.insets = new Insets(0, 0, 0, 5);
    gbc_txtName.fill = GridBagConstraints.BOTH;
    gbc_txtName.gridx = 0;
    gbc_txtName.gridy = 1;
    panel3.add(txtName, gbc_txtName);
    txtName.setColumns(10);

    txtAlias = new TextLabelField();
    final GridBagConstraints gbc_txtAlias = new GridBagConstraints();
    gbc_txtAlias.fill = GridBagConstraints.BOTH;
    gbc_txtAlias.gridx = 1;
    gbc_txtAlias.gridy = 1;
    panel3.add(txtAlias, gbc_txtAlias);
    txtAlias.setColumns(10);

    final JPanel panel4 = new JPanel();
    final GridBagConstraints gbc_panel4 = new GridBagConstraints();
    gbc_panel4.insets = new Insets(0, 0, 5, 0);
    gbc_panel4.fill = GridBagConstraints.BOTH;
    gbc_panel4.gridx = 0;
    gbc_panel4.gridy = 3;
    add(panel4, gbc_panel4);
    panel4.setLayout(new BorderLayout(0, 0));

    splitPane = new JSplitPane();
    panel4.add(splitPane);

    panelTelephones = new TelephonesPanel();
    splitPane.setLeftComponent(panelTelephones);

    panelAddresses = new AddressesPanel();
    splitPane.setRightComponent(panelAddresses);
    splitPane.setDividerLocation(300);

    final JPanel panel5 = new JPanel();
    final GridBagConstraints gbc_panel5 = new GridBagConstraints();
    gbc_panel5.fill = GridBagConstraints.BOTH;
    gbc_panel5.gridx = 0;
    gbc_panel5.gridy = 4;
    add(panel5, gbc_panel5);
    panel5.setLayout(new BorderLayout(0, 0));

    final JLabel lblComments = new JLabel(
        S.ORGANIZER_PERSONS_GENERAL_COMMENTS.s());
    lblComments.setFont(lblComments.getFont().deriveFont(
        lblComments.getFont().getStyle() | Font.BOLD));
    panel5.add(lblComments, BorderLayout.NORTH);

    final JScrollPane scrollPane = new JScrollPane();
    panel5.add(scrollPane, BorderLayout.CENTER);

    txtComments = new JTextArea();
    scrollPane.setViewportView(txtComments);
  }

  public Addresses getPersonAddresses(final int idperson) {
    return panelAddresses.getAddresses(idperson);
  }

  public String getPersonAlias() {
    return txtAlias.getText();
  }

  public String getPersonComments() {
    return txtComments.getText();
  }

  public String getPersonName() {
    return txtName.getText();
  }

  public Telephones getPersonTelephones(final int idperson) {
    return panelTelephones.getTelephones(idperson);
  }

  public PersonType getPersonType() {
    if (rdbtnLegalPerson.isSelected()) {
      return PersonType.Legal;
    }
    if (rdbtnNaturalPerson.isSelected()) {
      return PersonType.Natural;
    }
    return PersonType.Unknown;
  }

  @Override
  public void itemStateChanged(final ItemEvent e) {
    if (rdbtnLegalPerson.isSelected()) {
      lblAlias.setText(S.ORGANIZER_PERSONS_GENERAL_ALIAS_LEGAL.s());
    } else if (rdbtnNaturalPerson.isSelected()) {
      lblAlias.setText(S.ORGANIZER_PERSONS_GENERAL_ALIAS_NATURAL.s());
    } else {
      lblAlias.setText(S.ORGANIZER_PERSONS_GENERAL_ALIAS.s());
    }
  }

  @Override
  public void setEditable(final boolean yn) {
    rdbtnLegalPerson.setEnabled(yn);
    rdbtnNaturalPerson.setEnabled(yn);
    rdbtnLegalPerson.setForeground(rdbtnLegalPerson.isSelected() ? Color.BLACK
        : null);
    rdbtnNaturalPerson
        .setForeground(rdbtnNaturalPerson.isSelected() ? Color.BLACK : null);
    txtName.setEditable(yn);
    txtAlias.setEditable(yn);
    panelTelephones.setEditable(yn);
    panelAddresses.setEditable(yn);
    txtComments.setEditable(yn);
  }

  private void setLogDetails(final PersonLogDetails log) {
    if (log == null) {
      return;
    }

    final String createdWhen = log.getCreatedWhen().toString(
        DateTimeFormat.forPattern(S.FORMAT_DATETIME_SIMPLE_NO_SECONDS.s()));
    final String createdBy = Client.cache().users().get(log.getCreatedBy())
        .getUsername().toUpperCase();
    lblRegistered.setText(String.format("%s (%s)", createdWhen, createdBy));

    final String updatedWhen = log.getUpdatedWhen().toString(
        DateTimeFormat.forPattern(S.FORMAT_DATETIME_SIMPLE_NO_SECONDS.s()));
    final String updatedBy = Client.cache().users().get(log.getUpdatedBy())
        .getUsername().toUpperCase();
    lblUpdated.setText(String.format("%s (%s)", updatedWhen, updatedBy));
  }

  @Override
  public void setPerson(Person p) {
    lblID.setText("-");
    lblRegistered.setText("-");
    lblTUpdated.setText(S.ORGANIZER_PERSONS_GENERAL_UPDATED.s());
    lblUpdated.setText("-");
    setType(null);
    txtName.setText("");
    txtAlias.setText("");
    txtComments.setText("");
    panelTelephones.getTable().getModel().setTelephones(null);
    panelAddresses.getTable().getModel().setAddresses(null);
    buttonGroup.clearSelection();

    if (p == null) {
      return;
    }

    // if not new person -- check for complete version from cache
    // this will trigger a request if not found
    if (p.getId() > 0) {
      final Person fromCache = Client.cache().persons().get(p.getId());
      if (fromCache != null) {
        p = fromCache;
      }
    }

    lblTUpdated.setText(p.isDeleted() ? S.ORGANIZER_PERSONS_GENERAL_DELETED.s()
        : S.ORGANIZER_PERSONS_GENERAL_UPDATED.s());

    setType(p.getType());
    lblID.setText(String.valueOf(p.getId()));
    txtName.setText(p.getName());
    txtAlias.setText(p.getAlias());

    if (p instanceof PersonData) {
      final PersonData pd = (PersonData) p;
      setLogDetails(pd.getLogDetails());
      txtComments.setText(pd.getComments());
      panelTelephones.getTable().getModel().setTelephones(pd.getTelephones());
      panelAddresses.getTable().getModel().setAddresses(pd.getAddresses());
    }
  }

  private void setType(PersonType type) {
    if (type == null) {
      type = PersonType.Unknown;
    }
    switch (type) {
      case Natural:
        rdbtnNaturalPerson.setSelected(true);
        break;
      case Legal:
        rdbtnLegalPerson.setSelected(true);
        break;
      case Unknown:
        buttonGroup.clearSelection();
        break;
      default:
        logger.warn("Invalid type: " + type);
        break;
    }
  }
}
