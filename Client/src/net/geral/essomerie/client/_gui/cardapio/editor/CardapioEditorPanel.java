package net.geral.essomerie.client._gui.cardapio.editor;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import net.geral.essomerie.client._gui.cardapio.editor.categorias_tree.CategoriasTreeController;
import net.geral.essomerie.client._gui.cardapio.editor.navegacao_tree.NavegacaoTreeController;
import net.geral.essomerie.client.gui.main.TabPanel;
import net.geral.essomerie._shared.cardapio.Cardapio;
import net.geral.essomerie._shared.cardapio.CardapioCategoria;

public class CardapioEditorPanel extends TabPanel implements ItemListener {
	private static final long					serialVersionUID	= 1L;
	private final JTextField					txtNomeCompleto;
	private final JTextField					txtDescricaoPortugues;
	private final JTextField					txtDescricaoIngles;
	private final JTextField					txtCodigo;
	private final JTextField					txtInteira;
	private final JTextField					txtMeia;
	private final JTextField					txtCodigoTeste;
	private final JTextField					txtNomeGrupo;
	private final JTextField					txtId;
	private final ButtonGroup					buttonGroupRadios	= new ButtonGroup();
	private final JRadioButton					rdbtnAtual;
	private final JRadioButton					rdbtnNovo;
	private final Cardapio						editando			= new Cardapio();

	private final JComboBox<CardapioCategoria>	cbCategoria;
	private final JButton						btnLimpar;
	private final JButton						btnPublicar;
	private final JButton						btnNovoItem;
	private final JButton						btnNovaCategoria;
	private final JButton						btnCarregar;
	private final JButton						btnSalvar;
	private final CategoriasTreeController		categorias			= new CategoriasTreeController(editando);

	private final NavegacaoTreeController		navegacao			= new NavegacaoTreeController(editando);
	private final JTextField					txtNomeCardapio;

	public CardapioEditorPanel() {
		final SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);

		final JPanel panelCodigo = new JPanel();
		springLayout.putConstraint(SpringLayout.NORTH, panelCodigo, 10, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, panelCodigo, 10, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, panelCodigo, -10, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, panelCodigo, 230, SpringLayout.WEST, this);
		add(panelCodigo);
		panelCodigo.setLayout(new BorderLayout(0, 0));

		final JLabel lblNavegaoPorCdigo = new JLabel("Navega\u00E7\u00E3o por C\u00F3digo:");
		lblNavegaoPorCdigo.setFont(lblNavegaoPorCdigo.getFont().deriveFont(
				lblNavegaoPorCdigo.getFont().getStyle() | Font.BOLD));
		panelCodigo.add(lblNavegaoPorCdigo, BorderLayout.NORTH);

		final JScrollPane scrollCodigo = new JScrollPane();
		panelCodigo.add(scrollCodigo, BorderLayout.CENTER);

		scrollCodigo.setViewportView(navegacao.tree);

		final JPanel panelCategoria = new JPanel();
		springLayout.putConstraint(SpringLayout.NORTH, panelCategoria, 0, SpringLayout.NORTH, panelCodigo);
		springLayout.putConstraint(SpringLayout.WEST, panelCategoria, 10, SpringLayout.EAST, panelCodigo);
		springLayout.putConstraint(SpringLayout.SOUTH, panelCategoria, 0, SpringLayout.SOUTH, panelCodigo);
		springLayout.putConstraint(SpringLayout.EAST, panelCategoria, 260, SpringLayout.EAST, panelCodigo);

		txtCodigoTeste = new JTextField();
		panelCodigo.add(txtCodigoTeste, BorderLayout.SOUTH);
		txtCodigoTeste.setColumns(10);
		add(panelCategoria);
		panelCategoria.setLayout(new BorderLayout(0, 0));

