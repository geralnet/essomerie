package net.geral.essomerie.client.gui.warehouse.items.table;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;

import javax.swing.JComponent;
import javax.swing.TransferHandler;

import net.geral.essomerie.client.gui.warehouse.transferable.WarehouseTransferableItem;
import net.geral.essomerie.shared.warehouse.WarehouseItem;

import org.apache.log4j.Logger;

// Reference: http://www.coderanch.com/t/346509/GUI/java/JTree-drag-drop-tree-Java

public class WarehouseItemTableTransferHandle extends TransferHandler {
  private static final long   serialVersionUID = 1L;
  private static DataFlavor   flavor           = null;
  private static final Logger logger           = Logger
                                                   .getLogger(WarehouseItemTableTransferHandle.class);

  static {
    try {
      final String mimeType = DataFlavor.javaJVMLocalObjectMimeType
          + ";class=\"" + WarehouseItem.class.getName() + "\"";
      flavor = new DataFlavor(mimeType);
    } catch (final ClassNotFoundException e) {
      logger.error(e);
    }
  }

  public static DataFlavor getDataFlavor() {
    return flavor;
  }

  public static DataFlavor[] getDataFlavors() {
    return new DataFlavor[] { flavor };
  }

  @Override
  public boolean canImport(final TransferSupport support) {
    return false; // never import
  }

  @Override
  protected Transferable createTransferable(final JComponent c) {
    final WarehouseItemTable table = (WarehouseItemTable) c;
    final WarehouseItem item = table.getSelected();
    if (item == null) {
      return null;
    }
    return new WarehouseTransferableItem(item);
  }

  @Override
  public int getSourceActions(final JComponent c) {
    return MOVE;
  }

  @Override
  public boolean importData(final TransferSupport support) {
    System.err.println("importData " + this);
    return false;
    // if (!canImport(support)) {
    // return false;
    // }
    // // Extract transfer data.
    // WarehouseGroup group = null;
    // try {
    // final Transferable t = support.getTransferable();
    // group = (WarehouseGroup) t.getTransferData(flavor);
    // } catch (final UnsupportedFlavorException ufe) {
    // logger.error(ufe);
    // } catch (final IOException ioe) {
    // logger.error(ioe);
    // }
    // if (group == null) {
    // return false;
    // }
    // // Get drop location info.
    // final JTree.DropLocation dropLocation = (JTree.DropLocation) support
    // .getDropLocation();
    // final TreePath dropPath = dropLocation.getPath();
    // final DefaultMutableTreeNode dropParent = (DefaultMutableTreeNode)
    // dropPath
    // .getLastPathComponent();
    // final WarehouseGroupTree tree = (WarehouseGroupTree)
    // support.getComponent();
    // final WarehouseGroupTreeModel model = tree.getModel();
    // // Check if parent is the same
    // final WarehouseGroupTreeNode originalNode = model.getGroupNode(group);
    // final int dropParentGroupId = (dropParent instanceof
    // WarehouseGroupTreeNode) ? ((WarehouseGroupTreeNode) dropParent)
    // .getGroup().id : 0;
    // final DefaultMutableTreeNode originalParent = (DefaultMutableTreeNode)
    // originalNode
    // .getParent();
    // final int originalParentGroupId = (originalParent instanceof
    // WarehouseGroupTreeNode) ? ((WarehouseGroupTreeNode) originalNode
    // .getParent()).getGroup().id : 0;
    // int dropIndex = dropLocation.getChildIndex();
    // if (dropParentGroupId == originalParentGroupId) {
    // // same parent, if moving below remove one position from the count
    // // since the group will be removed from that position
    // if (dropIndex > originalParent.getIndex(originalNode)) {
    // dropIndex--;
    // }
    // }
    // // Request change
    // try {
    // Client
    // .connection()
    // .warehouse()
    // .requestGroupParentOrderChange(originalNode.getGroup().id,
    // dropParentGroupId, dropIndex);
    // } catch (final IOException e) {
    // e.printStackTrace();
    // }
    // // change will occur first at the server
    // return false;
  }
}
