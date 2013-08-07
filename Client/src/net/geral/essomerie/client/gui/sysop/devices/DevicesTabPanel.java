package net.geral.essomerie.client.gui.sysop.devices;

import java.awt.BorderLayout;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JSplitPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.geral.essomerie.client.core.Client;
import net.geral.essomerie.client.core.events.Events;
import net.geral.essomerie.client.core.events.listeners.SysopListener;
import net.geral.essomerie.client.gui.main.TabPanel;
import net.geral.essomerie.client.gui.sysop.devices.tables.DevicesTable;
import net.geral.essomerie.client.resources.S;
import net.geral.essomerie.shared.system.Device;
import net.geral.essomerie.shared.system.DevicesInfo;

import org.apache.log4j.Logger;

public class DevicesTabPanel extends TabPanel implements SysopListener,
    ListSelectionListener {
  private static final long       serialVersionUID = 1L;
  private static final Logger     logger           = Logger
                                                       .getLogger(DevicesTabPanel.class);
  private final DevicesTable      tableDevices;
  private final DeviceEditorPanel editor;

  public DevicesTabPanel() {
    setLayout(new BorderLayout(0, 0));

    final JSplitPane topSplitPane = new JSplitPane();
    topSplitPane.setDividerLocation(300);
    topSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
    add(topSplitPane);

    tableDevices = new DevicesTable();
    topSplitPane.setLeftComponent(tableDevices.getScroll());

    editor = new DeviceEditorPanel();
    topSplitPane.setRightComponent(editor);
    tableDevices.getSelectionModel().addListSelectionListener(this);
  }

  @Override
  public String getTabText() {
    return S.SYSOP_DEVICES.s();
  }

  @Override
  public void sysopInformedDevicesInfo(final DevicesInfo dev) {
    final ArrayList<Device> devs = dev.getDevices();
    dev.createReferences();
    tableDevices.getModel().setData(devs.toArray(new Device[devs.size()]));
  }

  @Override
  public void tabClosed() {
    Events.sysop().removeListener(this);
  }

  @Override
  public boolean tabCloseRequest() {
    return true;
  }

  @Override
  public void tabCreated() {
    Events.sysop().addListener(this);
    try {
      Client.connection().sysop().requestDevicesInfo();
    } catch (final IOException e) {
      logger.warn(e, e);
    }
  }

  @Override
  public void valueChanged(final ListSelectionEvent e) {
    if (e.getValueIsAdjusting()) {
      return;
    }
    final Device d = tableDevices.getSelected();
    editor.setDevice(d);
  }
}
