package net.geral.essomerie.client._gui.agenda.clientes.paineis;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.text.JTextComponent;

import net.geral.essomerie._shared.cliente.ClienteDadosExtras;
import net.geral.essomerie._shared.cliente.Customer;
import net.geral.essomerie.client._gui.shared.label.TitleLabel;
import net.geral.essomerie.client.gui.shared.tables.addresses.AddressesTable;
import net.geral.essomerie.client.gui.shared.tables.telephones.TelephonesTable;
import net.geral.plagiary.TransferFocus;

public class ClientePanelDadosCliente extends JPanel {
  static final long serialVersionUID = 1L;

  private static String getTitulo(final ClientePanelModo modo) {
    switch (modo) {
      case PESQUISAR:
        return "Pesquisar Clientes";
      case CONSULTAR:
        return "Consultar Cliente";
      case ALTERAR:
        return "Alterar Cliente";
      case CADASTRAR:
        return "Novo Cliente";
      default:
        return "---";
    }
  }

  final JLabel             lblTelefonesAdd;
  final JLabel             lblTelefonesRemove;
  final JLabel             lblEnderecosAdd;
  final JLabel             lblEnderecosRemove;
  final JLabel             lblCadastrado;
  final JLabel             lblPedidos;
  final JLabel             lblConsumo;
  final JLabel             lblUltimoPedido;
  final TelephonesTable    tableTelefones;
  final AddressesTable     tableEnderecos;
  final JCheckBox          cbNotaPaulista;
  final JTextPane          txtObservacoes;
  final JTextField         txtCodigo;
  final JTextField         txtNome;
  final JTextField         txtCPF;
  final JTextField         txtTelefones;
  final JTextField         txtEnderecos;
  final JScrollPane        scrollTelefones;
  final JScrollPane        scrollEnderecos;
  private final JPanel     panelExtrasInfo;
  private final JPanel     panelBotoes;
  private final TitleLabel lblTitulo;

