package net.geral.essomerie.shared.person;

import java.io.Serializable;

import org.joda.time.LocalDateTime;

public class PersonLogDetails implements Serializable {
  private static final long   serialVersionUID = 1L;

  private final int           idperson;
  private final LocalDateTime createdWhen;
  private final LocalDateTime updatedWhen;
  private final int           createdBy;
  private final int           updatedBy;

  public PersonLogDetails(final int idperson, final LocalDateTime createdWhen,
      final LocalDateTime updatedWhen, final int createdBy, final int updatedBy) {
    this.idperson = idperson;
    this.createdWhen = createdWhen;
    this.updatedWhen = updatedWhen;
    this.createdBy = createdBy;
    this.updatedBy = updatedBy;
  }

  public int getCreatedBy() {
    return createdBy;
  }

  public LocalDateTime getCreatedWhen() {
    return createdWhen;
  }

  public int getIdPerson() {
    return idperson;
  }

  public int getUpdatedBy() {
    return updatedBy;
  }

  public LocalDateTime getUpdatedWhen() {
    return updatedWhen;
  }
}
