package net.geral.essomerie.client._gui.caixa;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import net.geral.essomerie.shared.money.Money;
import net.geral.math.FormatadorDecimal;

public class ValorTableCellRenderer extends DefaultTableCellRenderer {
  private static final long       serialVersionUID = 1L;

  private final FormatadorDecimal inteiro          = new FormatadorDecimal(0);

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
      setText(inteiro.formatar(((Integer) value).intValue()));
    } else {
      setHorizontalAlignment(LEADING);
    }

    return this;
  }
}
