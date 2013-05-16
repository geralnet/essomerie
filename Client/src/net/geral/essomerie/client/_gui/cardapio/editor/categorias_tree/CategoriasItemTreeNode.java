package net.geral.essomerie.client._gui.cardapio.editor.categorias_tree;

import javax.swing.tree.DefaultMutableTreeNode;

import net.geral.essomerie._shared.cardapio.CardapioItem;

public class CategoriasItemTreeNode extends DefaultMutableTreeNode {
	private static final long	serialVersionUID	= 1L;
	private final CardapioItem	item;

	public CategoriasItemTreeNode(final CardapioItem ci) {
		item = ci;
		setUserObject(ci.nome);
	}

	@Override
	public boolean getAllowsChildren() {
		return false;
	}
}