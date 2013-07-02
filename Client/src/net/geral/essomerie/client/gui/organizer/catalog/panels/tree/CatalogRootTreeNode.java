package net.geral.essomerie.client.gui.organizer.catalog.panels.tree;

import javax.swing.tree.DefaultMutableTreeNode;

import net.geral.essomerie.client.resources.S;

public class CatalogRootTreeNode extends DefaultMutableTreeNode {
  private static final long serialVersionUID = 1L;

  public CatalogRootTreeNode() {
    super(S.ORGANIZER_CATALOG_LOADING.s());
  }
}
