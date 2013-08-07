package net.geral.essomerie.client.core.events.listeners;

import java.util.EventListener;

import net.geral.essomerie.shared.system.DevicesInfo;

public interface SysopListener extends EventListener {
  void sysopInformedDevicesInfo(DevicesInfo dev);
}
