package net.geral.essomerie.shared.person;

import java.io.Serializable;

import net.geral.essomerie.shared.money.Money;

import org.joda.time.LocalDateTime;

public class PersonSale implements Serializable {
  private static final long   serialVersionUID = 1L;
  private final int           idsale;
  private final LocalDateTime when;
  private final Money         price;
  private final String        comments;

  public PersonSale() {
    this(0, LocalDateTime.now(), Money.zero(), "");
  }

  public PersonSale(final int idsale, final LocalDateTime when,
      final Money price, final String comments) {
    this.idsale = idsale;
    this.when = when;
    this.price = price;
    this.comments = comments;
  }

  public String getComments() {
    return comments;
  }

  public int getIdSale() {
    return idsale;
  }

  public Money getPrice() {
    return price;
  }

  public LocalDateTime getWhen() {
    return when;
  }

  public PersonSale withComments(final String comments) {
    return new PersonSale(idsale, when, price, comments);
  }

  public PersonSale withPrice(final Money price) {
    return new PersonSale(0, when, price, comments);
  }

  public PersonSale withWhen(final LocalDateTime when) {
    return new PersonSale(0, when, price, comments);
  }
}
