package net.geral.essomerie.client._gui.caixa.panels.delivery;

import net.geral.essomerie.client._gui.caixa.Lancamentos;
import net.geral.essomerie.client._gui.caixa.base.BaseLancamentosPanel;

public class DeliveryPanel extends BaseLancamentosPanel {
	private static final long	serialVersionUID	= 1L;

	@Deprecated
	public DeliveryPanel() {
		this((new Lancamentos()).delivery);
	}

	public DeliveryPanel(final DeliveryLancamentos lancamentos) {
		super("Delivery", new DeliveryTableModel(lancamentos));
	}
}
