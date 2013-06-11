package net.geral.essomerie.server.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import net.geral.essomerie.server.core.Configuration;
import net.geral.essomerie.shared.money.Money;
import net.geral.lib.jodatime.GNJoda;

import org.apache.log4j.Logger;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class MySQL {
    private static final Logger logger = Logger.getLogger(MySQL.class);
    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormat
	    .forPattern("yyyy-MM-dd HH:mm:ss");

    public static int getRowCount(final ResultSet rs) throws SQLException {
	rs.last();
	final int rows = rs.getRow();
	rs.beforeFirst();
	return rows;
    }

    private int selects = 0;
    private int inserts = 0;
    private int updates = 0;
    private int deletes = 0;
    private Connection connection = null;

    public MySQL() {
    }

    public void close() {
	if (connection != null) {
	    logger.debug("MySQL: Closing...");
	    try {
		connection.close();
	    } catch (final SQLException e) {
		logger.warn("Could not close.", e);
	    }
	    connection = null;
	}
    }

    public void delete(final String sql, final Object... params)
	    throws SQLException {
	deletes++;
	query(sql, params);
    }

    public void deleteCount(final String sql, final Object... params)
	    throws SQLException {
	deletes++;
	query(sql, params);
    }

    public void insert(final String sql, final Object... params)
	    throws SQLException {
	inserts++;
	query(sql, params);
    }

    public int insertCount(final String sql, final Object... params)
	    throws SQLException {
	inserts++;
	return queryCount(sql, params);
    }

    public int insertLastId(final String sql, final Object... params)
	    throws SQLException {
	inserts++;
	return queryGeneratedKey(sql, params);
    }

    protected boolean open(final Configuration config) {
	if (connection != null) {
	    close();
	}
	// the line below replaces Class.forName("com.mysql.jdbc.Driver")
	logger.debug("Initializing MySQL Driver: "
		+ com.mysql.jdbc.Driver.class);
	final String url = "jdbc:mysql://" + config.MysqlServer + ":"
		+ config.MysqlPort + "/" + config.MysqlDatabase;
	logger.debug("MySQL: Connecting to " + config.MysqlUsername + "@" + url
		+ " (with password? "
		+ (config.MysqlPassword.length() == 0 ? "no" : "yes") + ")");
	try {
	    connection = DriverManager.getConnection(url, config.MysqlUsername,
		    config.MysqlPassword);
	    return true;
	} catch (final SQLException e) {
	    connection = null;
	    logger.warn("Cannot connect to database!", e);
	    return false;
	}
    }

    private void query(final String sql, final Object[] params)
	    throws SQLException {
	final PreparedStatement ps = connection.prepareStatement(sql,
		Statement.NO_GENERATED_KEYS);
	setSqlParameters(ps, params);
	ps.execute();
	ps.close();
    }

    private int queryCount(final String sql, final Object[] params)
	    throws SQLException {
	final PreparedStatement ps = connection.prepareStatement(sql,
		Statement.NO_GENERATED_KEYS);
	setSqlParameters(ps, params);
	ps.execute();
	final int n = ps.getUpdateCount();
	ps.close();
	return n;
    }

    private int queryGeneratedKey(final String sql, final Object[] params)
	    throws SQLException {
	final PreparedStatement ps = connection.prepareStatement(sql,
		Statement.RETURN_GENERATED_KEYS);
	setSqlParameters(ps, params);

	ps.execute();
	final ResultSet rs = ps.getGeneratedKeys();
	if (!rs.next()) {
	    throw new SQLException("Unable to read generated key for: "
		    + ps.toString());
	}
	final int result = rs.getInt(1);
	ps.close();
	return result;
    }

    private PreparedResultSet queryResult(final String sql,
	    final Object[] params) throws SQLException {
	if (connection == null) {
	    throw new SQLException("Not Connected.");
	}
	final PreparedStatement ps = connection.prepareStatement(sql,
		Statement.NO_GENERATED_KEYS);
	setSqlParameters(ps, params);

	final ResultSet rs = ps.executeQuery();
	return new PreparedResultSet(rs, ps);
    }

    public PreparedResultSet select(final String sql, final Object... params)
	    throws SQLException {
	selects++;
	return queryResult(sql, params);
    }

    public float selectFirstField_float(final String sql,
	    final Object... params) throws SQLException {
	selects++;
	final PreparedResultSet p = queryResult(sql, params);

	if (!p.rs.next()) {
	    p.close();
	    throw new SQLException("Cannot fetch first row.");
	}
	final float f = p.rs.getFloat(1);
	p.close();
	return f;
    }

    public int selectFirstField_int(final String sql, final Object... params)
	    throws SQLException {
	selects++;
	final PreparedResultSet p = queryResult(sql, params);

	if (!p.rs.next()) {
	    p.close();
	    throw new SQLException("Cannot fetch first row.");
	}
	final int i = p.rs.getInt(1);
	p.close();
	return i;
    }

    public Integer selectFirstField_IntegerOrNull(final String sql,
	    final Object... params) throws SQLException {
	selects++;
	final PreparedResultSet p = queryResult(sql, params);

	if (!p.rs.next()) {
	    p.close();
	    return null;
	}
	final int i = p.rs.getInt(1);
	p.close();
	return new Integer(i);
    }

    public LocalDateTime selectFirstField_LocalDateTime(final String sql,
	    final Object... params) throws SQLException {
	selects++;
	final PreparedResultSet p = queryResult(sql, params);

	if (!p.rs.next()) {
	    p.close();
	    throw new SQLException("Cannot fetch first row.");
	}
	final LocalDateTime dt = GNJoda.sqlLocalDateTime(p.rs.getString(1),
		false);
	p.close();
	return dt;
    }

    public String selectFirstField_String(final String sql,
	    final Object... params) throws SQLException {
	selects++;
	final PreparedResultSet p = queryResult(sql, params);

	if (!p.rs.next()) {
	    p.close();
	    return null;
	}
	final String s = p.rs.getString(1);
	p.close();
	return s;
    }

    private void setSqlParameters(final PreparedStatement ps,
	    final Object[] params) throws SQLException {
	int i = 1;
	for (final Object o : params) {
	    if (o == null) {
		ps.setNull(i, Types.NULL); // should be the correct type, null
					   // seems to work
	    } else if (o instanceof String) {
		ps.setString(i, (String) o);
	    } else if (o instanceof Integer) {
		ps.setInt(i, ((Integer) o).intValue());
	    } else if (o instanceof Long) {
		ps.setLong(i, ((Long) o).longValue());
	    } else if (o instanceof LocalDate) {
		ps.setString(i, ((LocalDate) o).toString());
	    } else if (o instanceof LocalDateTime) {
		ps.setString(i,
			((LocalDateTime) o).toString(DATETIME_FORMATTER));
	    } else if (o instanceof Character) {
		ps.setString(i, o.toString());
	    } else if (o instanceof Float) {
		ps.setFloat(i, ((Float) o).floatValue());
	    } else if (o instanceof byte[]) {
		ps.setBytes(i, (byte[]) o);
	    } else if (o instanceof Money) {
		ps.setString(i, ((Money) o).toSQL());
	    } else if (o instanceof char[]) {
		ps.setString(i, String.copyValueOf((char[]) o));
	    } else if (o instanceof Enum<?>) {
		ps.setString(i, ((Enum<?>) o).name());
	    } else {
		throw new SQLException("Invalid param #" + i + " class: "
			+ o.getClass() + " (" + o.toString() + ")");
	    }
	    i++;
	}
	logger.debug("MySQL: " + ps.toString().split(": ", 2)[1]);
    }

    @Override
    public String toString() {
	return "MySQL[" + selects + "s;" + inserts + "i;" + updates + "u;"
		+ deletes + "d]";
    }

    public void update(final String sql, final Object... params)
	    throws SQLException {
	updates++;
	query(sql, params);
    }

    public int updateCount(final String sql, final Object... params)
	    throws SQLException {
	updates++;
	return queryCount(sql, params);
    }

    public boolean updateSuccess(final String sql, final Object... params)
	    throws SQLException {
	updates++;
	return (queryCount(sql, params) > 0);
    }
}
