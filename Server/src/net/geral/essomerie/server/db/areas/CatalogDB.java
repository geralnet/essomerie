package net.geral.essomerie.server.db.areas;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import net.geral.essomerie.server.db.Database;
import net.geral.essomerie.server.db.DatabaseArea;
import net.geral.essomerie.server.db.PreparedResultSet;
import net.geral.essomerie.shared.catalog.Catalog;
import net.geral.essomerie.shared.catalog.CatalogGroup;
import net.geral.essomerie.shared.catalog.CatalogItem;
import net.geral.essomerie.shared.catalog.CatalogPublication;
import net.geral.essomerie.shared.money.Money;
import net.geral.lib.jodatime.GNJoda;

import org.joda.time.LocalDateTime;

public class CatalogDB extends DatabaseArea {
    public CatalogDB(final Database database) {
	super(database);
    }

    private void commitGroupIdChange(final int oldId, final int newId)
	    throws SQLException {
	db.update("UPDATE `catalog_group` SET `idparent`=? WHERE `idparent`=?",
		newId, oldId);
	db.update("UPDATE `catalog_item` SET `idgroup`=? WHERE `idgroup`=?",
		newId, oldId);
    }

    public CatalogGroup createGroup(final int iduser, final int idparent,
	    final int idpublication) throws SQLException {
	final String sqlOrder = "SELECT IFNULL(MAX(`order`),0)  "// select
		+ "FROM `catalog_group` "// from
		+ " WHERE `idparent`=? AND `idpublication`=? AND (`deleted_when` IS NULL)";
	final int order = db.selectFirstField_int(sqlOrder, idparent,
		idpublication);
	final String sql = "INSERT INTO `catalog_group` "// insert
		+ " (`idparent`,`order`,`created_by`,`created_when`,`idpublication`) "// fields
		+ " VALUES(?,?,?,NOW(),?)";
	final int idgroup = db.insertLastId(sql, idparent, order, iduser,
		idpublication);
	return getCatalogGroup(idgroup);
    }

    public CatalogItem createItem(final int iduser, final int idgroup)
	    throws SQLException {
	// get idpublication
	final String sqlCheck = "SELECT `idpublication` "// select
		+ " FROM `catalog_group`"// from
		+ " WHERE `id`=? AND (`deleted_when` IS NULL)";
	final int idpublication = db.selectFirstField_int(sqlCheck, idgroup);

	final String sqlOrder = "SELECT IFNULL(MAX(`order`),0)  "// select
		+ " FROM `catalog_item` "// from
		+ " WHERE `idgroup`=? AND `idpublication`=? AND (`deleted_when` IS NULL)";
	final int order = db.selectFirstField_int(sqlOrder, idgroup,
		idpublication);
	final String sql = "INSERT INTO `catalog_item` "// insert
		+ " (`idgroup`,`order`,`created_by`,`created_when`,`idpublication`) "// fields
		+ " VALUES(?,?,?,NOW(),?)";
	final int iditem = db.insertLastId(sql, idgroup, order, iduser,
		idpublication);
	return getCatalogItem(iditem);
    }

    private void deleteGroup(final int iduser, final int idgroup,
	    final boolean deleteChilds) throws SQLException {
	final String sql = "UPDATE `catalog_group` "// update
		+ " SET `deleted_when`=NOW(), `deleted_by`=? "// set
		+ " WHERE `id`=? LIMIT 1";// where
	db.update(sql, iduser, idgroup);
	if (deleteChilds) {
	    // remove subgroups recursively, since they can contain more
	    // subgroups
	    final String sqlSubgroups = "SELECT `id` FROM `catalog_group` WHERE `idparent`=?";
	    try (PreparedResultSet p = db.select(sqlSubgroups, idgroup)) {
		while (p.rs.next()) {
		    final int idsub = p.rs.getInt("id");
		    deleteGroup(iduser, idsub, deleteChilds);
		}
	    }
	    // remove items
	    final String sqlItems = "UPDATE `catalog_item` "// update
		    + " SET `deleted_when=NOW(), `deleted_by`=? "// set
		    + " WHERE `idgroup`=? LIMIT 1";// where
	    db.update(sqlItems, iduser, idgroup);
	}
    }

    private void deleteItem(final int iduser, final int iditem)
	    throws SQLException {
	final String sql = "UPDATE `catalog_item` "// update
		+ " SET `deleted_when`=NOW(), `deleted_by`=? "// set
		+ " WHERE `id`=? LIMIT 1";// where
	db.update(sql, iduser, iditem);
    }

