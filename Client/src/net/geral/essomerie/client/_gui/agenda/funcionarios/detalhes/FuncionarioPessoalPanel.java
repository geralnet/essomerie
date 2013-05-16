package net.geral.essomerie.client._gui.agenda.funcionarios.detalhes;

import javax.swing.JPanel;

public class FuncionarioPessoalPanel extends JPanel {
  // private static final long serialVersionUID = 1L;
  // private final JTextField txtApelido;
  // private final JTextField txtNome;
  // private final JTextField txtDataDeNascimento;
  // private final JLabel lblId;
  // private final JTextField txtNaturalidade;
  // private final JTextField txtNacionalidade;
  // private final JTextField txtNomeConjuge;
  // private final JTextField txtNomeDaMae;
  // private final JTextField txtNomeDoPai;
  // private final JTextField txtResidenciaCidade;
  // private final JTextField txtResidenciaBairro;
  // private final JTextField txtResidenciaEndereco;
  // private final JTextField txtResidenciaCEP;
  // private final JLabel lblCodigo;
  // private final JLabel lblCadastradoEm;
  // private final JLabel lblAlteradoEm;
  // private final JLabel lblAlteradoPor;
  // private final JLabel lblIdade;
  // private final JRadioButton rbSexoFeminino;
  // private final JRadioButton rbSexoMasculino;
  // private final JComboBox<Address.BrasilUF> cbNaturalidadeUF;
  // private final JComboBox<Escolaridade> cbEscolaridade;
  // private final JComboBox<EstadoCivil> cbEstadoCivil;
  // private final JComboBox<Address.BrasilUF> cbResidenciaUF;
  // private final TelephonesTable telefonesTable;
  // private final ButtonGroup sexoButtonGroup = new ButtonGroup();
  // private final JTextArea txtFilhos;
  // private final JTextArea txtObservacoes;
  //
  // public FuncionarioPessoalPanel() {
  // setBorder(new EmptyBorder(2, 2, 2, 2));
  // final SpringLayout springLayout = new SpringLayout();
  // setLayout(springLayout);
  //
  // final JPanel panelL1 = new JPanel();
  // springLayout.putConstraint(SpringLayout.NORTH, panelL1, 0,
  // SpringLayout.NORTH, this);
  // springLayout.putConstraint(SpringLayout.WEST, panelL1, 0,
  // SpringLayout.WEST, this);
  // springLayout.putConstraint(SpringLayout.SOUTH, panelL1, 40,
  // SpringLayout.NORTH, this);
  // add(panelL1);
  // final SpringLayout sl_panelL1 = new SpringLayout();
  // panelL1.setLayout(sl_panelL1);
  //
  // final JLabel lblTId = new JLabel("ID#:");
  // sl_panelL1.putConstraint(SpringLayout.EAST, lblTId, 50, SpringLayout.WEST,
  // panelL1);
  // lblTId.setFont(lblTId.getFont().deriveFont(
  // lblTId.getFont().getStyle() | Font.BOLD));
  // sl_panelL1.putConstraint(SpringLayout.NORTH, lblTId, 0, SpringLayout.NORTH,
  // panelL1);
  // sl_panelL1.putConstraint(SpringLayout.WEST, lblTId, 0, SpringLayout.WEST,
  // panelL1);
  // panelL1.add(lblTId);
  //
  // final JPanel panelL2 = new JPanel();
  // springLayout.putConstraint(SpringLayout.NORTH, panelL2, 0,
  // SpringLayout.SOUTH, panelL1);
  // springLayout.putConstraint(SpringLayout.WEST, panelL2, 0,
  // SpringLayout.WEST, this);
  // springLayout.putConstraint(SpringLayout.SOUTH, panelL2, 50,
  // SpringLayout.SOUTH, panelL1);
  // add(panelL2);
  // final SpringLayout sl_panelL2 = new SpringLayout();
  // panelL2.setLayout(sl_panelL2);
  //
  // final JLabel lblNome = new JLabel("Nome:");
  // sl_panelL2.putConstraint(SpringLayout.NORTH, lblNome, 0,
  // SpringLayout.NORTH, panelL2);
  // sl_panelL2.putConstraint(SpringLayout.WEST, lblNome, 0, SpringLayout.WEST,
  // panelL2);
  // lblNome.setFont(lblNome.getFont().deriveFont(
  // lblNome.getFont().getStyle() | Font.BOLD));
  // springLayout.putConstraint(SpringLayout.NORTH, lblNome, 9,
  // SpringLayout.NORTH, panelL2);
  // springLayout.putConstraint(SpringLayout.WEST, lblNome, 5,
  // SpringLayout.WEST, panelL2);
  // panelL2.add(lblNome);
  //
  // txtNome = new JTextField();
  // springLayout.putConstraint(SpringLayout.EAST, txtNome, 0,
  // SpringLayout.EAST, lblNome);
  // sl_panelL2.putConstraint(SpringLayout.NORTH, txtNome, 2,
  // SpringLayout.SOUTH, lblNome);
  // sl_panelL2.putConstraint(SpringLayout.WEST, txtNome, 0, SpringLayout.WEST,
  // lblNome);
  // sl_panelL2.putConstraint(SpringLayout.EAST, txtNome, 0, SpringLayout.EAST,
  // lblNome);
  // springLayout.putConstraint(SpringLayout.NORTH, txtNome, 6,
  // SpringLayout.NORTH, panelL2);
  // springLayout.putConstraint(SpringLayout.WEST, txtNome, 41,
  // SpringLayout.WEST, panelL2);
  // panelL2.add(txtNome);
  // txtNome.setColumns(10);
  //
  // final JLabel lblApelido = new JLabel("Apelido:");
  // lblApelido.setFont(lblApelido.getFont().deriveFont(
  // lblApelido.getFont().getStyle() | Font.BOLD));
  // sl_panelL2.putConstraint(SpringLayout.NORTH, lblApelido, 0,
  // SpringLayout.NORTH, panelL2);
  // sl_panelL2.putConstraint(SpringLayout.EAST, lblNome, -6, SpringLayout.WEST,
  // lblApelido);
  // springLayout.putConstraint(SpringLayout.NORTH, lblApelido, 9,
  // SpringLayout.NORTH, panelL2);
  // springLayout.putConstraint(SpringLayout.WEST, lblApelido, 132,
  // SpringLayout.WEST, panelL2);
  // panelL2.add(lblApelido);
  //
  // txtApelido = new JTextField();
  // springLayout.putConstraint(SpringLayout.EAST, txtApelido, 0,
  // SpringLayout.EAST, lblApelido);
  // sl_panelL2.putConstraint(SpringLayout.NORTH, txtApelido, 2,
  // SpringLayout.SOUTH, lblApelido);
  // sl_panelL2.putConstraint(SpringLayout.WEST, txtApelido, 0,
  // SpringLayout.WEST, lblApelido);
  // sl_panelL2.putConstraint(SpringLayout.EAST, txtApelido, 0,
  // SpringLayout.EAST, lblApelido);
  // springLayout.putConstraint(SpringLayout.NORTH, txtApelido, 6,
  // SpringLayout.NORTH, panelL2);
  // springLayout.putConstraint(SpringLayout.WEST, txtApelido, 176,
  // SpringLayout.WEST, panelL2);
  // panelL2.add(txtApelido);
  // txtApelido.setColumns(10);
  //
  // final JLabel lblSexo = new JLabel("Sexo:");
  // sl_panelL2.putConstraint(SpringLayout.WEST, lblApelido, -200,
  // SpringLayout.WEST, lblSexo);
  // sl_panelL2.putConstraint(SpringLayout.EAST, lblApelido, -6,
  // SpringLayout.WEST, lblSexo);
  // lblSexo.setFont(lblSexo.getFont().deriveFont(
  // lblSexo.getFont().getStyle() | Font.BOLD));
  // sl_panelL2.putConstraint(SpringLayout.NORTH, lblSexo, 0,
  // SpringLayout.NORTH, panelL2);
  // springLayout.putConstraint(SpringLayout.NORTH, lblSexo, 9,
  // SpringLayout.NORTH, panelL2);
  // springLayout.putConstraint(SpringLayout.WEST, lblSexo, 267,
  // SpringLayout.WEST, panelL2);
  // panelL2.add(lblSexo);
  //
  // rbSexoMasculino = new JRadioButton("M");
  // sexoButtonGroup.add(rbSexoMasculino);
  // sl_panelL2.putConstraint(SpringLayout.WEST, lblSexo, 0, SpringLayout.WEST,
  // rbSexoMasculino);
  // sl_panelL2.putConstraint(SpringLayout.NORTH, rbSexoMasculino, 2,
  // SpringLayout.SOUTH, lblSexo);
  // springLayout.putConstraint(SpringLayout.NORTH, rbSexoMasculino, 5,
  // SpringLayout.NORTH, panelL2);
  // springLayout.putConstraint(SpringLayout.WEST, rbSexoMasculino, 300,
  // SpringLayout.WEST, panelL2);
  // panelL2.add(rbSexoMasculino);
  //
  // rbSexoFeminino = new JRadioButton("F");
  // sexoButtonGroup.add(rbSexoFeminino);
  // sl_panelL2.putConstraint(SpringLayout.EAST, lblSexo, 0, SpringLayout.EAST,
  // rbSexoFeminino);
  // sl_panelL2.putConstraint(SpringLayout.EAST, rbSexoMasculino, -2,
  // SpringLayout.WEST, rbSexoFeminino);
  // sl_panelL2.putConstraint(SpringLayout.NORTH, rbSexoFeminino, 2,
  // SpringLayout.SOUTH, lblSexo);
  // sl_panelL2.putConstraint(SpringLayout.EAST, rbSexoFeminino, 0,
  // SpringLayout.EAST, panelL2);
  // springLayout.putConstraint(SpringLayout.NORTH, rbSexoFeminino, 5,
  // SpringLayout.NORTH, panelL2);
  // springLayout.putConstraint(SpringLayout.WEST, rbSexoFeminino, 338,
  // SpringLayout.WEST, panelL2);
  // panelL2.add(rbSexoFeminino);
  //
  // final JPanel panelL3 = new JPanel();
  // springLayout.putConstraint(SpringLayout.NORTH, panelL3, 0,
  // SpringLayout.SOUTH, panelL2);
  // springLayout.putConstraint(SpringLayout.WEST, panelL3, 0,
  // SpringLayout.WEST, this);
  // springLayout.putConstraint(SpringLayout.SOUTH, panelL3, 50,
  // SpringLayout.SOUTH, panelL2);
  // add(panelL3);
  //
  // final JPanel panelFoto = new JPanel();
  // springLayout.putConstraint(SpringLayout.EAST, panelL3, -6,
  // SpringLayout.WEST, panelFoto);
  // final SpringLayout sl_panelL3 = new SpringLayout();
  // panelL3.setLayout(sl_panelL3);
  //
  // final JLabel lblDataDeNascimento = new JLabel("Nascimento:");
  // lblDataDeNascimento.setFont(lblDataDeNascimento.getFont().deriveFont(
  // lblDataDeNascimento.getFont().getStyle() | Font.BOLD));
  // sl_panelL3.putConstraint(SpringLayout.NORTH, lblDataDeNascimento, 0,
  // SpringLayout.NORTH, panelL3);
  // springLayout.putConstraint(SpringLayout.NORTH, lblDataDeNascimento, 8,
  // SpringLayout.NORTH, panelL3);
  // springLayout.putConstraint(SpringLayout.WEST, lblDataDeNascimento, 33,
  // SpringLayout.WEST, panelL3);
  // panelL3.add(lblDataDeNascimento);
  // springLayout.putConstraint(SpringLayout.EAST, lblDataDeNascimento, 70,
  // SpringLayout.WEST, this);
  //
  // txtDataDeNascimento = new JTextField();
  // sl_panelL3.putConstraint(SpringLayout.EAST, txtDataDeNascimento, 80,
  // SpringLayout.WEST, panelL3);
  // sl_panelL3.putConstraint(SpringLayout.WEST, lblDataDeNascimento, 0,
  // SpringLayout.WEST, txtDataDeNascimento);
  // sl_panelL3.putConstraint(SpringLayout.NORTH, txtDataDeNascimento, 2,
  // SpringLayout.SOUTH, lblDataDeNascimento);
  // sl_panelL3.putConstraint(SpringLayout.WEST, txtDataDeNascimento, 0,
  // SpringLayout.WEST, panelL3);
  // springLayout.putConstraint(SpringLayout.SOUTH, lblDataDeNascimento, -3,
  // SpringLayout.NORTH, txtDataDeNascimento);
  // springLayout.putConstraint(SpringLayout.NORTH, txtDataDeNascimento, 5,
  // SpringLayout.NORTH, panelL3);
  // springLayout.putConstraint(SpringLayout.WEST, txtDataDeNascimento, 97,
  // SpringLayout.WEST, panelL3);
  // panelL3.add(txtDataDeNascimento);
  // txtDataDeNascimento.setColumns(10);
  //
  // final JLabel lblNacionalidade = new JLabel("Nacionalidade:");
  // lblNacionalidade.setFont(lblNacionalidade.getFont().deriveFont(
  // lblNacionalidade.getFont().getStyle() | Font.BOLD));
  // sl_panelL3.putConstraint(SpringLayout.NORTH, lblNacionalidade, 0,
  // SpringLayout.NORTH, panelL3);
  // springLayout.putConstraint(SpringLayout.NORTH, lblNacionalidade, 8,
  // SpringLayout.NORTH, panelL3);
  // springLayout.putConstraint(SpringLayout.WEST, lblNacionalidade, 188,
  // SpringLayout.WEST, panelL3);
  // panelL3.add(lblNacionalidade);
  //
  // txtNacionalidade = new JTextField();
  // sl_panelL3.putConstraint(SpringLayout.EAST, txtNacionalidade, 156,
  // SpringLayout.EAST, txtDataDeNascimento);
  // sl_panelL3.putConstraint(SpringLayout.WEST, lblNacionalidade, 0,
  // SpringLayout.WEST, txtNacionalidade);
  // sl_panelL3.putConstraint(SpringLayout.NORTH, txtNacionalidade, 2,
  // SpringLayout.SOUTH, lblNacionalidade);
  // sl_panelL3.putConstraint(SpringLayout.WEST, txtNacionalidade, 6,
  // SpringLayout.EAST, txtDataDeNascimento);
  // panelL3.add(txtNacionalidade);
  // txtNacionalidade.setColumns(10);
  //
  // final JLabel lblNaturalidade = new JLabel("Naturalidade:");
  // lblNaturalidade.setFont(lblNaturalidade.getFont().deriveFont(
  // lblNaturalidade.getFont().getStyle() | Font.BOLD));
  // sl_panelL3.putConstraint(SpringLayout.NORTH, lblNaturalidade, 0,
  // SpringLayout.NORTH, panelL3);
  // springLayout.putConstraint(SpringLayout.NORTH, lblNaturalidade, 8,
  // SpringLayout.NORTH, panelL3);
  // springLayout.putConstraint(SpringLayout.WEST, lblNaturalidade, 263,
  // SpringLayout.WEST, panelL3);
  // panelL3.add(lblNaturalidade);
  //
  // txtNaturalidade = new JTextField();
  // sl_panelL3.putConstraint(SpringLayout.WEST, txtNaturalidade, 6,
  // SpringLayout.EAST, txtNacionalidade);
  // sl_panelL3.putConstraint(SpringLayout.WEST, lblNaturalidade, 0,
  // SpringLayout.WEST, txtNaturalidade);
  // sl_panelL3.putConstraint(SpringLayout.NORTH, txtNaturalidade, 2,
  // SpringLayout.SOUTH, lblNaturalidade);
  // panelL3.add(txtNaturalidade);
  // txtNaturalidade.setColumns(10);
  //
  // final JLabel lblNaturalidadeUF = new JLabel("UF:");
  // sl_panelL3.putConstraint(SpringLayout.NORTH, lblNaturalidadeUF, 0,
  // SpringLayout.NORTH, panelL3);
  // lblNaturalidadeUF.setFont(lblNaturalidadeUF.getFont().deriveFont(
  // lblNaturalidadeUF.getFont().getStyle() | Font.BOLD));
  // springLayout.putConstraint(SpringLayout.NORTH, lblNaturalidadeUF, 8,
  // SpringLayout.NORTH, panelL3);
  // springLayout.putConstraint(SpringLayout.WEST, lblNaturalidadeUF, 333,
  // SpringLayout.WEST, panelL3);
  // panelL3.add(lblNaturalidadeUF);
  //
  // cbNaturalidadeUF = new JComboBox<>(new DefaultComboBoxModel<>(
  // Address.BrasilUF.values()));
  // cbNaturalidadeUF.setSelectedIndex(-1);
  // sl_panelL3.putConstraint(SpringLayout.EAST, txtNaturalidade, -6,
  // SpringLayout.WEST, cbNaturalidadeUF);
  // sl_panelL3.putConstraint(SpringLayout.WEST, lblNaturalidadeUF, 0,
  // SpringLayout.WEST, cbNaturalidadeUF);
  // sl_panelL3.putConstraint(SpringLayout.NORTH, cbNaturalidadeUF, 2,
  // SpringLayout.SOUTH, lblNaturalidadeUF);
  // panelL3.add(cbNaturalidadeUF);
  //
  // final JLabel lblEscolaridade = new JLabel("Escolaridade:");
  // lblEscolaridade.setFont(lblEscolaridade.getFont().deriveFont(
  // lblEscolaridade.getFont().getStyle() | Font.BOLD));
  // sl_panelL3.putConstraint(SpringLayout.NORTH, lblEscolaridade, 0,
  // SpringLayout.NORTH, panelL3);
  // springLayout.putConstraint(SpringLayout.NORTH, lblEscolaridade, 8,
  // SpringLayout.NORTH, panelL3);
  // springLayout.putConstraint(SpringLayout.WEST, lblEscolaridade, 355,
  // SpringLayout.WEST, panelL3);
  // panelL3.add(lblEscolaridade);
  //
  // cbEscolaridade = new JComboBox<>(new DefaultComboBoxModel<>(
  // Escolaridade.values()));
  // cbEscolaridade.setSelectedIndex(-1);
  // sl_panelL3.putConstraint(SpringLayout.EAST, cbNaturalidadeUF, -6,
  // SpringLayout.WEST, cbEscolaridade);
  // sl_panelL3.putConstraint(SpringLayout.EAST, cbEscolaridade, 0,
  // SpringLayout.EAST, panelL3);
  // sl_panelL3.putConstraint(SpringLayout.WEST, lblEscolaridade, 0,
  // SpringLayout.WEST, cbEscolaridade);
  // sl_panelL3.putConstraint(SpringLayout.NORTH, cbEscolaridade, 2,
  // SpringLayout.SOUTH, lblEscolaridade);
  // springLayout.putConstraint(SpringLayout.NORTH, cbEscolaridade, 5,
  // SpringLayout.NORTH, panelL3);
  // springLayout.putConstraint(SpringLayout.WEST, cbEscolaridade, 424,
  // SpringLayout.WEST, panelL3);
  // panelL3.add(cbEscolaridade);
  // springLayout.putConstraint(SpringLayout.EAST, panelL2, -6,
  // SpringLayout.WEST, panelFoto);
  // springLayout.putConstraint(SpringLayout.EAST, panelL1, -6,
  // SpringLayout.WEST, panelFoto);
  //
  // lblId = new JLabel("-");
  // sl_panelL1.putConstraint(SpringLayout.NORTH, lblId, 2, SpringLayout.SOUTH,
  // lblTId);
  // sl_panelL1.putConstraint(SpringLayout.WEST, lblId, 0, SpringLayout.WEST,
  // lblTId);
  // sl_panelL1.putConstraint(SpringLayout.EAST, lblId, 0, SpringLayout.EAST,
  // lblTId);
  // panelL1.add(lblId);
  //
  // final JLabel lblTCodigo = new JLabel("C\u00F3digo:");
  // sl_panelL1.putConstraint(SpringLayout.EAST, lblTCodigo, 76,
  // SpringLayout.EAST, lblTId);
  // lblTCodigo.setFont(lblTCodigo.getFont().deriveFont(
  // lblTCodigo.getFont().getStyle() | Font.BOLD));
  // sl_panelL1.putConstraint(SpringLayout.WEST, lblTCodigo, 6,
  // SpringLayout.EAST, lblTId);
  // panelL1.add(lblTCodigo);
  //
  // lblCodigo = new JLabel("-");
  // sl_panelL1.putConstraint(SpringLayout.NORTH, lblCodigo, 2,
  // SpringLayout.SOUTH, lblTId);
  // sl_panelL1.putConstraint(SpringLayout.WEST, lblCodigo, 0,
  // SpringLayout.WEST, lblTCodigo);
  // sl_panelL1.putConstraint(SpringLayout.EAST, lblCodigo, 0,
  // SpringLayout.EAST, lblTCodigo);
  // panelL1.add(lblCodigo);
  //
  // final JLabel lblTCadastradoEm = new JLabel("Cadastrado Em:");
  // sl_panelL1.putConstraint(SpringLayout.EAST, lblTCadastradoEm, 126,
  // SpringLayout.EAST, lblTCodigo);
  // lblTCadastradoEm.setFont(lblTCadastradoEm.getFont().deriveFont(
  // lblTCadastradoEm.getFont().getStyle() | Font.BOLD));
  // sl_panelL1.putConstraint(SpringLayout.NORTH, lblTCadastradoEm, 0,
  // SpringLayout.NORTH, panelL1);
  // sl_panelL1.putConstraint(SpringLayout.WEST, lblTCadastradoEm, 6,
  // SpringLayout.EAST, lblTCodigo);
  // panelL1.add(lblTCadastradoEm);
  //
  // lblCadastradoEm = new JLabel("-");
  // sl_panelL1.putConstraint(SpringLayout.NORTH, lblCadastradoEm, 2,
  // SpringLayout.SOUTH, lblTCadastradoEm);
  // sl_panelL1.putConstraint(SpringLayout.WEST, lblCadastradoEm, 0,
  // SpringLayout.WEST, lblTCadastradoEm);
  // sl_panelL1.putConstraint(SpringLayout.EAST, lblCadastradoEm, 0,
  // SpringLayout.EAST, lblTCadastradoEm);
  // panelL1.add(lblCadastradoEm);
  //
  // final JLabel lblTAlteradoEm = new JLabel("Alterado Em:");
  // sl_panelL1.putConstraint(SpringLayout.NORTH, lblTAlteradoEm, 0,
  // SpringLayout.NORTH, panelL1);
  // sl_panelL1.putConstraint(SpringLayout.WEST, lblTAlteradoEm, 6,
  // SpringLayout.EAST, lblTCadastradoEm);
  // sl_panelL1.putConstraint(SpringLayout.EAST, lblTAlteradoEm, 126,
  // SpringLayout.EAST, lblTCadastradoEm);
  // lblTAlteradoEm.setFont(lblTAlteradoEm.getFont().deriveFont(
  // lblTAlteradoEm.getFont().getStyle() | Font.BOLD));
  // panelL1.add(lblTAlteradoEm);
  //
  // lblAlteradoEm = new JLabel("-");
  // sl_panelL1.putConstraint(SpringLayout.NORTH, lblAlteradoEm, 2,
  // SpringLayout.SOUTH, lblTAlteradoEm);
  // sl_panelL1.putConstraint(SpringLayout.WEST, lblAlteradoEm, 0,
  // SpringLayout.WEST, lblTAlteradoEm);
  // sl_panelL1.putConstraint(SpringLayout.EAST, lblAlteradoEm, 0,
  // SpringLayout.EAST, lblTAlteradoEm);
  // panelL1.add(lblAlteradoEm);
  //
  // final JLabel lblTAlteradoPor = new JLabel("Por:");
  // sl_panelL1.putConstraint(SpringLayout.NORTH, lblTAlteradoPor, 0,
  // SpringLayout.NORTH, panelL1);
  // sl_panelL1.putConstraint(SpringLayout.WEST, lblTAlteradoPor, 6,
  // SpringLayout.EAST, lblTAlteradoEm);
  // lblTAlteradoPor.setFont(lblTAlteradoPor.getFont().deriveFont(
  // lblTAlteradoPor.getFont().getStyle() | Font.BOLD));
  // panelL1.add(lblTAlteradoPor);
  //
  // lblAlteradoPor = new JLabel("-");
  // sl_panelL1.putConstraint(SpringLayout.NORTH, lblAlteradoPor, 2,
  // SpringLayout.SOUTH, lblTAlteradoPor);
  // sl_panelL1.putConstraint(SpringLayout.WEST, lblAlteradoPor, 0,
  // SpringLayout.WEST, lblTAlteradoPor);
  // panelL1.add(lblAlteradoPor);
  //
  // final JLabel lblTIdade = new JLabel("Idade:");
  // lblTIdade.setFont(lblTIdade.getFont().deriveFont(
  // lblTIdade.getFont().getStyle() | Font.BOLD));
  // sl_panelL1.putConstraint(SpringLayout.NORTH, lblTIdade, 0,
  // SpringLayout.NORTH, lblTId);
  // sl_panelL1.putConstraint(SpringLayout.EAST, lblTIdade, 0,
  // SpringLayout.EAST, panelL1);
  // panelL1.add(lblTIdade);
  //
  // lblIdade = new JLabel("-");
  // sl_panelL1.putConstraint(SpringLayout.NORTH, lblIdade, 2,
  // SpringLayout.SOUTH, lblTIdade);
  // sl_panelL1.putConstraint(SpringLayout.EAST, lblIdade, 0, SpringLayout.EAST,
  // lblTIdade);
  // panelL1.add(lblIdade);
  //
  // final JPanel panelL4 = new JPanel();
  // springLayout.putConstraint(SpringLayout.NORTH, panelL4, 0,
  // SpringLayout.SOUTH, panelL3);
  // springLayout.putConstraint(SpringLayout.WEST, panelL4, 0,
  // SpringLayout.WEST, this);
  // springLayout.putConstraint(SpringLayout.SOUTH, panelL4, 50,
  // SpringLayout.SOUTH, panelL3);
  // springLayout.putConstraint(SpringLayout.EAST, panelL4, -6,
  // SpringLayout.WEST, panelFoto);
  // add(panelL4);
  // final SpringLayout sl_panelL4 = new SpringLayout();
  // panelL4.setLayout(sl_panelL4);
  //
  // final JLabel lblEstadoCivil = new JLabel("Estado Civil:");
  // lblEstadoCivil.setFont(lblEstadoCivil.getFont().deriveFont(
  // lblEstadoCivil.getFont().getStyle() | Font.BOLD));
  // panelL4.add(lblEstadoCivil);
  //
  // cbEstadoCivil = new JComboBox<>(new DefaultComboBoxModel<>(
  // EstadoCivil.values()));
  // cbEstadoCivil.setSelectedIndex(-1);
  // sl_panelL4.putConstraint(SpringLayout.NORTH, cbEstadoCivil, 2,
  // SpringLayout.SOUTH, lblEstadoCivil);
  // sl_panelL4.putConstraint(SpringLayout.WEST, cbEstadoCivil, 0,
  // SpringLayout.WEST, panelL4);
  // panelL4.add(cbEstadoCivil);
  //
  // final JLabel lblNomeConjuge = new JLabel("Nome do(a) C\u00F4njuge:");
  // lblNomeConjuge.setFont(lblNomeConjuge.getFont().deriveFont(
  // lblNomeConjuge.getFont().getStyle() | Font.BOLD));
  // sl_panelL4.putConstraint(SpringLayout.NORTH, lblNomeConjuge, 0,
  // SpringLayout.NORTH, panelL4);
  // panelL4.add(lblNomeConjuge);
  //
  // txtNomeConjuge = new JTextField();
  // sl_panelL4.putConstraint(SpringLayout.WEST, lblNomeConjuge, 0,
  // SpringLayout.WEST, txtNomeConjuge);
  // sl_panelL4.putConstraint(SpringLayout.NORTH, txtNomeConjuge, 2,
  // SpringLayout.SOUTH, lblNomeConjuge);
  // sl_panelL4.putConstraint(SpringLayout.WEST, txtNomeConjuge, 6,
  // SpringLayout.EAST, cbEstadoCivil);
  // panelL4.add(txtNomeConjuge);
  // txtNomeConjuge.setColumns(10);
  //
  // final JLabel lblNomeDaMae = new JLabel("Nome da M\u00E3e:");
  // lblNomeDaMae.setFont(lblNomeDaMae.getFont().deriveFont(
  // lblNomeDaMae.getFont().getStyle() | Font.BOLD));
  // sl_panelL4.putConstraint(SpringLayout.NORTH, lblNomeDaMae, 0,
  // SpringLayout.NORTH, panelL4);
  // panelL4.add(lblNomeDaMae);
  //
  // txtNomeDaMae = new JTextField();
  // sl_panelL4.putConstraint(SpringLayout.EAST, txtNomeConjuge, -6,
  // SpringLayout.WEST, txtNomeDaMae);
  // sl_panelL4.putConstraint(SpringLayout.WEST, lblNomeDaMae, 0,
  // SpringLayout.WEST, txtNomeDaMae);
  // sl_panelL4.putConstraint(SpringLayout.NORTH, txtNomeDaMae, 2,
  // SpringLayout.SOUTH, lblNomeDaMae);
  // panelL4.add(txtNomeDaMae);
  // txtNomeDaMae.setColumns(10);
  // final JLabel lblNomeDoPai = new JLabel("Nome do Pai:");
  // lblNomeDoPai.setFont(lblNomeDoPai.getFont().deriveFont(
  // lblNomeDoPai.getFont().getStyle() | Font.BOLD));
  // sl_panelL4.putConstraint(SpringLayout.NORTH, lblNomeDoPai, 0,
  // SpringLayout.NORTH, panelL4);
  // springLayout.putConstraint(SpringLayout.WEST, lblNomeDoPai, 6,
  // SpringLayout.EAST, lblNomeDaMae);
  // panelL4.add(lblNomeDoPai);
  //
  // txtNomeDoPai = new JTextField();
  // sl_panelL4.putConstraint(SpringLayout.WEST, txtNomeDaMae, -156,
  // SpringLayout.WEST, txtNomeDoPai);
  // sl_panelL4.putConstraint(SpringLayout.EAST, txtNomeDaMae, -6,
  // SpringLayout.WEST, txtNomeDoPai);
  // sl_panelL4.putConstraint(SpringLayout.WEST, lblNomeDoPai, 0,
  // SpringLayout.WEST, txtNomeDoPai);
  // sl_panelL4.putConstraint(SpringLayout.NORTH, txtNomeDoPai, 2,
  // SpringLayout.SOUTH, lblNomeDoPai);
  // sl_panelL4.putConstraint(SpringLayout.WEST, txtNomeDoPai, -150,
  // SpringLayout.EAST, panelL4);
  // sl_panelL4.putConstraint(SpringLayout.EAST, txtNomeDoPai, 0,
  // SpringLayout.EAST, panelL4);
  // panelL4.add(txtNomeDoPai);
  // txtNomeDoPai.setColumns(10);
  //
  // final JPanel panelL5 = new JPanel();
  // springLayout.putConstraint(SpringLayout.NORTH, panelL5, 0,
  // SpringLayout.SOUTH, panelL4);
  // springLayout.putConstraint(SpringLayout.WEST, panelL5, 0,
  // SpringLayout.WEST, this);
  // springLayout.putConstraint(SpringLayout.SOUTH, panelL5, 50,
  // SpringLayout.SOUTH, panelL4);
  // springLayout.putConstraint(SpringLayout.EAST, panelL5, -6,
  // SpringLayout.WEST, panelFoto);
  // add(panelL5);
  // final SpringLayout sl_panelL5 = new SpringLayout();
  // panelL5.setLayout(sl_panelL5);
  //
  // final JLabel lblResidenciaCEP = new JLabel("CEP:");
  // lblResidenciaCEP.setFont(lblResidenciaCEP.getFont().deriveFont(
  // lblResidenciaCEP.getFont().getStyle() | Font.BOLD));
  // sl_panelL5.putConstraint(SpringLayout.NORTH, lblResidenciaCEP, 0,
  // SpringLayout.NORTH, panelL5);
  // springLayout.putConstraint(SpringLayout.NORTH, lblResidenciaCEP, 5,
  // SpringLayout.NORTH, panelL5);
  // springLayout.putConstraint(SpringLayout.WEST, lblResidenciaCEP, 5,
  // SpringLayout.WEST, panelL5);
  // panelL5.add(lblResidenciaCEP);
  // springLayout.putConstraint(SpringLayout.EAST, lblResidenciaCEP, -406,
  // SpringLayout.WEST, panelFoto);
  //
  // txtResidenciaCEP = new JTextField();
  // sl_panelL5.putConstraint(SpringLayout.EAST, txtResidenciaCEP, 80,
  // SpringLayout.WEST, panelL5);
  // sl_panelL5.putConstraint(SpringLayout.WEST, lblResidenciaCEP, 0,
  // SpringLayout.WEST, txtResidenciaCEP);
  // sl_panelL5.putConstraint(SpringLayout.NORTH, txtResidenciaCEP, 2,
  // SpringLayout.SOUTH, lblResidenciaCEP);
  // sl_panelL5.putConstraint(SpringLayout.WEST, txtResidenciaCEP, 0,
  // SpringLayout.WEST, panelL5);
  // panelL5.add(txtResidenciaCEP);
  // txtResidenciaCEP.setColumns(10);
  //
  // final JLabel lblResidenciaEndereco = new JLabel("Endere\u00E7o:");
  // lblResidenciaEndereco.setFont(lblResidenciaEndereco.getFont().deriveFont(
  // lblResidenciaEndereco.getFont().getStyle() | Font.BOLD));
  // sl_panelL5.putConstraint(SpringLayout.NORTH, lblResidenciaEndereco, 0,
  // SpringLayout.NORTH, panelL5);
  // springLayout.putConstraint(SpringLayout.NORTH, lblResidenciaEndereco, 5,
  // SpringLayout.NORTH, panelL5);
  // springLayout.putConstraint(SpringLayout.WEST, lblResidenciaEndereco, 33,
  // SpringLayout.WEST, panelL5);
  // panelL5.add(lblResidenciaEndereco);
  //
  // txtResidenciaEndereco = new JTextField();
  // sl_panelL5.putConstraint(SpringLayout.WEST, txtResidenciaEndereco, 6,
  // SpringLayout.EAST, txtResidenciaCEP);
  // sl_panelL5.putConstraint(SpringLayout.WEST, lblResidenciaEndereco, 0,
  // SpringLayout.WEST, txtResidenciaEndereco);
  // sl_panelL5.putConstraint(SpringLayout.NORTH, txtResidenciaEndereco, 2,
  // SpringLayout.SOUTH, lblResidenciaEndereco);
  // panelL5.add(txtResidenciaEndereco);
  // txtResidenciaEndereco.setColumns(10);
  //
  // final JLabel lblResidenciaBairro = new JLabel("Bairro:");
  // lblResidenciaBairro.setFont(lblResidenciaBairro.getFont().deriveFont(
  // lblResidenciaBairro.getFont().getStyle() | Font.BOLD));
  // sl_panelL5.putConstraint(SpringLayout.NORTH, lblResidenciaBairro, 0,
  // SpringLayout.NORTH, panelL5);
  // springLayout.putConstraint(SpringLayout.NORTH, lblResidenciaBairro, 5,
  // SpringLayout.NORTH, panelL5);
  // springLayout.putConstraint(SpringLayout.WEST, lblResidenciaBairro, 87,
  // SpringLayout.WEST, panelL5);
  // panelL5.add(lblResidenciaBairro);
  //
  // txtResidenciaBairro = new JTextField();
  // sl_panelL5.putConstraint(SpringLayout.EAST, txtResidenciaEndereco, -6,
  // SpringLayout.WEST, txtResidenciaBairro);
  // sl_panelL5.putConstraint(SpringLayout.WEST, lblResidenciaBairro, 0,
  // SpringLayout.WEST, txtResidenciaBairro);
  // sl_panelL5.putConstraint(SpringLayout.NORTH, txtResidenciaBairro, 2,
  // SpringLayout.SOUTH, lblResidenciaBairro);
  // panelL5.add(txtResidenciaBairro);
  // txtResidenciaBairro.setColumns(10);
  //
  // final JLabel lblResidenciaCidade = new JLabel("Cidade:");
  // lblResidenciaCidade.setFont(lblResidenciaCidade.getFont().deriveFont(
  // lblResidenciaCidade.getFont().getStyle() | Font.BOLD));
  // sl_panelL5.putConstraint(SpringLayout.NORTH, lblResidenciaCidade, 0,
  // SpringLayout.NORTH, panelL5);
  // springLayout.putConstraint(SpringLayout.NORTH, lblResidenciaCidade, 5,
  // SpringLayout.NORTH, panelL5);
  // springLayout.putConstraint(SpringLayout.WEST, lblResidenciaCidade, 124,
  // SpringLayout.WEST, panelL5);
  // panelL5.add(lblResidenciaCidade);
  //
  // txtResidenciaCidade = new JTextField();
  // sl_panelL5.putConstraint(SpringLayout.WEST, txtResidenciaBairro, -156,
  // SpringLayout.WEST, txtResidenciaCidade);
  // sl_panelL5.putConstraint(SpringLayout.EAST, txtResidenciaBairro, -6,
  // SpringLayout.WEST, txtResidenciaCidade);
  // sl_panelL5.putConstraint(SpringLayout.WEST, lblResidenciaCidade, 0,
  // SpringLayout.WEST, txtResidenciaCidade);
  // sl_panelL5.putConstraint(SpringLayout.NORTH, txtResidenciaCidade, 2,
  // SpringLayout.SOUTH, lblResidenciaCidade);
  // panelL5.add(txtResidenciaCidade);
  // txtResidenciaCidade.setColumns(10);
  //
  // final JLabel lblResidenciaUF = new JLabel("UF:");
  // lblResidenciaUF.setFont(lblResidenciaUF.getFont().deriveFont(
  // lblResidenciaUF.getFont().getStyle() | Font.BOLD));
  // sl_panelL5.putConstraint(SpringLayout.NORTH, lblResidenciaUF, 0,
  // SpringLayout.NORTH, panelL5);
  // springLayout.putConstraint(SpringLayout.NORTH, lblResidenciaUF, 5,
  // SpringLayout.NORTH, panelL5);
  // springLayout.putConstraint(SpringLayout.WEST, lblResidenciaUF, 166,
  // SpringLayout.WEST, panelL5);
  // panelL5.add(lblResidenciaUF);
  // springLayout.putConstraint(SpringLayout.SOUTH, lblResidenciaUF, 0,
  // SpringLayout.SOUTH, lblResidenciaCEP);
  // springLayout.putConstraint(SpringLayout.EAST, lblResidenciaCidade, -19,
  // SpringLayout.WEST, lblResidenciaUF);
  //
  // cbResidenciaUF = new JComboBox<>(new DefaultComboBoxModel<>(
  // Address.BrasilUF.values()));
  // sl_panelL5.putConstraint(SpringLayout.WEST, txtResidenciaCidade, -156,
  // SpringLayout.WEST, cbResidenciaUF);
  // cbResidenciaUF.setSelectedIndex(-1);
  // sl_panelL5.putConstraint(SpringLayout.EAST, txtResidenciaCidade, -6,
  // SpringLayout.WEST, cbResidenciaUF);
  // sl_panelL5.putConstraint(SpringLayout.WEST, lblResidenciaUF, 0,
  // SpringLayout.WEST, cbResidenciaUF);
  // sl_panelL5.putConstraint(SpringLayout.NORTH, cbResidenciaUF, 2,
  // SpringLayout.SOUTH, lblResidenciaUF);
  // sl_panelL5.putConstraint(SpringLayout.EAST, cbResidenciaUF, 0,
  // SpringLayout.EAST, panelL5);
  // panelL5.add(cbResidenciaUF);
  // panelFoto.setBorder(new LineBorder(new Color(0, 0, 0)));
  // springLayout.putConstraint(SpringLayout.NORTH, panelFoto, 0,
  // SpringLayout.NORTH, this);
  // springLayout.putConstraint(SpringLayout.WEST, panelFoto, -150,
  // SpringLayout.EAST, this);
  // springLayout.putConstraint(SpringLayout.SOUTH, panelFoto, 200,
  // SpringLayout.NORTH, this);
  // springLayout.putConstraint(SpringLayout.EAST, panelFoto, 0,
  // SpringLayout.EAST, this);
  // add(panelFoto);
  //
  // final JPanel panelTelefones = new JPanel();
  // springLayout.putConstraint(SpringLayout.NORTH, panelTelefones, 0,
  // SpringLayout.SOUTH, panelL5);
  // springLayout.putConstraint(SpringLayout.WEST, panelTelefones, 0,
  // SpringLayout.WEST, this);
  // springLayout.putConstraint(SpringLayout.SOUTH, panelTelefones, 100,
  // SpringLayout.SOUTH, panelL5);
  // springLayout.putConstraint(SpringLayout.EAST, panelTelefones, 300,
  // SpringLayout.WEST, this);
  // add(panelTelefones);
  // panelTelefones.setLayout(new BorderLayout(0, 0));
  //
  // final JLabel lblTelefones = new JLabel("Telefones:");
  // lblTelefones.setFont(lblTelefones.getFont().deriveFont(
  // lblTelefones.getFont().getStyle() | Font.BOLD));
  // panelTelefones.add(lblTelefones, BorderLayout.NORTH);
  //
  // final JPanel panelFilhos = new JPanel();
  // springLayout.putConstraint(SpringLayout.NORTH, panelFilhos, 0,
  // SpringLayout.NORTH, panelTelefones);
  // springLayout.putConstraint(SpringLayout.WEST, panelFilhos, 6,
  // SpringLayout.EAST, panelTelefones);
  // springLayout.putConstraint(SpringLayout.SOUTH, panelFilhos, 0,
  // SpringLayout.SOUTH, panelTelefones);
  // springLayout.putConstraint(SpringLayout.EAST, panelFilhos, 0,
  // SpringLayout.EAST, this);
  // add(panelFilhos);
  // panelFilhos.setLayout(new BorderLayout(0, 0));
  //
  // final JLabel lblFilhos = new JLabel("<html><b>Filhos:</b> (um por linha)");
  // panelFilhos.add(lblFilhos, BorderLayout.NORTH);
  //
  // final JScrollPane scrollFilhos = new JScrollPane();
  // panelFilhos.add(scrollFilhos, BorderLayout.CENTER);
  //
  // txtFilhos = new JTextArea();
  // scrollFilhos.setViewportView(txtFilhos);
  //
  // final JScrollPane scrollTelefones = new JScrollPane();
  // panelTelefones.add(scrollTelefones, BorderLayout.CENTER);
  //
  // telefonesTable = new TelephonesTable();
  // scrollTelefones.setViewportView(telefonesTable);
  //
  // final JPanel panelObservacoes = new JPanel();
  // springLayout.putConstraint(SpringLayout.NORTH, panelObservacoes, 10,
  // SpringLayout.SOUTH, panelTelefones);
  // springLayout.putConstraint(SpringLayout.WEST, panelObservacoes, 0,
  // SpringLayout.WEST, this);
  // springLayout.putConstraint(SpringLayout.EAST, panelObservacoes, 0,
  // SpringLayout.EAST, this);
  // add(panelObservacoes);
  // panelObservacoes.setLayout(new BorderLayout(0, 0));
  //
  // final JLabel lblObservacoes = new JLabel("Observa\u00E7\u00F5es:");
  // lblObservacoes.setFont(lblObservacoes.getFont().deriveFont(
  // lblObservacoes.getFont().getStyle() | Font.BOLD));
  // panelObservacoes.add(lblObservacoes, BorderLayout.NORTH);
  //
  // final JScrollPane scrollObservacoes = new JScrollPane();
  // panelObservacoes.add(scrollObservacoes, BorderLayout.CENTER);
  //
  // txtObservacoes = new JTextArea();
  // scrollObservacoes.setViewportView(txtObservacoes);
  //
  // final JPanel panelButtons = new JPanel();
  // springLayout.putConstraint(SpringLayout.SOUTH, panelObservacoes, -6,
  // SpringLayout.NORTH, panelButtons);
  // springLayout.putConstraint(SpringLayout.WEST, panelButtons, 0,
  // SpringLayout.WEST, this);
  // springLayout.putConstraint(SpringLayout.SOUTH, panelButtons, 0,
  // SpringLayout.SOUTH, this);
  // springLayout.putConstraint(SpringLayout.EAST, panelButtons, 0,
  // SpringLayout.EAST, this);
  // add(panelButtons);
  // panelButtons.setLayout(new BorderLayout(0, 0));
  //
  // final JPanel panelButtonsGrid = new JPanel();
  // panelButtons.add(panelButtonsGrid, BorderLayout.EAST);
  // panelButtonsGrid.setLayout(new GridLayout(1, 0, 0, 0));
  //
  // final JButton btnNewButton = new JButton("New button");
  // panelButtonsGrid.add(btnNewButton);
  // }
  //
  // public void setDados(final LocalDateTime cadastrado,
  // final FuncionarioPessoal f) {
  // final boolean n = f == null;
  // lblId.setText(n ? "-" : String.valueOf(f.id));
  // lblCodigo.setText(n ? "-" : "F#" + f.idfuncionario);
  // lblCadastradoEm.setText(n ? "-" : cadastrado.toString());
  // lblAlteradoEm.setText(n ? "-" : f.alteradoEm.toString());
  // lblAlteradoPor.setText(n ? "-" : Client.cache().users().get(f.alteradoPor)
  // .getName());
  // lblIdade.setText(n ? "-" : f.calcularIdade() + " anos");
  //
  // txtNome.setText(n ? "" : f.nome);
  // txtApelido.setText(n ? "" : f.apelido);
  // rbSexoMasculino.setSelected(n ? false : f.sexo == 'M');
  // rbSexoFeminino.setSelected(n ? false : f.sexo == 'F');
  //
  // txtDataDeNascimento.setText(n ? "" : f.nascimento.toString());
  // txtNacionalidade.setText(n ? "" : f.nacionalidade);
  // txtNaturalidade.setText(n ? "" : f.naturalidade_cidade);
  // cbNaturalidadeUF.setSelectedItem(n ? null : f.naturalidade_uf);
  // cbEscolaridade.setSelectedItem(n ? null : f.escolaridade);
  //
  // cbEstadoCivil.setSelectedItem(n ? null : f.estadoCivil);
  // txtNomeConjuge.setText(n ? "" : f.nomeConjuge);
  // txtNomeDaMae.setText(n ? "" : f.nomeMae);
  // txtNomeDoPai.setText(n ? "" : f.nomePai);
  //
  // txtResidenciaCEP.setText(n ? "" : f.residenciaCEP.toString());
  // txtResidenciaEndereco.setText(n ? "" : f.residenciaEndereco);
  // txtResidenciaBairro.setText(n ? "" : f.residenciaBairro);
  // txtResidenciaCidade.setText(n ? "" : f.residenciaCidade);
  // cbResidenciaUF.setSelectedItem(n ? null : f.residenciaUF);
  //
  // // telefonesTable.getModel().setTelefones(n ? null : f.telefones);
  // txtFilhos.setText(n ? "" : f.filhos);
  //
  // txtObservacoes.setText(n ? "" : f.observacoes);
  // }
}
