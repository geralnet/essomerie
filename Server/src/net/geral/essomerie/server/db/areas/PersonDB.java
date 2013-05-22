package net.geral.essomerie.server.db.areas;

import java.sql.SQLException;
import java.util.ArrayList;

import net.geral.essomerie.server.db.Database;
import net.geral.essomerie.server.db.DatabaseArea;
import net.geral.essomerie.server.db.PreparedResultSet;
import net.geral.essomerie.shared.person.Address;
import net.geral.essomerie.shared.person.Addresses;
import net.geral.essomerie.shared.person.Person;
import net.geral.essomerie.shared.person.PersonData;
import net.geral.essomerie.shared.person.PersonDocument;
import net.geral.essomerie.shared.person.PersonDocuments;
import net.geral.essomerie.shared.person.PersonFullData;
import net.geral.essomerie.shared.person.PersonLogDetails;
import net.geral.essomerie.shared.person.PersonType;
import net.geral.essomerie.shared.person.Telephone;
import net.geral.essomerie.shared.person.Telephones;
import net.geral.jodatime.JodaTimeUtils;

import org.apache.log4j.Logger;
import org.joda.time.LocalDateTime;

//TODO worry about locks. If locking table, other user threads cannot use another table
//if not locking, other programs (if existing) could get inconsistent state.
// if synchronized, solves problem locally (other programs might fail)
// if unsynch, this program itself can get inconsistent data on other threads

public class PersonDB extends DatabaseArea {
    private static final Logger logger = Logger.getLogger(PersonDB.class);

    public PersonDB(final Database database) {
	super(database);
    }

    private void addNewAddresses(final int iduser, final int idperson,
	    final ArrayList<Address> news) throws SQLException {
	final String sql = "INSERT INTO `person_address` "// insert
		+ " (`idperson`,`postal_code`,`address`,`suburb`,`city`,"// fields
		+ "`state`,`country`,`comments`,`classification`,`updated_by`,`updated_when`) " // fields
		+ " VALUES (?,?,?,?,?,?,?,?,?,?,NOW()) ";// values
	for (final Address a : news) {
	    db.insert(sql, idperson, a.getPostalCode(), a.getAddress(),
		    a.getSuburb(), a.getCity(), a.getState(), a.getCountry(),
		    a.getComments(), a.getClassification(), iduser);
	}
    }

    private void addNewDocuments(final int iduser, final int idperson,
	    final ArrayList<PersonDocument> news) throws SQLException {
	final String sql = "INSERT INTO `person_document` "// insert
		+ " (`idperson`,`type`,`number`,`image`,`updated_by`,`updated_when`) " // fields
		+ " VALUES (?,?,?,?,?,NOW()) ";// values
	for (final PersonDocument d : news) {
	    byte[] img = d.getImageBytes();
	    if (img == null) {
		logger.warn("No image information for document to save.");
	    } else if (img.length == 0) {
		img = null;
	    }
	    db.insert(sql, idperson, d.getType(), d.getNumber(), img, iduser);
	}
    }

    private void addNewTelephones(final int iduser, final int idperson,
	    final ArrayList<Telephone> news) throws SQLException {
	final String sql = "INSERT INTO `person_telephone` "// insert
		+ " (`idperson`,`country`,`area`,`number`,`extension`,`type`,`updated_by`,`updated_when`) " // fields
		+ " VALUES (?,?,?,?,?,?,?,NOW()) ";// values
	for (final Telephone t : news) {
	    db.insert(sql, idperson, t.getCountry(), t.getArea(),
		    t.getNumber(), t.getExtension(), t.getType(), iduser);
	}
    }

    public PersonData change(final int iduser, final PersonData p)
	    throws SQLException {
	if (iduser <= 0) {
	    throw new IllegalArgumentException("iduser (" + iduser
		    + ") that is changing the person must be valid (positive).");
	}
	if (p == null) {
	    throw new IllegalArgumentException("person cannot be null.");
	}
	if (p.getId() <= 0) {
	    throw new IllegalArgumentException("person.id  (" + p.getId()
		    + ") must be valid (positive).");
	}
	if (p.isDeleted()) {
	    throw new IllegalArgumentException("person cannot be deleted.");
	}

	// was deleted?
	final String select = "SELECT COUNT(`pk`) FROM `person` WHERE `id`=? AND `replaced_to` IS NULL";

	final String insert = "INSERT INTO `person` "// insert
		+ " (`id`,`type`,`name`,`alias`,`comments`,`updated_when`,`updated_by`) "// fields
		+ " VALUES (?,?,?,?,?,NOW(),?)";// values

	final String update = "UPDATE `person` "// update
		+ " SET `replaced_to`=?, `replaced_when`=NOW(), `replaced_by`=? "// set
		+ " WHERE `id`=? AND `pk`!=? AND `replaced_to` IS NULL";// where

	final int n = db.selectFirstField_int(select, p.getId());
	if (n <= 0) {
	    throw new IllegalArgumentException(
		    "original person not found or deleted.");
	}

	final int pk = db.insertLastId(insert, p.getId(), p.getType(),
		p.getName(), p.getAlias(), p.getComments(), iduser);
	db.update(update, pk, iduser, p.getId(), pk);

	saveData(iduser, p.getId(), p);

	return get(p.getId());
    }

