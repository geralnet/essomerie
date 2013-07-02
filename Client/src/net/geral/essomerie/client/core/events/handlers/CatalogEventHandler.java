package net.geral.essomerie.client.core.events.handlers;

import java.util.ArrayList;

import javax.swing.event.EventListenerList;

import net.geral.essomerie.client.core.events.listeners.CatalogListener;
import net.geral.essomerie.shared.catalog.CatalogGroup;
import net.geral.essomerie.shared.catalog.CatalogItem;
import net.geral.essomerie.shared.catalog.CatalogPublication;
import net.geral.lib.edt.Edt;

import org.apache.log4j.Logger;

public class CatalogEventHandler extends EventListenerList {
  private static final Logger logger           = Logger
                                                   .getLogger(CatalogEventHandler.class);
  private static final long   serialVersionUID = 1L;

  public void addListener(final CatalogListener l) {
    add(CatalogListener.class, l);
  }

  public void fireCatalogPublicationReceived(final boolean latest,
      final int idpublication) {
    Edt.run(false, new Runnable() {
      @Override
      public void run() {
        logger.debug("Fired: fireCatalogPublicationReceived(" + latest + ","
            + idpublication + ")");
        for (final CatalogListener l : getListeners(CatalogListener.class)) {
          l.catalogPublicationReceived(latest, idpublication);
        }
      }
    });
  }

  public void fireCatalogPublished(final CatalogPublication publication) {
    Edt.run(false, new Runnable() {
      @Override
      public void run() {
        logger.debug("Fired: fireCatalogPublished(" + publication + ")");
        for (final CatalogListener l : getListeners(CatalogListener.class)) {
          l.catalogPublished(publication);
        }
      }
    });
  }

  public void fireCreateGroupSuccessful(final int idgroup) {
    Edt.run(false, new Runnable() {
      @Override
      public void run() {
        logger.debug("Fired: fireCreateGroupSuccessful(...)");
        for (final CatalogListener l : getListeners(CatalogListener.class)) {
          l.catalogCreateGroupSuccessful(idgroup);
        }
      }
    });
  }

  public void fireCreateItemSuccessful(final int iditem) {
    Edt.run(false, new Runnable() {
      @Override
      public void run() {
        logger.debug("Fired: fireCreateItemSuccessful(...)");
        for (final CatalogListener l : getListeners(CatalogListener.class)) {
          l.catalogCreateItemSuccessful(iditem);
        }
      }
    });
  }

  public void fireGroupAdded(final CatalogGroup group) {
    Edt.run(false, new Runnable() {
      @Override
      public void run() {
        logger.debug("Fired: fireGroupAdded(...)");
        for (final CatalogListener l : getListeners(CatalogListener.class)) {
          l.catalogGroupAdded(group);
        }
      }
    });
  }

  public void fireGroupSaved(final int oldId, final CatalogGroup group) {
    Edt.run(false, new Runnable() {
      @Override
      public void run() {
        logger.debug("Fired: fireGroupSaved(...)");
        for (final CatalogListener l : getListeners(CatalogListener.class)) {
          l.catalogGroupSaved(oldId, group);
        }
      }
    });
  }

  public void fireItemAdded(final CatalogItem item) {
    Edt.run(false, new Runnable() {
      @Override
      public void run() {
        logger.debug("Fired: fireItemAdded(...)");
        for (final CatalogListener l : getListeners(CatalogListener.class)) {
          l.catalogItemAdded(item);
        }
      }
    });
  }

  public void fireItemSaved(final int oldId, final CatalogItem item) {
    Edt.run(false, new Runnable() {
      @Override
      public void run() {
        logger.debug("Fired: fireItemSaved(...)");
        for (final CatalogListener l : getListeners(CatalogListener.class)) {
          l.catalogItemSaved(oldId, item);
        }
      }
    });
  }

  public void fireLatestPublishedIdChanged(final int oldId, final int newId) {
    Edt.run(false, new Runnable() {
      @Override
      public void run() {
        logger.debug("Fired: fireLatestPublishedIdChanged");
        for (final CatalogListener l : getListeners(CatalogListener.class)) {
          l.catalogLatestPublishedIdChanged(oldId, newId);
        }
      }
    });
  }

  public void firePublicationListReceived() {
    Edt.run(false, new Runnable() {
      @Override
      public void run() {
        logger.debug("Fired: firePublicationListReceived");
        for (final CatalogListener l : getListeners(CatalogListener.class)) {
          l.catalogPublicationListReceived();
        }
      }
    });
  }

  public void firePublishSuccessful(final int idpublication) {
    Edt.run(false, new Runnable() {
      @Override
      public void run() {
        logger.debug("Fired: firePublishSuccessful");
        for (final CatalogListener l : getListeners(CatalogListener.class)) {
          l.catalogPublishSuccessful(idpublication);
        }
      }
    });
  }

  public void fireRemovedGroups(final int idpublication,
      final ArrayList<Integer> ids) {
    Edt.run(false, new Runnable() {
      @Override
      public void run() {
        logger.debug("Fired: fireRemovedGroups");
        for (final CatalogListener l : getListeners(CatalogListener.class)) {
          l.catalogRemovedGroups(idpublication, new ArrayList<Integer>(ids));
        }
      }
    });
  }

  public void fireRemovedItems(final int idpublication,
      final ArrayList<Integer> ids) {
    Edt.run(false, new Runnable() {
      @Override
      public void run() {
        logger.debug("Fired: fireRemovedItems");
        for (final CatalogListener l : getListeners(CatalogListener.class)) {
          l.catalogRemovedItems(idpublication, new ArrayList<Integer>(ids));
        }
      }
    });
  }

  public void removeListener(final CatalogListener l) {
    remove(CatalogListener.class, l);
  }
}
