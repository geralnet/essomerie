package net.geral.essomerie.client._gui.delivery.tabelas;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

public class UltimosPedidosRenderer extends DefaultTableCellRenderer {
	private static final long	serialVersionUID	= 1L;

	@Override
	public Component getTableCellRendererComponent(final JTable table, final Object value, final boolean isSelected,
			final boolean hasFocus, final int row, final int column) {
		super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		setHorizontalAlignment(SwingConstants.RIGHT);
		return this;
	}
}
