package net.geral.essomerie.client.gui.shared.textfield;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import net.geral.essomerie.client._gui.shared.textfield.DinheiroDialog;
import net.geral.essomerie.shared.money.Money;
import net.geral.lib.gui.textfield.formula.FormulaTextField;

public class MoneyTextField extends FormulaTextField<Money> implements
    MouseListener {
  private static final long serialVersionUID = 1L;

  private final boolean     allowNegative;
  private final boolean     allowZero;
  private final boolean     allowPositive;
  private final boolean     showBreakdown;
  private DinheiroDialog    breakdown        = null;
  private Money             multipleOf       = null;

  public MoneyTextField(final boolean allowNegative, final boolean allowZero,
      final boolean allowPositive, final boolean showBreakdown) {
    super("[^0-9\\-\\+\\,]");
    this.allowNegative = allowNegative;
    this.allowZero = allowZero;
    this.allowPositive = allowPositive;
    this.showBreakdown = showBreakdown;
    if (showBreakdown) {
      addMouseListener(this);
    }
  }

  @Override
  protected boolean evaluate() {
    try {
      // evaluate
      final String parts[] = getParts();
      if (parts.length == 0) {
        value = null;
        return allowZero;
      }
      Money d = Money.zero();
      for (final String p : parts) {
        // if has decimal, up to 2 digits
        final int pos = p.indexOf(Money.getDecimalSeparator());
        if ((pos > -1) && (pos < (p.length() - 3))) {
          return false;
        }
        // sum
        d = Money.sum(d, Money.fromString(p));
      }
      // check number
      System.err.println(d);
      if (((!allowNegative) && d.isNegative()) || ((!allowZero) && d.isZero())
          || ((!allowPositive) && d.isPositive())) {
        return false;
      }
      // check multiple
      if (multipleOf != null) {
        if (!Money.remaining(d, multipleOf).isZero()) {
          throw new NumberFormatException("Not multiple of: " + multipleOf);
        }
      }
      // ok
      value = d;
      return true;
    } catch (final NumberFormatException e) {
      return false;
    }
  }

  @Override
  public Money getValueForNull() {
    return Money.zero();
  }

  @Override
  public void mouseClicked(final MouseEvent e) {
    if (e.getClickCount() == 2) {
      showBreakdown();
    }
  }

  @Override
  public void mouseEntered(final MouseEvent e) {
  }

  @Override
  public void mouseExited(final MouseEvent e) {
  }

  @Override
  public void mousePressed(final MouseEvent e) {
  }

  @Override
  public void mouseReleased(final MouseEvent e) {
  }

  public void resetBreakdown() {
    if (breakdown != null) {
      breakdown.reset();
    }
  }

  public void setMultiple(Money m) {
    if ((m != null) && (m.isZero())) {
      m = null;
    }
    multipleOf = m;
  }

  private void showBreakdown() {
    if (!showBreakdown) {
      return;
    }

    if (breakdown == null) {
      // TODO translate
      breakdown = new DinheiroDialog(this, "Detalhamento");
    }

    breakdown.setValue(getValue(false));

    breakdown.setVisible(true);
    if (breakdown.getCancelled()) {
      return;
    }
    setValue(breakdown.getValue());
  }

  @Override
  protected Money stringToValue(final String s, final boolean nullAllowed) {
    if ((s == null) || (s.length() == 0)) {
      return (nullAllowed ? null : getValueForNull());
    }
    return Money.fromString(s);
  }

  @Override
  protected String valueToString(final Money d) {
    if (d == null) {
      return "";
    }
    return d.toString();
  }
}
