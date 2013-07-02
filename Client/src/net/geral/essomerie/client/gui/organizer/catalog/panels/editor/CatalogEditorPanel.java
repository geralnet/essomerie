package net.geral.essomerie.client.gui.organizer.catalog.panels.editor;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import net.geral.essomerie.client.core.Client;
import net.geral.essomerie.client.gui.organizer.catalog.panels.editor.tables.GroupTitlesTable;
import net.geral.essomerie.client.gui.organizer.catalog.panels.editor.tables.ItemPricesTable;
import net.geral.essomerie.client.gui.organizer.catalog.panels.editor.tables.ItemTitlesTable;
import net.geral.essomerie.client.resources.S;
import net.geral.essomerie.shared.catalog.CatalogGroup;
import net.geral.essomerie.shared.catalog.CatalogGroupItemBase;
import net.geral.essomerie.shared.catalog.CatalogItem;
import net.geral.essomerie.shared.money.Money;

import org.apache.log4j.Logger;

public class CatalogEditorPanel extends JPanel implements TableModelListener {
  private static final long      serialVersionUID     = 1L;
  private static final Logger    logger               = Logger
                                                          .getLogger(CatalogEditorPanel.class);
  private final GroupTitlesTable tableGroupTitles;
  private final ItemTitlesTable  tableItemTitles;
  private final ItemPricesTable  tableItemPrices;
  private final CardLayout       cl_panelEditor;
  private final JPanel           panelEditor;
  private final JButton          btnSave;
  private final JButton          btnAddGroup;
  private final JButton          btnAddItem;
  private final JButton          btnRemove;
  private final JPanel           panelButtonsCard;
  private final CardLayout       cl_panelButtonsCard;
  private boolean                changed              = false;
  private CatalogGroupItemBase   showing              = null;
  private int                    currentIdPublication = 0;

