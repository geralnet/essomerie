package net.geral.essomerie.shared.communication.types;

import net.geral.essomerie._shared.UserPermission;
import net.geral.essomerie.shared.communication.IMessageType;

public enum CalendarMessageType implements IMessageType {
  RequestEvents,
  RequestEventAddChange,
  RequestEventDelete,
  RequestEventDetails,
  RequestRoster,
  RequestRosterSave,

  InformEvents,
  InformEventAddedChanged,
  InformEventDeleted,
  InformEventDetails,
  InformRoster;

  @Override
  public UserPermission requires() {
    return null;
  }

  @Override
  public String toEnglish() {
    switch (this) {
      case RequestEventAddChange:
        return "Saving calendar event...";
      case RequestEventDelete:
        return "Deleting calendar event...";
      case RequestEventDetails:
        return "Download calendar event details...";
      case RequestEvents:
        return "Downloading calendar events...";
      case RequestRoster:
        return "Downloading roster...";
      case RequestRosterSave:
        return "Saving roster...";
      default:
        return null;
    }
  }
}
