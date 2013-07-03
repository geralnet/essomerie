package net.geral.essomerie._shared.contagem;

import java.io.Serializable;
import java.util.ArrayList;

public class Inventory implements Serializable {
  private static final long serialVersionUID = 1L;

  private ContagemMotivos   motivos;
  private InventoryGroup[]  grupos;
  private InventoryItem[]   itens;

  public Inventory() {
    this(new ContagemMotivos(), new InventoryGroup[0], new InventoryItem[0]);
  }

  public Inventory(final ContagemMotivos motivos,
      final InventoryGroup[] grupos, final InventoryItem[] itens) {
    setMotivos(motivos);
    setGrupos(grupos);
    setItens(itens);
  }

  private InventoryGroup getGrupo(final int id) {
    for (final InventoryGroup g : grupos) {
      if (g.id == id) {
        return g;
      }
    }
    return null;
  }

  public InventoryGroup[] getGrupos() {
    return grupos.clone();
  }

  public InventoryGroup[] getGrupos(final int[] gruposIds) {
    final InventoryGroup[] cg = new InventoryGroup[gruposIds.length];
    for (int i = 0; i < cg.length; i++) {
      cg[i] = getGrupo(gruposIds[i]);
    }
    return cg;
  }

  public InventoryItem getItemById(final int iditem) {
    for (final InventoryItem i : itens) {
      if (i.id == iditem) {
        return i;
      }
    }
    return null;
  }

  public InventoryItem[] getItens() {
    return itens.clone();
  }

  public InventoryItem[] getItens(final InventoryGroup grupo) {
    if (grupo == null) {
      return new InventoryItem[0];
    }

    final ArrayList<InventoryItem> res = new ArrayList<InventoryItem>();
    for (final InventoryItem i : itens) {
      if (i.inGroup(grupo.id)) {
        res.add(i);
      }
    }
    return res.toArray(new InventoryItem[res.size()]);
  }

  public ContagemMotivos getMotivos() {
    return new ContagemMotivos(motivos);
  }

  public void setGrupos(final InventoryGroup[] contagemGrupos) {
    grupos = contagemGrupos;
  }

  public void setItens(final InventoryItem[] contagemItens) {
    itens = contagemItens;
  }

  public void setMotivos(final ContagemMotivos contagemMotivos) {
    motivos = contagemMotivos;
  }

  public void setNovaQuantidade(final int iditem, final float novaQuantidade) {
    for (final InventoryItem ci : itens) {
      if (ci.id == iditem) {
        ci.setQuantidade(novaQuantidade);
      }
    }
  }
}
