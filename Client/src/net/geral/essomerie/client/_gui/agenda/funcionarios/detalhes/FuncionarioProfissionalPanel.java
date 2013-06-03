package net.geral.essomerie.client._gui.agenda.funcionarios.detalhes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import net.geral.essomerie._shared.funcionario.FuncionarioProfissional;
import net.geral.essomerie.client.core.Client;
import net.geral.essomerie.client.gui.shared.textfield.MoneyTextField;
import net.geral.essomerie.shared.money.Money;

import org.joda.time.LocalDateTime;

public class FuncionarioProfissionalPanel extends JPanel {
  private static final long       serialVersionUID = 1L;
  private final JTextField        txtAdmissao;
  private final JTextField        txtFuncao;
  private final MoneyTextField    txtExtra;
  private final JTextField        txtExtraObservacoes;
  private final JTextField        txtPagamentoAgencia;
  private final JTextField        txtContaConta;
  private final JComboBox<String> cbContaBanco;
  private final JComboBox<String> cbCargo;
  private final JLabel            lblId;
  private final JLabel            lblCodigo;
  private final JLabel            lblCadastradoEm;
  private final JLabel            lblAlteradoEm;
  private final JLabel            lblAlteradoPor;
  private final MoneyTextField    txtSalario;
  private final JLabel            lblTempoDeCasa;
  private final JTextArea         txtResponsabilidades;

