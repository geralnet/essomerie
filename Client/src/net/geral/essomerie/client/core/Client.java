package net.geral.essomerie.client.core;

import net.geral.essomerie._shared.User;
import net.geral.essomerie.client.communication.Connection;
import net.geral.essomerie.client.core.cache.Cache;
import net.geral.essomerie.client.core.configuration.CoreConfiguration;
import net.geral.essomerie.client.core.configuration.Preferences;
import net.geral.essomerie.client.gui.main.MainWindow;

import org.apache.log4j.Logger;

public class Client {
  private static Logger            logger        = Logger
                                                     .getLogger(Client.class);
  private static Preferences       preferences   = null;
  private static CoreConfiguration configuration = null;
  private static Connection        connection    = null;
  private static MainWindow        window        = null;
  private static Cache             cache         = null;

  public static Cache cache() {
    if (cache == null) {
      throw new NullPointerException(
          "Client not initialized, cannot access: cache");
    }
    return cache;
  }

  public static void clearCache() {
    cache = new Cache();
  }

  public static CoreConfiguration config() {
    if (configuration == null) {
      throw new NullPointerException(
          "Client not initialized, cannot access: configuration");
    }
    return configuration;
  }

  public static Connection connection() {
    if (connection == null) {
      throw new NullPointerException(
          "Client not initialized, cannot access: connection");
    }
    return connection;
  }

  public static User getLoggerUser() {
    return cache().users().getLogged();
  }

  public static void initialize(final CoreConfiguration config,
      final Preferences prefs) {
    logger.debug("Initializing...");
    preferences = prefs;
    configuration = config;
    cache = new Cache();
    connection = new Connection();
    window = new MainWindow();
    logger.debug("Initialized!");
  }

  public static Preferences preferences() {
    if (preferences == null) {
      throw new NullPointerException(
          "Client not initialized, cannot access: preferences");
    }
    return preferences;
  }

  public static void start() {
    logger.debug("Starting Client...");
    window.start();
    connection.start();
    logger.debug("Client start() Finished!");
  }

  public static MainWindow window() {
    if (window == null) {
      throw new NullPointerException(
          "Client not initialized, cannot access: window");
    }
    return window;
  }
}
