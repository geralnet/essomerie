package net.geral.essomerie.shared.bulletinboard;

import org.junit.Test;

public class BulletinBoardTitleTest {

    @Test
    public void testBulletinBoardTitle_A() {
	new BulletinBoardTitle(0, "A");
    }

    @Test
    public void testBulletinBoardTitle_B() {
	new BulletinBoardTitle(1, "B");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBulletinBoardTitle_InvalidId() {
	new BulletinBoardTitle(-1, "A");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBulletinBoardTitle_InvalidTitle1() {
	new BulletinBoardTitle(0, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBulletinBoardTitle_InvalidTitle2() {
	new BulletinBoardTitle(0, "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBulletinBoardTitle_InvalidTitle3() {
	new BulletinBoardTitle(0, " ");
    }
}
