package net.geral.essomerie.client._gui.caixa;

import java.awt.BorderLayout;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.security.InvalidParameterException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import net.geral.essomerie.client._gui.caixa.base.BaseLancamentos;
import net.geral.essomerie.client._gui.caixa.base.strdin.StringDinheiroLancamento;
import net.geral.essomerie.client._gui.caixa.panels.conferencia.ConferenciaPanel;
import net.geral.essomerie.client._gui.caixa.panels.delivery.DeliveryPanel;
import net.geral.essomerie.client._gui.caixa.panels.entradas.EntradasLancamentos;
import net.geral.essomerie.client._gui.caixa.panels.entradas.EntradasLancamentosPanel;
import net.geral.essomerie.client._gui.caixa.panels.fundo.FundoLancamentosPanel;
import net.geral.essomerie.client._gui.caixa.panels.plantao.PlantaoPanel;
import net.geral.essomerie.client._gui.caixa.panels.saidas.SaidasLancamentosPanel;
import net.geral.essomerie.client._gui.caixa.panels.salao.SalaoPanel;
import net.geral.essomerie.client._gui.caixa.panels.viagem.ViagemPanel;
import net.geral.essomerie.client._gui.shared.label.DinheiroLabel;
import net.geral.essomerie.client.gui.main.TabPanel;
import net.geral.essomerie._shared.Dinheiro;

public class CaixaCompletoPanel extends TabPanel implements ActionListener, LancamentosListener {
	private static final long				serialVersionUID	= 1L;
	private final DinheiroLabel				lblSaldoEmCaixa;
	private final SaidasLancamentosPanel	panelSaidas;
	private final EntradasLancamentosPanel	panelEntradas;
	private final JPanel					panelColuna2;
	private final DeliveryPanel				panelDelivery;
	private final PlantaoPanel				panelPlantao;
	private final ViagemPanel				panelViagem;
	private final SalaoPanel				panelSalao;
	private final ConferenciaPanel			panelConferencia;
	private final JPanel					panelDiferenca;
	private final JLabel					lblDiferenca;
	private final DinheiroLabel				lblDiferencaValor;
	private final JPanel					panelColuna3;
	private final ResumoMesasPanel			panelResumoMesas;
	private final Lancamentos				lancamentos			= new Lancamentos();

	private final CaixaDetalhes				detalhes;
	private final JButton					btnImprimir;

	@Deprecated
	public CaixaCompletoPanel() {
		this(new CaixaDetalhes());
	}

	public CaixaCompletoPanel(final CaixaDetalhes caixaDetalhes) {
		detalhes = caixaDetalhes;

		final SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);

		final JPanel panelColuna1 = new JPanel();
		springLayout.putConstraint(SpringLayout.NORTH, panelColuna1, 10, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, panelColuna1, 10, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, panelColuna1, -10, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, panelColuna1, 260, SpringLayout.WEST, this);
		add(panelColuna1);
		final SpringLayout sl_panelColuna1 = new SpringLayout();
		panelColuna1.setLayout(sl_panelColuna1);

		final FundoLancamentosPanel panelFundo = new FundoLancamentosPanel(lancamentos.fundo);
		sl_panelColuna1.putConstraint(SpringLayout.NORTH, panelFundo, 0, SpringLayout.NORTH, panelColuna1);
		sl_panelColuna1.putConstraint(SpringLayout.WEST, panelFundo, 0, SpringLayout.WEST, panelColuna1);
		sl_panelColuna1.putConstraint(SpringLayout.SOUTH, panelFundo, 100, SpringLayout.NORTH, panelColuna1);
		sl_panelColuna1.putConstraint(SpringLayout.EAST, panelFundo, 0, SpringLayout.EAST, panelColuna1);
		panelColuna1.add(panelFundo);

		panelEntradas = new EntradasLancamentosPanel(lancamentos.entradas);
		sl_panelColuna1.putConstraint(SpringLayout.NORTH, panelEntradas, 20, SpringLayout.SOUTH, panelFundo);
		sl_panelColuna1.putConstraint(SpringLayout.WEST, panelEntradas, 0, SpringLayout.WEST, panelColuna1);
		sl_panelColuna1.putConstraint(SpringLayout.SOUTH, panelEntradas, 180, SpringLayout.SOUTH, panelFundo);
		sl_panelColuna1.putConstraint(SpringLayout.EAST, panelEntradas, 0, SpringLayout.EAST, panelColuna1);
		panelColuna1.add(panelEntradas);

		panelSaidas = new SaidasLancamentosPanel(lancamentos.saidas);
		sl_panelColuna1.putConstraint(SpringLayout.NORTH, panelSaidas, 10, SpringLayout.SOUTH, panelEntradas);
		sl_panelColuna1.putConstraint(SpringLayout.WEST, panelSaidas, 0, SpringLayout.WEST, panelColuna1);
		sl_panelColuna1.putConstraint(SpringLayout.EAST, panelSaidas, 0, SpringLayout.EAST, panelColuna1);
		panelColuna1.add(panelSaidas);

