package net.geral.essomerie.server.db.areas;

import java.sql.ResultSet;
import java.sql.SQLException;

import net.geral.essomerie.server.db.Database;
import net.geral.essomerie.server.db.DatabaseArea;
import net.geral.essomerie.server.db.PreparedResultSet;
import net.geral.essomerie.shared.warehouse.Warehouse;
import net.geral.essomerie.shared.warehouse.WarehouseChangeLog;
import net.geral.essomerie.shared.warehouse.WarehouseChangeLogEntry;
import net.geral.essomerie.shared.warehouse.WarehouseChangeReason;
import net.geral.essomerie.shared.warehouse.WarehouseChangeReasons;
import net.geral.essomerie.shared.warehouse.WarehouseGroup;
import net.geral.essomerie.shared.warehouse.WarehouseItem;
import net.geral.essomerie.shared.warehouse.WarehouseQuantityChange;
import net.geral.essomerie.shared.warehouse.report.MonthlyFlowReport;
import net.geral.lib.jodatime.GNJoda;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

public class WarehouseDB extends DatabaseArea {

    public WarehouseDB(final Database database) {
	super(database);
    }

    public void addGroup(final int iduser, final int idparent, final String name)
	    throws SQLException {
	final Integer res = db
		.selectFirstField_IntegerOrNull(
			"SELECT MAX(`order`) FROM `warehouse_group` WHERE `idparent`=?",
			idparent);
	final int order = (res == null) ? 0 : res.intValue() + 1;
	final String sql = "INSERT INTO `warehouse_group` (`name`,`idparent`,`order`,`log_created_user`,`log_created_when`) "
		+ " VALUES(?,?,?,?,NOW())";
	db.insert(sql, name, idparent, order, iduser);
    }

    public float change(final int iduser, final WarehouseQuantityChange change)
	    throws SQLException {

	String sql = "INSERT INTO `warehouse_quantity_log` "// insert
		+ " (`iduser`,`mode`,`idreason`,`iditem`,`quantity_before`,`quantity_change`,`when`,`comments`) "// fields
		+ " VALUES (?,?,?,?,(" // v1
		+ "    SELECT `quantity` FROM `warehouse_item` WHERE `id`=?" // subquery
		+ "),?,NOW(),?)"; // v2
	final WarehouseQuantityChange a = change;
	db.insert(sql, iduser, a.mode, a.idreason, a.iditem, a.iditem,
		a.quantity, a.comments);

	switch (a.mode) {
	case '+':
	    sql = "`quantity`=GREATEST(`quantity`+?,0)";
	    break;
	case '-':
	    sql = "`quantity`=GREATEST(`quantity`-?,0)";
	    break;
	case '=':
	    sql = "`quantity`=GREATEST(?,0)";
	    break;
	default:
	    throw new SQLException("WarehouseDB -- invalid mode: " + a.mode);
	}

	sql = "UPDATE `warehouse_item` SET " + sql + " WHERE `id`=? LIMIT 1";
	db.update(sql, a.quantity, a.iditem);

	sql = "SELECT `quantity` FROM `warehouse_item` WHERE `id`=?";
	final float newQuantity = db.selectFirstField_float(sql, a.iditem);
	return newQuantity;
    }

    public int createItem(final int iduser, final String name)
	    throws SQLException {
	final String sql = "INSERT INTO `warehouse_item` (`name`,`log_created_user`,`log_created_when`) VALUES (?, ?, NOW())";
	return db.insertLastId(sql, name, iduser);
    }

    public void deleteGroup(final int iduser, final int idgroup)
	    throws SQLException {
	final String sql = "UPDATE `warehouse_group` SET `deleted`='Y', log_deleted_user=?, log_deleted_when=NOW() WHERE `id`=? LIMIT 1";
	db.update(sql, iduser, idgroup);
    }

    public boolean deleteItem(final int iduser, final int iditem)
	    throws SQLException {
	final String sql = "UPDATE `warehouse_item` SET `deleted`='Y', log_deleted_user=?, log_deleted_when=NOW() WHERE `id`=? LIMIT 1";
	return db.updateSuccess(sql, iduser, iditem);
    }

    public Warehouse getFullData() throws SQLException {
	final Warehouse w = new Warehouse();
	w.setReasons(getReasons());
	w.setGroups(getGroups());
	w.setItems(getItems());
	return w;
    }

    public WarehouseGroup[] getGroups() throws SQLException {
	return getGroups(0);
    }

    private WarehouseGroup[] getGroups(final int pai) throws SQLException {
	final String sql = "SELECT `id`,`name` "// select
		+ "FROM `warehouse_group` "// from
		+ " WHERE `idparent`=? AND `deleted`='N' "// where
		+ " ORDER BY `order` ASC, `name` ASC";
	try (final PreparedResultSet p = db.select(sql, pai)) {
	    final WarehouseGroup[] wg = new WarehouseGroup[p.getRowCount()];
	    for (int i = 0; i < wg.length; i++) {
		p.rs.next();
		final int id = p.rs.getInt("id");
		final String name = p.rs.getString("name");
		final WarehouseGroup[] subgroups = getGroups(id);
		wg[i] = new WarehouseGroup(id, name, subgroups);
	    }
	    return wg;
	}
    }

