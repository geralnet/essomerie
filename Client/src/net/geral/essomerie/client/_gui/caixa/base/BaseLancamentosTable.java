package net.geral.essomerie.client._gui.caixa.base;

import java.awt.Component;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.EventObject;

import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.JTextComponent;

import net.geral.essomerie.client._gui.caixa.ValorTableCellRenderer;

public class BaseLancamentosTable extends JTable implements ComponentListener {
	private static final long					serialVersionUID	= 1L;
	private static final ValorTableCellRenderer	renderer			= new ValorTableCellRenderer();
	private final BaseLancamentosTableModel		model;
	private JScrollPane							parentScroll		= null;

	public BaseLancamentosTable(final BaseLancamentosTableModel aModel) {
		model = aModel;
		setModel(model);
		setAutoResizeMode(AUTO_RESIZE_OFF);
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		setTabEnter();
	}

	@Override
	public void componentHidden(final ComponentEvent e) {}

	@Override
	public void componentMoved(final ComponentEvent e) {}

	@Override
	public void componentResized(final ComponentEvent e) {
		model.resized(parentScroll.getViewport().getWidth(), getColumnModel());
	}

	@Override
	public void componentShown(final ComponentEvent e) {}

	@Override
	public boolean editCellAt(final int row, final int column, final EventObject e) {
		//always focus on editing cell
		// http://bugs.sun.com/view_bug.do?bug_id=4274963
		final boolean retval = super.editCellAt(row, column, e);
		if (retval) {
			editorComp.requestFocus();
		}
		return retval;
	}

	@Override
	public TableCellEditor getCellEditor(final int row, final int column) {
		return model.getCellEditor(row, column);
	}

	@Override
	public TableCellRenderer getCellRenderer(final int row, final int column) {
		return renderer;
	}

	@Override
	public Component prepareEditor(final TableCellEditor editor, final int row, final int column) {
		final Component c = super.prepareEditor(editor, row, column);
		if (c instanceof JTextComponent) {
			((JTextComponent)c).selectAll();
		}
		return c;
	}

	public void setScroll(final JScrollPane scroll) {
		if (parentScroll != null) {
			parentScroll.removeComponentListener(this);
		}
		parentScroll = scroll;
		parentScroll.addComponentListener(this);
	}

	private void setTabEnter() {
		final InputMap im = getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

		final KeyStroke tab = KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0, false);
		final KeyStroke enter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false);
		final Object tab_am = im.get(tab);
		final Object enter_am = im.get(enter);

		final KeyStroke stab = KeyStroke.getKeyStroke(KeyEvent.VK_TAB, InputEvent.SHIFT_DOWN_MASK, false);
		final KeyStroke senter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, InputEvent.SHIFT_DOWN_MASK, false);
		final Object stab_am = im.get(stab);
		final Object senter_am = im.get(senter);

		im.put(enter, tab_am);
		im.put(tab, enter_am);
		im.put(senter, stab_am);
		im.put(stab, senter_am);
	}
}
