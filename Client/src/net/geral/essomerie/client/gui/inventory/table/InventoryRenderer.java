package net.geral.essomerie.client.gui.inventory.table;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.SwingConstants;

import net.geral.essomerie._shared.contagem.InventoryLogEntry;
import net.geral.lib.table.GNTableRenderer;

public class InventoryRenderer extends GNTableRenderer {
  private static final long serialVersionUID = 1L;

  public InventoryRenderer() {
  }

  @Override
  public Component getTableCellRendererComponent(final JTable table,
      final Object value, final boolean isSelected, final boolean hasFocus,
      final int row, final int column) {

    super.getTableCellRendererComponent(table, value, isSelected, hasFocus,
        row, column);

    if (value instanceof Float) {
      setHorizontalAlignment(SwingConstants.TRAILING);
      setText(InventoryLogEntry
          .toQuantidadeString(((Float) value).floatValue()));
    } else {
      setHorizontalAlignment(SwingConstants.LEADING);
    }

    return this;
  }
}
