package net.geral.essomerie.client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import net.geral.essomerie.client.core.Client;
import net.geral.essomerie.client.core.configuration.CoreConfiguration;
import net.geral.essomerie.client.core.configuration.Preferences;
import net.geral.essomerie.client.resources.IMG;
import net.geral.essomerie.client.resources.S;
import net.geral.essomerie.shared.catalog.CatalogGroup;
import net.geral.essomerie.shared.catalog.CatalogItem;
import net.geral.essomerie.shared.person.Telephone;
import net.geral.lib.datepicker.DatePickerPanel;
import net.geral.lib.filechooser.GNFileChooser;
import net.geral.lib.strings.GNStrings;
import net.geral.lib.table.GNTableRenderer;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.TTCCLayout;
import org.apache.log4j.varia.LevelRangeFilter;

/**
 * Essomerie Client Main Class
 * 
 * @author Daniel Thee Roperto
 */

// FIXME whoke project :worry about synch methods
// TODO change SwingUtilities call to Edit.run...
public class Main {
  private final static long        started = System.currentTimeMillis();
  private static Logger            logger  = null;
  private static ArrayList<String> ignored = new ArrayList<>();

  private static void initializeLogger(final CoreConfiguration config) {
    // TODO log to file as well
    // Levels: OFF TRACE DEBUG INFO WARN ERROR FATAL
    LogManager.resetConfiguration();

    final LevelRangeFilter errfilter = new LevelRangeFilter();
    errfilter.setLevelMin(Level.WARN);
    final ConsoleAppender stderr = new ConsoleAppender(new TTCCLayout(),
        "System.err");
    stderr.setName("stderr");
    stderr.addFilter(errfilter);

    final LevelRangeFilter outfilter = new LevelRangeFilter();
    outfilter.setLevelMax(Level.DEBUG);
    final ConsoleAppender stdout = new ConsoleAppender(new TTCCLayout(),
        "System.out");
    stdout.setName("stdout");
    stdout.addFilter(outfilter);

    LogManager.getRootLogger().addAppender(stderr);
    LogManager.getRootLogger().addAppender(stdout);

    logger = Logger.getLogger(Main.class);
    logger.debug("Logger configured.");
  }

  private static void initializeLookAndFeel(final String laf) {
    logger.debug("Setting look and feel: " + laf);
    try {
      UIManager.setLookAndFeel(laf);
    } catch (ClassNotFoundException | InstantiationException
        | IllegalAccessException | UnsupportedLookAndFeelException e) {
      logger.warn("Cannot set look and feel: " + laf, e);
    }
  }

  private static CoreConfiguration loadConfiguration(final boolean verbose,
      final ArrayList<URL> configFiles) {
    final CoreConfiguration config = new CoreConfiguration();
    if (verbose) {
      verbose("Loading configuration files...");
    }
    for (final URL url : configFiles) {
      try {
        ignored.addAll(config.parse(verbose, configFiles));
      } catch (final Exception e) {
        System.err.println("Cannot process configuration file: " + url);
        System.err.println("--> " + e.getMessage());
        if (verbose) {
          e.printStackTrace();
        }
        System.exit(1);
      }
    }
    if (verbose) {
      verbose("Finished configuration loading.");
      verbose("Initial verbose mode is stopping, output will change to logging configuration.");
    }
    return config;
  }

  private static Preferences loadPreferences(final CoreConfiguration config) {
    logger.debug("Preparing preferences...");
    final Preferences prefs = new Preferences();
    if (config.PreferencesComputerAutoSaveFile.length() > 0) {
      loadPreferences(prefs, new File(config.PreferencesComputerAutoSaveFile));
    }
    if (config.PreferencesComputerEnforceFile.length() > 0) {
      loadPreferences(prefs, new File(config.PreferencesComputerEnforceFile));
    }
    return prefs;
  }

  private static void loadPreferences(final Preferences prefs, final File file) {
    BufferedReader reader = null;
    try {
      if (!file.exists()) {
        file.getParentFile().mkdirs();
        file.createNewFile();
      }
      final String path = file.getCanonicalPath(); // used when showing
      // the file path
      logger.debug("Loading preferences: " + path);
      reader = new BufferedReader(new FileReader(file));
      String line;
      while ((line = reader.readLine()) != null) {
        logger.debug("Setting: " + line);
        prefs.set(line);
      }
    } catch (final Exception e) {
      logger.error("Cannot process preferences file: " + file.getPath(), e);
      System.exit(0);
    } finally {
      try {
        if (reader != null) {
          reader.close();
        }
      } catch (final IOException e) {
        logger.error("Should never happen", e);
      }
    }
  }

