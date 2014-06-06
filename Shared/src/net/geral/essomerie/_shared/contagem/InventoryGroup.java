package net.geral.essomerie._shared.contagem;

import java.io.Serializable;

//TODO translate & check
public class InventoryGroup implements Serializable {
  private static final long      serialVersionUID = 1L;

  public final int               id;
  public final String            nome;

  private final InventoryGroup[] subgrupos;

  public InventoryGroup(final int id, final String nome,
      InventoryGroup[] subgrupos) {
    if (subgrupos == null) { // nao deixar null
      subgrupos = new InventoryGroup[0];
    }

    this.id = id;
    this.nome = nome;
    this.subgrupos = subgrupos;
  }

  public boolean contains(final InventoryGroup other) {
    if (id == other.id) {
      return true;
    }
    for (final InventoryGroup g : subgrupos) {
      if (g.contains(other)) {
        return true;
      }
    }
    return false;
  }

  public InventoryGroup[] getSubGrupos() {
    return subgrupos.clone();
  }

  @Override
  public String toString() {
    return nome;
  }
}
