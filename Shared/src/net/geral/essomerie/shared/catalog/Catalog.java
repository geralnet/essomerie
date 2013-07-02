package net.geral.essomerie.shared.catalog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import org.joda.time.LocalDateTime;

public class Catalog implements Serializable {
  private static final long serialVersionUID = 1L;

  public static Catalog create(final int idpublication,
      final LocalDateTime when, final int by, final String comments,
      final ArrayList<CatalogGroup> groups, final ArrayList<CatalogItem> items) {
    final Catalog c = new Catalog(idpublication, when, by, comments);
    c.groups.addAll(groups);
    c.items.addAll(items);
    c.sort();
    return c;
  }

  public static Catalog createCopy(final Catalog c) {
    if (c == null) {
      return createEmpty();
    }

    final Catalog nc = new Catalog();
    synchronized (c) {
      nc.groups.addAll(c.groups);
      nc.items.addAll(c.items);
    }

    nc.sort();
    return nc;
  }

  public static Catalog createEmpty() {
    return new Catalog();
  }

  private final int                     idpublication;

  private final LocalDateTime           when;
  private final int                     by;
  private final String                  comments;
  private final ArrayList<CatalogGroup> groups = new ArrayList<>();
  private final ArrayList<CatalogItem>  items  = new ArrayList<>();

  private Catalog() {
    this(0, null, 0, "");
  }

  private Catalog(final int idpublication, final LocalDateTime when,
      final int by, final String comments) {
    this.idpublication = idpublication;
    this.when = when;
    this.by = by;
    this.comments = comments;
  }

  public void addGroup(final CatalogGroup group) {
    groups.add(group);
    Collections.sort(groups);
  }

  public void addItem(final CatalogItem item) {
    items.add(item);
    Collections.sort(items);
  }

  public int getBy() {
    return by;
  }

  public String getComments() {
    return comments;
  }

  public int getIdPublication() {
    return idpublication;
  }

  public synchronized ArrayList<CatalogItem> getItems(final CatalogGroup g) {
    final int idgroup = g.getId();
    final ArrayList<CatalogItem> is = new ArrayList<>();
    for (final CatalogItem i : items) {
      if (i.getIdGroup() == idgroup) {
        is.add(i);
      }
    }
    return is;
  }

  public synchronized ArrayList<CatalogGroup> getRootGroups() {
    return getSubGroups(null);
  }

  public synchronized ArrayList<CatalogGroup> getSubGroups(
      final CatalogGroup parent) {
    final int id = (parent == null) ? 0 : parent.getId();
    final ArrayList<CatalogGroup> subs = new ArrayList<>();
    for (final CatalogGroup g : groups) {
      if (g.getIdParent() == id) {
        subs.add(g);
      }
    }
    return subs;
  }

  public LocalDateTime getWhen() {
    return when;
  }

  public synchronized void removeGroups(final int idpublication,
      final ArrayList<Integer> ids) {
    if (ids.size() == 0) {
      return;
    }
    // traverse groups
    for (int i = 0; i < groups.size(); i++) {
      final CatalogGroup g = groups.get(i);
      // traverse ids
      idsloop: for (int j = 0; j < ids.size(); j++) {
        final int id = ids.get(j).intValue();
        // check
        if (g.getId() == id) {
          groups.remove(i);
          i--;
          break idsloop;
        }
      }
    }
  }

  public synchronized void removeItems(final int idpublication,
      final ArrayList<Integer> ids) {
    if (ids.size() == 0) {
      return;
    }
    // traverse items
    for (int i = 0; i < items.size(); i++) {
      final CatalogItem it = items.get(i);
      // traverse ids
      idsloop: for (int j = 0; j < ids.size(); j++) {
        final int id = ids.get(j).intValue();
        // check
        if (it.getId() == id) {
          items.remove(i);
          i--;
          break idsloop;
        }
      }
    }
  }

  public synchronized void replaceGroup(final int oldGroupId,
      final CatalogGroup group) {
    final int newGroupId = group.getId();
    // remove old group and replace parents
    for (int i = 0; i < groups.size(); i++) {
      final CatalogGroup g = groups.get(i);
      if (g.getId() == oldGroupId) {
        groups.remove(i);
        i--;
      } else if (g.getIdParent() == oldGroupId) {
        groups.set(i, g.withIdParent(newGroupId));
      }
    }
    // replace item groups
    for (int i = 0; i < items.size(); i++) {
      final CatalogItem it = items.get(i);
      if (it.getIdGroup() == oldGroupId) {
        items.set(i, it.withIdGroup(newGroupId));
      }
    }
    // add & sort
    groups.add(group);
    Collections.sort(groups);
  }

  public synchronized void replaceItem(final int oldItemId,
      final CatalogItem item) {
    // remove old item
    for (int i = 0; i < items.size(); i++) {
      final CatalogItem it = items.get(i);
      if (it.getId() == oldItemId) {
        items.remove(i);
        i--;
      }
    }
    // add & sort
    items.add(item);
    Collections.sort(items);
  }

  private void sort() {
    Collections.sort(groups);
    Collections.sort(items);
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "[id=" + idpublication + ";cmt="
        + comments + ";groups=" + groups.size() + ";items=" + items.size()
        + "]";
  }
}
