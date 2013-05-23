package net.geral.essomerie.shared.person;

import java.io.Serializable;
import java.util.ArrayList;

import net.geral.essomerie._shared.Dinheiro;

public class PersonSales implements Serializable {
  private static final long           serialVersionUID = 1L;
  private final int                   idperson;
  private final ArrayList<PersonSale> sales;

  private transient Dinheiro          cacheTotal       = null;
  private transient Dinheiro          cacheAverage     = null;
  private transient PersonSale        cacheFirstOrder  = null;
  private transient PersonSale        cacheLastOrder   = null;

  public PersonSales(final int idperson) {
    this(idperson, new ArrayList<PersonSale>());
  }

  public PersonSales(final int idperson, final ArrayList<PersonSale> sales) {
    if (idperson < 0) {
      throw new IllegalArgumentException("idperson must be non-negative");
    }
    if (sales == null) {
      throw new IllegalArgumentException("sales cannot be null");
    }
    this.idperson = idperson;
    this.sales = sales;
  }

  public synchronized void calculateCache() {
    long sum = 0;
    PersonSale first = null;
    PersonSale last = null;
    int n = 0;
    for (final PersonSale s : sales) {
      sum += s.getPrice().getCentavos();
      if ((last == null) || (last.getWhen().compareTo(s.getWhen()) < 0)) {
        last = s;
      }
      if ((first == null) || (first.getWhen().compareTo(s.getWhen()) > 0)) {
        first = s;
      }
      n++;
    }
    // set cache
    cacheTotal = new Dinheiro(sum);
    cacheAverage = (n == 0) ? null : new Dinheiro(sum / n);
    cacheFirstOrder = first;
    cacheLastOrder = last;
  }

  public synchronized PersonSale[] getAll() {
    return sales.toArray(new PersonSale[sales.size()]);
  }

  public Dinheiro getCacheAverage() {
    if (cacheAverage == null) {
      calculateCache();
    }
    return cacheAverage;
  }

  public PersonSale getCacheFirstOrder() {
    return cacheFirstOrder;
  }

  public PersonSale getCacheLastOrder() {
    return cacheLastOrder;
  }

  public Dinheiro getCacheTotal() {
    if (cacheTotal == null) {
      calculateCache();
    }
    return cacheTotal;
  }

  public int getCount() {
    return sales.size();
  }

  public int getIdPerson() {
    return idperson;
  }
}
