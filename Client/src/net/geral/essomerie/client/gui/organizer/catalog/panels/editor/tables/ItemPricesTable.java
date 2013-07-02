package net.geral.essomerie.client.gui.organizer.catalog.panels.editor.tables;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.SwingConstants;

import net.geral.essomerie.client.core.Client;
import net.geral.essomerie.client.core.configuration.CoreConfiguration;
import net.geral.essomerie.client.resources.S;
import net.geral.lib.table.GNTable;
import net.geral.lib.table.GNTableRenderer;

public class ItemPricesTable extends GNTable<ItemPricesModel> {
  private static final long serialVersionUID = 1L;

  public ItemPricesTable() {
    super(new ItemPricesModel(), new GNTableRenderer() {
      private static final long serialVersionUID = 1L;

      @Override
      public Component getTableCellRendererComponent(final JTable table,
          final Object value, final boolean isSelected, final boolean hasFocus,
          final int row, final int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus,
            row, column);
        setHorizontalAlignment(column == 0 ? SwingConstants.LEADING
            : SwingConstants.TRAILING);
        return this;
      }
    });
  }

  @Override
  protected void createColumns() {
    final CoreConfiguration c = Client.config();
    createColumn(S.ORGANIZER_CATALOG_ITEM_PRICECODE.s(),
        c.TableColumnWidth_Catalog_ItemPrice_Code, c.TableColumnWidth_Default);
    createColumn(S.ORGANIZER_CATALOG_ITEM_PRICE.s(),
        c.TableColumnWidth_Catalog_ItemPrice_Price, c.TableColumnWidth_Default);
  }

  @Override
  protected boolean deleteClicked(final int viewRow, final int tableColumn) {
    return true;
  }

  @Override
  public String getNewEntryText(final int columnIndex) {
    switch (columnIndex) {
      case 1:
        return S.ORGANIZER_CATALOG_ITEM_PRICE_DEFAULT.s();
      default:
        return null;
    }
  }
}
