package net.geral.essomerie.shared.calendar;

import java.io.Serializable;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

public class CalendarEvent implements Serializable {
  private static final long   serialVersionUID = 1L;

  private final int           id;
  private final LocalDate     date;
  private final String        message;
  private final int           createdBy;
  private final LocalDateTime createdWhen;
  private final CalendarEvent previous;

  public CalendarEvent(final int id, final LocalDate date,
      final String message, final int createdBy,
      final LocalDateTime createdWhen, final CalendarEvent reference) {
    this.id = id;
    this.date = date;
    this.message = message;
    this.createdBy = createdBy;
    this.createdWhen = createdWhen;
    this.previous = reference;
  }

  public CalendarEvent(final LocalDate date) {
    this(0, date, "", 0, null, null);
  }

  public int getCreatedBy() {
    return createdBy;
  }

  public LocalDateTime getCreatedWhen() {
    return createdWhen;
  }

  public LocalDate getDate() {
    return date;
  }

  public int getId() {
    return id;
  }

  public String getMessage() {
    return message;
  }

  public CalendarEvent getPrevious() {
    return previous;
  }
}
