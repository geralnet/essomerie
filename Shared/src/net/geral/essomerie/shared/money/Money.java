package net.geral.essomerie.shared.money;

import java.io.Serializable;

public class Money implements Serializable, Comparable<Money> {
  private static final long  serialVersionUID   = 1L;

  private static MoneyType[] types              = null;
  private static char        decimalSeparator   = ',';
  private static char        thousandsSeparator = '.';

  private static final Money zero               = new Money(0L);

  private static MoneyType[] createDefaultStandardTypes() {
    return new MoneyType[] { new MoneyType("$100", 10000),
        new MoneyType("$50", 5000), new MoneyType("$20", 2000),
        new MoneyType("$10", 1000), new MoneyType("$5", 500),
        new MoneyType("$2", 200), new MoneyType("$1", 100),
        new MoneyType("50¢", 50), new MoneyType("25¢", 25),
        new MoneyType("10¢", 10), new MoneyType("5¢", 5),
        new MoneyType("1¢", 1) };
  }

  public static Money fromDouble(final double d) {
    return new Money((long) (d * 100));
  }

  public static Money fromLong(final long value) {
    return new Money(value);
  }

  public static Money fromString(String s) {
    s = (s.startsWith("-") ? "-" : "")
        + s.replace(String.valueOf(thousandsSeparator), "")
            .replace(String.valueOf(decimalSeparator), ".")
            .replaceAll("[^0-9\\.]", "");
    // if no decimal part, use long
    if (s.indexOf('.') == -1) {
      return fromLong(Long.parseLong(s) * 100);
    }
    // TODO if decimal has 1 or 2 digits only, use long
    // with decimal part, use double
    return fromDouble(Double.parseDouble(s));
  }

  public static char getDecimalSeparator() {
    return decimalSeparator;
  }

  public static final MoneyType[] getStandardTypes() {
    if (types == null) {
      types = createDefaultStandardTypes();
    }
    return types.clone();
  }

  public static char getThousandsSeparator() {
    return thousandsSeparator;
  }

  public static Money remaining(final Money d1, final Money d2) {
    return new Money(d1.value % d2.value);
  }

  public static void setSeparators(final char decimal, final char thousands) {
    decimalSeparator = decimal;
    thousandsSeparator = thousands;
  }

  public static final void setStandardTypes(final MoneyType[] standardTypes) {
    types = standardTypes;
  }

  public static Money sum(final Money... ms) {
    long value = 0L;
    for (final Money m : ms) {
      value += m.value;
    }
    return new Money(value);
  }

  public static Money zero() {
    return zero;
  }

  private final long value;
  private String     toStringCache = null;

  private Money(final long centavos) {
    this.value = centavos;
  }

  public Money add(final Money d) {
    return new Money(value + d.value);
  }

  @Override
  public int compareTo(final Money m) {
    return Long.compare(this.value, m.value);
  }

  public Money divide(final long by) {
    if (by == 0) {
      return null;
    }
    return new Money(value / by);
  }

  public Money fromSQL(final String s) {
    // TODO assume format is like #.## (with signal?)
    // no just stripe invalid chars and use long
    return fromString(s);
  }

  public double getDouble() {
    return value / 100.0;
  }

  public boolean isNegative() {
    return value < 0;
  }

  public boolean isNonNegative() {
    return value >= 0;
  }

  public boolean isNonPositive() {
    return value <= 0;
  }

  public boolean isPositive() {
    return value > 0;
  }

  public boolean isZero() {
    return (value == 0);
  }

  public Money multiply(final double by) {
    return Money.fromDouble((value / 100.0) * by);
  }

  public Money roundDown(final long multiple) {
    return new Money(value - (value % multiple));
  }

  public Money subtract(final Money m) {
    return new Money(value - m.value);
  }

  public long toLong() {
    return value;
  }

  public String toSQL() {
    // TODO ensure database use fromSQL and toSQL
    final long integer = value / 100;
    final long decimal = value % 100;
    return String.format("%d.%02d", integer, decimal);
  }

  @Override
  public String toString() {
    // TODO improve performance
    if (toStringCache != null) {
      return toStringCache;
    }
    long integer = value;
    final boolean negative = (integer < 0);
    if (negative) {
      integer = -integer; // strip signal
    }

    final long decimal = integer % 100;
    integer = integer / 100;

    final String sInteger = String.valueOf(integer);
    final int lenInteger = sInteger.length();
    final int separators = (lenInteger - 1) / 3;

    final int[] parts = new int[separators + 1];
    for (int i = 0; i < parts.length; i++) {
      parts[i] = (int) (integer % 1000);
      integer /= 1000;
    }
    final StringBuilder sb = new StringBuilder(lenInteger + separators + 3
        + (negative ? 2 : 0));
    if (negative) {
      sb.append("- ");
    }

    // inteiro
    boolean ponto = false;
    for (int i = parts.length - 1; i >= 0; i--) {
      if (ponto) {
        sb.append(thousandsSeparator);
        if (parts[i] < 10) {
          sb.append("00");
        } else if (parts[i] < 100) {
          sb.append("0");
        }
      }
      sb.append(parts[i]);
      ponto = true;
    }

    // decimal
    sb.append(decimalSeparator);
    if (decimal < 10) {
      sb.append("0");
    }
    sb.append(decimal);

    // salvar
    toStringCache = sb.toString();
    return toStringCache;
  }
}
