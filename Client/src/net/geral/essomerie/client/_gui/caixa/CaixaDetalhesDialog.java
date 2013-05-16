package net.geral.essomerie.client._gui.caixa;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Calendar;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import net.geral.essomerie._shared.CRUtil;
import net.geral.gui.textfield.IntegerTextField;
import net.geral.gui.textfield.formula.FormulaTextField;
import net.geral.gui.textfield.formula.FormulaTextFieldListener;

public class CaixaDetalhesDialog extends JDialog implements ItemListener, FormulaTextFieldListener<Integer>,
		ActionListener {
	private static final long		serialVersionUID	= 1L;
	private final IntegerTextField	tfDia;
	private final IntegerTextField	tfAno;
	private final JComboBox<String>	cbMes;
	private final JButton			btnDia;
	private final JButton			btnNoite;
	private final JLabel			lblDiaSemana;
	private final JLabel			lblComputador;

	private CaixaDetalhes			detalhes;

	public CaixaDetalhesDialog() {
		setModal(true);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setSize(new Dimension(530, 400));
		setPreferredSize(new Dimension(530, 400));
		setResizable(false);
		getContentPane().setLayout(new BorderLayout(0, 0));

		final JPanel panelTopo = new JPanel();
		getContentPane().add(panelTopo, BorderLayout.NORTH);
		panelTopo.setLayout(new GridLayout(0, 1, 0, 0));

		final JPanel panelData = new JPanel();
		panelTopo.add(panelData);

		tfDia = new IntegerTextField(false);
		tfDia.setHorizontalAlignment(SwingConstants.CENTER);
		tfDia.setFont(tfDia.getFont().deriveFont(tfDia.getFont().getSize() + 12f));
		panelData.add(tfDia);
		tfDia.setColumns(2);
		tfDia.addChangeListener(this);

		cbMes = new JComboBox<String>();
		cbMes.setFont(cbMes.getFont().deriveFont(cbMes.getFont().getSize() + 12f));
		cbMes.setModel(new DefaultComboBoxModel<String>(new String[] { "Janeiro", "Fevereiro", "Mar\u00E7o", "Abril",
				"Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro" }));
		panelData.add(cbMes);
		cbMes.addItemListener(this);

		tfAno = new IntegerTextField(false);
		tfAno.setHorizontalAlignment(SwingConstants.CENTER);
		tfAno.setFont(tfAno.getFont().deriveFont(tfAno.getFont().getSize() + 12f));
		panelData.add(tfAno);
		tfAno.setColumns(4);
		tfAno.addChangeListener(this);

		lblDiaSemana = new JLabel("[dia da semana]");
		lblDiaSemana.setHorizontalAlignment(SwingConstants.CENTER);
		lblDiaSemana.setFont(lblDiaSemana.getFont().deriveFont(lblDiaSemana.getFont().getStyle() | Font.BOLD,
				lblDiaSemana.getFont().getSize() + 10f));
		panelTopo.add(lblDiaSemana);

		lblComputador = new JLabel("[computador]");
		lblComputador.setHorizontalAlignment(SwingConstants.CENTER);
		lblComputador.setFont(lblComputador.getFont().deriveFont(lblComputador.getFont().getStyle() | Font.ITALIC,
				lblComputador.getFont().getSize() + 10f));
		panelTopo.add(lblComputador);

		final JPanel panelBotoes = new JPanel();
		getContentPane().add(panelBotoes, BorderLayout.SOUTH);

		btnDia = new JButton("");
		btnDia.setIcon(new ImageIcon(CaixaDetalhesDialog.class.getResource("/res/img/sol.png")));
		btnDia.addActionListener(this);
		panelBotoes.add(btnDia);

		btnNoite = new JButton("");
		btnNoite.setIcon(new ImageIcon(CaixaDetalhesDialog.class.getResource("/res/img/lua.png")));
		btnNoite.addActionListener(this);
		panelBotoes.add(btnNoite);

		loadDefaults();
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		detalhes = new CaixaDetalhes(getDate(), (e.getSource() == btnDia), CRUtil.getComputador("NA"));
		setVisible(false);
	}

	private Calendar getDate() {
		final Calendar c = Calendar.getInstance();
		c.clear();
		c.clear();
		c.set(tfAno.getValue(false), cbMes.getSelectedIndex(), tfDia.getValue(false));
		return c;
	}

	public CaixaDetalhes getDetalhes() {
		return detalhes;
	}

	@Override
	public void itemStateChanged(final ItemEvent e) {
		updateDiaSemana();
	}

	private void loadDefaults() {
		final Calendar c = Calendar.getInstance();
		tfDia.setText(String.format("%02d", c.get(Calendar.DAY_OF_MONTH)));
		cbMes.setSelectedIndex(c.get(Calendar.MONTH));
		tfAno.setText(String.format("%04d", c.get(Calendar.YEAR)));
		lblComputador.setText(CRUtil.getComputador("NA"));
		updateDiaSemana();
	}

	@Override
	public void setVisible(final boolean b) {
		//clear detalhes on show
		if (b) {
			detalhes = null;
		}
		super.setVisible(b);
	}

	private void updateDiaSemana() {
		boolean errors = tfDia.hasError() || tfAno.hasError();
		if (!errors) {
			final Calendar c = getDate();
			if (c.get(Calendar.DAY_OF_MONTH) == tfDia.getValue(false)) {
				lblDiaSemana.setText(CRUtil.getDiaSemana(c.get(Calendar.DAY_OF_WEEK)));
			}
			else {
				errors = true;
			}
		}
		if (errors) {
			lblDiaSemana.setText("[data inválida]");
		}
		btnDia.setEnabled(!errors);
		btnNoite.setEnabled(!errors);
	}

	@Override
	public void valueChanged(final FormulaTextField<Integer> src) {
		updateDiaSemana();
	}
}
