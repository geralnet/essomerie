package net.geral.essomerie.shared.catalog;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;

public class CatalogGroup extends CatalogGroupItemBase {
  private static final long serialVersionUID = 1L;
  private static String     defaultTitle     = "New Group";

  public static void setDefaultTitle(final String title) {
    defaultTitle = title;
  }

  private final int                     idparent;
  private final HashMap<String, String> titles;
  private final HashMap<String, String> subtitles;

  private transient String              fullTitle = null;

  public CatalogGroup(final int id, final int idparent, final int order,
      final int idpublication, final ArrayList<String> languages,
      final ArrayList<String> titles, final ArrayList<String> subtitles) {
    super(false, id, order, idpublication);
    if ((languages.size() != titles.size())
        || (languages.size() != subtitles.size())) {
      throw new InvalidParameterException(
          "languages, titles and subtitles must have the same length.");
    }

    this.idparent = idparent;
    this.titles = new HashMap<>();
    this.subtitles = new HashMap<>();

    for (int i = 0; i < languages.size(); i++) {
      final String language = languages.get(i);
      this.titles.put(language, titles.get(i));
      this.subtitles.put(language, subtitles.get(i));
    }
  }

  private CatalogGroup(final int id, final int idparent, final int order,
      final int idpublication, final HashMap<String, String> titles,
      final HashMap<String, String> subtitles) {
    super(false, id, order, idpublication);
    this.idparent = idparent;
    this.titles = titles;
    this.subtitles = subtitles;
  }

  public int getIdParent() {
    return idparent;
  }

  public synchronized String[] getLanguages() {
    return titles.keySet().toArray(new String[titles.size()]);
  }

  public synchronized String getSubtitle(final String language) {
    final String s = subtitles.get(language);
    return (s == null) ? "" : s;
  }

  public String getTitle() {
    if (fullTitle == null) {
      synchronized (this) {
        if (titles.size() == 0) {
          fullTitle = defaultTitle;
        } else {
          final StringBuilder sb = new StringBuilder();
          boolean slash = false;
          for (final String t : titles.values()) {
            if (slash) {
              sb.append(" / ");
            }
            sb.append(t);
            slash = true;
          }
          fullTitle = sb.toString();
        }
      }
    }
    return fullTitle;
  }

  public synchronized String getTitle(final String language) {
    final String s = titles.get(language);
    return (s == null) ? "" : s;
  }

  public CatalogGroup withIdParent(final int idparent) {
    return new CatalogGroup(getId(), idparent, getOrder(), getIdPublication(),
        titles, subtitles);
  }

  public CatalogGroup withTitles(final ArrayList<String> languages,
      final ArrayList<String> titles, final ArrayList<String> subtitles) {
    return new CatalogGroup(getId(), idparent, getOrder(), getIdPublication(),
        languages, titles, subtitles);
  }
}