		final JPanel panelSaldo = new JPanel();
		sl_panelColuna1.putConstraint(SpringLayout.SOUTH, panelSaidas, -10, SpringLayout.NORTH, panelSaldo);
		sl_panelColuna1.putConstraint(SpringLayout.WEST, panelSaldo, 0, SpringLayout.WEST, panelColuna1);
		sl_panelColuna1.putConstraint(SpringLayout.EAST, panelSaldo, 0, SpringLayout.EAST, panelColuna1);
		springLayout.putConstraint(SpringLayout.SOUTH, panelSaidas, -10, SpringLayout.NORTH, panelSaldo);
		springLayout.putConstraint(SpringLayout.WEST, panelSaldo, 0, SpringLayout.WEST, panelSaidas);
		springLayout.putConstraint(SpringLayout.SOUTH, panelSaldo, -10, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, panelSaldo, 0, SpringLayout.EAST, panelSaidas);
		panelColuna1.add(panelSaldo);
		panelSaldo.setLayout(new BorderLayout(0, 0));

		final JLabel lblSaldo = new JLabel("Saldo em Caixa:");
		lblSaldo.setFont(lblSaldo.getFont().deriveFont(lblSaldo.getFont().getStyle() | Font.BOLD));
		panelSaldo.add(lblSaldo);

		lblSaldoEmCaixa = new DinheiroLabel(true);
		panelSaldo.add(lblSaldoEmCaixa, BorderLayout.EAST);

		panelColuna2 = new JPanel();
		springLayout.putConstraint(SpringLayout.NORTH, panelColuna2, 0, SpringLayout.NORTH, panelColuna1);
		springLayout.putConstraint(SpringLayout.WEST, panelColuna2, 10, SpringLayout.EAST, panelColuna1);
		springLayout.putConstraint(SpringLayout.SOUTH, panelColuna2, 0, SpringLayout.SOUTH, panelColuna1);
		springLayout.putConstraint(SpringLayout.EAST, panelColuna2, 260, SpringLayout.EAST, panelColuna1);

		final JPanel panelBotoes = new JPanel();
		sl_panelColuna1.putConstraint(SpringLayout.SOUTH, panelSaldo, -10, SpringLayout.NORTH, panelBotoes);
		sl_panelColuna1.putConstraint(SpringLayout.WEST, panelBotoes, 0, SpringLayout.WEST, panelColuna1);
		sl_panelColuna1.putConstraint(SpringLayout.SOUTH, panelBotoes, 0, SpringLayout.SOUTH, panelColuna1);
		sl_panelColuna1.putConstraint(SpringLayout.EAST, panelBotoes, 0, SpringLayout.EAST, panelColuna1);
		springLayout.putConstraint(SpringLayout.WEST, panelBotoes, 0, SpringLayout.WEST, panelColuna1);
		springLayout.putConstraint(SpringLayout.SOUTH, panelBotoes, 0, SpringLayout.SOUTH, panelColuna1);
		springLayout.putConstraint(SpringLayout.EAST, panelBotoes, 0, SpringLayout.EAST, panelColuna1);
		panelColuna1.add(panelBotoes);
		panelBotoes.setLayout(new GridLayout(0, 2, 5, 0));

		final JButton btnCarregar = new JButton("Carregar");
		btnCarregar.addActionListener(this);
		btnCarregar.setActionCommand("carregar");
		panelBotoes.add(btnCarregar);

		final JButton btnSalvar = new JButton("Salvar");
		btnSalvar.setActionCommand("salvar");
		btnSalvar.addActionListener(this);
		panelBotoes.add(btnSalvar);

		final JButton btnFechar = new JButton("Fechar");
		panelBotoes.add(btnFechar);

		btnImprimir = new JButton("Imprimir");
		btnImprimir.setActionCommand("print");
		btnImprimir.addActionListener(this);
		panelBotoes.add(btnImprimir);
		add(panelColuna2);
		final SpringLayout sl_panelColuna2 = new SpringLayout();
		panelColuna2.setLayout(sl_panelColuna2);

		panelDelivery = new DeliveryPanel(lancamentos.delivery);
		sl_panelColuna2.putConstraint(SpringLayout.NORTH, panelDelivery, 0, SpringLayout.NORTH, panelColuna2);
		sl_panelColuna2.putConstraint(SpringLayout.WEST, panelDelivery, 0, SpringLayout.WEST, panelColuna2);
		sl_panelColuna2.putConstraint(SpringLayout.SOUTH, panelDelivery, 180, SpringLayout.NORTH, panelColuna2);
		sl_panelColuna2.putConstraint(SpringLayout.EAST, panelDelivery, 0, SpringLayout.EAST, panelColuna2);
		panelColuna2.add(panelDelivery);

