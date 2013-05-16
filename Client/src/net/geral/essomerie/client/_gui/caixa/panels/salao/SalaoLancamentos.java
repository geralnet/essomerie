package net.geral.essomerie.client._gui.caixa.panels.salao;

import net.geral.essomerie.client._gui.caixa.ResumoMesas;
import net.geral.essomerie.client._gui.caixa.base.BaseLancamento;
import net.geral.essomerie.client._gui.caixa.base.BaseLancamentos;
import net.geral.essomerie._shared.Dinheiro;

public class SalaoLancamentos extends BaseLancamentos {
	public boolean addResumo(final ResumoMesas rm) {
		for (final Object o : lancamentos) {
			final SalaoLancamento l = (SalaoLancamento)o;

			//se em branco, ignorar
			if (l.isNull()) {
				continue;
			}

			//obter dados
			final Integer mesa = l.getIntegerOrNull(SalaoLancamento.MESA);
			final Integer pessoas = l.getIntegerOrNull(SalaoLancamento.PESSOAS);
			final Dinheiro consumo = l.getDinheiroOrNull(SalaoLancamento.CONSUMO);
			final Dinheiro servico = l.getDinheiroOrNull(SalaoLancamento.SERVICO);
			final Dinheiro repique = l.getDinheiroOrNull(SalaoLancamento.REPIQUE);

			//se algum incompleto, abortar
			if (mesa == null) { return false; }
			if (pessoas == null) { return false; }
			if (consumo == null) { return false; }
			if (servico == null) { return false; }
			if (repique == null) { return false; }

			//somar
			rm.add(mesa.intValue(), pessoas.intValue(), consumo, servico, repique);
		}

		return true;
	}

	@Override
	protected void criarNovo() {
		lancamentos.add(new SalaoLancamento());
	}

	@Override
	protected int getFieldCount() {
		return SalaoLancamento.CAMPOS;
	}

	@Override
	public Dinheiro getTotal() {
		return null;
	}

	public Dinheiro getTotalConsumo() {
		Dinheiro total = Dinheiro.ZERO;
		for (final BaseLancamento l : lancamentos) {
			total = Dinheiro.add(total, l.getDinheiroOrZero(SalaoLancamento.CONSUMO));
		}
		return total;
	}

	@Override
	protected void load(final String[] ss) {
		lancamentos.add(SalaoLancamento.load(ss));
	}
}
