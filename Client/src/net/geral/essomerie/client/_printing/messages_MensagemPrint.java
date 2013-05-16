package net.geral.essomerie.client._printing;

import java.awt.Font;

import net.geral.essomerie.client.core.Client;
import net.geral.essomerie._shared.mensagens.Message;

public class messages_MensagemPrint extends CRPrintDocument {
	private final Message	msg;
	private final Font		FONTE_HEADER	= new Font("monospaced", Font.PLAIN, 7);
	private final Font		FONTE_MENSAGEM	= new Font("SansSerif", Font.PLAIN, 6);

	public messages_MensagemPrint(final Message m) {
		msg = m;
	}

	@Override
	protected void printBody() {
		g.setFont(FONTE_HEADER);
		writeBold("  De: ");
		writeline(Client.cache().users().get(msg.getFrom()).getName());

		final String para = (msg.getTo() == 0) ? "*** TODOS ***" : Client.cache().users().get(msg.getTo()).getName();
		writeBold("Para: ");
		writeline(para);

		writeBold("Data: ");
		writeline(msg.getSent().toString());

		separator();

		g.setFont(FONTE_MENSAGEM);
		writeline(msg.getMessage());
	}
}
