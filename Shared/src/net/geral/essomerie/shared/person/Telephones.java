package net.geral.essomerie.shared.person;

import java.io.Serializable;
import java.util.ArrayList;

public class Telephones implements Serializable {
  private static final long serialVersionUID = 1L;

  public static String createComparissonNumber(final String search) {
    return search.toUpperCase().replaceAll("[^A-Za-z0-9\\#\\*]", "");
  }

  private final int                  idperson;
  private final ArrayList<Telephone> telephones;
  private transient String           comparissonNumber = null;

  public Telephones(final int idperson) {
    this(idperson, new ArrayList<Telephone>());
  }

  public Telephones(final int idperson, final ArrayList<Telephone> telephones) {
    if (idperson < 0) {
      throw new IllegalArgumentException("idperson must be non-negative");
    }
    if (telephones == null) {
      throw new IllegalArgumentException("telephones cannot be null");
    }
    this.idperson = idperson;
    this.telephones = telephones;
  }

  private void createComparissonNumber() {
    if (telephones.size() == 0) {
      comparissonNumber = "";
      return;
    }
    final StringBuilder sb = new StringBuilder();
    for (final Telephone t : telephones) {
      sb.append(createComparissonNumber(t.toString(false)));
      sb.append(' ');
    }
    comparissonNumber = sb.toString();
  }

  public synchronized Telephone[] getAll() {
    return telephones.toArray(new Telephone[telephones.size()]);
  }

  public int getCount() {
    return telephones.size();
  }

  public int getIdperson() {
    return idperson;
  }

  public boolean matches(final String withComparissonNumber) {
    if (comparissonNumber == null) {
      createComparissonNumber();
    }
    return comparissonNumber.contains(withComparissonNumber);
  }
}
