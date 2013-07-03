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

  public boolean isTipo(final char t) {
    return (t == tipo);
  }
}
