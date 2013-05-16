package net.geral.essomerie.client._gui.caixa.base;

import java.security.InvalidParameterException;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumnModel;

import net.geral.essomerie.client._gui.caixa.LancamentosListener;

public abstract class BaseLancamentosTableModel extends AbstractTableModel implements LancamentosListener {
	private static final long		serialVersionUID	= 1L;

	private String[]				columnNames			= { "" };
	private int[]					columnWidths		= { 0 };

	public final BaseLancamentos	lancamentos;

	public BaseLancamentosTableModel(final BaseLancamentos lancamentos, final String[] colunas, final int[] tamanhos) {
		this.lancamentos = lancamentos;
		lancamentos.addLancamentosListener(this);
		setColumns(colunas, tamanhos);
	}

	public abstract TableCellEditor getCellEditor(int row, int column);

	@Override
	public abstract Class<?> getColumnClass(int columnIndex);

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public String getColumnName(final int columnIndex) {
		return columnNames[columnIndex];
	}

	@Override
	public int getRowCount() {
		return lancamentos.count();
	}

	@Override
	public abstract Object getValueAt(final int row, final int column);

	@Override
	public abstract boolean isCellEditable(final int rowIndex, final int columnIndex);

	@Override
	public void lancamentoAlterado(final BaseLancamentos origem, final int row) {
		if (row == -1) {
			fireTableDataChanged();
		}
		else {
			fireTableRowsUpdated(row, row);
		}
	}

	public void resized(final int width, final TableColumnModel columnModel) {
		if (columnWidths == null) { return; // nothing to do
		}

		// check how many variable widths and available width left
		int zeroCount = 0;
		int usedWidths = 0;
		for (final int columnWidth : columnWidths) {
			if (columnWidth == 0) {
				zeroCount++;
			}
			else {
				usedWidths += columnWidth;
			}
		}

		// calculate the variable width
		int availWidth = width - usedWidths;
		if (availWidth < 0) {
			availWidth = 0;
		}
		final int variableWidth = (zeroCount == 0) ? 0 : (availWidth / zeroCount);

		// sets the sizes, last one takes any remaining space (rounding error)
		usedWidths = 0;
		int i;
		for (i = 0; i < (columnWidths.length - 1); i++) {
			final int w = columnWidths[i] == 0 ? variableWidth : columnWidths[i];
			columnModel.getColumn(i).setPreferredWidth(w);
			usedWidths += w;
		}
		columnModel.getColumn(i).setPreferredWidth(width - usedWidths); // last column
	}

	private void setColumns(final String[] names, final int[] sizes) {
		if (names == null) { throw new NullPointerException("Columns cannot be null."); }
		if ((sizes != null) && (names.length != sizes.length)) { throw new InvalidParameterException(
				"Array sizes differ, names(" + names.length + ") sizes(" + sizes.length + ")"); }
		columnNames = names;
		columnWidths = new int[names.length];
		for (int i = 0; i < sizes.length; i++) {
			this.columnWidths[i] = (sizes == null) ? 0 : sizes[i];
		}
	}

	@Override
	public abstract void setValueAt(final Object aValue, final int rowIndex, final int columnIndex);
}
