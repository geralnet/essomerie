package net.geral.essomerie.shared.person;

import java.io.Serializable;
import java.util.ArrayList;

public class PersonDocuments implements Serializable {
  private static final long               serialVersionUID = 1L;

  private final int                       idperson;
  private final ArrayList<PersonDocument> documents;

  public PersonDocuments(final int idperson) {
    this(idperson, new ArrayList<PersonDocument>());
  }

  public PersonDocuments(final int idperson,
      final ArrayList<PersonDocument> documents) {
    if (idperson < 0) {
      throw new IllegalArgumentException("idperson must be non-negative");
    }
    if (documents == null) {
      throw new IllegalArgumentException("documents cannot be null");
    }
    this.idperson = idperson;
    this.documents = documents;
  }

  public PersonDocument get(final int index) {
    return documents.get(index);
  }

  public synchronized PersonDocument[] getAll() {
    return documents.toArray(new PersonDocument[documents.size()]);
  }

  public int getCount() {
    return documents.size();
  }

  public int getIdPerson() {
    return idperson;
  }
}
