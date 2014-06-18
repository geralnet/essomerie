package net.geral.essomerie.client.gui.warehouse.report.item;

import net.geral.essomerie.client.core.Client;
import net.geral.essomerie.client.core.configuration.CoreConfiguration;
import net.geral.essomerie.client.resources.S;
import net.geral.lib.table.GNTable;

public class WarehouseItemReportTable extends GNTable<WarehouseItemReportModel> {
  private static final long serialVersionUID = 1L;

  public WarehouseItemReportTable(final String[] reasons) {
    super(new WarehouseItemReportModel(reasons),
        new WarehouseItemReportRenderer(), (Object) reasons);
  }

  @Override
  protected void createColumns(final Object... params) {
    final CoreConfiguration c = Client.config();
    createColumn(S.WAREHOUSE_ITEM_REPORT_DATE.s(),
        c.TableColumnWidth_Default_Date,
        c.TableColumnWidth_Warehouse_ItemReport_Date,
        c.TableColumnWidth_Default);
    createColumn(S.WAREHOUSE_ITEM_REPORT_INITIAL.s(),
        c.TableColumnWidth_Warehouse_ItemReport_Ammount,
        c.TableColumnWidth_Default);
    createColumn(S.WAREHOUSE_ITEM_REPORT_DELTA.s(),
        c.TableColumnWidth_Warehouse_ItemReport_Ammount,
        c.TableColumnWidth_Default);
    final String[] columns = (String[]) params[0];
    for (final String col : columns) {
      createColumn(col, c.TableColumnWidth_Warehouse_ItemReport_Ammount,
          c.TableColumnWidth_Default);
    }
  }

  @Override
  protected boolean deleteClicked(final int viewRow, final int tableColumn) {
    return false;
  }

  @Override
  public String getNewEntryText(final int columnIndex) {
    return null;
  }
}
