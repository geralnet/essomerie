package net.geral.essomerie._shared.funcionario;


public class FuncionarioBasico {
	public final int	idfuncionario;
	public final String	nome;

	public final String	apelido;

	public FuncionarioBasico(final int idfuncionario, final String nome, final String apelido) {
		this.idfuncionario = idfuncionario;
		this.nome = nome;
		this.apelido = apelido;
	}

	@Override
	public String toString() {
		return nome + (apelido.length() == 0 ? "" : " (" + apelido + ")");
	}
}