  public CatalogEditorPanel() {
    setLayout(new BorderLayout(0, 0));

    panelEditor = new JPanel();
    cl_panelEditor = new CardLayout(5, 5);
    panelEditor.setLayout(cl_panelEditor);
    add(panelEditor);

    final JPanel panelNoSelection = new JPanel();
    panelEditor.add(panelNoSelection, "noselection");
    panelNoSelection.setLayout(new BorderLayout(0, 0));

    final JLabel lblNoSelection = new JLabel(
        S.ORGANIZER_CATALOG_EDITOR_NOSELECTION.s());
    lblNoSelection.setHorizontalAlignment(SwingConstants.CENTER);
    panelNoSelection.add(lblNoSelection);

    final JPanel panelGroupEditor = new JPanel();
    panelEditor.add(panelGroupEditor, "group");
    panelGroupEditor.setLayout(new BorderLayout(0, 0));

    final JLabel lblGroupDetails = new JLabel(
        S.ORGANIZER_CATALOG_EDITOR_GROUPDETAILS.s());
    panelGroupEditor.add(lblGroupDetails, BorderLayout.NORTH);

    tableGroupTitles = new GroupTitlesTable();
    tableGroupTitles.getModel().addTableModelListener(this);
    panelGroupEditor.add(tableGroupTitles.getScroll(), BorderLayout.CENTER);

    final JPanel panelItemEditor = new JPanel();
    panelEditor.add(panelItemEditor, "item");
    panelItemEditor.setLayout(new GridLayout(0, 1, 0, 5));

    final JPanel panelItemTitleEditor = new JPanel();
    panelItemEditor.add(panelItemTitleEditor);
    panelItemTitleEditor.setLayout(new BorderLayout(0, 0));

    final JLabel lblTitlesDescriptions = new JLabel(
        S.ORGANIZER_CATALOG_EDITOR_TITLESDESCRIPTIONS.s());
    panelItemTitleEditor.add(lblTitlesDescriptions, BorderLayout.NORTH);

    tableItemTitles = new ItemTitlesTable();
    tableItemTitles.getModel().addTableModelListener(this);
    panelItemTitleEditor.add(tableItemTitles.getScroll());

    final JPanel panelItemPriceEditor = new JPanel();
    panelItemEditor.add(panelItemPriceEditor);
    panelItemPriceEditor.setLayout(new BorderLayout(0, 0));

    final JLabel lblPrices = new JLabel(S.ORGANIZER_CATALOG_EDITOR_PRICES.s());
    panelItemPriceEditor.add(lblPrices, BorderLayout.NORTH);

    tableItemPrices = new ItemPricesTable();
    tableItemPrices.getModel().addTableModelListener(this);
    panelItemPriceEditor.add(tableItemPrices.getScroll());

    panelButtonsCard = new JPanel();
    add(panelButtonsCard, BorderLayout.SOUTH);
    cl_panelButtonsCard = new CardLayout(0, 0);
    panelButtonsCard.setLayout(cl_panelButtonsCard);

    final JPanel panelButtonsEmpty = new JPanel();
    panelButtonsCard.add(panelButtonsEmpty, "buttonsEmpty");

    final JPanel panelButtonsNormal = new JPanel();
    panelButtonsCard.add(panelButtonsNormal, "buttonsNormal");
    panelButtonsNormal.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));

    final JPanel panelButtonsNormalRight = new JPanel();
    panelButtonsNormal.add(panelButtonsNormalRight);
    panelButtonsNormalRight.setLayout(new GridLayout(1, 0, 0, 0));

    btnAddItem = new JButton(S.ORGANIZER_CATALOG_EDITOR_ADDITEM.s());
    btnAddItem.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(final ActionEvent e) {
        addItem();
      }
    });
    panelButtonsNormalRight.add(btnAddItem);

    btnAddGroup = new JButton(S.ORGANIZER_CATALOG_EDITOR_ADDGROUP.s());
    btnAddGroup.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(final ActionEvent e) {
        addGroup();
      }
    });
    panelButtonsNormalRight.add(btnAddGroup);

    btnRemove = new JButton(S.BUTTON_REMOVE.s());
    btnRemove.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(final ActionEvent e) {
        remove();
      }
    });
    panelButtonsNormalRight.add(btnRemove);

    final JPanel panelButtonsEditing = new JPanel();
    final FlowLayout flowLayout = (FlowLayout) panelButtonsEditing.getLayout();
    flowLayout.setAlignment(FlowLayout.RIGHT);
    flowLayout.setVgap(0);
    flowLayout.setHgap(0);
    panelButtonsCard.add(panelButtonsEditing, "buttonsEditing");

    final JPanel panelButtonsEditingRight = new JPanel();
    panelButtonsEditing.add(panelButtonsEditingRight);
    panelButtonsEditingRight.setLayout(new GridLayout(1, 0, 0, 0));

    btnSave = new JButton(S.BUTTON_SAVE.s());
    btnSave.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(final ActionEvent e) {
        save();
      }
    });
    panelButtonsEditingRight.add(btnSave);

    final JButton btnCancel = new JButton(S.BUTTON_CANCEL.s());
    btnCancel.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(final ActionEvent e) {
        cancel();
      }
    });
    panelButtonsEditingRight.add(btnCancel);

    showCard("noselection");
  }

  private void addGroup() {
    int parent = 0;
    if ((showing != null) && showing.isGroup()) {
      parent = ((CatalogGroup) showing).getId();
    }
    try {
      Client.connection().catalog()
          .requestCreateGroup(parent, currentIdPublication);
    } catch (final IOException e) {
      logger.warn(e, e);
    }
  }

  private void addItem() {
    if ((showing != null) && showing.isGroup()) {
      final int idgroup = ((CatalogGroup) showing).getId();
      try {
        Client.connection().catalog().requestCreateItem(idgroup);
      } catch (final IOException e) {
        logger.warn(e, e);
      }
    } else {
      logger.warn("Cannot create item, no group selected.");
    }
  }

  private void cancel() {
    changed = false; // do not confirm
    if (showing == null) {
      clear();
    } else if (showing.isGroup()) {
      setGroup((CatalogGroup) showing);
    } else if (showing.isItem()) {
      setItem((CatalogItem) showing);
    } else {
      logger.warn("Cannot cancel, 'showing': not null, group nor item: "
          + showing);
    }
  }

  private void checkChanged() {
    if (changed) {
      final int res = JOptionPane.showConfirmDialog(this,
          S.ORGANIZER_CATALOG_CHANGED, S.TITLE_SAVE.s(),
          JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
      if (res == JOptionPane.YES_OPTION) {
        save();
      }
    }
  }

  public void clear() {
    checkChanged();
    setChanged(false);
    showing = null;
    showCard("noselection");
    cl_panelButtonsCard.show(panelButtonsCard, "buttonsEmpty");
  }

  private boolean confirmRemove(final boolean group) {
    final S message = group ? S.ORGANIZER_CATALOG_CONFIRM_REMOVE_GROUP
        : S.ORGANIZER_CATALOG_CONFIRM_REMOVE_ITEM;
    final String title = S.TITLE_CONFIRM.s();
    final int optionType = JOptionPane.YES_NO_OPTION;
    final int messageType = group ? JOptionPane.WARNING_MESSAGE
        : JOptionPane.QUESTION_MESSAGE;
    final int i = JOptionPane.showConfirmDialog(this, message, title,
        optionType, messageType);
    return (i == JOptionPane.YES_OPTION);
  }

  private void remove() {
    try {
      if (showing == null) {
        logger.warn("Cannot remove, 'showing' is null.");
      } else if (showing.isGroup()) {
        if (confirmRemove(true)) {
          Client.connection().catalog()
              .requestRemoveGroup((CatalogGroup) showing);
        }
      } else if (showing.isItem()) {
        if (confirmRemove(false)) {
          Client.connection().catalog()
              .requestRemoveItem((CatalogItem) showing);
        }
      } else {
        logger.warn("Cannot remove, 'showing': not null, group nor item: "
            + showing);
      }
    } catch (final IOException e) {
      logger.warn(e, e);
    }
  }

  public void rootSelected() {
    showCard("noselection");
    showing = null;
    setChanged(false);
    btnAddGroup.setEnabled(true);
    btnAddItem.setEnabled(true);
    btnRemove.setEnabled(false);
  }

  private void save() {
    if (changed) {
      if (showing == null) {
        logger.warn("Cannot save, showing=null.");
      } else if (showing.isGroup()) {
        saveGroup((CatalogGroup) showing);
      } else if (showing.isItem()) {
        saveItem((CatalogItem) showing);
      } else {
        logger.warn("Cannot save, 'showing': not null, group nor item: "
            + showing);
      }
    } else {
      logger.warn("No changes to save.");
    }
    // reset to previous
    // will change when server informs new data
    cancel();
  }

  private void saveGroup(final CatalogGroup group) {
    final ArrayList<String> languages = new ArrayList<>();
    final ArrayList<String> titles = new ArrayList<>();
    final ArrayList<String> subtitles = new ArrayList<>();
    for (final String[] s : tableGroupTitles.getModel().getAll()) {
      languages.add(s[0]);
      titles.add(s[1]);
      subtitles.add(s[2]);
    }
    final CatalogGroup save = group.withTitles(languages, titles, subtitles);
    try {
      Client.connection().catalog().requestSaveGroup(save);
    } catch (final IOException e) {
      logger.warn(e, e);
    }
  }

  private void saveItem(final CatalogItem item) {
    final ArrayList<String> languages = new ArrayList<>();
    final ArrayList<String> titles = new ArrayList<>();
    final ArrayList<String> descriptions = new ArrayList<>();
    final ArrayList<String> priceCodes = new ArrayList<>();
    final ArrayList<Money> priceValues = new ArrayList<>();
    for (final String[] s : tableItemTitles.getModel().getAll()) {
      languages.add(s[0]);
      titles.add(s[1]);
      descriptions.add(s[2]);
    }
    for (final Object[] s : tableItemPrices.getModel().getAll()) {
      priceCodes.add((String) s[0]);
      priceValues.add((Money) s[1]);
    }
    final CatalogItem save = item.withTitlesPrices(languages, titles,
        descriptions, priceCodes, priceValues);
    try {
      Client.connection().catalog().requestSaveItem(save);
    } catch (final IOException e) {
      logger.warn(e, e);
    }
  }

  private void setChanged(final boolean yn) {
    changed = yn;
    cl_panelButtonsCard.show(panelButtonsCard, changed ? "buttonsEditing"
        : "buttonsNormal");
  }

  public void setCurrentIdPublication(final int id) {
    currentIdPublication = id;
  }

  public void setGroup(final CatalogGroup group) {
    if (currentIdPublication != group.getIdPublication()) {
      throw new InvalidParameterException("Wrong publication, received: "
          + showing.getIdPublication() + ", expected: " + currentIdPublication);
    }
    checkChanged();
    showing = group;
    tableGroupTitles.getModel().setGroup(group);
    showCard("group");
    btnAddGroup.setEnabled(true);
    btnAddItem.setEnabled(true);
    btnRemove.setEnabled(true);
    setChanged(false);
  }

  public void setItem(final CatalogItem item) {
    if (currentIdPublication != item.getIdPublication()) {
      throw new InvalidParameterException("Wrong publication, received: "
          + showing.getIdPublication() + ", expected: " + currentIdPublication);
    }
    checkChanged();
    showing = item;
    showCard("item");
    tableItemTitles.getModel().setItem(item);
    tableItemPrices.getModel().setItem(item);
    btnAddGroup.setEnabled(false);
    btnAddItem.setEnabled(false);
    btnRemove.setEnabled(true);
    setChanged(false);
  }

  private void showCard(final String name) {
    if ("group".equals(name) || "item".equals(name)
        || "noselection".equals(name)) {
      cl_panelEditor.show(panelEditor, name);
    } else {
      logger.warn("No card named: " + name);
    }
  }

  @Override
  public void tableChanged(final TableModelEvent e) {
    setChanged(true);
  }
}
