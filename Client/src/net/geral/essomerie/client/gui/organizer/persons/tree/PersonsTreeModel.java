package net.geral.essomerie.client.gui.organizer.persons.tree;

import java.util.ArrayList;
import java.util.Collections;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import net.geral.essomerie.client.gui.organizer.persons.PersonsListFilter;
import net.geral.essomerie.client.resources.S;
import net.geral.essomerie.shared.person.Person;
import net.geral.essomerie.shared.person.PersonData;
import net.geral.essomerie.shared.person.PersonType;
import net.geral.lib.edt.Edt;

import org.apache.log4j.Logger;

public class PersonsTreeModel extends DefaultTreeModel {
  private static final Logger          logger           = Logger
                                                            .getLogger(PersonsTreeModel.class);
  private static final long            serialVersionUID = 1L;

  private final DefaultMutableTreeNode root;
  private final DefaultMutableTreeNode typeNatural      = new DefaultMutableTreeNode(
                                                            S.ORGANIZER_PERSONS_NATURAL);
  private final DefaultMutableTreeNode typeLegal        = new DefaultMutableTreeNode(
                                                            S.ORGANIZER_PERSONS_LEGAL);
  private final DefaultMutableTreeNode typeUnknown      = new DefaultMutableTreeNode(
                                                            S.ORGANIZER_PERSONS_GENERAL_UNKNOWN);

  private ArrayList<Person>            persons          = new ArrayList<>();
  private PersonsListFilter            currentFilter    = null;

  public PersonsTreeModel() {
    super(new DefaultMutableTreeNode("root"));
    root = (DefaultMutableTreeNode) getRoot();
  }

  public void addPerson(final Person p) {
    Edt.required();
    final PersonsTreeNode newNode = new PersonsTreeNode(p);
    // add to persons list
    persons.add(p);
    Collections.sort(persons);
    // get proper node to add
    final DefaultMutableTreeNode parent = getParentForPersonType(p.getType());
    // find 'before node' to insert
    for (int i = 0; i < parent.getChildCount(); i++) {
      final PersonsTreeNode ptn = (PersonsTreeNode) parent.getChildAt(i);
      if (p.compareTo(ptn.getPerson()) < 0) {
        insertNodeInto(newNode, parent, i);
        return; // added, nothing else to do
      }
    }
    // no added before anything, add to end
    insertNodeInto(newNode, parent, parent.getChildCount());
  }

  public void applyFilter(final PersonsListFilter newFilter) {
    Edt.required();
    if ((newFilter == null) && (newFilter == null)) {
      logger.debug("applyFilter(null) - already without filter, ignore.");
      return;
    }

    if ((newFilter != null) && newFilter.equals(currentFilter)) {
      logger.debug("applyFilter(" + newFilter + ") same as current, ignore.");
      return;
    }

    currentFilter = newFilter;
    logger.debug("applyFilter(" + currentFilter + ")...");
    recreate();
  }

  private void changePerson(final DefaultMutableTreeNode n, final PersonData p) {
    // recursive check
    for (int i = 0; i < n.getChildCount(); i++) {
      final DefaultMutableTreeNode c = (DefaultMutableTreeNode) n.getChildAt(i);
      changePerson(c, p);
    }
    // check if node is to be changed
    if (n instanceof PersonsTreeNode) {
      final PersonsTreeNode pn = (PersonsTreeNode) n;
      if (pn.getPerson().getId() == p.getId()) {
        pn.setPerson(p);
        nodeChanged(pn);
      }
    }
  }

  public void changePerson(final PersonData p) {
    Edt.required();
    // replace in array
    for (int i = 0; i < persons.size(); i++) {
      if (persons.get(i).getId() == p.getId()) {
        persons.set(i, p);
      }
    }
    // replace node
    changePerson(root, p);
  }

  private void createNodes() {
    for (final Person p : persons) {
      if (hasFilter() && currentFilter.rejects(p)) {
        continue;
      }
      final PersonsTreeNode node = new PersonsTreeNode(p);
      switch (p.getType()) {
        case Legal:
          typeLegal.add(node);
          break;
        case Natural:
          typeNatural.add(node);
          break;
        default:
          typeUnknown.add(node);
          break;
      }
    }
  }

  private DefaultMutableTreeNode getParentForPersonType(final PersonType type) {
    switch (type) {
      case Legal:
        return typeLegal;
      case Natural:
        return typeNatural;
      default:
        return typeUnknown;
    }
  }

  private TreePath getPathToPerson(final DefaultMutableTreeNode node,
      final Person p) {
    // check if node is the person
    if (node instanceof PersonsTreeNode) {
      if (((PersonsTreeNode) node).getPerson().getId() == p.getId()) {
        return new TreePath(node.getPath());
      }
    }
    // recursive check
    for (int i = 0; i < node.getChildCount(); i++) {
      final TreePath path = getPathToPerson(
          (DefaultMutableTreeNode) node.getChildAt(i), p);
      if (path != null) {
        return path;
      }
    }
    // not found
    return null;
  }

  public TreePath getPathToPerson(final Person p) {
    return getPathToPerson(root, p);
  }

  public Person getSinglePersonFound() {
    final int legal = typeLegal.getChildCount();
    final int natural = typeNatural.getChildCount();
    final int unknown = typeUnknown.getChildCount();
    final int sum = legal + natural + unknown;
    if (sum != 1) {
      return null;// not only one
    }
    if (legal == 1) {
      return ((PersonsTreeNode) typeLegal.getChildAt(0)).getPerson();
    }
    if (natural == 1) {
      return ((PersonsTreeNode) typeNatural.getChildAt(0)).getPerson();
    }
    if (unknown == 1) {
      return ((PersonsTreeNode) typeUnknown.getChildAt(0)).getPerson();
    }
    return null;
  }

  public boolean hasFilter() {
    if (currentFilter == null) {
      return false;
    }
    if (currentFilter.disabled()) {
      return false;
    }
    return true;
  }

  public void recreate() {
    Edt.required();
    removeAllChildreen();
    createNodes();
    setBaseNodes();
    reload();
    if ((currentFilter == null) || currentFilter.disabled()) {
      return;
    }
  }

  private void removeAllChildreen() {
    root.removeAllChildren();
    typeNatural.removeAllChildren();
    typeLegal.removeAllChildren();
    typeUnknown.removeAllChildren();
  }

  private boolean removeById(final DefaultMutableTreeNode node,
      final int idperson) {
    // recursive check
    for (int i = 0; i < node.getChildCount(); i++) {
      final DefaultMutableTreeNode c = (DefaultMutableTreeNode) node
          .getChildAt(i);
      if (removeById(c, idperson)) {
        i--;
      }
    }
    // check if node is to be removed
    if (node instanceof PersonsTreeNode) {
      if (((PersonsTreeNode) node).getPerson().getId() == idperson) {
        removeNodeFromParent(node);
        return true;
      }
    }
    return false;
  }

  public void removeById(final int idperson) {
    Edt.required();
    // remove from persons list (used in filters)
    for (int i = 0; i < persons.size(); i++) {
      if (persons.get(i).getId() == idperson) {
        persons.remove(i);
        i--;
      }
    }
    // remove nodes
    removeById(root, idperson);
  }

  private void setBaseNodes() {
    // TODO add other options (for now is type)
    root.add(typeNatural);
    root.add(typeLegal);
    root.add(typeUnknown);
  }

  public void setPersons(final Person[] ps) {
    Edt.required();
    persons = new ArrayList<>();
    for (final Person p : ps) {
      persons.add(p);
    }
    Collections.sort(persons);
    recreate();
  }
}
