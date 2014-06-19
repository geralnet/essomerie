package net.geral.essomerie.client.gui.warehouse.transferable;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import net.geral.essomerie.shared.warehouse.WarehouseItem;

public class WarehouseTransferableItem implements Transferable {
  public static DataFlavor    FLAVOR = new DataFlavor(
                                         WarehouseTransferableItem.class,
                                         WarehouseTransferableItem.class
                                             .getSimpleName());
  private final WarehouseItem item;

  public WarehouseTransferableItem(final WarehouseItem item) {
    this.item = item;
  }

  @Override
  public Object getTransferData(final DataFlavor flavor)
      throws UnsupportedFlavorException, IOException {
    if (flavor == FLAVOR) {
      return item;
    }
    throw new UnsupportedFlavorException(flavor);
  }

  @Override
  public DataFlavor[] getTransferDataFlavors() {
    return new DataFlavor[] { FLAVOR };
  }

  @Override
  public boolean isDataFlavorSupported(final DataFlavor flavor) {
    return (flavor == FLAVOR);
  }
}
