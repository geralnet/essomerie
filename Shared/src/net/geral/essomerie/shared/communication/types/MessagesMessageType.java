package net.geral.essomerie.shared.communication.types;

import net.geral.essomerie._shared.UserPermission;
import net.geral.essomerie.shared.communication.IMessageType;

public enum MessagesMessageType implements IMessageType {
  RequestMessagesToUser,
  RequestSend,
  RequestRead,
  RequestDelete,

  InformMessagesToUser,
  InformSent,
  InformReceived,
  InformRead,
  InformDeleted, ;

  @Override
  public UserPermission requires() {
    return UserPermission.MESSAGING;
  }

  @Override
  public String toEnglish() {
    switch (this) {
      case RequestDelete:
        return "Deleting message...";
      case RequestMessagesToUser:
        return "Downloading messages...";
      case RequestRead:
        return "Setting message read...";
      case RequestSend:
        return "Sending message...";
      default:
        return null;
    }
  }
}
