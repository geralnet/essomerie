package net.geral.essomerie.client._gui.agenda.clientes.paineis.tabelas.encontrados;

import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.table.AbstractTableModel;

import net.geral.essomerie._shared.cliente.Customer;
import net.geral.essomerie.client._gui.agenda.clientes.paineis.ClientePanelFilter;

public class EncontradosModel extends AbstractTableModel {
  public static class ClienteTableLinha implements
      Comparable<ClienteTableLinha> {
    public final Customer cliente;
    public final int      linhaTabela;
    public final int      linhaCliente;
    public final boolean  andFilter;
    public final boolean  orFilter;
    public final float    match;

    public ClienteTableLinha(final Customer _cliente, final int _linhaTabela,
        final int _linhaCliente, final boolean _andFilter,
        final boolean _orFilter, final float _match) {
      cliente = _cliente;
      linhaTabela = _linhaTabela;
      linhaCliente = _linhaCliente;
      andFilter = _andFilter;
      orFilter = _orFilter;
      match = _match;
    }

    @Override
    public int compareTo(final ClienteTableLinha o) {
      if (match > o.match) {
        return -1;
      }
      if (match < o.match) {
        return 1;
      }
      if (andFilter && (!o.andFilter)) {
        return -1;
      }
      if ((!andFilter) && o.andFilter) {
        return 1;
      }
      if (orFilter && (!o.orFilter)) {
        return -1;
      }
      if ((!o.orFilter) && orFilter) {
        return 1;
      }
      // return Customer.compareByNome(cliente, o.cliente);
      return 0;
    }
  }

  private static final long        serialVersionUID = 1L;
  private static final String[]    COLUMNS          = { "Código", "Nome", "NP",
      "CPF", "Telefones", "Endereço", "Observacoes" };
  private ClienteTableLinha[]      encontrados      = new ClienteTableLinha[0];
  private int                      encontradosCount = 0;
  private final ClientePanelFilter lastFilter       = null;

  public void applyFilter(final ClientePanelFilter filter) {
    // lastFilter = filter;
    // final Customer[] clientes = Client.cache().clientes().getAll();
    // if (filter.nenhum()) {
    // setEncontrados(clientes, null);
    // }
    // else {
    // final ArrayList<Customer> and = new ArrayList<>();
    // final ArrayList<Customer> or = new ArrayList<>();
    // for (int i = 0; i < clientes.length; i++) {
    // final Customer c = clientes[i];
    // final Boolean[] founds = new Boolean[] { null, null, null, null, null,
    // null };
    // founds[0] = filter.isFoundByCodigo(c.idcliente);
    // if (!filter.porCodigo()) {
    // founds[1] = filter.isFoundByNome(c.getFilterNome());
    // founds[2] = filter.isFoundByCPF(c.getFilterCPF());
    // founds[3] = filter.isFoundByTelefones(c.getFilterTelefones());
    // founds[4] = filter.isFoundByEnderecos(c.getFilterEnderecoFonetico(),
    // c.getFilterEnderecoNumerico());
    // founds[5] = filter.isFoundByObservacoes(c.getFilterObservacoes());
    // }
    // if (checkAnd(founds)) {
    // and.add(c);
    // }
    // else if (checkOr(founds)) {
    // or.add(c);
    // }
    // }
    // setEncontrados(and, or);
    // }
  }

  private boolean checkAnd(final Boolean[] bs) {
    for (final Boolean b : bs) {
      if ((b != null) && (b.booleanValue() == false)) {
        return false;
      }
    }
    return true;
  }

  private boolean checkOr(final Boolean[] bs) {
    for (final Boolean b : bs) {
      if ((b != null) && (b.booleanValue() == true)) {
        return true;
      }
    }
    return false;
  }

  public void findDuplicates(final Customer lookFor) {
    // final Customer[] clientes = Client.cache().clientes().getAll();
    // final ArrayList<Float> matches = new ArrayList<>();
    // final ArrayList<Customer> encontrados = new ArrayList<>();
    // for (final Customer c : clientes) {
    // if (c.idcliente != lookFor.idcliente) {
    // final float m = c.match(lookFor);
    // if (m > 0f) {
    // encontrados.add(c);
    // matches.add(m);
    // }
    // }
    // }
    // final Customer[] ea = encontrados.toArray(new
    // Customer[encontrados.size()]);
    // final float[] ma = new float[matches.size()];
    // for (int i = 0; i < ma.length; i++) {
    // ma[i] = matches.get(i).floatValue();
    // }
    // setEncontrados(ea, ma);
  }

  public Customer getCliente(final int row) {
    return encontrados[row].cliente;
  }

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

  public int getEncontradosCount() {
    return encontradosCount;
  }

  @Override
  public int getRowCount() {
    return encontrados.length;
  }

  @Override
  public ClienteTableLinha getValueAt(final int rowIndex, final int columnIndex) {
    return encontrados[rowIndex];
  }

  public void reapplyFilter() {
    if (lastFilter != null) {
      applyFilter(lastFilter);
    }
  }

  private void setEncontrados(final ArrayList<Customer> ands,
      final ArrayList<Customer> ors) {
    // final ArrayList<ClienteTableLinha> linhas = new ArrayList<>();
    // final int linhaTabela = 0;
    //
    // for (final Customer c : ands) {
    // final int linhasCliente = Math.max(
    // Math.max(c.telefones.length, c.enderecos.length), 1);
    // for (int lc = 0; lc < linhasCliente; lc++) {
    // linhas.add(new ClienteTableLinha(c, linhaTabela, lc, true, false, 0f));
    // }
    // }
    // for (final Customer c : ors) {
    // final int linhasCliente = Math.max(
    // Math.max(c.telefones.length, c.enderecos.length), 1);
    // for (int lc = 0; lc < linhasCliente; lc++) {
    // linhas.add(new ClienteTableLinha(c, linhaTabela, lc, false, true, 0f));
    // }
    // }
    //
    // setEncontrados(linhas.toArray(new ClienteTableLinha[linhas.size()]),
    // ands.size() + ors.size());
  }

  private void setEncontrados(final ClienteTableLinha[] ctls, final int count) {
    encontrados = ctls;
    encontradosCount = count;
    Arrays.sort(encontrados);
    fireTableDataChanged();
  }

  private void setEncontrados(final Customer[] clientes, final float[] matches) {
    // final ArrayList<ClienteTableLinha> linhas = new ArrayList<>();
    // for (int i = 0; i < clientes.length; i++) {
    // final Customer c = clientes[i];
    // final int linhasCliente = Math.max(Math.max(c.telefones.length,
    // c.enderecos.length), 1);
    // final float m = (matches == null) ? 0f : matches[i];
    // for (int lc = 0; lc < linhasCliente; lc++) {
    // linhas.add(new ClienteTableLinha(c, i, lc, false, false, m));
    // }
    // }
    // setEncontrados(linhas.toArray(new ClienteTableLinha[linhas.size()]),
    // clientes.length);
  }
}
