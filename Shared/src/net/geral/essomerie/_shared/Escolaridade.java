package net.geral.essomerie._shared;

public enum Escolaridade {
	NENHUMA("Nenhuma"),
	LE_ESCREVE("Lê e Escreve"),
	FUNDAMENTAL("Ensino Fundamental"),
	MEDIO("Ensino Médio"),
	SUPERIOR("Ensino Superior"),
	POS("Pós-Graduado");

	public static Escolaridade fromId(final String id) {
		for (final Escolaridade e : values()) {
			if (id.equals(e.name())) return e;
		}
		return NENHUMA;
	}

	public final String	descricao;

	private Escolaridade(final String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String toString() {
		return descricao;
	}
}
