package net.geral.essomerie._shared.contagem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;

import org.joda.time.LocalDate;

public class InventoryItemReport implements Serializable {
  private static final long                                    serialVersionUID = 1L;

  private final InventoryItem                                  item;
  private final Hashtable<LocalDate, InventoryItemReportEntry> entries          = new Hashtable<>();
  private boolean                                              blocked          = false;

  public InventoryItemReport(final InventoryItem item) {
    this.item = item;
  }

  public void addEntry(final LocalDate date, final float initial,
      final String reason, final float delta) {
    if (blocked) {
      throw new IllegalStateException("Instance blocked.");
    }
    InventoryItemReportEntry entry = entries.get(date);
    if (entry == null) {
      // initial is only used when creating item
      // we assume the first is the oldest
      entry = new InventoryItemReportEntry(date, initial);
    }
    entry.add(reason, delta);
    entries.put(date, entry);
  }

  public void block() {
    blocked = true;
  }

  public LocalDate getFirstDate() {
    LocalDate first = null;
    for (final LocalDate d : entries.keySet()) {
      if (first == null) {
        first = d;
      } else if (d.isBefore(first)) {
        first = d;
      }
    }
    return first;
  }

  public InventoryItem getItem() {
    return item;
  }

  public String[] getReasons() {
    final ArrayList<String> reasons = new ArrayList<>();
    for (final InventoryItemReportEntry e : entries.values()) {
      for (final String s : e.getReasons()) {
        if (!reasons.contains(s)) {
          reasons.add(s);
        }
      }
    }
    Collections.sort(reasons);
    return reasons.toArray(new String[reasons.size()]);
  }

  public InventoryItemReportEntry[] getReportData() {
    final ArrayList<InventoryItemReportEntry> r = new ArrayList<>();
    LocalDate d = getFirstDate().withDayOfMonth(1);
    if (d == null) {
      return new InventoryItemReportEntry[0];
    }
    final LocalDate until = LocalDate.now().withDayOfMonth(1);
    InventoryItemReportEntry lastEntry = null;
    while (d.isBefore(until)) {
      InventoryItemReportEntry e = entries.get(d);
      if (e == null) {
        Float initial = (lastEntry == null) ? null : lastEntry.getInitial();
        if (initial != null) {
          initial += lastEntry.getTotal();
        }
        e = new InventoryItemReportEntry(d, initial);
      }
      r.add(e);
      lastEntry = e;
      d = d.plusMonths(1);
    }
    return r.toArray(new InventoryItemReportEntry[r.size()]);
  }

  @Override
  public String toString() {
    return "InventoryItemReport[" + item.nome + ";" + entries.size() + "]";
  }
}
