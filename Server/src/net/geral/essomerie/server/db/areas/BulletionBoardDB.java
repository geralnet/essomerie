package net.geral.essomerie.server.db.areas;

import java.sql.ResultSet;
import java.sql.SQLException;

import net.geral.essomerie.server.db.Database;
import net.geral.essomerie.server.db.DatabaseArea;
import net.geral.essomerie.server.db.PreparedResultSet;
import net.geral.essomerie.shared.bulletinboard.BulletinBoardEntry;
import net.geral.essomerie.shared.bulletinboard.BulletinBoardTitle;
import net.geral.lib.jodatime.GNJoda;
import net.geral.lib.util.StringUtils;

import org.joda.time.LocalDateTime;

public class BulletionBoardDB extends DatabaseArea {
    public BulletionBoardDB(final Database database) {
	super(database);
    }

    public void delete(final int iduser, final int bbeid) throws SQLException {
	final String sql = "UPDATE `bulletinboard` "// update
		+ " SET `deleted_by`=?, `deleted_when`=NOW() "// set
		+ " WHERE `id`=? AND `deleted_when` IS NULL LIMIT 1";
	db.update(sql, iduser, bbeid);
    }

    public BulletinBoardEntry get(final int idbbe) throws SQLException {
	final String sql = "SELECT `id`, `title`, `contents`, `created_when`, `created_by` "// select
		+ " FROM `bulletinboard` "// from
		+ " WHERE (`id`=?) AND (`deleted_when` IS NULL) "// where
	;
	final PreparedResultSet p = db.select(sql, idbbe);
	BulletinBoardEntry bbe = null;
	final ResultSet rs = p.rs;
	if (rs.next()) {
	    final int id = rs.getInt("id");
	    final String title = rs.getString("title");
	    final byte[] contents = rs.getBytes("contents");
	    final LocalDateTime createdWhen = GNJoda.sqlLocalDateTime(
		    rs.getString("created_when"), false);
	    final int createdBy = rs.getInt("created_by");
	    bbe = new BulletinBoardEntry(id, title, contents, createdWhen,
		    createdBy);
	}
	rs.close();
	return bbe;
    }

    public BulletinBoardTitle[] getTitles() throws SQLException {
	final String sql = "SELECT `id`,`title` "// select
		+ " FROM `bulletinboard` "// from
		+ " WHERE `deleted_when` IS NULL "// where
	;
	final PreparedResultSet p = db.select(sql);
	final BulletinBoardTitle[] titles = new BulletinBoardTitle[p
		.getRowCount()];
	final ResultSet rs = p.rs;
	int i = 0;
	while (rs.next()) {
	    final int id = rs.getInt("id");
	    final String titulo = rs.getString("title");
	    titles[i++] = new BulletinBoardTitle(id, titulo);
	}
	rs.close();
	return titles;
    }

    public BulletinBoardEntry save(final int userid,
	    final BulletinBoardEntry bbe) throws SQLException {
	String sql = "INSERT INTO `bulletinboard` (`id`,`title`,`contents`,`created_by`,`created_when`) "// insert
		+ " VALUES (NULL, ?, UNHEX(?), ?, NOW())";// values
	final int newId = db.insertLastId(sql, bbe.getFullTitle(),
		StringUtils.toHex(bbe.getRtfContents()), userid);

	if (bbe.getId() > 0) {
	    sql = "UPDATE `bulletinboard` "// update
		    + " SET `changed_to`=?, `deleted_by`=?, `deleted_when`=NOW() "// set
		    + " WHERE `id`=? LIMIT 1"// where
	    ;
	    db.update(sql, newId, userid, bbe.getId());
	}

	return get(newId);
    }
}
