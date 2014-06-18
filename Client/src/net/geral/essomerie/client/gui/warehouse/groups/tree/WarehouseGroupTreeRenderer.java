package net.geral.essomerie.client.gui.warehouse.groups.tree;

import java.awt.Component;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

public class WarehouseGroupTreeRenderer extends DefaultTreeCellRenderer {
  private static final long serialVersionUID = 1L;

  @Override
  public Component getTreeCellRendererComponent(final JTree tree,
      final Object value, final boolean sel, final boolean expanded,
      final boolean leaf, final int row, final boolean hasFocus) {

    super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row,
        hasFocus);

    if (value instanceof WarehouseGroupTreeNode) {
      setIcon(((WarehouseGroupTreeNode) value).getIcon());
    } else if (value instanceof DefaultMutableTreeNode) {
      setIcon(null);
    }
    return this;
  }
}
