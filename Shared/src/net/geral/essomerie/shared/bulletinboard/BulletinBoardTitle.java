package net.geral.essomerie.shared.bulletinboard;

import java.io.Serializable;

import net.geral.lib.util.StringUtils;

public class BulletinBoardTitle implements Serializable,
    Comparable<BulletinBoardTitle> {

  public static final char  PATH_SEPARATOR   = '/';

  private static final long serialVersionUID = 1L;
  private final int         id;
  private final String      fullTitle;
  private transient String  path             = null;
  private transient String  title            = null;

  public BulletinBoardTitle(final int id, String fullTitle) {
    if (id < 0) {
      throw new IllegalArgumentException("id must be non-negative.");
    }
    if (fullTitle == null) {
      throw new IllegalArgumentException("title cannot be null.");
    }
    fullTitle = StringUtils.trim(fullTitle);
    fullTitle = fullTitle.replaceAll("^" + PATH_SEPARATOR + "+", "");
    fullTitle = fullTitle.replaceAll(PATH_SEPARATOR + "$+", "");
    if (fullTitle.length() == 1) {
      fullTitle = "???";
    }
    fullTitle = "/" + fullTitle;

    this.id = id;
    this.fullTitle = fullTitle;
  }

  @Override
  public int compareTo(final BulletinBoardTitle o) {
    return fullTitle.compareTo(o.fullTitle);
  }

  public String getFullTitle() {
    return fullTitle;
  }

  public int getId() {
    return id;
  }

  public String getPath() {
    if (path == null) {
      final int index = fullTitle.lastIndexOf(PATH_SEPARATOR);
      path = fullTitle.substring(0, index + 1);
    }
    return path;
  }

  public String getTitle() {
    if (title == null) {
      final int index = fullTitle.lastIndexOf(PATH_SEPARATOR);
      title = fullTitle.substring(index + 1);
    }
    return title;
  }

  public String getTitleArrow() {
    return fullTitle.substring(1).replaceAll(String.valueOf(PATH_SEPARATOR),
        " \u2192 ");
  }

  public boolean inPath(final String ancestor) {
    return getPath().startsWith(ancestor);
  }

  @Override
  public String toString() {
    return this.getClass().getSimpleName() + "[id=" + id + ";full=" + fullTitle
        + "]";
  }
}
