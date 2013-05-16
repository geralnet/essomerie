package net.geral.essomerie.client._gui.agenda.clientes.paineis;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import net.geral.essomerie._shared.cliente.ClienteDadosExtras;
import net.geral.essomerie._shared.cliente.Customer;
import net.geral.essomerie.client._gui.agenda.clientes.paineis.tabelas.encontrados.EncontradosModel;
import net.geral.essomerie.client._gui.agenda.clientes.paineis.tabelas.encontrados.EncontradosTable;
import net.geral.essomerie.client._gui.delivery.LancarDeliveryTabPanel;
import net.geral.essomerie.client._gui.shared.label.TitleLabel;
import net.geral.essomerie.client.core.Client;
import net.geral.gui.button.ActionButton;
import net.geral.lib.edt.Edt;
import net.geral.lib.strings.GNStrings;
import net.geral.log4j.Log4J;

public class ClientePanel extends JPanel implements ListSelectionListener,
    TableModelListener, ActionListener, DocumentListener, MouseListener,
    Runnable {
  private final ActionButton             btnNovaPesquisa     = new ActionButton(
                                                                 "Nova Pesquisa",
                                                                 KeyEvent.VK_ESCAPE,
                                                                 0,
                                                                 "nova_pesquisa",
                                                                 this);
  private final ActionButton             btnImprimir         = new ActionButton(
                                                                 "Imprimir",
                                                                 'P',
                                                                 "imprimir",
                                                                 this);
  private final ActionButton             btnAlterarCliente   = new ActionButton(
                                                                 "Alterar Cliente",
                                                                 'T',
                                                                 "alterar_cliente",
                                                                 this);
  private final ActionButton             btnCancelar         = new ActionButton(
                                                                 "Cancelar",
                                                                 KeyEvent.VK_ESCAPE,
                                                                 0, "cancelar",
                                                                 this);
  private final ActionButton             btnNovoCliente      = new ActionButton(
                                                                 "Novo Cliente",
                                                                 'N',
                                                                 "novo_cliente",
                                                                 this);
  private final ActionButton             btnSalvar           = new ActionButton(
                                                                 "Salvar", 'S',
                                                                 "salvar", this);
  private final ActionButton             btnLimpar           = new ActionButton(
                                                                 "Limpar", 'L',
                                                                 "limpar", this);
  private final ActionButton             btnConsultarPedidos = new ActionButton(
                                                                 "Pedidos",
                                                                 'D',
                                                                 "consultar_pedidos",
                                                                 this);
  private final ActionButton             btnAbrirMapa        = new ActionButton(
                                                                 "Mapa", 'M',
                                                                 "mapa", this);

  private static final long              serialVersionUID    = 1L;
  private final EncontradosTable         tableEncontrados;
  private final ClientePanelDadosCliente panelDados;
  private final TitleLabel               lblEncontrados;
  private int                            ignoreChanges       = 0;
  private ClientePanelModo               modo                = ClientePanelModo.PESQUISAR;
  private Customer                       clienteAlterando    = null;
  private final ClienteDadosExtras       ultimoExtras        = null;
  private final int                      waitingFor          = 0;

  public ClientePanel() {
    setLayout(new BorderLayout(0, 0));

    final JSplitPane splitPane = new JSplitPane();
    splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
    splitPane.setOneTouchExpandable(true);
    add(splitPane, BorderLayout.CENTER);

    final JPanel panelTop = new JPanel();
    splitPane.setLeftComponent(panelTop);
    panelTop.setLayout(new BorderLayout(0, 0));

    lblEncontrados = new TitleLabel("Dados do Cliente");
    panelTop.add(lblEncontrados, BorderLayout.NORTH);
    lblEncontrados.setText("Clientes Encontrados:");

    final JScrollPane scrollEncontrados = new JScrollPane();
    panelTop.add(scrollEncontrados, BorderLayout.CENTER);

    tableEncontrados = new EncontradosTable();
    tableEncontrados.getSelectionModel().addListSelectionListener(this);
    scrollEncontrados.setViewportView(tableEncontrados);

    panelDados = new ClientePanelDadosCliente();
    splitPane.setRightComponent(panelDados);
    splitPane.setDividerLocation(200);

    // por ultimo (para acionar evento com todos componentes criados)
    addListeners();
    modoPesquisar();
  }

  private void abrirMapa() {
    // final Endereco e = panelDados.tableEnderecos.getSelectedOrLonely();
    // if (e == null) {
    // JOptionPane.showMessageDialog(this, "Favor selecionar um endereço.",
    // "Erro", JOptionPane.INFORMATION_MESSAGE);
    // return;
    // }
    // try {
    // final String procurar = e.getEnderecoCompleto()
    // + ", Sao Paulo - Sao Paulo - Brasil";
    // final Exception ex = HTTPUtils.browse("http://maps.google.com.br?q="
    // + URLEncoder.encode(procurar, "UTF-8"));
    // if (ex != null) {
    // throw ex;
    // }
    // } catch (final Exception exception) {
    // Log4J.warning(exception);
    // }
  }

  @Override
  public void actionPerformed(final ActionEvent e) {
    final String action = e.getActionCommand();
    final Object src = e.getSource();
    if ("pesquisando_txt".equals(action)) {
      tableEncontrados.selectAndFocusIfOne();
    } else if (src == btnAbrirMapa) {
      abrirMapa();
    } else if (src == btnAlterarCliente) {
      modoAlterar();
    } else if (src == btnCancelar) {
      cancelar();
    } else if (src == btnConsultarPedidos) {
      consultarPedidos();
    } else if (src == btnImprimir) {
      imprimirCliente();
    } else if (src == btnLimpar) {
      limpar();
    } else if (src == btnNovaPesquisa) {
      modoPesquisar();
    } else if (src == btnNovoCliente) {
      modoCadastrar();
    } else if (src == btnSalvar) {
      salvar();
    } else {
      Log4J.warning(new Exception("Invalid action: " + action));
    }
  }

  public void addListeners() {
    // Events.Clientes.addListener(this);
    // Events.Delivery.addListener(this);
    panelDados.txtObservacoes.getDocument().addDocumentListener(this);
    panelDados.lblTelefonesAdd.addMouseListener(this);
    panelDados.lblTelefonesRemove.addMouseListener(this);
    panelDados.lblEnderecosAdd.addMouseListener(this);
    panelDados.lblEnderecosRemove.addMouseListener(this);
    tableEncontrados.getModel().addTableModelListener(this);
    panelDados.tableTelefones.getModel().addTableModelListener(this);
    panelDados.tableEnderecos.getModel().addTableModelListener(this);
    textFieldsAddListeners();
  }

  private void cancelar() {
    if (modo == ClientePanelModo.CADASTRAR) {
      modoPesquisar();
    }
    if (modo == ClientePanelModo.ALTERAR) {
      cancelarAlterarCliente();
    }
  }

  private void cancelarAlterarCliente() {
    ignoreChanges++;
    modoConsultar(clienteAlterando);
    final ClientePanelFilter filter = new ClientePanelFilter(
        String.valueOf(clienteAlterando.idcliente), "", "", "", "", "");
    tableEncontrados.getModel().applyFilter(filter);
    clienteAlterando = null;
    ignoreChanges--;
    tableEncontrados.selectAndFocusIfOne();
  }

  @Override
  public void changedUpdate(final DocumentEvent e) {
    documentChanged();
  }

  private void clienteAlterarSalvar() {
    // if (panelDados.txtNome.getText().gntrim().length() == 0) {
    // JOptionPane.showMessageDialog(this, "Nome Inválido.", "Erro",
    // JOptionPane.ERROR_MESSAGE);
    // return;
    // }
    // final Customer c = panelDados.createCliente(clienteAlterando.idcliente);
    // if (c.sameData(clienteAlterando, true)) {
    // Log4J.debug("Cliente #" + c.idcliente +
    // " was not changed. Save not needed.");
    // cancelarAlterarCliente();
    // }
    // else {
    // try {
    // Client.connection().requestClientesAlterar(c);
    // cancelarAlterarCliente();
    // lblEncontrados.setText("Salvando cliente, aguarde...");
    // }
    // catch (final IOException e) {
    // Log4J.warning(e);
    // }
    // }
  }

  private void clienteCadastrarSalvar() {
    // if (panelDados.txtNome.getText().gntrim().length() == 0) {
    // JOptionPane.showMessageDialog(this, "Nome Inválido.", "Erro",
    // JOptionPane.ERROR_MESSAGE);
    // return;
    // }
    // final Customer c = panelDados.createCliente(0);
    // try {
    // Client.connection().requestClientesCadastrar(c);
    // panelDados.showButtons(btnNovaPesquisa);
    // lblEncontrados.setText("Salvando cliente, aguarde...");
    // }
    // catch (final IOException e) {
    // Log4J.warning(e);
    // }
  }

  // @Override
  // public void clientsCacheUpdated() {
  // if (waitingFor > 0) {
  // modoConsultar(waitingFor);
  // waitingFor = 0;
  // }
  // // not waiting
  // final Customer selected = tableEncontrados.getSelected();
  // ignoreChanges++;
  // search();
  // ignoreChanges--;
  // if (selected != null) {
  // tableEncontrados.selecionarClienteID(selected.idcliente);
  // }
  // }
  //
  // @Override
  // public void clientsClienteAlterado(final int idcliente) {
  // waitingFor = idcliente;
  // }
  //
  // @Override
  // public void clientsClienteCadastrado(final int idcliente) {
  // waitingFor = idcliente;
  // }
  //
  // @Override
  // public void clientsDadosExtras(final ClienteDadosExtras cde) {
  // ultimoExtras = cde;
  // final Customer c = tableEncontrados.getSelected();
  // if (c == null) { return; }
  // if (c.idcliente != cde.idcliente) { return; }
  // if (modo == ClientePanelModo.CONSULTAR) {
  // panelDados.setDadosExtras(cde);
  // }
  // }

  private void consultarPedidos() {
    Client.window().openTab(
        new LancarDeliveryTabPanel(tableEncontrados.getSelected()));
  }

  // @Override
  // public void deliveryPedidoLancado(final int idcliente) {
  // // final Customer c = tableEncontrados.getSelected();
  // // if (c == null) return;
  // // if (c.idcliente != idcliente) return;
  // // if (modo == ClientePanelModo.CONSULTAR) {
  // // try {
  // // Client.connection().requestClientesDadosExtras(c.idcliente);
  // // }
  // // catch (final IOException e) {
  // // Log4J.warning(e);
  // // }
  // // }
  // }

  // @Override
  // public void deliveryResumoRecebido(final int idcliente, final
  // ResumoPedido[] pedidos) {}

  private void documentChanged() {
    if (ignoreChanges > 0) {
      return;
    }
    switch (modo) {
      case PESQUISAR:
        panelDados.setPesquisaPorCodigo(isPesquisaPorCodigo());
        search();
        break;
      case ALTERAR:
      case CADASTRAR:
        search();
        break;
      default:
        break;
    }
  }

  private void imprimirCliente() {
    // if (modo == ClientePanelModo.CONSULTAR) {
    // try {
    // final Customer c = tableEncontrados.getSelected();
    // for (final ClienteImpressaoVia v : ClienteImpressaoVia.values()) {
    // PrintSupport.print(new ClienteImpressao(c, v));
    // }
    // } catch (final PrinterException e) {
    // Log4J.warning(e);
    // }
    // }
  }

  @Override
  public void insertUpdate(final DocumentEvent e) {
    documentChanged();
  }

  public boolean isPesquisaPorCodigo() {
    if (modo != ClientePanelModo.PESQUISAR) {
      return false;
    }
    return (GNStrings.trim(panelDados.txtCodigo.getText()).length() != 0);
  }

  public void limpar() {
    if (modo == ClientePanelModo.CADASTRAR) {
      modoCadastrar();
    }
  }

  private void modoAlterar() {
    // apenas se no modo consultar (e garantir que tem cliente selecionado)
    if (modo != ClientePanelModo.CONSULTAR) {
      return;
    }
    clienteAlterando = tableEncontrados.getSelected();
    if (clienteAlterando == null) {
      return;
    }
    modo = ClientePanelModo.ALTERAR;
    panelDados.setTitulo(modo);
    panelDados.cbNotaPaulista.setEnabled(true);
    ignoreChanges++;
    panelDados.setPesquisaPorCodigo(false);
    panelDados.showTelefoneEnderecoExtrasTable(true);
    panelDados.showButtons(btnSalvar, btnCancelar);
    panelDados.setEditable(true);
    panelDados.txtCodigo.setEditable(false);
    panelDados.setCliente(clienteAlterando, ultimoExtras);
    ignoreChanges--;
    search();
  }

  private void modoCadastrar() {
    clienteAlterando = null;
    final ClientePanelModo modoAntigo = modo;
    modo = ClientePanelModo.CADASTRAR;
    panelDados.setTitulo(modo);
    panelDados.cbNotaPaulista.setEnabled(true);
    ignoreChanges++;
    panelDados.setPesquisaPorCodigo(false);
    panelDados.showTelefoneEnderecoExtrasTable(true);
    panelDados.showButtons(btnSalvar, btnLimpar, btnCancelar);
    panelDados.setEditable(true);
    panelDados.txtCodigo.setEditable(false);
    if (modoAntigo != ClientePanelModo.PESQUISAR) {
      // se pesquisando, deixar os campos digitados
      panelDados.setCliente(null, null);
    }
    // panelDados.tableTelefones.getModel().add(panelDados.txtTelefones.getText());
    // panelDados.tableEnderecos.add(panelDados.txtEnderecos.getText());
    ignoreChanges--;
    search();
  }

  private void modoConsultar(final Customer c) {
    modo = ClientePanelModo.CONSULTAR;
    panelDados.setTitulo(modo);
    panelDados.cbNotaPaulista.setEnabled(false);
    ignoreChanges++;
    panelDados.setPesquisaPorCodigo(false);
    panelDados.limpar();
    panelDados.showTelefoneEnderecoExtrasTable(true);
    panelDados.showButtons(btnNovaPesquisa, btnNovoCliente, null,
        btnAlterarCliente, btnConsultarPedidos, btnImprimir, btnAbrirMapa);
    panelDados.setCliente(c, ultimoExtras);
    panelDados.setEditable(false);
    ignoreChanges--;
  }

  private void modoConsultar(final int idcliente) {
    modo = ClientePanelModo.CONSULTAR;
    ignoreChanges++;
    tableEncontrados.getModel().applyFilter(new ClientePanelFilter(idcliente));
    ignoreChanges--;
    tableEncontrados.selecionarClienteID(idcliente);
  }

  private void modoPesquisar() {
    modo = ClientePanelModo.PESQUISAR;
    panelDados.setTitulo(modo);
    panelDados.cbNotaPaulista.setEnabled(false);
    ignoreChanges++;
    tableEncontrados.clearSelection();
    panelDados.limpar();
    panelDados.showTelefoneEnderecoExtrasTable(false);
    panelDados.txtCodigo.setEnabled(true);
    panelDados.txtCodigo.setEditable(true);
    panelDados.setPesquisaPorCodigo(false);
    panelDados.showButtons(btnNovaPesquisa, btnNovoCliente);
    panelDados.setEditable(true);
    ignoreChanges--;
    search();
    Edt.run(false, this); // FIXME alread on EDT, no?
  }

  @Override
  public void mouseClicked(final MouseEvent e) {
    // ver quais modos validos
    switch (modo) {
      case ALTERAR:
      case CADASTRAR:
        break; // valido, continuar
      default:
        return; // invalido, parar
    }

    final Object src = e.getSource();
    if (src == panelDados.lblTelefonesAdd) {
      // panelDados.tableTelefones.getModel().add();
    } else if (src == panelDados.lblTelefonesRemove) {
      panelDados.tableTelefones.removeSelected();
    } else if (src == panelDados.lblEnderecosAdd) {
      // panelDados.tableEnderecos.getModel().add();
    } else if (src == panelDados.lblEnderecosRemove) {
      panelDados.tableEnderecos.removeSelected();
    } else {
      Log4J.warning(new Exception("Invalid source: " + src));
    }
  }

  @Override
  public void mouseEntered(final MouseEvent e) {
  }

  @Override
  public void mouseExited(final MouseEvent e) {
  }

  @Override
  public void mousePressed(final MouseEvent e) {
  }

  @Override
  public void mouseReleased(final MouseEvent e) {
  }

  public void pesquisar() {
    switch (modo) {
      case CONSULTAR:
      case PESQUISAR:
        modoPesquisar();
        break;
      default:
        break;
    }
  }

  public void removeListeners() {
    // Events.Clientes.removeListener(this);
    // Events.Delivery.removeListener(this);
    panelDados.txtCodigo.removeActionListener(this);
    panelDados.txtObservacoes.getDocument().removeDocumentListener(this);
    panelDados.lblTelefonesAdd.removeMouseListener(this);
    panelDados.lblTelefonesRemove.removeMouseListener(this);
    panelDados.lblEnderecosAdd.removeMouseListener(this);
    panelDados.lblEnderecosRemove.removeMouseListener(this);
    tableEncontrados.getModel().removeTableModelListener(this);
    panelDados.tableTelefones.getModel().removeTableModelListener(this);
    panelDados.tableEnderecos.getModel().removeTableModelListener(this);
    textFieldsRemoveListeners();
  }

  @Override
  public void removeUpdate(final DocumentEvent e) {
    documentChanged();
  }

  @Override
  public void run() {
    panelDados.txtTelefones.requestFocusInWindow();
  }

  public void salvar() {
    if (modo == ClientePanelModo.ALTERAR) {
      clienteAlterarSalvar();
    }
    if (modo == ClientePanelModo.CADASTRAR) {
      clienteCadastrarSalvar();
    }
  }

  private void search() {
    final EncontradosModel model = tableEncontrados.getModel();
    if (model == null) {
      return;
    }
    switch (modo) {
      case PESQUISAR:
        model.applyFilter(panelDados.getFilter());// refazer pesquisa
        break;
      case CONSULTAR:
        model.reapplyFilter();
        modoConsultar(tableEncontrados.getSelected());// atualizar atual
        break;
      case ALTERAR:
      case CADASTRAR:
        final int idcliente = (clienteAlterando == null) ? 0
            : clienteAlterando.idcliente;
        model.findDuplicates(panelDados.createCliente(idcliente));
        break;
    }
  }

  @Override
  public void tableChanged(final TableModelEvent e) {
    final Object src = e.getSource();
    if (src == tableEncontrados.getModel()) {
      tableEncontradosChanged();
    } else if (src == panelDados.tableEnderecos.getModel()) {
      tableEnderecosChanged();
    } else if (src == panelDados.tableTelefones.getModel()) {
      tableTelefonesChanged();
    } else {
      Log4J.warning(new Exception("Invalid src: " + src));
    }
  }

  private void tableEncontradosChanged() {
    // if (!Client.cache().clientes().loaded()) {
    // lblEncontrados.setText("Carregando lista de clientes, aguarde...");
    // return;
    // }
    final int n = tableEncontrados.getModel().getEncontradosCount();
    switch (modo) {
      case PESQUISAR:
      case CONSULTAR:
        if (n == 0) {
          lblEncontrados.setText("Nenhum Cliente Encontrado");
        } else {
          final String s = (n > 1 ? "s" : "");
          lblEncontrados.setText(n + " Cliente" + s + " Encontrado" + s);
        }
        break;
      case ALTERAR:
      case CADASTRAR:
        if (n == 0) {
          lblEncontrados
              .setText("Nenhum possível cliente duplicado encontrado.");
        } else {
          lblEncontrados.setText("Possiveis Duplicados: " + n);
        }
        break;
      default:
        Log4J.warning(new Exception("modo invalido: " + modo));
    }
  }

  private void tableEnderecosChanged() {
    documentChanged();
  }

  private void tableTelefonesChanged() {
    documentChanged();
  }

  private void textFieldsAddListeners() {
    final JTextField[] fields = panelDados.getTextFields();
    for (final JTextField f : fields) {
      f.setActionCommand("pesquisando_txt");
      f.addActionListener(this);
      f.getDocument().addDocumentListener(this);
    }
  }

  private void textFieldsRemoveListeners() {
    final JTextField[] fields = panelDados.getTextFields();
    for (final JTextField f : fields) {
      f.removeActionListener(this);
      f.getDocument().removeDocumentListener(this);
    }
  }

  @Override
  public void valueChanged(final ListSelectionEvent e) {
    if (ignoreChanges > 0) {
      return;
    }
    if (e.getValueIsAdjusting()) {
      return;
    }
    switch (modo) {
      case PESQUISAR:
      case CONSULTAR:
        final Customer c = tableEncontrados.getSelected();
        if (c == null) {
          modoPesquisar();
        } else {
          modoConsultar(c);
        }
        break;
      default:
        // do nothing
        break;
    }
  }
}
