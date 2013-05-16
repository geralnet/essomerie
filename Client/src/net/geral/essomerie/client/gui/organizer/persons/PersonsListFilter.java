package net.geral.essomerie.client.gui.organizer.persons;

import net.geral.essomerie.shared.person.Addresses;
import net.geral.essomerie.shared.person.Person;
import net.geral.essomerie.shared.person.PersonData;
import net.geral.essomerie.shared.person.Telephones;
import net.geral.lib.strings.GNStrings;

public class PersonsListFilter {
  private final String  search;
  private final int     idSearch;
  private final String  nameSearch;
  private final String  telephoneSearch;
  private final String  addressSearch;

  private final boolean id;
  private final boolean name;
  private final boolean telephone;
  private final boolean address;

  public PersonsListFilter(final String text, final boolean byId,
      final boolean byName, final boolean byTelephone, final boolean byAddress) {
    search = GNStrings.trim(text);

    id = byId;
    name = byName;
    telephone = byTelephone;
    address = byAddress;

    idSearch = id ? GNStrings.toInt(search, 0) : 0;
    nameSearch = name ? Person.createComparissonName(search) : null;
    telephoneSearch = telephone ? Telephones.createComparissonNumber(search)
        : null;
    addressSearch = address ? Addresses.createComparissonAddress(search) : null;
  }

  public boolean accepts(final Person p) {
    if (disabled()) {
      return true;
    }
    if (id) {
      if (p.getId() == idSearch) {
        return true;
      }
    }
    if (name) {
      // TODO phonetic search
      if (p.getComparissonNameAlias().contains(nameSearch)) {
        return true;
      }
    }
    if (p instanceof PersonData) {
      final PersonData pd = (PersonData) p;
      if (telephone) {
        if (pd.getTelephones().matches(telephoneSearch)) {
          return true;
        }
      }
      if (address) {
        if (pd.getAddresses().matches(addressSearch)) {
          return true;
        }
      }
    }
    // not accepted
    return false;
  }

  public boolean address() {
    return address;
  }

  public boolean disabled() {
    return search.length() == 0;
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final PersonsListFilter other = (PersonsListFilter) obj;
    if (disabled() && other.disabled()) {
      // no search text means no filter, doesn't matter where to search
      return true;
    }
    if (address != other.address) {
      return false;
    }
    if (id != other.id) {
      return false;
    }
    if (name != other.name) {
      return false;
    }
    if (!search.equals(other.search)) {
      return false;
    }
    if (telephone != other.telephone) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    if (disabled()) {
      return "".hashCode();
    }

    final int prime = 31;
    int result = 1;
    result = (prime * result) + (address ? 1231 : 1237);
    result = (prime * result) + (id ? 1231 : 1237);
    result = (prime * result) + (name ? 1231 : 1237);
    result = (prime * result) + search.hashCode();
    result = (prime * result) + (telephone ? 1231 : 1237);
    return result;
  }

  public boolean id() {
    return id;
  }

  public boolean name() {
    return name;
  }

  public boolean rejects(final Person p) {
    return !accepts(p);
  }

  public boolean requiresFullData() {
    return telephone || address;
  }

  public String search() {
    return search;
  }

  public boolean telephone() {
    return telephone;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append(getClass().getSimpleName());
    sb.append('[');
    if (!disabled()) {
      sb.append(search);
      sb.append(" { ");
      if (id) {
        sb.append("id ");
      }
      if (name) {
        sb.append("name ");
      }
      if (telephone) {
        sb.append("phone ");
      }
      if (address) {
        sb.append("address ");
      }
      sb.append('}');
    }
    sb.append(']');
    return sb.toString();
  }
}
