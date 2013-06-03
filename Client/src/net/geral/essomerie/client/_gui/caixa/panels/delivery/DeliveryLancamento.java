package net.geral.essomerie.client._gui.caixa.panels.delivery;

import net.geral.essomerie.client._gui.caixa.base.BaseLancamento;
import net.geral.essomerie.shared.money.Money;

public class DeliveryLancamento extends BaseLancamento {
  public static final int         ENTREGADOR = 0;
  public static final int         VALOR      = 1;
  public static final int         EXTRA      = 2;

  private static final Class<?>[] types      = { String.class, Money.class,
      Money.class                           };
  public static final int         CAMPOS     = types.length;

  public static DeliveryLancamento load(final String[] ss) {
    return new DeliveryLancamento(loadString(ss[0]), loadDinheiro(ss[1]),
        loadDinheiro(ss[2]));
  }

  public DeliveryLancamento() {
    this(null, null, null);
  }

  public DeliveryLancamento(final String entregador, final Money valor,
      final Money extra) {
    super(types.length);
    set(ENTREGADOR, entregador);
    set(VALOR, valor);
    set(EXTRA, extra);
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
