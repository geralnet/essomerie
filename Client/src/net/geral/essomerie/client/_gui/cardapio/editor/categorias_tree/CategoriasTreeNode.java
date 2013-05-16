package net.geral.essomerie.client._gui.cardapio.editor.categorias_tree;

import javax.swing.tree.DefaultMutableTreeNode;

import net.geral.essomerie._shared.cardapio.CardapioCategoria;

public class CategoriasTreeNode extends DefaultMutableTreeNode {
	private static final long		serialVersionUID	= 1L;
	private final CardapioCategoria	categoria;

	public CategoriasTreeNode() {
		this(null);
	}

	public CategoriasTreeNode(final CardapioCategoria cc) {
		categoria = cc;
		setUserObject((cc == null) ? "root" : cc.nome);
	}

	@Override
	public boolean getAllowsChildren() {
		return true;
	}
}
