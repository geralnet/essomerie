package net.geral.essomerie.server.db.areas;

import java.sql.ResultSet;
import java.sql.SQLException;

import net.geral.essomerie.server.db.Database;
import net.geral.essomerie.server.db.DatabaseArea;
import net.geral.essomerie.server.db.PreparedResultSet;
import net.geral.essomerie.shared.calendar.CalendarEvent;
import net.geral.essomerie.shared.roster.Roster;
import net.geral.essomerie.shared.roster.RosterEntry;
import net.geral.lib.jodatime.GNJoda;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

public class CalendarDB extends DatabaseArea {
    public CalendarDB(final Database database) {
	super(database);
    }

    public boolean delete(final int idevent, final int iduser)
	    throws SQLException {
	final String sql = "UPDATE `calendario`"// update
		+ " SET `excluido`='Y', `log_excluido_datahora`=NOW(), `log_excluido_por`=?"// set
		+ " WHERE `id`=? LIMIT 1";
	return db.updateSuccess(sql, iduser, idevent);
    }

    private void deleteRoster(final int iduser, final LocalDate date,
	    final boolean dayShift) throws SQLException {
	final String sql = "UPDATE `escala` " // update
		+ " SET `excluido`=NOW(), `excluido_por`=? " // set
		+ " WHERE `data`=? AND turno=? " // where
	;
	db.update(sql, iduser, date, (dayShift ? "D" : "N"));
    }

    public CalendarEvent get(int idevent, final boolean withPrevious)
	    throws SQLException {
	if (idevent < 0) {
	    throw new IllegalArgumentException("idevent (" + idevent
		    + ") must be non-negative.");
	}
	if (idevent == 0) {
	    return null; // when getting reference for 0 (previous)
	}

	try (PreparedResultSet p = db.select(
		"SELECT * FROM `calendario` WHERE `id`=?", idevent)) {
	    ;
	    final ResultSet rs = p.rs;
	    if (rs.next()) {
		idevent = rs.getInt("id");
		final LocalDate data = GNJoda.sqlLocalDate(
			rs.getString("data"), false);
		final String evento = rs.getString("mensagem");
		final int log_iduser = rs.getInt("log_usuario");
		final LocalDateTime log_datahora = GNJoda.sqlLocalDateTime(
			rs.getString("log_datahora"), false);
		final CalendarEvent referencia = (withPrevious ? get(
			rs.getInt("idreferencia"), true) : null);
		return new CalendarEvent(idevent, data, evento, log_iduser,
			log_datahora, referencia);
	    }
	}
	return null;
    }

    public CalendarEvent[] get(final LocalDate date) throws SQLException {
	final String sql = "SELECT * FROM `calendario` WHERE `excluido`='N' AND `data`=? ORDER BY `mensagem` ASC";
	try (PreparedResultSet p = db.select(sql, date)) {
	    final CalendarEvent[] cs = new CalendarEvent[p.getRowCount()];
	    int i = 0;
	    final ResultSet rs = p.rs;
	    while (rs.next()) {
		final int id = rs.getInt("id");
		final LocalDate data = GNJoda.sqlLocalDate(
			rs.getString("data"), false);
		final String evento = rs.getString("mensagem");
		final int log_iduser = rs.getInt("log_usuario");
		final LocalDateTime log_datahora = GNJoda.sqlLocalDateTime(
			rs.getString("log_datahora"), false);
		final CalendarEvent referencia = null;
		cs[i++] = new CalendarEvent(id, data, evento, log_iduser,
			log_datahora, referencia);
	    }
	    return cs;
	}
    }

    public Roster getRoster(final LocalDate date, final boolean dayShift)
	    throws SQLException {
	final String sql = "SELECT `id`,`funcao`,`funcionarios` "// select
		+ " FROM `escala` "// from
		+ " WHERE `data`=? AND `turno`=? AND (`excluido` IS NULL)"// from
		+ " ORDER BY `funcao` ASC, `funcionarios` ASC";

	try (final PreparedResultSet p = db.select(sql, date, (dayShift ? "D"
		: "N"))) {
	    final Roster re = new Roster(date, dayShift);
	    final ResultSet rs = p.rs;
	    while (rs.next()) {
		final int id = rs.getInt("id");
		final String funcao = rs.getString("funcao");
		final String funcionarios = rs.getString("funcionarios");
		re.addEntry(id, funcao, funcionarios);
	    }
	    return re;
	}
    }

    public CalendarEvent save(final int idprevious, final LocalDate date,
	    final String message, final int iduser) throws SQLException {
	if (idprevious < 0) {
	    throw new IllegalArgumentException("idprevious (" + idprevious
		    + ") cannot be negative.");
	}

	if (idprevious > 0) {
	    delete(idprevious, iduser);
	}

	final String sql = "INSERT INTO `calendario` "// insert
		+ " (`data`,`mensagem`,`idreferencia`,`log_usuario`,`log_datahora`) "// fields
		+ " VALUES(?,?,?,?,NOW())";// values
	final int lastid = db.insertLastId(sql, date, message, idprevious,
		iduser);
	return get(lastid, true); // send with details so interfaces can remove
				  // the old
    }

    public void saveRoster(final Roster re, final int iduser)
	    throws SQLException {
	deleteRoster(iduser, re.getDate(), re.isDayShift());
	final String date = re.getDate().toString();
	final String shift = re.isDayShift() ? "D" : "N";

	for (final RosterEntry ref : re.getEntries()) {
	    saveRosterAssignments(iduser, date, shift, ref);
	}
    }

    private void saveRosterAssignments(final int iduser, final String date,
	    final String shift, final RosterEntry ria)
	    throws SQLException {
	final String sql = "INSERT INTO `escala` (`data`,`turno`,`funcao`,`funcionarios`,`criado_por`) "// insert
		+ " VALUES (?, ?, ?, ?, ?)"// values
	;
	db.insert(sql, date, shift, ria.getAssignment(),
		ria.getNamesString(), iduser);
    }
}
