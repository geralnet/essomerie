package net.geral.essomerie.client.gui.warehouse.report.item;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JScrollPane;

import net.geral.essomerie.client.gui.main.TabPanel;
import net.geral.essomerie.client.gui.shared.label.TitleLabel;
import net.geral.essomerie.client.resources.S;
import net.geral.essomerie.shared.warehouse.report.MonthlyFlowReport;

public class WarehouseItemReportTabPanel extends TabPanel {
  private static final long              serialVersionUID = 1L;
  private final MonthlyFlowReport        report;
  private final WarehouseItemReportTable table;

  public WarehouseItemReportTabPanel(final MonthlyFlowReport r) {
    setLayout(new BorderLayout(0, 0));

    final JLabel lblTitle = new TitleLabel(S.WAREHOUSE_REPORT_ITEM_TITLE.s(
        r.getItem().name, r.getItem().unit));
    add(lblTitle, BorderLayout.NORTH);

    final JScrollPane scrollPane = new JScrollPane();
    add(scrollPane, BorderLayout.CENTER);

    table = new WarehouseItemReportTable(r.getReasons());
    scrollPane.setViewportView(table);
    report = r;
  }

  @Override
  public String getTabText() {
    return S.WAREHOUSE_REPORT_ITEM_TAB.s(report.getItem().name);
  }

  @Override
  public void tabClosed() {
  }

  @Override
  public boolean tabCloseRequest() {
    return true;
  }

  @Override
  public void tabCreated() {
    table.getModel().setData(report.getReportData());
  }
}
