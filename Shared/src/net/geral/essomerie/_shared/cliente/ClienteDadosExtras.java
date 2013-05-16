package net.geral.essomerie._shared.cliente;

import java.io.Serializable;

import net.geral.essomerie._shared.Dinheiro;

import org.joda.time.LocalDateTime;

public class ClienteDadosExtras implements Serializable {
	public final int			idcliente;
	public final LocalDateTime	cadastrado;
	public final LocalDateTime	ultimoPedido;
	public final int			numeroPedidos;
	public final Dinheiro		consumoPedidos;

	public ClienteDadosExtras(final int _idcliente, final LocalDateTime _cadastrado, final LocalDateTime _ultimoPedido,
			final int _numeroPedidos, final Dinheiro _consumoPedidos) {
		if (_cadastrado == null) throw new NullPointerException("DateTime cadastrado cannot be null.");
		if (_ultimoPedido == null) throw new NullPointerException("DateTime ultimoPedido cannot be null.");
		if (_consumoPedidos == null) throw new NullPointerException("DateTime consumoPedidos cannot be null.");
		idcliente = _idcliente;
		cadastrado = _cadastrado;
		ultimoPedido = _ultimoPedido;
		numeroPedidos = _numeroPedidos;
		consumoPedidos = _consumoPedidos;
	}
}
