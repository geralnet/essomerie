package net.geral.essomerie.shared.bulletinboard;

import java.io.Serializable;

import net.geral.lib.strings.GNStrings;

public class BulletinBoardTitle implements Serializable,
    Comparable<BulletinBoardTitle> {

  private static final long serialVersionUID = 1L;
  private final int         id;
  private final String      title;

  public BulletinBoardTitle(final int id, String title) {
    if (id < 0) {
      throw new IllegalArgumentException("id must be non-negative.");
    }
    if (title == null) {
      throw new IllegalArgumentException("title cannot be null.");
    }
    title = GNStrings.trim(title);
    if (title.length() == 0) {
      throw new IllegalArgumentException("title cannot be empty.");
    }

    this.id = id;
    this.title = title;
  }

  @Override
  public int compareTo(final BulletinBoardTitle o) {
    return title.compareTo(o.title);
  }

  public int getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }
}
