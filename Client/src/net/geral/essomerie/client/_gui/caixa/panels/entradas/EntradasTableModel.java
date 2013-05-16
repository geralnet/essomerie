package net.geral.essomerie.client._gui.caixa.panels.entradas;

import net.geral.essomerie.client._gui.caixa.base.BaseLancamentos;
import net.geral.essomerie.client._gui.caixa.base.strdin.StringDinheiroLancamentosTableModel;

public class EntradasTableModel extends StringDinheiroLancamentosTableModel {
	private static final long	serialVersionUID	= 1L;

	public EntradasTableModel(final BaseLancamentos lancamentos) {
		super(lancamentos);
	}

	@Override
	public boolean isCellEditable(final int rowIndex, final int columnIndex) {
		// valor sempre pode
		if (columnIndex > 0) { return true; }
		// se acima das fixas, pode
		if (rowIndex >= EntradasLancamentos.LINHAS_FIXAS) { return true; }
		// nao pode
		return false;
	}

	@Override
	public void setValueAt(final Object aValue, final int rowIndex, final int columnIndex) {
		lancamentos.set(rowIndex, columnIndex, aValue);
	}
}
