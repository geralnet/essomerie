package net.geral.essomerie._shared.contagem;

import java.io.Serializable;

//TODO check & translate
public class ContagemAlteracaoQuantidade implements Serializable {
    public final int iditem;
    public final char tipo;
    public final int idmotivo;
    public final float quantidade;
    public final String observacoes;

    public ContagemAlteracaoQuantidade(final int _iditem, final char _tipo,
	    final int _idmotivo, final float _quantidade,
	    final String _observacoes) {
	iditem = _iditem;
	tipo = _tipo;
	idmotivo = _idmotivo;
	quantidade = _quantidade;
	observacoes = _observacoes;
    }
}
