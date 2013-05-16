package net.geral.essomerie.client._gui.cardapio.editor.navegacao_tree;

import javax.swing.JTree;

public class NavegacaoTree extends JTree {
	private static final long	serialVersionUID	= 1L;

	public NavegacaoTree(final NavegacaoTreeNode root) {
		super(root);
		setRootVisible(false);
	}
}
