package net.geral.essomerie.client._gui.caixa.base.strdin;

import net.geral.essomerie.client._gui.caixa.base.BaseLancamento;
import net.geral.essomerie.shared.money.Money;

public class StringDinheiroLancamento extends BaseLancamento {
  public static final int         DESCRICAO = 0;
  public static final int         VALOR     = 1;

  private static final Class<?>[] types     = { String.class, Money.class };
  public static final int         CAMPOS    = types.length;

  public static BaseLancamento load(final String[] ss) {
    return new StringDinheiroLancamento(loadString(ss[0]), loadDinheiro(ss[1]));
  }

  public StringDinheiroLancamento() {
    this(null);
  }

  public StringDinheiroLancamento(final String descricao) {
    this(descricao, null);
  }

  public StringDinheiroLancamento(final String descricao, final Money valor) {
    super(types.length);
    set(DESCRICAO, descricao);
    set(VALOR, valor);
  }

  @Override
  protected int getColumnCount() {
    return types.length;
  }

  @Override
  protected Class<?> getType(final int i) {
    return types[i];
  }
}
