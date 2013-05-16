package net.geral.essomerie.client._gui.caixa.base;

import java.security.InvalidParameterException;

import net.geral.essomerie._shared.Dinheiro;
import net.geral.lib.strings.GNStrings;

public abstract class BaseLancamento {
  public static final String SEPARATOR = "\t";

  public static Dinheiro loadDinheiro(final String s) {
    if (s == null) {
      return null;
    }
    return new Dinheiro(s);
  }

  public static Integer loadInteger(final String s) {
    if (s == null) {
      return null;
    }
    try {
      return Integer.parseInt(s);
    } catch (final NumberFormatException e) {
      return null;
    }
  }

  public static String loadString(final String s) {
    if (s == null) {
      return null;
    }
    if (GNStrings.trim(s).length() == 0) {
      return null;
    }
    return s;
  }

  private final Object[] campos;

  protected BaseLancamento(final int nCampos) {
    campos = new Object[nCampos];
    for (int i = 0; i < nCampos; i++) {
      campos[i] = null;
    }
  }

  public Object get(final int campo) {
    return campos[campo];
  }

  protected abstract int getColumnCount();

  public Dinheiro getDinheiroOrNull(final int i) {
    if (campos[i] == null) {
      return null;
    }
    if (campos[i] instanceof Dinheiro) {
      return (Dinheiro) campos[i];
    }
    throw new InvalidParameterException("Field " + i + " not Dinheiro: "
        + campos[i].getClass() + "[" + campos[i].toString() + "]");
  }

  public Dinheiro getDinheiroOrZero(final int i) {
    final Dinheiro d = getDinheiroOrNull(i);
    if (d == null) {
      return Dinheiro.ZERO;
    }
    return d;
  }

  public Double getDoubleOrNull(final int i) {
    if (campos[i] == null) {
      return null;
    }
    if (campos[i] instanceof Double) {
      return (Double) campos[i];
    }
    throw new InvalidParameterException("Field " + i + " not Double: "
        + campos[i].getClass() + "[" + campos[i].toString() + "]");
  }

  public double getDoubleOrZero(final int i) {
    final Double d = getDoubleOrNull(i);
    if (d == null) {
      return 0.0;
    }
    return d.doubleValue();
  }

  public Integer getIntegerOrNull(final int i) {
    if (campos[i] == null) {
      return null;
    }
    if (campos[i] instanceof Integer) {
      return (Integer) campos[i];
    }
    throw new InvalidParameterException("Field " + i + " not Integer: "
        + campos[i].getClass() + "[" + campos[i].toString() + "]");
  }

  public int getIntegerOrZero(final int i) {
    final Integer n = getIntegerOrNull(i);
    if (n == null) {
      return 0;
    }
    return n.intValue();
  }

  public String getString(final int campo) {
    final Object o = get(campo);
    if (o == null) {
      return "";
    }
    return o.toString();
  }

  protected abstract Class<?> getType(final int i);

  public boolean isNull() {
    // if any field is not null, return false
    for (final Object o : campos) {
      if (o != null) {
        return false;
      }
    }
    return true;
  }

  public void set(final int i, Object o) {
    // this call will not fire changed event, only BaseLancamentos.set will

    if (i >= getColumnCount()) {
      throw new IndexOutOfBoundsException("Field " + i + " invalid, max: "
          + getColumnCount() + "; to set [" + o + "]");
    }

    if (o instanceof String) {
      if (GNStrings.trim(((String) o)).length() == 0) {
        o = null;
      }
    }

    if ((o == null) || (o.getClass() == getType(i))) {
      campos[i] = o;
    } else {
      throw new InvalidParameterException("Invalid class: " + o.getClass()
          + " (required: " + getType(i) + "), setting #" + i + " to ["
          + o.toString() + "]");
    }
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    boolean sep = false;
    for (final Object o : campos) {
      if (sep) {
        sb.append(SEPARATOR);
      }
      if (o != null) {
        sb.append(o.toString().replaceAll(SEPARATOR, ""));
      }
      sep = true;
    }
    return sb.toString();
  }

  public double zeroIfNull(final Double d) {
    if (d == null) {
      return 0.0;
    }
    return d.doubleValue();
  }
}
