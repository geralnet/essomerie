package net.geral.essomerie._shared.cliente;

import java.util.Vector;

public class Clientes {
	private final Vector<Customer>	clientes	= new Vector<>();

	public void add(final Customer c) {
		clientes.add(c);
	}

	public int count() {
		return clientes.size();
	}

	public Customer[] getAll() {
		synchronized (clientes) {
			return clientes.toArray(new Customer[clientes.size()]);
		}
	}

	public Customer getId(final int idcliente) {
		for (final Customer c : clientes) {
			if (c.idcliente == idcliente) return c;
		}
		return null;
	}

	public Customer getIndex(final int i) {
		return clientes.get(i);
	}

	public void set(final Customer cliente) {
		synchronized (clientes) {
			int i;
			for (i = 0; i < clientes.size(); i++)
				if (clientes.get(i).idcliente == cliente.idcliente) {
					clientes.set(i, cliente);
					return; // changed
				}
			//not changed, its new then
			clientes.add(cliente);
		}
	}

	public void set(final Clientes list) {
		synchronized (clientes) {
			clientes.clear();
			for (final Customer r : list.clientes) {
				clientes.add(r);
			}
		}
	}
}
