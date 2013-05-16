package net.geral.essomerie.shared.bulletinboard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;

public class BulletinBoardData {
    private final Hashtable<Integer, BulletinBoardEntry> entries = new Hashtable<Integer, BulletinBoardEntry>();
    private ArrayList<BulletinBoardEntry> sorted = null;

    public synchronized int count() {
	return entries.size();
    }

    private synchronized void createSortedList() {
	sorted = new ArrayList<BulletinBoardEntry>(entries.size());
	for (final BulletinBoardEntry i : entries.values()) {
	    sorted.add(i);
	}
	Collections.sort(sorted);
    }

    public synchronized BulletinBoardEntry get(final int id) {
	if (id <= 0) {
	    throw new IllegalArgumentException("id must be positive.");
	}
	return entries.get(id);
    }

    public synchronized BulletinBoardEntry[] getAllSorted() {
	if (sorted == null) {
	    createSortedList();
	}
	return sorted.toArray(new BulletinBoardEntry[sorted.size()]);
    }

    public synchronized void set(final BulletinBoardEntry[] newEntries) {
	if (newEntries == null) {
	    throw new IllegalArgumentException("new entries cannot be null.");
	}

	entries.clear();
	for (final BulletinBoardEntry i : newEntries) {
	    entries.put(i.getId(), i);
	}
	sorted = null;
    }
}
