package net.geral.essomerie.client.gui.organizer.persons.editors.tables.sales;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.SwingConstants;

import net.geral.essomerie.client.resources.S;
import net.geral.lib.table.GNTableRenderer;

import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;

public class SalesRenderer extends GNTableRenderer {
  private static final long serialVersionUID = 1L;

  @Override
  public Component getTableCellRendererComponent(final JTable table,
      final Object value, final boolean isSelected, final boolean hasFocus,
      final int row, final int column) {

    super.getTableCellRendererComponent(table, value, isSelected, hasFocus,
        row, column);

    if (value instanceof LocalDateTime) {
      setText(((LocalDateTime) value).toString(DateTimeFormat
          .forPattern(S.FORMAT_DATETIME_SIMPLE_NO_SECONDS.s())));
    }

    if (column == 1) {
      setHorizontalAlignment(SwingConstants.TRAILING);
    } else {
      setHorizontalAlignment(SwingConstants.LEADING);
    }

    return this;
  }
}
