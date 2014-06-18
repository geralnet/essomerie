package net.geral.essomerie.client.gui.warehouse.report.item;

import net.geral.essomerie.shared.warehouse.report.MonthlyFlowReportEntry;
import net.geral.lib.table.GNTableModel;

public class WarehouseItemReportModel extends
    GNTableModel<MonthlyFlowReportEntry> {
  private static final long serialVersionUID = 1L;
  private final String[]    reasons;

  public WarehouseItemReportModel(final String[] reasons) {
    super(false, false, false);
    this.reasons = reasons;
  }

  @Override
  protected MonthlyFlowReportEntry changeEntry(
      final MonthlyFlowReportEntry t, final int columnIndex,
      final Object aValue) {
    return null;
  }

  @Override
  public MonthlyFlowReportEntry createNewEntry() {
    return null;
  }

  @Override
  protected Object getValueFor(final MonthlyFlowReportEntry l,
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
