package net.geral.essomerie._shared.pedido;

import java.util.Vector;

import net.geral.essomerie._shared.cliente.Customer;

public class Pedido {
	public Customer				Cliente	= null;					// TODO new Cliente();
	public Vector<PedidoItem>	Items	= new Vector<PedidoItem>();
}
