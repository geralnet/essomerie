package net.geral.essomerie.client.gui.organizer.catalog;

import java.awt.BorderLayout;

import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;

import net.geral.essomerie.client.gui.main.TabPanel;
import net.geral.essomerie.client.gui.organizer.catalog.panels.editor.CatalogEditorPanel;
import net.geral.essomerie.client.gui.organizer.catalog.panels.tree.CatalogTreePanel;
import net.geral.essomerie.client.gui.organizer.catalog.panels.tree.CatalogTreePanelListener;
import net.geral.essomerie.client.resources.S;
import net.geral.essomerie.shared.catalog.CatalogGroup;
import net.geral.essomerie.shared.catalog.CatalogItem;

public class CatalogTabPanel extends TabPanel implements
    CatalogTreePanelListener {
  private static final long        serialVersionUID = 1L;
  private final CatalogTreePanel   catalogTreePanel;
  private final CatalogEditorPanel catalogEditorPanel;
  private final JSplitPane         splitPane;

  public CatalogTabPanel() {
    setLayout(new BorderLayout(0, 0));

    splitPane = new JSplitPane();
    splitPane.setResizeWeight(0.6);
    add(splitPane, BorderLayout.CENTER);

    catalogTreePanel = new CatalogTreePanel();
    splitPane.setLeftComponent(catalogTreePanel);

    catalogEditorPanel = new CatalogEditorPanel();
    splitPane.setRightComponent(catalogEditorPanel);

    catalogTreePanel.addCatalogTreePanelListener(this);
  }

  @Override
  public void catalogTreePanelGroupItemChanged(final boolean rootSelected,
      final CatalogGroup group, final CatalogItem item) {
    if (rootSelected) {
      catalogEditorPanel.rootSelected();
    } else if (group != null) {
      catalogEditorPanel.setGroup(group);
    } else if (item != null) {
      catalogEditorPanel.setItem(item);
    } else {
      catalogEditorPanel.clear();
    }
  }

  @Override
  public void catalogTreePanelPublicationChanged(final int idpublication) {
    catalogEditorPanel.setCurrentIdPublication(idpublication);
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        final boolean editable = (idpublication == 0);
        if (editable) {
          splitPane.setDividerLocation(0.5);
          splitPane.setEnabled(true);
        } else {
          splitPane.setDividerLocation(1.0);
          splitPane.setEnabled(false);
        }
      }
    });
  }

  @Override
  public String getTabText() {
    return S.ORGANIZER_CATALOG.s();
  }

  @Override
  public void tabClosed() {
    catalogTreePanel.unregisterListeners();
  }

  @Override
  public boolean tabCloseRequest() {
    return true;
  }

  @Override
  public void tabCreated() {
    catalogTreePanel.registerListeners();
  }
}
