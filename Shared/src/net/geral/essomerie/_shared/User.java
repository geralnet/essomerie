package net.geral.essomerie._shared;

import java.io.Serializable;

public class User implements Comparable<User>, Serializable {
  private static final long serialVersionUID = 1L;

  private final int         id;
  private final String      username;
  private final String      name;
  private final char[]      pin;

  public User(final int id, final String username, final String name,
      final char[] pin) {
    this.id = id;
    this.username = username;
    this.name = name;
    this.pin = (pin == null) ? null : pin.clone();
  }

  public boolean checkPIN(final char[] against) {
    // needs both values to compare
    if ((pin == null) || (against == null)) {
      return false;
    }
    // pin cannot be empty
    if (pin.length == 0) {
      return false;
    }
    // sizes must match
    if (pin.length != against.length) {
      return false;
    }
    // all characters must match
    for (int i = 0; i < pin.length; i++) {
      if (pin[i] != against[i]) {
        return false;
      }
    }
    // no differences found
    return true;
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

  public boolean hasPIN() {
    return ((pin != null) && (pin.length > 0));
  }

  @Override
  public String toString() {
    return name;
  }
}
