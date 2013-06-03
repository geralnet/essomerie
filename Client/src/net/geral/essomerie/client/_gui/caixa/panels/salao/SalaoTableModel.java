package net.geral.essomerie.client._gui.caixa.panels.salao;

import java.security.InvalidParameterException;

import javax.swing.table.TableCellEditor;

import net.geral.essomerie.client._gui.caixa.base.BaseLancamentosTableModel;
import net.geral.essomerie.client._gui.caixa.base.editor.DinheiroTableCellEditor;
import net.geral.essomerie.client._gui.caixa.base.editor.IntegerTableCellEditor;
import net.geral.essomerie.shared.money.Money;

public class SalaoTableModel extends BaseLancamentosTableModel {
  private static final long             serialVersionUID       = 1L;

  private static final String[]         COLUNAS                = { "M", "P",
      "Consumo", "Serviço", "Repique", "Conta", "Pago", "C. Médio" };
  private static final int[]            TAMANHOS               = { 30, 30, 0,
      0, 0, 0, 0, 0                                           };
  private static final Class<?>[]       TIPOS                  = {
      Integer.class, Integer.class, Money.class, Money.class, Money.class,
      Money.class, Money.class, Money.class                   };

  private final DinheiroTableCellEditor dinheiroPositivoEditor = new DinheiroTableCellEditor(
                                                                   false, true,
                                                                   true, false);
  private final DinheiroTableCellEditor dinheiroNegativoEditor = new DinheiroTableCellEditor(
                                                                   true, true,
                                                                   true, false);
  private final IntegerTableCellEditor  integerEditor          = new IntegerTableCellEditor(
                                                                   false);

  public SalaoTableModel(final SalaoLancamentos lancamentos) {
    super(lancamentos, COLUNAS, TAMANHOS);
  }

  @Override
  public TableCellEditor getCellEditor(final int row, final int column) {
    switch (column) {
      case 0:
      case 1:
        return integerEditor;
      case 2:
      case 3:
        return dinheiroPositivoEditor;
      case 4:
        return dinheiroNegativoEditor;
      default:
        return null;
    }
  }

  @Override
  public Class<?> getColumnClass(final int columnIndex) {
    return TIPOS[columnIndex];
  }

  @Override
  public Object getValueAt(final int row, final int column) {
    final SalaoLancamento l = (SalaoLancamento) lancamentos.get(row);
    switch (column) {
      case 0:
        return l.get(SalaoLancamento.MESA);
      case 1:
        return l.get(SalaoLancamento.PESSOAS);
      case 2:
        return l.get(SalaoLancamento.CONSUMO);
      case 3:
        return l.get(SalaoLancamento.SERVICO);
      case 4:
        return l.get(SalaoLancamento.REPIQUE);
      case 5:
        return l.getConta();
      case 6:
        return l.getPago();
      case 7:
        return l.getConsumoMedio();
      default:
        throw new InvalidParameterException("R" + row + "C" + column);
    }
  }

  @Override
  public boolean isCellEditable(final int rowIndex, final int columnIndex) {
    switch (columnIndex) {
      case 0:
      case 1:
      case 2:
      case 3:
      case 4:
        return true;
      default:
        return false;
    }
  }

  @Override
  public void setValueAt(final Object aValue, final int rowIndex,
      final int columnIndex) {
    switch (columnIndex) {
      case 0:
        lancamentos.set(rowIndex, SalaoLancamento.MESA, aValue);
        break;
      case 1:
        lancamentos.set(rowIndex, SalaoLancamento.PESSOAS, aValue);
        break;
      case 2:
        lancamentos.set(rowIndex, SalaoLancamento.CONSUMO, aValue);
        break;
      case 3:
        lancamentos.set(rowIndex, SalaoLancamento.SERVICO, aValue);
        break;
      case 4:
        lancamentos.set(rowIndex, SalaoLancamento.REPIQUE, aValue);
        break;
      default:
        throw new InvalidParameterException("R" + rowIndex + "C" + columnIndex
            + ": " + aValue);
    }
    fireTableRowsUpdated(rowIndex, rowIndex);
  }
}
