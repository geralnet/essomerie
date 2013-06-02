package net.geral.essomerie.client.core.events.listeners;

import java.util.EventListener;

import net.geral.essomerie.shared.person.PersonSaleExtended;

public interface SalesListener extends EventListener {
  void salesCacheReloaded(int requested, int received);

  void salesRegistered(PersonSaleExtended sale);
}
