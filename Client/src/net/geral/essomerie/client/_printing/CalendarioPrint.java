package net.geral.essomerie.client._printing;

//TODO translate&check

import java.awt.Font;
import java.util.ArrayList;

import net.geral.essomerie._shared.calendario.CalendarEvent;
import net.geral.jodatime.JodaTimeUtils;

import org.joda.time.LocalDate;

// TODO translate & check
public class CalendarioPrint extends CRPrintDocument {
  private final LocalDate       data;
  private final CalendarEvent[] eventos;

  public CalendarioPrint(final LocalDate _data,
      final ArrayList<CalendarEvent> _eventos) {
    data = _data;
    eventos = _eventos.toArray(new CalendarEvent[_eventos.size()]);
  }

  @Override
  protected void printBody() {
    g.setFont(new Font("SansSerif", Font.PLAIN, 8));
    String s = JodaTimeUtils.DMA.print(data) + " (" + data.getDayOfWeek() + ")"; // TODO
                                                                                 // nome
                                                                                 // semana
    centralizar(s, new Font("SansSerif", Font.BOLD, 10));
    s = (eventos.length > 1) ? "s" : "";
    centralizar(eventos.length + " evento" + s + ".");
    separator();
    newline();
    for (final CalendarEvent e : eventos) {
      writeline(e.getEvent());
      newline();
    }
  }
}