  public ClientePanelDadosCliente() {
    setLayout(new BorderLayout(0, 0));

    lblTitulo = new TitleLabel("Dados do Cliente");
    add(lblTitulo, BorderLayout.NORTH);
    lblTitulo.setText("---");

    final JPanel panelDadosBorder = new JPanel();
    panelDadosBorder.setBorder(new EmptyBorder(5, 5, 5, 5));
    add(panelDadosBorder, BorderLayout.CENTER);
    panelDadosBorder
        .setLayout(new BoxLayout(panelDadosBorder, BoxLayout.X_AXIS));

    final JPanel panelDados = new JPanel();
    panelDadosBorder.add(panelDados);
    final SpringLayout sl_panelDados = new SpringLayout();
    panelDados.setLayout(sl_panelDados);

    final JLabel lblCodigo = new JLabel("C\u00F3digo:");
    sl_panelDados.putConstraint(SpringLayout.NORTH, lblCodigo, 0,
        SpringLayout.NORTH, panelDados);
    sl_panelDados.putConstraint(SpringLayout.WEST, lblCodigo, 0,
        SpringLayout.WEST, panelDados);
    sl_panelDados.putConstraint(SpringLayout.EAST, lblCodigo, 60,
        SpringLayout.WEST, panelDados);
    panelDados.add(lblCodigo);
    lblCodigo.setFont(lblCodigo.getFont().deriveFont(
        lblCodigo.getFont().getStyle() | Font.BOLD));

    txtCodigo = new JTextField();
    sl_panelDados.putConstraint(SpringLayout.NORTH, txtCodigo, 2,
        SpringLayout.SOUTH, lblCodigo);
    sl_panelDados.putConstraint(SpringLayout.WEST, txtCodigo, 0,
        SpringLayout.WEST, lblCodigo);
    sl_panelDados.putConstraint(SpringLayout.EAST, txtCodigo, 0,
        SpringLayout.EAST, lblCodigo);
    panelDados.add(txtCodigo);

    final JLabel lblNome = new JLabel("Nome:");
    sl_panelDados.putConstraint(SpringLayout.NORTH, lblNome, 0,
        SpringLayout.NORTH, lblCodigo);
    sl_panelDados.putConstraint(SpringLayout.WEST, lblNome, 10,
        SpringLayout.EAST, lblCodigo);
    panelDados.add(lblNome);
    lblNome.setFont(lblNome.getFont().deriveFont(
        lblNome.getFont().getStyle() | Font.BOLD));

    txtNome = new JTextField();
    sl_panelDados.putConstraint(SpringLayout.NORTH, txtNome, 0,
        SpringLayout.NORTH, txtCodigo);
    sl_panelDados.putConstraint(SpringLayout.WEST, txtNome, 0,
        SpringLayout.WEST, lblNome);
    sl_panelDados.putConstraint(SpringLayout.EAST, txtNome, 0,
        SpringLayout.EAST, lblNome);
    panelDados.add(txtNome);

    final JLabel lblCPF = new JLabel("CPF:");
    sl_panelDados.putConstraint(SpringLayout.EAST, lblNome, -10,
        SpringLayout.WEST, lblCPF);
    sl_panelDados.putConstraint(SpringLayout.NORTH, lblCPF, 0,
        SpringLayout.NORTH, lblCodigo);
    panelDados.add(lblCPF);
    lblCPF.setFont(lblCPF.getFont().deriveFont(
        lblCPF.getFont().getStyle() | Font.BOLD));

    txtCPF = new JTextField();
    sl_panelDados.putConstraint(SpringLayout.WEST, lblCPF, 0,
        SpringLayout.WEST, txtCPF);
    sl_panelDados.putConstraint(SpringLayout.NORTH, txtCPF, 0,
        SpringLayout.NORTH, txtCodigo);
    panelDados.add(txtCPF);
    txtCPF.setColumns(11);

    cbNotaPaulista = new JCheckBox("NP");
    sl_panelDados.putConstraint(SpringLayout.SOUTH, cbNotaPaulista, 0,
        SpringLayout.NORTH, txtCPF);
    sl_panelDados.putConstraint(SpringLayout.EAST, cbNotaPaulista, 0,
        SpringLayout.EAST, txtCPF);
    panelDados.add(cbNotaPaulista);
    lblUltimoPedido = new JLabel("-");
    final JLabel lblTPedidos = new JLabel("Pedidos:  ");
    lblTPedidos.setFont(lblTPedidos.getFont().deriveFont(
        lblTPedidos.getFont().getStyle() | Font.BOLD));
    final JLabel lblTCadastrado = new JLabel("Cadastrado:  ");

    panelExtrasInfo = new JPanel();
    sl_panelDados.putConstraint(SpringLayout.WEST, txtCPF, -120,
        SpringLayout.WEST, panelExtrasInfo);
    sl_panelDados.putConstraint(SpringLayout.SOUTH, panelExtrasInfo, 0,
        SpringLayout.SOUTH, txtCPF);
    sl_panelDados.putConstraint(SpringLayout.EAST, txtCPF, -10,
        SpringLayout.WEST, panelExtrasInfo);
    sl_panelDados.putConstraint(SpringLayout.NORTH, panelExtrasInfo, 0,
        SpringLayout.NORTH, panelDados);
    sl_panelDados.putConstraint(SpringLayout.EAST, panelExtrasInfo, 0,
        SpringLayout.EAST, panelDados);
    panelDados.add(panelExtrasInfo);
    panelExtrasInfo.setBackground(Color.WHITE);
    panelExtrasInfo.setBorder(new LineBorder(new Color(0, 0, 0)));
    final GridBagLayout gbl_panelInfo = new GridBagLayout();
    gbl_panelInfo.columnWidths = new int[] { 90, 70, 80, 70, 0 };
    gbl_panelInfo.rowHeights = new int[] { 1, 1, 0 };
    gbl_panelInfo.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0,
        Double.MIN_VALUE };
    gbl_panelInfo.rowWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
    panelExtrasInfo.setLayout(gbl_panelInfo);

