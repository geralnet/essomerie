package net.geral.essomerie.client.gui.inventory.groups.tree;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import net.geral.essomerie._shared.contagem.InventoryGroup;
import net.geral.lib.edt.Edt;

public class InventoryGroupTreeModel extends DefaultTreeModel {
  private static final long            serialVersionUID = 1L;
  private final DefaultMutableTreeNode root;

  public InventoryGroupTreeModel() {
    super(new DefaultMutableTreeNode("root"));
    root = (DefaultMutableTreeNode) getRoot();
  }

  public InventoryGroupTreeNode getGroupNode(final InventoryGroup group) {
    final int n = root.getChildCount();
    for (int i = 0; i < n; i++) {
      InventoryGroupTreeNode node = ((InventoryGroupTreeNode) root
          .getChildAt(i));
      node = node.getGroupNode(group);
      if (node != null) {
        return node;
      }
    }
    return null;
  }

  public void moveTreeNode(final InventoryGroup group,
      final DefaultMutableTreeNode parent, final int index) {
    insertNodeInto(new InventoryGroupTreeNode(group), parent, index);
  }

  public void recreate(final InventoryGroup[] groups) {
    Edt.required();
    root.removeAllChildren();
    for (final InventoryGroup g : groups) {
      root.add(new InventoryGroupTreeNode(g));
    }
    reload();
  }
}
