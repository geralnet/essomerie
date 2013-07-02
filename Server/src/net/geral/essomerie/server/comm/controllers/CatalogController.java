package net.geral.essomerie.server.comm.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import net.geral.essomerie._shared.communication.MessageData;
import net.geral.essomerie._shared.communication.MessageSubSystem;
import net.geral.essomerie._shared.communication.types.CatalogMessageType;
import net.geral.essomerie.server.Server;
import net.geral.essomerie.server.comm.Connection;
import net.geral.essomerie.server.comm.ServerConnectionController;
import net.geral.essomerie.shared.catalog.Catalog;
import net.geral.essomerie.shared.catalog.CatalogGroup;
import net.geral.essomerie.shared.catalog.CatalogItem;
import net.geral.essomerie.shared.catalog.CatalogPublication;

import org.apache.log4j.Logger;

public class CatalogController extends
	ServerConnectionController<CatalogMessageType> {
    private static final Logger logger = Logger
	    .getLogger(CatalogController.class);

    public CatalogController(final Connection c) {
	super(c, MessageSubSystem.Catalog);
    }

    @Override
    protected void process(final CatalogMessageType type, final MessageData md)
	    throws IOException, SQLException {
	switch (type) {
	case RequestLatestPublishId:
	    requestLatestPublishId();
	    break;
	case RequestCatalogPublication:
	    requestCatalogPublication(md.getInt());
	    break;
	case RequestPublicationList:
	    requestPublicationList();
	    break;
	case RequestPublish:
	    requestPublish(md.getString());
	    break;
	case RequestSaveGroup:
	    requestSaveGroup((CatalogGroup) md.get());
	    break;
	case RequestSaveItem:
	    requestSaveItem((CatalogItem) md.get());
	    break;
	case RequestRemoveGroup:
	    requestRemoveGroup(md.getInt(), md.getInt());
	    break;
	case RequestRemoveItem:
	    requestRemoveItem(md.getInt(), md.getInt());
	    break;
	case RequestCreateGroup:
	    requestCreateGroup(md.getInt(), md.getInt());
	    break;
	case RequestCreateItem:
	    requestCreateItem(md.getInt());
	    break;
	default:
	    logger.warn("Invalid type: " + type.name());
	    break;
	}
    }

    private void requestCatalogPublication(final int idpublication) {
	try {
	    final Catalog c = Server.db().catalog().getCatalog(idpublication);
	    send(CatalogMessageType.InformCatalogPublication, c);
	} catch (final Exception e) {
	    logger.warn(e, e);
	}
    }

    private void requestCreateGroup(final int idparent, final int idpublication) {
	try {
	    final CatalogGroup group = Server
		    .db()
		    .catalog()
		    .createGroup(connection.getUserId(), idparent,
			    idpublication);
	    Server.broadcast(MessageSubSystem.Catalog,
		    CatalogMessageType.InformCreatedGroup, group);
	    send(CatalogMessageType.InformCreateGroupSuccessful, group.getId());
	} catch (final Exception e) {
	    logger.warn(e, e);
	}
    }

    private void requestCreateItem(final int idgroup) {
	try {
	    final CatalogItem item = Server.db().catalog()
		    .createItem(connection.getUserId(), idgroup);
	    Server.broadcast(MessageSubSystem.Catalog,
		    CatalogMessageType.InformCreatedItem, item);
	    send(CatalogMessageType.InformCreateItemSuccessful, item.getId());
	} catch (final Exception e) {
	    logger.warn(e, e);
	}
    }

    private void requestLatestPublishId() throws IOException, SQLException {
	final int id = Server.db().catalog().getLatestPublishId();
	send(CatalogMessageType.InformLatestPublishId, id);
    }

    private void requestPublicationList() throws SQLException, IOException {
	final CatalogPublication[] pubs = Server.db().catalog()
		.getPublicationList();
	send(CatalogMessageType.InformPublicationList, (Object) pubs);
    }

    private void requestPublish(final String comments) throws IOException,
	    SQLException {
	final int idpublication = Server.db().catalog()
		.publish(connection.getUserId(), comments);
	final CatalogPublication publication = Server.db().catalog()
		.getPublication(idpublication);
	send(CatalogMessageType.InformPublishSuccessful, idpublication);
	Server.broadcast(MessageSubSystem.Catalog,
		CatalogMessageType.InformCatalogPublished, publication);
    }

    private void requestRemoveGroup(final int idpublication, final int idgroup)
	    throws SQLException, IOException {
	final ArrayList<Integer> removedGroups = new ArrayList<>();
	final ArrayList<Integer> removedItems = new ArrayList<>();
	Server.db()
		.catalog()
		.removeGroup(connection.getUserId(), idpublication, idgroup,
			removedGroups, removedItems);
	if (removedGroups.size() > 0) {
	    Server.broadcast(MessageSubSystem.Catalog,
		    CatalogMessageType.InformRemovedGroups, idpublication,
		    removedGroups);
	}
	if (removedItems.size() > 0) {
	    Server.broadcast(MessageSubSystem.Catalog,
		    CatalogMessageType.InformRemovedItems, idpublication,
		    removedItems);
	}
    }

    private void requestRemoveItem(final int idpublication, final int iditem)
	    throws SQLException, IOException {
	final boolean removed = Server.db().catalog()
		.removeItem(connection.getUserId(), idpublication, iditem);
	if (removed) {
	    final ArrayList<Integer> removedItems = new ArrayList<>(1);
	    removedItems.add(iditem);
	    Server.broadcast(MessageSubSystem.Catalog,
		    CatalogMessageType.InformRemovedItems, idpublication,
		    removedItems);
	}
    }

    private void requestSaveGroup(final CatalogGroup group) throws SQLException {
	final CatalogGroup newGroup = Server.db().catalog()
		.saveGroup(connection.getUserId(), group);
	Server.broadcast(MessageSubSystem.Catalog,
		CatalogMessageType.InformGroupSaved, group.getId(), newGroup);
    }

    private void requestSaveItem(final CatalogItem item) throws SQLException {
	final CatalogItem newItem = Server.db().catalog()
		.saveItem(connection.getUserId(), item);
	Server.broadcast(MessageSubSystem.Catalog,
		CatalogMessageType.InformItemSaved, item.getId(), newItem);
    }
}
