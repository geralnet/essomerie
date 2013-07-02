package net.geral.essomerie.shared.catalog;

import java.io.Serializable;
import java.security.InvalidParameterException;

public class CatalogGroupItemBase implements Serializable,
    Comparable<CatalogGroupItemBase> {
  private static final long serialVersionUID = 1L;

  private final int         id;
  private final int         order;
  private final int         idpublication;
  private final boolean     isItem;

  public CatalogGroupItemBase(final boolean isItem, final int id,
      final int order, final int idpublication) {
    if (id <= 0) {
      throw new InvalidParameterException("id must be > 0 (was " + id + ").");
    }
    this.id = id;
    this.order = order;
    this.idpublication = idpublication;
    this.isItem = isItem;

    if (!(isGroup() ^ isItem())) {
      throw new RuntimeException(
          "Must be only an item or only a group, not neither or both.");
    }
  }

  @Override
  public int compareTo(final CatalogGroupItemBase o) {
    if (order != o.order) {
      return Integer.compare(order, o.order);
    }
    return Integer.compare(id, o.id);
  }

  public int getId() {
    return id;
  }

  public int getIdPublication() {
    return idpublication;
  }

  public int getOrder() {
    return order;
  }

  public boolean isGroup() {
    return !isItem;
  }

  public boolean isItem() {
    return isItem;
  }
}