    public Catalog getCatalog(final int idpublication) throws SQLException {
	if (idpublication == 0) {
	    final ArrayList<CatalogGroup> groups = getCatalogGroups(idpublication);
	    final ArrayList<CatalogItem> items = getCatalogItems(idpublication);
	    return Catalog.create(idpublication, null, 0, "", groups, items);
	} else {
	    final String sql = "SELECT `when`,`by`,`comments` FROM `catalog_publish` WHERE `id`=?";
	    try (PreparedResultSet p = db.select(sql, idpublication)) {
		if (p.rs.next()) {
		    final LocalDateTime when = GNJoda.sqlLocalDateTime(
			    p.rs.getString("when"), false);
		    final int by = p.rs.getInt("by");
		    final String comments = p.rs.getString("comments");
		    final ArrayList<CatalogGroup> groups = getCatalogGroups(idpublication);
		    final ArrayList<CatalogItem> items = getCatalogItems(idpublication);
		    return Catalog.create(idpublication, when, by, comments,
			    groups, items);
		}
	    }
	}
	return null;
    }

    private CatalogGroup getCatalogGroup(final int idgroup) throws SQLException {
	final String sql = "SELECT `idparent`,`order`,`idpublication`"// select
		+ " FROM `catalog_group`"// from
		+ " WHERE `deleted_when` IS NULL AND `id`=?"// where
	;
	try (final PreparedResultSet p = db.select(sql, idgroup)) {
	    if (p.rs.next()) {
		final int idparent = p.rs.getInt("idparent");
		final int order = p.rs.getInt("order");
		final int idpublication = p.rs.getInt("idpublication");
		final ArrayList<String> languages = new ArrayList<>();
		final ArrayList<String> titles = new ArrayList<>();
		final ArrayList<String> subtitles = new ArrayList<>();
		getCatalogGroupsTitles(idgroup, languages, titles, subtitles);
		return new CatalogGroup(idgroup, idparent, order,
			idpublication, languages, titles, subtitles);
	    }
	}
	return null;
    }

    private ArrayList<CatalogGroup> getCatalogGroups(final int idpublication)
	    throws SQLException {
	// TODO improve performance
	final String sql = "SELECT `id`,`idparent`,`order`"// select
		+ " FROM `catalog_group`"// from
		+ " WHERE `deleted_when` IS NULL AND `idpublication`=?"// where
	;
	final ArrayList<CatalogGroup> groups = new ArrayList<>();
	try (final PreparedResultSet p = db.select(sql, idpublication)) {
	    while (p.rs.next()) {
		final int id = p.rs.getInt("id");
		final int idparent = p.rs.getInt("idparent");
		final int order = p.rs.getInt("order");
		final ArrayList<String> languages = new ArrayList<>();
		final ArrayList<String> titles = new ArrayList<>();
		final ArrayList<String> subtitles = new ArrayList<>();
		getCatalogGroupsTitles(id, languages, titles, subtitles);
		groups.add(new CatalogGroup(id, idparent, order, idpublication,
			languages, titles, subtitles));
	    }
	}
	return groups;
    }

    private void getCatalogGroupsTitles(final int idgroup,
	    final ArrayList<String> languages, final ArrayList<String> titles,
	    final ArrayList<String> subtitles) throws SQLException {
	final String sql = "SELECT `language`,`title`,`subtitle` "// select
		+ " FROM `catalog_group_title` "// from
		+ " WHERE `idgroup`=?";
	final PreparedResultSet p = db.select(sql, idgroup);
	while (p.rs.next()) {
	    languages.add(p.rs.getString("language"));
	    titles.add(p.rs.getString("title"));
	    subtitles.add(p.rs.getString("subtitle"));
	}
	p.rs.close();
    }

    private CatalogItem getCatalogItem(final int iditem) throws SQLException {
	// TODO improve performance
	final String sql = "SELECT `idpublication`,`idgroup`,`order`"// select
		+ " FROM `catalog_item`"// from
		+ " WHERE `deleted_when` IS NULL AND `id`=?"// where
	;
	try (PreparedResultSet p = db.select(sql, iditem)) {
	    if (p.rs.next()) {
		final int idpublication = p.rs.getInt("idpublication");
		final int idgroup = p.rs.getInt("idgroup");
		final int order = p.rs.getInt("order");
		final ArrayList<String> languages = new ArrayList<>();
		final ArrayList<String> titles = new ArrayList<>();
		final ArrayList<String> descriptions = new ArrayList<>();
		final ArrayList<String> priceCodes = new ArrayList<>();
		final ArrayList<Money> priceValues = new ArrayList<>();
		getCatalogItemsTitles(iditem, languages, titles, descriptions);
		getCatalogItemsPrices(iditem, priceCodes, priceValues);
		return new CatalogItem(iditem, idgroup, order, idpublication,
			languages, titles, descriptions, priceCodes,
			priceValues);
	    }
	}
	return null;
    }

