package net.geral.essomerie.shared.warehouse;

import java.io.Serializable;

public class WarehouseItem implements Serializable {
  private static final long serialVersionUID = 1L;

  public final int          id;
  public final int          idgroup;
  public final String       name;
  public final String       unit;
  public final float        minimum;
  public final float        maximum;

  private float             quantity;

  public WarehouseItem(final int id, final int idgroup, final String name,
      final String unit, final float quantity, final float minimum,
      final float maximum) {
    this.id = id;
    this.idgroup = idgroup;
    this.name = name;
    this.unit = unit;
    this.minimum = minimum;
    this.maximum = maximum;
    this.quantity = quantity;
  }

  public float getQuantity() {
    return quantity;
  }

  public void setQuantity(final float newQuantity) {
    quantity = newQuantity;
  }

  @Override
  public String toString() {
    return "WarehouseItem[" + id + ";" + name + ";" + quantity + "(" + minimum
        + "~" + maximum + ");" + unit + "]";
  }

  public WarehouseItem withId(final int newId) {
    return new WarehouseItem(newId, idgroup, name, unit, quantity, minimum,
        maximum);
  }

  public WarehouseItem withMaximum(final float newMaximum) {
    return new WarehouseItem(id, idgroup, name, unit, quantity, minimum,
        newMaximum);
  }

  public WarehouseItem withMinimum(final float newMinimum) {
    return new WarehouseItem(id, idgroup, name, unit, quantity, newMinimum,
        maximum);
  }

  public WarehouseItem withName(final String newName) {
    return new WarehouseItem(id, idgroup, newName, unit, quantity, minimum,
        maximum);
  }

  public WarehouseItem withUnit(final String newUnit) {
    return new WarehouseItem(id, idgroup, name, newUnit, quantity, minimum,
        maximum);
  }
}
