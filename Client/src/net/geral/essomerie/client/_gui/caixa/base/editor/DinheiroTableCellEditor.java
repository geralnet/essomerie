package net.geral.essomerie.client._gui.caixa.base.editor;

import net.geral.essomerie.client._gui.shared.textfield.MoneyTextField;
import net.geral.essomerie.shared.money.Money;

public class DinheiroTableCellEditor extends
    BaseTableCellEditor<MoneyTextField> {
  private static final long serialVersionUID = 1L;

  public DinheiroTableCellEditor(final boolean allowNegative,
      final boolean allowZero, final boolean allowPositive,
      final boolean showBreakdown) {
    super(new MoneyTextField(allowNegative, allowZero, allowPositive,
        showBreakdown));
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
    editor.setValue((Money) value);
    editor.resetBreakdown();
  }
}
