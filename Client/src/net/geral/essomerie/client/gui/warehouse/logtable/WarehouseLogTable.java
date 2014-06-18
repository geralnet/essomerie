package net.geral.essomerie.client.gui.warehouse.logtable;

import net.geral.essomerie.client.core.Client;
import net.geral.essomerie.client.core.configuration.CoreConfiguration;
import net.geral.essomerie.client.gui.warehouse.items.WarehouseItemsPanel;
import net.geral.essomerie.client.resources.S;
import net.geral.essomerie.shared.warehouse.WarehouseChangeLog;
import net.geral.lib.table.GNTable;

public class WarehouseLogTable extends GNTable<WarehouseLogModel> {
  private static final long serialVersionUID = 1L;

  public WarehouseLogTable(final WarehouseItemsPanel contagemPanel) {
    super(new WarehouseLogModel(contagemPanel));
  }

  @Override
  protected void createColumns(final Object... params) {
    final CoreConfiguration c = Client.config();
    createColumn(S.WAREHOUSE_LOGTABLE_WHEN.s(),
        c.TableColumnWidth_Warehouse_Log_Date,
        c.TableColumnWidth_Default_DateTimeNoSeconds);
    createColumn(S.WAREHOUSE_LOGTABLE_BY.s(),
        c.TableColumnWidth_Warehouse_Log_By,
        c.TableColumnWidth_Default_Username);
    createColumn(S.WAREHOUSE_LOGTABLE_CHANGE.s(),
        c.TableColumnWidth_Warehouse_Log_Change, c.TableColumnWidth_Default);
    createColumn(S.WAREHOUSE_LOGTABLE_COMMENTS.s(),
        c.TableColumnWidth_Warehouse_Log_Comments, c.TableColumnWidth_Default);
  }

  @Override
  protected boolean deleteClicked(final int viewRow, final int tableColumn) {
    return false;
  }

  @Override
  public String getNewEntryText(final int columnIndex) {
    return null;
  }

  public void set(final WarehouseChangeLog h) {
    getModel().setData(h.getEntries());
  }
}
