package net.geral.essomerie.client.gui.warehouse.groups.tree;

import javax.swing.DropMode;
import javax.swing.JTree;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import net.geral.essomerie.shared.warehouse.WarehouseGroup;

public class WarehouseGroupTree extends JTree {
  private static final long             serialVersionUID = 1L;
  private final WarehouseGroupTreePopup popup;

  public WarehouseGroupTree() {
    super(new WarehouseGroupTreeModel());
    setRootVisible(false);
    setCellRenderer(new WarehouseGroupTreeRenderer());
    setDragEnabled(false);
    setDropMode(DropMode.ON_OR_INSERT);
    setTransferHandler(new WarehouseGroupTreeTransferHandler());
    getSelectionModel().setSelectionMode(
        TreeSelectionModel.SINGLE_TREE_SELECTION);
    popup = new WarehouseGroupTreePopup(this);
    addMouseListener(popup);
  }

  @Override
  public WarehouseGroupTreeModel getModel() {
    return (WarehouseGroupTreeModel) super.getModel();
  }

  public WarehouseGroup getSelectedGroup() {
    final TreePath selected = getSelectionPath();
    if (selected == null) {
      return null;
    }
    return ((WarehouseGroupTreeNode) selected.getLastPathComponent())
        .getGroup();
  }

  public void updateGroups(final WarehouseGroup[] warehouseGroups) {
    final WarehouseGroup selected = getSelectedGroup();
    getModel().recreate(warehouseGroups);
    // expand all!
    for (int i = 0; i < getRowCount(); i++) {
      expandRow(i);
      final TreePath row = getPathForRow(i);
      if ((row != null) && (selected != null)) {
        final Object last = row.getLastPathComponent();
        if (last instanceof WarehouseGroupTreeNode) {
          final WarehouseGroupTreeNode node = (WarehouseGroupTreeNode) last;
          if (selected.id == node.getGroup().id) {
            setSelectionRow(i);
          }
        }
      }
    }
  }
}
