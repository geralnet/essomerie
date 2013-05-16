package net.geral.essomerie.client.core.events;

import net.geral.essomerie.client.core.events.handlers.BulletinBoardEventHandler;
import net.geral.essomerie.client.core.events.handlers.CalendarEventHandler;
import net.geral.essomerie.client.core.events.handlers.CallerIdEventHandler;
import net.geral.essomerie.client.core.events.handlers.CommConfirmationEventHandler;
import net.geral.essomerie.client.core.events.handlers.InventoryEventHandler;
import net.geral.essomerie.client.core.events.handlers.MessagesEventHandler;
import net.geral.essomerie.client.core.events.handlers.PersonsEventHandler;
import net.geral.essomerie.client.core.events.handlers.SystemEventHandler;
import net.geral.essomerie.client.core.events.handlers.UsersEventHandler;

public class Events {
  private static final BulletinBoardEventHandler    bulletinBoard = new BulletinBoardEventHandler();
  private static final CalendarEventHandler         calendar      = new CalendarEventHandler();
  private static final InventoryEventHandler        inventory     = new InventoryEventHandler();
  private static final MessagesEventHandler         messages      = new MessagesEventHandler();
  private static final PersonsEventHandler          persons       = new PersonsEventHandler();
  private static final SystemEventHandler           system        = new SystemEventHandler();
  private static final UsersEventHandler            users         = new UsersEventHandler();
  private static final CallerIdEventHandler         callerid      = new CallerIdEventHandler();
  private static final CommConfirmationEventHandler comm          = new CommConfirmationEventHandler();

  public static BulletinBoardEventHandler bulletinBoard() {
    return bulletinBoard;
  }

  public static CalendarEventHandler calendar() {
    return calendar;
  }

  public static CallerIdEventHandler callerid() {
    return callerid;
  }

  public static CommConfirmationEventHandler comm() {
    return comm;
  }

  public static InventoryEventHandler inventory() {
    return inventory;
  }

  public static MessagesEventHandler messages() {
    return messages;
  }

  public static PersonsEventHandler persons() {
    return persons;
  }

  public static SystemEventHandler system() {
    return system;
  }

  public static UsersEventHandler users() {
    return users;
  }
}
