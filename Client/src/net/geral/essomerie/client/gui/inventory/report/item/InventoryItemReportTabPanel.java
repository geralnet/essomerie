package net.geral.essomerie.client.gui.inventory.report.item;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JScrollPane;

import net.geral.essomerie._shared.contagem.InventoryItemReport;
import net.geral.essomerie.client.gui.main.TabPanel;
import net.geral.essomerie.client.gui.shared.label.TitleLabel;

public class InventoryItemReportTabPanel extends TabPanel {
  private static final long              serialVersionUID = 1L;
  private final InventoryItemReport      report;
  private final InventoryItemReportTable table;

  public InventoryItemReportTabPanel(final InventoryItemReport r) {
    setLayout(new BorderLayout(0, 0));

    final JLabel lblTitle = new TitleLabel("Relatório de Entrada e Saída: "
        + r.getItem().nome + " (" + r.getItem().unidade + ")");
    add(lblTitle, BorderLayout.NORTH);

    final JScrollPane scrollPane = new JScrollPane();
    add(scrollPane, BorderLayout.CENTER);

    table = new InventoryItemReportTable(r.getReasons());
    scrollPane.setViewportView(table);
    report = r;
  }

  @Override
  public String getTabText() {
    return "Relatório: " + report.getItem().nome;
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