		final JLabel lblNavegaoPorCategoria = new JLabel("Navega\u00E7\u00E3o por Categoria:");
		lblNavegaoPorCategoria.setFont(lblNavegaoPorCategoria.getFont().deriveFont(
				lblNavegaoPorCategoria.getFont().getStyle() | Font.BOLD));
		panelCategoria.add(lblNavegaoPorCategoria, BorderLayout.NORTH);

		final JScrollPane scrollCategoria = new JScrollPane();
		panelCategoria.add(scrollCategoria, BorderLayout.CENTER);

		scrollCategoria.setViewportView(categorias.tree);

		final JPanel panel = new JPanel();
		panelCategoria.add(panel, BorderLayout.SOUTH);
		panel.setLayout(new GridLayout(0, 2, 2, 0));

		btnNovaCategoria = new JButton("Nova Categoria");
		panel.add(btnNovaCategoria);

		btnNovoItem = new JButton("Novo Item");
		panel.add(btnNovoItem);

		final JLabel lblCardapio = new JLabel("Card\u00E1pio:");
		springLayout.putConstraint(SpringLayout.NORTH, lblCardapio, 10, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, lblCardapio, 10, SpringLayout.EAST, panelCategoria);
		springLayout.putConstraint(SpringLayout.EAST, lblCardapio, -10, SpringLayout.EAST, this);
		lblCardapio.setFont(lblCardapio.getFont().deriveFont(lblCardapio.getFont().getStyle() | Font.BOLD));
		add(lblCardapio);

		final JPanel panelCardapio = new JPanel();
		panelCardapio.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		springLayout.putConstraint(SpringLayout.NORTH, panelCardapio, 0, SpringLayout.SOUTH, lblCardapio);
		springLayout.putConstraint(SpringLayout.WEST, panelCardapio, 0, SpringLayout.WEST, lblCardapio);
		springLayout.putConstraint(SpringLayout.EAST, panelCardapio, 0, SpringLayout.EAST, lblCardapio);
		add(panelCardapio);

		final JLabel lblEditor = new JLabel("Informa\u00E7\u00F5es:");
		springLayout.putConstraint(SpringLayout.NORTH, lblEditor, 10, SpringLayout.SOUTH, panelCardapio);
		springLayout.putConstraint(SpringLayout.WEST, lblEditor, 0, SpringLayout.WEST, panelCardapio);
		springLayout.putConstraint(SpringLayout.EAST, lblEditor, 0, SpringLayout.EAST, panelCardapio);
		panelCardapio.setLayout(new BorderLayout(0, 0));

		final JPanel panelCardapioRadios = new JPanel();
		panelCardapioRadios.setBorder(new EmptyBorder(0, 10, 0, 0));
		panelCardapio.add(panelCardapioRadios, BorderLayout.WEST);
		panelCardapioRadios.setLayout(new GridLayout(1, 0, 5, 0));

		rdbtnAtual = new JRadioButton("Atual");
		rdbtnAtual.setSelected(true);
		rdbtnAtual.addItemListener(this);
		buttonGroupRadios.add(rdbtnAtual);
		panelCardapioRadios.add(rdbtnAtual);

		rdbtnNovo = new JRadioButton("Novo");
		rdbtnNovo.addItemListener(this);
		buttonGroupRadios.add(rdbtnNovo);
		panelCardapioRadios.add(rdbtnNovo);

		final JPanel panelCardapioButtons = new JPanel();
		panelCardapio.add(panelCardapioButtons, BorderLayout.EAST);
		panelCardapioButtons.setLayout(new GridLayout(1, 0, 0, 0));

		btnLimpar = new JButton("Limpar");
		btnLimpar.setActionCommand("limpar");
		panelCardapioButtons.add(btnLimpar);

		btnPublicar = new JButton("Publicar");
		btnPublicar.setActionCommand("publicar");
		panelCardapioButtons.add(btnPublicar);

		btnCarregar = new JButton("Carregar");
		panelCardapioButtons.add(btnCarregar);

