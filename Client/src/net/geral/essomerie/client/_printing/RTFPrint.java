package net.geral.essomerie.client._printing;

import java.awt.Dimension;
import java.awt.Graphics2D;

import net.geral.essomerie.client.gui.shared.rtf.RTFPane;

public class RTFPrint extends CRPrintDocument {
  private static final double SCALA = 0.6;
  private final RTFPane       text;

  public RTFPrint(final RTFPane tp) {
    final RTFPane pane = new RTFPane(tp.getRTF());
    final Dimension d = new Dimension((int) (printWidth / SCALA),
        Integer.MAX_VALUE);
    pane.setSize(d);
    d.height = pane.getPreferredSize().height;
    pane.setSize(d);
    text = pane;
  }

  @Override
  protected void printBody() {
    final Graphics2D g2 = (Graphics2D) g.create();
    g2.scale(SCALA, SCALA);
    text.print(g2);
    feed((int) Math.ceil(text.getSize().height * SCALA));
  }
}
