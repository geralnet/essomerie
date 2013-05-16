package net.geral.essomerie.shared.person;

public enum PersonType {
  Natural,
  Legal,
  Unknown;

  public static PersonType fromString(final String s) {
    if (s == null) {
      return Unknown;
    }
    return valueOf(s);
  }
}
