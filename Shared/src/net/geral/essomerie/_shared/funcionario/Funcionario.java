package net.geral.essomerie._shared.funcionario;

import org.joda.time.LocalDateTime;

public class Funcionario {
	public final int						id;
	public final LocalDateTime				cadastradoEm;
	public final FuncionarioPessoal			pessoal;
	public final FuncionarioProfissional	profissional;

	public Funcionario(final int id, final LocalDateTime cadastradoEm, final FuncionarioPessoal pessoal,
			final FuncionarioProfissional profissional) {
		this.id = id;
		this.cadastradoEm = cadastradoEm;
		this.pessoal = pessoal;
		this.profissional = profissional;
	}
}
