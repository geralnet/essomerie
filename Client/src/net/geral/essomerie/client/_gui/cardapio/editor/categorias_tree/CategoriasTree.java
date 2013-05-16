package net.geral.essomerie.client._gui.cardapio.editor.categorias_tree;

import javax.swing.JTree;

public class CategoriasTree extends JTree {
	private static final long	serialVersionUID	= 1L;

	public CategoriasTree(final CategoriasTreeNode root) {
		super(root);
		setRootVisible(false);
	}
}
