package net.geral.essomerie.client.core.configuration;

import java.awt.Dimension;
import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import net.geral.configuration.ConfigurationBase;
import net.geral.essomerie.client.core.Client;

import org.apache.log4j.Logger;

public class Preferences extends ConfigurationBase {
	private static final Logger	logger				= Logger.getLogger(Preferences.class);

	/**
	 * If true, the window will start maximized.
	 */
	public boolean				MainWindowMaximized	= true;

	/**
	 * Window size. If null, use minimum size.
	 */
	public Dimension			MainWindowSize		= null;

	/**
	 * Window location. If null, centralize it in desktop.
	 */
	public Point				MainWindowLocation	= null;

	/**
	 * Save to autosave preferences file
	 */
	public void autosave() {
		logger.debug("Autosaving...");
		final File f = new File(Client.config().PreferencesComputerAutoSaveFile);
		PrintStream output = null;
		try {
			output = new PrintStream(f);
			output.print(toString());
		}
		catch (final FileNotFoundException e) {
			logger.debug("Autosave failed!", e);
		}
		finally {
			if (output != null) {
				output.close();
			}
		}
	}
}
