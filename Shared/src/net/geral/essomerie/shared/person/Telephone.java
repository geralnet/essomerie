package net.geral.essomerie.shared.person;

import java.io.Serializable;

import net.geral.lib.strings.GNStrings;

public class Telephone implements Serializable {
  private static final long serialVersionUID = 1L;
  private static final int  SEPARATOR_EVERY  = 4;
  private static final char SEPARATOR        = '-';

  public static void format(final StringBuilder sb, final String number) {
    int addSeparadorIn = number.length() % SEPARATOR_EVERY;
    if (addSeparadorIn == 0) {
      addSeparadorIn = SEPARATOR_EVERY;
    }
    for (int i = 0; i < number.length(); i++) {
      if (addSeparadorIn == 0) {
        sb.append(SEPARATOR);
        addSeparadorIn = SEPARATOR_EVERY;
      }
      sb.append(number.charAt(i));
      addSeparadorIn--;
    }
  }

  public static Telephone fromFormatted(final String fmt) {
    String f = GNStrings.trim(fmt);
    if (f.length() == 0) {
      return null; // nothing to parse
    }
    String[] parts;
    int pos;
    // check for type
    parts = f.split(":", 2);
    final String type = (parts.length == 1) ? "" : GNStrings.trim(parts[0]);
    f = GNStrings.trim(parts[parts.length - 1]);
    if (f.length() == 0) {
      return null; // invalid type without number
    }
    // check for country
    final String country;
    if (f.charAt(0) == '+') {
      // if has country, area code is required
      pos = f.indexOf('(');
      if (pos == -1) {
        return null;
      }
      country = GNStrings.trim(f.substring(1, pos - 1));
      f = GNStrings.trim(f.substring(pos));
    } else {
      country = "";
    }
    // check for area
    String area;
    if (f.charAt(0) == '(') {
      pos = f.indexOf(')');
      if (pos == -1) {
        return null;
      }
      area = GNStrings.trim(f.substring(1, pos));
      f = GNStrings.trim(f.substring(pos + 1));
    } else {
      area = "";
    }
    // check for extension if has ','
    String extension;
    pos = f.indexOf(',');
    if (pos == -1) { // not found
      extension = "";
    } else {
      extension = GNStrings.trim(f.substring(pos + 1));
      f = GNStrings.trim(f.substring(0, pos));
    }
    // rest is number
    final String number = GNStrings.trim(f);
    // finally its finished!
    return new Telephone(0, 0, country, area, number, extension, type);
  }

  private final int        id;
  private final int        idperson;
  private final String     country;
  private final String     area;
  private final String     number;
  private final String     extension;

  private final String     type;

  transient private String cachedFormat = null;

  public Telephone() {
    this(0, 0, "", "", "", "", "");
  }

  public Telephone(final int id, final int idperson, final String country,
      final String area, final String number, final String extension,
      final String type) {
    this.id = id;
    this.idperson = idperson;
    this.country = country.toUpperCase().replaceAll("[^A-Z0-9]", "");
    this.area = area.toUpperCase().replaceAll("[^A-Z0-9]", "");
    this.number = number.toUpperCase().replaceAll("[^A-Z0-9]", "");
    this.extension = GNStrings.trim(extension);
    this.type = GNStrings.trim(type);
  }

  private String format() {
    final StringBuilder s = new StringBuilder();
    if (country.length() > 0) {
      s.append('+');
      s.append(country);
      s.append(' ');
    }
    if (area.length() > 0) {
      s.append('(');
      s.append(area);
      s.append(") ");
    }
    format(s, number);
    if (extension.length() > 0) {
      s.append(", ");
      s.append(extension);
    }
    return s.toString();
  }

  public String getArea() {
    return area;
  }

  public String getCountry() {
    return country;
  }

  public String getExtension() {
    return extension;
  }

  public int getId() {
    return id;
  }

  public int getIdPerson() {
    return idperson;
  }

  public String getNumber() {
    return number;
  }

  public String getType() {
    return type;
  }

  public boolean hasNumber() {
    return (number.length() > 0);
  }

  @Override
  @Deprecated
  public String toString() {
    return toString(true);
  }

  public String toString(final boolean withType) {
    if (cachedFormat == null) {
      cachedFormat = format();
    }
    if (!withType) {
      return cachedFormat;
    }
    return String.format("%s: %s", type, cachedFormat);
  }

  public Telephone withType(final String newType) {
    return new Telephone(0, 0, country, area, number, extension, newType);
  }
}