  /**
   * Essomerie Client entry point.
   * 
   * @param args
   *          command-line arguments
   */
  public static void main(final String[] args) {
    final CoreConfiguration config = parseParameters(args);
    initializeLogger(config);
    initializeLookAndFeel(config.LookAndFeel);
    preloadResources(config);
    final Preferences prefs = loadPreferences(config);
    DatePickerPanel.getDefaultConfiguration().todayLabel = S.BUTTON_TODAY.s();
    // start from EDT
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        Client.initialize(config, prefs);
        Client.start();
        logger.debug("Startup completed!");
      }
    });
  }

  private static void overrideText(final String name, final String value) {
    try {
      logger.debug("Setting '" + name + "' to: " + value);
      S.set(name, value);
    } catch (final IllegalArgumentException e) {
      System.err.println("Invalid text to override: " + name);
    }
  }

  private static CoreConfiguration parseParameters(final String[] params) {
    if (params.length == 0) {
      showHelpAndExit();
    }
    final ArrayList<URL> configFiles = new ArrayList<>();
    boolean verbose = false;
    boolean useDefault = false;
    for (final String s : params) {
      if ("-v".equals(s)) {
        verbose = true;
        verbose("Verbose mode enabled!");
      } else if ("-d".equals(s)) {
        if (verbose) {
          verbose("Default Configuration enabled!");
        }
        useDefault = true;
      } else {
        if (verbose) {
          verbose("Will load: " + s);
        }
        try {
          configFiles.add(GNStrings.toURL(s));
        } catch (final MalformedURLException e) {
          System.err.println("Invalid file/url: " + s);
          e.printStackTrace();
          System.exit(1);
        }
      }
    }
    // if default, cannot have files
    if (useDefault) {
      if (configFiles.size() == 0) {
        return new CoreConfiguration();
      }
      System.err.println("Cannot load config file if '-d' parameter is set.");
      System.exit(1);
    }
    // not default, load configs
    if (configFiles.size() == 0) {
      System.err.println("No configuration files specified.");
      System.err
          .println("Please specify a configuration file to use or use the parameter '-d'.");
      System.exit(1);
    }
    return loadConfiguration(verbose, configFiles);
  }

  private static void preloadResources(final CoreConfiguration config) {
    logger.debug("Preloading resources...");
    IMG.preload();
    S.load(config.Language);
    // check ignored lines (resources override)
    for (final String s : ignored) {
      if (s.startsWith("#")) { // comment
        continue;
      }

      final String[] parts = s.substring(1).split("=", 2);
      final String name = GNStrings.trim(parts[0]);
      final String value = GNStrings.trim(parts[1]);
      if (parts.length != 2) {
        System.err.println("Invalid config line: " + s);
        System.exit(1);
      } else if (s.startsWith("$")) {
        overrideText(name, value);
      } else if (s.startsWith("%")) {
        // FIXME overrideImage(name, value);
      }
    }
    // initialize components
    GNTableRenderer.setDefaultDeleteIcon(IMG.ICON__DELETE
        .icon(config.DeleteIconSize));
    GNFileChooser.setTextImagesOnly(S.GNFILECHOOSER_ONLYIMAGES.s());
    Telephone.setDefaults(config.DefaultTelephoneCountryCode,
        config.DefaultTelephoneAreaCode);
    CatalogGroup.setDefaultTitle(S.ORGANIZER_CATALOG_GROUP_DEFAULT_TITLE.s());
    CatalogItem.setDefaultTitle(S.ORGANIZER_CATALOG_ITEM_DEFAULT_TITLE.s());
  }

  private static void showHelpAndExit() {
    System.err.println("Parameters: [-v] [-d] [configfiles]");
    System.err
        .println("-v           verbose mode (use it as the first parameter for best results)");
    System.err
        .println("-d           use default configuration (no need for configuration files)");
    System.err
        .println("configfiles  configuration files to load in the provided order");
    System.err.println("");
    System.err
        .println("No parameters found. Please use '-d' if you just want the defaults.");
    System.exit(1);
  }

  private static void verbose(final String txt) {
    final long now = System.currentTimeMillis() - started;
    System.out.println("[verbose:" + now + "] " + txt);
  }
}
