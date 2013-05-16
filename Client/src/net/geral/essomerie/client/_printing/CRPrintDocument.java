package net.geral.essomerie.client._printing;

import java.awt.Font;

import net.geral.essomerie.client.core.Client;
import net.geral.essomerie._shared.CRUtil;
import net.geral.essomerie._shared.User;
import net.geral.printing.BematechDocument;

import org.joda.time.LocalDateTime;

public abstract class CRPrintDocument extends BematechDocument {
	public static final Font	FOOTER_FONT	= new Font("SansSerif", Font.PLAIN, 6);

	@Override
	protected final void print() {
		printHeader();
		printBody();
		printFooter();
	}

	protected abstract void printBody();

	private void printFooter() {
		if (next_character_x != 0) {
			newline();
		}
		g.setFont(new Font("SansSerif", Font.PLAIN, 4));
		final User logged = Client.cache().users().getLogged();
		final LocalDateTime now = LocalDateTime.now();
		separator();
		writeline("Impresso por " + logged.getName() + " (" + CRUtil.getComputador("???") + ") em " + now.toString()
				+ ".");
	}

	private void printHeader() {
		drawImage("header", 0, 0, 203);
		feed(DEFAULT_SEPARATOR_PADDING);
	}
}
