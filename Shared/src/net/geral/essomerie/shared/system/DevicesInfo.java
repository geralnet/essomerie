package net.geral.essomerie.shared.system;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class DevicesInfo implements Serializable {
  private static final long                           serialVersionUID = 1L;
  private final ArrayList<Device>                     devices;
  private final ArrayList<DeviceInterface>            interfaces;
  private final ArrayList<DeviceInterfaceHostname>    hostnames;

  private transient HashMap<Integer, Device>          id2device        = null;
  private transient HashMap<Integer, DeviceInterface> id2interface     = null;

  public DevicesInfo(final ArrayList<Device> devices,
      final ArrayList<DeviceInterface> interfaces,
      final ArrayList<DeviceInterfaceHostname> hostnames) {
    this.devices = devices;
    this.interfaces = interfaces;
    this.hostnames = hostnames;
  }

  public synchronized void createReferences() {
    if (id2device != null) {
      return; // already created
    }
    // create id references (and clear older references)
    id2device = new HashMap<>();
    for (final Device d : devices) {
      id2device.put(d.getId(), d);
      d.clearInterfaces();
    }
    id2interface = new HashMap<>();
    for (final DeviceInterface i : interfaces) {
      id2interface.put(i.getId(), i);
      i.clearHostnames();
    }
    // set interfaces
    for (final DeviceInterface iface : interfaces) {
      final Device d = id2device.get(iface.getIdDevice());
      if (d != null) {
        d.addInterface(iface);
      }
    }
    // set hostnames
    for (final DeviceInterfaceHostname host : hostnames) {
      final DeviceInterface i = id2interface.get(host.getIdInterface());
      if (i != null) {
        i.addHostname(host);
      }
    }
  }

  public ArrayList<Device> getDevices() {
    return new ArrayList<Device>(devices);
  }

  public ArrayList<DeviceInterface> getInterfaces() {
    return new ArrayList<DeviceInterface>(interfaces);
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "[devs=" + devices.size() + "]";
  }
}
