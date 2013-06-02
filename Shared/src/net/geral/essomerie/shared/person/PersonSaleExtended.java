package net.geral.essomerie.shared.person;

import net.geral.essomerie._shared.Dinheiro;

import org.joda.time.LocalDateTime;

public class PersonSaleExtended extends PersonSale {
  private static final long   serialVersionUID = 1L;
  private final int           iduser;
  private final int           idperson;
  private final LocalDateTime registered;

  public PersonSaleExtended() {
    this(0, 0, LocalDateTime.now(), 0, LocalDateTime.now(), new Dinheiro(), "");
  }

  public PersonSaleExtended(final int iduser, final int idperson,
      final LocalDateTime registered, final int idsale,
      final LocalDateTime when, final Dinheiro price, final String comments) {
    super(idsale, when, price, comments);
    this.iduser = iduser;
    this.idperson = idperson;
    this.registered = registered;
  }

  public LocalDateTime getDateTimeRegistered() {
    return registered;
  }

  public int getIdPerson() {
    return idperson;
  }

  public int getIdUser() {
    return iduser;
  }
}
