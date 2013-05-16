package net.geral.essomerie._shared.cardapio;

import java.util.Hashtable;
import java.util.Vector;

import net.geral.essomerie._shared.Dinheiro;

public class Cardapio {
	public static final double	FRACAO_MEIA_INTEIRA	= 0.6f;

	public static Dinheiro calcularPrecoMeia(final Dinheiro inteira) {
		return inteira.multiply(FRACAO_MEIA_INTEIRA);
	}

	private Vector<CardapioCategoria>					categoriasVector;
	private Vector<CardapioItem>						itemsVector;

	private final Hashtable<Integer, CardapioCategoria>	categoriasTable	= new Hashtable<Integer, CardapioCategoria>();
	private final Hashtable<Integer, CardapioItem>		itemsTable		= new Hashtable<Integer, CardapioItem>();		;

	private final Vector<CardapioCategoria>				categoriasRoot	= new Vector<CardapioCategoria>();

	private final Vector<CardapioItem>					navegacaoRoot	= new Vector<CardapioItem>();

	public Cardapio() {
		limpar();
	}

	public Cardapio(final Vector<CardapioCategoria> categorias, final Vector<CardapioItem> items) {
		load(categorias, items);
	}

	private void clearReferences() {
		for (final CardapioCategoria cc : categoriasVector) {
			cc.clearReferences();
		}
	}

	private void createTables() {
		categoriasTable.clear();
		for (final CardapioCategoria cc : categoriasVector) {
			categoriasTable.put(cc.id, cc);
		}

		itemsTable.clear();
		for (final CardapioItem ci : itemsVector) {
			itemsTable.put(ci.id, ci);
		}
	}

	public CardapioCategoria[] getAllCategorias() {
		return categoriasVector.toArray(new CardapioCategoria[categoriasVector.size()]);
	}

	public CardapioItem[] getAllItems() {
		return itemsVector.toArray(new CardapioItem[itemsVector.size()]);
	}

	public CardapioCategoria[] getCategoriasRoot() {
		return categoriasRoot.toArray(new CardapioCategoria[categoriasRoot.size()]);
	}

	public CardapioItem[] getItemsWith(final String codigo, final String nome) {
		final Vector<CardapioItem> vi = new Vector<CardapioItem>();
		for (final CardapioItem ci : itemsVector) {
			if (ci.contains(codigo, nome)) {
				vi.add(ci);
			}
		}
		return vi.toArray(new CardapioItem[vi.size()]);
	}

	public CardapioItem[] getNavegacaoFilhos(final CardapioItem pai) {
		final Vector<CardapioItem> filhos = new Vector<CardapioItem>();
		for (final CardapioItem ci : itemsVector) {
			if (ci.isFilho(pai)) {
				filhos.add(ci);
			}
		}
		return filhos.toArray(new CardapioItem[filhos.size()]);
	}

	public CardapioItem[] getNavegacaoRoot() {
		return navegacaoRoot.toArray(new CardapioItem[navegacaoRoot.size()]);
	}

	public void limpar() {
		load(new Vector<CardapioCategoria>(), new Vector<CardapioItem>());
	}

	public synchronized void load(final Cardapio cardapio) {
		final Vector<CardapioCategoria> c = new Vector<CardapioCategoria>(cardapio.categoriasVector.size());
		final Vector<CardapioItem> i = new Vector<CardapioItem>(cardapio.itemsVector.size());

		for (final CardapioCategoria cc : cardapio.categoriasVector) {
			c.add(new CardapioCategoria(cc));
		}

		for (final CardapioItem ci : cardapio.itemsVector) {
			i.add(new CardapioItem(ci));
		}

		load(c, i);
	}

	public synchronized void load(final Vector<CardapioCategoria> categorias, final Vector<CardapioItem> items) {
		categoriasVector = categorias;
		itemsVector = items;

		createTables();
		clearReferences();
		setReferences();
		setCategoriasRoot();
		setNavegacaoRoot();
	}

	private void setCategoriasRoot() {
		categoriasRoot.clear();
		for (final CardapioCategoria cc : categoriasVector) {
			if (cc.getBase() == null) {
				categoriasRoot.add(cc);
			}
		}
	}

	private void setNavegacaoRoot() {
		navegacaoRoot.clear();
		for (final CardapioItem cc : itemsVector) {
			if (cc.getNavegacaoNivel() == 0) {
				navegacaoRoot.add(cc);
			}
		}
	}

	private void setReferences() {
		for (final CardapioCategoria cc : categoriasVector)
			if (cc.idbase > 0) {
				final CardapioCategoria base = categoriasTable.get(cc.idbase);
				if (base == null) {
					continue;
				}
				base.addSub(cc);
				cc.setBase(base);
			}

		for (final CardapioItem ci : itemsVector) {
			final CardapioCategoria cat = categoriasTable.get(ci.idcategoria);
			if (cat == null) {
				continue;
			}
			cat.addItem(ci);
			ci.setCategoria(cat);
		}
	}
}
