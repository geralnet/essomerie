package net.geral.essomerie.shared.system;

import java.io.Serializable;
import java.util.ArrayList;

public class Device implements Serializable {
  private static final long                    serialVersionUID = 1L;

  private final int                            id;
  private final String                         type;
  private final String                         name;
  private final String                         role;
  private final String                         os;
  private final String                         version;
  private final String                         cpu;
  private final String                         memory;
  private final String                         comments;
  private final String                         instructions;
  private final String                         configuration;

  private transient ArrayList<DeviceInterface> interfaces       = null;

  public Device() {
    this(0, "", "", "", "", "", "", "", "", "", "");
  }

  public Device(final int id, final String type, final String name,
      final String role, final String os, final String version,
      final String cpu, final String memory, final String comments,
      final String instructions, final String configuration) {
    this.id = id;
    this.type = type;
    this.name = name;
    this.role = role;
    this.os = os;
    this.version = version;
    this.cpu = cpu;
    this.memory = memory;
    this.comments = comments;
    this.instructions = instructions;
    this.configuration = configuration;
  }

  public synchronized void addInterface(final DeviceInterface iface) {
    interfaces.add(iface);
  }

  public synchronized void clearInterfaces() {
    interfaces = new ArrayList<>();
  }

  public String getComments() {
    return comments;
  }

  public String getConfiguration() {
    return configuration;
  }

  public String getCPU() {
    return cpu;
  }

  public synchronized String getHostnames() {
    final ArrayList<DeviceInterfaceHostname> hosts = new ArrayList<>();
    for (final DeviceInterface iface : interfaces) {
      hosts.addAll(iface.getHostnames());
    }
    return DeviceInterfaceHostname.hostnames2string(hosts);
  }

  public int getId() {
    return id;
  }

  public String getInstructions() {
    return instructions;
  }

  public synchronized DeviceInterface[] getInterfaces() {
    if (interfaces == null) {
      return null;
    }
    return interfaces.toArray(new DeviceInterface[interfaces.size()]);
  }

  public synchronized String getIP() {
    final int n = interfaces.size();
    if (n == 0) {
      return "";
    }
    if (n == 1) {
      return interfaces.get(0).getIP().toString();
    }
    final StringBuilder sb = new StringBuilder();
    sb.append("[");
    sb.append(n);
    sb.append("] ");
    for (int i = 0; i < n; i++) {
      if (i > 0) {
        sb.append(" ; ");
      }
      sb.append(interfaces.get(i).getIP().toString());
    }
    return sb.toString();
  }

  public synchronized String getMAC() {
    final int n = interfaces.size();
    if (n == 0) {
      return "";
    }
    if (n == 1) {
      return interfaces.get(0).getMAC().toString();
    }
    final StringBuilder sb = new StringBuilder();
    sb.append("[");
    sb.append(n);
    sb.append("] ");
    for (int i = 0; i < n; i++) {
      if (i > 0) {
        sb.append(" ; ");
      }
      sb.append(interfaces.get(i).getMAC().toString());
    }
    return sb.toString();
  }

  public String getMemory() {
    return memory;
  }

  public String getName() {
    return name;
  }

  public String getOS() {
    return os;
  }

  public String getRole() {
    return role;
  }

  public String getType() {
    return type;
  }

  public String getVersion() {
    return version;
  }

  @Override
  public String toString() {
    return name;
  }
}
