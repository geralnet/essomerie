package net.geral.essomerie.client._gui.shared.label;

import java.awt.Color;

import javax.swing.JLabel;

import net.geral.essomerie._shared.Dinheiro;

public class DinheiroLabel extends JLabel {
	private static final long	serialVersionUID	= 1L;
	private static final String	CIFRA				= "R$ ";
	private static final Color	COR_POSITIVO		= Color.BLACK;
	private static final Color	COR_NEGATIVO		= new Color(150, 0, 0);

	private final boolean		exibirCifra;
	private Dinheiro			dinheiro			= null;

	public DinheiroLabel(final boolean exibirCifra) {
		this.exibirCifra = exibirCifra;
		setHorizontalAlignment(TRAILING);
		showErro();
	}

	public DinheiroLabel(final Dinheiro d, final boolean exibirCifra) {
		this(exibirCifra);
		setDinheiro(d);
	}

	public void setDinheiro(final Dinheiro d) {
		dinheiro = d;
		if (d == null) {
			showErro();
		}
		else {
			super.setText((exibirCifra ? CIFRA : "") + dinheiro.toString());
			setForeground(dinheiro.isNegativo() ? COR_NEGATIVO : COR_POSITIVO);
		}
	}

	public void setDouble(final Double d) {
		setDinheiro(d == null ? null : new Dinheiro(d));
	}

	@Override
	public void setText(final String s) {
		showErro();
	}

	public void showErro() {
		dinheiro = null;
		super.setText((exibirCifra ? CIFRA : "") + "-,--");
	}
}
