package net.geral.essomerie.callerid.nextcall;

import java.io.IOException;
import java.util.ArrayList;

public class Main {
    private static void help(final String msg) {
	System.err.println("Error: " + msg);
	System.err
		.println("Arguments: [server] [line-1] [line-2] ... [line-n]");
	System.err
		.println("Usage Example: java Main 127.0.0.1:2255 c:\\nextcall\\rec1\\line1.exp c:\\nextcall\\rec\\line2.exp");
	System.exit(1);
    }

    public static void main(final String[] args) {
	try {
	    final CallerID_NextCall id = parseArguments(args);
	    id.start();
	} catch (final Exception e) {
	    e.printStackTrace();
	    help(e.getMessage() + " [" + e.getClass().getSimpleName() + "]");
	    System.exit(1);
	}
    }

    private static CallerID_NextCall parseArguments(final String[] args)
	    throws IOException {
	if (args.length == 0) {
	    help("No server information.");
	}
	final String server = args[0];
	if (args.length == 1) {
	    help("At least one line required.");
	}
	final ArrayList<PhoneLine> lines = new ArrayList<PhoneLine>();
	for (int i = 1; i < args.length; i++) {
	    lines.add(new PhoneLine(args[i]));
	}
	return new CallerID_NextCall(server, lines);
    }
}
