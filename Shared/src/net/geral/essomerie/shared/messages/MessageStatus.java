package net.geral.essomerie.shared.messages;

import java.io.Serializable;

import org.joda.time.LocalDateTime;

public class MessageStatus implements Serializable {
  private static final long  serialVersionUID = 1L;
  public final int           iduser;
  public final LocalDateTime read;

  public MessageStatus(final int iduser, final LocalDateTime read) {
    this.iduser = iduser;
    this.read = read;
  }
}
