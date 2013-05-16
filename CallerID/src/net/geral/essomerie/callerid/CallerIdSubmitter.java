package net.geral.essomerie.callerid;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class CallerIdSubmitter extends Thread {
    private final String server;
    private final String line;
    private final String type;
    private final String number;

    public CallerIdSubmitter(final String server, final String line,
	    final String type, final String number) {
	this.server = server == null ? "" : server;
	this.line = line == null ? "" : line;
	this.type = type == null ? "" : type;
	this.number = number == null ? "" : number;
    }

    @Override
    public void run() {
	final String[] parts = server.split(":");
	if (parts.length != 2) {
	    throw new IllegalArgumentException("Invalid address:port --> "
		    + server);
	}
	final String address = parts[0];
	final int port = Integer.parseInt(parts[1]);
	System.out
		.println("### SUBMITTING TO: " + address + " on port " + port);
	try (Socket socket = new Socket(address, port);
		PrintWriter writer = new PrintWriter(socket.getOutputStream())) {
	    writer.println(line);
	    writer.println(type);
	    writer.println(number);
	    System.out.println("### SUBMITED: " + type + " [" + line + "]: "
		    + number);
	} catch (final IOException e) {
	    e.printStackTrace();
	}
    }
}
