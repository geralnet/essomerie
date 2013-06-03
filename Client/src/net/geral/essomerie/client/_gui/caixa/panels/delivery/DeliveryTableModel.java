package net.geral.essomerie.client._gui.caixa.panels.delivery;

import javax.swing.table.TableCellEditor;

import net.geral.essomerie.client._gui.caixa.base.BaseLancamentos;
import net.geral.essomerie.client._gui.caixa.base.BaseLancamentosTableModel;
import net.geral.essomerie.client._gui.caixa.base.editor.DescricaoTableCellEditor;
import net.geral.essomerie.client._gui.caixa.base.editor.DinheiroTableCellEditor;
import net.geral.essomerie.shared.money.Money;

public class DeliveryTableModel extends BaseLancamentosTableModel {
  private static final long              serialVersionUID = 1L;

  private static final String[]          COLUNAS          = { "Entregador",
      "Valor", "Extra"                                   };
  private static final int[]             TAMANHOS         = { 0, 60, 60 };
  private static final Class<?>[]        TIPOS            = { String.class,
      Money.class, Money.class                           };

  private final DinheiroTableCellEditor  dinheiroEditor   = new DinheiroTableCellEditor(
                                                              false, true,
                                                              true, true);
  private final DescricaoTableCellEditor descricaoEditor  = new DescricaoTableCellEditor();

  public DeliveryTableModel(final BaseLancamentos lancamentos) {
    super(lancamentos, COLUNAS, TAMANHOS);
  }

  @Override
  public TableCellEditor getCellEditor(final int row, final int column) {
    if (column == 0) {
      return descricaoEditor;
    }
    return dinheiroEditor;
  }

  @Override
  public Class<?> getColumnClass(final int columnIndex) {
    return TIPOS[columnIndex];
  }

  @Override
  public Object getValueAt(final int row, final int column) {
    return lancamentos.get(column, row);
  }

  @Override
  public boolean isCellEditable(final int rowIndex, final int columnIndex) {
    return true;
  }

  @Override
  public void setValueAt(final Object aValue, final int rowIndex,
      final int columnIndex) {
    lancamentos.set(rowIndex, columnIndex, aValue);
  }
}
