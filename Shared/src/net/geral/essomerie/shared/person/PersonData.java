package net.geral.essomerie.shared.person;

public class PersonData extends Person {
  private static final long      serialVersionUID = 1L;

  private final String           comments;
  private final PersonLogDetails log;
  private final Telephones       telephones;
  private final Addresses        addresses;
  private final PersonDocuments  documents;
  private final PersonSales      sales;

  public PersonData() {
    this(0, PersonType.Natural, "", "", false, "", null, null, null, null, null);
  }

  public PersonData(final int id, final PersonType type, final String name,
      final String alias, final boolean deleted, final String comments,
      final PersonLogDetails log, Telephones telephones, Addresses addresses,
      PersonDocuments documents, PersonSales sales) {
    super(id, type, name, alias, deleted);
    // check arguments
    if (comments == null) {
      throw new IllegalArgumentException("comments cannot be null.");
    }
    telephones = (telephones == null) ? new Telephones(id) : telephones;
    addresses = (addresses == null) ? new Addresses(id) : addresses;
    documents = (documents == null) ? new PersonDocuments(id) : documents;
    sales = (sales == null) ? new PersonSales(id) : sales;

    // set arguments
    this.comments = comments;
    this.log = log;
    this.telephones = telephones;
    this.addresses = addresses;
    this.documents = documents;
    this.sales = sales;
  }

  public Addresses getAddresses() {
    return addresses;
  }

  public String getComments() {
    return comments;
  }

  public PersonDocuments getDocuments() {
    return documents;
  }

  public PersonLogDetails getLogDetails() {
    return log;
  }

  public PersonSales getSales() {
    return sales;
  }

  public Telephones getTelephones() {
    return telephones;
  }

  @Override
  public String toString() {
    return super.toString() + "[" + telephones.getCount() + "T;"
        + addresses.getCount() + "A]";
  }

  public PersonData withTelephonesAddresses(final Telephones telephones,
      final Addresses addresses) {
    return new PersonData(getId(), getType(), getName(), getAlias(),
        isDeleted(), comments, log, telephones, addresses, documents, sales);
  }
}
