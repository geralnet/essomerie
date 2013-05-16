package net.geral.essomerie._shared.dispositivos;


public class Dispositivo {
	public final int	id;
	public final String	nome;

	public Dispositivo(final int id, final String nome) {
		this.id = id;
		this.nome = nome;
	}

	@Override
	public String toString() {
		return nome;
	}
}
