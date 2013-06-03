package net.geral.essomerie.client._gui.caixa;

import net.geral.essomerie.client._gui.caixa.panels.plantao.PlantaoLancamento;
import net.geral.essomerie.client._gui.caixa.panels.salao.SalaoLancamento;
import net.geral.essomerie.shared.money.Money;

public class ResumoMesas {
  public static final int MESA_LIMITE = 20;

  public static final int SALAO_VELHO = 0;
  public static final int SALAO_NOVO  = 1;
  public static final int PLANTAO     = 2;
  public static final int RESULTADO   = 3;

  private final int[]     pessoas     = new int[] { 0, 0, 0 };
  private final int[]     mesas       = new int[] { 0, 0, 0 };
  private final Money[]   consumo     = new Money[] { Money.zero(),
      Money.zero(), Money.zero()     };
  private final Money[]   servico     = new Money[] { Money.zero(),
      Money.zero()                   };
  private final Money[]   repique     = new Money[] { Money.zero(),
      Money.zero()                   };

  public void add(final int mesa, final int aPessoas, final Money aConsumo,
      final Money aServico, final Money aRepique) {
    final int i = (mesa < MESA_LIMITE) ? SALAO_VELHO : SALAO_NOVO;

    mesas[i]++;
    pessoas[i] += aPessoas;
    if (aConsumo != null) {
      consumo[i] = Money.sum(consumo[i], aConsumo);
    }
    if (aServico != null) {
      servico[i] = Money.sum(servico[i], aServico);
    }
    if (aRepique != null) {
      repique[i] = Money.sum(repique[i], aRepique);
    }
  }

  public void add(final PlantaoLancamento p) {
    add(p.getIntegerOrNull(PlantaoLancamento.MESA),
        p.getIntegerOrNull(PlantaoLancamento.PESSOAS),
        p.getDinheiroOrNull(PlantaoLancamento.CONSUMO), null, null);
  }

  public void add(final SalaoLancamento e) {
    add(e.getIntegerOrNull(SalaoLancamento.MESA),
        e.getIntegerOrNull(SalaoLancamento.PESSOAS),
        e.getMoneyOrNull(SalaoLancamento.CONSUMO),
        e.getMoneyOrNull(SalaoLancamento.SERVICO),
        e.getDinheiroOrZero(SalaoLancamento.REPIQUE));
  }

  public void addPlantao(final int aPessoas, final Money aConsumo) {
    mesas[PLANTAO]++;
    pessoas[PLANTAO] += aPessoas;
    if (aConsumo != null) {
      consumo[PLANTAO] = consumo[PLANTAO].add(aConsumo);
    }
  }

  public Money get20Percento(final int i) {
    if (i == PLANTAO) {
      return null;
    }
    return getServico(i).multiply(0.2).roundDown(200);
  }

  public Money getConsumo(final int i) {
    if (i == RESULTADO) {
      return Money.sum(consumo[SALAO_VELHO], consumo[SALAO_NOVO],
          consumo[PLANTAO]);
    }
    return consumo[i];
  }

  public Money getGarcons(final int i) {
    if (i == PLANTAO) {
      return null;
    }
    return getServico(i).subtract(get20Percento(i)).add(getRepique(i));
  }

  public Money getMedia(final int i) {
    final int pessoas = getPessoas(i);
    if (pessoas == 0) {
      return Money.zero();
    }
    return getConsumo(i).divide(pessoas);
  }

  public int getMesas(final int i) {
    if (i == RESULTADO) {
      return mesas[SALAO_VELHO] + mesas[SALAO_NOVO] + mesas[PLANTAO];
    }
    return mesas[i];
  }

  public int getPessoas(final int i) {
    if (i == RESULTADO) {
      return pessoas[SALAO_VELHO] + pessoas[SALAO_NOVO] + pessoas[PLANTAO];
    }
    return pessoas[i];
  }

  public String getPessoasMesas(final int i) {
    if (i == RESULTADO) {
      return getPessoas(RESULTADO) + " (" + getMesas(RESULTADO) + ")";
    }
    return getPessoas(i) + " (" + getMesas(i) + ")";
  }

  public Money getRepique(final int i) {
    if (i == RESULTADO) {
      return Money.sum(repique[SALAO_VELHO], repique[SALAO_NOVO]);
    }
    if (i == PLANTAO) {
      return null;
    }
    return repique[i];
  }

  public Money getServico(final int i) {
    if (i == RESULTADO) {
      return Money.sum(servico[SALAO_VELHO], servico[SALAO_NOVO]);
    }
    if (i == PLANTAO) {
      return null;
    }
    return servico[i];
  }
}