  public FuncionarioProfissionalPanel() {
    setBorder(new EmptyBorder(2, 2, 2, 2));
    final SpringLayout springLayout = new SpringLayout();
    setLayout(springLayout);

    final JLabel lblTId = new JLabel("ID#:");
    springLayout.putConstraint(SpringLayout.NORTH, lblTId, 0,
        SpringLayout.NORTH, this);
    springLayout.putConstraint(SpringLayout.WEST, lblTId, 0, SpringLayout.WEST,
        this);
    springLayout.putConstraint(SpringLayout.EAST, lblTId, 50,
        SpringLayout.WEST, this);
    lblTId.setFont(lblTId.getFont().deriveFont(
        lblTId.getFont().getStyle() | Font.BOLD));
    add(lblTId);

    lblId = new JLabel("-");
    springLayout.putConstraint(SpringLayout.NORTH, lblId, 2,
        SpringLayout.SOUTH, lblTId);
    springLayout.putConstraint(SpringLayout.WEST, lblId, 0, SpringLayout.WEST,
        lblTId);
    springLayout.putConstraint(SpringLayout.EAST, lblId, 0, SpringLayout.EAST,
        lblTId);
    add(lblId);

    final JLabel lblTCodigo = new JLabel("C\u00F3digo:");
    springLayout.putConstraint(SpringLayout.WEST, lblTCodigo, 6,
        SpringLayout.EAST, lblTId);
    springLayout.putConstraint(SpringLayout.EAST, lblTCodigo, 76,
        SpringLayout.EAST, lblId);
    lblTCodigo.setFont(lblTCodigo.getFont().deriveFont(
        lblTCodigo.getFont().getStyle() | Font.BOLD));
    add(lblTCodigo);

    lblCodigo = new JLabel("-");
    springLayout.putConstraint(SpringLayout.NORTH, lblCodigo, 2,
        SpringLayout.SOUTH, lblTCodigo);
    springLayout.putConstraint(SpringLayout.WEST, lblCodigo, 0,
        SpringLayout.WEST, lblTCodigo);
    springLayout.putConstraint(SpringLayout.EAST, lblCodigo, 0,
        SpringLayout.EAST, lblTCodigo);
    add(lblCodigo);

    final JLabel lblTCadastradoEm = new JLabel("Cadastrado Em:");
    springLayout.putConstraint(SpringLayout.NORTH, lblTCadastradoEm, 0,
        SpringLayout.NORTH, this);
    springLayout.putConstraint(SpringLayout.WEST, lblTCadastradoEm, 6,
        SpringLayout.EAST, lblTCodigo);
    springLayout.putConstraint(SpringLayout.EAST, lblTCadastradoEm, 126,
        SpringLayout.EAST, lblTCodigo);
    lblTCadastradoEm.setFont(lblTCadastradoEm.getFont().deriveFont(
        lblTCadastradoEm.getFont().getStyle() | Font.BOLD));
    add(lblTCadastradoEm);

    lblCadastradoEm = new JLabel("-");
    springLayout.putConstraint(SpringLayout.NORTH, lblCadastradoEm, 2,
        SpringLayout.SOUTH, lblTCadastradoEm);
    springLayout.putConstraint(SpringLayout.WEST, lblCadastradoEm, 0,
        SpringLayout.WEST, lblTCadastradoEm);
    springLayout.putConstraint(SpringLayout.EAST, lblCadastradoEm, 0,
        SpringLayout.EAST, lblTCadastradoEm);
    add(lblCadastradoEm);

    final JLabel lblTAlteradoEm = new JLabel("Alterado Em:");
    springLayout.putConstraint(SpringLayout.NORTH, lblTAlteradoEm, 0,
        SpringLayout.NORTH, this);
    springLayout.putConstraint(SpringLayout.WEST, lblTAlteradoEm, 6,
        SpringLayout.EAST, lblTCadastradoEm);
    springLayout.putConstraint(SpringLayout.EAST, lblTAlteradoEm, 126,
        SpringLayout.EAST, lblTCadastradoEm);
    lblTAlteradoEm.setFont(lblTAlteradoEm.getFont().deriveFont(
        lblTAlteradoEm.getFont().getStyle() | Font.BOLD));
    add(lblTAlteradoEm);

    lblAlteradoEm = new JLabel("-");
    springLayout.putConstraint(SpringLayout.NORTH, lblAlteradoEm, 2,
        SpringLayout.SOUTH, lblTCadastradoEm);
    springLayout.putConstraint(SpringLayout.WEST, lblAlteradoEm, 0,
        SpringLayout.WEST, lblTAlteradoEm);
    springLayout.putConstraint(SpringLayout.EAST, lblAlteradoEm, 0,
        SpringLayout.EAST, lblTAlteradoEm);
    add(lblAlteradoEm);

    final JLabel lblTAlteradoPor = new JLabel("Por:");
    springLayout.putConstraint(SpringLayout.NORTH, lblTAlteradoPor, 0,
        SpringLayout.NORTH, this);
    springLayout.putConstraint(SpringLayout.WEST, lblTAlteradoPor, 6,
        SpringLayout.EAST, lblAlteradoEm);
    lblTAlteradoPor.setFont(lblTAlteradoPor.getFont().deriveFont(
        lblTAlteradoPor.getFont().getStyle() | Font.BOLD));
    add(lblTAlteradoPor);

    lblAlteradoPor = new JLabel("-");
    springLayout.putConstraint(SpringLayout.NORTH, lblAlteradoPor, 2,
        SpringLayout.SOUTH, lblTAlteradoPor);
    springLayout.putConstraint(SpringLayout.WEST, lblAlteradoPor, 0,
        SpringLayout.WEST, lblTAlteradoPor);
    springLayout.putConstraint(SpringLayout.EAST, lblAlteradoPor, 0,
        SpringLayout.EAST, lblTAlteradoPor);
    add(lblAlteradoPor);

    final JLabel lblTTempoDeCasa = new JLabel("Tempo de Casa:");
    springLayout.putConstraint(SpringLayout.EAST, lblTAlteradoPor, -6,
        SpringLayout.WEST, lblTTempoDeCasa);
    lblTTempoDeCasa.setFont(lblTTempoDeCasa.getFont().deriveFont(
        lblTTempoDeCasa.getFont().getStyle() | Font.BOLD));
    springLayout.putConstraint(SpringLayout.NORTH, lblTTempoDeCasa, 0,
        SpringLayout.NORTH, this);
    springLayout.putConstraint(SpringLayout.WEST, lblTTempoDeCasa, -100,
        SpringLayout.EAST, this);
    springLayout.putConstraint(SpringLayout.EAST, lblTTempoDeCasa, 0,
        SpringLayout.EAST, this);
    add(lblTTempoDeCasa);

    lblTempoDeCasa = new JLabel("-");
    springLayout.putConstraint(SpringLayout.NORTH, lblTempoDeCasa, 2,
        SpringLayout.SOUTH, lblTTempoDeCasa);
    springLayout.putConstraint(SpringLayout.WEST, lblTempoDeCasa, 0,
        SpringLayout.WEST, lblTTempoDeCasa);
    springLayout.putConstraint(SpringLayout.EAST, lblTempoDeCasa, 0,
        SpringLayout.EAST, lblTTempoDeCasa);
    add(lblTempoDeCasa);

    final JLabel lblAdmissao = new JLabel("Admiss\u00E3o:");
    lblAdmissao.setFont(lblAdmissao.getFont().deriveFont(
        lblAdmissao.getFont().getStyle() | Font.BOLD));
    springLayout.putConstraint(SpringLayout.NORTH, lblAdmissao, 6,
        SpringLayout.SOUTH, lblId);
    springLayout.putConstraint(SpringLayout.EAST, lblAdmissao, 80,
        SpringLayout.WEST, this);
    springLayout.putConstraint(SpringLayout.WEST, lblAdmissao, 0,
        SpringLayout.WEST, this);
    add(lblAdmissao);

    txtAdmissao = new JTextField();
    springLayout.putConstraint(SpringLayout.NORTH, txtAdmissao, 2,
        SpringLayout.SOUTH, lblAdmissao);
    springLayout.putConstraint(SpringLayout.WEST, txtAdmissao, 0,
        SpringLayout.WEST, lblAdmissao);
    springLayout.putConstraint(SpringLayout.EAST, txtAdmissao, 0,
        SpringLayout.EAST, lblAdmissao);
    txtAdmissao.setText("12/34/5678");
    add(txtAdmissao);

    final JLabel lblCargo = new JLabel("Cargo:");
    lblCargo.setFont(lblCargo.getFont().deriveFont(
        lblCargo.getFont().getStyle() | Font.BOLD));
    springLayout.putConstraint(SpringLayout.NORTH, lblCargo, 0,
        SpringLayout.NORTH, lblAdmissao);
    springLayout.putConstraint(SpringLayout.WEST, lblCargo, 6,
        SpringLayout.EAST, lblAdmissao);
    springLayout.putConstraint(SpringLayout.EAST, lblCargo, 280,
        SpringLayout.WEST, lblAdmissao);
    add(lblCargo);

    cbCargo = new JComboBox<String>();
    springLayout.putConstraint(SpringLayout.NORTH, cbCargo, 2,
        SpringLayout.SOUTH, lblCargo);
    springLayout.putConstraint(SpringLayout.WEST, cbCargo, 0,
        SpringLayout.WEST, lblCargo);
    springLayout.putConstraint(SpringLayout.EAST, cbCargo, 0,
        SpringLayout.EAST, lblCargo);
    cbCargo.setEditable(true);
    add(cbCargo);

    final JLabel lblFuncao = new JLabel("Fun\u00E7\u00E3o:");
    lblFuncao.setFont(lblFuncao.getFont().deriveFont(
        lblFuncao.getFont().getStyle() | Font.BOLD));
    springLayout.putConstraint(SpringLayout.NORTH, lblFuncao, 0,
        SpringLayout.NORTH, lblAdmissao);
    springLayout.putConstraint(SpringLayout.WEST, lblFuncao, 6,
        SpringLayout.EAST, lblCargo);
    springLayout.putConstraint(SpringLayout.EAST, lblFuncao, 156,
        SpringLayout.EAST, lblCargo);
    add(lblFuncao);

    txtFuncao = new JTextField();
    txtFuncao.setText("Sess\u00E3o de Cabritos");
    springLayout.putConstraint(SpringLayout.NORTH, txtFuncao, 2,
        SpringLayout.SOUTH, lblFuncao);
    springLayout.putConstraint(SpringLayout.WEST, txtFuncao, 0,
        SpringLayout.WEST, lblFuncao);
    springLayout.putConstraint(SpringLayout.EAST, txtFuncao, 0,
        SpringLayout.EAST, lblFuncao);
    add(txtFuncao);

    final JLabel lblSalario = new JLabel("Sal\u00E1rio:");
    lblSalario.setFont(lblSalario.getFont().deriveFont(
        lblSalario.getFont().getStyle() | Font.BOLD));
    springLayout.putConstraint(SpringLayout.WEST, lblSalario, 6,
        SpringLayout.EAST, lblFuncao);
    springLayout.putConstraint(SpringLayout.EAST, lblSalario, 66,
        SpringLayout.EAST, lblFuncao);
    springLayout.putConstraint(SpringLayout.SOUTH, lblSalario, 0,
        SpringLayout.SOUTH, lblAdmissao);
    add(lblSalario);

    txtSalario = new MoneyTextField(false, true, true, false);
    springLayout.putConstraint(SpringLayout.NORTH, txtSalario, 2,
        SpringLayout.SOUTH, lblSalario);
    springLayout.putConstraint(SpringLayout.WEST, txtSalario, 0,
        SpringLayout.WEST, lblSalario);
    springLayout.putConstraint(SpringLayout.EAST, txtSalario, 0,
        SpringLayout.EAST, lblSalario);
    add(txtSalario);

    final JLabel lblExtra = new JLabel("Extra:");
    lblExtra.setFont(lblExtra.getFont().deriveFont(
        lblExtra.getFont().getStyle() | Font.BOLD));
    springLayout.putConstraint(SpringLayout.WEST, lblExtra, 6,
        SpringLayout.EAST, lblSalario);
    springLayout.putConstraint(SpringLayout.EAST, lblExtra, 66,
        SpringLayout.EAST, lblSalario);
    springLayout.putConstraint(SpringLayout.NORTH, lblExtra, 0,
        SpringLayout.NORTH, lblAdmissao);
    add(lblExtra);

    txtExtra = new MoneyTextField(false, true, true, false);
    springLayout.putConstraint(SpringLayout.NORTH, txtExtra, 2,
        SpringLayout.SOUTH, lblExtra);
    springLayout.putConstraint(SpringLayout.WEST, txtExtra, 0,
        SpringLayout.WEST, lblExtra);
    springLayout.putConstraint(SpringLayout.EAST, txtExtra, 0,
        SpringLayout.EAST, lblExtra);
    add(txtExtra);

    final JLabel lblExtraObservacoes = new JLabel(
        "Observa\u00E7\u00F5es sobre Extras:");
    lblExtraObservacoes.setFont(lblExtraObservacoes.getFont().deriveFont(
        lblExtraObservacoes.getFont().getStyle() | Font.BOLD));
    springLayout.putConstraint(SpringLayout.NORTH, lblExtraObservacoes, 0,
        SpringLayout.NORTH, lblExtra);
    springLayout.putConstraint(SpringLayout.WEST, lblExtraObservacoes, 6,
        SpringLayout.EAST, lblExtra);
    springLayout.putConstraint(SpringLayout.EAST, lblExtraObservacoes, 0,
        SpringLayout.EAST, this);
    add(lblExtraObservacoes);

    txtExtraObservacoes = new JTextField();
    txtExtraObservacoes.setText("S\u00E1bados e Domingos (copa).");
    springLayout.putConstraint(SpringLayout.NORTH, txtExtraObservacoes, 2,
        SpringLayout.SOUTH, lblExtraObservacoes);
    springLayout.putConstraint(SpringLayout.WEST, txtExtraObservacoes, 0,
        SpringLayout.WEST, lblExtraObservacoes);
    springLayout.putConstraint(SpringLayout.EAST, txtExtraObservacoes, 0,
        SpringLayout.EAST, lblExtraObservacoes);
    add(txtExtraObservacoes);

    final JPanel panelContaSalario = new JPanel();
    springLayout.putConstraint(SpringLayout.WEST, panelContaSalario, 0,
        SpringLayout.WEST, this);
    add(panelContaSalario);
    final SpringLayout sl_panelContaSalario = new SpringLayout();
    panelContaSalario.setLayout(sl_panelContaSalario);

    final JPanel panelHorario = new JPanel();
    springLayout.putConstraint(SpringLayout.NORTH, panelContaSalario, 0,
        SpringLayout.NORTH, panelHorario);
    springLayout.putConstraint(SpringLayout.EAST, panelContaSalario, -6,
        SpringLayout.WEST, panelHorario);
    springLayout.putConstraint(SpringLayout.NORTH, panelHorario, 6,
        SpringLayout.SOUTH, txtFuncao);
    springLayout.putConstraint(SpringLayout.SOUTH, panelContaSalario, 0,
        SpringLayout.SOUTH, panelHorario);
    springLayout.putConstraint(SpringLayout.EAST, panelHorario, 0,
        SpringLayout.EAST, this);

    final JLabel lblContaBanco = new JLabel("Banco:");
    sl_panelContaSalario.putConstraint(SpringLayout.NORTH, lblContaBanco, 0,
        SpringLayout.NORTH, panelContaSalario);
    lblContaBanco.setFont(lblContaBanco.getFont().deriveFont(
        lblContaBanco.getFont().getStyle() | Font.BOLD));
    sl_panelContaSalario.putConstraint(SpringLayout.WEST, lblContaBanco, 2,
        SpringLayout.WEST, panelContaSalario);
    panelContaSalario.add(lblContaBanco);

    cbContaBanco = new JComboBox<>();
    sl_panelContaSalario.putConstraint(SpringLayout.NORTH, cbContaBanco, 2,
        SpringLayout.SOUTH, lblContaBanco);
    sl_panelContaSalario.putConstraint(SpringLayout.WEST, cbContaBanco, 0,
        SpringLayout.WEST, lblContaBanco);
    sl_panelContaSalario.putConstraint(SpringLayout.EAST, cbContaBanco, 0,
        SpringLayout.EAST, lblContaBanco);
    cbContaBanco.setEditable(true);
    panelContaSalario.add(cbContaBanco);

    final JLabel lblContaAgencia = new JLabel("Ag\u00EAncia:");
    lblContaAgencia.setFont(lblContaAgencia.getFont().deriveFont(
        lblContaAgencia.getFont().getStyle() | Font.BOLD));
    sl_panelContaSalario.putConstraint(SpringLayout.NORTH, lblContaAgencia, 0,
        SpringLayout.NORTH, lblContaBanco);
    sl_panelContaSalario.putConstraint(SpringLayout.EAST, lblContaBanco, -6,
        SpringLayout.WEST, lblContaAgencia);
    panelContaSalario.add(lblContaAgencia);

    txtPagamentoAgencia = new JTextField();
    sl_panelContaSalario.putConstraint(SpringLayout.NORTH, txtPagamentoAgencia,
        2, SpringLayout.SOUTH, lblContaAgencia);
    sl_panelContaSalario.putConstraint(SpringLayout.WEST, txtPagamentoAgencia,
        0, SpringLayout.WEST, lblContaAgencia);
    sl_panelContaSalario.putConstraint(SpringLayout.EAST, txtPagamentoAgencia,
        0, SpringLayout.EAST, lblContaAgencia);
    txtPagamentoAgencia.setHorizontalAlignment(SwingConstants.TRAILING);
    txtPagamentoAgencia.setText("1234-5");
    panelContaSalario.add(txtPagamentoAgencia);

    final JLabel lblContaConta = new JLabel("Conta:");
    lblContaConta.setFont(lblContaConta.getFont().deriveFont(
        lblContaConta.getFont().getStyle() | Font.BOLD));
    sl_panelContaSalario.putConstraint(SpringLayout.WEST, lblContaAgencia, -71,
        SpringLayout.WEST, lblContaConta);
    sl_panelContaSalario.putConstraint(SpringLayout.NORTH, lblContaConta, 0,
        SpringLayout.NORTH, lblContaBanco);
    sl_panelContaSalario.putConstraint(SpringLayout.WEST, lblContaConta, -82,
        SpringLayout.EAST, panelContaSalario);
    sl_panelContaSalario.putConstraint(SpringLayout.EAST, lblContaAgencia, -6,
        SpringLayout.WEST, lblContaConta);
    sl_panelContaSalario.putConstraint(SpringLayout.EAST, lblContaConta, -2,
        SpringLayout.EAST, panelContaSalario);
    panelContaSalario.add(lblContaConta);

    txtContaConta = new JTextField();
    sl_panelContaSalario.putConstraint(SpringLayout.NORTH, txtContaConta, 2,
        SpringLayout.SOUTH, lblContaConta);
    sl_panelContaSalario.putConstraint(SpringLayout.WEST, txtContaConta, 0,
        SpringLayout.WEST, lblContaConta);
    sl_panelContaSalario.putConstraint(SpringLayout.EAST, txtContaConta, 0,
        SpringLayout.EAST, lblContaConta);
    txtContaConta.setHorizontalAlignment(SwingConstants.TRAILING);
    txtContaConta.setText("12.345-6");
    panelContaSalario.add(txtContaConta);
    add(panelHorario);
    panelHorario.setLayout(new BorderLayout(0, 0));

    final JPanel panelHorarioTable = new JPanel();
    panelHorarioTable.setBackground(Color.WHITE);
    panelHorarioTable.setBorder(new LineBorder(new Color(0, 0, 0), 2));
    panelHorario.add(panelHorarioTable);
    panelHorarioTable.setLayout(new GridLayout(0, 8, 0, 0));

    final JLabel lblHorarioBlank = new JLabel("");
    lblHorarioBlank.setForeground(Color.WHITE);
    lblHorarioBlank.setOpaque(true);
    lblHorarioBlank.setBackground(Color.BLACK);
    panelHorarioTable.add(lblHorarioBlank);
    lblHorarioBlank.setHorizontalAlignment(SwingConstants.CENTER);

    final JLabel lblHorarioDomingo = new JLabel("Dom");
    lblHorarioDomingo.setForeground(Color.WHITE);
    lblHorarioDomingo.setOpaque(true);
    lblHorarioDomingo.setBackground(Color.BLACK);
    panelHorarioTable.add(lblHorarioDomingo);
    lblHorarioDomingo.setHorizontalAlignment(SwingConstants.CENTER);

    final JLabel lblHorarioSegunda = new JLabel("Seg");
    lblHorarioSegunda.setForeground(Color.WHITE);
    lblHorarioSegunda.setOpaque(true);
    lblHorarioSegunda.setBackground(Color.BLACK);
    panelHorarioTable.add(lblHorarioSegunda);
    lblHorarioSegunda.setHorizontalAlignment(SwingConstants.CENTER);

    final JLabel lblHorarioTerca = new JLabel("Ter");
    lblHorarioTerca.setForeground(Color.WHITE);
    lblHorarioTerca.setOpaque(true);
    lblHorarioTerca.setBackground(Color.BLACK);
    panelHorarioTable.add(lblHorarioTerca);
    lblHorarioTerca.setHorizontalAlignment(SwingConstants.CENTER);

    final JLabel lblHorarioQuarta = new JLabel("Qua");
    lblHorarioQuarta.setForeground(Color.WHITE);
    lblHorarioQuarta.setOpaque(true);
    lblHorarioQuarta.setBackground(Color.BLACK);
    panelHorarioTable.add(lblHorarioQuarta);
    lblHorarioQuarta.setHorizontalAlignment(SwingConstants.CENTER);

    final JLabel lblHorarioQuinta = new JLabel("Qui");
    lblHorarioQuinta.setForeground(Color.WHITE);
    lblHorarioQuinta.setOpaque(true);
    lblHorarioQuinta.setBackground(Color.BLACK);
    panelHorarioTable.add(lblHorarioQuinta);
    lblHorarioQuinta.setHorizontalAlignment(SwingConstants.CENTER);

    final JLabel lblHorarioSexta = new JLabel("Sex");
    lblHorarioSexta.setForeground(Color.WHITE);
    lblHorarioSexta.setOpaque(true);
    lblHorarioSexta.setBackground(Color.BLACK);
    panelHorarioTable.add(lblHorarioSexta);
    lblHorarioSexta.setHorizontalAlignment(SwingConstants.CENTER);

    final JLabel lblHorarioSabado = new JLabel("S\u00E1b");
    lblHorarioSabado.setForeground(Color.WHITE);
    lblHorarioSabado.setOpaque(true);
    lblHorarioSabado.setBackground(Color.BLACK);
    panelHorarioTable.add(lblHorarioSabado);
    lblHorarioSabado.setHorizontalAlignment(SwingConstants.CENTER);

    final JLabel lblHorarioEntrada = new JLabel("Entrada ");
    lblHorarioEntrada.setBackground(Color.BLACK);
    lblHorarioEntrada.setForeground(Color.WHITE);
    lblHorarioEntrada.setOpaque(true);
    panelHorarioTable.add(lblHorarioEntrada);
    lblHorarioEntrada.setHorizontalAlignment(SwingConstants.RIGHT);

    final JLabel lblHorarioEDom = new JLabel("00:00");
    panelHorarioTable.add(lblHorarioEDom);
    lblHorarioEDom.setHorizontalAlignment(SwingConstants.CENTER);

    final JLabel lblHorarioESeg = new JLabel("00:00");
    panelHorarioTable.add(lblHorarioESeg);
    lblHorarioESeg.setHorizontalAlignment(SwingConstants.CENTER);

    final JLabel lblHorarioETer = new JLabel("00:00");
    panelHorarioTable.add(lblHorarioETer);
    lblHorarioETer.setHorizontalAlignment(SwingConstants.CENTER);

    final JLabel lblHorarioEQua = new JLabel("00:00");
    panelHorarioTable.add(lblHorarioEQua);
    lblHorarioEQua.setHorizontalAlignment(SwingConstants.CENTER);

    final JLabel lblHorarioEQui = new JLabel("00:00");
    panelHorarioTable.add(lblHorarioEQui);
    lblHorarioEQui.setHorizontalAlignment(SwingConstants.CENTER);

    final JLabel lblHorarioESex = new JLabel("00:00");
    panelHorarioTable.add(lblHorarioESex);
    lblHorarioESex.setHorizontalAlignment(SwingConstants.CENTER);

    final JLabel lblHorarioESab = new JLabel("00:00");
    panelHorarioTable.add(lblHorarioESab);
    lblHorarioESab.setHorizontalAlignment(SwingConstants.CENTER);

    final JLabel lblHorarioSaida = new JLabel("Sa\u00EDda ");
    lblHorarioSaida.setBackground(Color.BLACK);
    lblHorarioSaida.setForeground(Color.WHITE);
    lblHorarioSaida.setOpaque(true);
    panelHorarioTable.add(lblHorarioSaida);
    lblHorarioSaida.setHorizontalAlignment(SwingConstants.RIGHT);

    final JLabel lblHorarioSDom = new JLabel("00:00");
    panelHorarioTable.add(lblHorarioSDom);
    lblHorarioSDom.setHorizontalAlignment(SwingConstants.CENTER);

    final JLabel lblHorarioSSeg = new JLabel("00:00");
    panelHorarioTable.add(lblHorarioSSeg);
    lblHorarioSSeg.setHorizontalAlignment(SwingConstants.CENTER);

    final JLabel lblHorarioSTer = new JLabel("00:00");
    panelHorarioTable.add(lblHorarioSTer);
    lblHorarioSTer.setHorizontalAlignment(SwingConstants.CENTER);

    final JLabel lblHorarioSQua = new JLabel("00:00");
    panelHorarioTable.add(lblHorarioSQua);
    lblHorarioSQua.setHorizontalAlignment(SwingConstants.CENTER);

    final JLabel lblHorarioSQui = new JLabel("00:00");
    panelHorarioTable.add(lblHorarioSQui);
    lblHorarioSQui.setHorizontalAlignment(SwingConstants.CENTER);

    final JLabel lblHorarioSSex = new JLabel("00:00");
    panelHorarioTable.add(lblHorarioSSex);
    lblHorarioSSex.setHorizontalAlignment(SwingConstants.CENTER);

    final JLabel lblHorarioSSab = new JLabel("00:00");
    panelHorarioTable.add(lblHorarioSSab);
    lblHorarioSSab.setHorizontalAlignment(SwingConstants.CENTER);

    final JLabel lblParaAlterarO = new JLabel(
        "Para alterar o hor\u00E1rio, acessar o controle de ponto.");
    lblParaAlterarO.setFont(new Font("Tahoma", Font.ITALIC, 11));
    lblParaAlterarO.setHorizontalAlignment(SwingConstants.CENTER);
    panelHorario.add(lblParaAlterarO, BorderLayout.SOUTH);

    final JPanel panelFerias = new JPanel();
    springLayout.putConstraint(SpringLayout.NORTH, panelFerias, 6,
        SpringLayout.SOUTH, panelHorario);
    springLayout.putConstraint(SpringLayout.WEST, panelFerias, -300,
        SpringLayout.EAST, this);
    springLayout.putConstraint(SpringLayout.SOUTH, panelFerias, 0,
        SpringLayout.SOUTH, this);
    springLayout.putConstraint(SpringLayout.EAST, panelFerias, 0,
        SpringLayout.EAST, this);
    add(panelFerias);
    panelFerias.setLayout(new BorderLayout(0, 0));

    final JPanel panelFeriasTop = new JPanel();
    panelFerias.add(panelFeriasTop, BorderLayout.NORTH);
    panelFeriasTop.setLayout(new GridLayout(1, 0, 0, 0));

    final JLabel lblFerias = new JLabel("F\u00E9rias:");
    lblFerias.setFont(lblFerias.getFont().deriveFont(
        lblFerias.getFont().getStyle() | Font.BOLD));
    panelFeriasTop.add(lblFerias);

    final JLabel lblFeriasVence = new JLabel("Vence em 12/34/5678");
    lblFeriasVence.setHorizontalAlignment(SwingConstants.RIGHT);
    panelFeriasTop.add(lblFeriasVence);

    final JScrollPane scrollFerias = new JScrollPane();
    scrollFerias
        .setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    panelFerias.add(scrollFerias, BorderLayout.CENTER);

    final JLabel lblParaAlterarFrias = new JLabel(
        "Para alterar f\u00E9rias, usar o controle de ponto.");
    lblParaAlterarFrias.setHorizontalAlignment(SwingConstants.CENTER);
    lblParaAlterarFrias.setFont(lblParaAlterarFrias.getFont().deriveFont(
        lblParaAlterarFrias.getFont().getStyle() | Font.ITALIC));
    panelFerias.add(lblParaAlterarFrias, BorderLayout.SOUTH);

    final JScrollPane scrollResponsabilidades = new JScrollPane();
    springLayout.putConstraint(SpringLayout.WEST, scrollResponsabilidades, 0,
        SpringLayout.WEST, this);
    springLayout.putConstraint(SpringLayout.SOUTH, scrollResponsabilidades, 0,
        SpringLayout.SOUTH, this);
    add(scrollResponsabilidades);
    scrollResponsabilidades
        .setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

    txtResponsabilidades = new JTextArea();
    scrollResponsabilidades.setViewportView(txtResponsabilidades);

    final JLabel lblResponsabilidades = new JLabel(
        "Atribui\u00E7\u00F5es e Responsabilidades:");
    lblResponsabilidades.setFont(lblResponsabilidades.getFont().deriveFont(
        lblResponsabilidades.getFont().getStyle() | Font.BOLD));
    springLayout.putConstraint(SpringLayout.NORTH, scrollResponsabilidades, 2,
        SpringLayout.SOUTH, lblResponsabilidades);
    springLayout.putConstraint(SpringLayout.EAST, scrollResponsabilidades, 0,
        SpringLayout.EAST, lblResponsabilidades);
    springLayout.putConstraint(SpringLayout.NORTH, lblResponsabilidades, 6,
        SpringLayout.SOUTH, panelContaSalario);
    springLayout.putConstraint(SpringLayout.WEST, lblResponsabilidades, 0,
        SpringLayout.WEST, this);
    springLayout.putConstraint(SpringLayout.EAST, lblResponsabilidades, -6,
        SpringLayout.WEST, panelFerias);
    add(lblResponsabilidades);
  }