    public PersonData create(final int iduser, final PersonData p)
	    throws SQLException {
	if (iduser <= 0) {
	    throw new IllegalArgumentException(
		    "iduser that is creating the person must be valid (positive).");
	}
	if (p == null) {
	    throw new IllegalArgumentException("person cannot be null.");
	}
	if (p.getId() != 0) {
	    throw new IllegalArgumentException("person.id must be zero.");
	}

	final String sql = "INSERT INTO `person` "// insert
		+ " (`id`,`type`,`name`,`alias`,`comments`,`updated_when`,`updated_by`) "// fields
		+ " VALUES (?,?,?,?,?,NOW(),?) "// values
	;
	final int nextId = db
		.selectFirstField_int("SELECT MAX(`id`)+1 FROM `person`");
	db.insert(sql, nextId, p.getType(), p.getName(), p.getAlias(),
		p.getComments(), iduser);

	saveData(iduser, nextId, p);

	return get(nextId);
    }

    public boolean delete(final int iduser, final int idperson)
	    throws SQLException {
	if (idperson <= 0) {
	    throw new IllegalArgumentException("id must be valid (positive).");
	}

	final String sql = "UPDATE `person` SET `replaced_to`=0, `replaced_when`=NOW(), `replaced_by`=? " // update
													  // set
		+ " WHERE `replaced_to` IS NULL AND `id`=? ";// where
	final int n = db.updateCount(sql, iduser, idperson);

	return (n > 0);
    }

    public boolean delete(final int iduser, final Person p) throws SQLException {
	if (iduser <= 0) {
	    throw new IllegalArgumentException(
		    "iduser that is deleting the person must be valid (positive).");
	}
	if (p == null) {
	    throw new IllegalArgumentException(
		    "person to delete cannot be null.");
	}
	return delete(iduser, p.getId());
    }

    private void deleteNonExistingAddresses(final int iduser,
	    final int idperson, final ArrayList<Address> olds)
	    throws SQLException {
	final StringBuilder sql = new StringBuilder();
	sql.append("UPDATE `person_address` SET `deleted_by`=?, `deleted_when`=NOW() WHERE (TRUE ");
	for (final Address a : olds) {
	    sql.append(" AND `id`<>");
	    sql.append(a.getId());
	}
	sql.append(") AND (`idperson`=? AND `deleted_when` IS NULL) ");
	db.update(sql.toString(), iduser, idperson);
    }

    private void deleteNonExistingDocuments(final int iduser,
	    final int idperson, final ArrayList<PersonDocument> olds)
	    throws SQLException {
	final StringBuilder sql = new StringBuilder();
	sql.append("UPDATE `person_document` SET `deleted_by`=?, `deleted_when`=NOW() WHERE (TRUE ");
	for (final PersonDocument doc : olds) {
	    sql.append(" AND `id`<>");
	    sql.append(doc.getId());
	}
	sql.append(") AND (`idperson`=? AND `deleted_when` IS NULL) ");
	db.update(sql.toString(), iduser, idperson);
    }

    private void deleteNonExistingTelephones(final int iduser,
	    final int idperson, final ArrayList<Telephone> olds)
	    throws SQLException {
	final StringBuilder sql = new StringBuilder();
	sql.append("UPDATE `person_telephone` SET `deleted_by`=?, `deleted_when`=NOW() WHERE (TRUE ");
	for (final Telephone t : olds) {
	    sql.append(" AND `id`<>");
	    sql.append(t.getId());
	}
	sql.append(") AND (`idperson`=? AND `deleted_when` IS NULL) ");
	db.update(sql.toString(), iduser, idperson);
    }

