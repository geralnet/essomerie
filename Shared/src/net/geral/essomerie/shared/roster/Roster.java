package net.geral.essomerie.shared.roster;

import java.io.Serializable;
import java.util.ArrayList;

import org.joda.time.LocalDate;

public class Roster implements Serializable {
  private static final long            serialVersionUID = 1L;
  private final LocalDate              date;
  private final boolean                dayShift;

  private final ArrayList<RosterEntry> entries          = new ArrayList<RosterEntry>();

  public Roster() {
    date = new LocalDate(0);
    dayShift = true;
  }

  public Roster(final LocalDate date, final boolean dayShift) {
    this.date = date;
    this.dayShift = dayShift;
  }

  public void addEntry(final int id, final String assignment, final String names) {
    addEntry(new RosterEntry(id, assignment, names));
  }

  public void addEntry(final RosterEntry ria) {
    entries.add(ria);
  }

  public int countEntries() {
    return entries.size();
  }

  public String getAssignment(final int i) {
    return entries.get(i).getAssignment();
  }

  public LocalDate getDate() {
    return date;
  }

  public RosterEntry[] getEntries() {
    return entries.toArray(new RosterEntry[entries.size()]);
  }

  public RosterEntry getEntry(final int i) {
    return entries.get(i);
  }

  public String getNamesString(final int i) {
    return entries.get(i).getNamesString();
  }

  public boolean isDayShift() {
    return dayShift;
  }

  public void remove(final int i) {
    entries.remove(i);
  }

  public void remove(final RosterEntry ria) {
    entries.remove(ria);
  }

  public void setEntry(final int i, final RosterEntry ria) {
    entries.set(i, ria);
  }

  public void setEntryAssignment(final int i, final String assignment) {
    entries.get(i).setAssignment(assignment);
  }

  public void setEntryNames(final int i, final String names) {
    entries.get(i).setNames(names);
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "[" + date + ";" + dayShift + ";"
        + entries + "]";
  }
}
