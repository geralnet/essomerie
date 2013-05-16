package net.geral.essomerie.client._gui.caixa.panels.fundo;

import net.geral.essomerie.client._gui.caixa.base.strdin.StringDinheiroLancamentos;

public class FundoLancamentos extends StringDinheiroLancamentos {
	public static final int			SALDO_INICIAL		= 0;
	public static final int			LINHAS_FIXAS		= 1;

	private static final String[]	lancamentos_fixos	= { "Saldo Inicial" };

	public FundoLancamentos() {
		super();
		for (final String f : lancamentos_fixos) {
			lancamentos.add(new FundoLancamento(f));
		}
		compactar();
	}
}
