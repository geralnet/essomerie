package net.geral.essomerie.client._gui.shared.textfield;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import net.geral.essomerie._shared.Dinheiro;
import net.geral.gui.textfield.formula.FormulaTextField;

public class DinheiroTextField extends FormulaTextField<Dinheiro> implements MouseListener {
	private static final long	serialVersionUID	= 1L;

	private final boolean		allowNegative;
	private final boolean		temDetalhamento;
	private DinheiroDialog		detalhamento		= null;
	private Dinheiro			multiplo			= null;

	public DinheiroTextField(final boolean negativeAllowed, final boolean apresentarDetalhamento) {
		super("[^0-9\\-\\+\\,]");
		allowNegative = negativeAllowed;
		temDetalhamento = apresentarDetalhamento;
		if (temDetalhamento) {
			addMouseListener(this);
		}
	}

	@Override
	protected boolean evaluate() {
		try {
			//evaluate
			final String parts[] = getParts();
			if (parts.length == 0) {
				value = null;
				return true;
			}
			Dinheiro d = Dinheiro.ZERO;
			for (final String p : parts) {
				//se houver separador decimal, maximo de dois digitos seguem
				final int pos = p.indexOf(Dinheiro.SEPARADOR_DECIMAL);
				if ((pos > -1) && (pos < (p.length() - 3))) { return false; }
				//somar
				d = Dinheiro.add(d, new Dinheiro(p));
			}
			//check number
			if ((!allowNegative) && d.isNegativo()) { return false; }
			//check multiple
			if (multiplo != null) {
				if (!Dinheiro.remaining(d, multiplo).isZero()) { throw new NumberFormatException("Nao multiplo."); }
			}
			//ok
			value = d;
			return true;
		}
		catch (final NumberFormatException e) {
			return false;
		}
	}

	@Override
	public Dinheiro getValueForNull() {
		return Dinheiro.ZERO;
	}

	@Override
	public void mouseClicked(final MouseEvent e) {
		if (e.getClickCount() == 2) {
			showDetalhamento();
		}
	}

	@Override
	public void mouseEntered(final MouseEvent e) {}

	@Override
	public void mouseExited(final MouseEvent e) {}

	@Override
	public void mousePressed(final MouseEvent e) {}

	@Override
	public void mouseReleased(final MouseEvent e) {}

	public void resetDetalhamento() {
		if (detalhamento != null) {
			detalhamento.reset();
		}
	}

	public void setMultiplo(Dinheiro d) {
		if ((d != null) && (d.isZero())) {
			d = null;
		}
		multiplo = d;
	}

	private void showDetalhamento() {
		if (!temDetalhamento) { return; }

		if (detalhamento == null) {
			detalhamento = new DinheiroDialog(this, "Detalhamento");
		}

		detalhamento.setValor(getValue(false));

		detalhamento.setVisible(true);
		if (detalhamento.getCancelado()) { return; }
		setValue(detalhamento.getValor());
	}

	@Override
	protected Dinheiro stringToValue(final String s, final boolean nullAllowed) {
		if ((s == null) || (s.length() == 0)) { return (nullAllowed ? null : getValueForNull()); }
		return new Dinheiro(s);
	}

	@Override
	protected String valueToString(final Dinheiro d) {
		if (d == null) { return ""; }
		return d.toString();
	}
}
