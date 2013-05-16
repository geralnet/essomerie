package net.geral.essomerie.client._gui.delivery.tabelas;

import javax.swing.JTable;
import javax.swing.table.TableColumnModel;

public class UltimosPedidosTable extends JTable {
	private static final long	serialVersionUID	= 1L;
	private static final int[]	WIDTHS				= { 100, 0 };

	public UltimosPedidosTable() {
		super(new UltimosPedidosModel());
		setAutoResizeMode(AUTO_RESIZE_LAST_COLUMN);
		setInitialTableSize();
		setDefaultRenderer(String.class, new UltimosPedidosRenderer());
	}

	@Override
	public UltimosPedidosModel getModel() {
		return (UltimosPedidosModel)super.getModel();
	}

	public void setInitialTableSize() {
		final TableColumnModel cm = getColumnModel();
		for (int i = 0; i < WIDTHS.length; i++) {
			if (WIDTHS[i] == 0) {
				continue;
			}
			cm.getColumn(i).setPreferredWidth(WIDTHS[i]);
		}
	}
}
