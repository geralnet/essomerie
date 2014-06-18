package net.geral.essomerie.client.gui.warehouse.groups;

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

import net.geral.essomerie.client.core.Client;
import net.geral.essomerie.client.gui.warehouse.WarehouseManagementTabPanel;
import net.geral.essomerie.client.gui.warehouse.groups.tree.WarehouseGroupTree;
import net.geral.essomerie.client.resources.S;
import net.geral.essomerie.shared.warehouse.WarehouseGroup;

public class WarehouseGroupsPanel extends JPanel implements
    TreeSelectionListener {
  private static final long                 serialVersionUID = 1L;
  private final WarehouseManagementTabPanel warehouseManagement;
  private final WarehouseGroupTree          treeGroups;

  public WarehouseGroupsPanel() {
    // called by window builder
    this(null);
  }

  public WarehouseGroupsPanel(final WarehouseManagementTabPanel parent) {
    warehouseManagement = parent;
    setLayout(new BorderLayout(0, 0));
    final JPanel panelItems = new JPanel();
    add(panelItems);
    panelItems.setLayout(new BorderLayout(0, 0));

    final JScrollPane scrollGroups = new JScrollPane();
    panelItems.add(scrollGroups, BorderLayout.CENTER);

    treeGroups = new WarehouseGroupTree();
    treeGroups.getSelectionModel().addTreeSelectionListener(this);
    scrollGroups.setViewportView(treeGroups);

    final JPanel panelOptions = new JPanel();
    panelItems.add(panelOptions, BorderLayout.SOUTH);
    panelOptions.setLayout(new BorderLayout(0, 0));

    final JPanel panelCheckbox = new JPanel();
    panelOptions.add(panelCheckbox, BorderLayout.NORTH);
    panelCheckbox.setLayout(new GridLayout(0, 1, 0, 0));

    final JCheckBox cbAllowItemChanges = new JCheckBox(
        S.WAREHOUSE_ALLOW_ITEM_ADMIN.s());
    cbAllowItemChanges.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(final ActionEvent e) {
        if (cbAllowItemChanges.isSelected()) {
          final int res = JOptionPane.showConfirmDialog(Client.window(),
              S.WAREHOUSE_ALLOW_ITEM_ADMIN_CONFIRM.s(), S.TITLE_CONFIRM.s(),
              JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
          if (res != JOptionPane.YES_OPTION) {
            cbAllowItemChanges.setSelected(false);
          }
        }
        setAllowItemChanges(cbAllowItemChanges.isSelected());
      }
    });
    panelCheckbox.add(cbAllowItemChanges);

    final JPanel panelButtons = new JPanel();
    panelOptions.add(panelButtons, BorderLayout.SOUTH);

    final JButton btnPrintAdjustments = new JButton(
        S.WAREHOUSE_PRINT_ADJUSTMENTS.s());
    btnPrintAdjustments.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(final ActionEvent e) {
        warehouseManagement.printLog();
      }
    });
    panelButtons.setLayout(new GridLayout(0, 1, 0, 0));
    panelButtons.add(btnPrintAdjustments);

    final JButton btnPrintChecklist = new JButton(
        S.WAREHOUSE_PRINT_CHECKLIST.s());
    btnPrintChecklist.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(final ActionEvent e) {
        warehouseManagement.printChecklist();
      }
    });
    panelButtons.add(btnPrintChecklist);
  }

  public WarehouseGroup getSelectedGroup() {
    return treeGroups.getSelectedGroup();
  }

  private void setAllowItemChanges(final boolean allow) {
    treeGroups.setDragEnabled(allow);
    warehouseManagement.setAllowChanges(allow);
  }

  public void updateGroups(final WarehouseGroup[] groups) {
    treeGroups.updateGroups(groups);
  }

  @Override
  public void valueChanged(final TreeSelectionEvent e) {
    warehouseManagement.groupChanged();
  }
}
