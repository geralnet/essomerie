package net.geral.essomerie.client._gui.delivery;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;
import javax.swing.border.EmptyBorder;

import net.geral.essomerie._shared.cliente.Customer;
import net.geral.essomerie.client._gui.delivery.tabelas.UltimosPedidosTable;
import net.geral.essomerie.client._gui.shared.label.TitleLabel;
import net.geral.essomerie.client.gui.main.TabPanel;
import net.geral.essomerie.client.gui.shared.tables.addresses.AddressesTable;
import net.geral.essomerie.client.gui.shared.textfield.MoneyTextField;
import net.geral.gui.button.ActionButton;
import net.geral.lib.datepicker.DatePickerPanel;
import net.geral.lib.edt.Edt;

public class LancarDeliveryTabPanel extends TabPanel implements ActionListener {
  private static final long         serialVersionUID = 1L;
  private final AddressesTable      tableEnderecos;
  private final MoneyTextField      txtValor;
  private final UltimosPedidosTable tableUltimosPedidos;
  private final Customer            cliente;
  private final DatePickerPanel     calendario;

  public LancarDeliveryTabPanel() {
    // this(new Customer(0, "Nome do Cliente", CPF.createOrZero(0), false, "",
    // new Address[] { new Address(0, 0, "Rua X", "nr Y", "Bairro",
    // CEP.createOrZero(12345678), "Observações") }, new Telephone[0]));
    this(null);
  }

  public LancarDeliveryTabPanel(final Customer _cliente) {
    cliente = _cliente;
    setBorder(new EmptyBorder(5, 5, 5, 5));
    final SpringLayout springLayout = new SpringLayout();
    setLayout(springLayout);

    final TitleLabel lblClienteNome = new TitleLabel(cliente.nome);
    springLayout.putConstraint(SpringLayout.NORTH, lblClienteNome, 0,
        SpringLayout.NORTH, this);
    springLayout.putConstraint(SpringLayout.WEST, lblClienteNome, 0,
        SpringLayout.WEST, this);
    springLayout.putConstraint(SpringLayout.EAST, lblClienteNome, 0,
        SpringLayout.EAST, this);
    add(lblClienteNome);

    final JScrollPane scrollPane = new JScrollPane();
    springLayout.putConstraint(SpringLayout.WEST, scrollPane, 0,
        SpringLayout.WEST, this);
    add(scrollPane);

    tableUltimosPedidos = new UltimosPedidosTable();
    scrollPane.setViewportView(tableUltimosPedidos);
    calendario = new DatePickerPanel();
    springLayout.putConstraint(SpringLayout.NORTH, scrollPane, 0,
        SpringLayout.NORTH, calendario);
    springLayout.putConstraint(SpringLayout.SOUTH, scrollPane, 0,
        SpringLayout.SOUTH, calendario);
    springLayout.putConstraint(SpringLayout.EAST, scrollPane, -2,
        SpringLayout.WEST, calendario);
    springLayout.putConstraint(SpringLayout.NORTH, calendario, 2,
        SpringLayout.SOUTH, lblClienteNome);
    springLayout.putConstraint(SpringLayout.EAST, calendario, 0,
        SpringLayout.EAST, this);
    add(calendario);

    final JScrollPane scrollEnderecos = new JScrollPane();
    springLayout.putConstraint(SpringLayout.NORTH, scrollEnderecos, 2,
        SpringLayout.SOUTH, calendario);
    springLayout.putConstraint(SpringLayout.WEST, scrollEnderecos, 0,
        SpringLayout.WEST, this);
    springLayout.putConstraint(SpringLayout.EAST, scrollEnderecos, 0,
        SpringLayout.EAST, this);
    add(scrollEnderecos);

    final JPanel panelButtons = new JPanel();
    springLayout.putConstraint(SpringLayout.SOUTH, scrollEnderecos, -5,
        SpringLayout.NORTH, panelButtons);

    tableEnderecos = new AddressesTable(false);
    scrollEnderecos.setViewportView(tableEnderecos);

    final JLabel lblValor = new JLabel("Valor:");
    springLayout.putConstraint(SpringLayout.WEST, lblValor, 0,
        SpringLayout.WEST, this);
    lblValor.setFont(lblValor.getFont().deriveFont(
        lblValor.getFont().getStyle() | Font.BOLD));
    add(lblValor);

    txtValor = new MoneyTextField(false, true, true, false);
    springLayout.putConstraint(SpringLayout.SOUTH, lblValor, -5,
        SpringLayout.SOUTH, txtValor);
    springLayout.putConstraint(SpringLayout.SOUTH, txtValor, -2,
        SpringLayout.SOUTH, this);
    springLayout.putConstraint(SpringLayout.WEST, txtValor, 10,
        SpringLayout.EAST, lblValor);
    add(txtValor);
    txtValor.setColumns(8);
    springLayout.putConstraint(SpringLayout.SOUTH, panelButtons, 0,
        SpringLayout.SOUTH, this);
    springLayout.putConstraint(SpringLayout.EAST, panelButtons, 0,
        SpringLayout.EAST, this);
    add(panelButtons);
    panelButtons.setLayout(new GridLayout(1, 0));

    final ActionButton btnSalvar = new ActionButton("Salvar", 'S',
        "lancar_delivery_salvar");
    btnSalvar.addActionListener(this);
    panelButtons.add(btnSalvar);

    final ActionButton btnCancelar = new ActionButton("Cancelar",
        KeyEvent.VK_ESCAPE, 0, "lancar_delivery_cancelar");
    btnCancelar.addActionListener(this);
    panelButtons.add(btnCancelar);
    springLayout.putConstraint(SpringLayout.WEST, btnCancelar, -100,
        SpringLayout.EAST, this);
    springLayout.putConstraint(SpringLayout.SOUTH, btnCancelar, 0,
        SpringLayout.SOUTH, this);
    springLayout.putConstraint(SpringLayout.EAST, btnCancelar, 0,
        SpringLayout.EAST, this);
    springLayout.putConstraint(SpringLayout.NORTH, btnSalvar, 0,
        SpringLayout.NORTH, btnCancelar);
    springLayout.putConstraint(SpringLayout.WEST, btnSalvar, -106,
        SpringLayout.WEST, btnCancelar);
    springLayout.putConstraint(SpringLayout.EAST, btnSalvar, -6,
        SpringLayout.WEST, btnCancelar);

    // tableEnderecos.getModel().setAddresses(cliente.enderecos);
    // tableEnderecos.selectFirst();
    updateCliente();

    Edt.run(false, new Runnable() {
      @Override
      public void run() {
        txtValor.requestFocusInWindow();
      }
    });
  }

