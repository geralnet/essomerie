package net.geral.essomerie._shared;

import java.io.Serializable;

@Deprecated
// FIXME delete it
public class CEP implements Serializable {
  public static CEP createOrZero(final int i) {
    return new CEP(i);
  }

  public static CEP createOrZero(String s) {
    s = s.replace("[\\D]", "");
    try {
      return new CEP(Integer.parseInt(s));
    } catch (final NumberFormatException e) {
      return new CEP(0);
    }
  }

  private final int numero;

  public CEP(int _numero) {
    // TODO make private, use factory, throw exception if invalid
    if ((_numero < 0) || (_numero > 99999999)) {
      _numero = 0;
    }
    numero = _numero;
  }

  public int getNumero() {
    return numero;
  }

  public boolean isZero() {
    return (numero == 0);
  }

  @Override
  public String toString() {
    if (numero == 0) {
      return "";
    }
    final int p2 = numero % 1000;
    final int p1 = numero / 1000;
    return String.format("%05d-%03d", p1, p2);
  }
}
