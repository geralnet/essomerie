package net.geral.essomerie.client.gui.inventory.table;

import net.geral.essomerie._shared.contagem.InventoryItem;
import net.geral.essomerie.client.core.Client;
import net.geral.essomerie.client.core.configuration.CoreConfiguration;
import net.geral.lib.table.GNTable;

//TODO translate
public class InventoryTable extends GNTable<InventoryModel> {
  private static final long serialVersionUID = 1L;

  public InventoryTable() {
    super(new InventoryModel(), new InventoryRenderer());
    initialSort(0, false);
  }

  @Override
  protected void createColumns(final Object... params) {
    final CoreConfiguration c = Client.config();
    createColumn("Produto", c.TableColumnWidth_Inventory_Product,
        c.TableColumnWidth_Default);
    createColumn("Qtd", c.TableColumnWidth_Inventory_Quantity,
        c.TableColumnWidth_Default);
    createColumn("Un", c.TableColumnWidth_Inventory_Unit,
        c.TableColumnWidth_Default);
  }

  @Override
  protected boolean deleteClicked(final int viewRow, final int tableColumn) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public String getNewEntryText(final int columnIndex) {
    return null;
  }

  @Override
  public InventoryItem getSelected() {
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
