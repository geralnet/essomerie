package net.geral.essomerie.client._gui.sysadmin;

import net.geral.essomerie.shared.system.Device;
import net.geral.essomerie.shared.system.DispositivoMonitor;

public class DispositivoMonitorEntry {
  public final Device         dispositivo;
  public DispositivoMonitor[] monitor = null;

  public DispositivoMonitorEntry() {
    dispositivo = new Device();// 0, "[desconhecido]");
  }

  public DispositivoMonitorEntry(final Device d) {
    dispositivo = d;
  }

  public boolean checkId(final int iddispositivo) {
    return (iddispositivo == dispositivo.getId());
  }

  private int countScreens() {
    if (monitor == null) {
      return 0;
    }
    int i = 0;
    for (final DispositivoMonitor dm : monitor) {
      if (dm.hasScreen) {
        i++;
      }
    }
    return i;
  }

  public boolean hasDetails() {
    return (monitor != null);
  }

  @Override
  public String toString() {
    if (!hasDetails()) {
      return dispositivo.getName();
    }
    return dispositivo.getName() + " (" + countScreens() + "/" + monitor.length
        + ")";
  }
}
