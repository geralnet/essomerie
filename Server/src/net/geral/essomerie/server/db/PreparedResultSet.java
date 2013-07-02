package net.geral.essomerie.server.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PreparedResultSet implements AutoCloseable {
    public final ResultSet rs;
    private final PreparedStatement preparedStatement;

    public PreparedResultSet(final ResultSet result,
	    final PreparedStatement statement) {
	rs = result;
	preparedStatement = statement;
    }

    @Override
    @Deprecated
    /**
     * Use 'try (...) { ... }' instead of opening/closing directly.
     */
    public void close() throws SQLException {
	preparedStatement.close();
    }

    public int getRowCount() throws SQLException {
	return MySQL.getRowCount(rs);
    }

    public PreparedStatement getStatement() {
	return preparedStatement;
    }
}
