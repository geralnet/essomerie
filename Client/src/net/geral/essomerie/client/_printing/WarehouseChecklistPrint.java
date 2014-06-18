package net.geral.essomerie.client._printing;

import java.awt.Font;

import net.geral.essomerie.shared.warehouse.WarehouseItem;

public class WarehouseChecklistPrint extends CRPrintDocument {
  private final WarehouseItem[] items;

  public WarehouseChecklistPrint(final WarehouseItem[] items) {
    this.items = items;
  }

  @Override
  protected void printBody() {
    g.setFont(new Font("monospaced", Font.PLAIN, 7));
    for (final WarehouseItem i : items) {
      writeline("[  ] " + i.name + " (" + i.getQuantity() + " " + i.unit
          + ")");
    }
  }
}
