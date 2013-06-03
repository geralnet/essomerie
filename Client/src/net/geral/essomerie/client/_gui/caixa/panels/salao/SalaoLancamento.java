package net.geral.essomerie.client._gui.caixa.panels.salao;

import net.geral.essomerie.client._gui.caixa.base.BaseLancamento;
import net.geral.essomerie.shared.money.Money;

public class SalaoLancamento extends BaseLancamento {
  public static final int         MESA    = 0;
  public static final int         PESSOAS = 1;
  public static final int         CONSUMO = 2;
  public static final int         SERVICO = 3;
  public static final int         REPIQUE = 4;

  private static final Class<?>[] types   = { Integer.class, Integer.class,
      Money.class, Money.class, Money.class };
  public static final int         CAMPOS  = types.length;

  public static BaseLancamento load(final String[] ss) {
    return new SalaoLancamento(loadInteger(ss[0]), loadInteger(ss[1]),
        loadDinheiro(ss[2]), loadDinheiro(ss[3]), loadDinheiro(ss[4]));
  }

  public SalaoLancamento() {
    this(null, null, null, null, null);
  }

  public SalaoLancamento(final Integer mesa, final Integer pessoas,
      final Money consumo, final Money servico, final Money repique) {
    super(types.length);
    set(MESA, mesa);
    set(PESSOAS, pessoas);
    set(CONSUMO, consumo);
    set(SERVICO, servico);
    set(REPIQUE, repique);
  }

  @Override
  protected int getColumnCount() {
    return types.length;
  }

  public Money getConsumoMedio() {
    final Money c = getDinheiroOrNull(CONSUMO);
    final Integer p = getIntegerOrNull(PESSOAS);
    if ((c == null) || (p == null)) {
      return null;
    }
    return c.divide(p.intValue());
  }

  public Money getConta() {
    final Money c = getDinheiroOrNull(CONSUMO);
    final Money s = getDinheiroOrNull(SERVICO);
    if ((c == null) || (s == null)) {
      return null;
    }
    return Money.sum(c, s);
  }

  public Money getMoneyOrNull(final int servico2) {
    return getDinheiroOrNull(servico2);
  }

  public Money getPago() {
    final Money c = getConta();
    final Money r = getDinheiroOrNull(REPIQUE);
    if ((c == null) || (r == null)) {
      return null;
    }
    return Money.sum(c, r);
  }

  @Override
  protected Class<?> getType(final int i) {
    return types[i];
  }
}
