package net.geral.essomerie.client._gui.caixa.panels.plantao;

import net.geral.essomerie.client._gui.caixa.Lancamentos;
import net.geral.essomerie.client._gui.caixa.base.BaseLancamentosPanel;

public class PlantaoPanel extends BaseLancamentosPanel {
	private static final long	serialVersionUID	= 1L;

	@Deprecated
	public PlantaoPanel() {
		this((new Lancamentos()).plantao);
	}

	public PlantaoPanel(final PlantaoLancamentos lancamentos) {
		super("Plantão", new PlantaoTableModel(lancamentos));
	}
}
