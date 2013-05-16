package net.geral.essomerie._shared.cardapio;

import net.geral.essomerie._shared.Dinheiro;
import net.geral.lib.strings._Conversor;

public class CardapioItem {
	public final int			id;
	public final int			ordem;
	public final int			idcategoria;
	public final Dinheiro		meia;
	public final Dinheiro		inteira;
	public final String			codigo;
	public final String			nome;
	public final String			nomeNavegacao;
	public final String			nomeCardapio;
	public final String			descricaoPortugues;
	public final String			descricaoIngles;

	private final String		nomeSearch;
	private CardapioCategoria	categoria	= null;

	public CardapioItem(final CardapioItem ci) {
		this(ci.id, ci.ordem, ci.idcategoria, ci.meia, ci.inteira, ci.codigo, ci.nome, ci.nomeNavegacao,
				ci.nomeCardapio, ci.descricaoPortugues, ci.descricaoIngles);
	}

	public CardapioItem(final int id, final int ordem, final int idcategoria, final Dinheiro meia,
			final Dinheiro inteira, final String codigo, final String nome, final String nomeNavegacao,
			final String nomeCardapio, final String descricaoPortugues, final String descricaoIngles) {
		this.id = id;
		this.ordem = ordem;
		this.idcategoria = idcategoria;
		this.meia = meia.isZero() ? Cardapio.calcularPrecoMeia(inteira) : meia;
		this.inteira = inteira;
		this.codigo = codigo;
		this.nome = nome;
		this.nomeNavegacao = nomeNavegacao;
		this.nomeCardapio = nomeCardapio;
		this.descricaoPortugues = descricaoPortugues;
		this.descricaoIngles = descricaoIngles;
		nomeSearch = _Conversor.toAZ09(nome + " " + nomeCardapio + " " + nomeNavegacao, false);
	}

	public boolean contains(final String cCodigo, final String cNome) {
		if ((categoria != null) && (categoria.contains(cCodigo, cNome))) return true;
		if (nomeSearch.indexOf(cNome) >= 0) return true;
		return false;
	}

	public CardapioCategoria getCategoria() {
		return categoria;
	}

	public char getCodigoNivel() {
		return codigo.charAt(codigo.length() - 1);
	}

	public int getNavegacaoNivel() {
		return codigo.length() - 1;
	}

	public boolean isFilho(final CardapioItem pai) {
		// tem que ser um nivel abaixo
		if (pai.getNavegacaoNivel() != getNavegacaoNivel() - 1) return false;
		// ver codigo
		return codigo.startsWith(pai.codigo);
	}

	public void setCategoria(final CardapioCategoria newCategoria) {
		categoria = newCategoria;
	}
}
