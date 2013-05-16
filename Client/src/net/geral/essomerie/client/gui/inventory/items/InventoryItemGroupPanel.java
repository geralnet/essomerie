package net.geral.essomerie.client.gui.inventory.items;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.geral.essomerie.client.resources.IMG;
import net.geral.essomerie._shared.contagem.InventoryGroup;

public class InventoryItemGroupPanel extends JPanel implements MouseListener,
	ItemListener {
    private static final long serialVersionUID = 1L;
    private static final int ICON_SIZE = 16;

    private final JComboBox<InventoryGroup> cbSelector;

    private final JLabel lblUp;
    private final JLabel lblDown;
    private final InventoryItemsPanel itemsPanel;

    public InventoryItemGroupPanel(final String title,
	    final InventoryItemsPanel parent) {
	itemsPanel = parent;
	setLayout(new BorderLayout(0, 0));

	final JPanel north = new JPanel();
	add(north, BorderLayout.NORTH);
	north.setLayout(new BorderLayout(0, 0));

	final JLabel lblTitle = new JLabel(title);
	north.add(lblTitle, BorderLayout.CENTER);
	lblTitle.setFont(lblTitle.getFont().deriveFont(
		lblTitle.getFont().getStyle() | Font.BOLD));

	final JPanel buttons = new JPanel();
	north.add(buttons, BorderLayout.EAST);
	buttons.setLayout(new GridLayout(1, 0, 2, 0));

	lblUp = new JLabel();
	lblUp.setIcon(IMG.ICON__UP.icon(ICON_SIZE));
	buttons.add(lblUp);
	lblUp.addMouseListener(this);

	lblDown = new JLabel();
	lblDown.setIcon(IMG.ICON__DOWN.icon(ICON_SIZE));
	buttons.add(lblDown);
	lblDown.addMouseListener(this);

	cbSelector = new JComboBox<InventoryGroup>();
	add(cbSelector, BorderLayout.SOUTH);
	cbSelector.addItemListener(this);

	updateUpDown();
    }

    public int getSelectedGroupId() {
	final Object o = cbSelector.getSelectedItem();
	if (o == null) {
	    return 0;
	}
	return ((InventoryGroup) o).id;
    }

    public JComboBox<InventoryGroup> getSelector() {
	return cbSelector;
    }

    @Override
    public void itemStateChanged(final ItemEvent e) {
	updateUpDown();
	itemsPanel.groupChanged(this);
    }

    @Override
    public void mouseClicked(final MouseEvent e) {
	final int to = cbSelector.getSelectedIndex()
		+ (e.getSource() == lblUp ? -1 : +1);
	if ((to < 0) || (to >= cbSelector.getItemCount())) {
	    return;
	}
	cbSelector.setSelectedIndex(to);
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
    }

    public void setGroups(final InventoryGroup[] groups) {
	cbSelector.removeAllItems();
	for (final InventoryGroup ig : groups) {
	    cbSelector.addItem(ig);
	}
	updateUpDown();
    }

    public void setSelectedGroupId(final int idgroup) {
	if (idgroup > 0) {
	    for (int i = 0; i < cbSelector.getItemCount(); i++) {
		if (cbSelector.getItemAt(i).id == idgroup) {
		    cbSelector.setSelectedIndex(i);
		    return;
		}
	    }
	}
	if (cbSelector.getItemCount() > 0) {
	    cbSelector.setSelectedIndex(0);
	}
    }

    private void updateUpDown() {
	lblUp.setEnabled(cbSelector.getSelectedIndex() > 0);
	lblDown.setEnabled(cbSelector.getSelectedIndex() < (cbSelector
		.getItemCount() - 1));
    }
}
