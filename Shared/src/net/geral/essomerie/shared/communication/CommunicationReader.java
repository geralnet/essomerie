package net.geral.essomerie.shared.communication;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.SocketException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

class CommunicationReader extends Thread {
	private static final Logger				logger		= Logger.getLogger(CommunicationReader.class);
	private final ArrayList<MessageData>	messages	= new ArrayList<>();
	private final Communication				comm;

	CommunicationReader(final Communication _comm) throws IOException {
		comm = _comm;
		setName("Comm-R#:" + comm.getId());
	}

	public MessageData read() {
		synchronized (messages) {
			if (messages.size() == 0) { return null; }
			return messages.remove(0);
		}
	}

	@Override
	public void run() {
		logger.debug("Started!");
		ObjectInputStream input = null;
		try {
			input = new ObjectInputStream(comm.getSocket().getInputStream());
			while (comm.isWorking()) {
				final MessageData md = (MessageData)input.readObject();
				logger.debug("< " + md);
				synchronized (messages) {
					messages.add(md);
				}
			}
			logger.warn("Finished!");
		}
		catch (final EOFException e) {
			logger.debug("End of communication detected (" + e.getMessage() + ").");
		}
		catch (final SocketException e) {
			logger.debug("Disconnected (" + e.getMessage() + ").");
		}
		catch (final Exception e) {
			logger.warn("Aborting...", e);
		}
		finally {
			logger.info("Closing...");
			try {
				if (input != null) {
					input.close();
				}
			}
			catch (final IOException e) {
				logger.error("Should never happen", e);
			}
			comm.close();
		}
	}
}
