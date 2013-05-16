package net.geral.essomerie.client._gui.sysadmin;

import net.geral.essomerie._shared.dispositivos.Dispositivo;
import net.geral.essomerie._shared.dispositivos.DispositivoMonitor;

public class DispositivoMonitorEntry {
	public final Dispositivo	dispositivo;
	public DispositivoMonitor[]	monitor	= null;

	public DispositivoMonitorEntry() {
		dispositivo = new Dispositivo(0, "[desconhecido]");
	}

	public DispositivoMonitorEntry(final Dispositivo d) {
		dispositivo = d;
	}

	public boolean checkId(final int iddispositivo) {
		return (iddispositivo == dispositivo.id);
	}

	private int countScreens() {
		if (monitor == null) { return 0; }
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
		if (!hasDetails()) { return dispositivo.nome; }
		return dispositivo.nome + " (" + countScreens() + "/" + monitor.length + ")";
	}
}
