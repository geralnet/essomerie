package net.geral.essomerie.client.gui.inventory.groups.tree;

import javax.swing.DropMode;
import javax.swing.JTree;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import net.geral.essomerie._shared.contagem.InventoryGroup;

public class InventoryGroupTree extends JTree {
  private static final long             serialVersionUID = 1L;
  private final InventoryGroupTreePopup popup;

  public InventoryGroupTree() {
    super(new InventoryGroupTreeModel());
    setRootVisible(false);
    setDragEnabled(false);
    setCellRenderer(new InventoryGroupTreeRenderer());
    setDropMode(DropMode.ON_OR_INSERT);
    setTransferHandler(new InventoryGroupTreeTransferHandle());
    getSelectionModel().setSelectionMode(
        TreeSelectionModel.SINGLE_TREE_SELECTION);
    popup = new InventoryGroupTreePopup(this);
    addMouseListener(popup);
  }

  @Override
  public InventoryGroupTreeModel getModel() {
    return (InventoryGroupTreeModel) super.getModel();
  }

  public InventoryGroup getSelectedGroup() {
    final TreePath selected = getSelectionPath();
    if (selected == null) {
      return null;
    }
    return ((InventoryGroupTreeNode) selected.getLastPathComponent())
        .getGroup();
  }

  public void updateGroups(final InventoryGroup[] inventoryGroups) {
    final InventoryGroup selected = getSelectedGroup();
    getModel().recreate(inventoryGroups);
    // expand all!
    for (int i = 0; i < getRowCount(); i++) {
      expandRow(i);
      final TreePath row = getPathForRow(i);
      if ((row != null) && (selected != null)) {
        final Object last = row.getLastPathComponent();
        if (last instanceof InventoryGroupTreeNode) {
          final InventoryGroupTreeNode node = (InventoryGroupTreeNode) last;
          if (selected.id == node.getGroup().id) {
            setSelectionRow(i);
          }
        }
      }
    }
  }
}
