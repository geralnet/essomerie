package net.geral.essomerie.client.core.events.listeners;

import java.util.EventListener;

import net.geral.essomerie._shared.contagem.Inventory;
import net.geral.essomerie._shared.contagem.InventoryItemReport;
import net.geral.essomerie._shared.contagem.InventoryLog;
import net.geral.essomerie._shared.contagem.InventoryLogEntry;

import org.joda.time.LocalDate;

public interface InventoryListener extends EventListener {
  public void inventoryFullDataReceived(Inventory c);

  public void inventoryItemReportReceived(InventoryItemReport report);

  public void inventoryLogByDateReceived(LocalDate from, LocalDate until,
      InventoryLogEntry[] entries);

  public void inventoryLogByItemReceived(InventoryLog log);

  public void inventoryQuantityChanged(int iditem, float newQuantity);
}
