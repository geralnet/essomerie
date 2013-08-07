package net.geral.essomerie.server.comm.controllers;

import java.io.IOException;
import java.sql.SQLException;

import net.geral.essomerie.server.Server;
import net.geral.essomerie.server.comm.Connection;
import net.geral.essomerie.shared.communication.ConnectionController;
import net.geral.essomerie.shared.communication.MessageData;
import net.geral.essomerie.shared.communication.MessageSubSystem;
import net.geral.essomerie.shared.communication.types.SysopMessageType;
import net.geral.essomerie.shared.system.DevicesInfo;

import org.apache.log4j.Logger;

public class SysopController extends ConnectionController<SysopMessageType> {
    private static final Logger logger = Logger
	    .getLogger(SysopController.class);
    private final Connection connection;

    public SysopController(final Connection c) {
	super(c, null, MessageSubSystem.Sysop);
	connection = c;
    }

    @Override
    protected void process(final SysopMessageType type, final MessageData md)
	    throws IOException, SQLException {
	switch (type) {
	case RequestDevicesInfo:
	    requestDevicesInfo();
	    break;
	default:
	    logger.warn("Invalid type: " + type.name());
	}
    }

    private void requestDevicesInfo() throws SQLException, IOException {
	final DevicesInfo info = Server.db().sysop().getDevicesInfo();
	send(SysopMessageType.InformDevicesInfo, info);
    }
}
