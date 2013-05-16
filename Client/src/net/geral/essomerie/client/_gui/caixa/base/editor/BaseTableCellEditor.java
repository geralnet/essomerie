package net.geral.essomerie.client._gui.caixa.base.editor;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

public abstract class BaseTableCellEditor<T extends JComponent> extends AbstractCellEditor implements TableCellEditor,
		FocusListener {
	private static final long	serialVersionUID	= 1L;

	protected final T			editor;

	public BaseTableCellEditor(final T editorComponent) {
		editor = editorComponent;
		editor.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		editor.addFocusListener(this);
	}

	@Override
	public void focusGained(final FocusEvent e) {}

	@Override
	public void focusLost(final FocusEvent e) {
		if (e.isTemporary()) { return; }
		super.stopCellEditing();
	}

	@Override
	public Component getTableCellEditorComponent(final JTable table, final Object value, final boolean isSelected,
			final int row, final int column) {
		setValue(value);
		return editor;
	}

	protected abstract boolean hasError();

	protected abstract void setValue(Object value);

	@Override
	public boolean stopCellEditing() {
		if (hasError()) { return false; }
		return super.stopCellEditing();
	}
}
