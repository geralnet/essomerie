package net.geral.essomerie.client._gui.cardapio;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpringLayout;

import net.geral.essomerie.client.gui.main.TabPanel;

public class CardapioInterativoPanel extends TabPanel implements KeyListener, ActionListener, ComponentListener {
	private static final long	serialVersionUID	= 1L;
	private final JTextField	txtProcurarPor;
	private final CardapioTable	cardapioTable;
	private final JButton		btnLimpar;

	public CardapioInterativoPanel() {
		final SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);

		final JLabel lblProcurarPor = new JLabel("Procurar Por:");
		springLayout.putConstraint(SpringLayout.NORTH, lblProcurarPor, 10, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, lblProcurarPor, 10, SpringLayout.WEST, this);
		add(lblProcurarPor);

		txtProcurarPor = new JTextField();
		txtProcurarPor.addKeyListener(this);
		springLayout.putConstraint(SpringLayout.NORTH, txtProcurarPor, 6, SpringLayout.SOUTH, lblProcurarPor);
		springLayout.putConstraint(SpringLayout.WEST, txtProcurarPor, 0, SpringLayout.WEST, lblProcurarPor);
		add(txtProcurarPor);
		txtProcurarPor.setColumns(10);

		final JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		springLayout.putConstraint(SpringLayout.NORTH, scrollPane, 10, SpringLayout.SOUTH, txtProcurarPor);
		springLayout.putConstraint(SpringLayout.WEST, scrollPane, 10, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, scrollPane, -10, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, scrollPane, -10, SpringLayout.EAST, this);
		add(scrollPane);

		cardapioTable = new CardapioTable(scrollPane);
		scrollPane.setViewportView(cardapioTable);

		btnLimpar = new JButton("Limpar");
		btnLimpar.addActionListener(this);
		springLayout.putConstraint(SpringLayout.EAST, txtProcurarPor, -10, SpringLayout.WEST, btnLimpar);
		springLayout.putConstraint(SpringLayout.SOUTH, btnLimpar, 0, SpringLayout.SOUTH, txtProcurarPor);
		springLayout.putConstraint(SpringLayout.EAST, btnLimpar, -10, SpringLayout.EAST, this);
		add(btnLimpar);

		addComponentListener(this);
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		if (e.getSource() == btnLimpar) {
			txtProcurarPor.setText("");
			updateTable();
		}
	}

	//	@Override
	//	public void cardapioLoaded() {
	//		updateTable();
	//	}

	@Override
	public void componentHidden(final ComponentEvent e) {}

	@Override
	public void componentMoved(final ComponentEvent e) {}

	@Override
	public void componentResized(final ComponentEvent e) {}

	@Override
	public void componentShown(final ComponentEvent e) {
		updateTable();
	}

	@Override
	public String getTabText() {
		return "Cardápio Interativo";
	}

	@Override
	public void keyPressed(final KeyEvent arg0) {}

	@Override
	public void keyReleased(final KeyEvent e) {
		updateTable();
	}

	@Override
	public void keyTyped(final KeyEvent e) {}

	//	@Override
	//	public void tabClosed() {
	//		Events.removeCardapioListener(this);
	//	}

	@Override
	public void tabClosed() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean tabCloseRequest() {
		return true;
	}

	@Override
	public void tabCreated() {
		//		Events.addCardapioListener(this);
	}

	public void updateTable() {
		cardapioTable.filter(txtProcurarPor.getText());
	}
}
