package net.geral.essomerie.shared.money;

public class MoneyType {
  private final String name;
  private final long   multiplier;

  public MoneyType(final String name, final long multiplier) {
    this.name = name;
    this.multiplier = multiplier;
  }

  public Money getMoney(final long units) {
    return Money.fromLong(units * multiplier);
  }

  public long getMultiplier() {
    return multiplier;
  }

  public String getName() {
    return name;
  }

  public long getQuantityOf(final Money value) {
    return value.toLong() / multiplier;
  }
}
