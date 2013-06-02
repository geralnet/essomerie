package net.geral.essomerie._shared.communication.types;

import net.geral.essomerie._shared.UserPermission;
import net.geral.essomerie._shared.communication.IMessageType;

public enum SalesMessageType implements IMessageType {
  RequestLatestRegistration,
  InformLatestRegistration,
  RequestRegister,
  InformRegistered;

  @Override
  public UserPermission requires() {
    return null;
  }

  @Override
  public String toEnglish() {
    switch (this) {
      case RequestLatestRegistration:
        return "Requesting last registered sales...";
      case RequestRegister:
        return "Saving sale...";
      default:
        return null;
    }
  }
}
