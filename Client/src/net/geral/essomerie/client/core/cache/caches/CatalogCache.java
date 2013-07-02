package net.geral.essomerie.client.core.cache.caches;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;

import net.geral.essomerie.client.core.Client;
import net.geral.essomerie.client.core.events.Events;
import net.geral.essomerie.shared.catalog.Catalog;
import net.geral.essomerie.shared.catalog.CatalogGroup;
import net.geral.essomerie.shared.catalog.CatalogItem;
import net.geral.essomerie.shared.catalog.CatalogPublication;

import org.apache.log4j.Logger;

public class CatalogCache {
  private static final Logger             logger          = Logger
                                                              .getLogger(CatalogCache.class);

  private final HashMap<Integer, Catalog> catalogs        = new HashMap<>();
  private ArrayList<CatalogPublication>   publications    = null;
  private int                             lastPublication = -1;

  public synchronized Catalog fetch(final int publication) {
    preload(publication);
    return Catalog.createCopy(catalogs.get(publication));
  }

  public synchronized Catalog fetchLatest() {
    preloadLatest();
    if (lastPublication == -1) {
      return Catalog.createEmpty();
    }

    final Catalog c = fetch(lastPublication);
    return (c == null) ? Catalog.createEmpty() : c;
  }

  public synchronized Catalog fetchUnpublised() {
    final Catalog c = fetch(0);
    return (c == null) ? Catalog.createEmpty() : c;
  }

  public synchronized ArrayList<CatalogPublication> getPublications() {
    preloadPublications();
    final ArrayList<CatalogPublication> list = new ArrayList<>();
    list.addAll(publications);
    return list;
  }

  public synchronized void informCatalogPublication(final Catalog catalog) {
    if (catalog == null) {
      throw new InvalidParameterException("catalog cannot be null.");
    }
    final int idpublication = catalog.getIdPublication();
    final boolean latest = (idpublication == lastPublication);
    catalogs.put(idpublication, catalog);
    Events.catalog().fireCatalogPublicationReceived(latest, idpublication);
  }

  public synchronized void informCatalogPublished(
      final CatalogPublication publication) {
    if (publications != null) {
      publications.add(publication);
    }
    Events.catalog().fireCatalogPublished(publication);
  }

  public synchronized void informCreatedGroup(final CatalogGroup group) {
    final Catalog catalog = catalogs.get(group.getIdPublication());
    if (catalog == null) {
      return;
    }
    catalog.addGroup(group);
    Events.catalog().fireGroupAdded(group);
  }

  public synchronized void informCreatedItem(final CatalogItem item) {
    final Catalog catalog = catalogs.get(item.getIdPublication());
    if (catalog == null) {
      return;
    }
    catalog.addItem(item);
    Events.catalog().fireItemAdded(item);
  }

  public void informCreateGroupSuccessful(final int idgroup) {
    Events.catalog().fireCreateGroupSuccessful(idgroup);
  }

  public void informCreateItemSuccessful(final int iditem) {
    Events.catalog().fireCreateItemSuccessful(iditem);
  }

  public synchronized void informGroupSaved(final int oldId,
      final CatalogGroup group) {
    final Catalog p = catalogs.get(group.getIdPublication());
    if (p != null) {
      p.replaceGroup(oldId, group);
    }
    Events.catalog().fireGroupSaved(oldId, group);
  }

  public synchronized void informItemSaved(final int oldId,
      final CatalogItem item) {
    final Catalog p = catalogs.get(item.getIdPublication());
    if (p != null) {
      p.replaceItem(oldId, item);
    }
    Events.catalog().fireItemSaved(oldId, item);
  }

  public synchronized void informLatestPublishId(final int newId) {
    if (lastPublication != newId) {
      final int oldId = lastPublication;
      lastPublication = newId;
      preload(newId);
      Events.catalog().fireLatestPublishedIdChanged(oldId, newId);
    }
  }

  public synchronized void informPublicationList(
      final CatalogPublication[] catalogPublications) {
    publications.clear();
    for (final CatalogPublication cp : catalogPublications) {
      publications.add(cp);
    }
    Events.catalog().firePublicationListReceived();
  }

  public void informPublishSuccessful(final int idpublication) {
    Events.catalog().firePublishSuccessful(idpublication);
  }

  public synchronized void informRemovedGroups(final int idpublication,
      final ArrayList<Integer> ids) {
    final Catalog p = catalogs.get(idpublication);
    if (p != null) {
      p.removeGroups(idpublication, ids);
    }
    Events.catalog().fireRemovedGroups(idpublication, ids);
  }

  public synchronized void informRemovedItems(final int idpublication,
      final ArrayList<Integer> ids) {
    final Catalog p = catalogs.get(idpublication);
    if (p != null) {
      p.removeItems(idpublication, ids);
    }
    Events.catalog().fireRemovedItems(idpublication, ids);
  }

  public synchronized void preload(final int idpublication) {
    // TODO avoid multiple request (once id requested, do not re-request)
    if (idpublication < 0) {
      logger.warn("Invalid catalog to preload: " + idpublication);
    }
    if (catalogs.get(idpublication) == null) {
      try {
        Client.connection().catalog().requestCatalogPublication(idpublication);
      } catch (final IOException e) {
        logger.warn(e, e);
      }
    }
  }

  public synchronized void preloadLatest() {
    // TODO avoid multiple request (once requested, do not re-request)
    if (lastPublication > 0) {
      // already has last publication, already preloaded.
      return;
    }
    try {
      Client.connection().catalog().requestLatestPublishId();
    } catch (final IOException e) {
      logger.warn(e, e);
    }
  }

  public synchronized void preloadPublications() {
    if (publications != null) {
      return;
    }
    publications = new ArrayList<>(); // requesting, set to zero
    try {
      Client.connection().catalog().requestPublicationList();
    } catch (final IOException e) {
      logger.warn(e, e);
    }
  }
}