    private Addresses fetchAddresses(final int idperson) throws SQLException {
	final String sql = "SELECT `id`,`postal_code`,`address`,`suburb`,`city`,`state`,`country`,`comments`,`classification` "// select
		+ " FROM `person_address` "// from
		+ " WHERE `idperson`=? AND `deleted_when` IS NULL"// where
	;
	final PreparedResultSet r = db.select(sql, idperson);
	final ArrayList<Address> addresses = new ArrayList<>();
	while (r.rs.next()) {
	    final int id = r.rs.getInt("id");
	    final String postalCode = r.rs.getString("postal_code");
	    final String address = r.rs.getString("address");
	    final String suburb = r.rs.getString("suburb");
	    final String city = r.rs.getString("city");
	    final String state = r.rs.getString("state");
	    final String country = r.rs.getString("country");
	    final String comments = r.rs.getString("comments");
	    final int classification = r.rs.getInt("classification");
	    final Address a = new Address(id, idperson, postalCode, address,
		    suburb, city, state, country, comments, classification);
	    addresses.add(a);
	}
	r.close();
	return new Addresses(idperson, addresses);
    }

    private PersonDocuments fetchDocuments(final int idperson,
	    final boolean withImages) throws SQLException {
	final String sql = "SELECT `id`,`type`,`number` "// select
		+ (withImages ? ",`image` " : "") // images?
		+ " FROM `person_document` "// from
		+ " WHERE `idperson`=? AND `deleted_when` IS NULL"// where
	;
	final PreparedResultSet r = db.select(sql, idperson);
	final ArrayList<PersonDocument> documents = new ArrayList<>();
	while (r.rs.next()) {
	    final int id = r.rs.getInt("id");
	    final String type = r.rs.getString("type");
	    final String number = r.rs.getString("number");
	    byte[] image = withImages ? r.rs.getBytes("image") : null;
	    if (withImages && (image == null)) {
		image = new byte[0];
	    }
	    final PersonDocument a = new PersonDocument(id, idperson, type,
		    number, image);
	    documents.add(a);
	}
	r.close();
	return new PersonDocuments(idperson, documents);
    }

    private PersonLogDetails fetchLogDetails(final int idperson)
	    throws SQLException {
	String sql;
	PreparedResultSet p;

	sql = "SELECT `updated_when`,`updated_by` FROM `person` WHERE `id`=? ORDER BY `updated_when` ASC LIMIT 1";
	p = db.select(sql, idperson);
	if (!p.rs.next()) {
	    p.close();
	    return null;
	}
	final LocalDateTime createdWhen = JodaTimeUtils.parseLocalDateTime(p.rs
		.getString("updated_when"));
	final int createdBy = p.rs.getInt("updated_by");
	p.close();

	sql = "SELECT `updated_when`,`updated_by` FROM `person` WHERE `id`=? ORDER BY `updated_when` DESC LIMIT 1";
	p = db.select(sql, idperson);
	if (!p.rs.next()) {
	    p.close();
	    return null;
	}
	final LocalDateTime updatedWhen = JodaTimeUtils.parseLocalDateTime(p.rs
		.getString("updated_when"));
	final int updatedBy = p.rs.getInt("updated_by");
	p.close();

	return new PersonLogDetails(idperson, createdWhen, updatedWhen,
		createdBy, updatedBy);
    }

    private Telephones fetchTelephones(final int idperson) throws SQLException {
	final String sql = "SELECT `id`,`country`,`area`,`number`,`extension`,`type` "// select
		+ " FROM `person_telephone` "
		+ " WHERE `idperson`=? AND `deleted_when` IS NULL";
	final PreparedResultSet r = db.select(sql, idperson);
	final ArrayList<Telephone> telephones = new ArrayList<>();
	while (r.rs.next()) {
	    final int id = r.rs.getInt("id");
	    final String country = r.rs.getString("country");
	    final String area = r.rs.getString("area");
	    final String number = r.rs.getString("number");
	    final String extension = r.rs.getString("extension");
	    final String type = r.rs.getString("type");
	    final Telephone t = new Telephone(id, idperson, country, area,
		    number, extension, type);
	    telephones.add(t);
	}
	r.close();
	return new Telephones(idperson, telephones);
    }

