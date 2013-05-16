package net.geral.essomerie._shared.pedido;

import java.io.Serializable;

import net.geral.essomerie._shared.Dinheiro;

import org.joda.time.LocalDateTime;

public class ResumoPedido implements Serializable {
	public final int			idendereco;
	public final Dinheiro		consumo;
	public final LocalDateTime	datahora;

	public ResumoPedido(final int _idendereco, final Dinheiro _consumo, final LocalDateTime _datahora) {
		idendereco = _idendereco;
		consumo = _consumo;
		datahora = _datahora;
	}
}
