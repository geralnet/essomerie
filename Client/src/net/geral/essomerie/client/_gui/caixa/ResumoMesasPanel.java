package net.geral.essomerie.client._gui.caixa;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import net.geral.essomerie.client._gui.caixa.panels.plantao.PlantaoLancamentos;
import net.geral.essomerie.client._gui.caixa.panels.salao.SalaoLancamentos;

public class ResumoMesasPanel extends JPanel {
	private static final long			serialVersionUID	= 1L;
	private final JLabel				lblPMV;
	private final JLabel				lblPMN;
	private final JLabel				lblPMP;
	private final JLabel				lblPMR;
	private final DinheiroLabel			lblCV;
	private final DinheiroLabel			lblCN;
	private final DinheiroLabel			lblCP;
	private final DinheiroLabel			lblCR;
	private final DinheiroLabel			lblSV;
	private final DinheiroLabel			lblSN;
	private final DinheiroLabel			lblSR;
	private final DinheiroLabel			lblRV;
	private final DinheiroLabel			lblRN;
	private final DinheiroLabel			lblRR;
	private final DinheiroLabel			lblMPV;
	private final DinheiroLabel			lblMPN;
	private final DinheiroLabel			lblMPP;
	private final DinheiroLabel			lblMPR;
	private final DinheiroLabel			lblGV;
	private final DinheiroLabel			lblGN;
	private final DinheiroLabel			lblGR;
	private final DinheiroLabel			lblPV;
	private final DinheiroLabel			lblPN;
	private final DinheiroLabel			lblPR;

	private final PlantaoLancamentos	plantao;
	private final SalaoLancamentos		salao;

