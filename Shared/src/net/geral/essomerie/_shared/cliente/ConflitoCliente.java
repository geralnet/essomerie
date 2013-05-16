package net.geral.essomerie._shared.cliente;

public class ConflitoCliente implements Comparable<ConflitoCliente> {
	public static final int	POSSIVEL_CONFLITO	= 1;
	public static final int	PROVAVEL_CONFLITO	= 2;
	public static final int	CONFLITO			= 3;

	public final Customer	cliente;
	public final int		severidade;
	public final String		campo;

	public ConflitoCliente(final Customer cliente, final int severidade, final String campo) {
		this.cliente = cliente;
		this.severidade = severidade;
		this.campo = campo;
	}

	@Override
	public int compareTo(final ConflitoCliente o) {
		if (severidade != o.severidade) return o.severidade - severidade;
		return cliente.compareTo(o.cliente);
	}
}
