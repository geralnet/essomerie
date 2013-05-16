package net.geral.essomerie._shared.mensagens;

import java.io.Serializable;

import org.joda.time.LocalDateTime;

public class MessageStatus implements Serializable {
	private static final long	serialVersionUID	= 1L;
	public final int			iduser;
	public final LocalDateTime	read;

	public MessageStatus(final int idusuario, final LocalDateTime lida) {
		this.iduser = idusuario;
		this.read = lida;
	}
}
