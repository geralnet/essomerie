package net.geral.essomerie.client.gui.organizer.catalog.panels.tree;

import java.awt.Component;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

import net.geral.essomerie.client.resources.IMG;

public class CatalogTreeCellRenderer extends DefaultTreeCellRenderer {
  private static final long serialVersionUID = 1L;
  private static final int  ICON_PIXELS      = 16;

  @Override
  public Component getTreeCellRendererComponent(final JTree tree,
      final Object value, final boolean selected, final boolean expanded,
      final boolean leaf, final int row, final boolean hasFocus) {
    super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf,
        row, hasFocus);

    if ((value instanceof CatalogGroupTreeNode)
        || (value instanceof CatalogRootTreeNode)) {
      setIcon(expanded ? IMG.ICON__CATALOG__FOLDER_OPEN.icon(ICON_PIXELS)
          : IMG.ICON__CATALOG__FOLDER_CLOSED.icon(ICON_PIXELS));
    } else if (value instanceof CatalogItemTreeNode) {
      setIcon(IMG.ICON__CATALOG__DOCUMENT.icon(ICON_PIXELS));
    } else if (value instanceof CatalogGroupDetailsTreeNode) {
      setIcon(IMG.ICON__CATALOG__FOLDER_DETAILS.icon(ICON_PIXELS));
    } else if (value instanceof CatalogItemDetailsTreeNode) {
      setIcon(IMG.ICON__CATALOG__DOCUMENT_DETAILS.icon(ICON_PIXELS));
    } else {
      setIcon(null);
    }

    return this;
  }
}