    private ArrayList<CatalogItem> getCatalogItems(final int idpublication)
	    throws SQLException {
	// TODO improve performance
	final String sql = "SELECT `id`,`idgroup`,`order`"// select
		+ " FROM `catalog_item`"// from
		+ " WHERE `deleted_when` IS NULL AND `idpublication`=?"// where
	;
	final ArrayList<CatalogItem> items = new ArrayList<>();
	try (PreparedResultSet p = db.select(sql, idpublication)) {
	    while (p.rs.next()) {
		final int id = p.rs.getInt("id");
		final int idgroup = p.rs.getInt("idgroup");
		final int order = p.rs.getInt("order");
		final ArrayList<String> languages = new ArrayList<>();
		final ArrayList<String> titles = new ArrayList<>();
		final ArrayList<String> descriptions = new ArrayList<>();
		final ArrayList<String> priceCodes = new ArrayList<>();
		final ArrayList<Money> priceValues = new ArrayList<>();
		getCatalogItemsTitles(id, languages, titles, descriptions);
		getCatalogItemsPrices(id, priceCodes, priceValues);
		items.add(new CatalogItem(id, idgroup, order, idpublication,
			languages, titles, descriptions, priceCodes,
			priceValues));
	    }
	}
	return items;
    }

    private void getCatalogItemsPrices(final int iditem,
	    final ArrayList<String> priceCodes,
	    final ArrayList<Money> priceValues) throws SQLException {
	final String sql = "SELECT `code`,`price` "// select
		+ " FROM `catalog_item_price` "// from
		+ " WHERE `iditem`=?";
	try (PreparedResultSet p = db.select(sql, iditem)) {
	    while (p.rs.next()) {
		priceCodes.add(p.rs.getString("code"));
		priceValues.add(Money.fromSQL(p.rs.getString("price")));
	    }
	}
    }

    private void getCatalogItemsTitles(final int iditem,
	    final ArrayList<String> languages, final ArrayList<String> titles,
	    final ArrayList<String> descriptions) throws SQLException {
	final String sql = "SELECT `language`,`title`,`description` "// select
		+ " FROM `catalog_item_title` "// from
		+ " WHERE `iditem`=?";
	try (PreparedResultSet p = db.select(sql, iditem)) {
	    while (p.rs.next()) {
		languages.add(p.rs.getString("language"));
		titles.add(p.rs.getString("title"));
		descriptions.add(p.rs.getString("description"));
	    }
	}
    }

    public int getLatestPublishId() throws SQLException {
	final String sql = "SELECT `id` FROM `catalog_publish` ORDER BY `when` DESC LIMIT 1";
	try (PreparedResultSet r = db.select(sql)) {
	    if (r.rs.next()) {
		return r.rs.getInt("id");
	    }
	}
	return 0;
    }

    public CatalogPublication getPublication(final int idpublication)
	    throws SQLException {
	final String sql = "SELECT * FROM `catalog_publish` WHERE `id`=?";
	try (PreparedResultSet r = db.select(sql, idpublication)) {
	    if (r.rs.next()) {
		final int id = r.rs.getInt("id");
		final LocalDateTime when = GNJoda.sqlLocalDateTime(
			r.rs.getString("when"), false);
		final int by = r.rs.getInt("by");
		final String comments = r.rs.getString("comments");
		return new CatalogPublication(id, when, by, comments);
	    }
	    return null;
	}
    }

    public CatalogPublication[] getPublicationList() throws SQLException {
	final String sql = "SELECT * FROM `catalog_publish`";
	try (PreparedResultSet r = db.select(sql)) {
	    final CatalogPublication[] cp = new CatalogPublication[r
		    .getRowCount() + 1];
	    int i = 0;
	    while (r.rs.next()) {
		final int id = r.rs.getInt("id");
		final LocalDateTime when = GNJoda.sqlLocalDateTime(
			r.rs.getString("when"), false);
		final int by = r.rs.getInt("by");
		final String comments = r.rs.getString("comments");
		cp[i++] = new CatalogPublication(id, when, by, comments);
	    }
	    return cp;
	}
    }

