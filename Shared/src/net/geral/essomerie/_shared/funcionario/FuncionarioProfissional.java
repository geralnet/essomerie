package net.geral.essomerie._shared.funcionario;

import net.geral.essomerie._shared.Dinheiro;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

public class FuncionarioProfissional {
	public final int			id;
	public final int			idfuncionario;
	public final LocalDateTime	alteradoEm;
	public final int			alteradoPor;
	public final LocalDate		admissao;
	public final String			cargo;
	public final String			funcao;
	public final Dinheiro		salario;
	public final Dinheiro		extra;
	public final String			extraObservacoes;
	public final String			pagamentoBanco;
	public final int			pagamentoAgencia;
	public final int			pagamentoConta;
	public final String			responsabilidades;

	public FuncionarioProfissional(final int id, final int idfuncionario, final LocalDateTime alteradoEm,
			final int alteradoPor, final LocalDate admissao, final String cargo, final String funcao,
			final Dinheiro salario, final Dinheiro extra, final String extraObservacoes, final String pagamentoBanco,
			final int pagamentoAgencia, final int pagamentoConta, final String responsabilidades) {

		this.id = id;
		this.idfuncionario = idfuncionario;
		this.alteradoEm = alteradoEm;
		this.alteradoPor = alteradoPor;
		this.admissao = admissao;
		this.cargo = cargo;
		this.funcao = funcao;
		this.salario = salario;
		this.extra = extra;
		this.extraObservacoes = extraObservacoes;
		this.pagamentoBanco = pagamentoBanco;
		this.pagamentoAgencia = pagamentoAgencia;
		this.pagamentoConta = pagamentoConta;
		this.responsabilidades = responsabilidades;
	}

	public String calcularTempoDeCasa() {
		if (admissao == null) return "-";
		final LocalDate hoje = LocalDate.now();
		int anos = hoje.getYear() - admissao.getYear();
		if (hoje.getMonthOfYear() < admissao.getMonthOfYear()) {
			anos--;
		}
		else if (hoje.getMonthOfYear() == admissao.getMonthOfYear()) {
			if (hoje.getDayOfYear() < admissao.getDayOfYear()) {
				anos--;
			}
		}
		if (anos > 1) return anos + " anos";
		if (anos == 1) return "1 ano";
		return "menos de 1 ano";
	}
}
