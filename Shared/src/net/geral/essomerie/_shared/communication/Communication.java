package net.geral.essomerie._shared.communication;

import java.io.IOException;
import java.net.Socket;

import net.geral.essomerie._shared.communication.types.SystemMessageType;
import net.geral.essomerie._shared.exceptions.DataCorruptedException;

import org.apache.log4j.Logger;

public final class Communication {
  public static final long          PING_INTERVAL_MS = 60 * 1000;
  private static final Logger       logger           = Logger
                                                         .getLogger(Communication.class);

  private final Socket              socket;
  private final CommunicationReader reader;
  private final CommunicationWriter writer;
  private final String              address;
  private final int                 id;

  private boolean                   lastPongReceived = true;
  private long                      nextPing         = 0;
  private long                      lag              = 0;

  public Communication(final Socket _socket, final int _id) throws IOException {
    socket = _socket;
    id = _id;
    address = socket.getInetAddress() + ":" + socket.getPort();
    nextPing = System.currentTimeMillis() + PING_INTERVAL_MS;
    writer = new CommunicationWriter(this);
    reader = new CommunicationReader(this);
    writer.start();
    reader.start();
  }

  public void close() {
    try {
      socket.close();
    } catch (final IOException e) {
      logger.error("Should never happend", e);
    }
  }

  public int getId() {
    return id;
  }

  public Socket getSocket() {
    return socket;
  }

  public boolean isWorking() {
    return !socket.isClosed();
  }

  public void loop() throws IOException, DataCorruptedException,
      PingTimeoutException {
    final long now = System.currentTimeMillis();
    if (nextPing <= now) {
      if (!lastPongReceived) {
        throw new PingTimeoutException();
      }
      nextPing = now + PING_INTERVAL_MS;
      lastPongReceived = false;
      send(MessageSubSystem.System, SystemMessageType.Ping, now);
    }
  }

  public void pong(final long ms) {
    lastPongReceived = true;
    lag = System.currentTimeMillis() - ms;
    logger.debug("Current Lag: " + lag);
  }

  public MessageData recv() {
    return reader.read();
  }

  public MessageData send(final MessageData md) {
    writer.write(md);
    return md;
  }

  public MessageData send(final MessageSubSystem subsystem,
      final Enum<? extends IMessageType> type, final Object... objs)
      throws IOException {
    return send(new MessageData(subsystem, type, objs));
  }

  @Override
  public String toString() {
    return address;
  }
}
