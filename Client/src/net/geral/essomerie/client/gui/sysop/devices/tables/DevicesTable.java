package net.geral.essomerie.client.gui.sysop.devices.tables;

import net.geral.essomerie.client.core.Client;
import net.geral.essomerie.client.core.configuration.CoreConfiguration;
import net.geral.essomerie.client.resources.S;
import net.geral.essomerie.shared.system.Device;
import net.geral.lib.table.GNTable;

public class DevicesTable extends GNTable<DevicesModel> {
  private static final long serialVersionUID = 1L;

  public DevicesTable() {
    super(new DevicesModel());
  }

  @Override
  protected void createColumns(final Object... params) {
    final CoreConfiguration c = Client.config();
    createColumn(S.SYSOP_DEVICES_TYPE.s(),
        c.TableColumnWidth_SysOp_Devices_Type, c.TableColumnWidth_Default);
    createColumn(S.SYSOP_DEVICES_NAME.s(),
        c.TableColumnWidth_SysOp_Devices_Name, c.TableColumnWidth_Default);
    createColumn(S.SYSOP_DEVICES_ROLE.s(),
        c.TableColumnWidth_SysOp_Devices_Role, c.TableColumnWidth_Default);
    createColumn(S.SYSOP_DEVICES_OS.s(), c.TableColumnWidth_SysOp_Devices_OS,
        c.TableColumnWidth_Default);
    createColumn(S.SYSOP_DEVICES_VERSION.s(),
        c.TableColumnWidth_SysOp_Devices_Version, c.TableColumnWidth_Default);
    createColumn(S.SYSOP_DEVICES_CPU.s(), c.TableColumnWidth_SysOp_Devices_CPU,
        c.TableColumnWidth_Default);
    createColumn(S.SYSOP_DEVICES_MEMORY.s(),
        c.TableColumnWidth_SysOp_Devices_Memory, c.TableColumnWidth_Default);
    createColumn(S.SYSOP_DEVICES_HOSTNAME.s(),
        c.TableColumnWidth_SysOp_Devices_Hostname, c.TableColumnWidth_Default);
    createColumn(S.SYSOP_DEVICES_MAC.s(), c.TableColumnWidth_SysOp_Devices_MAC,
        c.TableColumnWidth_Default);
    createColumn(S.SYSOP_DEVICES_IP.s(), c.TableColumnWidth_SysOp_Devices_IP,
        c.TableColumnWidth_Default);
  }

  @Override
  protected boolean deleteClicked(final int viewRow, final int tableColumn) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public String getNewEntryText(final int columnIndex) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Device getSelected() {
    return (Device) super.getSelected();
  }
}
