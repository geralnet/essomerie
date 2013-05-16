package net.geral.essomerie.client._gui.caixa.panels.fundo;

import net.geral.essomerie.client._gui.caixa.base.BaseLancamentos;
import net.geral.essomerie.client._gui.caixa.base.strdin.StringDinheiroLancamentosTableModel;

public class FundoLancamentosTableModel extends StringDinheiroLancamentosTableModel {
	private static final long	serialVersionUID	= 1L;

	public FundoLancamentosTableModel(final BaseLancamentos lancamentos) {
		super(lancamentos);
	}

	@Override
	public boolean isCellEditable(final int rowIndex, final int columnIndex) {
		// valor sempre pode
		if (columnIndex > 0) { return true; }
		// se acima das fixas, pode
		if (rowIndex >= FundoLancamentos.LINHAS_FIXAS) { return true; }
		// nao pode
		return false;
	}
}
