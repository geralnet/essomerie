package net.geral.essomerie.client.gui.tools.salesregister.log;

import java.awt.Component;

import javax.swing.JTable;

import net.geral.lib.table.GNTableRenderer;

public class LogRenderer extends GNTableRenderer {
  private static final long serialVersionUID = 1L;

  @Override
  public Component getTableCellRendererComponent(final JTable table,
      final Object value, final boolean isSelected, final boolean hasFocus,
      final int row, final int column) {
    super.getTableCellRendererComponent(table, value, isSelected, hasFocus,
        row, column);

    setHorizontalAlignment(column == 3 ? TRAILING : LEADING);

    return this;
  }
}
