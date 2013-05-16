package net.geral.essomerie.client._gui.shared.label;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

//TODO chekck & translate
public class TitleLabel extends JLabel {
    private static final long serialVersionUID = 1L;
    private static final float GRANDE = 5;
    private static final float PEQUENA = 3;
    private static Font fontGrande = null;
    private static Font fontPequena = null;

    public TitleLabel(final String txt) {
	this(txt, true);
    }

    public TitleLabel(final String txt, final boolean grande) {
	super(txt, SwingConstants.CENTER);
	setForeground(Color.WHITE);
	setOpaque(true);
	setBackground(Color.BLACK);

	if (fontGrande == null) {
	    criarFontes();
	}

	setFont(grande ? fontGrande : fontPequena);
    }

    private void criarFontes() {
	if (fontGrande == null) {
	    fontGrande = getFont();
	    fontGrande = fontGrande.deriveFont(fontGrande.getStyle()
		    | Font.BOLD, fontGrande.getSize() + GRANDE);

	    fontPequena = getFont();
	    fontPequena = fontPequena.deriveFont(fontPequena.getStyle()
		    | Font.BOLD, fontPequena.getSize() + PEQUENA);
	}
    }
}
