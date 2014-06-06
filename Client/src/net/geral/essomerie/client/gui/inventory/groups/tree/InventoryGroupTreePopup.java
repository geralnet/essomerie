package net.geral.essomerie.client.gui.inventory.groups.tree;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

import net.geral.essomerie._shared.contagem.InventoryGroup;
import net.geral.essomerie.client.core.Client;
import net.geral.essomerie.client.resources.S;

public class InventoryGroupTreePopup extends JPopupMenu implements
    MouseListener, ActionListener {
  private static final long        serialVersionUID = 1L;
  private final InventoryGroupTree tree;
  private InventoryGroup           selectedGroup    = null;
  private final JMenuItem          menuAdd;
  private final JMenuItem          menuRename;
  private final JMenuItem          menuRemove;

  public InventoryGroupTreePopup(final InventoryGroupTree tree) {
    this.tree = tree;
    menuAdd = createItem(S.INVENTORY_GROUP_POPUP_ADD);
    menuRename = createItem(S.INVENTORY_GROUP_POPUP_RENAME);
    menuRemove = createItem(S.INVENTORY_GROUP_POPUP_REMOVE);
  }

  private void actionAdd() {
    String name = JOptionPane.showInputDialog(Client.window(),
        S.INVENTORY_GROUP_POPUP_ADD_TEXT.s(),
        S.INVENTORY_GROUP_POPUP_ADD_TITLE.s(), JOptionPane.QUESTION_MESSAGE);
    if (name == null) {
      return;
    }
    name = name.trim();
    if (name.length() == 0) {
      return;
    }
    final int parent = (selectedGroup == null) ? 0 : selectedGroup.id;
    try {
      Client.connection().inventory().requestGroupAdd(parent, name);
    } catch (final IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void actionPerformed(final ActionEvent e) {
    final String cmd = e.getActionCommand();
    if (S.INVENTORY_GROUP_POPUP_ADD.name().equals(cmd)) {
      actionAdd();
    } else if (S.INVENTORY_GROUP_POPUP_RENAME.name().equals(cmd)) {
      actionRename();
    } else if (S.INVENTORY_GROUP_POPUP_REMOVE.name().equals(cmd)) {
      actionRemove();
    } else {
      System.err.println("Invalid action: " + cmd);
    }
  }

  private void actionRemove() {
    final int res = JOptionPane.showConfirmDialog(Client.window(),
        S.INVENTORY_GROUP_POPUP_REMOVE_CONFIRM.s(selectedGroup.nome),
        S.TITLE_CONFIRM.s(), JOptionPane.YES_NO_OPTION,
        JOptionPane.QUESTION_MESSAGE);
    if (res == JOptionPane.YES_OPTION) {
      try {
        Client.connection().inventory().requestGroupDelete(selectedGroup.id);
      } catch (final IOException e) {
        e.printStackTrace();
      }
    }
  }

  private void actionRename() {
    String name = (String) JOptionPane.showInputDialog(Client.window(),
        S.INVENTORY_GROUP_POPUP_RENAME_TEXT.s(selectedGroup.nome),
        S.TITLE_RENAME.s(), JOptionPane.QUESTION_MESSAGE, null, null,
        selectedGroup.nome);
    if (name == null) {
      return;
    }
    name = name.trim();
    if (name.length() == 0) {
      return;
    }
    if (name.equals(selectedGroup.nome)) {
      return;
    }
    try {
      Client.connection().inventory()
          .requestGroupRename(selectedGroup.id, name);
    } catch (final IOException e) {
      e.printStackTrace();
    }
  }

  private void checkPopup(final MouseEvent e) {
    if (!tree.getDragEnabled()) {
      return; // edit not allowed
    }
    if (e.isPopupTrigger()) {
      final int x = e.getX();
      final int y = e.getY();
      final int row = tree.getRowForLocation(x, y);
      tree.setSelectionRow(row);
      selectedGroup = tree.getSelectedGroup();
      // set enable/disable
      menuAdd.setEnabled(true);
      menuRemove.setEnabled(selectedGroup != null);
      menuRename.setEnabled(selectedGroup != null);
      // show
      show((JComponent) e.getSource(), x, y);
    }
  }

  private JMenuItem createItem(final S s) {
    final JMenuItem item = new JMenuItem(s.s());
    item.setActionCommand(s.name());
    item.addActionListener(this);
    add(item);
    return item;
  }

  @Override
  public void mouseClicked(final MouseEvent e) {
    checkPopup(e);
  }

  @Override
  public void mouseEntered(final MouseEvent e) {
  }

  @Override
  public void mouseExited(final MouseEvent e) {
  }

  @Override
  public void mousePressed(final MouseEvent e) {
  }

  @Override
  public void mouseReleased(final MouseEvent e) {
    checkPopup(e);
  }
}
