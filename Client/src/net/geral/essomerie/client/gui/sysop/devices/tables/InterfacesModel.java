package net.geral.essomerie.client.gui.sysop.devices.tables;

import net.geral.essomerie.shared.system.DeviceInterface;
import net.geral.essomerie.shared.system.DeviceInterfaceHostname;
import net.geral.lib.table.GNTableModel;

public class InterfacesModel extends GNTableModel<DeviceInterface> {
  private static final long serialVersionUID = 1L;

  public InterfacesModel() {
    super(true, true, true);
  }

  @Override
  protected DeviceInterface changeEntry(final DeviceInterface t,
      final int columnIndex, final Object aValue) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public DeviceInterface createNewEntry() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  protected Object getValueFor(final DeviceInterface i, final int columnIndex) {
    switch (columnIndex) {
      case 0:
        return i.getName();
      case 1:
        return i.getMAC();
      case 2:
        return i.getType();
      case 3:
        return i.getIP();
      case 4:
        return DeviceInterfaceHostname.hostnames2string(i.getHostnames());
      default:
        return "C" + columnIndex;
    }
  }
}