  @Override
  public void actionPerformed(final ActionEvent e) {
    final String action = e.getActionCommand();
    if ("lancar_delivery_cancelar".equals(action)) {
      close(false);
    } else if ("lancar_delivery_salvar".equals(action)) {
      salvar();
    }
  }

  // @Override
  // public void clientsCacheUpdated() {
  // updateCliente();
  // }
  //
  // @Override
  // public void clientsClienteAlterado(final int idcliente) {}
  //
  // @Override
  // public void clientsClienteCadastrado(final int idcliente) {}
  //
  // @Override
  // public void clientsDadosExtras(final ClienteDadosExtras cde) {}

  // @Override
  // public void deliveryPedidoLancado(final int idcliente) {
  // if (idcliente == cliente.idcliente) {
  // updateCliente();
  // }
  // }
  //
  // @Override
  // public void deliveryResumoRecebido(final int idcliente, final
  // ResumoPedido[] pedidos) {
  // if (idcliente != cliente.idcliente) { return; }
  // tableUltimosPedidos.getModel().setResumo(pedidos);
  // }

  @Override
  public String getTabText() {
    return "Pedidos";
  }

  private void salvar() {
    // if (!validarDados()) return;
    // final int idcliente = cliente.idcliente;
    // if (idcliente == 0) return;
    // try {
    // final int idendereco = tableEnderecos.getSelected().id;
    // final Dinheiro consumo = txtValor.getValue(false);
    // final LocalDateTime datahora = new LocalDateTime(calendario.getDate());
    // Client.connection().requestPedidosLancar(idcliente, new
    // ResumoPedido(idendereco, consumo, datahora));
    // close(true);
    // }
    // catch (final IOException e) {
    // Log4J.warning(e);
    // }
  }

  @Override
  public void tabClosed() {
    // Events.Delivery.removeListener(this);
    // Events.Clientes.removeListener(this);
  }

  @Override
  public boolean tabCloseRequest() {
    return true;
  }

  @Override
  public void tabCreated() {
    // Events.Clientes.addListener(this);
    // Events.Delivery.addListener(this);
  }

  private void updateCliente() {
    // final Customer novo =
    // Client.cache().clientes().getCliente(cliente.idcliente);
    // if (novo == null) return;
    // cliente = novo;
    // try {
    // Client.connection().requestPedidosResumo(cliente.idcliente);
    // }
    // catch (final IOException e) {
    // Log4J.warning(e);
    // }
  }

  private boolean validarDados() {
    // final Endereco e = tableEnderecos.getSelected();
    // if (e == null) {
    // JOptionPane.showMessageDialog(this, "Favor selecionar um endereço.",
    // "Erro",
    // JOptionPane.INFORMATION_MESSAGE);
    // return false;
    // }
    // if (txtValor.getValue(false).isNaoPositivo()) {
    // JOptionPane.showMessageDialog(this, "Valor inválido.", "Erro",
    // JOptionPane.ERROR_MESSAGE);
    // return false;
    // }
    return true;
  }
}
