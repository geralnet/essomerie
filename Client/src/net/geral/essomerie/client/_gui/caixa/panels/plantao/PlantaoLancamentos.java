package net.geral.essomerie.client._gui.caixa.panels.plantao;

import net.geral.essomerie.client._gui.caixa.ResumoMesas;
import net.geral.essomerie.client._gui.caixa.base.BaseLancamento;
import net.geral.essomerie.client._gui.caixa.base.BaseLancamentos;
import net.geral.essomerie.shared.money.Money;

public class PlantaoLancamentos extends BaseLancamentos {
  public PlantaoLancamentos() {
  }

  public boolean addResumo(final ResumoMesas rm) {
    for (final Object o : lancamentos) {
      final PlantaoLancamento l = (PlantaoLancamento) o;

      // se em branco, ignorar
      if (l.isNull()) {
        continue;
      }

      // obter dados
      final Integer pessoas = l.getIntegerOrNull(PlantaoLancamento.PESSOAS);
      final Money consumo = l.getDinheiroOrNull(PlantaoLancamento.CONSUMO);

      // se algum incompleto, abortar
      if (pessoas == null) {
        return false;
      }
      if (consumo == null) {
        return false;
      }

      // somar
      rm.addPlantao(pessoas, consumo);
    }

    return true;
  }

  @Override
  protected void criarNovo() {
    lancamentos.add(new PlantaoLancamento());
  }

  public Money getConsumoMedio() {
    int pessoas = 0;
    Money consumo = Money.zero();
    for (final BaseLancamento l : lancamentos) {
      final Integer p = l.getIntegerOrNull(PlantaoLancamento.PESSOAS);
      final Money c = l.getDinheiroOrNull(PlantaoLancamento.CONSUMO);
      if ((p == null) || (c == null)) {
        return null;
      }
      pessoas += p.intValue();
      consumo = consumo.add(c);
    }
    return consumo.divide(pessoas);
  }

  @Override
  protected int getFieldCount() {
    return PlantaoLancamento.CAMPOS;
  }

  public Integer getMesasCount() {
    int mesas = 0;
    for (final BaseLancamento l : lancamentos) {
      if (l.isNull()) {
        continue;
      }
      mesas++;
    }
    return new Integer(mesas);
  }

  public Integer getPessoas() {
    int p = 0;
    for (final BaseLancamento l : lancamentos) {
      p += l.getIntegerOrZero(PlantaoLancamento.PESSOAS);
    }
    return new Integer(p);
  }

  @Override
  public Money getTotal() {
    Money total = Money.zero();
    for (final BaseLancamento l : lancamentos) {
      total = Money.sum(total, l.getDinheiroOrZero(PlantaoLancamento.CONSUMO));
    }
    return total;
  }

  @Override
  protected void load(final String[] ss) {
    lancamentos.add(PlantaoLancamento.load(ss));
  }
}
