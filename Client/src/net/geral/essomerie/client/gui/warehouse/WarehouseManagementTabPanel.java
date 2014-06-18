package net.geral.essomerie.client.gui.warehouse;

import java.awt.BorderLayout;
import java.awt.print.PrinterException;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.geral.essomerie.client._printing.ResumoAcertosPrint;
import net.geral.essomerie.client._printing.WarehouseChecklistPrint;
import net.geral.essomerie.client.core.Client;
import net.geral.essomerie.client.core.events.Events;
import net.geral.essomerie.client.core.events.listeners.WarehouseListener;
import net.geral.essomerie.client.gui.main.TabPanel;
import net.geral.essomerie.client.gui.shared.label.TitleLabel;
import net.geral.essomerie.client.gui.warehouse.groups.WarehouseGroupsPanel;
import net.geral.essomerie.client.gui.warehouse.items.WarehouseItemsPanel;
import net.geral.essomerie.client.gui.warehouse.items.table.WarehouseTable;
import net.geral.essomerie.client.gui.warehouse.report.item.WarehouseItemReportTabPanel;
import net.geral.essomerie.client.resources.S;
import net.geral.essomerie.shared.warehouse.Warehouse;
import net.geral.essomerie.shared.warehouse.WarehouseChangeLog;
import net.geral.essomerie.shared.warehouse.WarehouseChangeLogEntry;
import net.geral.essomerie.shared.warehouse.WarehouseGroup;
import net.geral.essomerie.shared.warehouse.WarehouseItem;
import net.geral.essomerie.shared.warehouse.report.MonthlyFlowReport;
import net.geral.lib.printing.PrintSupport;

import org.apache.log4j.Logger;
import org.joda.time.LocalDate;

public class WarehouseManagementTabPanel extends TabPanel implements
    WarehouseListener, ListSelectionListener {

  private static final Logger        logger           = Logger
                                                          .getLogger(WarehouseManagementTabPanel.class);
  private static final long          serialVersionUID = 1L;

  private final WarehouseTable       warehouseTable   = new WarehouseTable();
  private final WarehouseItemsPanel  itemsPanel;
  private final WarehouseGroupsPanel groupsPanel;
  private Warehouse                  warehouse        = new Warehouse();
  private WarehouseGroup             lastGroup        = null;

  public WarehouseManagementTabPanel() {
    setLayout(new BorderLayout(0, 0));

    final JLabel lblTitle = new TitleLabel(S.WAREHOUSE_ADJUSTMENT_TITLE.s());
    add(lblTitle, BorderLayout.NORTH);

    final JPanel mainPanel = new JPanel();
    add(mainPanel, BorderLayout.CENTER);
    mainPanel.setLayout(new BorderLayout(0, 0));

    groupsPanel = new WarehouseGroupsPanel(this);
    mainPanel.add(groupsPanel, BorderLayout.WEST);

    itemsPanel = new WarehouseItemsPanel(warehouseTable);
    warehouseTable.getSelectionModel().addListSelectionListener(this);
    final JPanel warehouseTablePanel = new JPanel();
    warehouseTablePanel.setLayout(new BorderLayout());
    warehouseTablePanel.add(warehouseTable.getScroll(), BorderLayout.CENTER);

    final JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
        warehouseTablePanel, itemsPanel);
    splitPane.setResizeWeight(0.9);
    mainPanel.add(splitPane, BorderLayout.CENTER);
  }

  //
  // @Override
  // public void contagemHistoricoRecebido(final LocalDate de, final LocalDate
  // ate,
  // final ContagemHistoricoRegistro[] registros) {
  // if (de.equals(imprimir_de) && ate.equals(imprimir_ate)) {
  // imprimirAcertosGo(registros);
  // }
  // }

  @Override
  public String getTabText() {
    return S.WAREHOUSE_TITLE.s();
  }

  public Warehouse getWarehouse() {
    return warehouse;
  }

  public void groupChanged() {
    final WarehouseGroup group = groupsPanel.getSelectedGroup();
    if (lastGroup == group) {
      return;
    }
    lastGroup = group;

    final int selected = warehouseTable.getSelectedRow();
    warehouseTable.getSelectionModel().setSelectionInterval(selected, selected);
    warehouseTable.getModel().setData(group.id, warehouse.getItems(group));
  }

  public void printChecklist() {
    final WarehouseChecklistPrint p = new WarehouseChecklistPrint(
        warehouse.getItems(groupsPanel.getSelectedGroup()));
    try {
      PrintSupport.print(p);
    } catch (final PrinterException e) {
      logger.warn(e, e);
    }
  }

  public void printLog() {
    final LocalDate printFrom = LocalDate.now().minusDays(1);
    final LocalDate printUntil = LocalDate.now();
    try {
      Client.connection().warehouse()
          .requestChangeLogByDate(printFrom, printUntil);
    } catch (final IOException e) {
      logger.warn(e, e);
    }
  }

  public void setAllowChanges(final boolean allow) {
    warehouseTable.setEditable(allow);
  }

  public void setWarehouse(final Warehouse i) {
    warehouse = i;
    groupsPanel.updateGroups(i.getGroups());
    itemsPanel.setReasons(i.getReasons());
  }

  @Override
  public void tabClosed() {
    Events.warehouse().removeListener(this);
  }

  @Override
  public boolean tabCloseRequest() {
    return true;
  }

  @Override
  public void tabCreated() {
    Events.warehouse().addListener(this);
    setAllowChanges(false);
    try {
      Client.connection().warehouse().requestWarehouseData();
    } catch (final IOException e) {
      logger.warn(e, e);
    }
  }

  @Override
  public void valueChanged(final ListSelectionEvent e) {
    if (e.getValueIsAdjusting()) {
      return;
    }
    itemsPanel.setItem(warehouseTable.getSelected());
    warehouseTable.scrollToSelected();
  }

  @Override
  public void warehouseFullDataReceived(final Warehouse c) {
    setWarehouse(c);
  }

  @Override
  public void warehouseGroupsReceived(final WarehouseGroup[] groups) {
    groupsPanel.updateGroups(groups);
  }

  @Override
  public void warehouseItemChanged(final WarehouseItem wi) {
    warehouse.changeItem(wi);
    lastGroup = null;
    groupChanged(); // force refresh
  }

  @Override
  public void warehouseItemCreated(final int iditem) {
    // do nothing (wait for changed event)
  }

  @Override
  public void warehouseItemDeleted(final int iditem) {
    warehouse.removeItem(iditem);
    lastGroup = null;
    groupChanged(); // force refresh
  }

  @Override
  public void warehouseItemReportReceived(final MonthlyFlowReport report) {
    Client.window().openTab(new WarehouseItemReportTabPanel(report));
  }

  @Override
  public void warehouseLogByDateReceived(final LocalDate from,
      final LocalDate until, final WarehouseChangeLogEntry[] entries) {
    final ResumoAcertosPrint p = new ResumoAcertosPrint(entries, warehouse);
    try {
      PrintSupport.print(p);
    } catch (final PrinterException e) {
      logger.warn(e, e);
    }
  }

  @Override
  public void warehouseLogByItemReceived(final WarehouseChangeLog log) {
    final WarehouseItem ii = warehouseTable.getSelected();
    if (ii == null) {
      return;
    }
    if (ii.id == log.iditem) {
      itemsPanel.setChangeLog(log);
    }
  }

  @Override
  public void warehouseQuantityChanged(final int iditem, final float newQuantity) {
    warehouse.setNewQuantity(iditem, newQuantity);
    warehouseTable.getModel().refreshItem(iditem);
  }
}
