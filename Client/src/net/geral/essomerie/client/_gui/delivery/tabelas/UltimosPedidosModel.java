package net.geral.essomerie.client._gui.delivery.tabelas;

import javax.swing.table.AbstractTableModel;

import net.geral.essomerie._shared.pedido.ResumoPedido;
import net.geral.essomerie.shared.money.Money;

public class UltimosPedidosModel extends AbstractTableModel {
  private static final long     serialVersionUID = 1L;
  private static final String[] COLUMNS          = { "Data / Hora", "Valor" };
  private ResumoPedido[]        pedidos          = null;
  private Money                 total            = Money.zero();

  @Override
  public Class<?> getColumnClass(final int columnIndex) {
    return String.class;
  }

  @Override
  public int getColumnCount() {
    return COLUMNS.length;
  }

  @Override
  public String getColumnName(final int columnIndex) {
    return COLUMNS[columnIndex];
  }

  @Override
  public int getRowCount() {
    if (pedidos == null) {
      return 1;
    }
    return pedidos.length + 1;
  }

  @Override
  public Object getValueAt(final int rowIndex, final int columnIndex) {
    if (pedidos == null) {
      if (columnIndex == 0) {
        return "Carregando...";
      }
      return "";
    }
    if (rowIndex == pedidos.length) {
      if (columnIndex == 1) {
        return "Total: " + total.toString();
      }
      return "";
    }
    final ResumoPedido p = pedidos[rowIndex];
    switch (columnIndex) {
      case 0:
        return p.datahora.toString();
      case 1:
        return p.consumo.toString();
      default:
        return "R" + rowIndex + "C" + columnIndex;
    }
  }

  public void setResumo(final ResumoPedido[] resumo) {
    pedidos = resumo;
    long subtotal = 0;
    for (final ResumoPedido p : resumo) {
      subtotal += p.consumo.toLong();
    }
    total = Money.fromLong(subtotal);
    fireTableDataChanged();
  }
}
