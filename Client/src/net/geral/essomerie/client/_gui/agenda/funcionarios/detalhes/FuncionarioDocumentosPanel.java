package net.geral.essomerie.client._gui.agenda.funcionarios.detalhes;

import javax.swing.JPanel;
import javax.swing.SpringLayout;

public class FuncionarioDocumentosPanel extends JPanel {
	private static final long	serialVersionUID	= 1L;

	public FuncionarioDocumentosPanel() {
		final SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);

		final JPanel panelCTPS = new JPanel();
		springLayout.putConstraint(SpringLayout.NORTH, panelCTPS, 0, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, panelCTPS, 0, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, panelCTPS, 50, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.EAST, panelCTPS, 0, SpringLayout.EAST, this);
		add(panelCTPS);
	}
}
