package net.geral.essomerie.client.gui.warehouse.groups.tree;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import javax.swing.JComponent;
import javax.swing.JTree;
import javax.swing.TransferHandler;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import net.geral.essomerie.client.core.Client;
import net.geral.essomerie.shared.warehouse.WarehouseGroup;

import org.apache.log4j.Logger;

// Reference: http://www.coderanch.com/t/346509/GUI/java/JTree-drag-drop-tree-Java

public class WarehouseGroupTreeTransferHandle extends TransferHandler {
  private static final long   serialVersionUID = 1L;
  private static DataFlavor   flavor           = null;
  private static final Logger logger           = Logger
                                                   .getLogger(WarehouseGroupTreeTransferHandle.class);

  static {
    try {
      final String mimeType = DataFlavor.javaJVMLocalObjectMimeType
          + ";class=\"" + WarehouseGroupTreeNode.class.getName() + "\"";
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
    if (!support.isDrop()) {
      return false;
    }
    support.setShowDropLocation(true);
    if (!support.isDataFlavorSupported(flavor)) {
      return false;
    }

    // Do not allow a drop on the drag source selections.
    final WarehouseGroupTree tree = (WarehouseGroupTree) support.getComponent();
    final JTree.DropLocation dropLocation = (JTree.DropLocation) support
        .getDropLocation();
    final TreePath dropPath = dropLocation.getPath();
    final int dropRow = tree.getRowForPath(dropPath);
    final int selRow = tree.getSelectionRows()[0];
    if (selRow == dropRow) {
      return false;
    }

    // Do not allow a drop on an item inside the source selection.
    final Object dropNodeComponent = dropPath.getLastPathComponent();
    if (dropNodeComponent instanceof WarehouseGroupTreeNode) { // not root
      final WarehouseGroup sourceGroup = tree.getSelectedGroup();
      final WarehouseGroup dropGroup = ((WarehouseGroupTreeNode) dropNodeComponent)
          .getGroup();
      if (sourceGroup.contains(dropGroup)) {
        return false;
      }
    }

    return true;
  }

  @Override
  protected Transferable createTransferable(final JComponent c) {
    final JTree tree = (JTree) c;
    final TreePath[] paths = tree.getSelectionPaths();
    if ((paths == null) || (paths.length == 0)) {
      return null;
    }
    final Object obj = paths[0].getLastPathComponent();
    if (obj instanceof WarehouseGroupTreeNode) {
      return new WarehouseGroupTreeNode(
          ((WarehouseGroupTreeNode) obj).getGroup());
    }
    return null;
  }

  @Override
  public int getSourceActions(final JComponent c) {
    return COPY_OR_MOVE;
  }

  @Override
  public boolean importData(final TransferSupport support) {
    if (!canImport(support)) {
      return false;
    }
    // Extract transfer data.
    WarehouseGroup group = null;
    try {
      final Transferable t = support.getTransferable();
      group = (WarehouseGroup) t.getTransferData(flavor);
    } catch (final UnsupportedFlavorException ufe) {
      logger.error(ufe);
    } catch (final IOException ioe) {
      logger.error(ioe);
    }
    if (group == null) {
      return false;
    }
    // Get drop location info.
    final JTree.DropLocation dropLocation = (JTree.DropLocation) support
        .getDropLocation();
    final TreePath dropPath = dropLocation.getPath();
    final DefaultMutableTreeNode dropParent = (DefaultMutableTreeNode) dropPath
        .getLastPathComponent();
    final WarehouseGroupTree tree = (WarehouseGroupTree) support.getComponent();
    final WarehouseGroupTreeModel model = tree.getModel();
    // Check if parent is the same
    final WarehouseGroupTreeNode originalNode = model.getGroupNode(group);
    final int dropParentGroupId = (dropParent instanceof WarehouseGroupTreeNode) ? ((WarehouseGroupTreeNode) dropParent)
        .getGroup().id : 0;
    final DefaultMutableTreeNode originalParent = (DefaultMutableTreeNode) originalNode
        .getParent();
    final int originalParentGroupId = (originalParent instanceof WarehouseGroupTreeNode) ? ((WarehouseGroupTreeNode) originalNode
        .getParent()).getGroup().id : 0;
    int dropIndex = dropLocation.getChildIndex();
    if (dropParentGroupId == originalParentGroupId) {
      // same parent, if moving below remove one position from the count
      // since the group will be removed from that position
      if (dropIndex > originalParent.getIndex(originalNode)) {
        dropIndex--;
      }
    }
    // Request change
    try {
      Client
          .connection()
          .warehouse()
          .requestGroupParentOrderChange(originalNode.getGroup().id,
              dropParentGroupId, dropIndex);
    } catch (final IOException e) {
      e.printStackTrace();
    }
    // change will occur first at the server
    return false;
  }
}
