package net.geral.essomerie.client._gui.cardapio.editor.navegacao_tree;

import javax.swing.tree.DefaultMutableTreeNode;

import net.geral.essomerie._shared.cardapio.CardapioItem;

public class NavegacaoTreeNode extends DefaultMutableTreeNode {
	private static final long	serialVersionUID	= 1L;

	private static String getNavegacaoNome(final CardapioItem ci, final boolean padrao) {
		if (ci == null) { return "root"; }
		if (padrao) { return "_: " + ci.nome; }
		return ci.getCodigoNivel() + ": " + ci.nomeNavegacao;
	}

	private final CardapioItem	item;

	public NavegacaoTreeNode() {
		this(null, false);
	}

	public NavegacaoTreeNode(final CardapioItem ci, final boolean padrao) {
		item = ci;
		setUserObject((ci == null) ? "root" : getNavegacaoNome(ci, padrao));
	}

	public void criarItemPadraoSeNecessario(final CardapioItem ci) {
		if (getChildCount() == 0) {
			add(new NavegacaoTreeNode(ci, true));
		}
	}
}