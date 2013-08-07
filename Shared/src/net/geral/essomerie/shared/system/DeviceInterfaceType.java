package net.geral.essomerie.shared.system;

public enum DeviceInterfaceType {
  Static,
  DHCP_Static,
  DHCP_Dynamic;

  public static DeviceInterfaceType fromSQL(final String s) {
    return valueOf(s);
  }

  public String toSQL() {
    return name();
  }

  @Override
  public String toString() {
    return name().replaceAll("_", " ");
  }
}
