package net.geral.essomerie.shared.communication;

import net.geral.essomerie.shared.communication.types.BulletinBoardMessageType;
import net.geral.essomerie.shared.communication.types.CalendarMessageType;
import net.geral.essomerie.shared.communication.types.CallerIdMessageType;
import net.geral.essomerie.shared.communication.types.CatalogMessageType;
import net.geral.essomerie.shared.communication.types.InventoryMessageType;
import net.geral.essomerie.shared.communication.types.MessagesMessageType;
import net.geral.essomerie.shared.communication.types.PersonsMessageType;
import net.geral.essomerie.shared.communication.types.SalesMessageType;
import net.geral.essomerie.shared.communication.types.SysopMessageType;
import net.geral.essomerie.shared.communication.types.SystemMessageType;
import net.geral.essomerie.shared.communication.types.UsersMessageType;

public enum MessageSubSystem {
  BulletinBoard,
  Calendar,
  CallerId,
  Catalog,
  Inventory,
  Messages,
  Persons,
  Sales,
  System,
  Users,
  Sysop;

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
      case Sysop:
        return SysopMessageType.values();
      default:
        return null;
    }
  }
}
