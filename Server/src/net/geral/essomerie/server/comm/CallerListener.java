package net.geral.essomerie.server.comm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

import net.geral.essomerie.server.Server;
import net.geral.essomerie.server.comm.controllers.CallerIdController;

import org.apache.log4j.Logger;

public class CallerListener extends Thread {
    private static final Logger logger = Logger.getLogger(Listener.class);
    public ServerSocket socketListen = null;

    public CallerListener() {
	setName("CallerListener");
    }

    private void callerIdReceived(final Socket socket) {
	String line = null;
	String type = null;
	String number = null;
	try (BufferedReader input = new BufferedReader(new InputStreamReader(
		socket.getInputStream()))) {
	    logger.debug("Caller id received from " + socket.getInetAddress());
	    socket.setSoTimeout(Server.config().SocketTimeout);
	    line = input.readLine();
	    type = input.readLine();
	    number = input.readLine();
	} catch (final Exception e) {
	    logger.warn(e, e);
	} finally {
	    try {
		socket.close();
	    } catch (final IOException e) {
		logger.warn(e, e);
	    }
	}
	if ((line != null) && (type != null) && (number != null)) {
	    try {
		CallerIdController.callReceived(line, type, number);
	    } catch (final Exception e) {
		logger.warn(e, e);
	    }
	}
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
	socketListen = new ServerSocket(Server.config().CallerIdListenPort);
	socketListen.setSoTimeout(Server.config().SocketTimeout);
    }

    @Override
    public void run() {
	logger.debug("Listener running...");
	ServerSocket socket = socketListen;// track in separate variable in case
					   // it closes inside loop
	while (socket != null) {
	    try {
		callerIdReceived(socketListen.accept());
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
	if (Server.config().ListenPort == 0) {
	    logger.debug("Caller ID Server disabled.");
	    return;
	}

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
