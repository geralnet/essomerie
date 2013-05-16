package net.geral.essomerie.client.gui.inventory.logtable;

import net.geral.essomerie._shared.contagem.InventoryLog;
import net.geral.essomerie.client.core.Client;
import net.geral.essomerie.client.core.configuration.CoreConfiguration;
import net.geral.essomerie.client.gui.inventory.InventoryChangePanel;
import net.geral.lib.table.GNTable;

//TODO check & translate
public class InventoryLogTable extends GNTable<InventoryLogModel> {
  private static final long serialVersionUID = 1L;

  public InventoryLogTable(final InventoryChangePanel contagemPanel) {
    super(new InventoryLogModel(contagemPanel));
  }

  @Override
  protected void createColumns() {
    final CoreConfiguration c = Client.config();
    createColumn("Data", c.TableColumnWidth_Inventory_Log_Date,
        c.TableColumnWidth_Default_DateTimeNoSeconds);
    createColumn("Por", c.TableColumnWidth_Inventory_Log_By,
        c.TableColumnWidth_Default_Username);
    createColumn("Alteração", c.TableColumnWidth_Inventory_Log_Change,
        c.TableColumnWidth_Default);
    createColumn("Observações", c.TableColumnWidth_Inventory_Log_Comments,
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

  public void set(final InventoryLog h) {
    getModel().setData(h.getRegistros());
  }
}
