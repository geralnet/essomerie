package net.geral.essomerie.shared.system;

import org.joda.time.LocalDateTime;

//FIXME rename or fix or delete
@Deprecated
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
