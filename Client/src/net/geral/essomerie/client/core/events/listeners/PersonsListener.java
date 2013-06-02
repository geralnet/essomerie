package net.geral.essomerie.client.core.events.listeners;

import java.util.EventListener;

import net.geral.essomerie.shared.person.Person;
import net.geral.essomerie.shared.person.PersonData;

public interface PersonsListener extends EventListener {
  public void personsCacheReloaded(boolean fullData);

  public void personsDeleted(int idperson);

  public void personsFullDataReceived(PersonData p);

  public void personsSalesChanged(int idperson);

  public void personsSaved(Person p);
}
