package net.geral.essomerie.shared.warehouse.report;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Set;

import org.joda.time.LocalDate;

public class MonthlyFlowReportEntry implements Serializable {
  private static final long              serialVersionUID = 1L;

  private final LocalDate                date;
  private final Float                    initial;
  private final Hashtable<String, Float> ammount          = new Hashtable<>();

  public MonthlyFlowReportEntry(final LocalDate date, final Float initial) {
    this.initial = initial;
    this.date = date;
  }

  public void add(final String reason, final float delta) {
    Float f = ammount.get(reason);
    if (f == null) {
      f = delta;
    } else {
      f += delta;
    }
    ammount.put(reason, f);
  }

  public Float getAmmount(final String reason) {
    final Float f = ammount.get(reason);
    if (f == null) {
      return 0f;
    }
    return f;
  }

  public LocalDate getDate() {
    return date;
  }

  public Float getInitial() {
    return initial;
  }

  public Set<String> getReasons() {
    return ammount.keySet();
  }

  public Float getTotal() {
    float res = 0f;
    for (final Float f : ammount.values()) {
      res += f;
    }
    return res;
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "[" + date + ";" + ammount.size() + "]";
  }
}
