package net.geral.essomerie.client._gui.caixa.base.editor;

import javax.swing.JTextField;

import net.geral.lib.strings.GNStrings;

public class DescricaoTableCellEditor extends BaseTableCellEditor<JTextField> {
  private static final long serialVersionUID = 1L;
  private static final int  MAX_LENGTH       = 20;

  public DescricaoTableCellEditor() {
    super(new JTextField());
  }

  @Override
  public Object getCellEditorValue() {
    final String t = GNStrings.trim(editor.getText());
    if (t.length() == 0) {
      return null;
    }
    if (t.length() > MAX_LENGTH) {
      return GNStrings.trim(t.substring(0, MAX_LENGTH));
    }
    return t;
  }

  @Override
  protected boolean hasError() {
    return false;
  }

  @Override
  protected void setValue(final Object value) {
    editor.setText((value == null) ? "" : value.toString());
  }
}
