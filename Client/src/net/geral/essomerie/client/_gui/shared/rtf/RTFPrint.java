package net.geral.essomerie.client._gui.shared.rtf;

import java.awt.Dimension;
import java.awt.Graphics2D;

import net.geral.essomerie.client._printing.CRPrintDocument;

public class RTFPrint extends CRPrintDocument {
	private static final double	ESCALA	= 0.6;
	private final RTFPane		text;

	public RTFPrint(final RTFPane tp) {
		final RTFPane pane = new RTFPane(tp.getRTF());
		final Dimension d = new Dimension((int)(PRINTING_WIDTH / ESCALA), Integer.MAX_VALUE);
		pane.setSize(d);
		d.height = pane.getPreferredSize().height;
		pane.setSize(d);
		text = pane;
	}

	@Override
	protected void printBody() {
		final Graphics2D g2 = (Graphics2D)g.create();
		g2.scale(ESCALA, ESCALA); //half size
		text.print(g2);
		feed((int)Math.ceil(text.getSize().height * ESCALA));
	}
}
