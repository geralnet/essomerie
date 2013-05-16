package net.geral.essomerie._shared.cardapio;

import java.util.Vector;

import net.geral.lib.strings._Conversor;

public class CardapioCategoria {
	public final int						id;
	public final int						idbase;
	public final int						ordem;
	public final String						codigo;
	public final String						nome;

	private final String					nomeSearch;
	private final Vector<CardapioCategoria>	subs	= new Vector<CardapioCategoria>();
	private final Vector<CardapioItem>		items	= new Vector<CardapioItem>();
	private CardapioCategoria				base	= null;

	public CardapioCategoria(final CardapioCategoria cc) {
		this(cc.id, cc.idbase, cc.ordem, cc.codigo, cc.nome);
	}

	public CardapioCategoria(final int id, final int idbase, final int ordem, final String codigo,
			final String nome) {
		this.id = id;
		this.idbase = idbase;
		this.ordem = ordem;
		this.codigo = codigo;
		this.nome = nome;
		nomeSearch = _Conversor.toAZ09(nome, false);
	}

	public void addItem(final CardapioItem ci) {
		items.add(ci);
	}

	public void addSub(final CardapioCategoria cc) {
		subs.add(cc);
	}

	public void clearReferences() {
		subs.clear();
		items.clear();
		base = null;
	}

	public boolean contains(final String cCodigo, final String cNome) {
		if ((base != null) && (base.contains(cCodigo, cNome))) return true;
		if ((cCodigo != null) && codigo.startsWith(cCodigo)) return true;
		if (nomeSearch.indexOf(cNome) >= 0) return true;
		return false;
	}

	public CardapioCategoria getBase() {
		return base;
	}

	public CardapioItem[] getItems() {
		return items.toArray(new CardapioItem[items.size()]);
	}

	public CardapioCategoria[] getSubCategorias() {
		return subs.toArray(new CardapioCategoria[subs.size()]);
	}

	public void setBase(final CardapioCategoria newBase) {
		base = newBase;
	}
}
