package net.geral.essomerie.client.core.events.listeners;

import java.util.EventListener;

import net.geral.essomerie.shared.warehouse.Warehouse;
import net.geral.essomerie.shared.warehouse.WarehouseChangeLog;
import net.geral.essomerie.shared.warehouse.WarehouseChangeLogEntry;
import net.geral.essomerie.shared.warehouse.WarehouseGroup;
import net.geral.essomerie.shared.warehouse.WarehouseItem;
import net.geral.essomerie.shared.warehouse.report.MonthlyFlowReport;

import org.joda.time.LocalDate;

public interface WarehouseListener extends EventListener {
  public void warehouseFullDataReceived(Warehouse c);

  public void warehouseGroupsReceived(WarehouseGroup[] groups);

  public void warehouseItemChanged(WarehouseItem wi);

  public void warehouseItemCreated(int iditem);

  public void warehouseItemDeleted(int iditem);

  public void warehouseItemReportReceived(MonthlyFlowReport report);

  public void warehouseLogByDateReceived(LocalDate from, LocalDate until,
      WarehouseChangeLogEntry[] entries);

  public void warehouseLogByItemReceived(WarehouseChangeLog log);

  public void warehouseQuantityChanged(int iditem, float newQuantity);
}
