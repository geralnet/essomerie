package net.geral.essomerie._shared;

public enum EstadoCivil {
	SOLTEIRO("Solteiro(a)"),
	CASADO("Casado(a)"),
	SEPARADO("Separado(a)"),
	DIVORCIADO("Divorciado(a)"),
	VIUVO("Viúvo(a)");

	public static EstadoCivil fromString(final String s) {
		for (final EstadoCivil ec : values()) {
			if (s.equals(ec.name())) return ec;
			if (s.equals(ec.descricao)) return ec;
		}
		return SOLTEIRO;
	}

	private String	descricao;

	private EstadoCivil(final String desc) {
		descricao = desc;
	}

	@Override
	public String toString() {
		return descricao;
	}
}
