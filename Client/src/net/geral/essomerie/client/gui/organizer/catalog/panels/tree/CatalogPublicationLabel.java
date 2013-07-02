package net.geral.essomerie.client.gui.organizer.catalog.panels.tree;

import net.geral.essomerie.client.resources.S;
import net.geral.essomerie.shared.catalog.CatalogPublication;

public class CatalogPublicationLabel {
  private final String             label;
  private final CatalogPublication publication;

  public CatalogPublicationLabel() {
    this(false, null);
  }

  public CatalogPublicationLabel(final boolean dateFirst,
      final CatalogPublication catalogPublication) {
    publication = catalogPublication;
    if (publication == null) {
      label = S.ORGANIZER_CATALOG_UNPUBLISHED.s();
    } else {
      final String when = catalogPublication.getWhen().toString(
          S.FORMAT_DATETIME_SIMPLE_NO_SECONDS.s());
      final String comments = catalogPublication.getComments();
      if (dateFirst) {
        label = when + (comments.length() > 0 ? " (" + comments + ")" : "");
      } else {
        label = comments + (comments.length() > 0 ? " (" + when + ")" : "");
      }
    }
  }

  public CatalogPublication getPublication() {
    return publication;
  }

  @Override
  public String toString() {
    return label;
  }
}
