package net.geral.essomerie._shared.communication;

import net.geral.essomerie._shared.communication.types.BulletinBoardMessageType;
import net.geral.essomerie._shared.communication.types.CalendarMessageType;
import net.geral.essomerie._shared.communication.types.CallerIdMessageType;
import net.geral.essomerie._shared.communication.types.CatalogMessageType;
import net.geral.essomerie._shared.communication.types.InventoryMessageType;
import net.geral.essomerie._shared.communication.types.MessagesMessageType;
import net.geral.essomerie._shared.communication.types.PersonsMessageType;
import net.geral.essomerie._shared.communication.types.SalesMessageType;
import net.geral.essomerie._shared.communication.types.SystemMessageType;
import net.geral.essomerie._shared.communication.types.UsersMessageType;

public enum MessageSubSystem {
  System,
  Messages,
  BulletinBoard,
  Calendar,
  Inventory,
  Persons,
  Custommers,
  Delivery,
  SystemAdministration,
  Employees,
  Products,
  Users,
  CallerId,
  Sales,
  Catalog;

  public static IMessageType[] getMessages(final MessageSubSystem mss) {
    switch (mss) {
      case BulletinBoard:
        return BulletinBoardMessageType.values();
      case Calendar:
        return CalendarMessageType.values();
      case CallerId:
        return CallerIdMessageType.values();
      case Inventory:
        return InventoryMessageType.values();
      case Messages:
        return MessagesMessageType.values();
      case Persons:
        return PersonsMessageType.values();
      case System:
        return SystemMessageType.values();
      case Users:
        return UsersMessageType.values();
      case Sales:
        return SalesMessageType.values();
      case Catalog:
        return CatalogMessageType.values();
      case Custommers:
      case Delivery:
      case Employees:
      case Products:
      case SystemAdministration:
        return new IMessageType[0];
      default:
        return null;
    }
  }
}