	public ResumoMesasPanel(final PlantaoLancamentos lancamentosPlantao, final SalaoLancamentos lancamentosSalao) {
		plantao = lancamentosPlantao;
		salao = lancamentosSalao;
		setBorder(null);
		final GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.rowWeights = new double[] { 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, 1.0, 1.0, 1.0, 1.0 };
		gridBagLayout.columnWidths = new int[] { 0, 70, 70, 70, 70 };
		setLayout(gridBagLayout);

		final JLabel lblNull1 = new JLabel("");
		final GridBagConstraints gbc_lblNull1 = new GridBagConstraints();
		gbc_lblNull1.fill = GridBagConstraints.BOTH;
		gbc_lblNull1.gridx = 0;
		gbc_lblNull1.gridy = 0;
		add(lblNull1, gbc_lblNull1);
		lblNull1.setOpaque(true);
		lblNull1.setForeground(Color.WHITE);
		lblNull1.setBorder(new CompoundBorder(new MatteBorder(2, 2, 1, 0, new Color(0, 0, 0)), new EmptyBorder(0, 2, 0,
				0)));
		lblNull1.setBackground(Color.BLACK);

		final JLabel lblSalaoVelho = new JLabel("Sal\u00E3o Velho");
		final GridBagConstraints gbc_lblSalaoVelho = new GridBagConstraints();
		gbc_lblSalaoVelho.fill = GridBagConstraints.BOTH;
		gbc_lblSalaoVelho.gridx = 1;
		gbc_lblSalaoVelho.gridy = 0;
		add(lblSalaoVelho, gbc_lblSalaoVelho);
		lblSalaoVelho.setOpaque(true);
		lblSalaoVelho.setHorizontalAlignment(SwingConstants.TRAILING);
		lblSalaoVelho.setForeground(Color.WHITE);
		lblSalaoVelho.setFont(lblSalaoVelho.getFont().deriveFont(lblSalaoVelho.getFont().getStyle() | Font.BOLD));
		lblSalaoVelho.setBorder(new MatteBorder(3, 0, 1, 0, new Color(0, 0, 0)));
		lblSalaoVelho.setBackground(Color.BLACK);

		final JLabel lblSalaoNovo = new JLabel("Sal\u00E3o Novo");
		final GridBagConstraints gbc_lblSalaoNovo = new GridBagConstraints();
		gbc_lblSalaoNovo.fill = GridBagConstraints.BOTH;
		gbc_lblSalaoNovo.gridx = 2;
		gbc_lblSalaoNovo.gridy = 0;
		add(lblSalaoNovo, gbc_lblSalaoNovo);
		lblSalaoNovo.setOpaque(true);
		lblSalaoNovo.setHorizontalAlignment(SwingConstants.TRAILING);
		lblSalaoNovo.setForeground(Color.WHITE);
		lblSalaoNovo.setFont(lblSalaoNovo.getFont().deriveFont(lblSalaoNovo.getFont().getStyle() | Font.BOLD));
		lblSalaoNovo.setBorder(new MatteBorder(3, 0, 1, 0, new Color(0, 0, 0)));
		lblSalaoNovo.setBackground(Color.BLACK);

		final JLabel lblPlantao = new JLabel("Plant\u00E3o");
		lblPlantao.setOpaque(true);
		lblPlantao.setHorizontalAlignment(SwingConstants.TRAILING);
		lblPlantao.setForeground(Color.WHITE);
		lblPlantao.setFont(lblPlantao.getFont().deriveFont(lblPlantao.getFont().getStyle() | Font.BOLD));
		lblPlantao.setBorder(new MatteBorder(3, 0, 1, 0, new Color(0, 0, 0)));
		lblPlantao.setBackground(Color.BLACK);
		final GridBagConstraints gbc_lblPlantao = new GridBagConstraints();
		gbc_lblPlantao.fill = GridBagConstraints.BOTH;
		gbc_lblPlantao.gridx = 3;
		gbc_lblPlantao.gridy = 0;
		add(lblPlantao, gbc_lblPlantao);

		final JLabel lblResultado = new JLabel("Resultado");
		final GridBagConstraints gbc_lblResultado = new GridBagConstraints();
		gbc_lblResultado.fill = GridBagConstraints.BOTH;
		gbc_lblResultado.gridx = 4;
		gbc_lblResultado.gridy = 0;
		add(lblResultado, gbc_lblResultado);
		lblResultado.setOpaque(true);
		lblResultado.setHorizontalAlignment(SwingConstants.TRAILING);
		lblResultado.setForeground(Color.WHITE);
		lblResultado.setFont(lblResultado.getFont().deriveFont(lblResultado.getFont().getStyle() | Font.BOLD));
		lblResultado.setBorder(new CompoundBorder(new MatteBorder(2, 0, 1, 2, new Color(0, 0, 0)), new EmptyBorder(0,
				0, 0, 3)));
		lblResultado.setBackground(Color.BLACK);

		final JLabel lblPessoasMesas = new JLabel("Pessoas (Mesas):");
		lblPessoasMesas.setBorder(new CompoundBorder(new MatteBorder(0, 2, 0, 0, new Color(0, 0, 0)), new EmptyBorder(
				0, 2, 0, 0)));
		final GridBagConstraints gbc_lblPessoasMesas = new GridBagConstraints();
		gbc_lblPessoasMesas.fill = GridBagConstraints.BOTH;
		gbc_lblPessoasMesas.gridx = 0;
		gbc_lblPessoasMesas.gridy = 1;
		add(lblPessoasMesas, gbc_lblPessoasMesas);
		lblPessoasMesas.setOpaque(true);
		lblPessoasMesas.setHorizontalAlignment(SwingConstants.TRAILING);
		lblPessoasMesas.setFont(lblPessoasMesas.getFont().deriveFont(lblPessoasMesas.getFont().getStyle() | Font.BOLD));
		lblPessoasMesas.setBackground(Color.LIGHT_GRAY);

		lblPMV = new JLabel("- (-)");
		final GridBagConstraints gbc_lblPMV = new GridBagConstraints();
		gbc_lblPMV.fill = GridBagConstraints.BOTH;
		gbc_lblPMV.gridx = 1;
		gbc_lblPMV.gridy = 1;
		add(lblPMV, gbc_lblPMV);
		lblPMV.setOpaque(true);
		lblPMV.setHorizontalAlignment(SwingConstants.TRAILING);
		lblPMV.setBackground(Color.LIGHT_GRAY);

		lblPMN = new JLabel("- (-)");
		final GridBagConstraints gbc_lblPMN = new GridBagConstraints();
		gbc_lblPMN.fill = GridBagConstraints.BOTH;
		gbc_lblPMN.gridx = 2;
		gbc_lblPMN.gridy = 1;
		add(lblPMN, gbc_lblPMN);
		lblPMN.setOpaque(true);
		lblPMN.setHorizontalAlignment(SwingConstants.TRAILING);
		lblPMN.setBackground(Color.LIGHT_GRAY);

		lblPMP = new JLabel("- (-)");
		lblPMP.setOpaque(true);
		lblPMP.setHorizontalAlignment(SwingConstants.TRAILING);
		lblPMP.setBackground(Color.LIGHT_GRAY);
		final GridBagConstraints gbc_lblPMP = new GridBagConstraints();
		gbc_lblPMP.fill = GridBagConstraints.BOTH;
		gbc_lblPMP.gridx = 3;
		gbc_lblPMP.gridy = 1;
		add(lblPMP, gbc_lblPMP);

		lblPMR = new JLabel("- (-)");
		final GridBagConstraints gbc_lblPMR = new GridBagConstraints();
		gbc_lblPMR.fill = GridBagConstraints.BOTH;
		gbc_lblPMR.gridx = 4;
		gbc_lblPMR.gridy = 1;
		add(lblPMR, gbc_lblPMR);
		lblPMR.setOpaque(true);
		lblPMR.setHorizontalAlignment(SwingConstants.TRAILING);
		lblPMR.setBorder(new CompoundBorder(new MatteBorder(0, 0, 0, 2, new Color(0, 0, 0)),
				new EmptyBorder(0, 0, 0, 2)));
		lblPMR.setBackground(Color.LIGHT_GRAY);

		final JLabel lblConsumo = new JLabel("Consumo:");
		lblConsumo.setBorder(new CompoundBorder(new MatteBorder(0, 2, 0, 0, new Color(0, 0, 0)), new EmptyBorder(0, 2,
				0, 0)));
		final GridBagConstraints gbc_lblConsumo = new GridBagConstraints();
		gbc_lblConsumo.fill = GridBagConstraints.BOTH;
		gbc_lblConsumo.gridx = 0;
		gbc_lblConsumo.gridy = 2;
		add(lblConsumo, gbc_lblConsumo);
		lblConsumo.setOpaque(true);
		lblConsumo.setHorizontalAlignment(SwingConstants.TRAILING);
		lblConsumo.setFont(lblConsumo.getFont().deriveFont(lblConsumo.getFont().getStyle() | Font.BOLD));
		lblConsumo.setBackground(Color.LIGHT_GRAY);

		lblCV = new DinheiroLabel(false);
		final GridBagConstraints gbc_lblCV = new GridBagConstraints();
		gbc_lblCV.fill = GridBagConstraints.BOTH;
		gbc_lblCV.gridx = 1;
		gbc_lblCV.gridy = 2;
		add(lblCV, gbc_lblCV);
		lblCV.setOpaque(true);
		lblCV.setBackground(Color.LIGHT_GRAY);

		lblCN = new DinheiroLabel(false);
		final GridBagConstraints gbc_lblCN = new GridBagConstraints();
		gbc_lblCN.fill = GridBagConstraints.BOTH;
		gbc_lblCN.gridx = 2;
		gbc_lblCN.gridy = 2;
		add(lblCN, gbc_lblCN);
		lblCN.setOpaque(true);
		lblCN.setBackground(Color.LIGHT_GRAY);

		lblCP = new DinheiroLabel(false);
		lblCP.setOpaque(true);
		lblCP.setBackground(Color.LIGHT_GRAY);
		final GridBagConstraints gbc_lblCP = new GridBagConstraints();
		gbc_lblCP.fill = GridBagConstraints.BOTH;
		gbc_lblCP.gridx = 3;
		gbc_lblCP.gridy = 2;
		add(lblCP, gbc_lblCP);

		lblCR = new DinheiroLabel(false);
		final GridBagConstraints gbc_lblCR = new GridBagConstraints();
		gbc_lblCR.fill = GridBagConstraints.BOTH;
		gbc_lblCR.gridx = 4;
		gbc_lblCR.gridy = 2;
		add(lblCR, gbc_lblCR);
		lblCR.setOpaque(true);
		lblCR.setBorder(new CompoundBorder(new MatteBorder(0, 0, 0, 2, new Color(0, 0, 0)), new EmptyBorder(0, 0, 0, 2)));
		lblCR.setBackground(Color.LIGHT_GRAY);

		final JLabel lblServico = new JLabel("Servi\u00E7o:");
		lblServico.setBorder(new CompoundBorder(new MatteBorder(0, 2, 0, 0, new Color(0, 0, 0)), new EmptyBorder(0, 2,
				0, 0)));
		final GridBagConstraints gbc_lblServico = new GridBagConstraints();
		gbc_lblServico.fill = GridBagConstraints.BOTH;
		gbc_lblServico.gridx = 0;
		gbc_lblServico.gridy = 3;
		add(lblServico, gbc_lblServico);
		lblServico.setOpaque(true);
		lblServico.setHorizontalAlignment(SwingConstants.TRAILING);
		lblServico.setFont(lblServico.getFont().deriveFont(lblServico.getFont().getStyle() | Font.BOLD));
		lblServico.setBackground(Color.LIGHT_GRAY);

		lblSV = new DinheiroLabel(false);
		final GridBagConstraints gbc_lblSV = new GridBagConstraints();
		gbc_lblSV.fill = GridBagConstraints.BOTH;
		gbc_lblSV.gridx = 1;
		gbc_lblSV.gridy = 3;
		add(lblSV, gbc_lblSV);
		lblSV.setOpaque(true);
		lblSV.setBackground(Color.LIGHT_GRAY);

		lblSN = new DinheiroLabel(false);
		final GridBagConstraints gbc_lblSN = new GridBagConstraints();
		gbc_lblSN.fill = GridBagConstraints.BOTH;
		gbc_lblSN.gridx = 2;
		gbc_lblSN.gridy = 3;
		add(lblSN, gbc_lblSN);
		lblSN.setOpaque(true);
		lblSN.setBackground(Color.LIGHT_GRAY);

		final JLabel lblNull2 = new JLabel();
		lblNull2.setOpaque(true);
		lblNull2.setBackground(Color.LIGHT_GRAY);
		final GridBagConstraints gbc_lblNull2 = new GridBagConstraints();
		gbc_lblNull2.fill = GridBagConstraints.BOTH;
		gbc_lblNull2.gridx = 3;
		gbc_lblNull2.gridy = 3;
		add(lblNull2, gbc_lblNull2);

		lblSR = new DinheiroLabel(false);
		final GridBagConstraints gbc_lblSR = new GridBagConstraints();
		gbc_lblSR.fill = GridBagConstraints.BOTH;
		gbc_lblSR.gridx = 4;
		gbc_lblSR.gridy = 3;
		add(lblSR, gbc_lblSR);
		lblSR.setOpaque(true);
		lblSR.setBorder(new CompoundBorder(new MatteBorder(0, 0, 0, 2, new Color(0, 0, 0)), new EmptyBorder(0, 0, 0, 2)));
		lblSR.setBackground(Color.LIGHT_GRAY);

		final JLabel lblMP = new JLabel("M\u00E9dia / Pessoa:");
		lblMP.setBorder(new CompoundBorder(new MatteBorder(0, 2, 0, 0, new Color(0, 0, 0)), new EmptyBorder(0, 2, 0, 0)));
		final GridBagConstraints gbc_lblMP = new GridBagConstraints();
		gbc_lblMP.fill = GridBagConstraints.BOTH;
		gbc_lblMP.gridx = 0;
		gbc_lblMP.gridy = 4;
		add(lblMP, gbc_lblMP);
		lblMP.setOpaque(true);
		lblMP.setHorizontalAlignment(SwingConstants.TRAILING);
		lblMP.setFont(lblMP.getFont().deriveFont(lblMP.getFont().getStyle() | Font.BOLD));
		lblMP.setBackground(Color.LIGHT_GRAY);

		lblMPV = new DinheiroLabel(false);
		final GridBagConstraints gbc_lblMPV = new GridBagConstraints();
		gbc_lblMPV.fill = GridBagConstraints.BOTH;
		gbc_lblMPV.gridx = 1;
		gbc_lblMPV.gridy = 4;
		add(lblMPV, gbc_lblMPV);
		lblMPV.setOpaque(true);
		lblMPV.setBackground(Color.LIGHT_GRAY);

		lblMPN = new DinheiroLabel(false);
		lblMPN.setOpaque(true);
		lblMPN.setBackground(Color.LIGHT_GRAY);
		final GridBagConstraints gbc_lblMPN = new GridBagConstraints();
		gbc_lblMPN.fill = GridBagConstraints.BOTH;
		gbc_lblMPN.gridx = 2;
		gbc_lblMPN.gridy = 4;
		add(lblMPN, gbc_lblMPN);

		lblMPP = new DinheiroLabel(false);
		final GridBagConstraints gbc_lblMPP = new GridBagConstraints();
		gbc_lblMPP.fill = GridBagConstraints.BOTH;
		gbc_lblMPP.gridx = 3;
		gbc_lblMPP.gridy = 4;
		add(lblMPP, gbc_lblMPP);
		lblMPP.setOpaque(true);
		lblMPP.setBackground(Color.LIGHT_GRAY);

		lblMPR = new DinheiroLabel(false);
		final GridBagConstraints gbc_lblMPR = new GridBagConstraints();
		gbc_lblMPR.fill = GridBagConstraints.BOTH;
		gbc_lblMPR.gridx = 4;
		gbc_lblMPR.gridy = 4;
		add(lblMPR, gbc_lblMPR);
		lblMPR.setOpaque(true);
		lblMPR.setBorder(new CompoundBorder(new MatteBorder(0, 0, 0, 2, new Color(0, 0, 0)),
				new EmptyBorder(0, 0, 0, 2)));
		lblMPR.setBackground(Color.LIGHT_GRAY);

		final JLabel lblRepique = new JLabel("Repique:");
		final GridBagConstraints gbc_lblRepique = new GridBagConstraints();
		gbc_lblRepique.fill = GridBagConstraints.BOTH;
		gbc_lblRepique.gridx = 0;
		gbc_lblRepique.gridy = 5;
		add(lblRepique, gbc_lblRepique);
		lblRepique.setOpaque(true);
		lblRepique.setHorizontalAlignment(SwingConstants.TRAILING);
		lblRepique.setFont(lblRepique.getFont().deriveFont(lblRepique.getFont().getStyle() | Font.BOLD));
		lblRepique.setBorder(new CompoundBorder(new MatteBorder(0, 2, 1, 0, new Color(0, 0, 0)), new EmptyBorder(0, 2,
				0, 0)));
		lblRepique.setBackground(Color.LIGHT_GRAY);

		lblRV = new DinheiroLabel(false);
		final GridBagConstraints gbc_lblRV = new GridBagConstraints();
		gbc_lblRV.fill = GridBagConstraints.BOTH;
		gbc_lblRV.gridx = 1;
		gbc_lblRV.gridy = 5;
		add(lblRV, gbc_lblRV);
		lblRV.setOpaque(true);
		lblRV.setBorder(new MatteBorder(0, 0, 1, 0, new Color(0, 0, 0)));
		lblRV.setBackground(Color.LIGHT_GRAY);

		lblRN = new DinheiroLabel(false);
		lblRN.setOpaque(true);
		lblRN.setBorder(new MatteBorder(0, 0, 1, 0, new Color(0, 0, 0)));
		lblRN.setBackground(Color.LIGHT_GRAY);
		final GridBagConstraints gbc_lblRN = new GridBagConstraints();
		gbc_lblRN.fill = GridBagConstraints.BOTH;
		gbc_lblRN.gridx = 2;
		gbc_lblRN.gridy = 5;
		add(lblRN, gbc_lblRN);

		final JLabel lblNull3 = new JLabel();
		final GridBagConstraints gbc_lblNull3 = new GridBagConstraints();
		gbc_lblNull3.fill = GridBagConstraints.BOTH;
		gbc_lblNull3.gridx = 3;
		gbc_lblNull3.gridy = 5;
		add(lblNull3, gbc_lblNull3);
		lblNull3.setOpaque(true);
		lblNull3.setBorder(new MatteBorder(0, 0, 1, 0, new Color(0, 0, 0)));
		lblNull3.setBackground(Color.LIGHT_GRAY);

		lblRR = new DinheiroLabel(false);
		final GridBagConstraints gbc_lblRR = new GridBagConstraints();
		gbc_lblRR.fill = GridBagConstraints.BOTH;
		gbc_lblRR.gridx = 4;
		gbc_lblRR.gridy = 5;
		add(lblRR, gbc_lblRR);
		lblRR.setOpaque(true);
		lblRR.setBorder(new CompoundBorder(new MatteBorder(0, 0, 1, 2, new Color(0, 0, 0)), new EmptyBorder(0, 0, 0, 3)));
		lblRR.setBackground(Color.LIGHT_GRAY);

		final JLabel lblGarcons = new JLabel("Gar\u00E7ons:");
		lblGarcons.setBorder(new CompoundBorder(new MatteBorder(0, 2, 0, 0, new Color(0, 0, 0)), new EmptyBorder(0, 2,
				0, 0)));
		final GridBagConstraints gbc_lblGarcons = new GridBagConstraints();
		gbc_lblGarcons.fill = GridBagConstraints.BOTH;
		gbc_lblGarcons.gridx = 0;
		gbc_lblGarcons.gridy = 6;
		add(lblGarcons, gbc_lblGarcons);
		lblGarcons.setOpaque(true);
		lblGarcons.setHorizontalAlignment(SwingConstants.TRAILING);
		lblGarcons.setFont(lblGarcons.getFont().deriveFont(lblGarcons.getFont().getStyle() | Font.BOLD));
		lblGarcons.setBackground(Color.WHITE);

		lblGV = new DinheiroLabel(false);
		final GridBagConstraints gbc_lblGV = new GridBagConstraints();
		gbc_lblGV.fill = GridBagConstraints.BOTH;
		gbc_lblGV.gridx = 1;
		gbc_lblGV.gridy = 6;
		add(lblGV, gbc_lblGV);
		lblGV.setOpaque(true);
		lblGV.setBackground(Color.WHITE);

		lblGN = new DinheiroLabel(false);
		final GridBagConstraints gbc_lblGN = new GridBagConstraints();
		gbc_lblGN.fill = GridBagConstraints.BOTH;
		gbc_lblGN.gridx = 2;
		gbc_lblGN.gridy = 6;
		add(lblGN, gbc_lblGN);
		lblGN.setOpaque(true);
		lblGN.setBackground(Color.WHITE);

		final JLabel lblNull4 = new JLabel();
		lblNull4.setOpaque(true);
		lblNull4.setBackground(Color.WHITE);
		final GridBagConstraints gbc_lblNull4 = new GridBagConstraints();
		gbc_lblNull4.fill = GridBagConstraints.BOTH;
		gbc_lblNull4.gridx = 3;
		gbc_lblNull4.gridy = 6;
		add(lblNull4, gbc_lblNull4);

		lblGR = new DinheiroLabel(false);
		final GridBagConstraints gbc_lblGR = new GridBagConstraints();
		gbc_lblGR.fill = GridBagConstraints.BOTH;
		gbc_lblGR.gridx = 4;
		gbc_lblGR.gridy = 6;
		add(lblGR, gbc_lblGR);
		lblGR.setOpaque(true);
		lblGR.setBorder(new CompoundBorder(new MatteBorder(0, 0, 0, 2, new Color(0, 0, 0)), new EmptyBorder(0, 0, 0, 2)));
		lblGR.setBackground(Color.WHITE);

		final JLabel lblPercentual = new JLabel("20%:");
		lblPercentual.setBorder(new CompoundBorder(new MatteBorder(0, 2, 2, 0, new Color(0, 0, 0)), new EmptyBorder(0,
				2, 0, 0)));
		final GridBagConstraints gbc_lblPercentual = new GridBagConstraints();
		gbc_lblPercentual.fill = GridBagConstraints.BOTH;
		gbc_lblPercentual.gridx = 0;
		gbc_lblPercentual.gridy = 7;
		add(lblPercentual, gbc_lblPercentual);
		lblPercentual.setOpaque(true);
		lblPercentual.setHorizontalAlignment(SwingConstants.TRAILING);
		lblPercentual.setFont(lblPercentual.getFont().deriveFont(lblPercentual.getFont().getStyle() | Font.BOLD));
		lblPercentual.setBackground(Color.WHITE);

		lblPV = new DinheiroLabel(false);
		lblPV.setBorder(new MatteBorder(0, 0, 2, 0, new Color(0, 0, 0)));
		final GridBagConstraints gbc_lblPV = new GridBagConstraints();
		gbc_lblPV.fill = GridBagConstraints.BOTH;
		gbc_lblPV.gridx = 1;
		gbc_lblPV.gridy = 7;
		add(lblPV, gbc_lblPV);
		lblPV.setOpaque(true);
		lblPV.setBackground(Color.WHITE);

		lblPN = new DinheiroLabel(false);
		lblPN.setBorder(new MatteBorder(0, 0, 2, 0, new Color(0, 0, 0)));
		final GridBagConstraints gbc_lblPN = new GridBagConstraints();
		gbc_lblPN.fill = GridBagConstraints.BOTH;
		gbc_lblPN.gridx = 2;
		gbc_lblPN.gridy = 7;
		add(lblPN, gbc_lblPN);
		lblPN.setOpaque(true);
		lblPN.setBackground(Color.WHITE);

		final JLabel lblNull5 = new JLabel();
		final GridBagConstraints gbc_lblNull5 = new GridBagConstraints();
		gbc_lblNull5.fill = GridBagConstraints.BOTH;
		gbc_lblNull5.gridx = 3;
		gbc_lblNull5.gridy = 7;
		add(lblNull5, gbc_lblNull5);
		lblNull5.setOpaque(true);
		lblNull5.setBorder(new MatteBorder(0, 0, 2, 0, new Color(0, 0, 0)));
		lblNull5.setBackground(Color.WHITE);

		lblPR = new DinheiroLabel(false);
		lblPR.setOpaque(true);
		lblPR.setBorder(new CompoundBorder(new MatteBorder(0, 0, 2, 2, new Color(0, 0, 0)), new EmptyBorder(0, 0, 0, 2)));
		lblPR.setBackground(Color.WHITE);
		final GridBagConstraints gbc_lblPR = new GridBagConstraints();
		gbc_lblPR.fill = GridBagConstraints.BOTH;
		gbc_lblPR.gridx = 4;
		gbc_lblPR.gridy = 7;
		add(lblPR, gbc_lblPR);
	}

