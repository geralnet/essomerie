package net.geral.essomerie.client._gui.cardapio.editor.navegacao_tree;

import net.geral.essomerie._shared.cardapio.Cardapio;
import net.geral.essomerie._shared.cardapio.CardapioItem;

public class NavegacaoTreeController {
	public final NavegacaoTree		tree;
	public final NavegacaoTreeModel	model;
	public final Cardapio			cardapio;
	public final NavegacaoTreeNode	root;

	public NavegacaoTreeController(final Cardapio c) {
		root = new NavegacaoTreeNode();
		tree = new NavegacaoTree(root);
		model = new NavegacaoTreeModel(root);
		tree.setModel(model);
		cardapio = c;

		atualizar();
	}

	public void atualizar() {
		root.removeAllChildren();

		for (final CardapioItem ci : cardapio.getNavegacaoRoot()) {
			root.add(criarFilhos(ci));
		}

		tree.expandRow(0);
		model.nodeStructureChanged(root);
	}

	private NavegacaoTreeNode criarFilhos(final CardapioItem ci) {
		final NavegacaoTreeNode pai = new NavegacaoTreeNode(ci, false);
		for (final CardapioItem cif : cardapio.getNavegacaoFilhos(ci)) {
			pai.criarItemPadraoSeNecessario(ci);
			pai.add(criarFilhos(cif));
		}
		return pai;
	}
}