    lblTCadastrado.setHorizontalAlignment(SwingConstants.TRAILING);
    final GridBagConstraints gbc_lblTCadastrado = new GridBagConstraints();
    gbc_lblTCadastrado.fill = GridBagConstraints.HORIZONTAL;
    gbc_lblTCadastrado.insets = new Insets(0, 0, 5, 5);
    gbc_lblTCadastrado.gridx = 0;
    gbc_lblTCadastrado.gridy = 0;
    panelExtrasInfo.add(lblTCadastrado, gbc_lblTCadastrado);
    lblTCadastrado.setFont(lblTCadastrado.getFont().deriveFont(
        lblTCadastrado.getFont().getStyle() | Font.BOLD));

    lblCadastrado = new JLabel("-");
    final GridBagConstraints gbc_lblCadastrado = new GridBagConstraints();
    gbc_lblCadastrado.fill = GridBagConstraints.HORIZONTAL;
    gbc_lblCadastrado.insets = new Insets(0, 0, 5, 5);
    gbc_lblCadastrado.gridx = 1;
    gbc_lblCadastrado.gridy = 0;
    panelExtrasInfo.add(lblCadastrado, gbc_lblCadastrado);

    lblTPedidos.setHorizontalAlignment(SwingConstants.TRAILING);
    final GridBagConstraints gbc_lblTPedidos = new GridBagConstraints();
    gbc_lblTPedidos.fill = GridBagConstraints.HORIZONTAL;
    gbc_lblTPedidos.insets = new Insets(0, 0, 5, 5);
    gbc_lblTPedidos.gridx = 2;
    gbc_lblTPedidos.gridy = 0;
    panelExtrasInfo.add(lblTPedidos, gbc_lblTPedidos);
    lblPedidos = new JLabel("-");

    final GridBagConstraints gbc_lblPedidos = new GridBagConstraints();
    gbc_lblPedidos.fill = GridBagConstraints.HORIZONTAL;
    gbc_lblPedidos.insets = new Insets(0, 0, 5, 0);
    gbc_lblPedidos.gridx = 3;
    gbc_lblPedidos.gridy = 0;
    panelExtrasInfo.add(lblPedidos, gbc_lblPedidos);
    final JLabel lblTUltimoPedido = new JLabel(" \u00DAltimo Pedido:  ");
    final GridBagConstraints gbc_lblTUltimoPedido = new GridBagConstraints();
    gbc_lblTUltimoPedido.fill = GridBagConstraints.HORIZONTAL;
    gbc_lblTUltimoPedido.insets = new Insets(0, 0, 0, 5);
    gbc_lblTUltimoPedido.gridx = 0;
    gbc_lblTUltimoPedido.gridy = 1;
    panelExtrasInfo.add(lblTUltimoPedido, gbc_lblTUltimoPedido);
    lblTUltimoPedido.setFont(lblTUltimoPedido.getFont().deriveFont(
        lblTUltimoPedido.getFont().getStyle() | Font.BOLD));

    final GridBagConstraints gbc_lblUltimoPedido = new GridBagConstraints();
    gbc_lblUltimoPedido.fill = GridBagConstraints.HORIZONTAL;
    gbc_lblUltimoPedido.insets = new Insets(0, 0, 0, 5);
    gbc_lblUltimoPedido.gridx = 1;
    gbc_lblUltimoPedido.gridy = 1;
    panelExtrasInfo.add(lblUltimoPedido, gbc_lblUltimoPedido);
    final JLabel lblTConsumo = new JLabel("Consumo:  ");
    lblTConsumo.setHorizontalAlignment(SwingConstants.TRAILING);

    final GridBagConstraints gbc_lblTConsumo = new GridBagConstraints();
    gbc_lblTConsumo.fill = GridBagConstraints.HORIZONTAL;
    gbc_lblTConsumo.insets = new Insets(0, 0, 0, 5);
    gbc_lblTConsumo.gridx = 2;
    gbc_lblTConsumo.gridy = 1;
    panelExtrasInfo.add(lblTConsumo, gbc_lblTConsumo);
    lblTConsumo.setFont(lblTConsumo.getFont().deriveFont(
        lblTConsumo.getFont().getStyle() | Font.BOLD));

