package net.geral.essomerie.server.comm;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketTimeoutException;

import net.geral.essomerie.server.Server;

import org.apache.log4j.Logger;

public class Listener extends Thread {
    private static final Logger logger = Logger.getLogger(Listener.class);
    public ServerSocket socketListen = null;

    public Listener() {
	setName("SocketListener");
    }

    public void close() {
	if (socketListen != null) {
	    try {
		socketListen.close();
		logger.debug("Listener " + this + " closed.");
	    } catch (final IOException e) {
		logger.warn("Cannot close socket listener", e);
	    } finally {
		socketListen = null;
	    }
	}
    }

    private void createServerSocket() throws IOException {
	socketListen = new ServerSocket(Server.config().ListenPort);
	socketListen.setSoTimeout(Server.config().SocketTimeout);
    }

    @Override
    public void run() {
	logger.debug("Listener running...");
	ServerSocket socket = socketListen;// track in separate variable in case
					   // it closes inside loop
	while (socket != null) {
	    try {
		Server.addClient(socketListen.accept());
	    } catch (final SocketTimeoutException e) {
		// no problems, no connection received
		Thread.yield();
	    } catch (final IOException e) {
		// error connecting, but do not abort
		logger.error(
			"Could not receive connection, will continue listening...",
			e);
	    }
	    socket = socketListen; // it might have closed and be null
	}
    }

    @Override
    public synchronized void start() {
	try {
	    createServerSocket();
	    super.start();
	} catch (final IOException e) {
	    logger.error("Could not create socket.", e);
	}
    }

    @Override
    public String toString() {
	return getClass().getSimpleName() + "[" + Server.config().ListenPort
		+ "]";
    }
}
