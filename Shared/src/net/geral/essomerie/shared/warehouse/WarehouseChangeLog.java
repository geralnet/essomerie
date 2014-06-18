package net.geral.essomerie.shared.warehouse;

import java.io.Serializable;

public class WarehouseChangeLog implements Serializable {
  private static final long               serialVersionUID = 1L;

  public final int                        iditem;
  private final WarehouseChangeLogEntry[] entries;

  public WarehouseChangeLog(final int iditem, final int entriesCount) {
    this.iditem = iditem;
    entries = new WarehouseChangeLogEntry[entriesCount];
  }

  public int count() {
    return entries.length;
  }

  public WarehouseChangeLogEntry get(final int index) {
    return entries[index];
  }

  public WarehouseChangeLogEntry[] getEntries() {
    return entries.clone();
  }

  public void set(final int i, final WarehouseChangeLogEntry wcle) {
    entries[i] = wcle;
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "[item=" + iditem + ";entries="
        + entries.length + "]";
  }
}