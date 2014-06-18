package net.geral.essomerie.client.gui.warehouse.groups.tree;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import net.geral.essomerie.shared.warehouse.WarehouseGroup;
import net.geral.lib.edt.Edt;

public class WarehouseGroupTreeModel extends DefaultTreeModel {
  private static final long            serialVersionUID = 1L;
  private final DefaultMutableTreeNode root;

  public WarehouseGroupTreeModel() {
    super(new DefaultMutableTreeNode("root"));
    root = (DefaultMutableTreeNode) getRoot();
  }

  public WarehouseGroupTreeNode getGroupNode(final WarehouseGroup group) {
    final int n = root.getChildCount();
    for (int i = 0; i < n; i++) {
      WarehouseGroupTreeNode node = ((WarehouseGroupTreeNode) root
          .getChildAt(i));
      node = node.getGroupNode(group);
      if (node != null) {
        return node;
      }
    }
    return null;
  }

  public void moveTreeNode(final WarehouseGroup group,
      final DefaultMutableTreeNode parent, final int index) {
    insertNodeInto(new WarehouseGroupTreeNode(group), parent, index);
  }

  public void recreate(final WarehouseGroup[] groups) {
    Edt.required();
    root.removeAllChildren();
    for (final WarehouseGroup g : groups) {
      root.add(new WarehouseGroupTreeNode(g));
    }
    reload();
  }
}
