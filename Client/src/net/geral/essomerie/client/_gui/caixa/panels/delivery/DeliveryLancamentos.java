package net.geral.essomerie.client._gui.caixa.panels.delivery;

import net.geral.essomerie.client._gui.caixa.base.BaseLancamento;
import net.geral.essomerie.client._gui.caixa.base.BaseLancamentos;
import net.geral.essomerie.shared.money.Money;

public class DeliveryLancamentos extends BaseLancamentos {
  public static final double TAXA_ENTREGA = 5.0;

  public DeliveryLancamentos() {
  }

  @Override
  protected void criarNovo() {
    lancamentos.add(new DeliveryLancamento());
  }

  @Override
  protected int getFieldCount() {
    return DeliveryLancamento.CAMPOS;
  }

  @Override
  public Money getTotal() {
    Money total = Money.zero();
    for (final BaseLancamento l : lancamentos) {
      total = Money.sum(total, l.getDinheiroOrZero(DeliveryLancamento.VALOR));
    }
    return total;
  }

  @Override
  protected void load(final String[] ss) {
    lancamentos.add(DeliveryLancamento.load(ss));
  }
}
