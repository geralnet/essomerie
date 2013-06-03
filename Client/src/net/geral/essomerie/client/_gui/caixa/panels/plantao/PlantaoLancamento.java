package net.geral.essomerie.client._gui.caixa.panels.plantao;

import net.geral.essomerie.client._gui.caixa.base.BaseLancamento;
import net.geral.essomerie.shared.money.Money;

public class PlantaoLancamento extends BaseLancamento {
  public static final int         MESA    = 0;
  public static final int         PESSOAS = 1;
  public static final int         CONSUMO = 2;

  private static final Class<?>[] types   = { Integer.class, Integer.class,
      Money.class                        };
  public static final int         CAMPOS  = types.length;

  public static PlantaoLancamento load(final String[] ss) {
    return new PlantaoLancamento(loadInteger(ss[0]), loadInteger(ss[1]),
        loadDinheiro(ss[2]));
  }

  public PlantaoLancamento() {
    this(null, null, null);
  }

  public PlantaoLancamento(final Integer mesa, final Integer pessoas,
      final Money consumo) {
    super(types.length);
    set(MESA, mesa);
    set(PESSOAS, pessoas);
    set(CONSUMO, consumo);
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

  @Override
  protected Class<?> getType(final int i) {
    return types[i];
  }
}
