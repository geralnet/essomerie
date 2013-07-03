package net.geral.essomerie.client.communication.controllers;

import java.io.IOException;
import java.util.ArrayList;

import net.geral.essomerie.client.core.Client;
import net.geral.essomerie.client.core.events.Events;
import net.geral.essomerie.shared.catalog.Catalog;
import net.geral.essomerie.shared.catalog.CatalogGroup;
import net.geral.essomerie.shared.catalog.CatalogItem;
import net.geral.essomerie.shared.catalog.CatalogPublication;
import net.geral.essomerie.shared.communication.ConnectionController;
import net.geral.essomerie.shared.communication.ICommunication;
import net.geral.essomerie.shared.communication.MessageData;
import net.geral.essomerie.shared.communication.MessageSubSystem;
import net.geral.essomerie.shared.communication.types.CatalogMessageType;

import org.apache.log4j.Logger;

public class CatalogController extends ConnectionController<CatalogMessageType> {
  private static final Logger logger = Logger
                                         .getLogger(CatalogController.class);

  public CatalogController(final ICommunication comm) {
    super(comm, Events.system(), MessageSubSystem.Catalog);
  }

  @SuppressWarnings("unchecked")
  @Override
  protected void process(final CatalogMessageType type, final MessageData md) {
    switch (type) {
      case InformLatestPublishId:
        Client.cache().catalog().informLatestPublishId(md.getInt());
        break;
      case InformCatalogPublication:
        Client.cache().catalog().informCatalogPublication((Catalog) md.get());
        break;
      case InformPublicationList:
        Client.cache().catalog()
            .informPublicationList((CatalogPublication[]) md.get());
        break;
      case InformCatalogPublished:
        Client.cache().catalog()
            .informCatalogPublished((CatalogPublication) md.get());
        break;
      case InformPublishSuccessful:
        Client.cache().catalog().informPublishSuccessful(md.getInt());
        break;
      case InformGroupSaved:
        Client.cache().catalog()
            .informGroupSaved(md.getInt(), (CatalogGroup) md.get());
        break;
      case InformItemSaved:
        Client.cache().catalog()
            .informItemSaved(md.getInt(), (CatalogItem) md.get());
        break;
      case InformRemovedGroups:
        Client.cache().catalog()
            .informRemovedGroups(md.getInt(), (ArrayList<Integer>) md.get());
        break;
      case InformRemovedItems:
        Client.cache().catalog()
            .informRemovedItems(md.getInt(), (ArrayList<Integer>) md.get());
        break;
      case InformCreatedGroup:
        Client.cache().catalog().informCreatedGroup((CatalogGroup) md.get());
        break;
      case InformCreateGroupSuccessful:
        Client.cache().catalog().informCreateGroupSuccessful(md.getInt());
        break;
      case InformCreatedItem:
        Client.cache().catalog().informCreatedItem((CatalogItem) md.get());
        break;
      case InformCreateItemSuccessful:
        Client.cache().catalog().informCreateItemSuccessful(md.getInt());
        break;
      default:
        logger.warn("Invalid type: " + type.name());
        break;
    }
  }

  public void requestCatalogPublication(final int idpublication)
      throws IOException {
    send(CatalogMessageType.RequestCatalogPublication, idpublication);
  }

  public void requestCreateGroup(final int idparent, final int idpublication)
      throws IOException {
    send(CatalogMessageType.RequestCreateGroup, idparent, idpublication);
  }

  public void requestCreateItem(final int idgroup) throws IOException {
    send(CatalogMessageType.RequestCreateItem, idgroup);
  }

  public void requestLatestPublishId() throws IOException {
    send(CatalogMessageType.RequestLatestPublishId);
  }

  public void requestPublicationList() throws IOException {
    send(CatalogMessageType.RequestPublicationList);
  }

  public void requestPublish(final String comments) throws IOException {
    send(CatalogMessageType.RequestPublish, comments);
  }

  public void requestRemoveGroup(final CatalogGroup group) throws IOException {
    send(CatalogMessageType.RequestRemoveGroup, group.getIdPublication(),
        group.getId());
  }

  public void requestRemoveItem(final CatalogItem item) throws IOException {
    send(CatalogMessageType.RequestRemoveItem, item.getIdPublication(),
        item.getId());
  }

  public void requestSaveGroup(final CatalogGroup group) throws IOException {
    send(CatalogMessageType.RequestSaveGroup, group);
  }

  public void requestSaveItem(final CatalogItem item) throws IOException {
    send(CatalogMessageType.RequestSaveItem, item);
  }
}