    lblConsumo = new JLabel("-");
    final GridBagConstraints gbc_lblConsumo = new GridBagConstraints();
    gbc_lblConsumo.fill = GridBagConstraints.HORIZONTAL;
    gbc_lblConsumo.gridx = 3;
    gbc_lblConsumo.gridy = 1;
    panelExtrasInfo.add(lblConsumo, gbc_lblConsumo);

    final JPanel panelTelefones = new JPanel();
    sl_panelDados.putConstraint(SpringLayout.EAST, panelTelefones, 220,
        SpringLayout.WEST, panelDados);
    panelDados.add(panelTelefones);
    sl_panelDados.putConstraint(SpringLayout.NORTH, panelTelefones, 10,
        SpringLayout.SOUTH, txtCodigo);
    sl_panelDados.putConstraint(SpringLayout.WEST, panelTelefones, 0,
        SpringLayout.WEST, panelDados);
    panelTelefones.setLayout(new BorderLayout(0, 0));

    final JPanel panelTelefonesTop = new JPanel();
    panelTelefones.add(panelTelefonesTop, BorderLayout.NORTH);
    panelTelefonesTop.setLayout(new BorderLayout(0, 0));

    final JLabel lblTelefones = new JLabel("Telefones:");
    panelTelefonesTop.add(lblTelefones, BorderLayout.WEST);
    lblTelefones.setFont(lblTelefones.getFont().deriveFont(
        lblTelefones.getFont().getStyle() | Font.BOLD));

    final JPanel panelTelefonesButtons = new JPanel();
    final FlowLayout flowLayout = (FlowLayout) panelTelefonesButtons
        .getLayout();
    flowLayout.setVgap(0);
    panelTelefonesTop.add(panelTelefonesButtons, BorderLayout.EAST);

    lblTelefonesAdd = new JLabel();
    lblTelefonesAdd.setIcon(new ImageIcon(ClientePanelDadosCliente.class
        .getResource("/res/img/i16/mais.png")));
    panelTelefonesButtons.add(lblTelefonesAdd);

    lblTelefonesRemove = new JLabel();
    lblTelefonesRemove.setIcon(new ImageIcon(ClientePanelDadosCliente.class
        .getResource("/res/img/i16/menos.png")));
    panelTelefonesButtons.add(lblTelefonesRemove);

    scrollTelefones = new JScrollPane();
    scrollTelefones.setPreferredSize(new Dimension(2, 110));
    panelTelefones.add(scrollTelefones);

    tableTelefones = new TelephonesTable();
    scrollTelefones.setViewportView(tableTelefones);

    txtTelefones = new JTextField();
    panelTelefones.add(txtTelefones, BorderLayout.SOUTH);

    final JPanel panelEnderecos = new JPanel();
    sl_panelDados.putConstraint(SpringLayout.NORTH, panelEnderecos, 0,
        SpringLayout.NORTH, panelTelefones);
    sl_panelDados.putConstraint(SpringLayout.WEST, panelEnderecos, 10,
        SpringLayout.EAST, panelTelefones);
    sl_panelDados.putConstraint(SpringLayout.SOUTH, panelEnderecos, 0,
        SpringLayout.SOUTH, panelTelefones);
    sl_panelDados.putConstraint(SpringLayout.EAST, panelEnderecos, 0,
        SpringLayout.EAST, panelDados);
    panelDados.add(panelEnderecos);
    panelEnderecos.setLayout(new BorderLayout(0, 0));

    final JPanel panelEnderecosTop = new JPanel();
    panelEnderecos.add(panelEnderecosTop, BorderLayout.NORTH);
    panelEnderecosTop.setLayout(new BorderLayout(0, 0));

    final JLabel lblEnderecos = new JLabel("Endere\u00E7os:");
    panelEnderecosTop.add(lblEnderecos, BorderLayout.WEST);
    sl_panelDados.putConstraint(SpringLayout.EAST, lblEnderecos, 0,
        SpringLayout.EAST, panelDados);
    lblEnderecos.setFont(new Font("Tahoma", Font.BOLD, 11));

