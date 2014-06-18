package net.geral.essomerie.shared.warehouse.report;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;

import net.geral.essomerie.shared.warehouse.WarehouseItem;

import org.joda.time.LocalDate;

public class MonthlyFlowReport implements Serializable {
  private static final long                                  serialVersionUID = 1L;

  private final WarehouseItem                                item;
  private final Hashtable<LocalDate, MonthlyFlowReportEntry> entries          = new Hashtable<>();
  private boolean                                            ready            = false;

  public MonthlyFlowReport(final WarehouseItem item) {
    this.item = item;
  }

  public void addEntry(final LocalDate date, final float initial,
      final String reason, final float delta) {
    if (ready) {
      throw new IllegalStateException("Instance blocked.");
    }
    MonthlyFlowReportEntry entry = entries.get(date);
    if (entry == null) {
      // initial is only used when creating item
      // we assume the first is the oldest
      entry = new MonthlyFlowReportEntry(date, initial);
    }
    entry.add(reason, delta);
    entries.put(date, entry);
  }

  public void block() {
    ready = true;
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

  public WarehouseItem getItem() {
    return item;
  }

  public String[] getReasons() {
    final ArrayList<String> reasons = new ArrayList<>();
    for (final MonthlyFlowReportEntry e : entries.values()) {
      for (final String s : e.getReasons()) {
        if (!reasons.contains(s)) {
          reasons.add(s);
        }
      }
    }
    Collections.sort(reasons);
    return reasons.toArray(new String[reasons.size()]);
  }

  public MonthlyFlowReportEntry[] getReportData() {
    final ArrayList<MonthlyFlowReportEntry> r = new ArrayList<>();
    LocalDate d = getFirstDate().withDayOfMonth(1);
    if (d == null) {
      return new MonthlyFlowReportEntry[0];
    }
    final LocalDate until = LocalDate.now().withDayOfMonth(1);
    MonthlyFlowReportEntry lastEntry = null;
    while (d.isBefore(until)) {
      MonthlyFlowReportEntry e = entries.get(d);
      if (e == null) {
        Float initial = (lastEntry == null) ? null : lastEntry.getInitial();
        if (initial != null) {
          initial += lastEntry.getTotal();
        }
        e = new MonthlyFlowReportEntry(d, initial);
      }
      r.add(e);
      lastEntry = e;
      d = d.plusMonths(1);
    }
    return r.toArray(new MonthlyFlowReportEntry[r.size()]);
  }

  @Override
  public String toString() {
    return "MonthlyFlowReport[" + item.name + ";" + entries.size() + "]";
  }
}
