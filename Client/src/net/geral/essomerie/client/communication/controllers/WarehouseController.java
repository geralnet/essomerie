package net.geral.essomerie.client.communication.controllers;

import java.io.IOException;

import net.geral.essomerie.client.core.events.Events;
import net.geral.essomerie.shared.communication.ConnectionController;
import net.geral.essomerie.shared.communication.ICommunication;
import net.geral.essomerie.shared.communication.MessageData;
import net.geral.essomerie.shared.communication.MessageSubSystem;
import net.geral.essomerie.shared.communication.types.WarehouseMessageType;
import net.geral.essomerie.shared.warehouse.Warehouse;
import net.geral.essomerie.shared.warehouse.WarehouseChangeLog;
import net.geral.essomerie.shared.warehouse.WarehouseChangeLogEntry;
import net.geral.essomerie.shared.warehouse.WarehouseGroup;
import net.geral.essomerie.shared.warehouse.WarehouseItem;
import net.geral.essomerie.shared.warehouse.WarehouseQuantityChange;
import net.geral.essomerie.shared.warehouse.report.MonthlyFlowReport;

import org.apache.log4j.Logger;
import org.joda.time.LocalDate;

public class WarehouseController extends
    ConnectionController<WarehouseMessageType> {
  private static final Logger logger = Logger
                                         .getLogger(WarehouseController.class);

  public WarehouseController(final ICommunication comm) {
    super(comm, Events.system(), MessageSubSystem.Warehouse);
  }

  @Override
  protected void process(final WarehouseMessageType type, final MessageData md) {
    switch (type) {
      case InformFullData:
        Events.warehouse().fireFullDataReceived((Warehouse) md.get());
        break;
      case InformLogByItem:
        Events.warehouse().fireLogByItemReceived((WarehouseChangeLog) md.get());
        break;
      case InformLogByDate:
        Events.warehouse().fireLogByDateReceived((LocalDate) md.get(),
            (LocalDate) md.get(), (WarehouseChangeLogEntry[]) md.get());
        break;
      case InformQuantityChange:
        Events.warehouse().fireQuantityChanged(md.getInt(), md.getFloat());
        break;
      case InformItemReport:
        Events.warehouse().fireItemReportReceived((MonthlyFlowReport) md.get());
        break;
      case InformGroups:
        Events.warehouse().fireGroupsReceived((WarehouseGroup[]) md.get());
        break;
      case InformItemDeleted:
        Events.warehouse().fireItemDeleted(md.getInt());
        break;
      case InformItemCreated:
        Events.warehouse().fireItemCreated(md.getInt());
        break;
      case InformChangeItem:
        Events.warehouse().fireItemChanged((WarehouseItem) md.get());
        break;
      default:
        logger.warn("Invalid type: " + type.name());
    }
  }

  public void requestChangeItem(final WarehouseItem wi) throws IOException {
    send(WarehouseMessageType.RequestChangeItem, wi);
  }

  public void requestChangeLogByDate(final LocalDate from, final LocalDate to)
      throws IOException {
    send(WarehouseMessageType.RequestLogByDate, from, to);
  }

  public void requestChangeLogByItem(final int iditem) throws IOException {
    send(WarehouseMessageType.RequestLogByItem, iditem);
  }

  public void requestDeleteItem(final int iditem) throws IOException {
    send(WarehouseMessageType.RequestDeleteItem, iditem);
  }

  public void requestGroupAdd(final int parent, final String name)
      throws IOException {
    send(WarehouseMessageType.RequestGroupAdd, parent, name);
  }

  public void requestGroupDelete(final int idgroup) throws IOException {
    send(WarehouseMessageType.RequestGroupDelete, idgroup);
  }

  public void requestGroupParentOrderChange(final int idgroup,
      final int idparent, final int order) throws IOException {
    send(WarehouseMessageType.RequestGroupParentOrderChange, idgroup, idparent,
        order);
  }

  public void requestGroupRename(final int idgroup, final String newName)
      throws IOException {
    send(WarehouseMessageType.RequestGroupRename, idgroup, newName);
  }

  public void requestItemReport(final int iditem) throws IOException {
    send(WarehouseMessageType.RequestItemReport, iditem);
  }

  public void requestQuantityChange(final WarehouseQuantityChange quantity)
      throws IOException {
    send(WarehouseMessageType.RequestQuantityChange, quantity);
  }

  public void requestWarehouseData() throws IOException {
    send(WarehouseMessageType.RequestFullData);
  }
}
