package net.geral.essomerie.client.gui.warehouse.groups.tree;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import javax.swing.Icon;
import javax.swing.tree.DefaultMutableTreeNode;

import net.geral.essomerie.client.resources.IMG;
import net.geral.essomerie.shared.warehouse.WarehouseGroup;

public class WarehouseGroupTreeNode extends DefaultMutableTreeNode implements
    Transferable {
  private static final long serialVersionUID = 1L;
  public static final int   ICON_SIZE        = 16;

  public WarehouseGroupTreeNode(final WarehouseGroup group) {
    super(group);
    for (final WarehouseGroup g : group.getSubgroups()) {
      add(new WarehouseGroupTreeNode(g));
    }
  }

  public WarehouseGroup getGroup() {
    return (WarehouseGroup) getUserObject();
  }

  public WarehouseGroupTreeNode getGroupNode(final WarehouseGroup group) {
    if (getGroup().id == group.id) {
      return this;
    }
    final int n = getChildCount();
    for (int i = 0; i < n; i++) {
      WarehouseGroupTreeNode node = ((WarehouseGroupTreeNode) getChildAt(i));
      node = node.getGroupNode(group);
      if (node != null) {
        return node;
      }
    }
    return null;
  }

  public Icon getIcon() {
    if (getGroup().getSubgroups().length == 0) {
      return IMG.ICON__WAREHOUSE__CLOSED.icon(ICON_SIZE);
    }
    return IMG.ICON__WAREHOUSE__OPEN.icon(ICON_SIZE);
  }

  @Override
  public Object getTransferData(final DataFlavor flavor)
      throws UnsupportedFlavorException, IOException {
    if (!isDataFlavorSupported(flavor)) {
      throw new UnsupportedFlavorException(flavor);
    }
    return getGroup();
  }

  @Override
  public DataFlavor[] getTransferDataFlavors() {
    return WarehouseGroupTreeTransferHandle.getDataFlavors();
  }

  @Override
  public boolean isDataFlavorSupported(final DataFlavor flavor) {
    return WarehouseGroupTreeTransferHandle.getDataFlavor().equals(flavor);
  }
}
