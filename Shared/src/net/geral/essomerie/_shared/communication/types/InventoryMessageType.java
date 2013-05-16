package net.geral.essomerie._shared.communication.types;

import net.geral.essomerie._shared.UserPermission;
import net.geral.essomerie._shared.communication.IMessageType;

public enum InventoryMessageType implements IMessageType {
  RequestFullData,
  RequestLogByItem,
  RequestLogByDate,
  RequestQuantityChange,

  InformFullData,
  InformLogByItem,
  InformLogByDate,
  InformQuantityChange, ;

  @Override
  public UserPermission requires() {
    return null;
  }

  @Override
  public String toEnglish() {
    switch (this) {
      case RequestFullData:
        return "Downloading inventory...";
      case RequestLogByDate:
        return "Downloading inventory log by date...";
      case RequestLogByItem:
        return "Downloading inventory log by item...";
      case RequestQuantityChange:
        return "Saving inventory change...";
      default:
        return null;
    }
  }
}