    final JPanel panelEnderecosButtons = new JPanel();
    final FlowLayout fl_panelEnderecosButtons = (FlowLayout) panelEnderecosButtons
        .getLayout();
    fl_panelEnderecosButtons.setVgap(0);
    panelEnderecosTop.add(panelEnderecosButtons, BorderLayout.EAST);

    lblEnderecosAdd = new JLabel();
    lblEnderecosAdd.setIcon(new ImageIcon(ClientePanelDadosCliente.class
        .getResource("/res/img/i16/mais.png")));
    panelEnderecosButtons.add(lblEnderecosAdd);

    lblEnderecosRemove = new JLabel();
    lblEnderecosRemove.setIcon(new ImageIcon(ClientePanelDadosCliente.class
        .getResource("/res/img/i16/menos.png")));
    panelEnderecosButtons.add(lblEnderecosRemove);

    scrollEnderecos = new JScrollPane();
    panelEnderecos.add(scrollEnderecos, BorderLayout.CENTER);

    tableEnderecos = new AddressesTable();
    scrollEnderecos.setViewportView(tableEnderecos);

    txtEnderecos = new JTextField();
    panelEnderecos.add(txtEnderecos, BorderLayout.SOUTH);

    final JLabel lblObservacoes = new JLabel("Observa\u00E7\u00F5es:");
    sl_panelDados.putConstraint(SpringLayout.NORTH, lblObservacoes, 10,
        SpringLayout.SOUTH, panelTelefones);
    sl_panelDados.putConstraint(SpringLayout.WEST, lblObservacoes, 0,
        SpringLayout.WEST, panelDados);
    sl_panelDados.putConstraint(SpringLayout.EAST, lblObservacoes, 0,
        SpringLayout.EAST, panelDados);
    panelDados.add(lblObservacoes);
    lblObservacoes.setFont(lblObservacoes.getFont().deriveFont(
        lblObservacoes.getFont().getStyle() | Font.BOLD));

    final JScrollPane scrollObservacoes = new JScrollPane();
    sl_panelDados.putConstraint(SpringLayout.NORTH, scrollObservacoes, 2,
        SpringLayout.SOUTH, lblObservacoes);
    sl_panelDados.putConstraint(SpringLayout.WEST, scrollObservacoes, 0,
        SpringLayout.WEST, lblObservacoes);
    sl_panelDados.putConstraint(SpringLayout.SOUTH, scrollObservacoes, 0,
        SpringLayout.SOUTH, panelDados);
    sl_panelDados.putConstraint(SpringLayout.EAST, scrollObservacoes, 0,
        SpringLayout.EAST, lblObservacoes);
    panelDados.add(scrollObservacoes);

    txtObservacoes = new JTextPane();
    TransferFocus.patch(txtObservacoes);
    scrollObservacoes.setViewportView(txtObservacoes);

