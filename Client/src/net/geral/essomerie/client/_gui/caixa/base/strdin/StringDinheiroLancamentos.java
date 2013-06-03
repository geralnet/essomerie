package net.geral.essomerie.client._gui.caixa.base.strdin;

import net.geral.essomerie.client._gui.caixa.base.BaseLancamento;
import net.geral.essomerie.client._gui.caixa.base.BaseLancamentos;
import net.geral.essomerie.shared.money.Money;

public class StringDinheiroLancamentos extends BaseLancamentos {
  @Override
  protected void criarNovo() {
    lancamentos.add(new StringDinheiroLancamento());
  }

  @Override
  protected int getFieldCount() {
    return StringDinheiroLancamento.CAMPOS;
  }

  @Override
  public Money getTotal() {
    Money total = Money.zero();
    for (final BaseLancamento l : lancamentos) {
      total = Money.sum(total,
          l.getDinheiroOrZero(StringDinheiroLancamento.VALOR));
    }
    return total;
  }

  @Override
  protected void load(final String[] ss) {
    lancamentos.add(StringDinheiroLancamento.load(ss));
  }
}
