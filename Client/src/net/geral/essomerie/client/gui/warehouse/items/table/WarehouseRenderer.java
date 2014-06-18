package net.geral.essomerie.client.gui.warehouse.items.table;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.SwingConstants;

import net.geral.essomerie.shared.warehouse.WarehouseChangeLogEntry;
import net.geral.essomerie.shared.warehouse.WarehouseItem;
import net.geral.lib.table.GNTableRenderer;

public class WarehouseRenderer extends GNTableRenderer {
  private static final long serialVersionUID = 1L;

  public WarehouseRenderer() {
  }

  @Override
  public Component getTableCellRendererComponent(final JTable table,
      final Object value, final boolean isSelected, final boolean hasFocus,
      final int row, final int column) {

    super.getTableCellRendererComponent(table, value, isSelected, hasFocus,
        row, column);

    setHorizontalAlignment(SwingConstants.LEADING);
    if (value instanceof WarehouseItem) {
      setHorizontalAlignment(SwingConstants.TRAILING);
      final WarehouseItem wi = (WarehouseItem) value;
      float f = 0;
      if (column == 3) {
        f = wi.minimum;
      } else if (column == 4) {
        f = wi.maximum;
      }
      final float d = wi.getQuantity() - f;
      final String sf = WarehouseChangeLogEntry.toQuantityString(f);
      final String sd = WarehouseChangeLogEntry.toQuantityString(d);
      setText(sf + " (\u0394 " + sd + ")");
      if (d < 0f) {
        setForeground(Color.RED);
      }
    } else if (value instanceof Float) {
      setHorizontalAlignment(SwingConstants.TRAILING);
      final float f = ((Float) value).floatValue();
      setText(WarehouseChangeLogEntry.toQuantityString(f));
    }

    return this;
  }
}
