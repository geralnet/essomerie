package net.geral.essomerie.shared.warehouse;

import java.io.Serializable;
import java.util.ArrayList;

public class Warehouse implements Serializable {
  private static final long      serialVersionUID = 1L;

  private WarehouseChangeReasons reasons;
  private WarehouseGroup[]       groups;
  private WarehouseItem[]        items;

  public Warehouse() {
    this(new WarehouseChangeReasons(), new WarehouseGroup[0],
        new WarehouseItem[0]);
  }

  public Warehouse(final WarehouseChangeReasons reasons,
      final WarehouseGroup[] groups, final WarehouseItem[] items) {
    setReasons(reasons);
    setGroups(groups);
    setItems(items);
  }

  public void changeItem(final WarehouseItem item) {
    synchronized (this) {
      boolean found = false;
      final ArrayList<WarehouseItem> newList = new ArrayList<>(items.length);
      for (final WarehouseItem wi : items) {
        if (wi.id == item.id) {
          found = true;
          newList.add(item);
        } else {
          newList.add(wi);
        }
      }
      if (!found) {
        newList.add(item);
      }
      items = newList.toArray(new WarehouseItem[newList.size()]);
    }
  }

  private WarehouseGroup getGroup(final int id) {
    for (final WarehouseGroup g : groups) {
      if (g.id == id) {
        return g;
      }
    }
    return null;
  }

  public WarehouseGroup[] getGroups() {
    return groups.clone();
  }

  public WarehouseGroup[] getGroups(final int[] ids) {
    final WarehouseGroup[] wg = new WarehouseGroup[ids.length];
    for (int i = 0; i < wg.length; i++) {
      wg[i] = getGroup(ids[i]);
    }
    return wg;
  }

  public WarehouseItem getItem(final int id) {
    for (final WarehouseItem wi : items) {
      if (wi.id == id) {
        return wi;
      }
    }
    return null;
  }

  public WarehouseItem[] getItems() {
    return items.clone();
  }

  public WarehouseItem[] getItems(final WarehouseGroup group) {
    if (group == null) {
      return new WarehouseItem[0];
    }

    final ArrayList<WarehouseItem> res = new ArrayList<WarehouseItem>();
    for (final WarehouseItem i : items) {
      if (group.id == i.idgroup) {
        res.add(i);
      }
    }
    return res.toArray(new WarehouseItem[res.size()]);
  }

  public WarehouseChangeReasons getReasons() {
    return new WarehouseChangeReasons(reasons);
  }

  public void removeItem(final int iditem) {
    final ArrayList<WarehouseItem> newList = new ArrayList<>(items.length);
    boolean removed = false;
    for (final WarehouseItem wi : items) {
      if (wi.id == iditem) {
        removed = true;
      } else {
        newList.add(wi);
      }
    }
    if (removed) {
      items = newList.toArray(new WarehouseItem[newList.size()]);
    }
  }

  public void setGroups(final WarehouseGroup[] warehouseGroups) {
    groups = warehouseGroups;
  }

  public void setItems(final WarehouseItem[] warehouseItems) {
    items = warehouseItems;
  }

  public void setNewQuantity(final int iditem, final float newQuantity) {
    for (final WarehouseItem wi : items) {
      if (wi.id == iditem) {
        wi.setQuantity(newQuantity);
      }
    }
  }

  public void setReasons(final WarehouseChangeReasons changeReasons) {
    reasons = changeReasons;
  }
}
