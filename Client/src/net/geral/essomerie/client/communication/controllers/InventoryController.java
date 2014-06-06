package net.geral.essomerie.client.communication.controllers;

import java.io.IOException;

import net.geral.essomerie._shared.contagem.ContagemAlteracaoQuantidade;
import net.geral.essomerie._shared.contagem.Inventory;
import net.geral.essomerie._shared.contagem.InventoryGroup;
import net.geral.essomerie._shared.contagem.InventoryItemReport;
import net.geral.essomerie._shared.contagem.InventoryLog;
import net.geral.essomerie._shared.contagem.InventoryLogEntry;
import net.geral.essomerie.client.core.events.Events;
import net.geral.essomerie.shared.communication.ConnectionController;
import net.geral.essomerie.shared.communication.ICommunication;
import net.geral.essomerie.shared.communication.MessageData;
import net.geral.essomerie.shared.communication.MessageSubSystem;
import net.geral.essomerie.shared.communication.types.InventoryMessageType;

import org.apache.log4j.Logger;
import org.joda.time.LocalDate;

public class InventoryController extends
    ConnectionController<InventoryMessageType> {
  private static final Logger logger = Logger
                                         .getLogger(InventoryController.class);

  public InventoryController(final ICommunication comm) {
    super(comm, Events.system(), MessageSubSystem.Inventory);
  }

  @Override
  protected void process(final InventoryMessageType type, final MessageData md) {
    switch (type) {
      case InformFullData:
        Events.inventory().fireFullDataReceived((Inventory) md.get());
        break;
      case InformLogByItem:
        Events.inventory().fireLogByItemReceived((InventoryLog) md.get());
        break;
      case InformLogByDate:
        Events.inventory().fireLogByDateReceived((LocalDate) md.get(),
            (LocalDate) md.get(), (InventoryLogEntry[]) md.get());
        break;
      case InformQuantityChange:
        Events.inventory().fireQuantityChanged(md.getInt(), md.getFloat());
        break;
      case InformItemReport:
        Events.inventory().fireItemReportReceived(
            (InventoryItemReport) md.get());
        break;
      case InformGroups:
        Events.inventory().fireGroupsReceived((InventoryGroup[]) md.get());
        break;
      default:
        logger.warn("Invalid type: " + type.name());
    }
  }

  public void requestChangeLogByDate(final LocalDate from, final LocalDate to)
      throws IOException {
    send(InventoryMessageType.RequestLogByDate, from, to);
  }

  public void requestChangeLogByItem(final int iditem) throws IOException {
    send(InventoryMessageType.RequestLogByItem, iditem);
  }

  public void requestGroupAdd(final int parent, final String name)
      throws IOException {
    send(InventoryMessageType.RequestGroupAdd, parent, name);
  }

  public void requestGroupDelete(final int idgroup) throws IOException {
    send(InventoryMessageType.RequestGroupDelete, idgroup);
  }

  public void requestGroupParentOrderChange(final int idgroup,
      final int idparent, final int order) throws IOException {
    send(InventoryMessageType.RequestGroupParentOrderChange, idgroup, idparent,
        order);
  }

  public void requestGroupRename(final int idgroup, final String newName)
      throws IOException {
    send(InventoryMessageType.RequestGroupRename, idgroup, newName);
  }

  public void requestInventoryData() throws IOException {
    send(InventoryMessageType.RequestFullData);
  }

  public void requestItemReport(final int iditem) throws IOException {
    send(InventoryMessageType.RequestItemReport, iditem);
  }

  public void requestQuantityChange(final ContagemAlteracaoQuantidade quantity)
      throws IOException {
    send(InventoryMessageType.RequestQuantityChange, quantity);
  }
}
