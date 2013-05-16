package net.geral.essomerie.client._gui.caixa.base.strdin;

import net.geral.essomerie.client._gui.caixa.base.BaseLancamento;
import net.geral.essomerie.client._gui.caixa.base.BaseLancamentos;
import net.geral.essomerie._shared.Dinheiro;

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
	public Dinheiro getTotal() {
		Dinheiro total = Dinheiro.ZERO;
		for (final BaseLancamento l : lancamentos) {
			total = Dinheiro.add(total, l.getDinheiroOrZero(StringDinheiroLancamento.VALOR));
		}
		return total;
	}

	@Override
	protected void load(final String[] ss) {
		lancamentos.add(StringDinheiroLancamento.load(ss));
	}
}