		panelViagem = new ViagemPanel(lancamentos.viagem);
		sl_panelColuna2.putConstraint(SpringLayout.NORTH, panelViagem, 10, SpringLayout.SOUTH, panelDelivery);
		sl_panelColuna2.putConstraint(SpringLayout.WEST, panelViagem, 0, SpringLayout.WEST, panelColuna2);
		sl_panelColuna2.putConstraint(SpringLayout.SOUTH, panelViagem, 130, SpringLayout.SOUTH, panelDelivery);
		sl_panelColuna2.putConstraint(SpringLayout.EAST, panelViagem, 0, SpringLayout.EAST, panelColuna2);
		panelColuna2.add(panelViagem);

		panelPlantao = new PlantaoPanel(lancamentos.plantao);
		sl_panelColuna2.putConstraint(SpringLayout.NORTH, panelPlantao, 10, SpringLayout.SOUTH, panelViagem);
		sl_panelColuna2.putConstraint(SpringLayout.WEST, panelPlantao, 0, SpringLayout.WEST, panelColuna2);
		sl_panelColuna2.putConstraint(SpringLayout.SOUTH, panelPlantao, 130, SpringLayout.SOUTH, panelViagem);
		sl_panelColuna2.putConstraint(SpringLayout.EAST, panelPlantao, 0, SpringLayout.EAST, panelColuna2);
		panelColuna2.add(panelPlantao);

		panelConferencia = new ConferenciaPanel(lancamentos.conferencia);
		sl_panelColuna2.putConstraint(SpringLayout.NORTH, panelConferencia, 30, SpringLayout.SOUTH, panelPlantao);
		sl_panelColuna2.putConstraint(SpringLayout.WEST, panelConferencia, 0, SpringLayout.WEST, panelColuna2);
		sl_panelColuna2.putConstraint(SpringLayout.EAST, panelConferencia, 0, SpringLayout.EAST, panelColuna2);
		panelColuna2.add(panelConferencia);

		panelDiferenca = new JPanel();
		sl_panelColuna2.putConstraint(SpringLayout.SOUTH, panelConferencia, -10, SpringLayout.NORTH, panelDiferenca);
		sl_panelColuna2.putConstraint(SpringLayout.WEST, panelDiferenca, 0, SpringLayout.WEST, panelColuna2);
		sl_panelColuna2.putConstraint(SpringLayout.SOUTH, panelDiferenca, 0, SpringLayout.SOUTH, panelColuna2);
		sl_panelColuna2.putConstraint(SpringLayout.EAST, panelDiferenca, 0, SpringLayout.EAST, panelColuna2);
		panelColuna2.add(panelDiferenca);
		panelDiferenca.setLayout(new GridLayout(0, 2, 0, 0));

		lblDiferenca = new JLabel("Diferen\u00E7a:");
		lblDiferenca.setFont(lblDiferenca.getFont().deriveFont(lblDiferenca.getFont().getStyle() | Font.BOLD));
		panelDiferenca.add(lblDiferenca);

		lblDiferencaValor = new DinheiroLabel(true);
		lblDiferencaValor.setDouble(0.0);
		panelDiferenca.add(lblDiferencaValor);

		panelColuna3 = new JPanel();
		springLayout.putConstraint(SpringLayout.NORTH, panelColuna3, 0, SpringLayout.NORTH, panelColuna2);
		springLayout.putConstraint(SpringLayout.WEST, panelColuna3, 10, SpringLayout.EAST, panelColuna2);
		springLayout.putConstraint(SpringLayout.SOUTH, panelColuna3, 0, SpringLayout.SOUTH, panelColuna2);
		springLayout.putConstraint(SpringLayout.EAST, panelColuna3, -10, SpringLayout.EAST, this);
		add(panelColuna3);
		final SpringLayout sl_panelColuna3 = new SpringLayout();
		panelColuna3.setLayout(sl_panelColuna3);

		panelSalao = new SalaoPanel(lancamentos.salao);
		sl_panelColuna3.putConstraint(SpringLayout.NORTH, panelSalao, 0, SpringLayout.NORTH, panelColuna3);
		sl_panelColuna3.putConstraint(SpringLayout.WEST, panelSalao, 0, SpringLayout.WEST, panelColuna3);
		sl_panelColuna3.putConstraint(SpringLayout.EAST, panelSalao, 0, SpringLayout.EAST, panelColuna3);
		springLayout.putConstraint(SpringLayout.NORTH, panelSalao, 0, SpringLayout.NORTH, panelColuna3);
		springLayout.putConstraint(SpringLayout.WEST, panelSalao, 0, SpringLayout.WEST, panelColuna3);
		springLayout.putConstraint(SpringLayout.SOUTH, panelSalao, 0, SpringLayout.SOUTH, panelColuna3);
		springLayout.putConstraint(SpringLayout.EAST, panelSalao, 0, SpringLayout.EAST, panelColuna3);
		panelColuna3.add(panelSalao);

