package net.geral.essomerie.client._gui.agenda.clientes.listagem;

import java.awt.BorderLayout;

import net.geral.essomerie.client._gui.agenda.clientes.paineis.ClientePanel;
import net.geral.essomerie.client.gui.main.TabPanel;

public class ListagemDeClientesTabPanel extends TabPanel {
	private static final long	serialVersionUID	= 1L;
	private final ClientePanel	panel;

	public ListagemDeClientesTabPanel() {
		setLayout(new BorderLayout(0, 0));

		panel = new ClientePanel();
		add(panel, BorderLayout.CENTER);
	}

	@Override
	public String getTabText() {
		return "Clientes";
	}

	@Override
	public void tabClosed() {
		panel.removeListeners();
	}

	@Override
	public boolean tabCloseRequest() {
		return true;
	}

	@Override
	public void tabCreated() {
		panel.addListeners();
		//		Client.cache().clientes().request();
	}
}
