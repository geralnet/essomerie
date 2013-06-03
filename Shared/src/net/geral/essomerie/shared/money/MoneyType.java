package net.geral.essomerie.shared.money;

public class MoneyType {
  private final String name;
  private final long   multiplier;

  public MoneyType(final String name, final long multiplier) {
    this.name = name;
    this.multiplier = multiplier;
  }

  @Deprecated
  public double getDouble() {
    return multiplier / 100.0;
  }

  public long getMultiplier() {
    return multiplier;
  }

  public String getName() {
    return name;
  }
}
