package net.geral.essomerie.shared.person;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;

public class PersonFullData implements Serializable {
  private static final long                                  serialVersionUID = 1L;
  private final PersonData[]                                 persons;
  private final Telephone[]                                  telephones;
  private final Address[]                                    addresses;
  private final PersonDocument[]                             documents;

  private transient Hashtable<Integer, ArrayList<Telephone>> id2telephones    = null;
  private transient Hashtable<Integer, ArrayList<Address>>   id2addresses     = null;

  public PersonFullData(final PersonData[] persons,
      final Telephone[] telephones, final Address[] addresses,
      final PersonDocument[] documents) {
    this.persons = persons;
    this.telephones = telephones;
    this.addresses = addresses;
    this.documents = documents;
  }

  public synchronized Addresses getAddresses(final int idperson) {
    // create map ?
    if (id2addresses == null) {
      id2addresses = new Hashtable<>();
      for (final Address a : addresses) {
        ArrayList<Address> al = id2addresses.get(a.getIdPerson());
        if (al == null) {
          al = new ArrayList<>();
          id2addresses.put(a.getIdPerson(), al);
        }
        al.add(a);
      }
    }
    final ArrayList<Address> al = id2addresses.get(idperson);
    if (al == null) {
      return new Addresses(idperson);
    }
    return new Addresses(idperson, al);
  }

  public PersonData[] getPersons() {
    return persons;
  }

  public synchronized Telephones getTelephones(final int idperson) {
    // create map ?
    if (id2telephones == null) {
      id2telephones = new Hashtable<>();
      for (final Telephone t : telephones) {
        ArrayList<Telephone> tl = id2telephones.get(t.getIdPerson());
        if (tl == null) {
          tl = new ArrayList<>();
          id2telephones.put(t.getIdPerson(), tl);
        }
        tl.add(t);
      }
    }
    final ArrayList<Telephone> tl = id2telephones.get(idperson);
    if (tl == null) {
      return new Telephones(idperson);
    }
    return new Telephones(idperson, tl);
  }

  @Override
  public String toString() {
    int bytes = -1;
    try {
      final ByteArrayOutputStream bOut = new ByteArrayOutputStream();
      final ObjectOutputStream oOut = new ObjectOutputStream(bOut);
      oOut.writeObject(this);
      oOut.close();
      bytes = bOut.toByteArray().length;
    } catch (final IOException e) {
    }
    return getClass().getSimpleName() + "[" + bytes + " bytes]";
  }
}
