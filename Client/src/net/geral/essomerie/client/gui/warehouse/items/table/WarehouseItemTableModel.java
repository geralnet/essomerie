package net.geral.essomerie.client.gui.warehouse.items.table;

import java.io.IOException;
import java.util.ArrayList;

import net.geral.essomerie.client.core.Client;
import net.geral.essomerie.client.resources.S;
import net.geral.essomerie.shared.warehouse.WarehouseItem;
import net.geral.lib.table.GNTableModel;

import org.apache.log4j.Logger;

public class WarehouseItemTableModel extends GNTableModel<WarehouseItem> {
  private static final long serialVersionUID = 1L;
  private int               idgroup          = 0;
  private static Logger     logger           = Logger
                                                 .getLogger(WarehouseItemTableModel.class);

  public WarehouseItemTableModel() {
    super(true, true, true);
  }

  @Override
  protected WarehouseItem changeEntry(WarehouseItem wi, final int columnIndex,
      final Object aValue) {
    if (wi.id == 0) {
      return null; // wait until item is created
    }
    if (wi.id == -1) {
      wi = wi.withId(0); // creating item
    }
    switch (columnIndex) {
      case 0: // name
        wi = wi.withName(aValue.toString());
        break;
      case 1: // quantity
        return null;
      case 2: // unit
        wi = wi.withUnit(aValue.toString());
        break;
      case 3: // min
      case 4: // max
        try {
          final String sf = aValue.toString().replaceAll("\\.", "")
              .replaceAll(",", ".");
          final float f = Float.parseFloat(sf);
          if (f < 0) {
            return null;
          }
          if (columnIndex == 3) {
            wi = wi.withMinimum(f);
          }
          if (columnIndex == 4) {
            wi = wi.withMaximum(f);
          }
        } catch (final NumberFormatException e) {
          logger.warn(e);
          return null;
        }
        break;
      default:
        return null;
    }
    try {
      Client.connection().warehouse().requestChangeItem(wi);
      return wi.withName(S.WAREHOUSE_ADMIN_SAVING.s(wi.name)).withId(0);
    } catch (final IOException e) {
      logger.error(e);
      return null;
    }
  }

  @Override
  public WarehouseItem createNewEntry() {
    return new WarehouseItem(-1, idgroup, S.WAREHOUSE_ADMIN_NEWITEM_NAME.s(),
        "", 0F, 0F, 0F);
  }

  private int getRowForItemID(final int iditem) {
    final ArrayList<WarehouseItem> items = getAll();
    for (int i = 0; i < items.size(); i++) {
      if (items.get(i).id == iditem) {
        return i;
      }
    }
    return -1;
  }

  @Override
  protected Object getValueFor(final WarehouseItem wi, final int columnIndex) {
    switch (columnIndex) {
      case 0:
        return wi.name;
      case 1:
        return wi.getQuantity();
      case 2:
        return wi.unit;
      case 3:
      case 4:
        // text handled by renderer
        return wi;
      default:
        return null;
    }
  }

  @Override
  public synchronized boolean isCellEditable(final int rowIndex,
      final int columnIndex) {
    // block quantity cell
    if (columnIndex == 1) {
      return false;
    }
    return super.isCellEditable(rowIndex, columnIndex);
  }

  public void refreshItem(final int iditem) {
    final int row = getRowForItemID(iditem);
    if (row == -1) {
      return;
    }
    fireTableRowsUpdated(row, row);
  }

  public void setData(final int idgroup, final WarehouseItem[] items) {
    this.idgroup = idgroup;
    super.setData(items);
  }

  @Deprecated
  @Override
  public synchronized void setData(final WarehouseItem[] data) {
    super.setData(new WarehouseItem[0]);
  }
}
