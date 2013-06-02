package net.geral.essomerie.client.core.cache.caches;

import java.io.IOException;
import java.util.ArrayList;

import net.geral.essomerie.client.core.Client;
import net.geral.essomerie.client.core.events.Events;
import net.geral.essomerie.shared.person.PersonSaleExtended;

import org.apache.log4j.Logger;

public class SalesCache {
  private static final Logger                 logger = Logger
                                                         .getLogger(SalesCache.class);

  private final ArrayList<PersonSaleExtended> sales  = new ArrayList<>();

  public synchronized int count() {
    preload();
    return sales.size();
  }

  public synchronized PersonSaleExtended[] getAll() {
    preload();
    return sales.toArray(new PersonSaleExtended[sales.size()]);
  }

  public synchronized PersonSaleExtended getByIndex(final int index) {
    preload();
    return sales.get(index);
  }

  public synchronized void informLatest(final int entriesRequested,
      final PersonSaleExtended[] entries) {
    final int entriesReceived = entries.length;
    sales.clear();
    for (final PersonSaleExtended pse : entries) {
      sales.add(pse);
    }
    Events.sales().fireCacheReloaded(entriesRequested, entriesReceived);
  }

  public synchronized void informRegistered(final PersonSaleExtended sale) {
    sales.add(sale);
    Client.cache().persons().registerSale(sale);
    Events.sales().fireRegistered(sale);
  }

  public synchronized void preload() {
    // it is not needed to be called directly
    // but may be called to preload in advance
    if (sales.size() > 0) {
      return;
    }
    try {
      Client.connection().sales()
          .requestLatest(Client.config().SalesCachePreloadEntries);
    } catch (final IOException e) {
      logger.warn(e, e);
    }
  }
}
