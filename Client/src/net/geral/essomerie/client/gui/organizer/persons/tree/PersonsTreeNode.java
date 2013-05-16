package net.geral.essomerie.client.gui.organizer.persons.tree;

import javax.swing.Icon;
import javax.swing.tree.DefaultMutableTreeNode;

import net.geral.essomerie.client.resources.IMG;
import net.geral.essomerie.shared.person.Person;

public class PersonsTreeNode extends DefaultMutableTreeNode {
  private static final long serialVersionUID = 1L;
  private static final int  ICON_SIZE        = PersonsTreeRenderer.ICON_SIZE;

  public PersonsTreeNode(final Person p) {
    super(p);
  }

  public Icon getIcon() {
    if (getPerson().getType() == null) {
      return IMG.ICON__PERSONS__TYPE_UNKNOWN.icon(ICON_SIZE);
    }
    switch (getPerson().getType()) {
      case Natural:
        return IMG.ICON__PERSONS__TYPE_NATURAL.icon(ICON_SIZE);
      case Legal:
        return IMG.ICON__PERSONS__TYPE_LEGAL.icon(ICON_SIZE);
      default:
        return IMG.ICON__PERSONS__TYPE_UNKNOWN.icon(ICON_SIZE);
    }
  }

  public Person getPerson() {
    return (Person) getUserObject();
  }

  public void setPerson(final Person p) {
    super.setUserObject(p);
  }

  @Override
  @Deprecated
  public void setUserObject(final Object userObject) {
    if (userObject instanceof Person) {
      super.setUserObject(userObject);
    } else {
      throw new IllegalArgumentException("Only persons are acceptable.");
    }
  }

  @Override
  public String toString() {
    final Person p = getPerson();
    final String name = p.getName();
    if (name.length() > 0) {
      return name;
    }
    return String.format("[P#%d]", p.getId());
  }
}
