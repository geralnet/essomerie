package net.geral.essomerie.client.gui.organizer.catalog.panels.tree;

import javax.swing.JTree;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import net.geral.lib.edt.Edt;

public class CatalogTree extends JTree {
  private static final long serialVersionUID = 1L;

  public CatalogTree() {
    super(new CatalogTreeModel());
    getSelectionModel().setSelectionMode(
        TreeSelectionModel.SINGLE_TREE_SELECTION);
    setRootVisible(true);
    setCellRenderer(new CatalogTreeCellRenderer());
  }

  @Override
  public CatalogTreeModel getModel() {
    return (CatalogTreeModel) super.getModel();
  }

  public void selectGroup(final int idgroup) {
    Edt.required();
    final CatalogGroupTreeNode node = getModel().getGroupNode(idgroup);
    setSelectionPath((node == null) ? null : new TreePath(node.getPath()));
  }

  public void selectItem(final int iditem) {
    Edt.required();
    final CatalogItemTreeNode node = getModel().getItemNode(iditem);
    setSelectionPath((node == null) ? null : new TreePath(node.getPath()));
  }
}
