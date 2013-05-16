package net.geral.essomerie.client.gui.organizer.persons;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import net.geral.essomerie.client.core.Client;
import net.geral.essomerie.client.gui.organizer.persons.tree.PersonsTree;
import net.geral.essomerie.client.resources.S;
import net.geral.essomerie.shared.person.Person;
import net.geral.essomerie.shared.person.Telephone;
import net.geral.lib.actiondelay.ActionDelay;
import net.geral.lib.actiondelay.ActionDelayListener;

public class PersonsList extends JPanel implements DocumentListener,
    ActionDelayListener<PersonsListFilter>, ItemListener {
  private static final long                    serialVersionUID = 1L;
  private final PersonsTree                    personsTree;
  private final JTextField                     txtSearch;
  private final JCheckBox                      checkboxID;
  private final JCheckBox                      checkboxTelephones;
  private final JCheckBox                      checkboxAddresses;
  private final JCheckBox                      checkboxName;
  private final ActionDelay<PersonsListFilter> actionDelay      = new ActionDelay<PersonsListFilter>(
                                                                    "PersonsFilterDelay",
                                                                    this, this);
  private final JCheckBox                      chckbxUseCallerId;

  public PersonsList() {
    setLayout(new BorderLayout(0, 0));

    final JPanel filterPanel = new JPanel();
    add(filterPanel, BorderLayout.NORTH);
    filterPanel.setLayout(new BorderLayout(0, 0));

    final JPanel searchBox = new JPanel();
    filterPanel.add(searchBox, BorderLayout.NORTH);
    searchBox.setLayout(new BorderLayout(0, 0));

    final JLabel lblSearch = new JLabel(S.ORGANIZER_PERSONS_FILTER_SEARCH.s());
    searchBox.add(lblSearch, BorderLayout.NORTH);
    lblSearch.setFont(lblSearch.getFont().deriveFont(
        lblSearch.getFont().getStyle() | Font.BOLD));

    txtSearch = new JTextField();
    txtSearch.getDocument().addDocumentListener(this);
    searchBox.add(txtSearch, BorderLayout.CENTER);

    final JPanel searchBoxOptions = new JPanel();
    searchBox.add(searchBoxOptions, BorderLayout.SOUTH);
    searchBoxOptions.setLayout(new GridLayout(0, 2, 0, 0));

    checkboxID = new JCheckBox(S.ORGANIZER_PERSONS_FILTER_ID.s());
    checkboxID.setSelected(true);
    searchBoxOptions.add(checkboxID);

    checkboxName = new JCheckBox(S.ORGANIZER_PERSONS_FILTER_NAME.s());
    checkboxName.setSelected(true);
    searchBoxOptions.add(checkboxName);

    checkboxTelephones = new JCheckBox(S.ORGANIZER_PERSONS_FILTER_TELEPHONE.s());
    searchBoxOptions.add(checkboxTelephones);

    checkboxAddresses = new JCheckBox(S.ORGANIZER_PERSONS_FILTER_ADDRESS.s());
    searchBoxOptions.add(checkboxAddresses);

    final JScrollPane scrollPane = new JScrollPane();
    scrollPane
        .setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    add(scrollPane);

    personsTree = new PersonsTree();
    scrollPane.setViewportView(personsTree);

    addCheckboxEvents(searchBoxOptions);

    chckbxUseCallerId = new JCheckBox(S.ORGANIZER_PERSONS_USER_CALLERID.s());
    chckbxUseCallerId.addItemListener(this);
    add(chckbxUseCallerId, BorderLayout.SOUTH);
  }

  private void addCheckboxEvents(final JPanel panel) {
    for (final Component c : panel.getComponents()) {
      if (c instanceof JCheckBox) {
        ((JCheckBox) c).addItemListener(this);
      }
    }
  }

  public void callReceived(final Telephone telephone) {
    if (chckbxUseCallerId.isSelected()) {
      txtSearch.setText(telephone.getNumber());
    }
  }

  @Override
  public void changedUpdate(final DocumentEvent e) {
    prepareApplyFilter();
  }

  private PersonsListFilter createFilter() {
    final PersonsListFilter filter = new PersonsListFilter(txtSearch.getText(),
        checkboxID.isSelected(), checkboxName.isSelected(),
        checkboxTelephones.isSelected(), checkboxAddresses.isSelected());
    if (filter.requiresFullData()) {
      Client.cache().persons().fullDataRequired();
    }
    return filter;
  }

  @Override
  public void delayedAction(final PersonsListFilter action) {
    personsTree.applyFilter(action);
  }

  public PersonsTree getTree() {
    return personsTree;
  }

  @Override
  public void insertUpdate(final DocumentEvent e) {
    prepareApplyFilter();
  }

  @Override
  public void itemStateChanged(final ItemEvent e) {
    if (e.getSource() == chckbxUseCallerId) {
      if (chckbxUseCallerId.isSelected()) {
        checkboxTelephones.setSelected(true);
      }
    } else {
      prepareApplyFilter();
    }
  }

  private void prepareApplyFilter() {
    final PersonsListFilter filter = createFilter();
    actionDelay.prepare(filter);
  }

  public void refresh() {
    final Person[] ps = Client.cache().persons().getAll();
    personsTree.getModel().setPersons(ps);
    personsTree.applyFilter();
  }

  @Override
  public void removeUpdate(final DocumentEvent e) {
    prepareApplyFilter();
  }

  @Override
  public void setEnabled(final boolean enabled) {
    personsTree.setEnabled(enabled);
    txtSearch.setEnabled(enabled);
    checkboxID.setEnabled(enabled);
    checkboxTelephones.setEnabled(enabled);
    checkboxAddresses.setEnabled(enabled);
    checkboxName.setEnabled(enabled);
    super.setEnabled(enabled);
  }
}
