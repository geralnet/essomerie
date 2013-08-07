package net.geral.essomerie.client.communication;

import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;

import net.geral.essomerie.client.communication.controllers.BulletinBoardController;
import net.geral.essomerie.client.communication.controllers.CalendarController;
import net.geral.essomerie.client.communication.controllers.CallerIdController;
import net.geral.essomerie.client.communication.controllers.CatalogController;
import net.geral.essomerie.client.communication.controllers.InventoryController;
import net.geral.essomerie.client.communication.controllers.MessagesController;
import net.geral.essomerie.client.communication.controllers.PersonsController;
import net.geral.essomerie.client.communication.controllers.SalesController;
import net.geral.essomerie.client.communication.controllers.SysopController;
import net.geral.essomerie.client.communication.controllers.SystemController;
import net.geral.essomerie.client.communication.controllers.UsersController;
import net.geral.essomerie.client.core.Client;
import net.geral.essomerie.client.core.configuration.CoreConfiguration;
import net.geral.essomerie.client.core.events.Events;
import net.geral.essomerie.shared.communication.Communication;
import net.geral.essomerie.shared.communication.ICommunication;
import net.geral.essomerie.shared.communication.MessageData;
import net.geral.lib.edt.Edt;

import org.apache.log4j.Logger;
import org.omg.CORBA.SystemException;

//TODO every send message needs a signal that was received, like an id.
//TODO when requesting, show status in main windows until signal received
public class Connection extends Thread implements ICommunication {
  public static enum ConnectionState {
    DISCONNECTED,
    TRY_AGAIN,
    CONNECTING,
    CONNECTED,
  }

  private static final Logger           logger                = Logger
                                                                  .getLogger(Connection.class);

  private Communication                 comm                  = null;
  private ConnectionState               state;
  private boolean                       running;
  private int                           tryAgainCountdown     = 0;
  private int                           lastTryAgainCountdown = 0;
  private int                           nextConnectionId      = 0;

  private final BulletinBoardController bulletinBoard         = new BulletinBoardController(
                                                                  this);
  private final CalendarController      calendar              = new CalendarController(
                                                                  this);
  private final InventoryController     inventory             = new InventoryController(
                                                                  this);
  private final MessagesController      messages              = new MessagesController(
                                                                  this);
  private final PersonsController       persons               = new PersonsController(
                                                                  this);
  private final SystemController        system                = new SystemController(
                                                                  this);
  private final UsersController         users                 = new UsersController(
                                                                  this);
  private final CallerIdController      callerid              = new CallerIdController(
                                                                  this);
  private final SalesController         sales                 = new SalesController(
                                                                  this);
  private final CatalogController       catalog               = new CatalogController(
                                                                  this);
  private final SysopController         sysop                 = new SysopController(
                                                                  this);

  public Connection() {
    running = false;
    setName("Connection");
    state = ConnectionState.DISCONNECTED;
  }

  public BulletinBoardController bulletinBoard() {
    return bulletinBoard;
  }

  public CalendarController calendar() {
    return calendar;
  }

  public CatalogController catalog() {
    return catalog;
  }

  public void close() {
    if (comm != null) {
      comm.close();
    }
    comm = null;
  }

  @Override
  public Communication comm() {
    return comm;
  }

  public void finish() {
    running = false;
  }

  public InventoryController inventory() {
    return inventory;
  }

  public MessagesController messages() {
    return messages;
  }

  public PersonsController persons() {
    return persons;
  }

  private void processMessage(final MessageData md) throws IOException,
      SQLException {
    switch (md.getSubSystem()) {
      case BulletinBoard:
        bulletinBoard.process(md);
        break;
      case Calendar:
        calendar.process(md);
        break;
      case Inventory:
        inventory.process(md);
        break;
      case Messages:
        messages.process(md);
        break;
      case Persons:
        persons.process(md);
        break;
      case System:
        system.process(md);
        break;
      case Users:
        users.process(md);
        break;
      case CallerId:
        callerid.process(md);
        break;
      case Sales:
        sales.process(md);
        break;
      case Catalog:
        catalog.process(md);
        break;
      case Sysop:
        sysop.process(md);
        break;
      default:
        logger.warn("Invalid subsystem: " + md.getSubSystem());
    }
  }

