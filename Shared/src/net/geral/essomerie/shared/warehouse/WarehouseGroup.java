package net.geral.essomerie.shared.warehouse;

import java.io.Serializable;

public class WarehouseGroup implements Serializable {
  private static final long      serialVersionUID = 1L;

  public final int               id;
  public final String            name;
  private final WarehouseGroup[] subgroups;

  public WarehouseGroup(final int id, final String name,
      WarehouseGroup[] subgroups) {
    if (subgroups == null) { // never leave it null
      subgroups = new WarehouseGroup[0];
    }

    this.id = id;
    this.name = name;
    this.subgroups = subgroups;
  }

  public boolean contains(final WarehouseGroup other) {
    if (id == other.id) {
      return true;
    }
    for (final WarehouseGroup g : subgroups) {
      if (g.contains(other)) {
        return true;
      }
    }
    return false;
  }

  public WarehouseGroup[] getSubgroups() {
    return subgroups.clone();
  }

  @Override
  public String toString() {
    return name;
  }
}
