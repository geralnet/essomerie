package net.geral.essomerie.shared.person;

import java.io.Serializable;

import net.geral.essomerie._shared.Dinheiro;

import org.joda.time.LocalDateTime;

public class PersonSale implements Serializable {
  private static final long   serialVersionUID = 1L;
  private final int           id;
  private final LocalDateTime when;
  private final Dinheiro      price;
  private final String        comments;

  public PersonSale() {
    this(0, LocalDateTime.now(), new Dinheiro(), "");
  }

  public PersonSale(final int id, final LocalDateTime when,
      final Dinheiro price, final String comments) {
    this.id = id;
    this.when = when;
    this.price = price;
    this.comments = comments;
  }

  public String getComments() {
    return comments;
  }

  public int getId() {
    return id;
  }

  public Dinheiro getPrice() {
    return price;
  }

  public LocalDateTime getWhen() {
    return when;
  }

  public PersonSale withComments(final String comments) {
    return new PersonSale(id, when, price, comments);
  }

  public PersonSale withPrice(final Dinheiro price) {
    return new PersonSale(0, when, price, comments);
  }

  public PersonSale withWhen(final LocalDateTime when) {
    return new PersonSale(0, when, price, comments);
  }
}
