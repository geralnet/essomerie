package net.geral.essomerie.server.db.areas;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import net.geral.essomerie._shared.mensagens.Message;
import net.geral.essomerie._shared.mensagens.MessageStatus;
import net.geral.essomerie.server.db.Database;
import net.geral.essomerie.server.db.DatabaseArea;
import net.geral.essomerie.server.db.PreparedResultSet;
import net.geral.lib.jodatime.GNJoda;

import org.apache.log4j.Logger;

public class MessageDB extends DatabaseArea {
    private static final Logger logger = Logger.getLogger(MessageDB.class);

    public MessageDB(final Database database) {
	super(database);
    }

    public void createMessageStatusIfNeeded(final int idmsg, final int iduser)
	    throws SQLException {

	String sql = "SELECT COUNT(*) " // select
		+ " FROM `messages_status` " // from
		+ " WHERE `idmessage`=? AND `iduser`=?"; // where...
	final int n = db.selectFirstField_int(sql, idmsg, iduser);

	if (n > 0) {
	    return; // no need to create
	}

	sql = "INSERT INTO `messages_status` (`idmessage`,`iduser`) VALUES (?,?)";
	db.insert(sql, idmsg, iduser);
    }

    public Message[] createNew(final int from, final int[] tos,
	    final String message) throws SQLException {
	final Message msgs[] = new Message[tos.length];
	int i = 0;
	for (final int p : tos) {
	    final String sql = "INSERT INTO `messages` "// insert
		    + " (`from`,`to`,`message`,`sent_when`) "// fields
		    + " VALUES (?,?,?,NOW()) ";
	    final int id = db.insertLastId(sql, from, p, message);
	    msgs[i++] = getMessage(id);
	}
	return msgs;
    }

    public boolean delete(final int idmsg, final int userid)
	    throws SQLException {
	createMessageStatusIfNeeded(idmsg, userid);
	final String sql = "UPDATE `messages_status` "// update
		+ " SET `datetime_hidden`=NOW() "// set
		+ " WHERE `idmessage`=? AND `iduser`=? AND `datetime_hidden` IS NULL "// where
		+ " LIMIT 1";
	if (db.updateSuccess(sql, idmsg, userid)) {
	    return true;
	}
	logger.warn("Cannot delete as requested: " + idmsg + " for " + userid);
	return false;
    }

    public int[] delete(final int[] idmsgs, final int userid)
	    throws SQLException {
	final ArrayList<Integer> deleted = new ArrayList<>();
	for (final int idmsg : idmsgs) {
	    if (delete(idmsg, userid)) {
		deleted.add(idmsg);
	    }
	}
	final int[] array = new int[deleted.size()];
	for (int i = 0; i < deleted.size(); i++) {
	    array[i] = deleted.get(i);
	}
	return array;
    }

    public boolean getHasUnread(final int userid) throws SQLException {
	final String sql = "SELECT `id` " // select
		+ " FROM `messages` "// from
		+ " LEFT JOIN `messages_status` ON (`id`=`idmessage` AND `iduser`=?)" // join
		+ " WHERE (`to`=0 OR `to`=?) AND (`datetime_read` IS NULL) AND (`datetime_hidden` IS NULL) " // where...
		+ " LIMIT 1" // if there is one, is enough
	;
	try (final PreparedResultSet p = db.select(sql, userid, userid)) {
	    return p.rs.next();
	}
    }

    public Message getMessage(final int idmessage) throws SQLException {
	final String sql = "SELECT `id`,`from`,`to`,`message`,`sent_when` " // select
		+ " FROM `messages` " // from
		+ " WHERE `id`=?"; // where...
	try (final PreparedResultSet p = db.select(sql, idmessage)) {
	    Message msg = null;
	    final ResultSet rs = p.rs;
	    if (rs.next()) {
		msg = new Message(rs.getInt("id"));
		msg.setFrom(rs.getInt("from"));
		msg.setTo(rs.getInt("to"));
		msg.setMessage(rs.getString("message"));
		msg.setSent(GNJoda.sqlLocalDateTime(rs.getString("sent_when"),
			false));
		final MessageStatus[] leituras = getStatus(idmessage);
		msg.setStatus(leituras);
	    }
	    return msg;
	}
    }

    private MessageStatus[] getStatus(final int idmessage) throws SQLException {
	final String sql = "SELECT `iduser`,`datetime_read` " // select
		+ " FROM `messages_status` " // from
		+ " WHERE `idmessage`=?"; // where...
	try (final PreparedResultSet p = db.select(sql, idmessage)) {
	    final MessageStatus[] mls = new MessageStatus[p.getRowCount()];
	    int i = 0;
	    final ResultSet rs = p.rs;
	    while (rs.next()) {
		mls[i++] = new MessageStatus(rs.getInt("iduser"),
			GNJoda.sqlLocalDateTime(rs.getString("datetime_read"),
				true));
	    }
	    return mls;
	}
    }

    public Message[] getToUser(final int userid) throws SQLException {
	final String sql = "SELECT `id`,`from`,`to`,`message`,`sent_when` " // select
		+ " FROM `messages` "// from
		+ " LEFT JOIN `messages_status` ON (`id`=`idmessage` AND `iduser`=?)" // join
		+ " WHERE (`to`=0 OR `to`=?) AND (`datetime_hidden` IS NULL) " // where...
		+ " ORDER BY `sent_when` DESC";
	try (final PreparedResultSet p = db.select(sql, userid, userid)) {
	    final Message[] msgs = new Message[p.getRowCount()];
	    int i = 0;
	    final ResultSet rs = p.rs;
	    while (rs.next()) {
		final Message msg = new Message(rs.getInt("id"));
		msg.setFrom(rs.getInt("from"));
		msg.setTo(rs.getInt("to"));
		msg.setMessage(rs.getString("message"));
		msg.setSent(GNJoda.sqlLocalDateTime(rs.getString("sent_when"),
			false));
		final MessageStatus[] status = getStatus(rs.getInt("id"));
		msg.setStatus(status);
		msgs[i++] = msg;
	    }
	    return msgs;
	}
    }

    public boolean setMessageStatusRead(final int idmessage, final int iduser)
	    throws SQLException {
	createMessageStatusIfNeeded(idmessage, iduser);

	final String sql = "UPDATE `messages_status` "// update
		+ " SET `datetime_read`=NOW() "// set
		+ " WHERE `idmessage`=? AND `iduser`=? AND `datetime_hidden` IS NULL LIMIT 1";
	return db.updateSuccess(sql, idmessage, iduser);
    }
}
