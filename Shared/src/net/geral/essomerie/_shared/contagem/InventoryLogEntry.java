package net.geral.essomerie._shared.contagem;

import java.io.Serializable;

import org.joda.time.LocalDateTime;

public final class InventoryLogEntry implements Serializable {
  private static final long serialVersionUID = 1L;

  public static String toQuantidadeString(final float qtd) {
    final int i = (int) qtd;
    if ((qtd - i) == 0f) {
      return String.valueOf(i); // no decimal part
    }
    return String.valueOf(qtd).replace('.', ','); // with decimal part
  }

  public final int           id;
  public final int           iditem;
  public final int           idusuario;
  public final char          tipo;
  public final int           idmotivo;
  public final float         quantidadeInicial;
  public final float         variacao;

  public final LocalDateTime datahora;

  public final String        observacoes;

  public InventoryLogEntry(final int _id, final int _iditem,
      final int _idusuario, final char _tipo, final int _idmotivo,
      final float _quantidadeInicial, final float _variacao,
      final LocalDateTime _datahora, final String _observacoes) {
    id = _id;
    iditem = _iditem;
    idusuario = _idusuario;
    tipo = _tipo;
    idmotivo = _idmotivo;
    quantidadeInicial = _quantidadeInicial;
    variacao = _variacao;
    datahora = _datahora;
    observacoes = _observacoes;
  }

  public String getAlteracaoString() {
    return getAlteracaoString("\u2192");
  }

  public String getAlteracaoString(final String seta) {
    switch (tipo) {
      case '+':
        return toQuantidadeString(quantidadeInicial) + " + "
            + toQuantidadeString(variacao) + " = "
            + toQuantidadeString(quantidadeInicial + variacao);
      case '-':
        return toQuantidadeString(quantidadeInicial) + " - "
            + toQuantidadeString(variacao) + " = "
            + toQuantidadeString(Math.max(quantidadeInicial - variacao, 0));
      case '=':
        return toQuantidadeString(quantidadeInicial) + " " + seta + " "
            + toQuantidadeString(variacao);
      default:
        return "[ERROR " + tipo + "]";
    }
  }
}
