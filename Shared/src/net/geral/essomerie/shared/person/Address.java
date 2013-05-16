package net.geral.essomerie.shared.person;

import java.io.Serializable;

public class Address implements Serializable {
  private static final long serialVersionUID = 1L;

  private final int         id;
  private final int         idperson;
  private final String      postalCode;
  private final String      address;
  private final String      suburb;
  private final String      city;
  private final String      state;
  private final String      country;
  private final String      comments;
  private final int         classification;

  public Address() {
    this(0, 0, "", "", "", "", "", "", "", 0);
  }

  public Address(final int id, final int idperson, final String postalCode,
      final String address, final String suburb, final String city,
      final String state, final String country, final String comments,
      final int classification) {
    this.id = id;
    this.idperson = idperson;
    this.postalCode = postalCode;
    this.address = address;
    this.suburb = suburb;
    this.city = city;
    this.state = state;
    this.country = country;
    this.comments = comments;
    this.classification = classification;
  }

  public String getAddress() {
    return address;
  }

  public String getCity() {
    return city;
  }

  public int getClassification() {
    return classification;
  }

  public String getComments() {
    return comments;
  }

  public String getCountry() {
    return country;
  }

  public int getId() {
    return id;
  }

  public int getIdPerson() {
    return idperson;
  }

  public String getPostalCode() {
    return postalCode;
  }

  public String getState() {
    return state;
  }

  public String getSuburb() {
    return suburb;
  }

  public boolean hasAddress() {
    return (address.length() > 0);
  }

  @Override
  public String toString() {
    return postalCode + " - " + address + " - " + suburb + "/" + city + "/"
        + state + "/" + country + " - " + comments + " (" + classification
        + ")";
  }

  public Address withAddress(final String newAddress) {
    return new Address(0, id, postalCode, newAddress, suburb, city, state,
        country, comments, classification);
  }

  public Address withClassification(final int newClassification) {
    return new Address(0, id, postalCode, address, suburb, city, state,
        country, comments, newClassification);
  }

  public Address withComments(final String newComments) {
    return new Address(0, id, postalCode, address, suburb, city, state,
        country, newComments, classification);
  }

  public Address withCountry(final String newCountry) {
    return new Address(0, id, postalCode, address, suburb, city, state,
        newCountry, comments, classification);
  }

  public Address withPostalCode(final String newPostalCode) {
    return new Address(0, id, newPostalCode, address, suburb, city, state,
        country, comments, classification);
  }

  public Address withState(final String newState) {
    return new Address(0, id, postalCode, address, suburb, city, newState,
        country, comments, classification);
  }

  public Address withSuburb(final String newSuburb) {
    return new Address(0, id, postalCode, address, newSuburb, city, state,
        country, comments, classification);
  }
}