	public void atualizarSalao() {
		final ResumoMesas rm = new ResumoMesas();

		resetResumo();

		if (salao.addResumo(rm) && plantao.addResumo(rm)) {
			lblPMV.setText(rm.getPessoasMesas(ResumoMesas.SALAO_VELHO));
			lblPMN.setText(rm.getPessoasMesas(ResumoMesas.SALAO_NOVO));
			lblPMP.setText(rm.getPessoasMesas(ResumoMesas.PLANTAO));
			lblPMR.setText(rm.getPessoasMesas(ResumoMesas.RESULTADO));

			lblCV.setDinheiro(rm.getConsumo(ResumoMesas.SALAO_VELHO));
			lblCN.setDinheiro(rm.getConsumo(ResumoMesas.SALAO_NOVO));
			lblCP.setDinheiro(rm.getConsumo(ResumoMesas.PLANTAO));
			lblCR.setDinheiro(rm.getConsumo(ResumoMesas.RESULTADO));

			lblSV.setDinheiro(rm.getServico(ResumoMesas.SALAO_VELHO));
			lblSN.setDinheiro(rm.getServico(ResumoMesas.SALAO_NOVO));
			lblSR.setDinheiro(rm.getServico(ResumoMesas.RESULTADO));

			lblMPV.setDinheiro(rm.getMedia(ResumoMesas.SALAO_VELHO));
			lblMPN.setDinheiro(rm.getMedia(ResumoMesas.SALAO_NOVO));
			lblMPP.setDinheiro(rm.getMedia(ResumoMesas.PLANTAO));
			lblMPR.setDinheiro(rm.getMedia(ResumoMesas.RESULTADO));

			lblRV.setDinheiro(rm.getRepique(ResumoMesas.SALAO_VELHO));
			lblRN.setDinheiro(rm.getRepique(ResumoMesas.SALAO_NOVO));
			lblRR.setDinheiro(rm.getRepique(ResumoMesas.RESULTADO));

			lblGV.setDinheiro(rm.getGarcons(ResumoMesas.SALAO_VELHO));
			lblGN.setDinheiro(rm.getGarcons(ResumoMesas.SALAO_NOVO));
			lblGR.setDinheiro(rm.getGarcons(ResumoMesas.RESULTADO));

			lblPV.setDinheiro(rm.get20Percento(ResumoMesas.SALAO_VELHO));
			lblPN.setDinheiro(rm.get20Percento(ResumoMesas.SALAO_NOVO));
			lblPR.setDinheiro(rm.get20Percento(ResumoMesas.RESULTADO));
		}
	}

	private void resetResumo() {
		lblPMV.setText("- (-)");
		lblPMN.setText("- (-)");
		lblPMP.setText("- (-)");
		lblPMR.setText("- (-)");
		lblCV.showErro();
		lblCN.showErro();
		lblCP.showErro();
		lblCR.showErro();
		lblSV.showErro();
		lblSN.showErro();
		lblSR.showErro();
		lblCV.showErro();
		lblCN.showErro();
		lblCP.showErro();
		lblCR.showErro();
		lblMPV.showErro();
		lblMPN.showErro();
		lblMPP.showErro();
		lblMPR.showErro();
		lblRV.showErro();
		lblRN.showErro();
		lblRR.showErro();
		lblGV.showErro();
		lblGN.showErro();
		lblGR.showErro();
		lblPV.showErro();
		lblPN.showErro();
		lblPR.showErro();
	}

}
