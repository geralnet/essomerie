package net.geral.essomerie.shared.communication.types;

import net.geral.essomerie._shared.UserPermission;
import net.geral.essomerie.shared.communication.IMessageType;

public enum WarehouseMessageType implements IMessageType {
  RequestFullData,
  RequestLogByItem,
  RequestLogByDate,
  RequestQuantityChange,
  RequestItemReport,
  RequestGroupParentOrderChange,
  RequestGroupDelete,
  RequestGroupRename,
  RequestGroupAdd,
  RequestDeleteItem,
  RequestChangeItem,

  InformGroups,
  InformFullData,
  InformLogByItem,
  InformLogByDate,
  InformQuantityChange,
  InformItemReport,
  InformItemDeleted,
  InformItemCreated,
  InformChangeItem;

  @Override
  public UserPermission requires() {
    return null;
  }

  @Override
  public String toEnglish() {
    switch (this) {
      case RequestChangeItem:
        return "Saving warehouse item changes...";
      case RequestDeleteItem:
        return "Deleting warehouse item...";
      case RequestFullData:
        return "Downloading warehouse data...";
      case RequestGroupAdd:
        return "Creating new warehouse group...";
      case RequestGroupDelete:
        return "Deleting warehouse group...";
      case RequestGroupParentOrderChange:
        return "Saving new warehouse group order...";
      case RequestGroupRename:
        return "Renaming warehouse group...";
      case RequestItemReport:
        return "Downloading warehouse item report...";
      case RequestLogByDate:
        return "Downloading warehouse log by date...";
      case RequestLogByItem:
        return "Downloading warehouse log by item...";
      case RequestQuantityChange:
        return "Saving warehouse change...";
      default:
        return null;
    }
  }
}
