package net.geral.essomerie.client._gui.caixa.panels.conferencia;

import javax.swing.table.TableCellEditor;

import net.geral.essomerie.client._gui.caixa.base.BaseLancamentos;
import net.geral.essomerie.client._gui.caixa.base.editor.DinheiroTableCellEditor;
import net.geral.essomerie.client._gui.caixa.base.strdin.StringDinheiroLancamentosTableModel;

public class ConferenciaTableModel extends StringDinheiroLancamentosTableModel {
  private static final long             serialVersionUID          = 1L;

  private final DinheiroTableCellEditor dinheiroSemDetalhesEditor = new DinheiroTableCellEditor(
                                                                      false,
                                                                      true,
                                                                      true,
                                                                      false);

  public ConferenciaTableModel(final BaseLancamentos lancamentos) {
    super(lancamentos);
  }

  @Override
  public TableCellEditor getCellEditor(final int row, final int column) {
    if ((column == 1) && (row != ConferenciaLancamentos.DINHEIRO)) {
      return dinheiroSemDetalhesEditor;
    }
    return super.getCellEditor(row, column);
  }

  @Override
  public boolean isCellEditable(final int rowIndex, final int columnIndex) {
    // valor sempre pode
    if (columnIndex > 0) {
      return true;
    }
    // se acima das fixas, pode
    if (rowIndex >= ConferenciaLancamentos.LINHAS_FIXAS) {
      return true;
    }
    // nao pode
    return false;
  }
}
