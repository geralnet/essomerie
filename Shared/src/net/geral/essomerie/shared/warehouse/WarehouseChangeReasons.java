package net.geral.essomerie.shared.warehouse;

import java.io.Serializable;


public class WarehouseChangeReasons implements Serializable {
  private static final long      serialVersionUID     = 1L;
  public static final int        MAX_REASONS_PER_TYPE = 4;

  private final WarehouseChangeReason[] reasons;

  public WarehouseChangeReasons() {
    this(new WarehouseChangeReason[0]);
  }

  public WarehouseChangeReasons(final WarehouseChangeReason[] wcr) {
    reasons = wcr;
  }

  public WarehouseChangeReasons(final WarehouseChangeReasons wcr) {
    this(wcr.reasons.clone());
  }

  public String get(final char mode, final int idreason) {
    for (final WarehouseChangeReason wcr : reasons) {
      if (wcr.isMode(mode) && (wcr.id == idreason)) {
        return wcr.description;
      }
    }
    return mode + "#" + idreason;
  }

  public WarehouseChangeReason[] getAll() {
    return reasons.clone();
  }

  public WarehouseChangeReason[] getReasonsForMode(final char mode) {
    final WarehouseChangeReason[] result = new WarehouseChangeReason[MAX_REASONS_PER_TYPE];
    for (int i = 0; i < MAX_REASONS_PER_TYPE; i++) {
      result[i] = null;
    }
    for (final WarehouseChangeReason wcr : reasons) {
      if (wcr.isMode(mode)) {
        result[wcr.id - 1] = wcr;
      }
    }
    return result;
  }
}
