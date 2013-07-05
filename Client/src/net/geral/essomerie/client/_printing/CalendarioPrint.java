package net.geral.essomerie.client._printing;

//TODO translate&check

import java.awt.Font;
import java.util.ArrayList;

import net.geral.essomerie.client.resources.S;
import net.geral.essomerie.shared.calendar.CalendarEvent;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;

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
    String s = data
        .toString(DateTimeFormat.forPattern(S.FORMAT_DATE_SIMPLE.s()))
        + " (" + data.getDayOfWeek() + ")"; // TODO
                                            // nome
                                            // semana
    writeCentralized(s, new Font("SansSerif", Font.BOLD, 10));
    s = (eventos.length > 1) ? "s" : "";
    writeCentralized(eventos.length + " evento" + s + ".");
    drawHorizontalLine();
    newline();
    for (final CalendarEvent e : eventos) {
      writeline(e.getMessage());
      newline();
    }
  }
}
