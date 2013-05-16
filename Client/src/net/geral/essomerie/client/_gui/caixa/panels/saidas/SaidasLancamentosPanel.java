package net.geral.essomerie.client._gui.caixa.panels.saidas;

import net.geral.essomerie.client._gui.caixa.Lancamentos;
import net.geral.essomerie.client._gui.caixa.base.strdin.StringDinheiroLancamentosPanel;

public class SaidasLancamentosPanel extends StringDinheiroLancamentosPanel {
	private static final long	serialVersionUID	= 1L;

	@Deprecated
	public SaidasLancamentosPanel() {
		this((new Lancamentos()).saidas);
	}

	public SaidasLancamentosPanel(final SaidasLancamentos lancamentos) {
		super("Saídas", new SaidasLancamentosTableModel(lancamentos));
	}
}
