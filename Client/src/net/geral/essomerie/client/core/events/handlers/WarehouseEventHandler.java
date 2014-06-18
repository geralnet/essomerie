package net.geral.essomerie.client.core.events.handlers;

import javax.swing.event.EventListenerList;

import net.geral.essomerie.client.core.events.listeners.WarehouseListener;
import net.geral.essomerie.shared.warehouse.Warehouse;
import net.geral.essomerie.shared.warehouse.WarehouseChangeLog;
import net.geral.essomerie.shared.warehouse.WarehouseChangeLogEntry;
import net.geral.essomerie.shared.warehouse.WarehouseGroup;
import net.geral.essomerie.shared.warehouse.WarehouseItem;
import net.geral.essomerie.shared.warehouse.report.MonthlyFlowReport;
import net.geral.lib.edt.Edt;

import org.apache.log4j.Logger;
import org.joda.time.LocalDate;

public class WarehouseEventHandler extends EventListenerList {
  private static final long   serialVersionUID = 1L;
  private static final Logger logger           = Logger
                                                   .getLogger(WarehouseEventHandler.class);

  public void addListener(final WarehouseListener l) {
    add(WarehouseListener.class, l);
  }

  public void fireFullDataReceived(final Warehouse warehouse) {
    Edt.run(false, new Runnable() {
      @Override
      public void run() {
        logger.debug("Fired: fireFullDataReceived");
        for (final WarehouseListener l : getListeners(WarehouseListener.class)) {
          l.warehouseFullDataReceived(warehouse);
        }
      }
    });
  }

  public void fireGroupsReceived(final WarehouseGroup[] groups) {
    Edt.run(false, new Runnable() {
      @Override
      public void run() {
        logger.debug("Fired: fireGroupsReceived");
        for (final WarehouseListener l : getListeners(WarehouseListener.class)) {
          l.warehouseGroupsReceived(groups);
        }
      }
    });
  }

  public void fireItemChanged(final WarehouseItem wi) {
    Edt.run(false, new Runnable() {
      @Override
      public void run() {
        logger.debug("Fired: fireItemChanged");
        for (final WarehouseListener l : getListeners(WarehouseListener.class)) {
          l.warehouseItemChanged(wi);
        }
      }
    });
  }

  public void fireItemCreated(final int iditem) {
    Edt.run(false, new Runnable() {
      @Override
      public void run() {
        logger.debug("Fired: fireItemCreated");
        for (final WarehouseListener l : getListeners(WarehouseListener.class)) {
          l.warehouseItemCreated(iditem);
        }
      }
    });
  }

  public void fireItemDeleted(final int iditem) {
    Edt.run(false, new Runnable() {
      @Override
      public void run() {
        logger.debug("Fired: fireItemDeleted(int)");
        for (final WarehouseListener l : getListeners(WarehouseListener.class)) {
          l.warehouseItemDeleted(iditem);
        }
      }
    });
  }

  public void fireItemReportReceived(final MonthlyFlowReport report) {
    Edt.run(false, new Runnable() {
      @Override
      public void run() {
        logger.debug("Fired: fireItemReportReceived");
        for (final WarehouseListener l : getListeners(WarehouseListener.class)) {
          l.warehouseItemReportReceived(report);
        }
      }
    });
  }

  public void fireLogByDateReceived(final LocalDate from,
      final LocalDate until, final WarehouseChangeLogEntry[] entries) {
    Edt.run(false, new Runnable() {
      @Override
      public void run() {
        logger.debug("Fired: fireLogByDateReceived");
        for (final WarehouseListener l : getListeners(WarehouseListener.class)) {
          l.warehouseLogByDateReceived(from, until, entries);
        }
      }
    });
  }

  public void fireLogByItemReceived(final WarehouseChangeLog log) {
    Edt.run(false, new Runnable() {
      @Override
      public void run() {
        logger.debug("Fired: fireLogByItemReceived");
        for (final WarehouseListener l : getListeners(WarehouseListener.class)) {
          l.warehouseLogByItemReceived(log);
        }
      }
    });
  }

  public void fireQuantityChanged(final int iditem, final float newQuantity) {
    Edt.run(false, new Runnable() {
      @Override
      public void run() {
        logger.debug("Fired: fireQuantityChanged");
        for (final WarehouseListener l : getListeners(WarehouseListener.class)) {
          l.warehouseQuantityChanged(iditem, newQuantity);
        }
      }
    });
  }

  public void removeListener(final WarehouseListener l) {
    remove(WarehouseListener.class, l);
  }
}
