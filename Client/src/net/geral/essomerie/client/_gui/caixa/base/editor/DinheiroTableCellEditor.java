package net.geral.essomerie.client._gui.caixa.base.editor;

import net.geral.essomerie.client._gui.shared.textfield.DinheiroTextField;
import net.geral.essomerie._shared.Dinheiro;

public class DinheiroTableCellEditor extends BaseTableCellEditor<DinheiroTextField> {
	private static final long	serialVersionUID	= 1L;

	public DinheiroTableCellEditor(final boolean negativeAllowed, final boolean apresentarDetalhamento) {
		super(new DinheiroTextField(negativeAllowed, apresentarDetalhamento));
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
		editor.setValue((Dinheiro)value);
		editor.resetDetalhamento();
	}
}
