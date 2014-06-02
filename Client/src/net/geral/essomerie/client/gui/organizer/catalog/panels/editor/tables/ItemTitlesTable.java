package net.geral.essomerie.client.gui.organizer.catalog.panels.editor.tables;

import net.geral.essomerie.client.core.Client;
import net.geral.essomerie.client.core.configuration.CoreConfiguration;
import net.geral.essomerie.client.resources.S;
import net.geral.lib.table.GNTable;

public class ItemTitlesTable extends GNTable<ItemTitlesModel> {
  private static final long serialVersionUID = 1L;

  public ItemTitlesTable() {
    super(new ItemTitlesModel());
  }

  @Override
  protected void createColumns(final Object... params) {
    final CoreConfiguration c = Client.config();
    createColumn(S.ORGANIZER_CATALOG_ITEM_LANGUAGE.s(),
        c.TableColumnWidth_Catalog_ItemTitle_Language,
        c.TableColumnWidth_Default);
    createColumn(S.ORGANIZER_CATALOG_ITEM_TITLE.s(),
        c.TableColumnWidth_Catalog_ItemTitle_Title, c.TableColumnWidth_Default);
    createColumn(S.ORGANIZER_CATALOG_ITEM_DESCRIPTION.s(),
        c.TableColumnWidth_Catalog_ItemTitle_Description,
        c.TableColumnWidth_Default);
  }

  @Override
  protected boolean deleteClicked(final int viewRow, final int tableColumn) {
    return true;
  }

  @Override
  public String getNewEntryText(final int columnIndex) {
    switch (columnIndex) {
      case 1:
        return S.ORGANIZER_CATALOG_ITEM_TITLE_DEFAULT.s();
      default:
        return null;
    }
  }
}
