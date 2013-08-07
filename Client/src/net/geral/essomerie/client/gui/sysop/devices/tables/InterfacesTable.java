package net.geral.essomerie.client.gui.sysop.devices.tables;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.table.TableColumn;

import net.geral.essomerie.client.core.Client;
import net.geral.essomerie.client.core.configuration.CoreConfiguration;
import net.geral.essomerie.client.resources.S;
import net.geral.essomerie.shared.system.DeviceInterfaceType;
import net.geral.lib.table.GNTable;

public class InterfacesTable extends GNTable<InterfacesModel> {
  private static final long serialVersionUID = 1L;

  public InterfacesTable() {
    super(new InterfacesModel());
  }

  @Override
  protected void createColumns() {
    final CoreConfiguration c = Client.config();
    createColumn(S.SYSOP_DEVICES_INTERFACES_NAME.s(),
        c.TableColumnWidth_SysOp_Devices_Interfaces_Name,
        c.TableColumnWidth_Default);
    createColumn(S.SYSOP_DEVICES_INTERFACES_MAC.s(),
        c.TableColumnWidth_SysOp_Devices_Interfaces_MAC,
        c.TableColumnWidth_Default);
    createColumn(S.SYSOP_DEVICES_INTERFACES_TYPE.s(),
        c.TableColumnWidth_SysOp_Devices_Interfaces_Type,
        c.TableColumnWidth_Default);
    createColumn(S.SYSOP_DEVICES_INTERFACES_IP.s(),
        c.TableColumnWidth_SysOp_Devices_Interfaces_IP,
        c.TableColumnWidth_Default);
    createColumn(S.SYSOP_DEVICES_INTERFACES_HOSTNAMES.s(),
        c.TableColumnWidth_SysOp_Devices_Interfaces_Hostnames,
        c.TableColumnWidth_Default);

    final TableColumn tc = getColumnModel().getColumn(2);
    final JComboBox<DeviceInterfaceType> combo = new JComboBox<>();
    for (final DeviceInterfaceType t : DeviceInterfaceType.values()) {
      combo.addItem(t);
    }
    tc.setCellEditor(new DefaultCellEditor(combo));
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
}
