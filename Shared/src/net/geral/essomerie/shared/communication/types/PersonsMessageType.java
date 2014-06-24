package net.geral.essomerie.shared.communication.types;

import net.geral.essomerie._shared.UserPermission;
import net.geral.essomerie.shared.communication.IMessageType;

public enum PersonsMessageType implements IMessageType {
  RequestList,
  RequestSave,
  RequestDelete,
  RequestPersonData,
  RequestFullData,

  InformList,
  InformSaved,
  InformDeleted,
  InformPersonData,
  InformFullData,
  InformIdNotFound;

  @Override
  public UserPermission requires() {
    return null;
  }

  @Override
  public String toEnglish() {
    switch (this) {
      case RequestDelete:
        return "Deleting person data...";
      case RequestFullData:
        return "Downloading all persons data...";
      case RequestList:
        return "Downloading persons list...";
      case RequestPersonData:
        return "Downloading person data...";
      case RequestSave:
        return "Saving person data...";
      default:
        return null;
    }
  }
}
