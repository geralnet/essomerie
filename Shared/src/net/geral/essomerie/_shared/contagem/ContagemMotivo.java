package net.geral.essomerie._shared.contagem;

import java.io.Serializable;

public class ContagemMotivo implements Serializable {
  private static final long serialVersionUID = 1L;

  public final int          id;
  public final char         tipo;
  public final String       motivo;

  public ContagemMotivo(final int id, final char tipo, final String motivo) {
    this.id = id;
    this.tipo = tipo;
    this.motivo = motivo;
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final ContagemMotivo other = (ContagemMotivo) obj;
    if (id != other.id) {
      return false;
    }
    if (motivo == null) {
      if (other.motivo != null) {
        return false;
      }
    } else if (!motivo.equals(other.motivo)) {
      return false;
    }
    if (tipo != other.tipo) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = (prime * result) + id;
    result = (prime * result) + ((motivo == null) ? 0 : motivo.hashCode());
    result = (prime * result) + tipo;
    return result;
  }

  public boolean isTipo(final char t) {
    return (t == tipo);
  }
}
