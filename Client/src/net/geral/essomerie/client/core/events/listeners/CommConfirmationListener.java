package net.geral.essomerie.client.core.events.listeners;

import java.util.EventListener;

public interface CommConfirmationListener extends EventListener {
  void commConfirm(long messageId);
}