    public PersonData get(final int idperson) throws SQLException {
	if (idperson <= 0) {
	    throw new IllegalArgumentException("id must be valid (positive).");
	}

	final String sql = "SELECT * " // select
		+ " FROM `person` "// from
		+ " WHERE `id`=? AND "// where
		+ " (`replaced_to` IS NULL OR `replaced_to`=0) " // deleted
	;

	final PreparedResultSet r = db.select(sql, idperson);
	PersonData p = null;
	if (r.rs.next()) {
	    final int id = r.rs.getInt("id");
	    final PersonType type = PersonType.fromString(r.rs
		    .getString("type"));
	    final String name = r.rs.getString("name");
	    final String alias = r.rs.getString("alias");
	    final boolean deleted = ((r.rs.getInt("replaced_to") == 0) && (!r.rs
		    .wasNull()));
	    final String comments = r.rs.getString("comments");
	    final PersonLogDetails log = fetchLogDetails(id);
	    final Telephones telephones = fetchTelephones(id);
	    final Addresses addresses = fetchAddresses(id);
	    final PersonDocuments documents = fetchDocuments(id, true);
	    p = new PersonData(id, type, name, alias, deleted, comments, log,
		    telephones, addresses, documents);
	}
	if (r.rs.next()) {
	    logger.warn("get(" + idperson + ") found more than one result");
	}
	r.close();
	return p;
    }

    public Person[] getAll(final boolean withDeleted) throws SQLException {
	final String sql = "SELECT `id`,`type`,`name`,`alias`,`replaced_to` "// select
		+ " FROM `person` "// from
		+ " WHERE " // where
		+ " `replaced_to` IS NULL " // only rows that where not replaced
		+ (withDeleted ? " OR `replaced_to`=0 " : "") // or deleted
	;
	final PreparedResultSet r = db.select(sql);
	final Person[] person = new Person[r.getRowCount()];
	int i = 0;
	while (r.rs.next()) {
	    final int id = r.rs.getInt("id");
	    final PersonType type = PersonType.fromString(r.rs
		    .getString("type"));
	    final String name = r.rs.getString("name");
	    final String alias = r.rs.getString("alias");
	    final boolean deleted = ((r.rs.getInt("replaced_to") == 0) && (!r.rs
		    .wasNull()));
	    person[i++] = new Person(id, type, name, alias, deleted);
	}
	r.close();
	return person;
    }

    private Address[] getAllAddresses() throws SQLException {
	final String sql = "SELECT `id`,`idperson`,`postal_code`,`address`,`suburb`,`city`,`state`,`country`,`comments`,`classification` "// select
		+ " FROM `person_address` "// from
		+ " WHERE `deleted_when` IS NULL"// where
	;
	final PreparedResultSet r = db.select(sql);
	final Address[] adds = new Address[r.getRowCount()];
	int i = 0;
	while (r.rs.next()) {
	    final int id = r.rs.getInt("id");
	    final int idperson = r.rs.getInt("idperson");
	    final String postalCode = r.rs.getString("postal_code");
	    final String address = r.rs.getString("address");
	    final String suburb = r.rs.getString("suburb");
	    final String city = r.rs.getString("city");
	    final String state = r.rs.getString("state");
	    final String country = r.rs.getString("country");
	    final String comments = r.rs.getString("comments");
	    final int classification = r.rs.getInt("classification");
	    final Address a = new Address(id, idperson, postalCode, address,
		    suburb, city, state, country, comments, classification);
	    adds[i++] = a;
	}
	return adds;
    }

    private PersonDocument[] getAllDocuments(final boolean withImages)
	    throws SQLException {
	final String sql = "SELECT `id`,`idperson`,`type`,`number` "// select
		+ (withImages ? ",`image` " : "") // images?
		+ " FROM `person_document` "// from
		+ " WHERE `deleted_when` IS NULL";// where
	final PreparedResultSet r = db.select(sql);
	final PersonDocument[] docs = new PersonDocument[r.getRowCount()];
	int i = 0;
	while (r.rs.next()) {
	    final int id = r.rs.getInt("id");
	    final int idperson = r.rs.getInt("idperson");
	    final String type = r.rs.getString("type");
	    final String number = r.rs.getString("number");
	    byte[] image = withImages ? r.rs.getBytes("image") : null;
	    if (withImages && (image == null)) {
		image = new byte[0];
	    }
	    docs[i++] = new PersonDocument(id, idperson, type, number, image);
	}
	return docs;
    }

    private Telephone[] getAllTelephones() throws SQLException {
	final String sql = "SELECT `id`,`idperson`,`country`,`area`,`number`,`extension`,`type` "// select
		+ " FROM `person_telephone` "// from
		+ " WHERE `deleted_when` IS NULL";// where
	final PreparedResultSet r = db.select(sql);
	final Telephone[] tels = new Telephone[r.getRowCount()];
	int i = 0;
	while (r.rs.next()) {
	    final int id = r.rs.getInt("id");
	    final int idperson = r.rs.getInt("idperson");
	    final String country = r.rs.getString("country");
	    final String area = r.rs.getString("area");
	    final String number = r.rs.getString("number");
	    final String extension = r.rs.getString("extension");
	    final String type = r.rs.getString("type");
	    tels[i++] = new Telephone(id, idperson, country, area, number,
		    extension, type);
	}
	return tels;
    }

