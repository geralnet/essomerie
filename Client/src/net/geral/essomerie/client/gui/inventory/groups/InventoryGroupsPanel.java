package net.geral.essomerie.client.gui.inventory.groups;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import net.geral.essomerie._shared.contagem.InventoryGroup;
import net.geral.essomerie.client.core.Client;
import net.geral.essomerie.client.gui.inventory.InventoryManagementTabPanel;
import net.geral.essomerie.client.gui.inventory.groups.tree.InventoryGroupTree;
import net.geral.essomerie.client.resources.S;

public class InventoryGroupsPanel extends JPanel implements
    TreeSelectionListener {
  private static final long                 serialVersionUID = 1L;
  private final InventoryManagementTabPanel inventoryManagement;
  private final InventoryGroupTree          treeGroups;

  public InventoryGroupsPanel(final InventoryManagementTabPanel parent) {
    inventoryManagement = parent;
    setLayout(new BorderLayout(0, 0));
    final JPanel panelItems = new JPanel();
    add(panelItems);
    panelItems.setLayout(new BorderLayout(0, 0));

    final JScrollPane scrollGroups = new JScrollPane();
    panelItems.add(scrollGroups, BorderLayout.CENTER);

    treeGroups = new InventoryGroupTree();
    treeGroups.getSelectionModel().addTreeSelectionListener(this);
    scrollGroups.setViewportView(treeGroups);

    final JPanel panelOptions = new JPanel();
    panelItems.add(panelOptions, BorderLayout.SOUTH);
    panelOptions.setLayout(new BorderLayout(0, 0));

    final JPanel panelButtons = new JPanel();
    panelOptions.add(panelButtons, BorderLayout.SOUTH);

    final JButton btnPrintAdjustments = new JButton("Imprimir Acertos");
    btnPrintAdjustments.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(final ActionEvent e) {
        inventoryManagement.printLog();
      }
    });
    panelButtons.setLayout(new GridLayout(0, 1, 0, 0));
    panelButtons.add(btnPrintAdjustments);

    final JButton btnPrintChecklist = new JButton("Imprimir Checklist");
    btnPrintChecklist.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(final ActionEvent e) {
        inventoryManagement.printChecklist();
      }
    });
    panelButtons.add(btnPrintChecklist);

    final JPanel panelCheckbox = new JPanel();
    panelOptions.add(panelCheckbox, BorderLayout.NORTH);
    panelCheckbox.setLayout(new GridLayout(0, 1, 0, 0));

    final JCheckBox cbAllowItemChanges = new JCheckBox(
        S.INVENTORY_ALLOW_ITEM_ADMIN.s());
    cbAllowItemChanges.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(final ActionEvent e) {
        if (cbAllowItemChanges.isSelected()) {
          final int res = JOptionPane.showConfirmDialog(Client.window(),
              S.INVENTORY_ALLOW_ITEM_ADMIN_CONFIRM.s(), S.TITLE_CONFIRM.s(),
              JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
          if (res != JOptionPane.YES_OPTION) {
            cbAllowItemChanges.setSelected(false);
          }
        }
        InventoryGroupsPanel.this.treeGroups.setDragEnabled(cbAllowItemChanges
            .isSelected());
      }
    });
    panelCheckbox.add(cbAllowItemChanges);
  }

  public InventoryGroup getSelectedGroup() {
    return treeGroups.getSelectedGroup();
  }

  public void updateGroups(final InventoryGroup[] groups) {
    treeGroups.updateGroups(groups);
  }

  @Override
  public void valueChanged(final TreeSelectionEvent e) {
    inventoryManagement.groupChanged();
  }
}
