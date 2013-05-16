package net.geral.essomerie.client._gui.cardapio.editor.categorias_tree;

import net.geral.essomerie._shared.cardapio.Cardapio;
import net.geral.essomerie._shared.cardapio.CardapioCategoria;
import net.geral.essomerie._shared.cardapio.CardapioItem;

public class CategoriasTreeController {
	public final CategoriasTree			tree;
	public final CategoriasTreeModel	model;
	public final Cardapio				cardapio;
	public final CategoriasTreeNode		root;

	public CategoriasTreeController(final Cardapio c) {
		root = new CategoriasTreeNode();
		tree = new CategoriasTree(root);
		model = new CategoriasTreeModel(root);
		tree.setModel(model);
		cardapio = c;

		atualizar();
	}

	public void atualizar() {
		root.removeAllChildren();

		for (final CardapioCategoria cc : cardapio.getCategoriasRoot()) {
			root.add(criarFilhos(cc));
		}

		tree.expandRow(0);
		model.nodeStructureChanged(root);
	}

	private CategoriasTreeNode criarFilhos(final CardapioCategoria c) {
		final CategoriasTreeNode pai = new CategoriasTreeNode(c);

		for (final CardapioCategoria cc : c.getSubCategorias()) {
			pai.add(criarFilhos(cc));
		}

		for (final CardapioItem ci : c.getItems()) {
			pai.add(new CategoriasItemTreeNode(ci));
		}

		return pai;
	}
}