  private String comDigito(int i) {
    final int digito = i % 10;
    i /= 10;
    return i + "-" + digito;
  }

  public void setBancos(final String[] bancos) {
    final String selected = (String) cbContaBanco.getSelectedItem();
    final DefaultComboBoxModel<String> model = (DefaultComboBoxModel<String>) cbContaBanco
        .getModel();
    model.removeAllElements();
    for (final String cargo : bancos) {
      model.addElement(cargo);
    }
    cbContaBanco.setSelectedItem(selected);
  }

  public void setCargos(final String[] cargos) {
    final String selected = (String) cbCargo.getSelectedItem();
    final DefaultComboBoxModel<String> model = (DefaultComboBoxModel<String>) cbCargo
        .getModel();
    model.removeAllElements();
    for (final String cargo : cargos) {
      model.addElement(cargo);
    }
    cbCargo.setSelectedItem(selected);
  }

  public void setDados(final LocalDateTime cadastrado,
      final FuncionarioProfissional f) {
    final boolean n = (f == null);
    lblId.setText(n ? "-" : String.valueOf(f.id));
    lblCodigo.setText(n ? "-" : "F#" + f.idfuncionario);
    lblCadastradoEm.setText(n ? "-" : cadastrado.toString());
    lblAlteradoEm.setText(n ? "-" : f.alteradoEm.toString());
    lblAlteradoPor.setText(n ? "-" : Client.cache().users().get(f.alteradoPor)
        .getName());
    lblTempoDeCasa.setText(n ? "-" : f.calcularTempoDeCasa());

    txtAdmissao.setText(n ? "" : f.admissao.toString());
    cbCargo.setSelectedItem(n ? null : f.cargo);
    txtFuncao.setText(n ? "" : f.funcao);
    txtSalario.setValue(n ? Money.zero() : f.salario);
    txtExtra.setValue(n ? Money.zero() : f.extra);
    txtExtraObservacoes.setText(n ? "" : f.extraObservacoes);

    cbContaBanco.setSelectedItem(n ? null : f.pagamentoBanco);
    txtPagamentoAgencia.setText(n ? "" : comDigito(f.pagamentoAgencia));
    txtContaConta.setText(n ? "" : comDigito(f.pagamentoConta));

    txtResponsabilidades.setText(n ? null : f.responsabilidades);
  }
}
