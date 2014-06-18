package net.geral.essomerie.shared.warehouse;

import java.io.Serializable;

public class WarehouseQuantityChange implements Serializable {
  private static final long serialVersionUID = 1L;

  public final int          iditem;
  public final char         mode;
  public final int          idreason;
  public final float        quantity;
  public final String       comments;

  public WarehouseQuantityChange(final int iditem, final char mode,
      final int idreason, final float quantity, final String comments) {
    this.iditem = iditem;
    this.mode = mode;
    this.idreason = idreason;
    this.quantity = quantity;
    this.comments = comments;
  }
}
