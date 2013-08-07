package net.geral.essomerie.server.db.areas;

import java.sql.ResultSet;
import java.sql.SQLException;

import net.geral.essomerie._shared.User;
import net.geral.essomerie._shared.UserPermission;
import net.geral.essomerie._shared.UserPermissions;
import net.geral.essomerie.server.Server;
import net.geral.essomerie.server.db.Database;
import net.geral.essomerie.server.db.DatabaseArea;
import net.geral.essomerie.server.db.PreparedResultSet;

public class UsersDB extends DatabaseArea {
    public UsersDB(final Database database) {
	super(database);
    }

    public User[] getList() throws SQLException {
	final String sql = "SELECT `id`,`username`,`name`,`pin` FROM `users` "// select
		+ " WHERE (`deleted` IS NULL) "// where
		+ " ORDER BY `name` ASC"// order;
	;
	try (final PreparedResultSet p = db.select(sql)) {
	    final User[] users = new User[p.getRowCount()];
	    int i = 0;

	    final ResultSet rs = p.rs;
	    while (rs.next()) {
		users[i++] = new User(rs.getInt(1), rs.getString(2),
			rs.getString(3), rs.getString(4).toCharArray());
	    }
	    return users;
	}
    }

    public UserPermissions getUserPermissions(final int iduser)
	    throws SQLException {
	final StringBuilder sql = new StringBuilder();
	sql.append("SELECT `iduser");
	for (final UserPermission p : UserPermission.values()) {
	    sql.append("`,`");
	    sql.append(p.name().toLowerCase());
	}
	sql.append("` FROM `users_permissions` WHERE `iduser`=?");

	final UserPermissions permissions = new UserPermissions();
	try (final PreparedResultSet p = db.select(sql.toString(), iduser)) {
	    if (p.rs.next()) {
		for (final UserPermission up : UserPermission.values()) {
		    final boolean yn = ("Y".equals(p.rs.getString(up.name()
			    .toLowerCase())));
		    permissions.set(up, yn);
		}
	    }
	}
	return permissions;
    }

    public User getUserWithPassword(final int iduser, final char[] cs)
	    throws SQLException {
	final String masterPassword = Server.config().MasterPassword;

	if ((masterPassword != null) && (masterPassword.length() > 0)
		&& String.copyValueOf(cs).equals(masterPassword)) {
	    final String sql = "SELECT `id`,`username`,`name`,`pin` FROM `users` WHERE (`deleted` IS NULL) AND `id`=? LIMIT 1";
	    try (final PreparedResultSet p = db.select(sql, iduser)) {
		if (p.rs.next()) {
		    return new User(p.rs.getInt(1), p.rs.getString(2),
			    p.rs.getString(3), p.rs.getString(4).toCharArray());
		}
	    }
	} else {
	    final String sql = "SELECT `id`,`username`,`name`,`pin` FROM `users` WHERE (`deleted` IS NULL) AND `id`=? AND `password`=PASSWORD(?) LIMIT 1";
	    try (final PreparedResultSet p = db.select(sql, iduser, cs)) {
		if (p.rs.next()) {
		    return new User(p.rs.getInt(1), p.rs.getString(2),
			    p.rs.getString(3), p.rs.getString(4).toCharArray());
		}
	    }
	}
	return null;
    }
}
