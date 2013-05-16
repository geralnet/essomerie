package net.geral.essomerie.client._gui.caixa.panels.salao;

import net.geral.essomerie.client._gui.caixa.Lancamentos;
import net.geral.essomerie.client._gui.caixa.base.BaseLancamentosPanel;

public class SalaoPanel extends BaseLancamentosPanel {
	private static final long	serialVersionUID	= 1L;

	@Deprecated
	public SalaoPanel() {
		this((new Lancamentos()).salao);
	}

	public SalaoPanel(final SalaoLancamentos lancamentos) {
		super("Salão", new SalaoTableModel(lancamentos));
	}
}
