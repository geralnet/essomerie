package net.geral.essomerie.client.gui.warehouse.groups.tree;

import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import javax.swing.JComponent;
import javax.swing.JTree;
import javax.swing.TransferHandler;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import net.geral.essomerie.client.core.Client;
import net.geral.essomerie.client.gui.warehouse.transferable.WarehouseTransferableGroup;
import net.geral.essomerie.client.gui.warehouse.transferable.WarehouseTransferableItem;
import net.geral.essomerie.shared.warehouse.WarehouseGroup;
import net.geral.essomerie.shared.warehouse.WarehouseItem;

import org.apache.log4j.Logger;

// Reference: http://www.coderanch.com/t/346509/GUI/java/JTree-drag-drop-tree-Java

public class WarehouseGroupTreeTransferHandler extends TransferHandler {
  private static final long   serialVersionUID = 1L;
  private static final Logger logger           = Logger
                                                   .getLogger(WarehouseGroupTreeTransferHandler.class);

  @Override
  public boolean canImport(final TransferSupport support) {
    if (!support.isDrop()) {
      return false;
    }
    return canImport_group(support) || canImport_item(support);
  }

  private boolean canImport_group(final TransferSupport support) {
    if (!support.isDataFlavorSupported(WarehouseTransferableGroup.FLAVOR)) {
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

  private boolean canImport_item(final TransferSupport support) {
    if (!support.isDataFlavorSupported(WarehouseTransferableItem.FLAVOR)) {
      return false;
    }

    final JTree.DropLocation dropLocation = (JTree.DropLocation) support
        .getDropLocation();
    // must be on (not insert)
    if (dropLocation.getChildIndex() != -1) {
      return false;
    }
    // not allowed dropping into same group
    final TreePath path = dropLocation.getPath();
    if (path == null) {
      return false;
    }
    final Object last = path.getLastPathComponent();
    if (!(last instanceof WarehouseGroupTreeNode)) {
      return false;
    }
    final WarehouseGroup group = ((WarehouseGroupTreeNode) last).getGroup();
    if (group == null) {
      return false;
    }
    try {
      final WarehouseItem item = (WarehouseItem) support.getTransferable()
          .getTransferData(WarehouseTransferableItem.FLAVOR);
      if (item.idgroup == group.id) {
        return false;
      }
    } catch (final Exception e) {
      return false;
    }

    // ok then
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
      return new WarehouseTransferableGroup(
          ((WarehouseGroupTreeNode) obj).getGroup());
    }
    return null;
  }

  @Override
  public int getSourceActions(final JComponent c) {
    return MOVE;
  }

  @Override
  public boolean importData(final TransferSupport support) {
    if (canImport_group(support)) {
      return importData_group(support);
    }
    if (canImport_item(support)) {
      return importData_item(support);
    }
    return false;
  }

  private boolean importData_group(final TransferSupport support) {
    // Extract transfer data.
    WarehouseGroup group = null;
    try {
      final Transferable t = support.getTransferable();
      group = (WarehouseGroup) t
          .getTransferData(WarehouseTransferableGroup.FLAVOR);
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

  private boolean importData_item(final TransferSupport support) {
    if (!canImport_item(support)) {
      return false;
    }
    try {
      final JTree.DropLocation dropLocation = (JTree.DropLocation) support
          .getDropLocation();
      final WarehouseGroup group = ((WarehouseGroupTreeNode) dropLocation
          .getPath().getLastPathComponent()).getGroup();
      final WarehouseItem item = (WarehouseItem) support.getTransferable()
          .getTransferData(WarehouseTransferableItem.FLAVOR);
      Client.connection().warehouse()
          .requestChangeItem(item.withIdGroup(group.id));
    } catch (final Exception e) {
      return false;
    }
    return false; // will change on the server first
  }
}
