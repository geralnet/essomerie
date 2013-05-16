package net.geral.essomerie.shared.bulletinboard;

import java.io.Serializable;

import org.joda.time.LocalDateTime;

public class BulletinBoardEntry implements Comparable<BulletinBoardEntry>,
	Serializable {
    private static final long serialVersionUID = 1L;

    private final BulletinBoardTitle bulletinBoardTitle;
    private final byte[] rtfContents;
    private final LocalDateTime createdWhen;
    private final int createdBy;

    private BulletinBoardEntry(BulletinBoardTitle bulletinBoardTitle,
	    byte[] rtfContents, LocalDateTime createdWhen, int createdBy) {

	if (createdBy < 0)
	    throw new IllegalArgumentException(
		    "user (createdBy) must be non-negative.");

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
	this(new BulletinBoardTitle(id, title), contents, createdWhen,
		createdBy);
    }

    public BulletinBoardEntry(final String title, final byte[] contents) {
	this(new BulletinBoardTitle(0, title), contents, null, 0);
    }

    @Override
    public int compareTo(final BulletinBoardEntry other) {
	return bulletinBoardTitle.getTitle().compareToIgnoreCase(
		other.bulletinBoardTitle.getTitle());
    }

    public int getId() {
	return bulletinBoardTitle.getId();
    }

    public BulletinBoardTitle getBulletinBoardTitle() {
	return bulletinBoardTitle;
    }

    public String getTitle() {
	return bulletinBoardTitle.getTitle();
    }

    public int getCreatedBy() {
	return createdBy;
    }

    public LocalDateTime getCreatedWhen() {
	return createdWhen;
    }

    public byte[] getRtfContents() {
	return rtfContents;
    }
}
