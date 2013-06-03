package net.geral.essomerie.client._gui.shared.label;

import java.awt.Color;

import javax.swing.JLabel;

import net.geral.essomerie.shared.money.Money;

public class DinheiroLabel extends JLabel {
  private static final long   serialVersionUID = 1L;
  private static final String CIFRA            = "R$ ";
  private static final Color  COR_POSITIVO     = Color.BLACK;
  private static final Color  COR_NEGATIVO     = new Color(150, 0, 0);

  private final boolean       exibirCifra;
  private Money               dinheiro         = null;

  public DinheiroLabel(final boolean exibirCifra) {
    this.exibirCifra = exibirCifra;
    setHorizontalAlignment(TRAILING);
    showErro();
  }

  public DinheiroLabel(final Money d, final boolean exibirCifra) {
    this(exibirCifra);
    setDinheiro(d);
  }

  public void setDinheiro(final Money d) {
    dinheiro = d;
    if (d == null) {
      showErro();
    } else {
      super.setText((exibirCifra ? CIFRA : "") + dinheiro.toString());
      setForeground(dinheiro.isNegative() ? COR_NEGATIVO : COR_POSITIVO);
    }
  }

  public void setDouble(final Double d) {
    setDinheiro(d == null ? null : Money.fromDouble(d));
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
