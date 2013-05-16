package net.geral.essomerie.client._gui.caixa.base.editor;

import net.geral.gui.textfield.IntegerTextField;

public class IntegerTableCellEditor extends BaseTableCellEditor<IntegerTextField> {
	private static final long	serialVersionUID	= 1L;

	public IntegerTableCellEditor(final boolean negativeAllowed) {
		super(new IntegerTextField(negativeAllowed));
	}

	@Override
	public Object getCellEditorValue() {
		return editor.getValue(true);
	}

	@Override
	protected boolean hasError() {
		return editor.hasError();
	}

	@Override
	protected void setValue(final Object value) {
		editor.setValue((Integer)value);
	}
}
