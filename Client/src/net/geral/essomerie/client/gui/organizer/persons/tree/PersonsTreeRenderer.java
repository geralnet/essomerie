package net.geral.essomerie.client.gui.organizer.persons.tree;

import java.awt.Component;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import net.geral.essomerie.client.resources.IMG;
import net.geral.essomerie.client.resources.S;

public class PersonsTreeRenderer extends DefaultTreeCellRenderer {
  private static final long serialVersionUID = 1L;
  public static final int   ICON_SIZE        = 16;

  @Override
  public Component getTreeCellRendererComponent(final JTree tree,
      final Object value, final boolean sel, final boolean expanded,
      final boolean leaf, final int row, final boolean hasFocus) {

    super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row,
        hasFocus);

    setIcon(IMG.ICON__PERSONS__TYPE_UNKNOWN.icon(ICON_SIZE));
    if (value instanceof PersonsTreeNode) {
      setIcon(((PersonsTreeNode) value).getIcon());
    } else if (value instanceof DefaultMutableTreeNode) {
      final Object o = ((DefaultMutableTreeNode) value).getUserObject();
      if (o instanceof S) {
        switch ((S) o) {
          case ORGANIZER_PERSONS_NATURAL:
            setIcon(IMG.ICON__PERSONS__TYPE_NATURAL.icon(ICON_SIZE));
            break;
          case ORGANIZER_PERSONS_LEGAL:
            setIcon(IMG.ICON__PERSONS__TYPE_LEGAL.icon(ICON_SIZE));
            break;
          default:
            break;
        }
      }
    }

    return this;
  }
}
