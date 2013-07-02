package net.geral.essomerie.client.gui.organizer.catalog.panels.tree;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.event.EventListenerList;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import net.geral.essomerie.client.core.Client;
import net.geral.essomerie.client.core.events.Events;
import net.geral.essomerie.client.core.events.listeners.CatalogListener;
import net.geral.essomerie.client.resources.S;
import net.geral.essomerie.shared.catalog.CatalogGroup;
import net.geral.essomerie.shared.catalog.CatalogItem;
import net.geral.essomerie.shared.catalog.CatalogPublication;
import net.geral.lib.edt.Edt;

public class CatalogTreePanel extends JPanel implements CatalogListener,
    ActionListener, ItemListener, TreeSelectionListener {
  private static final long                                   serialVersionUID = 1L;
  private static final Comparator<? super CatalogPublication> publicationsComparator;

  private final CatalogTree                                   tree;
  private final JComboBox<CatalogPublicationLabel>            cbPublication;
  private final ButtonGroup                                   buttonGroup      = new ButtonGroup();
  private final JRadioButton                                  rdbtnLatest;
  private final JRadioButton                                  rdbtnSpecify;
  private final EventListenerList                             listeners        = new EventListenerList();
  static {
    publicationsComparator = new Comparator<CatalogPublication>() {
      @Override
      public int compare(final CatalogPublication o1,
          final CatalogPublication o2) {
        if ((o1 == null) && (o2 == null)) {
          return 0;
        }
        if (o1 == null) {
          return -1;
        }
        if (o2 == null) {
          return 1;
        }
        // reverse date sort
        return o2.getWhen().compareTo(o1.getWhen());
      }
    };
  }

  public CatalogTreePanel() {
    setLayout(new BorderLayout(0, 0));

    final JScrollPane scrollPane = new JScrollPane();
    add(scrollPane, BorderLayout.CENTER);

    tree = new CatalogTree();
    scrollPane.setViewportView(tree);
    tree.addTreeSelectionListener(this);

    final JPanel panelPublication = new JPanel();
    scrollPane.setColumnHeaderView(panelPublication);
    panelPublication.setLayout(new BorderLayout(10, 0));

    rdbtnLatest = new JRadioButton(S.ORGANIZER_CATALOG_LATEST.s());
    rdbtnLatest.addActionListener(this);
    rdbtnLatest.setSelected(true);
    buttonGroup.add(rdbtnLatest);
    panelPublication.add(rdbtnLatest, BorderLayout.WEST);

    final JPanel panelPublicationSpecify = new JPanel();
    panelPublication.add(panelPublicationSpecify, BorderLayout.CENTER);
    panelPublicationSpecify.setLayout(new BorderLayout(5, 0));

    rdbtnSpecify = new JRadioButton(S.ORGANIZER_CATALOG_SPECIFY.s());
    rdbtnSpecify.addActionListener(this);
    buttonGroup.add(rdbtnSpecify);
    panelPublicationSpecify.add(rdbtnSpecify, BorderLayout.WEST);

    cbPublication = new JComboBox<>();
    cbPublication.addItemListener(this);
    cbPublication.setEnabled(false);
    panelPublicationSpecify.add(cbPublication);
  }

  @Override
  public void actionPerformed(final ActionEvent e) {
    final boolean latest = rdbtnLatest.isSelected();
    if (latest) {
      selectLatestPublication();
    }
    cbPublication.setEnabled(!latest);
  }

  public void addCatalogTreePanelListener(final CatalogTreePanelListener l) {
    listeners.add(CatalogTreePanelListener.class, l);
  }

  @Override
  public void catalogCreateGroupSuccessful(final int idgroup) {
    tree.selectGroup(idgroup);
  }

  @Override
  public void catalogCreateItemSuccessful(final int iditem) {
    tree.selectItem(iditem);
  }

  @Override
  public void catalogGroupAdded(final CatalogGroup group) {
    tree.getModel().addGroup(group);
  }

  @Override
  public void catalogGroupSaved(final int oldId, final CatalogGroup newGroup) {
    if (tree.getModel().changeNode(oldId, newGroup)) {
      // check selection
      final TreePath path = tree.getSelectionPath();
      if (path != null) {
        final TreeNode node = getItemOrGroup(path);
        if (node instanceof CatalogGroupTreeNode) {
          final CatalogGroup g = ((CatalogGroupTreeNode) node).getGroup();
          if (g.getId() == newGroup.getId()) {
            fireGroupItemChanged(false, g, null);
          }
        }
      }
    } else {
      // not found! reload whole tree
      reloadPublication();
    }
  }

  @Override
  public void catalogItemAdded(final CatalogItem item) {
    tree.getModel().addItem(item);
  }

  @Override
  public void catalogItemSaved(final int oldId, final CatalogItem newItem) {
    if (tree.getModel().changeNode(oldId, newItem)) {
      // check selection
      final TreePath path = tree.getSelectionPath();
      if (path != null) {
        final TreeNode node = getItemOrGroup(path);
        if (node instanceof CatalogItemTreeNode) {
          final CatalogItem it = ((CatalogItemTreeNode) node).getItem();
          if (it.getId() == newItem.getId()) {
            fireGroupItemChanged(false, null, it);
          }
        }
      }
    } else {
      // not found! reload whole tree
      reloadPublication();
    }
  }

  @Override
  public void catalogLatestPublishedIdChanged(final int oldId, final int newId) {
    if (rdbtnLatest.isSelected()) {
      selectLatestPublication();
    }
  }

  @Override
  public void catalogPublicationListReceived() {
    loadPublications();
  }

  @Override
  public void catalogPublicationReceived(final boolean latest,
      final int idpublication) {
    final CatalogPublication p = getSelectedPublication();
    final int id = (p == null) ? 0 : p.getId();
    if (id == idpublication) {
      tree.getModel().load(p);
    }
  }

  @Override
  public void catalogPublished(final CatalogPublication publication) {
    loadPublications();
  }

  @Override
  public void catalogPublishSuccessful(final int idpublication) {
    // nothing to do, will use catalogLatestPublishedIdChanged
  }

  @Override
  public void catalogRemovedGroups(final int idpublication,
      final ArrayList<Integer> ids) {
    final CatalogPublication p = getSelectedPublication();
    final int id = (p == null) ? 0 : p.getId();
    if (id == idpublication) {
      tree.getModel().removeGroups(ids);
    }
  }

  @Override
  public void catalogRemovedItems(final int idpublication,
      final ArrayList<Integer> ids) {
    final CatalogPublication p = getSelectedPublication();
    final int id = (p == null) ? 0 : p.getId();
    if (id == idpublication) {
      tree.getModel().removeItems(ids);
    }
  }

  private void fireGroupItemChanged(final boolean rootSelected,
      final CatalogGroup group, final CatalogItem item) {
    final boolean hasGroup = (group != null);
    final boolean hasItem = (item != null);
    if ((rootSelected && hasGroup) || (rootSelected && hasItem)
        || (hasGroup && hasItem)) {
      throw new InvalidParameterException("Only one can be true: root="
          + rootSelected + ",hasGroup=" + hasGroup + ",hasItem=" + hasItem);
    }

    for (final CatalogTreePanelListener l : listeners
        .getListeners(CatalogTreePanelListener.class)) {
      l.catalogTreePanelGroupItemChanged(rootSelected, group, item);
    }
  }

  private void firePublicationChanged(final int idpublication) {
    for (final CatalogTreePanelListener l : listeners
        .getListeners(CatalogTreePanelListener.class)) {
      l.catalogTreePanelPublicationChanged(idpublication);
    }
  }

  private TreeNode getItemOrGroup(final TreePath path) {
    if (path == null) {
      return null;
    }
    final Object node = path.getLastPathComponent();
    if (node instanceof CatalogGroupTreeNode) {
      return (CatalogGroupTreeNode) node;
    }
    if (node instanceof CatalogItemTreeNode) {
      return (CatalogItemTreeNode) node;
    }
    if (node instanceof CatalogRootTreeNode) {
      return (CatalogRootTreeNode) node;
    }
    return null; // something invalid
  }

  private CatalogPublication getSelectedPublication() {
    final CatalogPublicationLabel selectedItem = (CatalogPublicationLabel) cbPublication
        .getSelectedItem();
    return (selectedItem == null) ? null : selectedItem.getPublication();
  }

  @Override
  public void itemStateChanged(final ItemEvent e) {
    if (e.getStateChange() == ItemEvent.DESELECTED) {
      if ((cbPublication.getItemCount() > 0)
          && (cbPublication.getSelectedIndex() == -1)) {
        // has options but none selected. Select first.
        cbPublication.setSelectedIndex(0);
      }
    } else {
      reloadPublication();
    }
  }

  private void loadPublications() {
    Edt.required();
    final ArrayList<CatalogPublication> publications = Client.cache().catalog()
        .getPublications();
    Collections.sort(publications, publicationsComparator);
    // save selection
    final CatalogPublication selected = getSelectedPublication();
    // update combo
    cbPublication.removeAllItems();
    for (final CatalogPublication p : publications) {
      cbPublication.addItem(new CatalogPublicationLabel(true, p));
    }
    // select last selected
    if (rdbtnLatest.isSelected()) {
      selectLatestPublication();
    } else {
      setSelectedPublication(selected);
    }
  }

  public void registerListeners() {
    Events.catalog().addListener(this);
    loadPublications();
    selectLatestPublication();
  }

  private void reloadPublication() {
    final CatalogPublication p = getSelectedPublication();
    tree.getModel().load(p);
    firePublicationChanged(p == null ? 0 : p.getId());
  }

  public void removeCatalogTreePanelListener(final CatalogTreePanelListener l) {
    listeners.add(CatalogTreePanelListener.class, l);
  }

  private void selectLatestPublication() {
    Edt.required();
    // select order: latest (index=1), unpublished (index=0), deselect (-1)
    final int select = Math.min(1, (cbPublication.getItemCount() - 1));
    cbPublication.setSelectedIndex(select);
  }

  private void setSelectedPublication(final CatalogPublication selected) {
    if ((selected == null) || (selected.getId() == 0)) {
      if (cbPublication.getItemCount() > 0) {
        cbPublication.setSelectedIndex(0);
      }
    } else {
      final int selectedId = selected.getId();
      // for below starts from '1' (skip 'unpublished' item)
      for (int i = 1; i < cbPublication.getItemCount(); i++) {
        final CatalogPublication p = cbPublication.getItemAt(i)
            .getPublication();
        if (p.getId() == selectedId) {
          cbPublication.setSelectedIndex(i);
          return;
        }
      }
    }
    // select first option (unpublished)
    if (cbPublication.getItemCount() > 0) {
      cbPublication.setSelectedIndex(0);
    }
  }

  public void unregisterListeners() {
    Events.catalog().removeListener(this);
  }

  @Override
  public void valueChanged(final TreeSelectionEvent e) {
    final TreePath path = e.getNewLeadSelectionPath();
    if (path == null) {
      fireGroupItemChanged(false, null, null);
      return;
    }

    final TreeNode node = getItemOrGroup(path);
    if (node == null) {
      // if node was null, maybe the parent is acceptable.
      // ex: description or details of group/item
      tree.setSelectionPath(path.getParentPath());
    } else {
      final CatalogGroup g = (node instanceof CatalogGroupTreeNode) ? ((CatalogGroupTreeNode) node)
          .getGroup() : null;
      final CatalogItem i = (node instanceof CatalogItemTreeNode) ? ((CatalogItemTreeNode) node)
          .getItem() : null;
      final boolean isRoot = (node instanceof CatalogRootTreeNode);
      fireGroupItemChanged(isRoot, g, i);
    }
  }
}
