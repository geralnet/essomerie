package net.geral.essomerie._shared.contagem;

import java.io.Serializable;

//TODO translate & check
public class InventoryItem implements Serializable {
  private static final long serialVersionUID = 1L;

  public final int          id;
  public final String       nome;
  public final String       unidade;
  private float             quantidade;

  private final int[]       grupos;

  public InventoryItem(final int id, final String nome, final String unidade,
      final float quantidade, final int[] grupos) {
    this.id = id;
    this.nome = nome;
    this.unidade = unidade;
    this.quantidade = quantidade;
    this.grupos = grupos;
  }

  public int[] getGruposIds() {
    return grupos.clone();
  }

  public float getQuantidade() {
    return quantidade;
  }

  public boolean inGroup(final int idgrupo) {
    for (final int i : grupos) {
      if (i == idgrupo) {
        return true;
      }
    }
    return false;
  }

  public void setQuantidade(final float novaQuantidade) {
    quantidade = novaQuantidade;
  }
}
