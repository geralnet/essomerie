package net.geral.essomerie.client.gui.inventory.items;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.geral.essomerie._shared.contagem.InventoryGroup;
import net.geral.essomerie.client.gui.inventory.InventoryManagementTabPanel;
import net.geral.essomerie.client.resources.S;

public class InventoryItemsPanel extends JPanel {
  private static final long                 serialVersionUID = 1L;

  private final InventoryItemGroupPanel     panelGroup;
  private final InventoryItemGroupPanel     panelSubgroup;
  private final InventoryManagementTabPanel inventoryManagement;

  public InventoryItemsPanel(final InventoryManagementTabPanel parent) {
    inventoryManagement = parent;
    setLayout(new BorderLayout(0, 0));
    final JPanel panelOptions = new JPanel();
    add(panelOptions);
    panelOptions.setLayout(new BorderLayout(0, 0));

    final JPanel panelGroups = new JPanel();
    panelGroups.setBorder(new EmptyBorder(2, 2, 2, 2));
    panelOptions.add(panelGroups, BorderLayout.NORTH);
    panelGroups.setLayout(new GridLayout(0, 1, 0, 2));

    panelGroup = new InventoryItemGroupPanel(S.INVENTORY_GROUP.s(), this);
    panelGroups.add(panelGroup);

    panelSubgroup = new InventoryItemGroupPanel(S.INVENTORY_SUBGROUP.s(), this);
    panelGroups.add(panelSubgroup);

    final JPanel panelButtons = new JPanel();
    panelOptions.add(panelButtons, BorderLayout.SOUTH);

    final JButton btnPrintAdjustments = new JButton("Imprimir Acertos");
    btnPrintAdjustments.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(final ActionEvent e) {
        inventoryManagement.printLog();
      }
    });
    panelButtons.add(btnPrintAdjustments);
  }

  public InventoryGroup getSelectedGroup() {
    final InventoryGroup g = (InventoryGroup) panelGroup.getSelector()
        .getSelectedItem();
    if (g == null) {
      return null;
    }

    if (g.getSubGrupos().length == 0) {
      return g;
    }
    return (InventoryGroup) panelSubgroup.getSelector().getSelectedItem();
  }

  public void groupChanged(final InventoryItemGroupPanel src) {
    if (src == panelGroup) {
      final InventoryGroup g = (InventoryGroup) panelGroup.getSelector()
          .getSelectedItem();

      InventoryGroup[] subs = new InventoryGroup[0];
      if (g != null) {
        subs = g.getSubGrupos();
      }

      panelSubgroup.setGroups(subs);
      panelSubgroup.setVisible(subs.length > 0);
    }
    inventoryManagement.groupChanged();
  }

  public void updateGroups() {
    final int selectedGroup = panelGroup.getSelectedGroupId();
    final int selectedSubgroup = panelSubgroup.getSelectedGroupId();

    panelGroup.setGroups(inventoryManagement.getInventory().getGrupos());
    panelGroup.setSelectedGroupId(selectedGroup);

    final InventoryGroup selected = (InventoryGroup) panelGroup.getSelector()
        .getSelectedItem();
    if (selected == null) {
      panelSubgroup.setGroups(new InventoryGroup[0]);
      panelSubgroup.setVisible(false);
    } else {
      final InventoryGroup[] subs = selected.getSubGrupos();
      panelSubgroup.setGroups(subs);
      panelSubgroup.setSelectedGroupId(selectedSubgroup);
      panelSubgroup.setVisible(subs.length > 0);
    }
  }
}
