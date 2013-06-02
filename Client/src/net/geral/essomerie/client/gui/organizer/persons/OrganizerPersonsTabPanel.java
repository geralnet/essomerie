package net.geral.essomerie.client.gui.organizer.persons;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.print.PrinterException;
import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreeSelectionModel;

import net.geral.essomerie.client._gui.agenda.clientes.impressao.ClienteImpressao;
import net.geral.essomerie.client._gui.agenda.clientes.impressao.ClienteImpressao.ClienteImpressaoVia;
import net.geral.essomerie.client.core.Client;
import net.geral.essomerie.client.core.events.Events;
import net.geral.essomerie.client.core.events.listeners.CallerIdListener;
import net.geral.essomerie.client.core.events.listeners.CommConfirmationListener;
import net.geral.essomerie.client.core.events.listeners.PersonsListener;
import net.geral.essomerie.client.gui.main.TabPanel;
import net.geral.essomerie.client.gui.organizer.persons.editors.DocumentsPersonEditor;
import net.geral.essomerie.client.gui.organizer.persons.editors.GeneralPersonEditor;
import net.geral.essomerie.client.gui.organizer.persons.editors.SalesPersonEditor;
import net.geral.essomerie.client.gui.organizer.persons.tree.PersonsTreeNode;
import net.geral.essomerie.client.resources.S;
import net.geral.essomerie.shared.person.Addresses;
import net.geral.essomerie.shared.person.Person;
import net.geral.essomerie.shared.person.PersonData;
import net.geral.essomerie.shared.person.PersonDocuments;
import net.geral.essomerie.shared.person.PersonLogDetails;
import net.geral.essomerie.shared.person.PersonSales;
import net.geral.essomerie.shared.person.PersonType;
import net.geral.essomerie.shared.person.Telephone;
import net.geral.essomerie.shared.person.Telephones;
import net.geral.gui.button.ActionButton;
import net.geral.lib.actiondelay.ActionDelay;
import net.geral.lib.actiondelay.ActionDelayListener;
import net.geral.printing.PrintSupport;

import org.apache.log4j.Logger;

