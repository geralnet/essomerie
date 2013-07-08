package net.geral.essomerie.client.gui.shared.label;

import java.awt.Color;

import javax.swing.BorderFactory;

public class ErroLabel extends TitleLabel {
  private static final long serialVersionUID = 1L;

  public ErroLabel(final String txt) {
    this(txt, false);
  }

  public ErroLabel(final String txt, final boolean isLarge) {
    super(txt, isLarge);
    setBackground(new Color(80, 0, 0));
    setBorder(BorderFactory.createLineBorder(Color.BLACK));
  }
}
