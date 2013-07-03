package net.geral.essomerie.shared.messages;

import java.io.Serializable;
import java.util.Vector;

import org.joda.time.LocalDateTime;

public class Message implements Comparable<Message>, Serializable {
  private static final long           serialVersionUID = 1L;

  private final int                   id;
  private int                         from             = 0;
  private int                         to               = 0;
  private String                      message          = "";
  private LocalDateTime               sent             = new LocalDateTime();
  private final Vector<MessageStatus> status           = new Vector<MessageStatus>();

  public Message(final int idmessage) {
    id = idmessage;
  }

  public Message(final int _id, final int _from, final int _to,
      final LocalDateTime _sent, final String _message,
      final Vector<MessageStatus> _status) {
    id = _id;
    from = _from;
    to = _to;
    sent = _sent;
    message = _message;
    status.addAll(_status);
  }

  public void addStatus(final MessageStatus _status) {
    status.add(_status);
  }

  @Override
  public int compareTo(final Message other) {
    return sent.compareTo(other.sent);
  }

  public int getFrom() {
    return from;
  }

  public int getId() {
    return id;
  }

  public String getMessage() {
    return message;
  }

  public LocalDateTime getSent() {
    return sent;
  }

  public MessageStatus getStatus(final int i) {
    return status.get(i);
  }

  public int getStatusCount() {
    return status.size();
  }

  public int getTo() {
    return to;
  }

  public boolean isBroadcast() {
    return (to == 0);
  }

  public boolean isRead(final int iduser) {
    for (final MessageStatus ms : status) {
      if (ms.iduser == iduser) {
        if (ms.read != null) {
          return true;
        }
      }
    }
    return false;
  }

  public void read(final int idusuario) {
    synchronized (status) {
      status.add(new MessageStatus(idusuario, new LocalDateTime()));
    }
  }

  public void setFrom(final int _from) {
    from = _from;
  }

  public void setMessage(final String _message) {
    message = _message;
  }

  public void setSent(final LocalDateTime _sent) {
    sent = _sent;
  }

  public void setStatus(final MessageStatus[] mls) {
    synchronized (status) {
      status.clear();
      for (final MessageStatus ml : mls) {
        status.add(ml);
      }
    }
  }

  public void setTo(final int _to) {
    to = _to;
  }
}