  @Override
  public void run() {
    try {
      while (running) {
        try {
          final Communication c = comm; // comm can become null
          if ((c != null) && c.isWorking()) {
            c.loop();
          } else if (c != null) {
            tryAgain(new IOException("closed (null=" + (c == null) + ")"));
          }
          while (runState()) {
            Thread.yield();
          }
          Thread.sleep(10); // FIXME any better value?
        } catch (final IOException e) {
          tryAgain(e);
        }
      }
    } catch (final Exception e) {
      logger.error("run() finished by exception", e);
    }
  }

  /**
   * 
   * @return true if should execute again without sleeping
   * @throws IOException
   * @throws SystemException
   */
  private boolean runState() throws IOException, SystemException {
    switch (state) {
      case DISCONNECTED:
        stateDisconnected();
        return true;
      case TRY_AGAIN:
        stateTryAgain();
        return false;
      case CONNECTING:
        stateConnecting();
        return true;
      case CONNECTED:
        return stateConnected();
      default:
        logger.error("runState()",
            new Exception("Invalid State: " + state.toString()));
        return false;
    }
  }

  public SalesController sales() {
    return sales;
  }

  @Override
  public synchronized void start() {
    running = true;
    super.start();
  }

  /**
   * @return true if message processed, false otherwise
   * @throws IOException
   * @throws SystemException
   */
  private boolean stateConnected() throws IOException, SystemException {
    try {
      // check for a message
      if (comm == null) {
        return false;
      }
      comm.loop();
      final MessageData md = comm.recv();
      if (md == null) {
        return false;
      }
      processMessage(md);
      return true;
    } catch (final Exception e) {
      logger.warn("stateConnected()", e);
      state = ConnectionState.DISCONNECTED;
      System.exit(0); // abort! TODO add message? what to do?
      return false;
    }
  }

  private void stateConnecting() throws IOException {
    final CoreConfiguration config = Client.config();
    Events.system().fireConnecting(config.ServerAddress, config.ServerPort);

    // connect
    try {
      logger.info("Connecting to " + config.ServerAddress + ":"
          + config.ServerPort + " ...");
      final Socket socket = new Socket(config.ServerAddress, config.ServerPort);

      logger.debug("Setting up communication protocol...");
      comm = new Communication(socket, nextConnectionId++);

      Events.system().fireConnected();
      lastTryAgainCountdown = 1; // connected! reset it
      state = ConnectionState.CONNECTED;
    } catch (final IOException e) {
      tryAgain(e);
    }
  }

  private void stateDisconnected() {
    Client.clearCache();
    Edt.run(true, new Runnable() {
      @Override
      public void run() {
        Client.window().closeAllTabs();
        Events.system().fireLoggedOut();
      }
    });
    state = ConnectionState.CONNECTING;
  }

  /**
   * 
   * @return true if going to try again, false if still counting down
   */
  private void stateTryAgain() {
    Events.system().fireConnectionTryAgainCountdown(tryAgainCountdown);
    try {
      Thread.sleep(1000);
    } catch (final InterruptedException e) {
      logger.warn(e);
    }
    tryAgainCountdown--;
    if (tryAgainCountdown <= 0) {
      Events.system().fireConnectionTryAgainCountdown(0);
      state = ConnectionState.DISCONNECTED;
    }
  }

  public SysopController sysop() {
    return sysop;
  }

  public SystemController system() {
    return system;
  }

  private void tryAgain(final IOException failureReason) {
    logger.debug("try again, reason: ", failureReason);
    close();
    lastTryAgainCountdown++;
    if (lastTryAgainCountdown > Client.config().ConnectionTryAgainDelayMaxSeconds) {
      lastTryAgainCountdown = Client.config().ConnectionTryAgainDelayMaxSeconds;
    }
    tryAgainCountdown = lastTryAgainCountdown;
    Events.system().fireConnectionFailed(true);
    state = ConnectionState.TRY_AGAIN;
  }

  public UsersController users() {
    return users;
  }
}
