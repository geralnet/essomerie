package net.geral.essomerie.shared.communication.types;

import net.geral.essomerie._shared.UserPermission;
import net.geral.essomerie.shared.communication.IMessageType;

public enum CatalogMessageType implements IMessageType {
  RequestLatestPublishId,
  InformLatestPublishId,
  RequestCatalogPublication,
  InformCatalogPublication,
  RequestPublicationList,
  InformPublicationList,
  RequestPublish,
  InformPublishSuccessful,
  InformCatalogPublished,
  RequestSaveGroup,
  InformGroupSaved,
  RequestSaveItem,
  InformItemSaved,
  RequestRemoveGroup,
  InformRemovedGroups,
  RequestRemoveItem,
  InformRemovedItems,
  RequestCreateGroup,
  InformCreatedGroup,
  InformCreateGroupSuccessful,
  RequestCreateItem,
  InformCreatedItem,
  InformCreateItemSuccessful;

  @Override
  public UserPermission requires() {
    return null;
  }

  @Override
  public String toEnglish() {
    switch (this) {
      case RequestLatestPublishId:
        return "Requesting last published catalog id...";
      case RequestCatalogPublication:
        return "Requesting catalog...";
      case RequestPublicationList:
        return "Requesting list of catalog publications...";
      case RequestPublish:
        return "Publishing Catalog...";
      case RequestSaveGroup:
        return "Saving catalog group...";
      case RequestSaveItem:
        return "Saving catalog item...";
      case RequestRemoveGroup:
        return "Removing catalog group...";
      case RequestRemoveItem:
        return "Removing catalog item...";
      case RequestCreateGroup:
        return "Creating catalog group...";
      case RequestCreateItem:
        return "Creating catalog item...";
      default:
        return null;
    }
  }
}