    public PersonData[] getAllWithData(final boolean withDeleted)
	    throws SQLException {
	final String sql = "SELECT `id`,`type`,`name`,`alias`,`comments`,`replaced_to` "// select
		+ " FROM `person` "// from
		+ " WHERE " // where
		+ " `replaced_to` IS NULL " // only rows that where not replaced
		+ (withDeleted ? " OR `replaced_to`=0 " : "") // or deleted
	;
	final PreparedResultSet r = db.select(sql);
	final PersonData[] person = new PersonData[r.getRowCount()];
	int i = 0;
	while (r.rs.next()) {
	    final int id = r.rs.getInt("id");
	    final PersonType type = PersonType.fromString(r.rs
		    .getString("type"));
	    final String name = r.rs.getString("name");
	    final String alias = r.rs.getString("alias");
	    final boolean deleted = ((r.rs.getInt("replaced_to") == 0) && (!r.rs
		    .wasNull()));
	    final String comments = r.rs.getString("comments");
	    final PersonLogDetails log = fetchLogDetails(id);
	    person[i++] = new PersonData(id, type, name, alias, deleted,
		    comments, log, null, null, null);
	}
	r.close();
	return person;
    }

    public Person getForTelephone(final Telephone t) throws SQLException {
	// TODO replace 'letters' for 'numbers' for search (maybe column in DB
	// without letters)
	final String sql = "SELECT `idperson` FROM `person_telephone` WHERE `number`=? AND `deleted_when` IS NULL";
	final PreparedResultSet p = db.select(sql, t.getNumber());
	final ArrayList<Person> candidates = new ArrayList<>();
	while (p.rs.next()) {
	    candidates.add(get(p.rs.getInt("idperson")));
	}
	p.close();
	// TODO ? try reduce candidades by area code
	return candidates.size() == 1 ? candidates.get(0) : null;
    }

    public PersonFullData getFullData(final boolean withDeletedPersons,
	    final boolean withImages) throws SQLException {
	final PersonData[] persons = getAllWithData(withDeletedPersons);
	final Telephone[] telephones = getAllTelephones();
	final Address[] addresses = getAllAddresses();
	final PersonDocument[] documents = getAllDocuments(false);
	return new PersonFullData(persons, telephones, addresses, documents);
    }

    public PersonData save(final int userid, final PersonData person)
	    throws SQLException {
	if (person.getId() == 0) {
	    return create(userid, person);
	}
	return change(userid, person);
    }

    private void saveAddresses(final int iduser, final int idperson,
	    final Addresses addresses) throws SQLException {
	final ArrayList<Address> olds = new ArrayList<>();
	final ArrayList<Address> news = new ArrayList<>();
	for (final Address a : addresses.getAll()) {
	    ((a.getId() == 0) ? news : olds).add(a);
	}
	deleteNonExistingAddresses(iduser, idperson, olds);
	addNewAddresses(iduser, idperson, news);
    }

    private void saveData(final int iduser, final int idperson,
	    final PersonData data) throws SQLException {
	saveTelephones(iduser, idperson, data.getTelephones());
	saveAddresses(iduser, idperson, data.getAddresses());
	saveDocuments(iduser, idperson, data.getDocuments());
    }

    private void saveDocuments(final int iduser, final int idperson,
	    final PersonDocuments documents) throws SQLException {
	final ArrayList<PersonDocument> olds = new ArrayList<>();
	final ArrayList<PersonDocument> news = new ArrayList<>();
	for (final PersonDocument d : documents.getAll()) {
	    ((d.getId() == 0) ? news : olds).add(d);
	}
	deleteNonExistingDocuments(iduser, idperson, olds);
	addNewDocuments(iduser, idperson, news);
    }

    private void saveTelephones(final int iduser, final int idperson,
	    final Telephones telephones) throws SQLException {
	final ArrayList<Telephone> olds = new ArrayList<>();
	final ArrayList<Telephone> news = new ArrayList<>();
	for (final Telephone t : telephones.getAll()) {
	    ((t.getId() == 0) ? news : olds).add(t);
	}
	deleteNonExistingTelephones(iduser, idperson, olds);
	addNewTelephones(iduser, idperson, news);

    }
}
