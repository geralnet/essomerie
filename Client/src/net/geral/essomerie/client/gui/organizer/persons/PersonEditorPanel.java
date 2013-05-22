package net.geral.essomerie.client.gui.organizer.persons;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.geral.essomerie.shared.person.Person;

public abstract class PersonEditorPanel extends JPanel {
  private static final long serialVersionUID = 1L;

  public PersonEditorPanel() {
    setBorder(new EmptyBorder(2, 2, 2, 2));
  }

  public abstract void setEditable(final boolean yn);

  public abstract void setPerson(final Person p);
}
