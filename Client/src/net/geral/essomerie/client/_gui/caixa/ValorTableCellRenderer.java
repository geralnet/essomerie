package net.geral.essomerie.client._gui.caixa;

import java.awt.Component;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import net.geral.essomerie.shared.money.Money;

public class ValorTableCellRenderer extends DefaultTableCellRenderer {
  private static final long  serialVersionUID = 1L;

  private final NumberFormat decimalFormat    = DecimalFormat
                                                  .getIntegerInstance();

  @Override
  public Component getTableCellRendererComponent(final JTable table,
      final Object value, final boolean isSelected, final boolean hasFocus,
      final int row, final int column) {
    super.getTableCellRendererComponent(table, value, isSelected, hasFocus,
        row, column);

    if (value instanceof Money) {
      setHorizontalAlignment(TRAILING);
      setText(value.toString());
    } else if (value instanceof Integer) {
      setHorizontalAlignment(TRAILING);
      setText(decimalFormat.format(((Integer) value).intValue()));
    } else {
      setHorizontalAlignment(LEADING);
    }

    return this;
  }
}
