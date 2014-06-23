package net.geral.essomerie.shared.person;

public enum PersonType {
  Natural,
  Legal;

  public static PersonType fromString(final String s) {
    if (s == null) {
      return Natural;
    }
    return valueOf(s);
  }
}