		btnSalvar = new JButton("Salvar");
		panelCardapioButtons.add(btnSalvar);
		lblEditor.setFont(lblEditor.getFont().deriveFont(lblEditor.getFont().getStyle() | Font.BOLD));
		add(lblEditor);

		final JPanel panelEditor = new JPanel();
		springLayout.putConstraint(SpringLayout.NORTH, panelEditor, 0, SpringLayout.SOUTH, lblEditor);
		springLayout.putConstraint(SpringLayout.WEST, panelEditor, 0, SpringLayout.WEST, lblEditor);
		springLayout.putConstraint(SpringLayout.EAST, panelEditor, 0, SpringLayout.EAST, lblEditor);
		panelEditor.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		springLayout.putConstraint(SpringLayout.SOUTH, panelEditor, -10, SpringLayout.SOUTH, this);
		add(panelEditor);
		final SpringLayout sl_panelEditor = new SpringLayout();
		panelEditor.setLayout(sl_panelEditor);

		final JLabel lblId = new JLabel("ID#:");
		sl_panelEditor.putConstraint(SpringLayout.NORTH, lblId, 10, SpringLayout.NORTH, panelEditor);
		sl_panelEditor.putConstraint(SpringLayout.WEST, lblId, 10, SpringLayout.WEST, panelEditor);
		sl_panelEditor.putConstraint(SpringLayout.EAST, lblId, 50, SpringLayout.WEST, panelEditor);
		panelEditor.add(lblId);

		txtId = new JTextField();
		txtId.setEnabled(false);
		txtId.setEditable(false);
		sl_panelEditor.putConstraint(SpringLayout.NORTH, txtId, 2, SpringLayout.SOUTH, lblId);
		sl_panelEditor.putConstraint(SpringLayout.WEST, txtId, 0, SpringLayout.WEST, lblId);
		sl_panelEditor.putConstraint(SpringLayout.EAST, txtId, 0, SpringLayout.EAST, lblId);
		panelEditor.add(txtId);
		txtId.setColumns(10);

		final JLabel lblCategoria = new JLabel("Categoria:");
		sl_panelEditor.putConstraint(SpringLayout.NORTH, lblCategoria, 0, SpringLayout.NORTH, lblId);
		sl_panelEditor.putConstraint(SpringLayout.WEST, lblCategoria, 10, SpringLayout.EAST, lblId);
		sl_panelEditor.putConstraint(SpringLayout.EAST, lblCategoria, 160, SpringLayout.EAST, lblId);
		panelEditor.add(lblCategoria);

		cbCategoria = new JComboBox<CardapioCategoria>();
		sl_panelEditor.putConstraint(SpringLayout.NORTH, cbCategoria, 2, SpringLayout.SOUTH, lblCategoria);
		sl_panelEditor.putConstraint(SpringLayout.WEST, cbCategoria, 0, SpringLayout.WEST, lblCategoria);
		sl_panelEditor.putConstraint(SpringLayout.EAST, cbCategoria, 0, SpringLayout.EAST, lblCategoria);
		panelEditor.add(cbCategoria);

		final JLabel lblCodigo = new JLabel("C\u00F3digo:");
		sl_panelEditor.putConstraint(SpringLayout.NORTH, lblCodigo, 0, SpringLayout.NORTH, lblCategoria);
		sl_panelEditor.putConstraint(SpringLayout.WEST, lblCodigo, 10, SpringLayout.EAST, lblCategoria);
		sl_panelEditor.putConstraint(SpringLayout.EAST, lblCodigo, 80, SpringLayout.EAST, lblCategoria);
		panelEditor.add(lblCodigo);

		txtCodigo = new JTextField();
		sl_panelEditor.putConstraint(SpringLayout.NORTH, txtCodigo, 2, SpringLayout.SOUTH, lblCodigo);
		sl_panelEditor.putConstraint(SpringLayout.WEST, txtCodigo, 0, SpringLayout.WEST, lblCodigo);
		sl_panelEditor.putConstraint(SpringLayout.EAST, txtCodigo, 0, SpringLayout.EAST, lblCodigo);
		txtCodigo.setColumns(10);
		panelEditor.add(txtCodigo);