public class OrganizerPersonsTabPanel extends TabPanel implements
    TreeSelectionListener, PersonsListener, ActionDelayListener<Person>,
    CommConfirmationListener, ActionListener, CallerIdListener {
  private static final long           serialVersionUID = 1L;
  private static final Logger         logger           = Logger
                                                           .getLogger(OrganizerPersonsTabPanel.class);
  private final PersonsList           list             = new PersonsList();
  private final GeneralPersonEditor   panelGeneral;
  private final DocumentsPersonEditor panelDocuments;
  private final SalesPersonEditor     panelSales;
  private final ActionDelay<Person>   actionDelay      = new ActionDelay<>(
                                                           "PersonsChangeDelay",
                                                           this, this);
  private Person                      showing          = null;
  private boolean                     editing          = false;
  private Person                      lastSaved        = null;
  private long                        savingMessageId  = 0;
  private final ActionButton          btnCancel;
  private final ActionButton          btnSave;
  private final ActionButton          btnDelete;
  private final ActionButton          btnChange;
  private final ActionButton          btnAdd;
  private final ActionButton          btnPrint;
  private final JPanel                panelButtons;
  private final JTabbedPane           tabbedPane;

  public OrganizerPersonsTabPanel() {
    setLayout(new BorderLayout(0, 0));

    final JSplitPane split = new JSplitPane();
    split.setLeftComponent(list);
    list.getTree().getSelectionModel()
        .setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
    list.getTree().addTreeSelectionListener(this);

    add(split);

    final JPanel panelRight = new JPanel();

    tabbedPane = new JTabbedPane(SwingConstants.TOP);
    split.setRightComponent(panelRight);
    panelRight.setLayout(new BorderLayout(0, 0));
    panelRight.add(tabbedPane, BorderLayout.CENTER);

    panelGeneral = new GeneralPersonEditor();
    tabbedPane
        .addTab(S.ORGANIZER_PERSONS_GENERAL.s(), null, panelGeneral, null);

    panelDocuments = new DocumentsPersonEditor();
    tabbedPane.addTab(S.ORGANIZER_PERSONS_DOCUMENTS.s(), null, panelDocuments,
        null);

    panelSales = new SalesPersonEditor();
    tabbedPane.addTab(S.ORGANIZER_PERSONS_SALES.s(), null, panelSales, null);

    final JPanel panelButtonsFlow = new JPanel();
    panelButtonsFlow.setBorder(new MatteBorder(1, 0, 0, 0, new Color(0, 0, 0)));
    final FlowLayout flowLayout = (FlowLayout) panelButtonsFlow.getLayout();
    flowLayout.setAlignment(FlowLayout.TRAILING);
    flowLayout.setVgap(0);
    flowLayout.setHgap(0);
    panelRight.add(panelButtonsFlow, BorderLayout.SOUTH);

    panelButtons = new JPanel();
    panelButtonsFlow.add(panelButtons);
    panelButtons.setLayout(new GridLayout(1, 0, 0, 0));

    btnPrint = new ActionButton(S.BUTTON_PRINT.s(), 'P', "print", this);
    panelButtons.add(btnPrint);

    btnAdd = new ActionButton(S.BUTTON_ADD.s(), 'N', "add", this);
    panelButtons.add(btnAdd);

    btnChange = new ActionButton(S.BUTTON_CHANGE.s(), 'E', "change", this);
    panelButtons.add(btnChange);

    btnDelete = new ActionButton(S.BUTTON_DELETE.s(), 'D', "delete", this);
    panelButtons.add(btnDelete);

    btnSave = new ActionButton(S.BUTTON_SAVE.s(), 'S', "save", this);
    panelButtons.add(btnSave);

    btnCancel = new ActionButton(S.BUTTON_CANCEL.s(), KeyEvent.VK_ESCAPE, 0,
        "cancel", this);
    panelButtons.add(btnCancel);

    split.setDividerLocation(200);

    setPerson(null);
  }

  @Override
  public void actionPerformed(final ActionEvent e) {
    final Object src = e.getSource();
    if (src == btnAdd) {
      add();
    } else if (src == btnChange) {
      change();
    } else if (src == btnDelete) {
      delete();
    } else if (src == btnSave) {
      save();
    } else if (src == btnCancel) {
      cancel();
    } else if (src == btnPrint) {
      print();
    } else {
      logger.warn("Invalid action: " + e);
    }
  }

  private void add() {
    setPerson(new PersonData());
    change();
  }

  @Override
  public void callerIdCallReceived(final String line,
      final Telephone telephone, final PersonData person) {
    list.callReceived(telephone);
  }

  private void cancel() {
    final String msg = S.ORGANIZER_PERSONS_DISCARD_CHANGES.s();
    final int r = JOptionPane.showConfirmDialog(this, msg, S.TITLE_CONFIRM.s(),
        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
    if (r == JOptionPane.YES_OPTION) {
      setPerson(showing.getId() == 0 ? null : showing); // refresh
    }
  }

  private void change() {
    editingStart();
  }

  @Override
  public void commConfirm(final long messageId) {
    if (messageId == savingMessageId) {
      savingMessageId = 0;
      if (lastSaved != null) {
        list.getTree().setSelected(lastSaved);
        lastSaved = null;
      }
    }
  }

  private PersonData createPersonFromEditor() {
    final int idperson = showing.getId();
    final PersonType type = panelGeneral.getPersonType();
    final String name = panelGeneral.getPersonName();
    final String alias = panelGeneral.getPersonAlias();
    final boolean deleted = false;
    final String comments = panelGeneral.getPersonComments();
    final PersonLogDetails log = null;
    final Telephones telephones = panelGeneral.getPersonTelephones(idperson);
    final Addresses addresses = panelGeneral.getPersonAddresses(idperson);
    final PersonDocuments documents = panelDocuments.getDocuments(idperson);
    final PersonSales sales = panelSales.getSales(idperson);
    final PersonData p = new PersonData(idperson, type, name, alias, deleted,
        comments, log, telephones, addresses, documents, sales);
    return p;
  }

  @Override
  public void delayedAction(final Person action) {
    setPerson(action);
  }

  private void delete() {
    final String msg = S.ORGANIZER_PERSONS_DELETE_PERSON.s(showing.getName());
    final int r = JOptionPane.showConfirmDialog(this, msg, S.TITLE_CONFIRM.s(),
        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
    if (r == JOptionPane.NO_OPTION) {
      return;
    }

    try {
      Client.connection().persons().requestDelete(showing.getId());
    } catch (final IOException e) {
      logger.warn(e, e);
    }
  }

  private void editingStart() {
    editing = true;
    setPanelsEditing(true);
    updateButtonsEnableVisible();
  }

  private void editingStop() {
    editing = false;
    setPanelsEditing(false);
    updateButtonsEnableVisible();
  }

  @Override
  public String getTabText() {
    return "Persons";
  }

  @Override
  public void personsCacheReloaded(final boolean fullData) {
    list.refresh();
  }

  @Override
  public void personsDeleted(final int idperson) {
    list.getTree().getModel().removeById(idperson);
  }

  @Override
  public void personsFullDataReceived(final PersonData p) {
    list.getTree().getModel().changePerson(p);
    // line disabled because it will deselect current
    if ((showing != null) && (showing.getId() == p.getId())) {
      setPerson(p);
    }
  }

  @Override
  public void personsSalesChanged(final int idperson) {
    if (showing.getId() == idperson) {
      panelSales.setPerson(showing);
    }
  }

  @Override
  public void personsSaved(final Person p) {
    // remove if exists
    list.getTree().getModel().removeById(p.getId());
    // add
    list.getTree().getModel().addPerson(p);
    // set last saved, if this client requested it wil be selected later
    lastSaved = p;
  }

  private void print() {
    if (!(showing instanceof PersonData)) {
      return;
    }
    try {
      for (final ClienteImpressaoVia v : ClienteImpressaoVia.values()) {
        PrintSupport.print(new ClienteImpressao((PersonData) showing, v));
      }
    } catch (final PrinterException e) {
      logger.warn(e, e);
    }
  }

  private void save() {
    final PersonData p = createPersonFromEditor();
    try {
      savingMessageId = Client.connection().persons().requestSave(p).getId();
    } catch (final IOException e) {
      logger.warn(e, e);
    }
  }

  private void setPanelsEditing(final boolean yn) {
    list.setEnabled(!yn);
    for (final Component c : tabbedPane.getComponents()) {
      if (c instanceof PersonEditorPanel) {
        ((PersonEditorPanel) c).setEditable(yn);
      }
    }
  }

  private void setPerson(final Person p) {
    showing = p;
    editingStop(); // just in case...
    for (final Component c : tabbedPane.getComponents()) {
      if (c instanceof PersonEditorPanel) {
        ((PersonEditorPanel) c).setPerson(p);
      }
    }
  }

  @Override
  public void tabClosed() {
    Events.persons().removeListener(this);
    Events.callerid().removeListener(this);
    Events.comm().removeListener(this);
  }

  @Override
  public boolean tabCloseRequest() {
    return true;
  }

  @Override
  public void tabCreated() {
    Events.persons().addListener(this);
    Events.callerid().addListener(this);
    Events.comm().addListener(this);
    list.refresh();
    // Events.Funcionarios.addListener(this);
    // try {
    // Client.comm().requestListaFuncionarios();
    // }
    // catch (final IOException e) {
    // Log4J.warning(e);
    // }
  }

  private void updateButtonsEnableVisible() {
    final boolean hasSelected = (showing != null);
    panelButtons.removeAll();
    if (editing) {
      panelButtons.add(btnSave);
      panelButtons.add(btnCancel);
      btnSave.setEnabled(true);
      btnCancel.setEnabled(true);
    } else {
      panelButtons.add(btnPrint);
      panelButtons.add(btnAdd);
      panelButtons.add(btnChange);
      panelButtons.add(btnDelete);
      btnAdd.setEnabled(true);
      btnPrint.setEnabled(showing instanceof PersonData);
      btnChange.setEnabled(hasSelected);
      btnDelete.setEnabled(hasSelected);
    }
    panelButtons.invalidate();
  }

  @Override
  public void valueChanged(final TreeSelectionEvent e) {
    final Object node = list.getTree().getLastSelectedPathComponent();
    Person p = null;
    if (node != null) {
      if (node instanceof PersonsTreeNode) {
        p = ((PersonsTreeNode) node).getPerson();
      } else {
        p = null;
      }
    }
    setPerson(null);
    actionDelay.prepare(p);
  }
}
