package net.geral.essomerie.client._gui.caixa.panels.salao;

import net.geral.essomerie.client._gui.caixa.base.BaseLancamento;
import net.geral.essomerie._shared.Dinheiro;

public class SalaoLancamento extends BaseLancamento {
	public static final int			MESA	= 0;
	public static final int			PESSOAS	= 1;
	public static final int			CONSUMO	= 2;
	public static final int			SERVICO	= 3;
	public static final int			REPIQUE	= 4;

	private static final Class<?>[]	types	= { Integer.class, Integer.class, Dinheiro.class, Dinheiro.class,
			Dinheiro.class					};
	public static final int			CAMPOS	= types.length;

	public static BaseLancamento load(final String[] ss) {
		return new SalaoLancamento(loadInteger(ss[0]), loadInteger(ss[1]), loadDinheiro(ss[2]), loadDinheiro(ss[3]),
				loadDinheiro(ss[4]));
	}

	public SalaoLancamento() {
		this(null, null, null, null, null);
	}

	public SalaoLancamento(final Integer mesa, final Integer pessoas, final Dinheiro consumo, final Dinheiro servico,
			final Dinheiro repique) {
		super(types.length);
		set(MESA, mesa);
		set(PESSOAS, pessoas);
		set(CONSUMO, consumo);
		set(SERVICO, servico);
		set(REPIQUE, repique);
	}

	@Override
	protected int getColumnCount() {
		return types.length;
	}

	public Dinheiro getConsumoMedio() {
		final Dinheiro c = getDinheiroOrNull(CONSUMO);
		final Integer p = getIntegerOrNull(PESSOAS);
		if ((c == null) || (p == null)) { return null; }
		return Dinheiro.divide(c, p.intValue());
	}

	public Dinheiro getConta() {
		final Dinheiro c = getDinheiroOrNull(CONSUMO);
		final Dinheiro s = getDinheiroOrNull(SERVICO);
		if ((c == null) || (s == null)) { return null; }
		return Dinheiro.add(c, s);
	}

	public Dinheiro getPago() {
		final Dinheiro c = getConta();
		final Dinheiro r = getDinheiroOrNull(REPIQUE);
		if ((c == null) || (r == null)) { return null; }
		return Dinheiro.add(c, r);
	}

	@Override
	protected Class<?> getType(final int i) {
		return types[i];
	}
}
