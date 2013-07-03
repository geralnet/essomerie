package net.geral.essomerie.shared.roster;

import java.io.Serializable;

public class RosterEntry implements Serializable {
  private static final long serialVersionUID = 1L;

  private final int         id;
  private String            assignment;
  private String[]          names;

  public RosterEntry() {
    this(0, "", "");
  }

  public RosterEntry(final int id, final String assignment,
      final String names) {
    this.id = id;
    this.assignment = assignment;
    this.names = new String[] { names };
  }

  public RosterEntry(final int id, final String assignment,
      final String[] names) {
    this.id = id;
    this.assignment = assignment;
    this.names = names;
  }

  public String getAssignment() {
    return assignment;
  }

  public int getId() {
    return id;
  }

  public String[] getNamesArray() {
    return names;
  }

  public String getNamesString() {
    if (names.length == 0) {
      return "n/a";
    }
    if (names.length == 1) {
      return names[0];
    }

    final StringBuilder sb = new StringBuilder();
    boolean first = true;
    for (final String f : names) {
      if (first) {
        first = false;
      } else {
        sb.append(" ; ");
      }
      sb.append(f);
    }

    return sb.toString();
  }

  public void setAssignment(final String funcao) {
    this.assignment = funcao;
  }

  public void setNames(final String names) {
    this.names = new String[] { names };
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "[" + id + ";" + assignment + ";"
        + names.length + "]";
  }
}
