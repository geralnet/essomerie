package net.geral.essomerie._shared;

import java.io.Serializable;

public class User implements Comparable<User>, Serializable {
  private static final long serialVersionUID = 1L;

  private final int         id;
  private final String      username;
  private final String      name;

  public User(final int id, final String username, final String name) {
    this.id = id;
    this.username = username;
    this.name = name;
  }

  @Override
  public int compareTo(final User o) {
    // FIXME accents case...
    return name.compareTo(o.name);
  }

  public String getFirstName() {
    if (name == null) {
      return null;
    }
    final int pos = name.indexOf(' ');
    if (pos == -1) {
      return name;
    }
    return name.substring(0, pos);
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getUsername() {
    return username;
  }

  @Override
  public String toString() {
    return name;
  }
}
