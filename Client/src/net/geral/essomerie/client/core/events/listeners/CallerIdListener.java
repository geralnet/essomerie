package net.geral.essomerie.client.core.events.listeners;

import java.util.EventListener;

import net.geral.essomerie.shared.person.PersonData;
import net.geral.essomerie.shared.person.Telephone;

public interface CallerIdListener extends EventListener {
  void callerIdCallReceived(String line, Telephone telephone, PersonData person);
}
