package net.geral.essomerie.client._gui.caixa.base.strdin;

import net.geral.essomerie.client._gui.caixa.base.BaseLancamentosPanel;

public abstract class StringDinheiroLancamentosPanel extends BaseLancamentosPanel {
	private static final long	serialVersionUID	= 1L;

	public StringDinheiroLancamentosPanel(final String title, final StringDinheiroLancamentosTableModel tableModel) {
		super(title, tableModel);
	}
}
