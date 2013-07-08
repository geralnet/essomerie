package net.geral.essomerie.client.gui.inventory;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;

import net.geral.essomerie._shared.contagem.ContagemAlteracaoQuantidade;
import net.geral.essomerie._shared.contagem.ContagemMotivo;
import net.geral.essomerie._shared.contagem.ContagemMotivos;
import net.geral.essomerie._shared.contagem.InventoryItem;
import net.geral.essomerie._shared.contagem.InventoryLog;
import net.geral.essomerie.client.core.Client;
import net.geral.essomerie.client.gui.inventory.logtable.InventoryLogTable;
import net.geral.essomerie.client.gui.inventory.table.InventoryTable;
import net.geral.essomerie.client.gui.shared.label.TitleLabel;
import net.geral.lib.util.StringUtils;

import org.apache.log4j.Logger;

//TODO check & translate
//TODO use ActionDelay instead of reimplementing it
public class InventoryChangePanel extends JPanel implements ItemListener,
    KeyListener, ActionListener, Runnable {
  private static final Logger     logger                  = Logger
                                                              .getLogger(InventoryChangePanel.class);
  private static final long       serialVersionUID        = 1L;
  private static final String     MOTIVO_BRANCO           = "---";

  private static final long       SLEEP_BETWEEN_CHECKS_MS = 100;
  private static final long       WAIT_BEFORE_HISTORY_MS  = 500;
  private final Dimension         tamanho_alterar         = new Dimension(300,
                                                              300);
  private final QuantityTextField txtQuantidade           = new QuantityTextField(
                                                              this);
  private final JTextField        txtObservacoes;
  private final JLabel            lblTitulo;
  private final JRadioButton      rdAumentar;
  private final JRadioButton      rdDiminuir;
  private final JRadioButton      rdAcertar;
  private final JRadioButton[]    rdMotivos;
  private final ButtonGroup       buttonGroupAlteracao    = new ButtonGroup();
  private final ButtonGroup       buttonGroupMotivo       = new ButtonGroup();
  private final InventoryTable    contagemTable;
  private int                     ultimoItemID            = -1;
  private int                     historicoItemID         = 0;
  private ContagemMotivos         motivos                 = new ContagemMotivos();
  private final JPanel            panelErroNegativo;
  private final InventoryLogTable tableHistorico;
  private long                    obterHistoricoAt        = 0;
  private boolean                 running                 = true;

  public InventoryChangePanel(final InventoryTable table) {
    contagemTable = table;
    setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
    setMinimumSize(tamanho_alterar);
    setPreferredSize(tamanho_alterar);

    final SpringLayout sl_panelAlterar = new SpringLayout();
    setLayout(sl_panelAlterar);

    lblTitulo = new TitleLabel("[alterar]", false);
    sl_panelAlterar.putConstraint(SpringLayout.NORTH, lblTitulo, 5,
        SpringLayout.NORTH, this);
    sl_panelAlterar.putConstraint(SpringLayout.WEST, lblTitulo, 5,
        SpringLayout.WEST, this);
    sl_panelAlterar.putConstraint(SpringLayout.EAST, lblTitulo, -5,
        SpringLayout.EAST, this);
    add(lblTitulo);

    final JPanel panelAlterarAlteracao = new JPanel();
    sl_panelAlterar.putConstraint(SpringLayout.NORTH, panelAlterarAlteracao, 7,
        SpringLayout.SOUTH, lblTitulo);
    sl_panelAlterar.putConstraint(SpringLayout.WEST, panelAlterarAlteracao, 5,
        SpringLayout.WEST, this);
    sl_panelAlterar.putConstraint(SpringLayout.EAST, panelAlterarAlteracao,
        142, SpringLayout.WEST, this);
    this.add(panelAlterarAlteracao);
    panelAlterarAlteracao.setLayout(new BorderLayout(0, 0));

    final JLabel lblAlterao = new JLabel("Altera\u00E7\u00E3o:");
    lblAlterao.setFont(lblAlterao.getFont().deriveFont(
        lblAlterao.getFont().getStyle() | Font.BOLD));
    panelAlterarAlteracao.add(lblAlterao, BorderLayout.NORTH);

    final JPanel panelAlterarAlteracaoSub = new JPanel();
    panelAlterarAlteracao.add(panelAlterarAlteracaoSub);
    panelAlterarAlteracaoSub.setLayout(new GridLayout(0, 1, 0, 0));

    rdAumentar = new JRadioButton("Aumentar");
    rdAumentar.addItemListener(this);
    buttonGroupAlteracao.add(rdAumentar);
    panelAlterarAlteracaoSub.add(rdAumentar);

    rdDiminuir = new JRadioButton("Diminuir");
    rdDiminuir.addItemListener(this);
    buttonGroupAlteracao.add(rdDiminuir);
    panelAlterarAlteracaoSub.add(rdDiminuir);

    rdAcertar = new JRadioButton("Acertar");
    rdAcertar.addItemListener(this);
    buttonGroupAlteracao.add(rdAcertar);
    panelAlterarAlteracaoSub.add(rdAcertar);

    final JPanel panelAlterarMotivo = new JPanel();
    sl_panelAlterar.putConstraint(SpringLayout.NORTH, panelAlterarMotivo, 0,
        SpringLayout.NORTH, panelAlterarAlteracao);
    sl_panelAlterar.putConstraint(SpringLayout.WEST, panelAlterarMotivo, 5,
        SpringLayout.EAST, panelAlterarAlteracao);
    sl_panelAlterar.putConstraint(SpringLayout.EAST, panelAlterarMotivo, -5,
        SpringLayout.EAST, this);
    this.add(panelAlterarMotivo);
    panelAlterarMotivo.setLayout(new BorderLayout(0, 0));

    final JLabel lblMotivo = new JLabel("Motivo:");
    lblMotivo.setFont(new Font("Tahoma", Font.BOLD, 11));
    panelAlterarMotivo.add(lblMotivo, BorderLayout.NORTH);

    final JPanel panelAlterarMotivoSub = new JPanel();
    panelAlterarMotivo.add(panelAlterarMotivoSub);
    panelAlterarMotivoSub.setLayout(new GridLayout(0, 1, 0, 0));

    rdMotivos = new JRadioButton[ContagemMotivos.MOTIVOS_POR_TIPO];
    for (int i = 0; i < rdMotivos.length; i++) {
      rdMotivos[i] = new JRadioButton();
      buttonGroupMotivo.add(rdMotivos[i]);
      panelAlterarMotivoSub.add(rdMotivos[i]);
    }

    final JPanel panelAlterarQuantidade = new JPanel();
    sl_panelAlterar.putConstraint(SpringLayout.NORTH, panelAlterarQuantidade,
        7, SpringLayout.SOUTH, panelAlterarMotivo);
    sl_panelAlterar.putConstraint(SpringLayout.WEST, panelAlterarQuantidade, 0,
        SpringLayout.WEST, panelAlterarAlteracao);
    sl_panelAlterar.putConstraint(SpringLayout.EAST, panelAlterarQuantidade, 0,
        SpringLayout.EAST, panelAlterarAlteracao);
    this.add(panelAlterarQuantidade);
    panelAlterarQuantidade.setLayout(new BorderLayout(0, 0));

    final JLabel lblQuantidade = new JLabel("Quantidade:");
    panelAlterarQuantidade.add(lblQuantidade, BorderLayout.NORTH);
    lblQuantidade.setFont(lblQuantidade.getFont().deriveFont(
        lblQuantidade.getFont().getStyle() | Font.BOLD));
    sl_panelAlterar.putConstraint(SpringLayout.NORTH, lblQuantidade, 51,
        SpringLayout.NORTH, this);
    sl_panelAlterar.putConstraint(SpringLayout.WEST, lblQuantidade, 155,
        SpringLayout.WEST, this);

    panelAlterarQuantidade.add(txtQuantidade);
    sl_panelAlterar.putConstraint(SpringLayout.NORTH, txtQuantidade, 48,
        SpringLayout.NORTH, this);
    sl_panelAlterar.putConstraint(SpringLayout.WEST, txtQuantidade, 188,
        SpringLayout.WEST, this);
    txtQuantidade.setColumns(10);

    final JPanel panelAlterarObservacoes = new JPanel();
    sl_panelAlterar.putConstraint(SpringLayout.NORTH, panelAlterarObservacoes,
        7, SpringLayout.SOUTH, panelAlterarQuantidade);

    panelErroNegativo = new JPanel();
    sl_panelAlterar.putConstraint(SpringLayout.SOUTH, panelErroNegativo, 0,
        SpringLayout.SOUTH, panelAlterarQuantidade);
    panelErroNegativo.setVisible(false);
    panelErroNegativo.setBorder(new LineBorder(new Color(0, 0, 0)));
    panelErroNegativo.setBackground(Color.PINK);
    sl_panelAlterar.putConstraint(SpringLayout.WEST, panelErroNegativo, 5,
        SpringLayout.EAST, panelAlterarQuantidade);
    sl_panelAlterar.putConstraint(SpringLayout.EAST, panelErroNegativo, 0,
        SpringLayout.EAST, panelAlterarMotivo);
    add(panelErroNegativo);
    panelErroNegativo.setLayout(new GridLayout(0, 1, 0, 0));

    final JLabel lblErroNegativo1 = new JLabel("Quantidade Negativa");
    panelErroNegativo.add(lblErroNegativo1);
    lblErroNegativo1.setFont(lblErroNegativo1.getFont().deriveFont(
        lblErroNegativo1.getFont().getStyle() | Font.BOLD));
    lblErroNegativo1.setHorizontalAlignment(SwingConstants.CENTER);
    sl_panelAlterar.putConstraint(SpringLayout.NORTH, lblErroNegativo1, 0,
        SpringLayout.NORTH, panelAlterarQuantidade);
    sl_panelAlterar.putConstraint(SpringLayout.WEST, lblErroNegativo1, 5,
        SpringLayout.EAST, panelAlterarQuantidade);
    sl_panelAlterar.putConstraint(SpringLayout.EAST, lblErroNegativo1, 0,
        SpringLayout.EAST, panelAlterarMotivo);

    final JLabel lblErroNegativo2 = new JLabel("(ser\u00E1 ajustada para zero)");
    lblErroNegativo2.setHorizontalAlignment(SwingConstants.CENTER);
    panelErroNegativo.add(lblErroNegativo2);
    sl_panelAlterar.putConstraint(SpringLayout.WEST, lblErroNegativo2, 16,
        SpringLayout.EAST, panelAlterarAlteracao);
    sl_panelAlterar.putConstraint(SpringLayout.SOUTH, lblErroNegativo2, -13,
        SpringLayout.NORTH, panelAlterarObservacoes);
    sl_panelAlterar.putConstraint(SpringLayout.WEST, panelAlterarObservacoes,
        0, SpringLayout.WEST, lblTitulo);
    sl_panelAlterar.putConstraint(SpringLayout.EAST, panelAlterarObservacoes,
        0, SpringLayout.EAST, lblTitulo);
    this.add(panelAlterarObservacoes);
    panelAlterarObservacoes.setLayout(new BorderLayout(0, 0));

    final JLabel lblObservaes = new JLabel("Observa\u00E7\u00F5es:");
    lblObservaes.setFont(lblObservaes.getFont().deriveFont(
        lblObservaes.getFont().getStyle() | Font.BOLD));
    panelAlterarObservacoes.add(lblObservaes, BorderLayout.NORTH);

    txtObservacoes = new JTextField();
    panelAlterarObservacoes.add(txtObservacoes, BorderLayout.SOUTH);
    txtObservacoes.setColumns(10);

    txtQuantidade.addKeyListener(this);
    txtObservacoes.addKeyListener(this);

    final JButton btnUp = new JButton("Anterior ( \u2191 )");
    btnUp.addActionListener(this);
    btnUp.setActionCommand("up");
    sl_panelAlterar.putConstraint(SpringLayout.NORTH, btnUp, 7,
        SpringLayout.SOUTH, panelAlterarObservacoes);
    add(btnUp);

    final JButton btnDown = new JButton("Próximo ( \u2193 )");
    sl_panelAlterar.putConstraint(SpringLayout.WEST, btnUp, -125,
        SpringLayout.WEST, btnDown);
    sl_panelAlterar.putConstraint(SpringLayout.EAST, btnUp, -5,
        SpringLayout.WEST, btnDown);
    sl_panelAlterar.putConstraint(SpringLayout.WEST, btnDown, -120,
        SpringLayout.EAST, this);
    btnDown.addActionListener(this);
    btnDown.setActionCommand("down");
    sl_panelAlterar.putConstraint(SpringLayout.NORTH, btnDown, 0,
        SpringLayout.NORTH, btnUp);
    sl_panelAlterar.putConstraint(SpringLayout.EAST, btnDown, 0,
        SpringLayout.EAST, panelAlterarMotivo);
    add(btnDown);

    final JButton btnCommit = new JButton("Salvar (Enter)");
    btnCommit.addActionListener(this);
    sl_panelAlterar.putConstraint(SpringLayout.NORTH, btnCommit, 5,
        SpringLayout.SOUTH, btnUp);
    sl_panelAlterar.putConstraint(SpringLayout.WEST, btnCommit, 0,
        SpringLayout.WEST, btnUp);
    sl_panelAlterar.putConstraint(SpringLayout.EAST, btnCommit, 0,
        SpringLayout.EAST, btnUp);
    btnCommit.setActionCommand("commit");
    add(btnCommit);

    final JButton btnCancel = new JButton("Cancelar (Esc)");
    btnCancel.addActionListener(this);
    sl_panelAlterar.putConstraint(SpringLayout.NORTH, btnCancel, 0,
        SpringLayout.NORTH, btnCommit);
    sl_panelAlterar.putConstraint(SpringLayout.WEST, btnCancel, 0,
        SpringLayout.WEST, btnDown);
    sl_panelAlterar.putConstraint(SpringLayout.EAST, btnCancel, 0,
        SpringLayout.EAST, btnDown);
    btnCancel.setActionCommand("cancel");
    add(btnCancel);

    final JPanel panelHistorico = new JPanel();
    sl_panelAlterar.putConstraint(SpringLayout.NORTH, panelHistorico, 14,
        SpringLayout.SOUTH, btnCommit);
    sl_panelAlterar.putConstraint(SpringLayout.WEST, panelHistorico, 0,
        SpringLayout.WEST, lblTitulo);
    sl_panelAlterar.putConstraint(SpringLayout.SOUTH, panelHistorico, -5,
        SpringLayout.SOUTH, this);
    sl_panelAlterar.putConstraint(SpringLayout.EAST, panelHistorico, 0,
        SpringLayout.EAST, lblTitulo);
    add(panelHistorico);
    panelHistorico.setLayout(new BorderLayout(0, 0));

    final JLabel lblHistorico = new JLabel("Hist\u00F3rico:");
    lblHistorico.setFont(lblHistorico.getFont().deriveFont(
        lblHistorico.getFont().getStyle() | Font.BOLD));
    panelHistorico.add(lblHistorico, BorderLayout.NORTH);

    final JScrollPane scrollPane = new JScrollPane();
    panelHistorico.add(scrollPane, BorderLayout.CENTER);

    tableHistorico = new InventoryLogTable(this);
    scrollPane.setViewportView(tableHistorico);

    setItem(null);
    final Thread t = new Thread(this);
    t.setName("InventoryLogFetcher");
    t.start();
  }

  @Override
  public void actionPerformed(final ActionEvent e) {
    final String cmd = e.getActionCommand();
    if ("up".equals(cmd)) {
      moveUp();
    } else if ("down".equals(cmd)) {
      moveDown();
    } else if ("cancel".equals(cmd)) {
      cancelEditing();
    } else if ("commit".equals(cmd)) {
      commitEditing();
    } else {
      logger.warn("Invalid action command: " + cmd);
    }
  }

  private boolean alterado() {
    if (txtQuantidade.getValue(true) != null) {
      return true;
    }
    if (StringUtils.trim(txtObservacoes.getText()).length() != 0) {
      return true;
    }
    return false;
  }

  private void cancelEditing() {
    setItem(null); // if not setting null first, it will not reload because
    // has same id
    setItem(contagemTable.getSelected());
  }

  public boolean changeMotivo(char c) {
    c = Character.toUpperCase(c);
    for (final JRadioButton rb : rdMotivos) {
      final String s = rb.getText();
      if (s.length() > 0) {
        final char rbc = Character.toUpperCase(s.charAt(0));
        if (c == rbc) {
          rb.setSelected(true);
          return true;
        }
      }
    }
    return false;
  }

  public boolean changeTipo(final char c) {
    switch (c) {
      case '+':
        rdAumentar.setSelected(true);
        return true;
      case '-':
        rdDiminuir.setSelected(true);
        return true;
      case '=':
        rdAcertar.setSelected(true);
        return true;
    }
    return false;
  }

  private boolean checkKeyPress(final int keyCode) {
    switch (keyCode) {
      case KeyEvent.VK_DOWN:
        moveDown();
        return true;
      case KeyEvent.VK_UP:
        moveUp();
        return true;
      case KeyEvent.VK_ESCAPE:
        cancelEditing();
        return true;
      case KeyEvent.VK_ENTER:
        commitEditing();
        return true;
      default:
        return false;
    }
  }

  private void commitEditing() {
    try {
      if (alterado()) {
        final int id = contagemTable.getSelected().id;
        final char tipo = getTipo();
        final int idmotivo = getMotivoId();
        final float quantidade = txtQuantidade.getValue(false).floatValue();
        final String observacoes = StringUtils.trim(txtObservacoes.getText());
        Client
            .connection()
            .inventory()
            .requestQuantityChange(
                new ContagemAlteracaoQuantidade(id, tipo, idmotivo, quantidade,
                    observacoes));
        Client.connection().inventory().requestChangeLogByItem(id);
      }
      cancelEditing();
    } catch (final IOException e) {
      logger.warn(e, e);
    }
  }

  public String getMotivo(final char tipo, final int idmotivo) {
    return motivos.get(tipo, idmotivo);
  }

  private int getMotivoId() {
    for (int i = 0; i < rdMotivos.length; i++) {
      if (rdMotivos[i].isSelected()) {
        return i + 1;
      }
    }
    return 0;
  }

  private char getTipo() {
    if (rdAumentar.isSelected()) {
      return '+';
    }
    if (rdDiminuir.isSelected()) {
      return '-';
    }
    return '=';
  }

  @Override
  public void itemStateChanged(final ItemEvent e) {
    updateMotivos();
  }

  @Override
  public void keyPressed(final KeyEvent e) {
    if (checkKeyPress(e.getKeyCode())) {
      e.consume();
    }
  }

  @Override
  public void keyReleased(final KeyEvent e) {
  }

  @Override
  public void keyTyped(final KeyEvent e) {
  }

  private void moveDown() {
    commitEditing();
    contagemTable.selectNext();
  }

  private void moveUp() {
    commitEditing();
    contagemTable.selectPrevious();
  }

  public boolean quantidadeDigitada(final float qtd) {
    final InventoryItem ci = contagemTable.getSelected();
    if ((getTipo() != '-') || (ci == null)) {
      panelErroNegativo.setVisible(false);
      return true;
    }
    final float qtd_item = ci.getQuantidade();
    final boolean negativo = (qtd_item - qtd) < 0;
    panelErroNegativo.setVisible(negativo);
    return !negativo;
  }

  @Override
  public void run() {
    while (running) {
      // TODO add loop count
      synchronized (this) {
        if (ultimoItemID != historicoItemID) {
          // se o item selecionado for diferente, contar tempo para
          // pegar historico
          historicoItemID = ultimoItemID;
          obterHistoricoAt = System.currentTimeMillis()
              + WAIT_BEFORE_HISTORY_MS;
        } else if (obterHistoricoAt == 0) {
          // se nao for o mesmo (mudou), se obterHistorico=0 significa
          // que ja pegou historico
          // do nothing
          // nota: nao usar sleep com o tempo que falta, pois o ID
          // pode mudar e a contagem reiniciar
        } else if (obterHistoricoAt <= System.currentTimeMillis()) {
          // se esta na hora de checar, solicitar historico
          try {
            Client.connection().inventory()
                .requestChangeLogByItem(historicoItemID);
            obterHistoricoAt = 0;
          } catch (final IOException e) {
            logger.warn(e, e);
          }
        }
      }
      try {
        Thread.sleep(SLEEP_BETWEEN_CHECKS_MS);
      } catch (final InterruptedException e) {
        logger.warn(e, e);
      }
    }
  }

  public void setHistorico(final InventoryLog h) {
    tableHistorico.set(h);
  }

  public void setItem(final InventoryItem i) {
    synchronized (this) {
      final int id = i == null ? 0 : i.id;
      if (ultimoItemID == id) {
        return;
      }
      ultimoItemID = id;
      panelErroNegativo.setVisible(false);
      final boolean ok = i != null;
      setEnabled(ok);
      lblTitulo.setText(ok ? i.nome : "[selecione um item]");
      rdAcertar.setSelected(true);
      rdMotivos[3].setSelected(true);
      txtQuantidade.setValue(null);
      txtObservacoes.setText("");

      rdAcertar.setEnabled(ok);
      rdAumentar.setEnabled(ok);
      rdDiminuir.setEnabled(ok);
      for (final JRadioButton rb : rdMotivos) {
        rb.setEnabled(ok && !rb.getText().equals(MOTIVO_BRANCO));
      }
      txtQuantidade.setEnabled(ok);
      txtObservacoes.setEnabled(ok);

      txtQuantidade.requestFocus();
      tableHistorico.getModel().setData(null);
    }
  }

  public void setMotivos(final ContagemMotivos motivos) {
    this.motivos = motivos;
    rdAcertar.setSelected(true);
    updateMotivos();
  }

  public synchronized void stop() {
    running = false;
  }

  private void updateMotivos() {
    final ContagemMotivo[] ms = motivos.getMotivos(getTipo());
    for (int i = 0; i < ContagemMotivos.MOTIVOS_POR_TIPO; i++) {
      final ContagemMotivo m = ms[i];
      rdMotivos[i].setText(m == null ? MOTIVO_BRANCO : m.motivo);
      rdMotivos[i].setEnabled(isEnabled() && (m != null));
    }
    rdMotivos[rdMotivos.length - 1].setSelected(true);
  }
}
