package net.geral.essomerie.shared.warehouse;

import java.io.Serializable;

public class WarehouseChangeReason implements Serializable {
  private static final long serialVersionUID = 1L;

  private static void validateMode(final char mode) {
    switch (mode) {
      case '+':
      case '-':
      case '=':
        return;
      default:
        throw new IllegalArgumentException(
            "Invalid WarehouseChangeReason mode: " + mode);
    }
  }

  public final int    id;
  public final char   mode;

  public final String description;

  public WarehouseChangeReason(final int id, final char mode,
      final String description) {
    validateMode(mode);
    this.id = id;
    this.mode = mode;
    this.description = description;
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final WarehouseChangeReason other = (WarehouseChangeReason) obj;
    if (id != other.id) {
      return false;
    }
    if (description == null) {
      if (other.description != null) {
        return false;
      }
    } else if (!description.equals(other.description)) {
      return false;
    }
    if (mode != other.mode) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = (prime * result) + id;
    result = (prime * result)
        + ((description == null) ? 0 : description.hashCode());
    result = (prime * result) + mode;
    return result;
  }

  public boolean isMode(final char m) {
    validateMode(m);
    return (m == mode);
  }
}
