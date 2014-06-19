package net.geral.essomerie.client.gui.warehouse.transferable;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import net.geral.essomerie.shared.warehouse.WarehouseGroup;

public class WarehouseTransferableGroup implements Transferable {
  public static DataFlavor     FLAVOR = new DataFlavor(
                                          WarehouseTransferableGroup.class,
                                          WarehouseTransferableGroup.class
                                              .getSimpleName());
  private final WarehouseGroup group;

  public WarehouseTransferableGroup(final WarehouseGroup group) {
    this.group = group;
  }

  @Override
  public Object getTransferData(final DataFlavor flavor)
      throws UnsupportedFlavorException, IOException {
    if (flavor == FLAVOR) {
      return group;
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
