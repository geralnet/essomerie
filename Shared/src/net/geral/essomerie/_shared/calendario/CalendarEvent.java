package net.geral.essomerie._shared.calendario;

import java.io.Serializable;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

// TODO translage check
public class CalendarEvent implements Serializable {
  private final int           id;
  private final LocalDate     data;
  private final String        evento;
  private final int           log_usuario;
  private final LocalDateTime log_datahora;

  private final CalendarEvent referencia;

  public CalendarEvent(final int id, final LocalDate data, final String evento,
      final int log_usuario, final LocalDateTime log_datahora,
      final CalendarEvent referencia) {
    this.id = id;
    this.data = data;
    this.evento = evento;
    this.log_usuario = log_usuario;
    this.log_datahora = log_datahora;
    this.referencia = referencia;
  }

  public CalendarEvent(final LocalDate date) {
    this(0, date, "", 0, null, null);
  }

  public LocalDate getDate() {
    return data;
  }

  public String getEvent() {
    return evento;
  }

  public int getId() {
    return id;
  }

  public LocalDateTime getLogDataHora() {
    return log_datahora;
  }

  public int getLogUsuario() {
    return log_usuario;
  }

  public CalendarEvent getPrevious() {
    return referencia;
  }
}
