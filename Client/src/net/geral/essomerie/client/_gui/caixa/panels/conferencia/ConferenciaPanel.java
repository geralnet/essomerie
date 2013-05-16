package net.geral.essomerie.client._gui.caixa.panels.conferencia;

import net.geral.essomerie.client._gui.caixa.Lancamentos;
import net.geral.essomerie.client._gui.caixa.base.BaseLancamentosPanel;

public class ConferenciaPanel extends BaseLancamentosPanel {
	private static final long	serialVersionUID	= 1L;

	@Deprecated
	public ConferenciaPanel() {
		this((new Lancamentos()).conferencia);
	}

	public ConferenciaPanel(final ConferenciaLancamentos lancamentos) {
		super("Conferência", new ConferenciaTableModel(lancamentos));
	}
}
