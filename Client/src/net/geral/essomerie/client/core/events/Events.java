package net.geral.essomerie.client.core.events;

import net.geral.essomerie.client.core.events.handlers.BulletinBoardEventHandler;
import net.geral.essomerie.client.core.events.handlers.CalendarEventHandler;
import net.geral.essomerie.client.core.events.handlers.CallerIdEventHandler;
import net.geral.essomerie.client.core.events.handlers.CatalogEventHandler;
import net.geral.essomerie.client.core.events.handlers.CommConfirmationEventHandler;
import net.geral.essomerie.client.core.events.handlers.WarehouseEventHandler;
import net.geral.essomerie.client.core.events.handlers.MessagesEventHandler;
import net.geral.essomerie.client.core.events.handlers.PersonsEventHandler;
import net.geral.essomerie.client.core.events.handlers.SalesEventHandler;
import net.geral.essomerie.client.core.events.handlers.SysopEventHandler;
import net.geral.essomerie.client.core.events.handlers.SystemEventHandler;
import net.geral.essomerie.client.core.events.handlers.UsersEventHandler;

public class Events {
  private static final BulletinBoardEventHandler    bulletinBoard = new BulletinBoardEventHandler();
  private static final CalendarEventHandler         calendar      = new CalendarEventHandler();
  private static final WarehouseEventHandler        warehouse     = new WarehouseEventHandler();
  private static final MessagesEventHandler         messages      = new MessagesEventHandler();
  private static final PersonsEventHandler          persons       = new PersonsEventHandler();
  private static final SystemEventHandler           system        = new SystemEventHandler();
  private static final UsersEventHandler            users         = new UsersEventHandler();
  private static final CallerIdEventHandler         callerid      = new CallerIdEventHandler();
  private static final CommConfirmationEventHandler comm          = new CommConfirmationEventHandler();
  private static final SalesEventHandler            sales         = new SalesEventHandler();
  private static final CatalogEventHandler          catalog       = new CatalogEventHandler();
  private static final SysopEventHandler            sysop         = new SysopEventHandler();

  public static BulletinBoardEventHandler bulletinBoard() {
    return bulletinBoard;
  }

  public static CalendarEventHandler calendar() {
    return calendar;
  }

  public static CallerIdEventHandler callerid() {
    return callerid;
  }

  public static CatalogEventHandler catalog() {
    return catalog;
  }

  public static CommConfirmationEventHandler comm() {
    return comm;
  }

  public static WarehouseEventHandler warehouse() {
    return warehouse;
  }

  public static MessagesEventHandler messages() {
    return messages;
  }

  public static PersonsEventHandler persons() {
    return persons;
  }

  public static SalesEventHandler sales() {
    return sales;
  }

  public static SysopEventHandler sysop() {
    return sysop;
  }

  public static SystemEventHandler system() {
    return system;
  }

  public static UsersEventHandler users() {
    return users;
  }
}
