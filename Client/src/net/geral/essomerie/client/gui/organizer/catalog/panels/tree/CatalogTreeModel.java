package net.geral.essomerie.client.gui.organizer.catalog.panels.tree;

import java.security.InvalidParameterException;
import java.util.ArrayList;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

import net.geral.essomerie.client.core.Client;
import net.geral.essomerie.shared.catalog.Catalog;
import net.geral.essomerie.shared.catalog.CatalogGroup;
import net.geral.essomerie.shared.catalog.CatalogItem;
import net.geral.essomerie.shared.catalog.CatalogPublication;
import net.geral.essomerie.shared.money.Money;
import net.geral.lib.edt.Edt;

public class CatalogTreeModel extends DefaultTreeModel {
  private static final long         serialVersionUID = 1L;
  private final CatalogRootTreeNode root             = (CatalogRootTreeNode) getRoot();
  private Catalog                   showing          = null;

  public CatalogTreeModel() {
    super(new CatalogRootTreeNode());
  }

  public void addGroup(final CatalogGroup group) {
    Edt.required();
    final int idparent = group.getIdParent();
    final MutableTreeNode parentNode = (idparent == 0) ? root
        : getGroupNode(idparent);
    final int n = parentNode.getChildCount();
    int index = 0;
    // skip details
    for (; index < n; index++) {
      if (!(parentNode.getChildAt(index) instanceof CatalogGroupDetailsTreeNode)) {
        break;
      }
    }
    // skip groups with smaller order
    for (; index < n; index++) {
      final TreeNode child = parentNode.getChildAt(index);
      if (!(child instanceof CatalogGroupTreeNode)) {
        break;
      }
      final CatalogGroupTreeNode gChild = (CatalogGroupTreeNode) child;
      if (gChild.getGroup().compareTo(group) > 0) {
        break;
      }
    }
    // insert in found position
    parentNode.insert(new CatalogGroupTreeNode(group), index);
    nodesWereInserted(parentNode, new int[] { index });
  }

  public void addItem(final CatalogItem item) {
    Edt.required();
    final int idgroup = item.getIdGroup();
    final MutableTreeNode groupNode = getGroupNode(idgroup);
    if (groupNode == null) {
      throw new InvalidParameterException("Item must be in a group.");
    }
    final int nChild = groupNode.getChildCount();
    int index = 0;
    // skip details
    for (; index < nChild; index++) {
      if (!(groupNode.getChildAt(index) instanceof CatalogItemDetailsTreeNode)) {
        break;
      }
    }
    // skip items with smaller order
    for (; index < nChild; index++) {
      final TreeNode child = groupNode.getChildAt(index);
      if (!(child instanceof CatalogItemTreeNode)) {
        break;
      }
      final CatalogItemTreeNode gChild = (CatalogItemTreeNode) child;
      if (gChild.getItem().compareTo(item) > 0) {
        break;
      }
    }
    // insert in found position
    groupNode.insert(new CatalogItemTreeNode(item), index);
    nodesWereInserted(groupNode, new int[] { index });
  }

  private void adjustDetailsFor(final CatalogGroupTreeNode node) {
    final CatalogGroup g = node.getGroup();
    boolean removed = false;
    boolean inserted = false;
    // remove old
    for (int i = 0; i < node.getChildCount(); i++) {
      final TreeNode child = node.getChildAt(i);
      if (child instanceof CatalogGroupDetailsTreeNode) {
        node.remove(i);
        removed = true;
        i--;
      }
    }
    // add new
    for (final String lang : g.getLanguages()) {
      final String sub = g.getSubtitle(lang);
      if (sub.length() > 0) {
        inserted = true;
        node.insert(new CatalogGroupDetailsTreeNode(lang + ": " + sub), 0);
      }
    }
    // event
    if (inserted || removed) {
      nodeStructureChanged(node);
    }
  }

  private void adjustDetailsFor(final CatalogItemTreeNode node) {
    final CatalogItem it = node.getItem();
    boolean removed = false;
    final boolean inserted = false;
    // remove old
    for (int i = 0; i < node.getChildCount(); i++) {
      final TreeNode child = node.getChildAt(i);
      if (child instanceof CatalogItemDetailsTreeNode) {
        node.remove(i);
        removed = true;
        i--;
      }
    }
    // add new item descriptions
    for (final String lang : it.getLanguages()) {
      final String desc = it.getDescription(lang);
      if (desc.length() > 0) {
        node.add(new CatalogItemDetailsTreeNode(lang + ": " + desc));
      }
    }
    // add new item prices
    for (final String code : it.getPriceCodes()) {
      final Money price = it.getPrice(code);
      node.add(new CatalogItemDetailsTreeNode("$ " + price + " (" + code + ")"));
    }
    // event
    if (inserted || removed) {
      nodeStructureChanged(node);
    }
  }

  public boolean changeNode(final int oldId, final CatalogGroup group) {
    Edt.required();
    final CatalogGroupTreeNode node = getGroupNodeFor(root, oldId);
    if (node == null) {
      return false;
    }
    node.setGroup(group);
    adjustDetailsFor(node);
    nodeChanged(node);
    return true;
  }

