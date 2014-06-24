package net.geral.essomerie.server.db;

import java.sql.SQLException;

import net.geral.essomerie.server.core.Configuration;
import net.geral.essomerie.server.db.areas.BulletionBoardDB;
import net.geral.essomerie.server.db.areas.CalendarDB;
import net.geral.essomerie.server.db.areas.CallerDB;
import net.geral.essomerie.server.db.areas.CatalogDB;
import net.geral.essomerie.server.db.areas.MessageDB;
import net.geral.essomerie.server.db.areas.PersonDB;
import net.geral.essomerie.server.db.areas.SysopDB;
import net.geral.essomerie.server.db.areas.UsersDB;
import net.geral.essomerie.server.db.areas.WarehouseDB;

import org.apache.log4j.Logger;

public class Database extends MySQL {
    private static final Logger logger = Logger.getLogger(Database.class);

    private final BulletionBoardDB bulletinBoard = new BulletionBoardDB(this);
    private final CalendarDB calendar = new CalendarDB(this);
    private final WarehouseDB warehouse = new WarehouseDB(this);
    private final PersonDB person = new PersonDB(this);
    private final MessageDB message = new MessageDB(this);
    private final UsersDB users = new UsersDB(this);
    private final CatalogDB catalog = new CatalogDB(this);
    private final SysopDB sysop = new SysopDB(this);
    private final CallerDB caller = new CallerDB(this);

    private int execution_id = 0;

    public BulletionBoardDB bulletinBoard() {
	return bulletinBoard;
    }

    public CalendarDB calendar() {
	return calendar;
    }

    public CallerDB caller() {
	return caller;
    }

    public CatalogDB catalog() {
	return catalog;
    }

    @Override
    public void close() {
	if (execution_id > 0) {
	    try {
		update("UPDATE `log_execution` SET `stopped`=NOW() WHERE `id`=?",
			execution_id);
	    } catch (final SQLException e) {
		logger.warn("Could not log execution stopped.", e);
	    }
	    execution_id = 0;
	}
	super.close();
    }

    public void keepAlive() throws SQLException {
	selectFirstField_int("SELECT 1");
    }

    public MessageDB message() {
	return message;
    }

    @Override
    public boolean open(final Configuration config) {
	// TODO add timer or something to keep trying
	if (!super.open(config)) {
	    return false;
	}
	sysop.updateSystemDatabaseName();
	try {
	    execution_id = insertLastId("INSERT INTO `log_execution` (`started`) VALUES (NOW())");
	    return true;
	} catch (final SQLException e) {
	    logger.debug("Error inserting row. Closing database...", e);
	    close();
	    return false;
	}
    }

    public PersonDB person() {
	return person;
    }

    public SysopDB sysop() {
	return sysop;
    }

    public UsersDB users() {
	return users;
    }

    public WarehouseDB warehouse() {
	return warehouse;
    }
}
