package net.geral.essomerie.shared.communication.types;

import net.geral.essomerie._shared.UserPermission;
import net.geral.essomerie.shared.communication.IMessageType;

public enum SysopMessageType implements IMessageType {
  RequestDevicesInfo,
  InformDevicesInfo;

  @Override
  public UserPermission requires() {
    return null;
  }

  @Override
  public String toEnglish() {
    switch (this) {
      case RequestDevicesInfo:
        return "Fetching devices...";
      default:
        return null;
    }
  }
}
