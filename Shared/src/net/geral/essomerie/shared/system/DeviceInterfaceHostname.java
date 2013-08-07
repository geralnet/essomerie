package net.geral.essomerie.shared.system;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class DeviceInterfaceHostname implements Serializable,
    Comparable<DeviceInterfaceHostname> {
  private static final long serialVersionUID = 1L;

  public static String hostnames2string(
      final ArrayList<DeviceInterfaceHostname> hosts) {
    if (hosts == null) {
      return "";
    }
    final int n = hosts.size();
    if (n == 0) {
      return "";
    }
    if (n == 1) {
      return hosts.get(0).getHostname();
    }
    Collections.sort(hosts);
    final StringBuilder sb = new StringBuilder();
    sb.append("[");
    sb.append(n);
    sb.append("] ");
    for (int i = 0; i < n; i++) {
      if (i > 0) {
        sb.append(" ; ");
      }
      sb.append(hosts.get(i).getHostname());
    }
    return sb.toString();
  }

  private final int    id;
  private final int    idinterface;
  private final String hostname;

  private final int    order;

  public DeviceInterfaceHostname(final int id, final int idinterface,
      final String hostname, final int order) {
    this.id = id;
    this.idinterface = idinterface;
    this.hostname = hostname;
    this.order = order;
  }

  @Override
  public int compareTo(final DeviceInterfaceHostname o) {
    int r;

    r = Integer.compare(this.order, o.order);
    if (r != 0) {
      return r;
    }

    r = this.hostname.compareTo(o.hostname);
    if (r != 0) {
      return r;
    }

    r = Integer.compare(this.idinterface, o.idinterface);
    if (r != 0) {
      return r;
    }

    return Integer.compare(this.id, o.id);
  }

  public String getHostname() {
    return hostname;
  }

  public int getId() {
    return id;
  }

  public int getIdInterface() {
    return idinterface;
  }

  public int getOrder() {
    return order;
  }
}