  public boolean changeNode(final int oldId, final CatalogItem item) {
    Edt.required();
    final CatalogItemTreeNode node = getItemNodeFor(root, oldId);
    if (node == null) {
      return false;
    }
    node.setItem(item);
    adjustDetailsFor(node);
    nodeChanged(node);
    return true;
  }

  public CatalogGroupTreeNode getGroupNode(final int idgroup) {
    return getGroupNodeFor(root, idgroup);
  }

  private CatalogGroupTreeNode getGroupNodeFor(final TreeNode parent,
      final int idgroup) {
    final int n = parent.getChildCount();
    for (int i = 0; i < n; i++) {
      final TreeNode node = parent.getChildAt(i);
      if (node instanceof CatalogGroupTreeNode) {
        final CatalogGroupTreeNode gNode = (CatalogGroupTreeNode) node;
        if (gNode.getGroup().getId() == idgroup) {
          return gNode;
        }
      }
      final CatalogGroupTreeNode found = getGroupNodeFor(node, idgroup);
      if (found != null) {
        return found;
      }
    }
    return null;
  }

  public CatalogItemTreeNode getItemNode(final int iditem) {
    return getItemNodeFor(root, iditem);
  }

  private CatalogItemTreeNode getItemNodeFor(final TreeNode parent,
      final int iditem) {
    final int n = parent.getChildCount();
    for (int i = 0; i < n; i++) {
      final TreeNode node = parent.getChildAt(i);
      if (node instanceof CatalogItemTreeNode) {
        final CatalogItemTreeNode iNode = (CatalogItemTreeNode) node;
        if (iNode.getItem().getId() == iditem) {
          return iNode;
        }
      }
      final CatalogItemTreeNode found = getItemNodeFor(node, iditem);
      if (found != null) {
        return found;
      }
    }
    return null;
  }

  private void load(final CatalogGroupTreeNode gtn) {
    final CatalogGroup g = gtn.getGroup();
    // load group details
    adjustDetailsFor(gtn);
    // load subgroups
    final ArrayList<CatalogGroup> subs = showing.getSubGroups(g);
    for (final CatalogGroup sub : subs) {
      final CatalogGroupTreeNode sgtn = new CatalogGroupTreeNode(sub);
      load(sgtn);
      gtn.add(sgtn);
    }
    // load items
    final ArrayList<CatalogItem> items = showing.getItems(g);
    for (final CatalogItem i : items) {
      final CatalogItemTreeNode itn = new CatalogItemTreeNode(i);
      gtn.add(itn);
      // load item descriptions
      for (final String lang : i.getLanguages()) {
        final String desc = i.getDescription(lang);
        if (desc.length() > 0) {
          itn.add(new CatalogItemDetailsTreeNode(lang + ": " + desc));
        }
      }
      // load item prices
      for (final String code : i.getPriceCodes()) {
        final Money price = i.getPrice(code);
        itn.add(new CatalogItemDetailsTreeNode("$ " + price + " (" + code + ")"));
      }
    }
  }

  public void load(final CatalogPublication publication) {
    Edt.required();
    final Catalog catalog = Client.cache().catalog()
        .fetch((publication == null) ? 0 : publication.getId());
    final ArrayList<CatalogGroup> roots = catalog.getRootGroups();
    showing = catalog;
    root.removeAllChildren();
    for (final CatalogGroup g : roots) {
      final CatalogGroupTreeNode gtn = new CatalogGroupTreeNode(g);
      load(gtn);
      root.add(gtn);
    }
    root.setUserObject(new CatalogPublicationLabel(false, publication));
    nodeStructureChanged(root);
  }

  public void removeGroups(final ArrayList<Integer> ids) {
    Edt.required();
    removeGroups(root, ids);
  }

  private void removeGroups(final MutableTreeNode parent,
      final ArrayList<Integer> ids) {
    nextChild: for (int i = 0; i < parent.getChildCount(); i++) {
      final MutableTreeNode node = (MutableTreeNode) parent.getChildAt(i);
      if (node instanceof CatalogGroupTreeNode) {
        final int gid = ((CatalogGroupTreeNode) node).getGroup().getId();
        for (final int id : ids) {
          if (id == gid) {
            node.removeFromParent();
            nodesWereRemoved(parent, new int[] { i }, new Object[] { node });
            i--;
            continue nextChild;
          }
        }
      }
      // if got here, was not removed, then go recursively inside
      removeGroups(node, ids);
    }
  }

  public void removeItems(final ArrayList<Integer> ids) {
    Edt.required();
    removeItems(root, ids);
  }

  private void removeItems(final MutableTreeNode parent,
      final ArrayList<Integer> ids) {
    nextChild: for (int i = 0; i < parent.getChildCount(); i++) {
      final MutableTreeNode node = (MutableTreeNode) parent.getChildAt(i);
      if (node instanceof CatalogItemTreeNode) {
        final int gid = ((CatalogItemTreeNode) node).getItem().getId();
        for (final int id : ids) {
          if (id == gid) {
            node.removeFromParent();
            nodesWereRemoved(parent, new int[] { i }, new Object[] { node });
            i--;
            continue nextChild;
          }
        }
      }
      // if got here, was not removed, then go recursively inside
      removeItems(node, ids);
    }
  }
}
