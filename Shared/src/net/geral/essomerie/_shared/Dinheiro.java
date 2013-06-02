package net.geral.essomerie._shared;

import java.io.Serializable;

@Deprecated
public final class Dinheiro implements Serializable, Comparable<Dinheiro> {
  private static final long    serialVersionUID     = 1L;
  public static final Dinheiro ZERO                 = new Dinheiro(0L);
  public static final Dinheiro NOTA_100             = new Dinheiro(10000);
  public static final Dinheiro NOTA_50              = new Dinheiro(5000);
  public static final Dinheiro NOTA_20              = new Dinheiro(2000);
  public static final Dinheiro NOTA_10              = new Dinheiro(1000);
  public static final Dinheiro NOTA_5               = new Dinheiro(500);
  public static final Dinheiro NOTA_2               = new Dinheiro(200);
  public static final Dinheiro MOEDA_100            = new Dinheiro(100);
  public static final Dinheiro MOEDA_50             = new Dinheiro(50);
  public static final Dinheiro MOEDA_25             = new Dinheiro(25);
  public static final Dinheiro MOEDA_10             = new Dinheiro(10);
  public static final Dinheiro MOEDA_5              = new Dinheiro(5);
  public static final Dinheiro MOEDA_1              = new Dinheiro(1);

  public static final char     SEPARADOR_DECIMAL    = ',';
  public static final char     SEPARADOR_MILHARES   = '.';
  public static final String   SEPARADOR_DECIMAL_S  = String
                                                        .valueOf(SEPARADOR_DECIMAL);
  public static final String   SEPARADOR_MILHARES_S = String
                                                        .valueOf(SEPARADOR_MILHARES);

  public static Dinheiro add(final Dinheiro d1, final Dinheiro d2,
      final Dinheiro... ds) {
    long total = d1.centavos + d2.centavos;
    for (final Dinheiro d : ds) {
      total += d.centavos;
    }
    return new Dinheiro(total);
  }

  public static Dinheiro divide(final Dinheiro d, final int i) {
    return new Dinheiro(d.centavos / 100.0 / i);
  }

  public static Dinheiro multiply(final Dinheiro a, final double b) {
    return new Dinheiro((a.centavos / 100.0) * b);
  }

  public static Dinheiro remaining(final Dinheiro d1, final Dinheiro d2) {
    return new Dinheiro(d1.centavos % d2.centavos);
  }

  public static Dinheiro subtract(final Dinheiro d1, final Dinheiro d2) {
    return new Dinheiro(d1.centavos - d2.centavos);
  }

  private final long centavos;

  private String     str = null;

  public Dinheiro() {
    centavos = 0;
  }

  public Dinheiro(final double reais) {
    centavos = Math.round(reais * 100);
  }

  public Dinheiro(final long centavos) {
    this.centavos = centavos;
  }

  public Dinheiro(final String s) {
    this(Double.parseDouble(s.replaceAll(" ", "")
        .replace(SEPARADOR_MILHARES_S, "").replace(SEPARADOR_DECIMAL_S, ".")));
  }

  public Dinheiro add(final Dinheiro d) {
    return new Dinheiro(centavos + d.centavos);
  }

  @Override
  public int compareTo(final Dinheiro d) {
    return Long.compare(this.centavos, d.centavos);
  }

  public Dinheiro divide(final int i) {
    return Dinheiro.divide(this, i);
  }

  public long getCentavos() {
    return centavos;
  }

  public double getDouble() {
    return centavos / 100.0;
  }

  public boolean isNaoPositivo() {
    return (centavos <= 0);
  }

  public boolean isNegative() {
    return getCentavos() < 0;
  }

  public boolean isNegativo() {
    return (centavos < 0);
  }

  public boolean isNonNegative() {
    return getCentavos() >= 0;
  }

  public boolean isNonPositive() {
    return getCentavos() <= 0;
  }

  public boolean isPositive() {
    return getCentavos() > 0;
  }

  public boolean isZero() {
    return (centavos == 0);
  }

  public Dinheiro multiply(final double d) {
    return Dinheiro.multiply(this, d);
  }

  public Dinheiro roundDown(final long multiple) {
    return new Dinheiro(centavos - (centavos % multiple));
  }

  public Dinheiro subtract(final Dinheiro d) {
    return new Dinheiro(centavos - d.centavos);
  }

  public String toSQL() {
    final long inteiro = centavos / 100;
    final long decimal = centavos % 100;
    return String.format("%d.%02d", inteiro, decimal);
  }

  @Override
  public String toString() {
    if (str == null) {
      long inteiro = centavos;
      final boolean negativo = (inteiro < 0);
      if (negativo) {
        inteiro = -inteiro;
      }

      final long fracao = inteiro % 100;
      inteiro = inteiro / 100;

      final String inteiro_str = String.valueOf(inteiro);
      final int inteiro_len = inteiro_str.length();
      final int pontos = (inteiro_len - 1) / 3;

      final int[] partes = new int[pontos + 1];
      for (int i = 0; i < partes.length; i++) {
        partes[i] = (int) (inteiro % 1000);
        inteiro /= 1000;
      }
      final StringBuilder sb = new StringBuilder(inteiro_len + pontos + 3
          + (negativo ? 2 : 0));
      if (negativo) {
        sb.append("- ");
      }

      // inteiro
      boolean ponto = false;
      for (int i = partes.length - 1; i >= 0; i--) {
        if (ponto) {
          sb.append(SEPARADOR_MILHARES);
          if (partes[i] < 10) {
            sb.append("00");
          } else if (partes[i] < 100) {
            sb.append("0");
          }
        }
        sb.append(partes[i]);
        ponto = true;
      }

      // decimal
      sb.append(SEPARADOR_DECIMAL);
      if (fracao < 10) {
        sb.append("0");
      }
      sb.append(fracao);

      // salvar
      str = sb.toString();
    }
    return str;
  }
}
