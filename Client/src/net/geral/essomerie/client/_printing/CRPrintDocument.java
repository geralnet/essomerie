package net.geral.essomerie.client._printing;

import java.awt.Font;

import net.geral.essomerie._shared.CRUtil;
import net.geral.essomerie._shared.User;
import net.geral.essomerie.client.core.Client;
import net.geral.lib.printing.ContinuousPaperPrintDocument;

import org.joda.time.LocalDateTime;

public abstract class CRPrintDocument extends ContinuousPaperPrintDocument {
  public static final Font FOOTER_FONT = new Font("SansSerif", Font.PLAIN, 6);

  @Override
  protected final void print() {
    printHeader();
    printBody();
    printFooter();
  }

  protected abstract void printBody();

  private void printFooter() {
    if (next_character_x != 0) {
      newline();
    }
    g.setFont(new Font("SansSerif", Font.PLAIN, 4));
    final User logged = Client.cache().users().getLogged();
    final LocalDateTime now = LocalDateTime.now();
    drawHorizontalLine();
    writeline("Impresso por " + logged.getName() + " ("
        + CRUtil.getComputador("???") + ") em " + now.toString() + ".");
  }

  private void printHeader() {
    drawImage("header", 0, 0, 203);
    feed(separadorPadding);
  }
}
