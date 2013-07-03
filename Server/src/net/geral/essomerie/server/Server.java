package net.geral.essomerie.server;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import net.geral.essomerie.server.comm.CallerListener;
import net.geral.essomerie.server.comm.Connection;
import net.geral.essomerie.server.comm.Listener;
import net.geral.essomerie.server.core.Configuration;
import net.geral.essomerie.server.db.Database;
import net.geral.essomerie.shared.communication.Communication;
import net.geral.essomerie.shared.communication.IMessageType;
import net.geral.essomerie.shared.communication.MessageData;
import net.geral.essomerie.shared.communication.MessageSubSystem;
import net.geral.lib.configuration.ConfigurationException;
import net.geral.lib.strings.GNStrings;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.TTCCLayout;
import org.apache.log4j.varia.LevelRangeFilter;

//TODO improve database connect (retry) and liste (retry, other software may close)
//TODO improve console display/commands, show username@machine for connected users
public class Server {
    private static final long started = System.currentTimeMillis();
    private static final Listener clientListener = new Listener();
    private static final CallerListener callerListener = new CallerListener();
    private static Logger logger = null;
    private static boolean running = true;

    private static long startTime = System.currentTimeMillis();
    private static Vector<Connection> clients = new Vector<Connection>();

    private static final Configuration configuration = new Configuration();

    private static final Database database = new Database();

    public static void addClient(final Socket clientSocket) throws IOException {
	if (clientSocket == null) {
	    throw new IllegalArgumentException("clientSocket cannot be null");
	}
	logger.info("New Client: " + clientSocket.toString());
	final Connection client = new Connection(clientSocket);
	clients.add(client);
	client.start();
    }

    public static void broadcast(final MessageData md) {
	// get clients
	final Connection[] cs = clients.toArray(new Connection[clients.size()]);
	// send
	for (final Connection c : cs) {
	    try {
		c.comm().send(md);
	    } catch (final Exception e) {
		// ensure one exception will not
		// prevent broadcast to continue
		logger.warn("Could not broadcast (" + md + ") to: " + c, e);
	    }
	}
    }

    public static void broadcast(final MessageSubSystem subsystem,
	    final Enum<? extends IMessageType> type, final Object... objects) {
	broadcast(new MessageData(subsystem, type, objects));
    }

    private static void checkConsoleInput() throws IOException {
	if (System.in.available() > 0) {
	    final char c = Character.toUpperCase((char) System.in.read());
	    if (consoleCommand(c)) {
		consoleHelp();
	    }
	}
    }

    private static void cleanup() {
	int removed = 0;
	synchronized (clients) {
	    for (int i = 0; i < clients.size(); i++) {
		final Connection c = clients.get(i);
		if (!c.isAlive()) {
		    removed++;
		    clients.remove(c);
		    i--;
		}
	    }
	}
	final StringBuffer sb = new StringBuffer();
	sb.append("CLEANUP:");
	sb.append(removed);
	sb.append("  MEM:");
	sb.append(Runtime.getRuntime().totalMemory() / 1024 / 1024);
	sb.append("/");
	sb.append(Runtime.getRuntime().maxMemory() / 1024 / 1024);
	sb.append("MB  THREADS:");
	sb.append(Thread.activeCount());
	sb.append("  CLIENTS:");
	sb.append(clients.size());
	logger.debug(sb.toString());
    }

    public static Configuration config() {
	return configuration;
    }

    private static boolean consoleCommand(final char c) {
	switch (c) {
	case 'S':
	    System.out.println(getStatus());
	    return false;
	default:
	    return false;
	}
    }

    private static void consoleHelp() {
	System.out.println("N/V/D - Output Level (norma/verbose/debug)");
	System.out.println();
    }

    public static Database db() {
	return database;
    }

    public static void fatalError(final String s) {
	System.err.println("*FATAL ERROR* " + s);
	running = false;
    }

    public static Communication[] getClientComm(final int idcliente) {
	final Connection[] cs = clients.toArray(new Connection[clients.size()]);
	final ArrayList<Communication> found = new ArrayList<>();
	for (final Connection c : cs) {
	    if (c.getUserId() == idcliente) {
		found.add(c.comm());
	    }
	}
	return found.toArray(new Communication[found.size()]);
    };

