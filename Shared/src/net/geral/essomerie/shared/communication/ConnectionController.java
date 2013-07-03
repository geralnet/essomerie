package net.geral.essomerie.shared.communication;

import java.io.IOException;
import java.sql.SQLException;

public abstract class ConnectionController<T extends Enum<? extends IMessageType>> {
  private final ICommunication               ic;
  private final MessageSubSystem             subsystem;
  private final ConnectionControllerInformer informer;

  public ConnectionController(final ICommunication comm,
      final ConnectionControllerInformer info, final MessageSubSystem mss) {
    ic = comm;
    subsystem = mss;
    informer = info;
  }

  public void process(final MessageData md) throws IOException, SQLException {
    @SuppressWarnings("unchecked")
    final T type = (T) md.getType();
    process(type, md);
  }

  protected abstract void process(T type, MessageData md) throws IOException,
      SQLException;

  protected MessageData send(final T type, final Object... objects)
      throws IOException {
    final MessageData md = ic.comm().send(subsystem, type, objects);
    if (informer != null) {
      informer.informSent(md);
    }
    return md;
  }
}
