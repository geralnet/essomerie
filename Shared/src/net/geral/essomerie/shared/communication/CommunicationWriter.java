package net.geral.essomerie.shared.communication;

import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import org.apache.log4j.Logger;

class CommunicationWriter extends Thread {
  private final Logger                 logger = Logger
                                                  .getLogger(CommunicationWriter.class);
  private final ArrayList<MessageData> toSend = new ArrayList<>();
  private final Communication          comm;

  CommunicationWriter(final Communication _comm) {
    comm = _comm;
    setName("Comm-W#:" + comm.getId());
  }

  @Override
  public void run() {
    logger.debug("Started!");
    ObjectOutputStream output = null;
    try {
      output = new ObjectOutputStream(comm.getSocket().getOutputStream());
      while (comm.isWorking()) {
        MessageData md = null;
        do {
          synchronized (toSend) {
            md = (toSend.size() > 0) ? toSend.remove(0) : null;
          }
          if (md != null) {
            logger.debug("> " + md);
            output.writeObject(md);
            output.flush();
          }
        } while (md != null);
        Thread.sleep(20); // FIXME any better value?
      }
      logger.debug("Finished!");
    } catch (final NotSerializableException e) {
      logger.warn("Cannot serialize: " + e.getMessage());
    } catch (final Exception e) {
      logger.warn("Aborting...", e);
    } finally {
      logger.debug("Closing...");
      try {
        if (output != null) {
          output.close();
        }
      } catch (final IOException e) {
        logger.error("Should never happen", e);
      }
      comm.close();
    }
  }

  public void write(final MessageData md) {
    synchronized (toSend) {
      toSend.add(md);
    }
  }
}
