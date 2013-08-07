package net.geral.essomerie.server.db.areas;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import net.geral.essomerie.server.Server;
import net.geral.essomerie.server.db.Database;
import net.geral.essomerie.server.db.DatabaseArea;
import net.geral.essomerie.server.db.PreparedResultSet;
import net.geral.essomerie.shared.system.Device;
import net.geral.essomerie.shared.system.DeviceInterface;
import net.geral.essomerie.shared.system.DeviceInterfaceHostname;
import net.geral.essomerie.shared.system.DeviceInterfaceType;
import net.geral.essomerie.shared.system.DevicesInfo;
import net.geral.lib.networking.IPv4;
import net.geral.lib.networking.MAC;

public class SysopDB extends DatabaseArea {
    private String qdb; // quoted system database name

    public SysopDB(final Database database) {
	super(database);
	updateSystemDatabaseName();
    }

    private ArrayList<Device> getAllDevices() throws SQLException {
	final String sql = "SELECT * FROM " + qdb + ".`device` ";
	try (PreparedResultSet p = db.select(sql)) {
	    final ArrayList<Device> devices = new ArrayList<>();
	    final ResultSet r = p.rs;
	    while (r.next()) {
		final int id = r.getInt("id");
		final String type = r.getString("type");
		final String name = r.getString("name");
		final String role = r.getString("role");
		final String os = r.getString("os");
		final String version = r.getString("version");
		final String cpu = r.getString("cpu");
		final String memory = r.getString("memory");
		final String comments = r.getString("comments");
		final String instructions = r.getString("instructions");
		final String configuration = r.getString("configuration");
		final Device d = new Device(id, type, name, role, os, version,
			cpu, memory, comments, instructions, configuration);
		devices.add(d);
	    }
	    return devices;
	}
    }

    private ArrayList<DeviceInterfaceHostname> getAllHostnames()
	    throws SQLException {
	final String sql = "SELECT * FROM " + qdb
		+ ".`device_interface_hostname` ";
	try (final PreparedResultSet p = db.select(sql)) {
	    final ArrayList<DeviceInterfaceHostname> hostnames = new ArrayList<>();
	    final ResultSet r = p.rs;
	    while (r.next()) {
		final int id = r.getInt("id");
		final int idinterface = r.getInt("idinterface");
		final String hostname = r.getString("hostname");
		final int order = r.getInt("order");
		final DeviceInterfaceHostname host = new DeviceInterfaceHostname(
			id, idinterface, hostname, order);
		hostnames.add(host);
	    }
	    return hostnames;
	}
    }

    private ArrayList<DeviceInterface> getAllInterfaces() throws SQLException {
	final String sql = "SELECT * FROM " + qdb + ".`device_interface` ";
	try (final PreparedResultSet p = db.select(sql)) {
	    final ArrayList<DeviceInterface> interfaces = new ArrayList<>();
	    final ResultSet r = p.rs;
	    while (r.next()) {
		final int id = r.getInt("id");
		final int iddevice = r.getInt("iddevice");
		final String name = r.getString("name");
		final MAC mac = MAC.fromSQL(r.getString("mac"));
		final DeviceInterfaceType type = DeviceInterfaceType.fromSQL(r
			.getString("type"));
		final IPv4 ipv4 = IPv4.toSQL(r.getString("ipv4"));
		final DeviceInterface iface = new DeviceInterface(id, iddevice,
			name, mac, type, ipv4);
		interfaces.add(iface);
	    }
	    return interfaces;
	}
    }

    public DevicesInfo getDevicesInfo() throws SQLException {
	final ArrayList<Device> devices = getAllDevices();
	final ArrayList<DeviceInterface> interfaces = getAllInterfaces();
	final ArrayList<DeviceInterfaceHostname> hostnames = getAllHostnames();
	return new DevicesInfo(devices, interfaces, hostnames);
    }

    public void updateSystemDatabaseName() {
	qdb = " `" + Server.config().MysqlSystemDatabase + "` ";
    }
}
