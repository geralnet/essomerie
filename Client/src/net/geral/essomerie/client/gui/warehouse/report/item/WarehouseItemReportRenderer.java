package net.geral.essomerie.client.gui.warehouse.report.item;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.SwingConstants;

import net.geral.essomerie.shared.warehouse.WarehouseChangeLogEntry;
import net.geral.lib.table.GNTableRenderer;

public class WarehouseItemReportRenderer extends GNTableRenderer {
  private static final long serialVersionUID = 1L;

  @Override
  public Component getTableCellRendererComponent(final JTable table,
      final Object value, final boolean isSelected, final boolean hasFocus,
      final int row, final int column) {

    super.getTableCellRendererComponent(table, value, isSelected, hasFocus,
        row, column);

    if (value instanceof Float) {
      setHorizontalAlignment(SwingConstants.TRAILING);
      final float f = Math.round(((Float) value).floatValue() * 100) / 100;
      setText((f == 0) && (column > 2) ? "" : WarehouseChangeLogEntry
          .toQuantityString(f));
      if (f < 0) {
        setForeground(Color.RED);
      } else {
        setForeground(Color.BLACK);
      }
    } else {
      setHorizontalAlignment(SwingConstants.LEADING);
    }

    return this;
  }
}
