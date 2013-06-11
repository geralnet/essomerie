package net.geral.essomerie.client.gui.organizer.persons.tree;

import javax.swing.JTree;
import javax.swing.tree.TreePath;

import net.geral.essomerie.client.gui.organizer.persons.PersonsListFilter;
import net.geral.essomerie.shared.person.Person;

public class PersonsTree extends JTree {
  private static final long serialVersionUID = 1L;

  public PersonsTree() {
    super(new PersonsTreeModel());
    setCellRenderer(new PersonsTreeRenderer());
    setRootVisible(false);
  }

  public void applyFilter() {
    getModel().recreate();
    checkExpandSelect();
  }

  public void applyFilter(final PersonsListFilter filter) {
    getModel().applyFilter(filter);
    checkExpandSelect();
  }

  private void checkExpandSelect() {
    if (getModel().hasFilter()) {
      // has filter, expand all!
      for (int i = 0; i < getRowCount(); i++) {
        expandRow(i);
      }

      setSelected(getModel().getSinglePersonFound());
    }
  }

  @Override
  public PersonsTreeModel getModel() {
    return (PersonsTreeModel) super.getModel();
  }

  public void setSelected(final Person p) {
    final TreePath path = (p == null) ? null : getModel().getPathToPerson(p);
    setSelectionPath(path);
    if (path != null) {
      scrollPathToVisible(path);
    }
  }
}
