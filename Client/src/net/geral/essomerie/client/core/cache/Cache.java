package net.geral.essomerie.client.core.cache;

import java.io.IOException;

import net.geral.essomerie.client.core.Client;
import net.geral.essomerie.client.core.cache.caches.BulletinBoardCache;
import net.geral.essomerie.client.core.cache.caches.CatalogCache;
import net.geral.essomerie.client.core.cache.caches.MessagesCache;
import net.geral.essomerie.client.core.cache.caches.PersonsCache;
import net.geral.essomerie.client.core.cache.caches.SalesCache;
import net.geral.essomerie.client.core.cache.caches.UsersCache;

import org.apache.log4j.Logger;

public class Cache {
  private static final Logger      logger        = Logger
                                                     .getLogger(Cache.class);

  private final BulletinBoardCache bulletinBoard = new BulletinBoardCache();
  private final MessagesCache      messages      = new MessagesCache();
  private final PersonsCache       persons       = new PersonsCache();
  private final UsersCache         users         = new UsersCache();
  private final SalesCache         sales         = new SalesCache();
  private final CatalogCache       catalog       = new CatalogCache();

  public BulletinBoardCache bulletinBoard() {
    return bulletinBoard;
  }

  public CatalogCache catalog() {
    return catalog;
  }

  public void makeInicialRequests() {
    // request initial info
    try {
      Client.connection().bulletinBoard().requestList();
      Client.connection().messages().requestRead(0);
    } catch (final IOException e) {
      logger.warn(e, e);
    }
  }

  public MessagesCache messages() {
    return messages;
  }

  public PersonsCache persons() {
    return persons;
  }

  public SalesCache sales() {
    return sales;
  }

  public UsersCache users() {
    return users;
  }
}
