package net.geral.essomerie.client.gui.organizer.persons.editors.tables.documents;

import java.awt.Component;
import java.awt.Font;

import javax.swing.JTable;
import javax.swing.SwingConstants;

import net.geral.lib.table.GNTableRenderer;

public class PersonDocumentRenderer extends GNTableRenderer {
  private static final long serialVersionUID = 1L;

  public PersonDocumentRenderer() {
  }

  @Override
  public Component getTableCellRendererComponent(final JTable table,
      final Object value, final boolean isSelected, final boolean hasFocus,
      final int row, final int column) {

    super.getTableCellRendererComponent(table, value, isSelected, hasFocus,
        row, column);

    if (column == 0) {
      setHorizontalAlignment(SwingConstants.TRAILING);
      setFont(getFont().deriveFont(Font.BOLD));
    } else {
      setHorizontalAlignment(SwingConstants.LEADING);
      setFont(getFont().deriveFont(Font.PLAIN));
    }

    return this;
  }
}
