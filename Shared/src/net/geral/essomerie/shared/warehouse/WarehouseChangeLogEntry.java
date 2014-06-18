package net.geral.essomerie.shared.warehouse;

import java.io.Serializable;

import org.joda.time.LocalDateTime;

public final class WarehouseChangeLogEntry implements Serializable {
  private static final long   serialVersionUID = 1L;
  private static final String ARROW_SCREEN     = "\u2192";
  private static final String ARROW_PRINT      = "->";

  public static String toQuantityString(final float qty) {
    final int i = (int) qty;
    if ((qty - i) == 0f) {
      return String.valueOf(i); // no decimal part
    }
    // TODO internationalisation
    return String.valueOf(qty).replace('.', ','); // TODO with decimal part
  }

  public final int           id;
  public final int           iditem;
  public final int           iduser;
  public final char          mode;
  public final int           idreason;
  public final float         quantityBefore;
  public final float         quantityChange;
  public final LocalDateTime when;
  public final String        comments;

  public WarehouseChangeLogEntry(final int id, final int iditem,
      final int iduser, final char mode, final int idreason,
      final float quantityBefore, final float quantityChange,
      final LocalDateTime when, final String comments) {
    this.id = id;
    this.iditem = iditem;
    this.iduser = iduser;
    this.mode = mode;
    this.idreason = idreason;
    this.quantityBefore = quantityBefore;
    this.quantityChange = quantityChange;
    this.when = when;
    this.comments = comments;
  }

  @Override
  public String toString() {
    return toString(false);
  }

  public String toString(final boolean print) {
    switch (mode) {
      case '+':
        return toQuantityString(quantityBefore) + " + "
            + toQuantityString(quantityChange) + " = "
            + toQuantityString(quantityBefore + quantityChange);
      case '-':
        return toQuantityString(quantityBefore) + " - "
            + toQuantityString(quantityChange) + " = "
            + toQuantityString(Math.max(quantityBefore - quantityChange, 0));
      case '=':
        return toQuantityString(quantityBefore) + " "
            + (print ? ARROW_PRINT : ARROW_SCREEN) + " "
            + toQuantityString(quantityChange);
      default:
        return "[ERROR " + mode + "]";
    }
  }
}
