package net.geral.essomerie.client.gui.sysop.devices.tables;

import net.geral.essomerie.shared.system.Device;
import net.geral.lib.table.GNTableModel;

public class DevicesModel extends GNTableModel<Device> {
  private static final long serialVersionUID = 1L;

  public DevicesModel() {
    super(true, true, true);
  }

  @Override
  protected Device changeEntry(final Device t, final int columnIndex,
      final Object aValue) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Device createNewEntry() {
    return new Device();
  }

  @Override
  protected Object getValueFor(final Device d, final int columnIndex) {
    switch (columnIndex) {
      case 0:
        return d.getType();
      case 1:
        return d.getName();
      case 2:
        return d.getRole();
      case 3:
        return d.getOS();
      case 4:
        return d.getVersion();
      case 5:
        return d.getCPU();
      case 6:
        return d.getMemory();
      case 7:
        return d.getHostnames();
      case 8:
        return d.getMAC();
      case 9:
        return d.getIP();
      default:
        return "C" + columnIndex;
    }
  }
}
