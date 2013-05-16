package net.geral.essomerie.server.db;

public class DatabaseArea {
    protected Database db;

    public DatabaseArea(final Database database) {
	if (database == null) {
	    throw new IllegalArgumentException("database cannot be null.");
	}
	db = database;
    }
}
