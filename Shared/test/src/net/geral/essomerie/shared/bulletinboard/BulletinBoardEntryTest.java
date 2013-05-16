package net.geral.essomerie.shared.bulletinboard;

import static org.junit.Assert.fail;

import org.joda.time.LocalDateTime;
import org.junit.Test;

public class BulletinBoardEntryTest {
    @Test
    public void testBulletinBoardEntry_id_title() {
	new BulletinBoardEntry(0, "A");
    }

    @Test
    public void testBulletinBoardEntry_title_contents() {
	new BulletinBoardEntry("A", new byte[0]);
    }

    @Test
    public void testBulletinBoardEntry_id_title_contents() {
	new BulletinBoardEntry(1, "A", new byte[0]);
    }

    @Test
    public void testBulletinBoardEntry_id_title_contents_created_user() {
	new BulletinBoardEntry(1, "A", new byte[0], LocalDateTime.now(), 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBulletinBoardEntry_InvalidUser() {
	new BulletinBoardEntry(1, "A", new byte[0], LocalDateTime.now(), -1);
    }

    @Test
    public void testCompareTo() {
	BulletinBoardEntry a = new BulletinBoardEntry(1, "A");
	BulletinBoardEntry b1 = new BulletinBoardEntry(1, "B");
	BulletinBoardEntry b2 = new BulletinBoardEntry(1, "B");
	BulletinBoardEntry c = new BulletinBoardEntry(1, "C");
	BulletinBoardEntry d = new BulletinBoardEntry(1, "D");

	if (a.compareTo(a) != 0)
	    fail("a != a ??");
	if (a.compareTo(b1) >= 0)
	    fail("a >= b ??");
	if (a.compareTo(b2) >= 0)
	    fail("a >= b ??");
	if (a.compareTo(c) >= 0)
	    fail("a >= c ??");
	if (a.compareTo(d) >= 0)
	    fail("a >= d ??");

	if (b1.compareTo(b2) != 0)
	    fail("b != b ??");
	if (b1.compareTo(c) >= 0)
	    fail("b >= c ??");
	if (b1.compareTo(d) >= 0)
	    fail("b >= d ??");

	if (b2.compareTo(c) >= 0)
	    fail("b >= c ??");
	if (b2.compareTo(d) >= 0)
	    fail("b >= d ??");

	if (c.compareTo(c) != 0)
	    fail("c != c ??");
	if (c.compareTo(d) >= 0)
	    fail("c >= d ??");
    }
}
