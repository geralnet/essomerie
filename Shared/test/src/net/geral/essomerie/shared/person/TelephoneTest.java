package net.geral.essomerie.shared.person;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

public class TelephoneTest {
  private static final String DEFAULT_COUNTRY = "DC";
  private static final String DEFAULT_AREA    = "DA";

  @BeforeClass
  public static void setDefaults() {
    Telephone.setDefaults(DEFAULT_COUNTRY, DEFAULT_AREA);
  }

  @Test
  public void fromFormat1() {
    final Telephone t = Telephone
        .fromFormatted("type: +11 (22) 345-678.9, ext");
    assertEquals(0, t.getId());
    assertEquals(0, t.getIdPerson());
    assertEquals("11", t.getCountry());
    assertEquals("22", t.getArea());
    assertEquals("3456789", t.getNumber());
    assertEquals("ext", t.getExtension());
    assertEquals("type", t.getType());
  }

  @Test
  public void fromFormat2() {
    final Telephone t = Telephone.fromFormatted("type:+11(22)345-678.9,ext");
    assertEquals(0, t.getId());
    assertEquals(0, t.getIdPerson());
    assertEquals("11", t.getCountry());
    assertEquals("22", t.getArea());
    assertEquals("3456789", t.getNumber());
    assertEquals("ext", t.getExtension());
    assertEquals("type", t.getType());
  }

  @Test
  public void testChangeType() {
    final Telephone t1 = new Telephone(0, 0, null, null, "N", "", "type1");
    final Telephone t2 = t1.withType("type2");
    assertEquals("type1", t1.getType());
    assertEquals("type2", t2.getType());
  }

  @Test
  public void testTelephoneFullArgs() {
    final Telephone t = new Telephone(1, 2, "c", "A-B", "N.C-", "EXT", "t:ype");
    assertEquals(1, t.getId());
    assertEquals(2, t.getIdPerson());
    assertEquals("C", t.getCountry());
    assertEquals("AB", t.getArea());
    assertEquals("NC", t.getNumber());
    assertEquals("EXT", t.getExtension());
    assertEquals("t:ype", t.getType());
    assertTrue(t.hasNumber());
  }

  @Test
  public void testTelephoneNoArgs() {
    final Telephone t = new Telephone();
    assertEquals(0, t.getId());
    assertEquals(0, t.getIdPerson());
    assertEquals(DEFAULT_COUNTRY, t.getCountry());
    assertEquals(DEFAULT_AREA, t.getArea());
    assertEquals("", t.getNumber());
    assertEquals("", t.getExtension());
    assertEquals("", t.getType());
    assertFalse(t.hasNumber());
  }

  @Test
  public void testTelephoneNumeric() {
    final String i = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    final String e = "22233344455566677778889999";
    final String o = Telephone.toNumeric(i);
    assertEquals(e, o);
  }
}
