package net.geral.essomerie.client.gui.shared.label;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class TitleLabel extends JLabel {
  private static final long  serialVersionUID = 1L;
  private static final float LARGE            = 5;
  private static final float SMALL            = 3;
  private static Font        fontLarge        = null;
  private static Font        fontSmall        = null;

  public TitleLabel(final String txt) {
    this(txt, true);
  }

  public TitleLabel(final String txt, final boolean isLarge) {
    super(txt, SwingConstants.CENTER);
    setForeground(Color.WHITE);
    setOpaque(true);
    setBackground(Color.BLACK);

    if (fontLarge == null) {
      createFonts();
    }

    setFont(isLarge ? fontLarge : fontSmall);
  }

  private void createFonts() {
    if (fontLarge == null) {
      fontLarge = getFont();
      fontLarge = fontLarge.deriveFont(fontLarge.getStyle() | Font.BOLD,
          fontLarge.getSize() + LARGE);

      fontSmall = getFont();
      fontSmall = fontSmall.deriveFont(fontSmall.getStyle() | Font.BOLD,
          fontSmall.getSize() + SMALL);
    }
  }

  @Override
  public void setText(final String text) {
    super.setText(" " + text + " ");
  }
}
