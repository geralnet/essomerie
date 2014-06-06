package net.geral.essomerie.client.gui.inventory.groups.tree;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import javax.swing.Icon;
import javax.swing.tree.DefaultMutableTreeNode;

import net.geral.essomerie._shared.contagem.InventoryGroup;
import net.geral.essomerie.client.resources.IMG;

public class InventoryGroupTreeNode extends DefaultMutableTreeNode implements
    Transferable {
  private static final long serialVersionUID = 1L;
  public static final int   ICON_SIZE        = 16;

  public InventoryGroupTreeNode(final InventoryGroup group) {
    super(group);
    for (final InventoryGroup g : group.getSubGrupos()) {
      add(new InventoryGroupTreeNode(g));
    }
  }

  public InventoryGroup getGroup() {
    return (InventoryGroup) getUserObject();
  }

  public InventoryGroupTreeNode getGroupNode(final InventoryGroup group) {
    if (getGroup().id == group.id) {
      return this;
    }
    final int n = getChildCount();
    for (int i = 0; i < n; i++) {
      InventoryGroupTreeNode node = ((InventoryGroupTreeNode) getChildAt(i));
      node = node.getGroupNode(group);
      if (node != null) {
        return node;
      }
    }
    return null;
  }

  public Icon getIcon() {
    if (getGroup().getSubGrupos().length == 0) {
      return IMG.ICON__INVENTORY__CLOSED.icon(ICON_SIZE);
    }
    return IMG.ICON__INVENTORY__OPEN.icon(ICON_SIZE);
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
    return InventoryGroupTreeTransferHandle.getDataFlavors();
  }

  @Override
  public boolean isDataFlavorSupported(final DataFlavor flavor) {
    // if (!InventoryGroupTreeTransferHandle.getDataFlavor().equals(flavor)) {
    // System.err.println("1) " + flavor + "\n2) "
    // + InventoryGroupTreeTransferHandle.getDataFlavor());
    // }
    return InventoryGroupTreeTransferHandle.getDataFlavor().equals(flavor);
  }
}
