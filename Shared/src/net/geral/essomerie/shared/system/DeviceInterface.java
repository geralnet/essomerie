package net.geral.essomerie.shared.system;

import java.io.Serializable;
import java.util.ArrayList;

import net.geral.lib.networking.IPv4;
import net.geral.lib.networking.MAC;

public class DeviceInterface implements Serializable {
  private static final long                            serialVersionUID = 1L;

  private final int                                    id;
  private final int                                    iddevice;
  private final String                                 name;
  private final MAC                                    mac;
  private final DeviceInterfaceType                    type;
  private final IPv4                                   ipv4;

  private transient ArrayList<DeviceInterfaceHostname> hostnames        = null;

  public DeviceInterface(final int id, final int iddevice, final String name,
      final MAC mac, final DeviceInterfaceType type, final IPv4 ipv4) {
    this.id = id;
    this.iddevice = iddevice;
    this.name = name;
    this.mac = mac;
    this.type = type;
    this.ipv4 = ipv4;
  }

  public synchronized void addHostname(final DeviceInterfaceHostname host) {
    hostnames.add(host);
  }

  public synchronized void clearHostnames() {
    hostnames = new ArrayList<>();
  }

  public synchronized ArrayList<DeviceInterfaceHostname> getHostnames() {
    return new ArrayList<>(hostnames);
  }

  public int getId() {
    return id;
  }

  public int getIdDevice() {
    return iddevice;
  }

  public IPv4 getIP() {
    return ipv4;
  }

  public MAC getMAC() {
    return mac;
  }

  public String getName() {
    return name;
  }

  public DeviceInterfaceType getType() {
    return type;
  }
}
