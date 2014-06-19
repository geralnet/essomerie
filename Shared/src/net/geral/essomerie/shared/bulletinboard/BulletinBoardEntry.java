package net.geral.essomerie.shared.bulletinboard;

import java.io.Serializable;

import org.joda.time.LocalDateTime;

public class BulletinBoardEntry implements Comparable<BulletinBoardEntry>,
    Serializable {
  private static final long        serialVersionUID = 1L;

  private final BulletinBoardTitle bulletinBoardTitle;
  private final byte[]             rtfContents;
  private final LocalDateTime      createdWhen;
  private final int                createdBy;

  private BulletinBoardEntry(final BulletinBoardTitle bulletinBoardTitle,
      final byte[] rtfContents, final LocalDateTime createdWhen,
      final int createdBy) {

    if (createdBy < 0) {
      throw new IllegalArgumentException(
          "user (createdBy) must be non-negative.");
    }

    this.bulletinBoardTitle = bulletinBoardTitle;
    this.rtfContents = rtfContents;
    this.createdWhen = createdWhen;
    this.createdBy = createdBy;
  }

  public BulletinBoardEntry(final int id, final String title) {
    this(new BulletinBoardTitle(id, title), null, null, 0);
  }

  public BulletinBoardEntry(final int id, final String title,
      final byte[] contents) {
    this(new BulletinBoardTitle(id, title), contents, null, 0);
  }

  public BulletinBoardEntry(final int id, final String title,
      final byte[] contents, final LocalDateTime createdWhen,
      final int createdBy) {
    this(new BulletinBoardTitle(id, title), contents, createdWhen, createdBy);
  }

  public BulletinBoardEntry(final String title, final byte[] contents) {
    this(new BulletinBoardTitle(0, title), contents, null, 0);
  }

  @Override
  public int compareTo(final BulletinBoardEntry other) {
    return bulletinBoardTitle.getFullTitle().compareToIgnoreCase(
        other.bulletinBoardTitle.getFullTitle());
  }

  public BulletinBoardTitle getBulletinBoardTitle() {
    return bulletinBoardTitle;
  }

  public int getCreatedBy() {
    return createdBy;
  }

  public LocalDateTime getCreatedWhen() {
    return createdWhen;
  }

  public String getFullTitle() {
    return bulletinBoardTitle.getFullTitle();
  }

  public int getId() {
    return bulletinBoardTitle.getId();
  }

  public byte[] getRtfContents() {
    return rtfContents;
  }

  public String getTitle() {
    return bulletinBoardTitle.getTitle();
  }

  public String getTitleArrow() {
    return bulletinBoardTitle.getTitleArrow();
  }
}
