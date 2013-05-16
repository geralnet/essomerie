package net.geral.essomerie._shared.contagem;

import java.io.Serializable;

public class ContagemMotivos implements Serializable {
	public static final int			MOTIVOS_POR_TIPO	= 4;

	private final ContagemMotivo[]	motivos;

	public ContagemMotivos() {
		this(new ContagemMotivo[0]);
	}

	public ContagemMotivos(final ContagemMotivo[] cm) {
		motivos = cm;
	}

	public ContagemMotivos(final ContagemMotivos cm) {
		this(cm.motivos.clone());
	}

	public String get(final char tipo, final int idmotivo) {
		for (final ContagemMotivo cm : motivos) {
			if (cm.isTipo(tipo) && (cm.id == idmotivo)) return cm.motivo;
		}
		return tipo + "#" + idmotivo;
	}

	public ContagemMotivo[] getAll() {
		return motivos.clone();
	}

	public ContagemMotivo[] getMotivos(final char tipo) {
		final ContagemMotivo[] cms = new ContagemMotivo[MOTIVOS_POR_TIPO];
		for (int i = 0; i < MOTIVOS_POR_TIPO; i++) {
			cms[i] = null;
		}
		for (final ContagemMotivo cm : motivos) {
			if (cm.isTipo(tipo)) {
				cms[cm.id - 1] = cm;
			}
		}
		return cms;
	}
}
