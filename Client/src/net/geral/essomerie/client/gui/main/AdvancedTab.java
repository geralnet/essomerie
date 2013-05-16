package net.geral.essomerie.client.gui.main;

import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import net.geral.essomerie.client.core.Client;
import net.geral.essomerie.client.resources.IMG;

public class AdvancedTab extends JPanel implements MouseListener {
	private static final long	serialVersionUID	= 1L;
	private static final String	POSTFIX				= "  ";
	private static final int	ICON_SIZE			= 16;

	private final JLabel		label;
	private final JLabel		close;

	private final TabPanel		tabPanel;

	public AdvancedTab(final String title, final JTabbedPane pane, final TabPanel panel) {
		super(new FlowLayout(FlowLayout.LEFT, 0, 0));
		this.tabPanel = panel;

		label = new JLabel(title + POSTFIX);
		close = new JLabel(IMG.ICON__CLOSE_NORMAL.icon(16));
		close.addMouseListener(this);

		setOpaque(false);

		add(label);
		add(close);
	}

	@Override
	public void mouseClicked(final MouseEvent e) {
		Client.window().closeTab(tabPanel);
	}

	@Override
	public void mouseEntered(final MouseEvent e) {
		close.setIcon(IMG.ICON__CLOSE_OVER.icon(ICON_SIZE));
	}

	@Override
	public void mouseExited(final MouseEvent e) {
		close.setIcon(IMG.ICON__CLOSE_NORMAL.icon(ICON_SIZE));
	}

	@Override
	public void mousePressed(final MouseEvent e) {
		close.setIcon(IMG.ICON__CLOSE_PRESSED.icon(ICON_SIZE));
	}

	@Override
	public void mouseReleased(final MouseEvent e) {
		close.setIcon(IMG.ICON__CLOSE_OVER.icon(ICON_SIZE));
	}

	public void setTitle(final String title) {
		label.setText(title + POSTFIX);
	}
}
