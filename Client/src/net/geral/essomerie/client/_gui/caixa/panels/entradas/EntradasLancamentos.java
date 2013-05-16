package net.geral.essomerie.client._gui.caixa.panels.entradas;

import net.geral.essomerie.client._gui.caixa.base.strdin.StringDinheiroLancamentos;

public class EntradasLancamentos extends StringDinheiroLancamentos {
	public static final int	SALAO				= 0;
	public static final int	DELIVERY			= 1;
	public static final int	VIAGEM				= 2;
	public static final int	PLANTAO				= 3;
	public static final int	LINHAS_FIXAS		= 4;
	private final String[]	lancamentos_fixos	= { "Salão", "Delivery", "Viagem", "Plantão" };

	public EntradasLancamentos() {
		super();
		for (final String f : lancamentos_fixos) {
			lancamentos.add(new EntradasLancamento(f));
		}
		compactar();
	}
}
