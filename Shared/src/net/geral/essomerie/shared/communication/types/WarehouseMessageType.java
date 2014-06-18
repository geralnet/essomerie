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

  InformGroups,
  InformFullData,
  InformLogByItem,
  InformLogByDate,
  InformQuantityChange,
  InformItemReport,
  RequestGroupDelete,
  RequestGroupRename,
  RequestGroupAdd,
  RequestDeleteItem,
  InformItemDeleted,
  RequestChangeItem,
  InformItemCreated,
  InformChangeItem;

  @Override
  public UserPermission requires() {
    return null;
  }

  @Override
  public String toEnglish() {
    switch (this) {
      case RequestFullData:
        return "Downloading warehouse data...";
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
