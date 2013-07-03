package net.geral.essomerie.shared.communication.types;

import net.geral.essomerie._shared.UserPermission;
import net.geral.essomerie.shared.communication.IMessageType;

public enum UsersMessageType implements IMessageType {
  RequestList,
  RequestChange,
  RequestCreate,
  RequestDelete,
  InformList,
  InformCreated,
  InformChanged,
  InformDeleted;

  @Override
  public UserPermission requires() {
    switch (this) {
      case RequestList:
      case InformList:
        return null;
      default:
        return UserPermission.USER_MANAGEMENT;
    }
  }

  @Override
  public String toEnglish() {
    switch (this) {
      case RequestChange:
        return "Saving changes to user...";
      case RequestCreate:
        return "Saving new user...";
      case RequestDelete:
        return "Deleting user...";
      case RequestList:
        return "Listing users...";
      default:
        return null;
    }
  }
}