    public WarehouseItem getItem(final int iditem) throws SQLException {
	final String sql = "SELECT `id`,`idgroup`,`name`,`unit`,`quantity`,`minimum`,`maximum` "// select
		+ "FROM `warehouse_item` "// from
		+ " WHERE `id`=? "// where
	;
	try (final PreparedResultSet p = db.select(sql, iditem)) {
	    if (!p.rs.next()) {
		return null;
	    }
	    final int id = p.rs.getInt("id");
	    final int idgroup = p.rs.getInt("idgroup");
	    final String name = p.rs.getString("name");
	    final String unit = p.rs.getString("unit");
	    final float quantity = p.rs.getFloat("quantity");
	    final float minimum = p.rs.getFloat("minimum");
	    final float maximum = p.rs.getFloat("maximum");
	    return new WarehouseItem(id, idgroup, name, unit, quantity,
		    minimum, maximum);
	}
    }

    public MonthlyFlowReport getItemReport(final int iditem)
	    throws SQLException {
	final WarehouseItem item = getItem(iditem);
	final String sql = "SELECT "// select
		+ " CONCAT(`cr`.`description`,' (',`cr`.`mode`,')') AS `reason`, " // reason
		+ " `ql`.`quantity_before`, " // month started with
		+ " DATE(`ql`.`when`) AS `date`," // date
		+ " IF(`ql`.`mode`='-',-1,1)*SUM(IF(`ql`.`mode`='=',ROUND(`ql`.`quantity_change`-`ql`.`quantity_before`,2),`ql`.`quantity_change`)) AS `delta`" // delta
		+ " FROM `warehouse_quantity_log` AS `ql` " // from
		+ " INNER JOIN `warehouse_change_reason` AS `cr` ON (`cr`.`id`=`ql`.`idreason`) AND (`ql`.`mode`=`cr`.`mode`) " // join
		+ " WHERE `ql`.`iditem`=?" // where
		+ " GROUP BY YEAR(`ql`.`when`) DESC, MONTH(`ql`.`when`) DESC, `ql`.`mode` ASC, `cr`.`description` ASC" // group
		+ " ORDER BY `ql`.`when` ASC"; // order
	final MonthlyFlowReport r = new MonthlyFlowReport(item);
	final PreparedResultSet p = db.select(sql, iditem);
	final ResultSet rs = p.rs;
	while (rs.next()) {
	    final LocalDate date = GNJoda.sqlLocalDate(rs.getString("date"),
		    false).withDayOfMonth(1);
	    final String reason = rs.getString("reason");
	    final float delta = rs.getFloat("delta");
	    final float initial = rs.getFloat("quantity_before");
	    r.addEntry(date, initial, reason, delta);
	}
	r.block();
	return r;
    }

    private WarehouseItem[] getItems() throws SQLException {
	final String sql = "SELECT `id`,`idgroup`,`name`,`unit`,`quantity`,`minimum`,`maximum` "// select
		+ "FROM `warehouse_item` "// from
		+ " WHERE `deleted`='N' "// where
		+ " ORDER BY `name` ASC";
	try (final PreparedResultSet p = db.select(sql)) {
	    final WarehouseItem[] wi = new WarehouseItem[p.getRowCount()];
	    for (int i = 0; i < wi.length; i++) {
		p.rs.next();
		final int id = p.rs.getInt("id");
		final int idgroup = p.rs.getInt("idgroup");
		final String name = p.rs.getString("name");
		final String unit = p.rs.getString("unit");
		final float quantity = p.rs.getFloat("quantity");
		final float minimum = p.rs.getFloat("minimum");
		final float maximum = p.rs.getFloat("maximum");
		wi[i] = new WarehouseItem(id, idgroup, name, unit, quantity,
			minimum, maximum);
	    }
	    return wi;
	}
    }

    public WarehouseChangeLog getQuantityHistory(final int iditem)
	    throws SQLException {
	final String sql = "SELECT * FROM `warehouse_quantity_log` WHERE `iditem`=? "// select
		+ " ORDER BY `when` DESC LIMIT 10";
	final PreparedResultSet p = db.select(sql, iditem);
	final WarehouseChangeLog log = new WarehouseChangeLog(iditem,
		p.getRowCount());
	int i = 0;
	final ResultSet rs = p.rs;
	while (rs.next()) {
	    final int id = rs.getInt("id");
	    final int iduser = rs.getInt("iduser");
	    final char mode = rs.getString("mode").charAt(0);
	    final int idreason = rs.getInt("idreason");
	    final float quantityBefore = rs.getFloat("quantity_before");
	    final float quantityChange = rs.getFloat("quantity_change");
	    final LocalDateTime when = GNJoda.sqlLocalDateTime(
		    rs.getString("when"), false);
	    final String comments = rs.getString("comments");
	    final WarehouseChangeLogEntry l = new WarehouseChangeLogEntry(id,
		    iditem, iduser, mode, idreason, quantityBefore,
		    quantityChange, when, comments);
	    log.set(i++, l);
	}
	return log;
    }

