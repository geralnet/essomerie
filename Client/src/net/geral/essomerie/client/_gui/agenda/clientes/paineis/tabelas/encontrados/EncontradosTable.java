package net.geral.essomerie.client._gui.agenda.clientes.paineis.tabelas.encontrados;

import java.awt.Toolkit;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;

import net.geral.essomerie._shared.cliente.Customer;
import net.geral.essomerie.client._gui.agenda.clientes.paineis.tabelas.encontrados.EncontradosModel.ClienteTableLinha;

public class EncontradosTable extends JTable {
  // private static class CodigoComparator implements
  // Comparator<ClienteTableLinha> {
  // @Override
  // public int compare(final ClienteTableLinha o1, final ClienteTableLinha o2)
  // {
  // return EncontradosTable.compareAndOr(o1, o2,
  // Customer.compareByID(o1.cliente, o2.cliente));
  // }
  // }
  //
  // private static class CPFComparator implements Comparator<ClienteTableLinha>
  // {
  // @Override
  // public int compare(final ClienteTableLinha o1, final ClienteTableLinha o2)
  // {
  // return EncontradosTable.compareAndOr(o1, o2,
  // Customer.compareByCPF(o1.cliente, o2.cliente));
  // }
  // }
  //
  // private static class EnderecosComparator implements
  // Comparator<ClienteTableLinha> {
  // @Override
  // public int compare(final ClienteTableLinha o1, final ClienteTableLinha o2)
  // {
  // return EncontradosTable.compareAndOr(o1, o2,
  // Customer.compareByEndereco(o1.cliente, o2.cliente));
  // }
  // }
  //
  // private static class NomeComparator implements
  // Comparator<ClienteTableLinha> {
  // @Override
  // public int compare(final ClienteTableLinha o1, final ClienteTableLinha o2)
  // {
  // return EncontradosTable.compareAndOr(o1, o2,
  // Customer.compareByNome(o1.cliente, o2.cliente));
  // }
  // }
  //
  // private static class NotaPaulistaComparator implements
  // Comparator<ClienteTableLinha> {
  // @Override
  // public int compare(final ClienteTableLinha o1, final ClienteTableLinha o2)
  // {
  // return EncontradosTable.compareAndOr(o1, o2,
  // Customer.compareByNotaPaulista(o1.cliente, o2.cliente));
  // }
  // }
  //
  // private static class ObservacoesComparator implements
  // Comparator<ClienteTableLinha> {
  // @Override
  // public int compare(final ClienteTableLinha o1, final ClienteTableLinha o2)
  // {
  // return EncontradosTable.compareAndOr(o1, o2,
  // Customer.compareByObservacoes(o1.cliente, o2.cliente));
  // }
  // }
  //
  // private static class TelefonesComparator implements
  // Comparator<ClienteTableLinha> {
  // @Override
  // public int compare(final ClienteTableLinha o1, final ClienteTableLinha o2)
  // {
  // return EncontradosTable.compareAndOr(o1, o2,
  // Customer.compareByTelefone(o1.cliente, o2.cliente));
  // }
  // }

  private static final long  serialVersionUID = 1L;
  private static final int[] WIDTHS           = { 70, 200, 40, 100, 150, 300,
      500                                    };

  public static int compareAndOr(final ClienteTableLinha o1,
      final ClienteTableLinha o2, final int resultIfSame) {
    if (o1.andFilter != o2.andFilter) {
      if (o1.andFilter) {
        return -1;
      }
      return 1;
    }
    if (o1.orFilter != o2.orFilter) {
      if (o1.orFilter) {
        return -1;
      }
      return 1;
    }
    return resultIfSame;
  }

  public EncontradosTable() {
    super(new EncontradosModel());
    setAutoResizeMode(AUTO_RESIZE_OFF);
    setInitialTableSize();
    setDefaultRenderer(Object.class, new EncontradosRenderer());
    setRowSorter(createSorter());
    getTableHeader().setReorderingAllowed(false);
    getSelectionModel().setSelectionMode(
        ListSelectionModel.SINGLE_INTERVAL_SELECTION);
  }

  private TableRowSorter<EncontradosModel> createSorter() {
    TableRowSorter<EncontradosModel> s;
    s = new TableRowSorter<>(getModel());
    // s.setComparator(0, new CodigoComparator());
    // s.setComparator(1, new NomeComparator());
    // s.setComparator(2, new NotaPaulistaComparator());
    // s.setComparator(3, new CPFComparator());
    // s.setComparator(4, new TelefonesComparator());
    // s.setComparator(5, new EnderecosComparator());
    // s.setComparator(6, new ObservacoesComparator());
    return s;
  }

  public Customer get(final int i) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public EncontradosModel getModel() {
    return (EncontradosModel) super.getModel();
  }

  public Customer getSelected() {
    int i = getSelectedRow();
    if (i == -1) {
      return null;
    }
    i = convertRowIndexToModel(i);
    return getModel().getCliente(i);
  }

  public void selecionarClienteID(final int idcliente) {
    final EncontradosModel model = getModel();
    for (int i = 0; i < getColumnCount(); i++) {
      if (model.getCliente(i).idcliente == idcliente) {
        getSelectionModel().setSelectionInterval(i, i);
        return;
      }
    }
  }

  public void selectAndFocusIfOne() {
    if (getModel().getEncontradosCount() == 1) {
      selectAll();
      requestFocusInWindow();
    } else {
      // error beep
      Toolkit.getDefaultToolkit().beep();
    }
  }

  public void setInitialTableSize() {
    final TableColumnModel cm = getColumnModel();
    for (int i = 0; i < WIDTHS.length; i++) {
      cm.getColumn(i).setPreferredWidth(WIDTHS[i]);
    }
  }
}
