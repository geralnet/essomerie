package net.geral.essomerie.client.core.events.handlers;

import javax.swing.event.EventListenerList;

import net.geral.essomerie._shared.contagem.Inventory;
import net.geral.essomerie._shared.contagem.InventoryItemReport;
import net.geral.essomerie._shared.contagem.InventoryLog;
import net.geral.essomerie._shared.contagem.InventoryLogEntry;
import net.geral.essomerie.client.core.events.listeners.InventoryListener;
import net.geral.lib.edt.Edt;

import org.apache.log4j.Logger;
import org.joda.time.LocalDate;

public class InventoryEventHandler extends EventListenerList {
  private static final long   serialVersionUID = 1L;
  private static final Logger logger           = Logger
                                                   .getLogger(InventoryEventHandler.class);

  public void addListener(final InventoryListener l) {
    add(InventoryListener.class, l);
  }

  public void fireFullDataReceived(final Inventory inventory) {
    Edt.run(false, new Runnable() {
      @Override
      public void run() {
        logger.debug("Fired: fireFullDataReceived");
        for (final InventoryListener l : getListeners(InventoryListener.class)) {
          l.inventoryFullDataReceived(inventory);
        }
      }
    });
  }

  public void fireItemReportReceived(final InventoryItemReport report) {
    Edt.run(false, new Runnable() {
      @Override
      public void run() {
        logger.debug("Fired: fireItemReportReceived");
        for (final InventoryListener l : getListeners(InventoryListener.class)) {
          l.inventoryItemReportReceived(report);
        }
      }
    });
  }

  public void fireLogByDateReceived(final LocalDate from,
      final LocalDate until, final InventoryLogEntry[] entries) {
    Edt.run(false, new Runnable() {
      @Override
      public void run() {
        logger.debug("Fired: fireLogByDateReceived");
        for (final InventoryListener l : getListeners(InventoryListener.class)) {
          l.inventoryLogByDateReceived(from, until, entries);
        }
      }
    });
  }

  public void fireLogByItemReceived(final InventoryLog log) {
    Edt.run(false, new Runnable() {
      @Override
      public void run() {
        logger.debug("Fired: fireLogByItemReceived");
        for (final InventoryListener l : getListeners(InventoryListener.class)) {
          l.inventoryLogByItemReceived(log);
        }
      }
    });
  }

  public void fireQuantityChanged(final int iditem, final float newQuantity) {
    Edt.run(false, new Runnable() {
      @Override
      public void run() {
        logger.debug("Fired: fireQuantityChanged");
        for (final InventoryListener l : getListeners(InventoryListener.class)) {
          l.inventoryQuantityChanged(iditem, newQuantity);
        }
      }
    });
  }

  public void removeListener(final InventoryListener l) {
    remove(InventoryListener.class, l);
  }
}
