package net.geral.essomerie.client._gui.caixa.panels.viagem;

import net.geral.essomerie.client._gui.caixa.Lancamentos;
import net.geral.essomerie.client._gui.caixa.base.strdin.StringDinheiroLancamentosPanel;

public class ViagemPanel extends StringDinheiroLancamentosPanel {
	private static final long	serialVersionUID	= 1L;

	@Deprecated
	public ViagemPanel() {
		this((new Lancamentos()).viagem);
	}

	public ViagemPanel(final ViagemLancamentos lancamentos) {
		super("Viagem", new ViagemTableModel(lancamentos));
	}
}
