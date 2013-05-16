package net.geral.essomerie.client.gui.inventory.table;

import java.util.ArrayList;

import net.geral.essomerie._shared.contagem.InventoryItem;
import net.geral.lib.table.GNTableModel;

//TODO check & translate
public class InventoryModel extends GNTableModel<InventoryItem> {
  private static final long serialVersionUID = 1L;

  public InventoryModel() {
    super(false, false, false);
  }

  @Override
  protected InventoryItem changeEntry(final InventoryItem t,
      final int columnIndex, final Object aValue) {
    return null;
  }

  @Override
  public InventoryItem createNewEntry() {
    return null;
  }

  private int getRowForItemID(final int iditem) {
    final ArrayList<InventoryItem> items = getAll();
    for (int i = 0; i < items.size(); i++) {
      if (items.get(i).id == iditem) {
        return i;
      }
    }
    return -1;
  }

  @Override
  protected Object getValueFor(final InventoryItem ii, final int columnIndex) {
    switch (columnIndex) {
      case 0:
        return ii.nome;
      case 1:
        return ii.getQuantidade();
      case 2:
        return ii.unidade;
      default:
        return null;
    }
  }

  public void refreshItem(final int iditem) {
    final int row = getRowForItemID(iditem);
    if (row == -1) {
      return;
    }
    fireTableRowsUpdated(row, row);
  }
}