		final JLabel lblInteira = new JLabel("$ Inteira:");
		sl_panelEditor.putConstraint(SpringLayout.NORTH, lblInteira, 0, SpringLayout.NORTH, lblCodigo);
		sl_panelEditor.putConstraint(SpringLayout.WEST, lblInteira, 10, SpringLayout.EAST, lblCodigo);
		sl_panelEditor.putConstraint(SpringLayout.EAST, lblInteira, 80, SpringLayout.EAST, lblCodigo);
		panelEditor.add(lblInteira);

		txtInteira = new JTextField();
		sl_panelEditor.putConstraint(SpringLayout.NORTH, txtInteira, 2, SpringLayout.SOUTH, lblInteira);
		sl_panelEditor.putConstraint(SpringLayout.WEST, txtInteira, 0, SpringLayout.WEST, lblInteira);
		sl_panelEditor.putConstraint(SpringLayout.EAST, txtInteira, 0, SpringLayout.EAST, lblInteira);
		txtInteira.setColumns(10);
		panelEditor.add(txtInteira);

		final JLabel lblMeia = new JLabel("$ Meia:");
		sl_panelEditor.putConstraint(SpringLayout.NORTH, lblMeia, 0, SpringLayout.NORTH, lblInteira);
		sl_panelEditor.putConstraint(SpringLayout.WEST, lblMeia, 10, SpringLayout.EAST, lblInteira);
		sl_panelEditor.putConstraint(SpringLayout.EAST, lblMeia, 80, SpringLayout.EAST, lblInteira);
		panelEditor.add(lblMeia);

		txtMeia = new JTextField();
		sl_panelEditor.putConstraint(SpringLayout.NORTH, txtMeia, 2, SpringLayout.SOUTH, lblMeia);
		sl_panelEditor.putConstraint(SpringLayout.WEST, txtMeia, 0, SpringLayout.WEST, lblMeia);
		sl_panelEditor.putConstraint(SpringLayout.EAST, txtMeia, 0, SpringLayout.EAST, lblMeia);
		txtMeia.setColumns(10);
		panelEditor.add(txtMeia);

		final JLabel lblNomeCompleto = new JLabel("Nome:");
		sl_panelEditor.putConstraint(SpringLayout.NORTH, lblNomeCompleto, 10, SpringLayout.SOUTH, txtId);
		sl_panelEditor.putConstraint(SpringLayout.WEST, lblNomeCompleto, 0, SpringLayout.WEST, lblId);
		sl_panelEditor.putConstraint(SpringLayout.EAST, lblNomeCompleto, -10, SpringLayout.EAST, panelEditor);
		panelEditor.add(lblNomeCompleto);

		txtNomeCompleto = new JTextField();
		sl_panelEditor.putConstraint(SpringLayout.NORTH, txtNomeCompleto, 2, SpringLayout.SOUTH, lblNomeCompleto);
		sl_panelEditor.putConstraint(SpringLayout.WEST, txtNomeCompleto, 0, SpringLayout.WEST, lblNomeCompleto);
		sl_panelEditor.putConstraint(SpringLayout.EAST, txtNomeCompleto, 2, SpringLayout.EAST, lblNomeCompleto);
		panelEditor.add(txtNomeCompleto);
		txtNomeCompleto.setColumns(10);

		final JLabel lblNomeGrupo = new JLabel("Nome do grupo de navega\u00E7\u00E3o (pode ser em branco):");
		sl_panelEditor.putConstraint(SpringLayout.NORTH, lblNomeGrupo, 10, SpringLayout.SOUTH, txtNomeCompleto);
		sl_panelEditor.putConstraint(SpringLayout.WEST, lblNomeGrupo, 10, SpringLayout.WEST, panelEditor);
		sl_panelEditor.putConstraint(SpringLayout.EAST, lblNomeGrupo, -10, SpringLayout.EAST, panelEditor);
		panelEditor.add(lblNomeGrupo);

