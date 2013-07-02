package net.geral.essomerie.client.gui.organizer.catalog.panels.tree;

import javax.swing.tree.DefaultMutableTreeNode;

import net.geral.essomerie.shared.catalog.CatalogItem;

public class CatalogItemTreeNode extends DefaultMutableTreeNode {
  private static final long serialVersionUID = 1L;

  public CatalogItemTreeNode(final CatalogItem i) {
    super(i);
  }

  public CatalogItem getItem() {
    return getUserObject();
  }

  @Override
  public CatalogItem getUserObject() {
    return (CatalogItem) super.getUserObject();
  }

  public void setItem(final CatalogItem item) {
    setUserObject(item);
  }

  @Override
  public String toString() {
    return getItem().getTitle();
  }
}
