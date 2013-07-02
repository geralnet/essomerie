package net.geral.essomerie.client.gui.organizer.catalog.panels.tree;

import java.util.EventListener;

import net.geral.essomerie.shared.catalog.CatalogGroup;
import net.geral.essomerie.shared.catalog.CatalogItem;

public interface CatalogTreePanelListener extends EventListener {
  void catalogTreePanelGroupItemChanged(boolean rootSelected,
      CatalogGroup group, CatalogItem item);

  void catalogTreePanelPublicationChanged(int idpublication);
}