		panelResumoMesas = new ResumoMesasPanel(lancamentos.plantao, lancamentos.salao);
		sl_panelColuna3.putConstraint(SpringLayout.SOUTH, panelSalao, -10, SpringLayout.NORTH, panelResumoMesas);
		sl_panelColuna3.putConstraint(SpringLayout.WEST, panelResumoMesas, 0, SpringLayout.WEST, panelColuna3);
		sl_panelColuna3.putConstraint(SpringLayout.SOUTH, panelResumoMesas, 0, SpringLayout.SOUTH, panelColuna3);
		sl_panelColuna3.putConstraint(SpringLayout.EAST, panelResumoMesas, 0, SpringLayout.EAST, panelColuna3);
		panelColuna3.add(panelResumoMesas);

		preparar();
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		final String cmd = e.getActionCommand();
		if ("salvar".equals(cmd)) {
			save();
		}
		else if ("carregar".equals(cmd)) {
			load();
		}
		else if ("print".equals(cmd)) {
			print();
		}
	}

	private void atualizarCaixaConferencia() {
		final Dinheiro fundo = lancamentos.fundo.getTotal();
		final Dinheiro entradas = lancamentos.entradas.getTotal();
		final Dinheiro pagamentos = lancamentos.saidas.getTotal();
		final Dinheiro saldo = fundo.add(entradas).subtract(pagamentos);

		final Dinheiro conferencia = lancamentos.conferencia.getTotal();
		final Dinheiro diferenca = Dinheiro.subtract(conferencia, saldo);

		lblSaldoEmCaixa.setDinheiro(saldo);
		lblDiferencaValor.setDinheiro(diferenca);
	}

	@Override
	public String getTabText() {
		return "Caixa";
	}

	@Override
	public void lancamentoAlterado(final BaseLancamentos origem, final int index) {
		if (origem == lancamentos.fundo) {
			atualizarCaixaConferencia();
			return;
		}
		if (origem == lancamentos.entradas) {
			atualizarCaixaConferencia();
			return;
		}
		if (origem == lancamentos.saidas) {
			atualizarCaixaConferencia();
			return;
		}
		if (origem == lancamentos.delivery) {
			lancamentos.entradas.set(EntradasLancamentos.DELIVERY, StringDinheiroLancamento.VALOR,
					lancamentos.delivery.getTotal());
			return;
		}
		if (origem == lancamentos.viagem) {
			lancamentos.entradas.set(EntradasLancamentos.VIAGEM, StringDinheiroLancamento.VALOR,
					lancamentos.viagem.getTotal());
			return;
		}
		if (origem == lancamentos.plantao) {
			lancamentos.entradas.set(EntradasLancamentos.PLANTAO, StringDinheiroLancamento.VALOR,
					lancamentos.plantao.getTotal());
			panelResumoMesas.atualizarSalao();
			return;
		}
		if (origem == lancamentos.conferencia) {
			atualizarCaixaConferencia();
			return;
		}
		if (origem == lancamentos.salao) {
			lancamentos.entradas.set(EntradasLancamentos.SALAO, StringDinheiroLancamento.VALOR,
					lancamentos.salao.getTotalConsumo());
			panelResumoMesas.atualizarSalao();
			return;
		}
		throw new InvalidParameterException("Invalid source: " + origem);
	}

	private void load() {
		try {
			final FileDialog fd = new FileDialog((Frame)null, "Carregar", FileDialog.LOAD);
			fd.setDirectory(Lancamentos.SAVE_DIRECTORY.getPath());
			fd.setVisible(true);

			final File[] file = fd.getFiles();
			if ((file == null) || (file.length == 0)) { return; }

			detalhes.set(lancamentos.load(file[0]));
			atualizarCaixaConferencia();
		}
		catch (final SaveLoadException e) {
			e.printStackTrace();
		}
	}

	private void preparar() {
		lancamentos.addLancamentosListener(this);
		atualizarCaixaConferencia();
	}

	private void print() {
		//final CaixaImprimirFrame cif = new CaixaImprimirFrame(detalhes, lancamentos);
		//cif.setVisible(true);
	}

	private void save() {
		try {
			lancamentos.salvar(detalhes);
		}
		catch (final SaveLoadException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void tabClosed() {}

	@Override
	public boolean tabCloseRequest() {
		return true;
	}

	@Override
	public void tabCreated() {}
}
