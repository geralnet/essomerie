package net.geral.essomerie.server.comm.controllers;

import java.io.IOException;
import java.sql.SQLException;

import net.geral.essomerie._shared.communication.ConnectionController;
import net.geral.essomerie._shared.communication.MessageData;
import net.geral.essomerie._shared.communication.MessageSubSystem;
import net.geral.essomerie._shared.communication.types.BulletinBoardMessageType;
import net.geral.essomerie.server.Server;
import net.geral.essomerie.server.comm.Connection;
import net.geral.essomerie.shared.bulletinboard.BulletinBoardEntry;
import net.geral.essomerie.shared.bulletinboard.BulletinBoardTitle;

import org.apache.log4j.Logger;

public class BulletinBoardController extends
	ConnectionController<BulletinBoardMessageType> {
    private static final Logger logger = Logger
	    .getLogger(BulletinBoardController.class);
    private final Connection connection;

    public BulletinBoardController(final Connection c) {
	super(c, null, MessageSubSystem.BulletinBoard);
	connection = c;
    }

    @Override
    protected void process(final BulletinBoardMessageType type,
	    final MessageData md) throws IOException, SQLException {

	switch (type) {
	case RequestTitleList:
	    requestTitleList();
	    break;
	case RequestFullContents:
	    requestFullContents(md.getInt());
	    break;
	case RequestSave:
	    requestSave((BulletinBoardEntry) md.get());
	    break;
	case RequestDelete:
	    requestDelete(md.getInt());
	    break;
	default:
	    logger.warn("Invalid type: " + type.name());
	}
    }

    private void requestDelete(final int bbeid) throws SQLException,
	    IOException {
	Server.db().bulletinBoard().delete(connection.getUserId(), bbeid);
	Server.broadcast(MessageSubSystem.BulletinBoard,
		BulletinBoardMessageType.InformDeleted, bbeid);
    }

    private void requestFullContents(final int bbeid) throws SQLException,
	    IOException {
	final BulletinBoardEntry bbe = Server.db().bulletinBoard().get(bbeid);
	send(BulletinBoardMessageType.InformFullContents, bbe);
    }

    private void requestSave(BulletinBoardEntry bbe) throws SQLException,
	    IOException {
	final int oldId = bbe.getId();
	bbe = Server.db().bulletinBoard().save(connection.getUserId(), bbe);
	// todos: notificar alteracao
	Server.broadcast(MessageSubSystem.BulletinBoard,
		BulletinBoardMessageType.InformChanged, oldId,
		bbe.getBulletinBoardTitle());
	// usuario: notificar que foi salva
	send(BulletinBoardMessageType.InformSaveSuccessful, oldId, bbe.getId());
    }

    private void requestTitleList() throws SQLException, IOException {
	final BulletinBoardTitle[] bbt = Server.db().bulletinBoard()
		.getTitles();
	send(BulletinBoardMessageType.InformTitleList, (Object) bbt);
    }
}
