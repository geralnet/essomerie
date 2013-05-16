package net.geral.essomerie.client._gui.caixa.panels.saidas;

import net.geral.essomerie.client._gui.caixa.base.BaseLancamentos;
import net.geral.essomerie.client._gui.caixa.base.strdin.StringDinheiroLancamentosTableModel;

public class SaidasLancamentosTableModel extends StringDinheiroLancamentosTableModel {
	private static final long	serialVersionUID	= 1L;

	public SaidasLancamentosTableModel(final BaseLancamentos lancamentos) {
		super(lancamentos);
	}
}
