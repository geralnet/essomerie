package net.geral.essomerie.client._gui.caixa.panels.plantao;

import javax.swing.table.TableCellEditor;

import net.geral.essomerie.client._gui.caixa.base.BaseLancamentos;
import net.geral.essomerie.client._gui.caixa.base.BaseLancamentosTableModel;
import net.geral.essomerie.client._gui.caixa.base.editor.DinheiroTableCellEditor;
import net.geral.essomerie.client._gui.caixa.base.editor.IntegerTableCellEditor;
import net.geral.essomerie._shared.Dinheiro;

public class PlantaoTableModel extends BaseLancamentosTableModel {
	private static final long				serialVersionUID	= 1L;

	private static final String[]			COLUNAS				= { "Mesa", "Pessoas", "Consumo" };
	private static final int[]				TAMANHOS			= { 0, 0, 80 };
	private static final Class<?>[]			TIPOS				= { Integer.class, Integer.class, Dinheiro.class };

	private final IntegerTableCellEditor	integerEditor		= new IntegerTableCellEditor(false);
	private final DinheiroTableCellEditor	dinheiroEditor		= new DinheiroTableCellEditor(false, true);

	public PlantaoTableModel(final BaseLancamentos lancamentos) {
		super(lancamentos, COLUNAS, TAMANHOS);
	}

	@Override
	public TableCellEditor getCellEditor(final int row, final int column) {
		if (column == 2) { return dinheiroEditor; }
		return integerEditor;
	}

	@Override
	public Class<?> getColumnClass(final int columnIndex) {
		return TIPOS[columnIndex];
	}

	@Override
	public Object getValueAt(final int row, final int column) {
		return lancamentos.get(column, row);
	}

	@Override
	public boolean isCellEditable(final int rowIndex, final int columnIndex) {
		return true;
	}

	@Override
	public void setValueAt(final Object aValue, final int rowIndex, final int columnIndex) {
		lancamentos.set(rowIndex, columnIndex, aValue);
	}
}
