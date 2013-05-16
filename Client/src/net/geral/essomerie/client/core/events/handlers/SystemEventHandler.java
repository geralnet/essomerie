package net.geral.essomerie.client.core.events.handlers;

import javax.swing.event.EventListenerList;

import net.geral.essomerie._shared.BuildInfo;
import net.geral.essomerie._shared.communication.ConnectionControllerInformer;
import net.geral.essomerie._shared.communication.MessageData;
import net.geral.essomerie.client.core.events.listeners.SystemListener;
import net.geral.lib.edt.Edt;

import org.apache.log4j.Logger;

public class SystemEventHandler extends EventListenerList implements
    ConnectionControllerInformer {
  private static final Logger logger           = Logger
                                                   .getLogger(SystemEventHandler.class);
  private static final long   serialVersionUID = 1L;

  public void addListener(final SystemListener l) {
    add(SystemListener.class, l);
  }

  public void fireConnected() {
    Edt.run(false, new Runnable() {
      @Override
      public void run() {
        logger.debug("Fired: fireConnected");
        for (final SystemListener l : getListeners(SystemListener.class)) {
          l.systemConnected();
        }
      }
    });
  }

  public void fireConnecting(final String serverAddress, final int serverPort) {
    Edt.run(false, new Runnable() {
      @Override
      public void run() {
        logger.debug("Fired: fireConnecting");
        for (final SystemListener l : getListeners(SystemListener.class)) {
          l.systemConnecting(serverAddress, serverPort);
        }
      }
    });
  }

  public void fireConnectionFailed(final boolean willTryAgain) {
    Edt.run(false, new Runnable() {
      @Override
      public void run() {
        logger.debug("Fired: fireConnectionFailed");
        for (final SystemListener l : getListeners(SystemListener.class)) {
          l.systemConnectionFailed(willTryAgain);
        }
      }
    });
  }

  public void fireConnectionTryAgainCountdown(final int tryAgainCountdown) {
    Edt.run(false, new Runnable() {
      @Override
      public void run() {
        logger.debug("Fired: fireTryAgainCountdown");
        for (final SystemListener l : getListeners(SystemListener.class)) {
          l.systemConnectionTryAgainCountdown(tryAgainCountdown);
        }
      }
    });
  }

  public void fireInformSent(final MessageData md) {
    Edt.run(false, new Runnable() {
      @Override
      public void run() {
        logger.debug("Fired: fireInformSent");
        for (final SystemListener l : getListeners(SystemListener.class)) {
          l.systemInformSent(md);
        }
      }
    });
  }

  public void fireLoggedOut() {
    Edt.run(false, new Runnable() {
      @Override
      public void run() {
        logger.debug("Fired: fireLoggedOut");
        for (final SystemListener l : getListeners(SystemListener.class)) {
          l.systemLoggedOut();
        }
      }
    });
  }

  public void fireLoginAccepted() {
    Edt.run(false, new Runnable() {
      @Override
      public void run() {
        logger.debug("Fired: fireLoginAccepted");
        for (final SystemListener l : getListeners(SystemListener.class)) {
          l.systemLoginAccepted();
        }
      }
    });
  }

  public void fireLoginFailed() {
    Edt.run(false, new Runnable() {
      @Override
      public void run() {
        logger.debug("Fired: fireLoginFailed");
        for (final SystemListener l : getListeners(SystemListener.class)) {
          l.systemLoginFailed();
        }
      }
    });
  }

  public void firePongReceived(final long lag) {
    Edt.run(false, new Runnable() {
      @Override
      public void run() {
        logger.debug("Fired: firePongReceived");
        for (final SystemListener l : getListeners(SystemListener.class)) {
          l.systemPongReceived(lag);
        }
      }
    });
  }

  public void fireVersionReceived(final BuildInfo version) {
    Edt.run(false, new Runnable() {
      @Override
      public void run() {
        logger.debug("Fired: fireVersionReceived");
        for (final SystemListener l : getListeners(SystemListener.class)) {
          l.systemVersionReceived(version);
        }
      }
    });
  }

  @Override
  public void informSent(final MessageData md) {
    fireInformSent(md);
  }

  public void removeListener(final SystemListener l) {
    remove(SystemListener.class, l);
  }
}
