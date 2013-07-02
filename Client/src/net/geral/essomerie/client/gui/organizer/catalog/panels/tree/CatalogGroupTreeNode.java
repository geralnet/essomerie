package net.geral.essomerie.client.gui.organizer.catalog.panels.tree;

import javax.swing.tree.DefaultMutableTreeNode;

import net.geral.essomerie.shared.catalog.CatalogGroup;

public class CatalogGroupTreeNode extends DefaultMutableTreeNode {
  private static final long serialVersionUID = 1L;

  public CatalogGroupTreeNode(final CatalogGroup g) {
    super(g);
  }

  public CatalogGroup getGroup() {
    return getUserObject();
  }

  @Override
  public CatalogGroup getUserObject() {
    return (CatalogGroup) super.getUserObject();
  }

  public void setGroup(final CatalogGroup group) {
    setUserObject(group);
  }

  @Override
  public String toString() {
    return getGroup().getTitle();
  }
}
