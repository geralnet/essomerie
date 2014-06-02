package net.geral.essomerie.client.gui.inventory.report.item;

import net.geral.essomerie._shared.contagem.InventoryItemReportEntry;
import net.geral.lib.table.GNTableModel;

public class InventoryItemReportModel extends
    GNTableModel<InventoryItemReportEntry> {
  private static final long serialVersionUID = 1L;
  private final String[]    reasons;

  public InventoryItemReportModel(final String[] reasons) {
    super(false, false, false);
    this.reasons = reasons;
  }

  @Override
  protected InventoryItemReportEntry changeEntry(
      final InventoryItemReportEntry t, final int columnIndex,
      final Object aValue) {
    return null;
  }

  @Override
  public InventoryItemReportEntry createNewEntry() {
    return null;
  }

  @Override
  protected Object getValueFor(final InventoryItemReportEntry l,
      final int columnIndex) {
    if (columnIndex >= (reasons.length + 3)) {
      return "[" + columnIndex + "]";
    }
    switch (columnIndex) {
      case 0:
        return l.getDate();
      case 1:
        final Float f = l.getInitial();
        return (f == null) ? 0f : f;
      case 2:
        return l.getTotal();
      default:
        return l.getAmmount(reasons[columnIndex - 3]);
    }
  }
}
