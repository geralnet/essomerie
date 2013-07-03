package net.geral.essomerie._shared.contagem;

import java.io.Serializable;

public class InventoryLog implements Serializable {
  private static final long         serialVersionUID = 1L;

  public final int                  iditem;
  private final InventoryLogEntry[] registros;

  public InventoryLog(final int item, final int nRegistros) {
    iditem = item;
    registros = new InventoryLogEntry[nRegistros];
  }

  public int count() {
    return registros.length;
  }

  public InventoryLogEntry get(final int rowIndex) {
    return registros[rowIndex];
  }

  public InventoryLogEntry[] getRegistros() {
    return registros.clone();
  }

  public void set(final int i, final InventoryLogEntry hr) {
    registros[i] = hr;
  }
}