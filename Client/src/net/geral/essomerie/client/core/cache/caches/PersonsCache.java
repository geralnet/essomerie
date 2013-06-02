package net.geral.essomerie.client.core.cache.caches;

import java.io.IOException;
import java.util.HashMap;

import net.geral.essomerie.client.core.Client;
import net.geral.essomerie.client.core.events.Events;
import net.geral.essomerie.shared.person.Addresses;
import net.geral.essomerie.shared.person.Person;
import net.geral.essomerie.shared.person.PersonData;
import net.geral.essomerie.shared.person.PersonFullData;
import net.geral.essomerie.shared.person.PersonSaleExtended;
import net.geral.essomerie.shared.person.Telephones;

import org.apache.log4j.Logger;

public class PersonsCache {
  private static final Logger            logger         = Logger
                                                            .getLogger(PersonsCache.class);

  private final HashMap<Integer, Person> persons        = new HashMap<>();
  private boolean                        loaded         = false;
  private boolean                        fullDataLoaded = false;

  public synchronized int count() {
    preload();
    return persons.size();
  }

  public synchronized void fullDataRequired() {
    if (fullDataLoaded) {
      return; // already loaded
    }
    try {
      Client.connection().persons().requestFullData();
      fullDataLoaded = true;
      loaded = true;
    } catch (final IOException e) {
      logger.warn(e, e);
    }
  }

  public synchronized Person get(final int id) {
    preload();
    final Person p = persons.get(id);
    if (!(p instanceof PersonData)) {
      // fetch full data
      try {
        Client.connection().persons().requestPersonData(id);
      } catch (final IOException e) {
        logger.warn(e, e);
      }
    }
    return p;
  }

  public synchronized Person[] getAll() {
    preload();
    final Person[] ps = new Person[persons.size()];
    int i = 0;
    for (final Person p : persons.values()) {
      ps[i++] = p;
    }
    return ps;
  }

  public synchronized void informDeleted(final int idperson) {
    // remove
    if (persons.remove(idperson) == null) {
      logger.warn("Could not remove, id: " + idperson);
    }
    // fire
    Events.persons().fireDeleted(idperson);
  }

  public synchronized void informFullData(final PersonFullData data) {
    persons.clear();
    for (final PersonData p : data.getPersons()) {
      final int pid = p.getId();
      final Telephones telephones = data.getTelephones(pid);
      final Addresses addresses = data.getAddresses(pid);
      final PersonData pd = p.withTelephonesAddresses(telephones, addresses);
      persons.put(p.getId(), pd);
    }
    Events.persons().fireCacheReloaded(true);
  }

  public synchronized void informList(final Person[] list) {
    persons.clear();
    for (final Person p : list) {
      persons.put(p.getId(), p);
    }
    Events.persons().fireCacheReloaded(false);
  }

  public synchronized void informPersonData(final PersonData p) {
    // remove
    if (persons.remove(p.getId()) == null) {
      logger.warn("Could not remove old value when changing, id: " + p.getId());
    }
    // add
    persons.put(p.getId(), p);
    // fire
    Events.persons().firePersonDataInformed(p);
  }

  public synchronized void informSaved(final Person p) {
    // remove if exists
    persons.remove(p.getId());
    // add
    persons.put(p.getId(), p);
    // fire
    Events.persons().fireSaved(p);
  }

  public void preload() {
    // it is not needed to be called directly
    // but may be called to preload in advance
    if (loaded) {
      return;
    }
    try {
      Client.connection().persons().requestList();
      loaded = true;
    } catch (final IOException e) {
      logger.warn(e, e);
    }
  }

  public void registerSale(final PersonSaleExtended sale) {
    final Person p = persons.get(sale.getIdPerson());
    if (p instanceof PersonData) {
      final PersonData pd = (PersonData) p;
      if (pd.getId() == sale.getIdPerson()) {
        pd.getSales().register(sale);
        Events.persons().fireSalesChanged(pd.getId());
      }
    }
  }
}
