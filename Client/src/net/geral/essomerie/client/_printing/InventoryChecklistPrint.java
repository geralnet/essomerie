package net.geral.essomerie.client._printing;

import java.awt.Font;

import net.geral.essomerie._shared.contagem.InventoryItem;

public class InventoryChecklistPrint extends CRPrintDocument {
  private final InventoryItem[] items;

  public InventoryChecklistPrint(final InventoryItem[] items) {
    this.items = items;
  }

  @Override
  protected void printBody() {
    g.setFont(new Font("monospaced", Font.PLAIN, 7));
    for (final InventoryItem i : items) {
      writeline("[  ] " + i.nome + " (" + i.getQuantidade() + " " + i.unidade
          + ")");
    }
  }
}
