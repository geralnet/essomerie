package net.geral.essomerie.client.gui.warehouse.logtable;

import net.geral.essomerie.client.core.Client;
import net.geral.essomerie.client.gui.warehouse.items.WarehouseItemsPanel;
import net.geral.essomerie.client.resources.S;
import net.geral.essomerie.shared.warehouse.WarehouseChangeLogEntry;
import net.geral.lib.table.GNTableModel;

import org.joda.time.format.DateTimeFormat;

public class WarehouseLogModel extends GNTableModel<WarehouseChangeLogEntry> {
  private static final long         serialVersionUID = 1L;
  private final WarehouseItemsPanel changePanel;

  public WarehouseLogModel(final WarehouseItemsPanel panel) {
    super(false, false, false);
    changePanel = panel;
  }

  @Override
  protected WarehouseChangeLogEntry changeEntry(
      final WarehouseChangeLogEntry t, final int columnIndex,
      final Object aValue) {
    return null;
  }

  @Override
  public WarehouseChangeLogEntry createNewEntry() {
    return null;
  }

  @Override
  protected Object getValueFor(final WarehouseChangeLogEntry log,
      final int columnIndex) {
    switch (columnIndex) {
      case 0:
        return log.when.toString(DateTimeFormat
            .forPattern(S.FORMAT_DATETIME_SIMPLE_NO_SECONDS.s()));
      case 1:
        return Client.cache().users().get(log.iduser).getUsername()
            .toUpperCase();
      case 2:
        return log.toString();
      case 3:
        final String reason = changePanel.getReason(log.mode, log.idreason);
        if (log.comments.length() == 0) {
          return reason;
        }
        return reason + ": " + log.comments;
      default:
        return "[" + columnIndex + "]";
    }
  }
}
