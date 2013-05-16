package net.geral.essomerie.client._gui.caixa;

import net.geral.essomerie.client._gui.caixa.panels.plantao.PlantaoLancamento;
import net.geral.essomerie.client._gui.caixa.panels.salao.SalaoLancamento;
import net.geral.essomerie._shared.Dinheiro;

public class ResumoMesas {
	public static final int		MESA_LIMITE	= 20;

	public static final int		SALAO_VELHO	= 0;
	public static final int		SALAO_NOVO	= 1;
	public static final int		PLANTAO		= 2;
	public static final int		RESULTADO	= 3;

	private final int[]			pessoas		= new int[] { 0, 0, 0 };
	private final int[]			mesas		= new int[] { 0, 0, 0 };
	private final Dinheiro[]	consumo		= new Dinheiro[] { Dinheiro.ZERO, Dinheiro.ZERO, Dinheiro.ZERO };
	private final Dinheiro[]	servico		= new Dinheiro[] { Dinheiro.ZERO, Dinheiro.ZERO };
	private final Dinheiro[]	repique		= new Dinheiro[] { Dinheiro.ZERO, Dinheiro.ZERO };

	public void add(final int mesa, final int aPessoas, final Dinheiro aConsumo, final Dinheiro aServico,
			final Dinheiro aRepique) {
		final int i = (mesa < MESA_LIMITE) ? SALAO_VELHO : SALAO_NOVO;

		mesas[i]++;
		pessoas[i] += aPessoas;
		if (aConsumo != null) {
			consumo[i] = Dinheiro.add(consumo[i], aConsumo);
		}
		if (aServico != null) {
			servico[i] = Dinheiro.add(servico[i], aServico);
		}
		if (aRepique != null) {
			repique[i] = Dinheiro.add(repique[i], aRepique);
		}
	}

	public void add(final PlantaoLancamento p) {
		add(p.getIntegerOrNull(PlantaoLancamento.MESA), p.getIntegerOrNull(PlantaoLancamento.PESSOAS),
				p.getDinheiroOrNull(PlantaoLancamento.CONSUMO), null, null);
	}

	public void add(final SalaoLancamento e) {
		add(e.getIntegerOrNull(SalaoLancamento.MESA), e.getIntegerOrNull(SalaoLancamento.PESSOAS),
				e.getDinheiroOrNull(SalaoLancamento.CONSUMO), e.getDinheiroOrNull(SalaoLancamento.SERVICO),
				e.getDinheiroOrZero(SalaoLancamento.REPIQUE));
	}

	public void addPlantao(final int aPessoas, final Dinheiro aConsumo) {
		mesas[PLANTAO]++;
		pessoas[PLANTAO] += aPessoas;
		if (aConsumo != null) {
			consumo[PLANTAO] = consumo[PLANTAO].add(aConsumo);
		}
	}

	public Dinheiro get20Percento(final int i) {
		if (i == PLANTAO) { return null; }
		return getServico(i).multiply(0.2).roundDown(200);
	}

	public Dinheiro getConsumo(final int i) {
		if (i == RESULTADO) { return Dinheiro.add(consumo[SALAO_VELHO], consumo[SALAO_NOVO], consumo[PLANTAO]); }
		return consumo[i];
	}

	public Dinheiro getGarcons(final int i) {
		if (i == PLANTAO) { return null; }
		return getServico(i).subtract(get20Percento(i)).add(getRepique(i));
	}

	public Dinheiro getMedia(final int i) {
		final int pessoas = getPessoas(i);
		if (pessoas == 0) { return Dinheiro.ZERO; }
		return Dinheiro.divide(getConsumo(i), pessoas);
	}

	public int getMesas(final int i) {
		if (i == RESULTADO) { return mesas[SALAO_VELHO] + mesas[SALAO_NOVO] + mesas[PLANTAO]; }
		return mesas[i];
	}

	public int getPessoas(final int i) {
		if (i == RESULTADO) { return pessoas[SALAO_VELHO] + pessoas[SALAO_NOVO] + pessoas[PLANTAO]; }
		return pessoas[i];
	}

	public String getPessoasMesas(final int i) {
		if (i == RESULTADO) { return getPessoas(RESULTADO) + " (" + getMesas(RESULTADO) + ")"; }
		return getPessoas(i) + " (" + getMesas(i) + ")";
	}

	public Dinheiro getRepique(final int i) {
		if (i == RESULTADO) { return Dinheiro.add(repique[SALAO_VELHO], repique[SALAO_NOVO]); }
		if (i == PLANTAO) { return null; }
		return repique[i];
	}

	public Dinheiro getServico(final int i) {
		if (i == RESULTADO) { return Dinheiro.add(servico[SALAO_VELHO], servico[SALAO_NOVO]); }
		if (i == PLANTAO) { return null; }
		return servico[i];
	}
}