    final JPanel panelOpcoes = new JPanel();
    add(panelOpcoes, BorderLayout.EAST);
    panelOpcoes.setBorder(new CompoundBorder(new EmptyBorder(0, 10, 0, 0),
        new CompoundBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null),
            new EmptyBorder(5, 5, 5, 5))));
    panelOpcoes.setLayout(new BorderLayout(0, 0));

    panelBotoes = new JPanel();
    panelOpcoes.add(panelBotoes, BorderLayout.SOUTH);
    panelBotoes.setLayout(new GridLayout(0, 1, 0, 0));

    final JButton btnNewButton = new JButton("New button");
    panelBotoes.add(btnNewButton);
  }

  public Customer createCliente(final int idcliente) {
    // final CPF cpf = CPF.createOrZero(txtCPF.getText());
    // return new Customer(idcliente, txtNome.getText(), cpf,
    // cbNotaPaulista.isSelected(), txtObservacoes.getText(), tableEnderecos
    // .getModel().getEnderecos(), tableTelefones.getModel()
    // .getTelefones());
    return null;
  }

  ClientePanelFilter getFilter() {
    final ClientePanelFilter filter = new ClientePanelFilter(
        txtCodigo.getText(), txtNome.getText(), txtCPF.getText(),
        txtTelefones.getText(), txtEnderecos.getText(),
        txtObservacoes.getText());
    return filter;
  }

  public JTextField[] getTextFields() {
    return new JTextField[] { txtCodigo, txtNome, txtCPF, txtTelefones,
        txtEnderecos };
  }

  public void limpar() {
    setCliente(null, null);
  }

  public void setCliente(final Customer c, final ClienteDadosExtras extras) {
    // txtTelefones.setText("");
    // txtEnderecos.setText("");
    // tableTelefones.getModel().clear();
    // tableEnderecos.getModel().clear();
    //
    // final boolean n = (c == null);
    // txtCodigo.setText(n ? "" : String.valueOf(c.idcliente));
    // txtNome.setText(n ? "" : c.nome);
    // txtCPF.setText(n ? "" : c.cpf.toString());
    // cbNotaPaulista.setSelected(n ? false : c.notaPaulista);
    // txtObservacoes.setText(n ? "" : c.observacoes);
    // tableTelefones.getModel().setTelefones(n ? new Telefone[0] :
    // c.telefones);
    // tableEnderecos.getModel().setEnderecos(n ? new Endereco[0] :
    // c.enderecos);
    // lblCadastrado.setText("-");
    // lblUltimoPedido.setText("-");
    // lblPedidos.setText("-");
    // lblConsumo.setText("-");
    // if (!n) {
    // if ((extras != null) && (extras.idcliente == c.idcliente)) {
    // setDadosExtras(extras);
    // }
    // else {
    // try {
    // Client.connection().requestClientesDadosExtras(c.idcliente);
    // }
    // catch (final IOException e) {
    // Log4J.warning(e);
    // }
    // }
    // }
  }

  public void setDadosExtras(final ClienteDadosExtras cde) {
    lblCadastrado.setText(cde.cadastrado.toString());
    lblPedidos.setText(String.format("%,d", cde.numeroPedidos));
    if (cde.numeroPedidos == 0) {
      lblUltimoPedido.setText("-");
      lblConsumo.setText("-");
    } else {
      lblUltimoPedido.setText(cde.ultimoPedido.toString());
      lblConsumo.setText(cde.consumoPedidos.toString());
    }
  }

  public void setEditable(final boolean b) {
    txtNome.setEditable(b);
    txtCPF.setEditable(b);
    txtTelefones.setEditable(b);
    txtEnderecos.setEditable(b);
    txtObservacoes.setEditable(b);
    // tableTelefones.getModel().setEditable(b);
    // tableEnderecos.getModel().setEditable(b);
    lblTelefonesAdd.setEnabled(b);
    lblTelefonesRemove.setEnabled(b);
    lblEnderecosAdd.setEnabled(b);
    lblEnderecosRemove.setEnabled(b);
  }

  void setPesquisaPorCodigo(final boolean porCodigo) {
    final JTextComponent[] components = { txtNome, txtCPF, txtTelefones,
        txtEnderecos, txtObservacoes };
    for (final JTextComponent j : components) {
      j.setEnabled(!porCodigo);
    }
  }

  public void setTitulo(final ClientePanelModo modo) {
    lblTitulo.setText(getTitulo(modo));
  }

  public void showButtons(final JButton... btns) {
    panelBotoes.removeAll();
    for (final JButton b : btns) {
      if (b == null) {
        panelBotoes.add(new JPanel());
      } else {
        panelBotoes.add(b);
      }
    }
    panelBotoes.revalidate();
  }

  void showTelefoneEnderecoExtrasTable(final boolean b) {
    panelExtrasInfo.setVisible(b);
    scrollTelefones.setVisible(b);
    scrollEnderecos.setVisible(b);
    lblEnderecosAdd.setVisible(b);
    lblEnderecosRemove.setVisible(b);
    lblTelefonesAdd.setVisible(b);
    lblTelefonesRemove.setVisible(b);
    txtTelefones.setVisible(!b);
    txtEnderecos.setVisible(!b);
  }
}
