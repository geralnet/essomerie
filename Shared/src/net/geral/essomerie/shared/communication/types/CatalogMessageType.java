package net.geral.essomerie.shared.communication.types;

import net.geral.essomerie._shared.UserPermission;
import net.geral.essomerie.shared.communication.IMessageType;

public enum CatalogMessageType implements IMessageType {
  RequestLatestPublishId,
  RequestCatalogPublication,
  RequestPublicationList,
  RequestPublish,
  RequestSaveGroup,
  RequestSaveItem,
  RequestRemoveGroup,
  RequestRemoveItem,
  RequestCreateGroup,
  RequestCreateItem,

  InformLatestPublishId,
  InformCatalogPublication,
  InformPublicationList,
  InformPublishSuccessful,
  InformCatalogPublished,
  InformGroupSaved,
  InformItemSaved,
  InformRemovedGroups,
  InformRemovedItems,
  InformCreatedGroup,
  InformCreateGroupSuccessful,
  InformCreatedItem,
  InformCreateItemSuccessful;

  @Override
  public UserPermission requires() {
    return null;
  }

  @Override
  public String toEnglish() {
    switch (this) {
      case RequestCatalogPublication:
        return "Requesting catalog...";
      case RequestCreateGroup:
        return "Creating catalog group...";
      case RequestCreateItem:
        return "Creating catalog item...";
      case RequestLatestPublishId:
        return "Requesting last published catalog id...";
      case RequestPublicationList:
        return "Requesting list of catalog publications...";
      case RequestPublish:
        return "Publishing Catalog...";
      case RequestRemoveGroup:
        return "Removing catalog group...";
      case RequestRemoveItem:
        return "Removing catalog item...";
      case RequestSaveGroup:
        return "Saving catalog group...";
      case RequestSaveItem:
        return "Saving catalog item...";
      default:
        return null;
    }
  }
}
