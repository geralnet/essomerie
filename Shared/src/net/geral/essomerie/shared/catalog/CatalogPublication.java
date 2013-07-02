package net.geral.essomerie.shared.catalog;

import java.io.Serializable;

import org.joda.time.LocalDateTime;

public class CatalogPublication implements Serializable {
  private static final long   serialVersionUID = 1L;

  private final int           id;
  private final LocalDateTime when;
  private final int           by;
  private final String        comments;

  public CatalogPublication(final int id, final LocalDateTime when,
      final int by, final String comments) {
    this.id = id;
    this.when = when;
    this.by = by;
    this.comments = comments;
  }

  public int getBy() {
    return by;
  }

  public String getComments() {
    return comments;
  }

  public int getId() {
    return id;
  }

  public LocalDateTime getWhen() {
    return when;
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "[id=" + id + ";cmt=" + comments + "]";
  }
}
