package net.geral.essomerie.shared.person;

import java.io.Serializable;
import java.util.ArrayList;

public class Addresses implements Serializable {
  private static final long serialVersionUID = 1L;

  public static String createComparissonAddress(final String search) {
    return search.toUpperCase().replaceAll("[^A-Za-z0-9]", "");
  }

  private final int                idperson;
  private final ArrayList<Address> addresses;
  private transient String         comparissonAddress = null;

  public Addresses(final int idperson) {
    this(idperson, new ArrayList<Address>());
  }

  public Addresses(final int idperson, final ArrayList<Address> addresses) {
    if (idperson < 0) {
      throw new IllegalArgumentException("idperson must be non-negative");
    }
    if (addresses == null) {
      throw new IllegalArgumentException("telephones cannot be null");
    }
    this.idperson = idperson;
    this.addresses = addresses;
  }

  private void createComparissonAddress() {
    if (addresses.size() == 0) {
      comparissonAddress = "";
      return;
    }
    final StringBuilder sb = new StringBuilder();
    for (final Address a : addresses) {
      sb.append(createComparissonAddress(a.toString()));
      sb.append(' ');
    }
    comparissonAddress = sb.toString();
  }

  public Address get(final int index) {
    return addresses.get(index);
  }

  public synchronized Address[] getAll() {
    return addresses.toArray(new Address[addresses.size()]);
  }

  public int getCount() {
    return addresses.size();
  }

  public int getIdPerson() {
    return idperson;
  }

  public boolean matches(final String withComparissonAddress) {
    if (comparissonAddress == null) {
      createComparissonAddress();
    }
    return comparissonAddress.contains(withComparissonAddress);
  }
}
