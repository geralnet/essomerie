package net.geral.essomerie._shared.dispositivos;

import org.joda.time.LocalDateTime;

public class DispositivoMonitor {
  public final int           id;
  public final LocalDateTime datahora;
  public final long          uptime;
  public final boolean       hasScreen;

  public DispositivoMonitor(final int id, final LocalDateTime datahora,
      final long uptime, final boolean hasScreen) {
    this.id = id;
    this.datahora = datahora;
    this.uptime = uptime;
    this.hasScreen = hasScreen;
  }

  @Override
  public String toString() {
    return /* FIXME GNJoda.DMAHMS.print(datahora) + */" (" + id + ")";
  }
}
