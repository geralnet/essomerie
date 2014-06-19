package net.geral.essomerie.client.gui.warehouse.items.table;

import java.io.IOException;

import javax.swing.JOptionPane;

import net.geral.essomerie.client.core.Client;
import net.geral.essomerie.client.core.configuration.CoreConfiguration;
import net.geral.essomerie.client.resources.S;
import net.geral.essomerie.shared.warehouse.WarehouseChangeLogEntry;
import net.geral.essomerie.shared.warehouse.WarehouseItem;
import net.geral.lib.table.GNTable;

import org.apache.log4j.Logger;

public class WarehouseItemTable extends GNTable<WarehouseItemTableModel> {
  private static final long serialVersionUID = 1L;
  private final Logger      logger           = Logger
                                                 .getLogger(WarehouseItemTable.class);

  public WarehouseItemTable() {
    super(new WarehouseItemTableModel(), new WarehouseItemTableRenderer());
    initialSort(0, false);
    setDragEnabled(false);
    setTransferHandler(new WarehouseItemTableTransferHandle());
  }

  @Override
  protected void createColumns(final Object... params) {
    final CoreConfiguration c = Client.config();
    createColumn(S.WAREHOUSE_TABLE_PRODUCT.s(),
        c.TableColumnWidth_Warehouse_Product, c.TableColumnWidth_Default);
    createColumn(S.WAREHOUSE_TABLE_QUANTITY.s(),
        c.TableColumnWidth_Warehouse_Quantity, c.TableColumnWidth_Default);
    createColumn(S.WAREHOUSE_TABLE_UNIT.s(), c.TableColumnWidth_Warehouse_Unit,
        c.TableColumnWidth_Default);
    createColumn(S.WAREHOUSE_TABLE_MINIMUM.s(),
        c.TableColumnWidth_Warehouse_Minimum,
        c.TableColumnWidth_Warehouse_Quantity, c.TableColumnWidth_Default);
    createColumn(S.WAREHOUSE_TABLE_MAXIMUM.s(),
        c.TableColumnWidth_Warehouse_Maximum,
        c.TableColumnWidth_Warehouse_Quantity, c.TableColumnWidth_Default);
  }

  @Override
  protected boolean deleteClicked(final int viewRow, final int tableColumn) {
    final WarehouseItem wi = getModel().get(convertRowIndexToModel(viewRow));
    final int res = JOptionPane.showConfirmDialog(Client.window(),
        S.WAREHOUSE_ADMIN_DELETE_CONFIRM.s(wi.name), S.TITLE_CONFIRM.s(),
        JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
    if (res == JOptionPane.YES_OPTION) {
      try {
        Client.connection().warehouse().requestDeleteItem(wi.id);
      } catch (final IOException e) {
        logger.error(e);
      }
    }
    return false; // will be deleted on server first
  }

  @Override
  public String getDefaultEditingString(final int row, final int column) {
    final Object value = getValueAt(row, column);
    if (value instanceof WarehouseItem) {
      final WarehouseItem wi = (WarehouseItem) value;
      final int col = convertColumnIndexToModel(column);
      if (col == 3) {
        return WarehouseChangeLogEntry.toQuantityString(wi.minimum);
      }
      if (col == 4) {
        return WarehouseChangeLogEntry.toQuantityString(wi.maximum);
      }
    }
    return super.getDefaultEditingString(row, column);
  }

  @Override
  public String getNewEntryText(final int columnIndex) {
    if (columnIndex == 0) {
      return S.WAREHOUSE_ADMIN_NEWITEM_TEXT.s();
    }
    return "";
  }

  @Override
  public WarehouseItem getSelected() {
    final int s = getSelectedRow();
    if (s == -1) {
      return null;
    }
    return getModel().get(convertRowIndexToModel(s));
  }

  public void selectNext() {
    if (getModel().getRowCount() == 0) {
      return;
    }

    int s = getSelectedRow() + 1;
    if (s >= getModel().getRowCount()) {
      s = 0;
    }
    getSelectionModel().setSelectionInterval(s, s);
  }

  public void selectPrevious() {
    if (getModel().getRowCount() == 0) {
      return;
    }

    int s = getSelectedRow() - 1;
    if (s < 0) {
      s = getModel().getRowCount() - 1;
    }
    getSelectionModel().setSelectionInterval(s, s);
  }
}
