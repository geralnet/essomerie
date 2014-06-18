package net.geral.essomerie.client._printing;

import java.awt.Font;

import net.geral.essomerie.client.core.Client;
import net.geral.essomerie.shared.warehouse.Warehouse;
import net.geral.essomerie.shared.warehouse.WarehouseChangeLogEntry;
import net.geral.essomerie.shared.warehouse.WarehouseItem;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

//TODO translate and check
public class ResumoAcertosPrint extends CRPrintDocument {
  private final WarehouseChangeLogEntry[] registros;
  private final Warehouse                 contagem;

  public ResumoAcertosPrint(final WarehouseChangeLogEntry[] r, final Warehouse c) {
    registros = r;
    contagem = c;
  }

  @Override
  protected void printBody() {
    g.setFont(new Font("monospaced", Font.PLAIN, 7));
    LocalDate last = null;
    for (final WarehouseChangeLogEntry r : registros) {
      if (!contagem.getReasons().get(r.mode, r.idreason).equals("acerto")) {
        continue; // apenas acertos interessam
      }

      final LocalDate d = new LocalDate(r.when);
      if (!d.equals(last)) {
        if (last == null) {
          drawHorizontalLine();
        }
        last = d;
        final String s = d.toString() + " (" + d.toString() + ")";
        writeCentralized(s, new Font("SansSerif", Font.BOLD, 10));
      }

      final LocalTime t = new LocalTime(r.when);
      final WarehouseItem item = contagem.getItem(r.iditem);
      final String nome = (item == null) ? "ITEM#" + r.iditem : item.name;
      final String u = (item == null) ? "?" : item.unit;
      final String por = Client.cache().users().get(r.iduser).getUsername();
      final String f = String.format("%s %-3s %15s: %s %s", t.toString(), por,
          nome, r.toString(true), u);
      writeline(f);
    }
  }
}
