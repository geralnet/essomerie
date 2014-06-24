package net.geral.essomerie.server.db.areas;

import java.sql.SQLException;

import net.geral.essomerie.server.db.Database;
import net.geral.essomerie.server.db.DatabaseArea;
import net.geral.essomerie.shared.communication.types.CallerIdMessageType;

public class CallerDB extends DatabaseArea {
    public CallerDB(final Database db) {
	super(db);
    }

    public void register(final String line, final CallerIdMessageType type,
	    final String number, final int idperson) throws SQLException {
	final String sql = "INSERT INTO `caller` " // insert
		+ " (`when`,`idperson`,`io`,`line`,`number`) " // fields
		+ " VALUES (NOW(), ?, ?, ?, ?) ";
	db.insert(sql, idperson, type.name(), line, number);
    }
}
