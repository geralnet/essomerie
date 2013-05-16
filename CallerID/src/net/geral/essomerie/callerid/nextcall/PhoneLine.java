package net.geral.essomerie.callerid.nextcall;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PhoneLine {
    private static final String PREFIX = "BINA - ";

    private final Path path;
    private final String name;
    private String lastNumber = null;
    private String lastContents;
    private Exception lastException;

    public PhoneLine(final String lineFilePath) throws IOException {
	path = Paths.get(lineFilePath).normalize().toAbsolutePath();
	if (path.getNameCount() == 0) {
	    throw new IllegalArgumentException("Invalid line file path ("
		    + path.getNameCount() + "): " + path);
	}
	final Path dir = getDirectory();
	if ((dir == null) || (!Files.isDirectory(dir))) {
	    throw new IllegalArgumentException("Not a directory: " + dir);
	}
	final String fname = path.getFileName().toString();
	if (!fname.endsWith(".exp")) {
	    throw new IllegalArgumentException("Not .exp: " + fname + " ("
		    + path + ")");
	}
	name = fname.substring(0, fname.length() - 4);
    }

    public void analyseFile() {
	lastNumber = null;
	lastContents = null;
	lastException = null;
	try (FileReader reader = new FileReader(path.toFile());
		BufferedReader buffered = new BufferedReader(reader)) {
	    final StringBuilder sb = new StringBuilder();
	    String line;
	    while ((line = buffered.readLine()) != null) {
		if (line.startsWith(PREFIX)) {
		    final String number = line.substring(PREFIX.length());
		    lastNumber = number.replaceAll("\\D+", "");
		}
		sb.append(line);
		sb.append("\n");
	    }
	    lastContents = sb.toString();
	} catch (final Exception e) {
	    lastException = e;
	}

	if (lastException != null) {
	    lastException.printStackTrace();
	} else {
	    System.out.println("LastException=null");
	}

	System.out.println(lastContents == null ? "LastContents=null"
		: lastContents);

	System.out.println(lastNumber == null ? "LastNumber=null" : lastNumber);
    }

    public Path getDirectory() {
	if (path == null) {
	    return null;
	}
	return path.getParent();
    }

    public String getLastContents() {
	return lastContents;
    }

    public Exception getLastException() {
	return lastException;
    }

    public String getLastNumber() {
	return lastNumber;
    }

    public String getName() {
	return name;
    }

    public Path getPath() {
	return path;
    }

    @Override
    public String toString() {
	return name + "[" + path + "]";
    }
}