    public int publish(final int iduser, final String comments)
	    throws SQLException {
	final String sql = "INSERT INTO `catalog_publish` (`when`,`by`,`comments`) VALUES (NOW(), ?, ?)";
	final int idpublication = db.insertLastId(sql, iduser, comments);
	final HashMap<Integer, Integer> oldGroupToNew = new HashMap<>();
	oldGroupToNew.put(0, 0); // parent 0 is always 0
	publishGroups(idpublication, oldGroupToNew, 0);
	publishItems(idpublication, oldGroupToNew);
	return idpublication;
    }

    private void publishGroups(final int idpublication,
	    final HashMap<Integer, Integer> oldGroupToNew, final int oldParent)
	    throws SQLException {
	final int newParent = oldGroupToNew.get(oldParent);
	final String select = "SELECT `id` FROM `catalog_group` "// selectfrom
		+ " WHERE `idpublication`=0 AND `idparent`=? AND (`deleted_when` IS NULL)";
	final String insert = "INSERT INTO `catalog_group`"// insert
		+ " (`idpublication`,`idparent`,`order`,`created_by`,`created_when`) "// fields
		+ " (SELECT ?,?,`order`,`created_by`,`created_when`" // subselect
		+ " FROM `catalog_group` WHERE `id`=?)"; // subfromwhere
	final String titles = "INSERT INTO `catalog_group_title` "// insert
		+ "(`idgroup`,`language`,`title`,`subtitle`) "// fields
		+ " (SELECT ?,`language`,`title`,`subtitle` " // subselect
		+ " FROM `catalog_group_title` WHERE `idgroup`=?)";// subfromwhere
	try (PreparedResultSet p = db.select(select, oldParent)) {
	    while (p.rs.next()) {
		final int oldId = p.rs.getInt("id");
		final int newId = db.insertLastId(insert, idpublication,
			newParent, oldId);
		oldGroupToNew.put(oldId, newId);
		db.insert(titles, newId, oldId);
		// recursively go to subgroups
		publishGroups(idpublication, oldGroupToNew, oldId);
	    }
	}
    }

    private void publishItems(final int idpublication,
	    final HashMap<Integer, Integer> oldGroupToNew) throws SQLException {
	final String select = "SELECT `id`,`idgroup` FROM `catalog_item` "// selectfrom
		+ " WHERE `idpublication`=0 AND (`deleted_when` IS NULL)";
	final String insert = "INSERT INTO `catalog_item`"// insert
		+ " (`idpublication`,`idgroup`,`order`,`created_by`,`created_when`) "// fields
		+ " (SELECT ?,?,`order`,`created_by`,`created_when`" // subselect
		+ " FROM `catalog_item` WHERE `id`=?)"; // subfromwhere
	final String prices = "INSERT INTO `catalog_item_price` "// insert
		+ "(`iditem`,`code`,`price`) "// fields
		+ " (SELECT ?,`code`,`price` " // subselect
		+ " FROM `catalog_item_price` WHERE `iditem`=?)";// subfromwhere
	final String titles = "INSERT INTO `catalog_item_title` "// insert
		+ "(`iditem`,`language`,`title`,`description`) "// fields
		+ " (SELECT ?,`language`,`title`,`description` " // subselect
		+ " FROM `catalog_item_title` WHERE `iditem`=?)";// subfromwhere
	try (PreparedResultSet p = db.select(select)) {
	    while (p.rs.next()) {
		final int oldId = p.rs.getInt("id");
		final int oldGroup = p.rs.getInt("idgroup");
		final int newGroup = oldGroupToNew.get(oldGroup);
		final int newId = db.insertLastId(insert, idpublication,
			newGroup, oldId);
		db.insert(prices, newId, oldId);
		db.insert(titles, newId, oldId);
	    }
	}
    }

