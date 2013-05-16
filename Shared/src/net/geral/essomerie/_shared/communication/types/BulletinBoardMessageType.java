package net.geral.essomerie._shared.communication.types;

import net.geral.essomerie._shared.UserPermission;
import net.geral.essomerie._shared.communication.IMessageType;

public enum BulletinBoardMessageType implements IMessageType {
  RequestTitleList,
  RequestFullContents,
  RequestSave,
  RequestDelete,
  InformTitleList,
  InformFullContents,
  InformSaveSuccessful,
  InformChanged,
  InformDeleted;

  @Override
  public UserPermission requires() {
    return null;
  }

  @Override
  public String toEnglish() {
    switch (this) {
      case RequestDelete:
        return "Deleting bulletin board entry...";
      case RequestFullContents:
        return "Downloading bulletin board contents...";
      case RequestSave:
        return "Saving bulletin board entry...";
      case RequestTitleList:
        return "Downloading bulletin board titles...";
      default:
        return null;
    }
  }
}
