package net.geral.essomerie.shared.communication;

import net.geral.essomerie._shared.UserPermission;

public interface IMessageType {
  public UserPermission requires();

  public String toEnglish();
}
