package net.geral.essomerie.client._gui.caixa.panels.fundo;

import net.geral.essomerie.client._gui.caixa.Lancamentos;
import net.geral.essomerie.client._gui.caixa.base.BaseLancamentosPanel;

public class FundoLancamentosPanel extends BaseLancamentosPanel {
	private static final long	serialVersionUID	= 1L;

	@Deprecated
	public FundoLancamentosPanel() {
		this((new Lancamentos()).fundo);
	}

	public FundoLancamentosPanel(final FundoLancamentos lancamentos) {
		super("Fundo de Caixa", new FundoLancamentosTableModel(lancamentos));
	}
}
