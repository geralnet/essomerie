package net.geral.essomerie.client._gui.caixa.panels.entradas;

import net.geral.essomerie.client._gui.caixa.Lancamentos;
import net.geral.essomerie.client._gui.caixa.base.BaseLancamentosPanel;

public class EntradasLancamentosPanel extends BaseLancamentosPanel {
	private static final long	serialVersionUID	= 1L;

	@Deprecated
	public EntradasLancamentosPanel() {
		this((new Lancamentos()).entradas);
	}

	public EntradasLancamentosPanel(final EntradasLancamentos lancamentos) {
		super("Entradas", new EntradasTableModel(lancamentos));
	}
}