    public WarehouseChangeLogEntry[] getQuantityLog(final LocalDate de,
	    final LocalDate ate) throws SQLException {
	final String sql = "SELECT * FROM `warehouse_quantity_log` WHERE `when`>=? AND `when`<? ORDER BY `when` ASC";
	final LocalDateTime dt_from = new LocalDateTime(de.toDateMidnight());
	final LocalDateTime dt_until = dt_from.plusDays(2);
	final PreparedResultSet p = db.select(sql, dt_from, dt_until);
	final WarehouseChangeLogEntry[] res = new WarehouseChangeLogEntry[p
		.getRowCount()];
	int i = 0;
	while (p.rs.next()) {
	    final int id = p.rs.getInt("id");
	    final int iditem = p.rs.getInt("iditem");
	    final int iduser = p.rs.getInt("iduser");
	    final char mode = p.rs.getString("mode").charAt(0);
	    final int idreason = p.rs.getInt("idreason");
	    final float quantityBefore = p.rs.getFloat("quantity_before");
	    final float quantityChange = p.rs.getFloat("quantity_change");
	    final LocalDateTime when = GNJoda.sqlLocalDateTime(
		    p.rs.getString("when"), false);
	    final String comments = p.rs.getString("comments");
	    res[i++] = new WarehouseChangeLogEntry(id, iditem, iduser, mode,
		    idreason, quantityBefore, quantityChange, when, comments);
	}
	return res;
    }

    private WarehouseChangeReasons getReasons() throws SQLException {
	final String sql = "SELECT `id`,`mode`,`description` "// select
		+ "FROM `warehouse_change_reason` ORDER BY `mode` ASC, `id` ASC";
	try (final PreparedResultSet p = db.select(sql)) {
	    final WarehouseChangeReason[] wcr = new WarehouseChangeReason[p
		    .getRowCount()];
	    for (int i = 0; i < wcr.length; i++) {
		p.rs.next();
		final int id = p.rs.getInt("id");
		final char mode = p.rs.getString("mode").charAt(0);
		final String description = p.rs.getString("description");
		wcr[i] = new WarehouseChangeReason(id, mode, description);
	    }
	    return new WarehouseChangeReasons(wcr);
	}
    }

    public void renameGroup(final int iduser, final int idgroup, String newName)
	    throws SQLException {
	if (newName.length() > 30) {
	    newName = newName.substring(0, 30);
	}
	final String sql = "UPDATE `warehouse_group` SET `name`=?, `log_created_user`=?, log_created_when=NOW() WHERE `id`=? LIMIT 1";
	db.update(sql, newName, iduser, idgroup);
    }

    public void updateGroupOrder(final int idgroup, final int order)
	    throws SQLException {
	final String select = "SELECT `id` " // select
		+ " FROM `warehouse_group` " // from
		+ " WHERE `idparent`=(" // where
		+ "   SELECT `idparent` " // sub-select
		+ "   FROM `warehouse_group` " // sub-from
		+ "   WHERE `id`=?" // sub-where
		+ " ) AND `id`<>? AND `deleted`='N'" // end of where
		+ " ORDER BY `order` ASC, `name` ASC";
	final String update = "UPDATE `warehouse_group` SET `order`=? WHERE `id`=? LIMIT 1";
	final PreparedResultSet p = db.select(select, idgroup, idgroup);
	int i = 0;
	while (p.rs.next()) {
	    final int id = p.rs.getInt("id");
	    if (order == i) {
		i++;
	    }
	    db.update(update, i, id);
	    i++;
	}
	db.update(update, (order == -1 ? i : order), idgroup);
    }

    public void updateGroupParent(final int idgroup, final int idparent)
	    throws SQLException {
	final String sql = "UPDATE `warehouse_group` SET `idparent`=? WHERE `id`=? LIMIT 1";
	db.update(sql, idparent, idgroup);
    }

    public void updateItem(final int iduser, final WarehouseItem wi)
	    throws SQLException {
	final String sql = "UPDATE `warehouse_item` SET " // update
		+ " `idgroup`=?,`name`=?,`unit`=?,`minimum`=?,`maximum`=?,`log_created_user`=?,`log_created_when`=NOW() "// set
		+ " WHERE `id`=? LIMIT 1";
	db.update(sql, wi.idgroup, wi.name, wi.unit, wi.minimum, wi.maximum,
		iduser, wi.id);
    }
}
