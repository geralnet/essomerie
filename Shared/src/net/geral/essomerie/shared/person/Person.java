package net.geral.essomerie.shared.person;

import java.io.Serializable;

import net.geral.lib.strings.GNStrings;

public class Person implements Serializable, Comparable<Person> {
  private static final long serialVersionUID = 1L;

  public static String createComparissonName(final String name) {
    return GNStrings.removeAccents(name).toUpperCase();
  }

  public static String createComparissonName(final String name,
      final String alias) {
    return GNStrings.removeAccents(name + " (" + alias + ")").toUpperCase();
  }

  public static Integer getIdOrNull(final String text) {
    final int id = GNStrings.toInt(text, 0);
    if (id <= 0) {
      return null;
    }
    return new Integer(id);
  }

  private final int        id;
  private final PersonType type;
  private final String     name;
  private final String     alias;

  private final boolean    deleted;

  transient private String comparissonNameAlias = null;

  public Person() {
    this(0, PersonType.Unknown, "name", "alias", false);
  }

  public Person(final int id, final PersonType type, final String name,
      final String alias, final boolean deleted) {
    // check arguments
    if (id < 0) {
      throw new IllegalArgumentException(
          "id must be zero (new person) or positive (person id).");
    }
    if (name == null) {
      throw new IllegalArgumentException("name cannot be null.");
    }
    if (alias == null) {
      throw new IllegalArgumentException("alias cannot be null.");
    }
    if ((id == 0) && (deleted)) {
      throw new IllegalArgumentException(
          "if id=0 (new person), deleted must be false.");
    }
    if (type == null) {
      throw new IllegalArgumentException("Person type cannot be null.");
    }
    // set arguments
    this.id = id;
    this.type = type;
    this.name = name;
    this.alias = alias;
    this.deleted = deleted;
  }

  @Override
  public int compareTo(final Person o) {
    return getComparissonNameAlias().compareTo(o.getComparissonNameAlias());
  }

  public String getAlias() {
    return alias;
  }

  public String getComparissonNameAlias() {
    if (comparissonNameAlias == null) {
      comparissonNameAlias = createComparissonName(name, alias);
    }
    return comparissonNameAlias;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getNameAlias() {
    if ((name.length() == 0) && (alias.length() == 0)) {
      return String.format("P#%d", id);
    }
    if (name.length() == 0) {
      return alias;
    }
    if (alias.length() == 0) {
      return name;
    }
    return String.format("%s (%s)", name, alias);
  }

  public PersonType getType() {
    return type;
  }

  public boolean isDeleted() {
    return deleted;
  }

  @Override
  public String toString() {
    return String.format("P#%d:%s", id, name);
  }
}
