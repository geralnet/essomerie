package net.geral.essomerie.client.core.events.listeners;

import java.util.ArrayList;
import java.util.EventListener;

import net.geral.essomerie.shared.catalog.CatalogGroup;
import net.geral.essomerie.shared.catalog.CatalogItem;
import net.geral.essomerie.shared.catalog.CatalogPublication;

public interface CatalogListener extends EventListener {
  void catalogCreateGroupSuccessful(int idgroup);

  void catalogCreateItemSuccessful(int iditem);

  void catalogGroupAdded(CatalogGroup group);

  void catalogGroupSaved(int oldId, CatalogGroup group);

  void catalogItemAdded(CatalogItem item);

  void catalogItemSaved(int oldId, CatalogItem item);

  void catalogLatestPublishedIdChanged(int oldId, int newId);

  void catalogPublicationListReceived();

  void catalogPublicationReceived(boolean latest, int idpublication);

  void catalogPublished(CatalogPublication publication);

  void catalogPublishSuccessful(int idpublication);

  void catalogRemovedGroups(int idpublication, ArrayList<Integer> ids);

  void catalogRemovedItems(int idpublication, ArrayList<Integer> ids);
}
