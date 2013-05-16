package net.geral.essomerie.callerid.nextcall;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;
import static java.nio.file.StandardWatchEventKinds.OVERFLOW;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import net.geral.essomerie.callerid.CallerIdSubmitter;

public class CallerID_NextCall extends Thread {
    private final String server;
    private final PhoneLine[] lines;
    private WatchService watcher;

    public CallerID_NextCall(final String server,
	    final ArrayList<PhoneLine> lines) {
	this.server = server;
	this.lines = lines.toArray(new PhoneLine[lines.size()]);
    }

    private boolean checkWatchService() throws InterruptedException {
	// http://docs.oracle.com/javase/tutorial/essential/io/notification.html
	// wait for key to be signaled
	final WatchKey key = watcher.take();
	final SimpleDateFormat format = new SimpleDateFormat(
		"yyyy-MM-dd HH:mm:ss");
	for (final WatchEvent<?> event : key.pollEvents()) {
	    final Kind<?> kind = event.kind();
	    System.out.println();
	    System.out
		    .println("*** " + format.format(new Date()) + ": " + kind);
	    if (kind == OVERFLOW) {
		continue;
	    }
	    @SuppressWarnings("unchecked")
	    final WatchEvent<Path> ev = (WatchEvent<Path>) event;
	    final Path dir = (Path) key.watchable();
	    final Path filename = dir.resolve(ev.context());
	    System.out.println("       dir=" + dir);
	    System.out.println("  filename=" + filename);
	    process(kind, filename);
	}
	return key.reset();
    }

    private void created(final PhoneLine l) {
	System.out.println("*** Created: " + l);
	l.analyseFile();
	System.err.println("IN " + l.getName() + " " + l.getLastNumber());
	(new CallerIdSubmitter(server, l.getName(), "IN", l.getLastNumber()))
		.start();
    }

    private void createWatchService() throws IOException {
	watcher = FileSystems.getDefault().newWatchService();
	final ArrayList<String> watched = new ArrayList<>();
	for (final PhoneLine line : lines) {
	    final Path dir = line.getDirectory();
	    final String sdir = dir.toString();
	    if (!watched.contains(sdir)) {
		dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
		watched.add(sdir);
		System.out.println("Watching directory: " + dir);
	    }
	}
    }

    private void deleted(final PhoneLine l) {
	System.out.println("*** Deleted: " + l);
    }

    private void modified(final PhoneLine l) {
	System.out.println("*** Modified: " + l);
	l.analyseFile();
    }

    private void process(final Kind<?> kind, Path path) {
	path = path.normalize().toAbsolutePath();
	try {
	    for (final PhoneLine l : lines) {
		if (l.getPath().equals(path)) {
		    if (kind == ENTRY_CREATE) {
			created(l);
		    } else if (kind == ENTRY_MODIFY) {
			modified(l);
		    } else if (kind == ENTRY_DELETE) {
			deleted(l);
		    } else {
			System.err.println("Invalid kind: " + kind + "(" + l
				+ ")");
		    }
		}
	    }
	} catch (final Exception e) {
	    e.printStackTrace();
	}
    }

    @Override
    public synchronized void run() {
	try {
	    createWatchService();
	    while (checkWatchService()) {
	    }
	} catch (final Exception e) {
	    e.printStackTrace();
	}
    }
}