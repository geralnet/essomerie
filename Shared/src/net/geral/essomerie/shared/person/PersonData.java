package net.geral.essomerie.shared.person;

public class PersonData extends Person {
  private static final long      serialVersionUID = 1L;

  private final String           comments;
  private final PersonLogDetails log;
  private final Telephones       telephones;
  private final Addresses        addresses;

  public PersonData() {
    this(0, PersonType.Unknown, "", "", false, "", null, null, null);
  }

  public PersonData(final int id, final PersonType type, final String name,
      final String alias, final boolean deleted, final String comments,
      final PersonLogDetails log, Telephones telephones, Addresses addresses) {
    super(id, type, name, alias, deleted);
    // check arguments
    if (comments == null) {
      throw new IllegalArgumentException("comments cannot be null.");
    }
    if (telephones == null) {
      telephones = new Telephones(id);
    }
    if (addresses == null) {
      addresses = new Addresses(id);
    }
    // set arguments
    this.comments = comments;
    this.log = log;
    this.telephones = telephones;
    this.addresses = addresses;
  }

  public PersonData(final PersonData p, final Telephones telephones,
      final Addresses addresses) {
    this(p.getId(), p.getType(), p.getName(), p.getAlias(), p.isDeleted(), p
        .getComments(), p.getLogDetails(), telephones, addresses);
  }

  public Addresses getAddresses() {
    return addresses;
  }

  public String getComments() {
    return comments;
  }

  public PersonLogDetails getLogDetails() {
    return log;
  }

  public Telephones getTelephones() {
    return telephones;
  }

  @Override
  public String toString() {
    return super.toString() + "[" + telephones.getCount() + "T;"
        + addresses.getCount() + "A]";
  }
}
