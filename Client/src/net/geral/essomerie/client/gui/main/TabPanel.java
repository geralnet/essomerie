package net.geral.essomerie.client.gui.main;

import javax.swing.JPanel;

import net.geral.essomerie.client.core.Client;

public abstract class TabPanel extends JPanel {
	private static final long	serialVersionUID	= 1L;

	public void close(final boolean force) {
		Client.window().closeTab(this, force);
	}

	public abstract String getTabText();

	public abstract void tabClosed();

	public abstract boolean tabCloseRequest();

	public abstract void tabCreated();
}