		txtNomeGrupo = new JTextField();
		sl_panelEditor.putConstraint(SpringLayout.NORTH, txtNomeGrupo, 2, SpringLayout.SOUTH, lblNomeGrupo);
		sl_panelEditor.putConstraint(SpringLayout.WEST, txtNomeGrupo, 0, SpringLayout.WEST, lblNomeGrupo);
		sl_panelEditor.putConstraint(SpringLayout.EAST, txtNomeGrupo, 0, SpringLayout.EAST, lblNomeGrupo);
		panelEditor.add(txtNomeGrupo);
		txtNomeGrupo.setColumns(10);

		final JLabel lblNomeCardapio = new JLabel("Nome no Card\u00E1pio (preferencialmente em italiano):");
		sl_panelEditor.putConstraint(SpringLayout.NORTH, lblNomeCardapio, 10, SpringLayout.SOUTH, txtNomeGrupo);
		sl_panelEditor.putConstraint(SpringLayout.WEST, lblNomeCardapio, 10, SpringLayout.WEST, panelEditor);
		sl_panelEditor.putConstraint(SpringLayout.EAST, lblNomeCardapio, -10, SpringLayout.EAST, panelEditor);
		panelEditor.add(lblNomeCardapio);

		txtNomeCardapio = new JTextField();
		sl_panelEditor.putConstraint(SpringLayout.NORTH, txtNomeCardapio, 2, SpringLayout.SOUTH, lblNomeCardapio);
		sl_panelEditor.putConstraint(SpringLayout.WEST, txtNomeCardapio, 0, SpringLayout.WEST, lblNomeCardapio);
		sl_panelEditor.putConstraint(SpringLayout.EAST, txtNomeCardapio, 0, SpringLayout.EAST, lblNomeCardapio);
		panelEditor.add(txtNomeCardapio);
		txtNomeCardapio.setColumns(10);

		final JLabel lblDescricaoPortugues = new JLabel("Descri\u00E7\u00E3o em Portugu\u00EAs:");
		sl_panelEditor
				.putConstraint(SpringLayout.NORTH, lblDescricaoPortugues, 10, SpringLayout.SOUTH, txtNomeCardapio);
		sl_panelEditor.putConstraint(SpringLayout.WEST, lblDescricaoPortugues, 10, SpringLayout.WEST, panelEditor);
		sl_panelEditor.putConstraint(SpringLayout.EAST, lblDescricaoPortugues, -10, SpringLayout.EAST, panelEditor);
		panelEditor.add(lblDescricaoPortugues);

		txtDescricaoPortugues = new JTextField();
		sl_panelEditor.putConstraint(SpringLayout.NORTH, txtDescricaoPortugues, 2, SpringLayout.SOUTH,
				lblDescricaoPortugues);
		sl_panelEditor.putConstraint(SpringLayout.WEST, txtDescricaoPortugues, 0, SpringLayout.WEST,
				lblDescricaoPortugues);
		sl_panelEditor.putConstraint(SpringLayout.EAST, txtDescricaoPortugues, 0, SpringLayout.EAST,
				lblDescricaoPortugues);
		txtDescricaoPortugues.setColumns(10);
		panelEditor.add(txtDescricaoPortugues);

		final JLabel lblDescricaoIngles = new JLabel("Descri\u00E7\u00E3o em Ingl\u00EAs:");
		sl_panelEditor.putConstraint(SpringLayout.NORTH, lblDescricaoIngles, 10, SpringLayout.SOUTH,
				txtDescricaoPortugues);
		sl_panelEditor
				.putConstraint(SpringLayout.WEST, lblDescricaoIngles, 0, SpringLayout.WEST, lblDescricaoPortugues);
		sl_panelEditor
				.putConstraint(SpringLayout.EAST, lblDescricaoIngles, 0, SpringLayout.EAST, lblDescricaoPortugues);
		panelEditor.add(lblDescricaoIngles);