    public void removeGroup(final int iduser, final int idpublication,
	    final int idgroup, final ArrayList<Integer> removedGroups,
	    final ArrayList<Integer> removedItems) throws SQLException {
	// remove all items from this group
	final String getItems = "SELECT `id` FROM `catalog_item` "
		+ "WHERE (`deleted_when` IS NULL) AND `idpublication`=? AND `idgroup`=?";
	try (PreparedResultSet p = db.select(getItems, idpublication, idgroup)) {
	    while (p.rs.next()) {
		removedItems.add(p.rs.getInt("id"));
	    }
	}
	final String delItems = "UPDATE `catalog_item` "// update
		+ " SET `deleted_when`=NOW(), `deleted_by`=? "// set
		+ "WHERE (`deleted_when` IS NULL) AND `idpublication`=? AND `idgroup`=?";
	db.update(delItems, iduser, idpublication, idgroup);
	// recursively remove all subgroups from this group
	final String getSubs = "SELECT `id` FROM `catalog_group` "
		+ "WHERE (`deleted_when` IS NULL) AND `idparent`=? AND `idpublication`=?";
	try (PreparedResultSet p = db.select(getSubs, idgroup, idpublication)) {
	    while (p.rs.next()) {
		final int subgroup = p.rs.getInt("id");
		removeGroup(iduser, idpublication, subgroup, removedGroups,
			removedItems);
	    }
	}
	// remove group
	removedGroups.add(idgroup);
	final String delGroup = "UPDATE `catalog_group` "// update
		+ " SET `deleted_when`=NOW(), `deleted_by`=? "// set
		+ " WHERE (`deleted_when` IS NULL) AND `id`=? AND `idpublication`=?";
	db.update(delGroup, iduser, idgroup, idpublication);
    }

    public boolean removeItem(final int iduser, final int idpublication,
	    final int iditem) throws SQLException {
	final String sql = "UPDATE `catalog_item` "// update
		+ " SET `deleted_when`=NOW(), `deleted_by`=? "// set
		+ " WHERE `id`=? AND `idpublication`=? AND (`deleted_when` IS NULL)";// where
	return db.updateSuccess(sql, iduser, iditem, idpublication);
    }

    public CatalogGroup saveGroup(final int iduser, final CatalogGroup group)
	    throws SQLException {
	if (group.getId() > 0) {
	    deleteGroup(iduser, group.getId(), false);
	}
	final int idparent = db.selectFirstField_int(
		"SELECT `idparent` FROM `catalog_group` WHERE `id`=?",
		group.getId()); // ignore informed parent
	final String insertGroup = "INSERT INTO `catalog_group` "// insert
		+ " (`idparent`,`order`,`idpublication`,`created_by`,`created_when`) "// fields
		+ " VALUES (?,?,?,?,NOW())";// values
	final int newGroupId = db.insertLastId(insertGroup, idparent,
		group.getOrder(), group.getIdPublication(), iduser);

	final String insertTitle = "INSERT INTO `catalog_group_title` "// insert
		+ " (`idgroup`,`language`,`title`,`subtitle`) "// fields
		+ " VALUES (?,?,?,?)";
	for (final String language : group.getLanguages()) {
	    final String title = group.getTitle(language);
	    final String subtitle = group.getSubtitle(language);
	    db.insert(insertTitle, newGroupId, language, title, subtitle);
	}
	if (group.getId() > 0) {
	    commitGroupIdChange(group.getId(), newGroupId);
	}

	return getCatalogGroup(newGroupId);
    }

    public CatalogItem saveItem(final int iduser, final CatalogItem item)
	    throws SQLException {
	if (item.getId() > 0) {
	    deleteItem(iduser, item.getId());
	}
	final int idgroup = db.selectFirstField_int(
		"SELECT `idgroup` FROM `catalog_item` WHERE `id`=?",
		item.getId()); // ignore informed parent
	final String insertItem = "INSERT INTO `catalog_item` "// insert
		+ " (`idgroup`,`order`,`idpublication`,`created_by`,`created_when`) "// fields
		+ " VALUES (?,?,?,?,NOW())";// values
	final int newItemId = db.insertLastId(insertItem, idgroup,
		item.getOrder(), item.getIdPublication(), iduser);

	final String insertTitle = "INSERT INTO `catalog_item_title` "// insert
		+ " (`iditem`,`language`,`title`,`description`) "// fields
		+ " VALUES (?,?,?,?)";
	for (final String language : item.getLanguages()) {
	    final String title = item.getTitle(language);
	    final String description = item.getDescription(language);
	    db.insert(insertTitle, newItemId, language, title, description);
	}

	final String insertPrice = "INSERT INTO `catalog_item_price` "// insert
		+ " (`iditem`,`code`,`price`) "// fields
		+ " VALUES (?,?,?)";
	for (final String code : item.getPriceCodes()) {
	    final Money price = item.getPrice(code);
	    db.insert(insertPrice, newItemId, code, price);
	}

	return getCatalogItem(newItemId);
    }
}
