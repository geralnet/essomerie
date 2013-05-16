package net.geral.essomerie.client._gui.caixa.panels.conferencia;

import net.geral.essomerie.client._gui.caixa.base.strdin.StringDinheiroLancamentos;

public class ConferenciaLancamentos extends StringDinheiroLancamentos {
	public static final int			VALE				= 0;
	public static final int			CHEQUE				= 1;
	public static final int			CARTAO				= 2;
	public static final int			DINHEIRO			= 3;
	public static final int			COFRE				= 4;
	public static final int			LINHAS_FIXAS		= 5;

	private static final String[]	lancamentos_fixos	= { "Vales", "Cheques", "Cartões", "Dinheiro", "Cofre" };

	public ConferenciaLancamentos() {
		super();
		for (final String f : lancamentos_fixos) {
			lancamentos.add(new ConferenciaLancamento(f));
		}
		compactar();
	}
}