		txtDescricaoIngles = new JTextField();
		sl_panelEditor.putConstraint(SpringLayout.NORTH, txtDescricaoIngles, 2, SpringLayout.SOUTH, lblDescricaoIngles);
		sl_panelEditor.putConstraint(SpringLayout.WEST, txtDescricaoIngles, 0, SpringLayout.WEST, lblDescricaoIngles);
		sl_panelEditor.putConstraint(SpringLayout.EAST, txtDescricaoIngles, 0, SpringLayout.EAST, lblDescricaoIngles);
		txtDescricaoIngles.setColumns(10);
		panelEditor.add(txtDescricaoIngles);

		final JButton btnSalvar_1 = new JButton("Salvar");
		sl_panelEditor.putConstraint(SpringLayout.SOUTH, btnSalvar_1, -10, SpringLayout.SOUTH, panelEditor);
		sl_panelEditor.putConstraint(SpringLayout.EAST, btnSalvar_1, -10, SpringLayout.EAST, panelEditor);
		panelEditor.add(btnSalvar_1);

		final JButton btnCancelar = new JButton("Cancelar");
		sl_panelEditor.putConstraint(SpringLayout.SOUTH, btnCancelar, 0, SpringLayout.SOUTH, btnSalvar_1);
		sl_panelEditor.putConstraint(SpringLayout.EAST, btnCancelar, -6, SpringLayout.WEST, btnSalvar_1);
		panelEditor.add(btnCancelar);
	}

	private void atualizarArvores() {
		categorias.atualizar();
		navegacao.atualizar();
	}

	//	@Override
	//	public void cardapioLoaded() {
	//		// TODO Auto-generated method stub
	//	}

	private void carregarCardapio(final boolean atual) {
		//		rdbtnAtual.setSelected(atual);
		//		rdbtnNovo.setSelected(!atual);
		//
		//		if (atual) {
		//			editando.load(Client.cache().cardapio());
		//		}
		//		else {
		//			editando.limpar();
		//		}
		//
		//		setEditavel(!atual);
		//		limparEdicao();
		//
		//		atualizarArvores();
	}

	@Override
	public String getTabText() {
		return "Editor de Cardápio";
	}

	@Override
	public void itemStateChanged(final ItemEvent e) {
		//ativar apenas na troca de um botao (um ativa, outro desativa)
		if (e.getSource() == rdbtnAtual) {
			carregarCardapio(rdbtnAtual.isSelected());
		}
	}

	private void limparEdicao() {
		cbCategoria.setSelectedIndex(-1);
		txtNomeCompleto.setText("");
		txtDescricaoPortugues.setText("");
		txtDescricaoIngles.setText("");
		txtCodigo.setText("");
		txtInteira.setText("");
		txtMeia.setText("");
		txtNomeGrupo.setText("");
	}

	private void setEditavel(final boolean b) {
		btnLimpar.setEnabled(b);
		btnPublicar.setEnabled(b);
		btnCarregar.setEnabled(b);
		btnSalvar.setEnabled(b);
		btnNovaCategoria.setEnabled(b);
		btnNovoItem.setEnabled(b);
		cbCategoria.setEnabled(b);
		txtNomeCompleto.setEnabled(b);
		txtDescricaoPortugues.setEnabled(b);
		txtDescricaoIngles.setEnabled(b);
		txtCodigo.setEnabled(b);
		txtInteira.setEnabled(b);
		txtMeia.setEnabled(b);
		txtNomeGrupo.setEnabled(b);
	}

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
		carregarCardapio(rdbtnAtual.isSelected());
		atualizarArvores();
	}
}
