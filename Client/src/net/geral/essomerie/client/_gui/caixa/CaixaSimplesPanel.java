package net.geral.essomerie.client._gui.caixa;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SpringLayout;
import javax.swing.table.DefaultTableModel;

public class CaixaSimplesPanel extends JPanel {
	private static final long	serialVersionUID	= 1L;
	private final JTable		table;
	private final JTextField	textField;

	public CaixaSimplesPanel() {
		final SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);

		final JRadioButton rdbtnMesa = new JRadioButton("Mesa");
		springLayout.putConstraint(SpringLayout.NORTH, rdbtnMesa, 10, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, rdbtnMesa, 10, SpringLayout.WEST, this);
		add(rdbtnMesa);

		final JRadioButton rdbtnViagem = new JRadioButton("Viagem");
		springLayout.putConstraint(SpringLayout.WEST, rdbtnViagem, 6, SpringLayout.EAST, rdbtnMesa);
		springLayout.putConstraint(SpringLayout.SOUTH, rdbtnViagem, 0, SpringLayout.SOUTH, rdbtnMesa);
		add(rdbtnViagem);

		final JRadioButton rdbtnDelivery = new JRadioButton("Delivery");
		springLayout.putConstraint(SpringLayout.WEST, rdbtnDelivery, 6, SpringLayout.EAST, rdbtnViagem);
		springLayout.putConstraint(SpringLayout.SOUTH, rdbtnDelivery, 0, SpringLayout.SOUTH, rdbtnMesa);
		add(rdbtnDelivery);

		final JScrollPane scrollPane = new JScrollPane();
		springLayout.putConstraint(SpringLayout.NORTH, scrollPane, 6, SpringLayout.SOUTH, rdbtnMesa);
		springLayout.putConstraint(SpringLayout.WEST, scrollPane, 10, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, scrollPane, 138, SpringLayout.SOUTH, rdbtnMesa);
		springLayout.putConstraint(SpringLayout.EAST, scrollPane, 223, SpringLayout.WEST, this);
		add(scrollPane);

		table = new JTable();
		table.setModel(new DefaultTableModel(new Object[][] { { null, null, null }, { null, null, null },
				{ null, null, null }, }, new String[] { "New column", "New column", "New column" }));
		scrollPane.setViewportView(table);

		textField = new JTextField();
		springLayout.putConstraint(SpringLayout.WEST, textField, 10, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, textField, -10, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, textField, 440, SpringLayout.WEST, this);
		add(textField);
		textField.setColumns(10);

		final JLabel lblComando = new JLabel("Comando:");
		springLayout.putConstraint(SpringLayout.WEST, lblComando, 0, SpringLayout.WEST, rdbtnMesa);
		springLayout.putConstraint(SpringLayout.SOUTH, lblComando, -6, SpringLayout.NORTH, textField);
		add(lblComando);

		final JTextPane txtpnCouvert = new JTextPane();
		txtpnCouvert
				.setText("1 - Couvert\r\n2 - \u00C1gua/Caf\u00E9/Ch\u00E1\r\n3 - Refrigerante/Latas\r\n4 - Outras Bebidas\r\n5 - Antipastos\r\n6 - Saladas\r\n7 - Massas\r\n8 - Molhos\r\n9 - Carnes\r\n0 - Outros\r\n. - Meio\r\n* - Quantidade\r\n/ - Exibir Lista");
		springLayout.putConstraint(SpringLayout.NORTH, txtpnCouvert, 30, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, txtpnCouvert, 139, SpringLayout.EAST, scrollPane);
		springLayout.putConstraint(SpringLayout.SOUTH, txtpnCouvert, 310, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.EAST, txtpnCouvert, -46, SpringLayout.EAST, this);
		add(txtpnCouvert);
	}
}