    private static String getStatus() {
	long uptime = System.currentTimeMillis() - startTime;
	final int ms = (int) (uptime % 1000);
	uptime /= 1000; // uptime now holds seconds
	final int s = (int) (uptime % 60);
	uptime /= 60; // uptime now holds minutes
	final int m = (int) (uptime % 60);
	uptime /= 60; // uptime now holds hours
	final int h = (int) (uptime % 24);
	uptime /= 24; // uptime now hold days
	final int d = (int) uptime;

	return String.format("%02dd %02d:%02d:%02d.%04d %s Clients:%d", d, h,
		m, s, ms, database.toString(), clients.size());
    }

    private static void initializeLogger() {
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

	logger = Logger.getLogger(Server.class);
	logger.debug("Logger configured.");
    }

    public static void main(final String[] args) {
	parseParameters(args); // parse parameters and get configuration
	initializeLogger();
	if (!database.open(configuration)) {
	    logger.error("Database error. Aborting...");
	    System.exit(1);
	}
	logger.debug("Server started! Preparing to serve...");
	run();
    }

    private static void parseParameters(final String[] params) {
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
		}
	    }
	}
	// if default, cannot have files
	if (useDefault) {
	    if (configFiles.size() == 0) {
		return;
	    }
	    System.err
		    .println("Cannot load config file if '-d' parameter is set.");
	    System.exit(1);
	}
	// not default, load configs
	if (configFiles.size() == 0) {
	    System.err.println("No configuration files specified.");
	    System.err
		    .println("Please specify a configuration file to use or use the parameter '-d'.");
	    System.exit(1);
	}

	try {
	    configuration.parse(verbose, configFiles);
	} catch (final ConfigurationException e) {
	    System.err.println(e.getMessage());
	    e.printStackTrace();
	    System.exit(0);
	}
	verbose("Initial verbose mode is stopping, output will change to logging configuration.");
    }

    private static void run() {
	long nextCleanup = 0;
	long nextKeepAlive = 0;
	// keep checking
	logger.debug("Running server...");
	clientListener.start();
	callerListener.start();
	while (running) {
	    if (System.currentTimeMillis() > nextCleanup) {
		cleanup();
		nextCleanup = System.currentTimeMillis()
			+ configuration.CleanupPeriod;
	    }
	    if (System.currentTimeMillis() > nextKeepAlive) {
		try {
		    database.keepAlive();
		} catch (final SQLException e) {
		    logger.debug("DB KeepAlive failed. Reconnecting...", e);
		    if (!database.open(configuration)) {
			logger.debug("Could not reconnect. Aborting...", e);
			System.exit(1);
		    }
		}
		nextKeepAlive = System.currentTimeMillis()
			+ configuration.MysqlKeepAlive;
	    }
	    try {
		checkConsoleInput();
		Thread.sleep(configuration.ConsoleCheckPeriod);
	    } catch (final Exception e) {
		logger.warn("Exception occures!", e);
	    }
	}
	logger.debug("Running stopped!");
	shutdown();
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

    private static void shutdown() {
	logger.debug("Shutting down...");

	if (clientListener != null) {
	    logger.debug("Closing Listener...");
	    clientListener.close();
	    try {
		clientListener.join(configuration.WaitForGracefulClose);
	    } catch (final InterruptedException e) {
		logger.warn("Failed to wait for listener to finish...", e);
	    }
	}

	if (clientListener != null) {
	    logger.debug("Closing Caller ID Listener...");
	    callerListener.close();
	    try {
		callerListener.join(configuration.WaitForGracefulClose);
	    } catch (final InterruptedException e) {
		logger.warn(
			"Failed to wait for caller id listener to finish...", e);
	    }
	}

	for (final Connection c : clients) {
	    logger.debug("Closing Client: " + c);
	    c.close();
	    try {
		c.join(configuration.WaitForGracefulClose);
	    } catch (final InterruptedException e) {
		logger.warn("Failed to wait for client: " + c, e);
	    }
	}

	database.close();
    }

    private static void verbose(final String txt) {
	final long now = System.currentTimeMillis() - started;
	System.out.println("[verbose:" + now + "] " + txt);
    }
}
