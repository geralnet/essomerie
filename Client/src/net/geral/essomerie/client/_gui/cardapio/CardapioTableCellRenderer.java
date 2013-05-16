package net.geral.essomerie.client._gui.cardapio;

import java.awt.Component;
import java.awt.Font;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class CardapioTableCellRenderer extends DefaultTableCellRenderer {
	private static final long	serialVersionUID	= 1L;

	public static CardapioTableCellRenderer createCodigo() {
		final CardapioTableCellRenderer r = new CardapioTableCellRenderer();
		r.monospaced = true;
		return r;
	}

	public static CardapioTableCellRenderer createNormal() {
		return new CardapioTableCellRenderer();
	}

	public static CardapioTableCellRenderer createPreco() {
		final CardapioTableCellRenderer r = new CardapioTableCellRenderer();
		r.numeric = true;
		return r;
	}

	private final Font	defaultFont;

	private final Font	monospacedFont;

	private boolean		monospaced	= false;

	private boolean		numeric		= false;

	private CardapioTableCellRenderer() {
		defaultFont = getFont();
		monospacedFont = new Font("monospaced", defaultFont.getStyle(), defaultFont.getSize());
	}

	@Override
	public Component getTableCellRendererComponent(final JTable table, final Object value, final boolean isSelected,
			final boolean hasFocus, final int row, final int column) {
		super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		setFont(monospaced ? monospacedFont : defaultFont);
		setHorizontalAlignment(numeric ? TRAILING : LEADING);

		return this;
	}
}
