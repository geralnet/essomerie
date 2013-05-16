package net.geral.essomerie._shared.cliente;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import net.geral.essomerie.shared.person.Address;
import net.geral.essomerie.shared.person.Telephone;

public class ClientesDataSet implements Serializable {
  private final Vector<ClienteData>         clientes;
  private final Vector<Telephone>           telefones;
  private final Vector<Address>             enderecos;

  private HashMap<Integer, Telephone[]>     idcliente2telefones = null;
  private final HashMap<Integer, Address[]> idcliente2enderecos = null;

  public ClientesDataSet(final Vector<ClienteData> _clientes,
      final Vector<Telephone> _telefones, final Vector<Address> _enderecos) {
    clientes = _clientes;
    telefones = _telefones;
    enderecos = _enderecos;
  }

  public Clientes createClientes() {
    final Clientes cs = new Clientes();
    for (final ClienteData cd : clientes) {
      final Address[] enderecos = getEnderecos(cd.idcliente);
      final Telephone[] telefones = getTelefones(cd.idcliente);
      // final Customer c = new Customer(cd.idcliente, cd.nome, cd.cpf,
      // cd.notaPaulista, cd.observacoes, enderecos, telefones);
      // cs.add(c);
    }
    return cs;
  }

  private void createEnderecosMap() {
    // final HashMap<Integer, ArrayList<Address>> map = new HashMap<Integer,
    // ArrayList<Address>>();
    // for (final Address e : enderecos) {
    // if (!map.containsKey(e.idcliente)) {
    // map.put(e.idcliente, new ArrayList<Address>());
    // }
    // map.get(e.idcliente).add(e);
    // }
    // final HashMap<Integer, Address[]> map2 = new HashMap<Integer, Address[]>(
    // map.size());
    // for (final Integer i : map.keySet()) {
    // final ArrayList<Address> alt = map.get(i);
    // map2.put(i, alt.toArray(new Address[alt.size()]));
    // }
    // idcliente2enderecos = map2;
  }

  private void createTelefonesMap() {
    final HashMap<Integer, ArrayList<Telephone>> map = new HashMap<Integer, ArrayList<Telephone>>();
    for (final Telephone t : telefones) {
      // if (!map.containsKey(t.idreferencia)) {
      // map.put(t.idreferencia, new ArrayList<Telephone>());
      // }
      // map.get(t.idreferencia).add(t);
    }
    final HashMap<Integer, Telephone[]> map2 = new HashMap<Integer, Telephone[]>(
        map.size());
    for (final Integer i : map.keySet()) {
      final ArrayList<Telephone> alt = map.get(i);
      map2.put(i, alt.toArray(new Telephone[alt.size()]));
    }
    idcliente2telefones = map2;
  }

  private Address[] getEnderecos(final int idcliente) {
    if (idcliente2enderecos == null) {
      createEnderecosMap();
    }
    final Address[] enderecos = idcliente2enderecos.get(idcliente);
    if (enderecos == null) {
      return new Address[0];
    }
    return enderecos;
  }

  private Telephone[] getTelefones(final int idcliente) {
    if (idcliente2telefones == null) {
      createTelefonesMap();
    }
    final Telephone[] telefones = idcliente2telefones.get(idcliente);
    if (telefones == null) {
      return new Telephone[0];
    }
    return telefones;
  }
}
