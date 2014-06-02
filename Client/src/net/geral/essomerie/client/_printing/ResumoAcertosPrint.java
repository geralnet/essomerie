package net.geral.essomerie.client._printing;

import java.awt.Font;

import net.geral.essomerie._shared.contagem.Inventory;
import net.geral.essomerie._shared.contagem.InventoryItem;
import net.geral.essomerie._shared.contagem.InventoryLogEntry;
import net.geral.essomerie.client.core.Client;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

//TODO translate and check
public class ResumoAcertosPrint extends CRPrintDocument {
  private final InventoryLogEntry[] registros;
  private final Inventory           contagem;

  public ResumoAcertosPrint(final InventoryLogEntry[] r, final Inventory c) {
    registros = r;
    contagem = c;
  }

  @Override
  protected void printBody() {
    g.setFont(new Font("monospaced", Font.PLAIN, 7));
    LocalDate last = null;
    for (final InventoryLogEntry r : registros) {
      if (!contagem.getMotivos().get(r.tipo, r.idmotivo).equals("acerto")) {
        continue; // apenas acertos interessam
      }

      final LocalDate d = new LocalDate(r.datahora);
      if (!d.equals(last)) {
        if (last == null) {
          drawHorizontalLine();
        }
        last = d;
        final String s = d.toString() + " (" + d.toString() + ")";
        writeCentralized(s, new Font("SansSerif", Font.BOLD, 10));
      }

      final LocalTime t = new LocalTime(r.datahora);
      final InventoryItem item = contagem.getItemById(r.iditem);
      final String nome = (item == null) ? "ITEM#" + r.iditem : item.nome;
      final String u = (item == null) ? "?" : item.unidade;
      final String por = Client.cache().users().get(r.idusuario).getUsername();
      final String f = String.format("%s %-3s %15s: %s %s", t.toString(), por,
          nome, r.getAlteracaoString("->"), u);
      writeline(f);
    }
  }
}
