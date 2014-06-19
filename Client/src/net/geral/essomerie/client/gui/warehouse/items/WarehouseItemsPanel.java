package net.geral.essomerie.client.gui.warehouse.items;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
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

import net.geral.essomerie.client.core.Client;
import net.geral.essomerie.client.gui.shared.label.TitleLabel;
import net.geral.essomerie.client.gui.warehouse.items.table.WarehouseItemTable;
import net.geral.essomerie.client.gui.warehouse.logtable.WarehouseLogTable;
import net.geral.essomerie.client.resources.S;
import net.geral.essomerie.shared.warehouse.WarehouseChangeLog;
import net.geral.essomerie.shared.warehouse.WarehouseChangeReason;
import net.geral.essomerie.shared.warehouse.WarehouseChangeReasons;
import net.geral.essomerie.shared.warehouse.WarehouseItem;
import net.geral.essomerie.shared.warehouse.WarehouseQuantityChange;
import net.geral.lib.actiondelay.ActionDelay;
import net.geral.lib.actiondelay.ActionDelayListener;
import net.geral.lib.util.StringUtils;

import org.apache.log4j.Logger;

public class WarehouseItemsPanel extends JPanel implements ItemListener,
    KeyListener, ActionListener, ActionDelayListener<WarehouseItem> {
  private static final Logger              logger            = Logger
                                                                 .getLogger(WarehouseItemsPanel.class);
  private static final long                serialVersionUID  = 1L;
  private static final String              BLANK_REASON      = "---";

  private final Dimension                  change_size       = new Dimension(
                                                                 300, 300);
  private final QuantityTextField          txtQuantity       = new QuantityTextField(
                                                                 this);
  private final JTextField                 txtComments;
  private final TitleLabel                 lblTitle;
  private final JRadioButton               rdIncrease;
  private final JRadioButton               rdDecrease;
  private final JRadioButton               rdSet;
  private final JRadioButton[]             rdReasons;
  private final ButtonGroup                buttonGroupChange = new ButtonGroup();
  private final ButtonGroup                buttonGroupReason = new ButtonGroup();
  private final WarehouseItemTable             warehouseTable;
  private final JPanel                     panelNegativeError;
  private final WarehouseLogTable          changeLogTable;
  private WarehouseChangeReasons           reasons           = new WarehouseChangeReasons();
  private final ActionDelay<WarehouseItem> actionDelay       = new ActionDelay<WarehouseItem>(
                                                                 "WarehouseItemDelay",
                                                                 this, this);

  public WarehouseItemsPanel() {
    // used for Window Builder
    this(null);
  }

  public WarehouseItemsPanel(final WarehouseItemTable table) {
    warehouseTable = table;
    setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
    setMinimumSize(change_size);
    setPreferredSize(new Dimension(655, 468));

    final SpringLayout sl_panelChange = new SpringLayout();
    setLayout(sl_panelChange);

    lblTitle = new TitleLabel("", false);
    sl_panelChange.putConstraint(SpringLayout.NORTH, lblTitle, 5,
        SpringLayout.NORTH, this);
    sl_panelChange.putConstraint(SpringLayout.WEST, lblTitle, 5,
        SpringLayout.WEST, this);
    sl_panelChange.putConstraint(SpringLayout.EAST, lblTitle, -5,
        SpringLayout.EAST, this);
    add(lblTitle);

    final JPanel panelChangeMode = new JPanel();
    sl_panelChange.putConstraint(SpringLayout.NORTH, panelChangeMode, 4,
        SpringLayout.SOUTH, lblTitle);
    sl_panelChange.putConstraint(SpringLayout.WEST, panelChangeMode, 0,
        SpringLayout.WEST, lblTitle);
    add(panelChangeMode);

    rdIncrease = new JRadioButton(S.WAREHOUSE_CHANGE_MODE_INCREASE.s());
    rdIncrease.addItemListener(this);
    panelChangeMode.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));

    final JLabel lblChangeMode = new JLabel(S.WAREHOUSE_CHANGE_MODE_TITLE.s());
    panelChangeMode.add(lblChangeMode);
    sl_panelChange.putConstraint(SpringLayout.NORTH, lblChangeMode, 5,
        SpringLayout.SOUTH, lblTitle);
    sl_panelChange.putConstraint(SpringLayout.WEST, lblChangeMode, 0,
        SpringLayout.WEST, lblTitle);
    lblChangeMode.setFont(lblChangeMode.getFont().deriveFont(
        lblChangeMode.getFont().getStyle() | Font.BOLD));
    sl_panelChange.putConstraint(SpringLayout.SOUTH, lblChangeMode, 0,
        SpringLayout.SOUTH, panelChangeMode);
    buttonGroupChange.add(rdIncrease);
    panelChangeMode.add(rdIncrease);

    rdDecrease = new JRadioButton(S.WAREHOUSE_CHANGE_MODE_DECREASE.s());
    rdDecrease.addItemListener(this);
    buttonGroupChange.add(rdDecrease);
    panelChangeMode.add(rdDecrease);

    rdSet = new JRadioButton(S.WAREHOUSE_CHANGE_MODE_SET.s());
    rdSet.addItemListener(this);
    buttonGroupChange.add(rdSet);
    panelChangeMode.add(rdSet);

    final JPanel panelChangeReason = new JPanel();
    sl_panelChange.putConstraint(SpringLayout.NORTH, panelChangeReason, 4,
        SpringLayout.SOUTH, panelChangeMode);
    sl_panelChange.putConstraint(SpringLayout.WEST, panelChangeReason, 0,
        SpringLayout.WEST, lblTitle);
    add(panelChangeReason);
    panelChangeReason.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));

    final JLabel lblReason = new JLabel(S.WAREHOUSE_CHANGE_REASON.s());
    panelChangeReason.add(lblReason);
    lblReason.setFont(lblReason.getFont().deriveFont(
        lblReason.getFont().getStyle() | Font.BOLD));
    sl_panelChange.putConstraint(SpringLayout.SOUTH, lblReason, 0,
        SpringLayout.SOUTH, panelChangeReason);

    rdReasons = new JRadioButton[WarehouseChangeReasons.MAX_REASONS_PER_TYPE];
    for (int i = 0; i < rdReasons.length; i++) {
      rdReasons[i] = new JRadioButton();
      buttonGroupReason.add(rdReasons[i]);
      panelChangeReason.add(rdReasons[i]);
    }

    final JPanel panelChangeQuantity = new JPanel();
    sl_panelChange.putConstraint(SpringLayout.NORTH, panelChangeQuantity, 10,
        SpringLayout.SOUTH, panelChangeReason);
    sl_panelChange.putConstraint(SpringLayout.WEST, panelChangeQuantity, 0,
        SpringLayout.WEST, lblTitle);
    sl_panelChange.putConstraint(SpringLayout.EAST, panelChangeQuantity, 100,
        SpringLayout.WEST, lblTitle);
    this.add(panelChangeQuantity);
    panelChangeQuantity.setLayout(new BorderLayout(0, 0));

    final JLabel lblQuantity = new JLabel(S.WAREHOUSE_CHANGE_QUANTITY.s());
    panelChangeQuantity.add(lblQuantity, BorderLayout.NORTH);
    lblQuantity.setFont(lblQuantity.getFont().deriveFont(
        lblQuantity.getFont().getStyle() | Font.BOLD));
    sl_panelChange.putConstraint(SpringLayout.NORTH, lblQuantity, 51,
        SpringLayout.NORTH, this);
    sl_panelChange.putConstraint(SpringLayout.WEST, lblQuantity, 155,
        SpringLayout.WEST, this);

    panelChangeQuantity.add(txtQuantity);
    sl_panelChange.putConstraint(SpringLayout.NORTH, txtQuantity, 48,
        SpringLayout.NORTH, this);
    sl_panelChange.putConstraint(SpringLayout.WEST, txtQuantity, 188,
        SpringLayout.WEST, this);
    txtQuantity.setColumns(10);

    final JPanel panelChangeComments = new JPanel();
    sl_panelChange.putConstraint(SpringLayout.EAST, panelChangeComments, -5,
        SpringLayout.EAST, this);

    panelNegativeError = new JPanel();
    sl_panelChange.putConstraint(SpringLayout.NORTH, panelNegativeError, 0,
        SpringLayout.NORTH, panelChangeQuantity);
    sl_panelChange.putConstraint(SpringLayout.NORTH, panelChangeComments, 0,
        SpringLayout.NORTH, panelNegativeError);
    sl_panelChange.putConstraint(SpringLayout.WEST, panelChangeComments, 5,
        SpringLayout.EAST, panelNegativeError);
    sl_panelChange.putConstraint(SpringLayout.WEST, panelNegativeError, 5,
        SpringLayout.EAST, panelChangeQuantity);
    sl_panelChange.putConstraint(SpringLayout.SOUTH, panelNegativeError, 0,
        SpringLayout.SOUTH, panelChangeQuantity);
    sl_panelChange.putConstraint(SpringLayout.EAST, panelNegativeError, 155,
        SpringLayout.EAST, panelChangeQuantity);
    panelNegativeError.setVisible(false);
    panelNegativeError.setBorder(new LineBorder(new Color(0, 0, 0)));
    panelNegativeError.setBackground(Color.PINK);
    add(panelNegativeError);
    panelNegativeError.setLayout(new GridLayout(0, 1, 0, 0));

    final JLabel lblNegativeError1 = new JLabel(
        S.WAREHOUSE_CHANGE_ERROR_NEGATIVE_1.s());
    panelNegativeError.add(lblNegativeError1);
    lblNegativeError1.setFont(lblNegativeError1.getFont().deriveFont(
        lblNegativeError1.getFont().getStyle() | Font.BOLD));
    lblNegativeError1.setHorizontalAlignment(SwingConstants.CENTER);
    sl_panelChange.putConstraint(SpringLayout.NORTH, lblNegativeError1, 0,
        SpringLayout.NORTH, panelChangeQuantity);
    sl_panelChange.putConstraint(SpringLayout.WEST, lblNegativeError1, 5,
        SpringLayout.EAST, panelChangeQuantity);

    final JLabel lblNegativeError2 = new JLabel(
        S.WAREHOUSE_CHANGE_ERROR_NEGATIVE_2.s());
    lblNegativeError2.setHorizontalAlignment(SwingConstants.CENTER);
    panelNegativeError.add(lblNegativeError2);
    sl_panelChange.putConstraint(SpringLayout.SOUTH, lblNegativeError2, -13,
        SpringLayout.NORTH, panelChangeComments);
    this.add(panelChangeComments);
    panelChangeComments.setLayout(new BorderLayout(0, 0));

    final JLabel lblComments = new JLabel(S.WAREHOUSE_CHANGE_COMMENTS.s());
    lblComments.setFont(lblComments.getFont().deriveFont(
        lblComments.getFont().getStyle() | Font.BOLD));
    panelChangeComments.add(lblComments, BorderLayout.NORTH);

    txtComments = new JTextField();
    panelChangeComments.add(txtComments, BorderLayout.SOUTH);
    txtComments.setColumns(10);

    txtQuantity.addKeyListener(this);
    txtComments.addKeyListener(this);

    final JPanel panelButtons = new JPanel();
    sl_panelChange.putConstraint(SpringLayout.NORTH, panelButtons, 6,
        SpringLayout.SOUTH, panelChangeComments);
    sl_panelChange.putConstraint(SpringLayout.WEST, panelButtons, 0,
        SpringLayout.WEST, lblTitle);
    sl_panelChange.putConstraint(SpringLayout.EAST, panelButtons, 0,
        SpringLayout.EAST, lblTitle);
    add(panelButtons);

    final JPanel panelHistoryLog = new JPanel();
    sl_panelChange.putConstraint(SpringLayout.NORTH, panelHistoryLog, 14,
        SpringLayout.SOUTH, panelButtons);
    sl_panelChange.putConstraint(SpringLayout.WEST, panelHistoryLog, 0,
        SpringLayout.WEST, lblTitle);
    sl_panelChange.putConstraint(SpringLayout.SOUTH, panelHistoryLog, -5,
        SpringLayout.SOUTH, this);
    sl_panelChange.putConstraint(SpringLayout.EAST, panelHistoryLog, 0,
        SpringLayout.EAST, lblTitle);
    add(panelHistoryLog);
    panelHistoryLog.setLayout(new BorderLayout(0, 0));

    final JLabel lblHistorico = new JLabel(S.WAREHOUSE_HISTORY.s());
    lblHistorico.setFont(lblHistorico.getFont().deriveFont(
        lblHistorico.getFont().getStyle() | Font.BOLD));
    panelHistoryLog.add(lblHistorico, BorderLayout.NORTH);

    final JScrollPane scrollPane = new JScrollPane();
    panelHistoryLog.add(scrollPane, BorderLayout.CENTER);

    changeLogTable = new WarehouseLogTable(this);
    scrollPane.setViewportView(changeLogTable);
    panelButtons.setLayout(new GridLayout(1, 0));

    final JButton btnUp = new JButton(S.WAREHOUSE_CHANGE_BUTTON_UP.s());
    panelButtons.add(btnUp);
    sl_panelChange.putConstraint(SpringLayout.WEST, btnUp, 5,
        SpringLayout.WEST, this);
    sl_panelChange.putConstraint(SpringLayout.EAST, btnUp, 125,
        SpringLayout.WEST, this);
    btnUp.addActionListener(this);
    btnUp.setActionCommand("up");
    sl_panelChange.putConstraint(SpringLayout.NORTH, btnUp, 7,
        SpringLayout.SOUTH, panelChangeComments);

    final JButton btnDown = new JButton(S.WAREHOUSE_CHANGE_BUTTON_DOWN.s());
    panelButtons.add(btnDown);
    btnDown.addActionListener(this);
    btnDown.setActionCommand("down");
    sl_panelChange.putConstraint(SpringLayout.WEST, btnDown, 5,
        SpringLayout.EAST, btnUp);
    sl_panelChange.putConstraint(SpringLayout.EAST, btnDown, 125,
        SpringLayout.EAST, btnUp);
    sl_panelChange.putConstraint(SpringLayout.NORTH, btnDown, 0,
        SpringLayout.NORTH, btnUp);

    final JButton btnCommit = new JButton(S.WAREHOUSE_CHANGE_BUTTON_SAVE.s());
    panelButtons.add(btnCommit);
    btnCommit.addActionListener(this);
    btnCommit.setActionCommand("commit");
    sl_panelChange.putConstraint(SpringLayout.NORTH, btnCommit, 0,
        SpringLayout.NORTH, btnUp);
    sl_panelChange.putConstraint(SpringLayout.WEST, btnCommit, 5,
        SpringLayout.EAST, btnDown);
    sl_panelChange.putConstraint(SpringLayout.EAST, btnCommit, 125,
        SpringLayout.EAST, btnDown);

    final JButton btnCancel = new JButton(S.WAREHOUSE_CHANGE_BUTTON_CANCEL.s());
    panelButtons.add(btnCancel);
    btnCancel.addActionListener(this);
    btnCancel.setActionCommand("cancel");
    sl_panelChange.putConstraint(SpringLayout.NORTH, btnCancel, 0,
        SpringLayout.NORTH, btnUp);
    sl_panelChange.putConstraint(SpringLayout.WEST, btnCancel, 5,
        SpringLayout.EAST, btnCommit);
    sl_panelChange.putConstraint(SpringLayout.EAST, btnCancel, 125,
        SpringLayout.EAST, btnCommit);

    final JButton btnReport = new JButton(S.WAREHOUSE_REPORT.s());
    panelButtons.add(btnReport);
    sl_panelChange.putConstraint(SpringLayout.NORTH, btnReport, 4,
        SpringLayout.SOUTH, btnUp);
    sl_panelChange.putConstraint(SpringLayout.WEST, btnReport, 0,
        SpringLayout.WEST, btnUp);
    sl_panelChange.putConstraint(SpringLayout.EAST, btnReport, 0,
        SpringLayout.EAST, btnUp);
    btnReport.setActionCommand("report");
    btnReport.addActionListener(this);

    setItem(null);
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
    } else if ("report".equals(cmd)) {
      report();
    } else {
      logger.warn("Invalid action command: " + cmd);
    }
  }

  private void cancelEditing() {
    setItem(warehouseTable.getSelected());
  }

  private boolean changed() {
    if (txtQuantity.getValue(true) != null) {
      return true;
    }
    if (StringUtils.trim(txtComments.getText()).length() != 0) {
      return true;
    }

    return false;
  }

  public boolean changeMode(final char c) {
    switch (c) {
      case '+':
        rdIncrease.setSelected(true);
        return true;
      case '-':
        rdDecrease.setSelected(true);
        return true;
      case '=':
        rdSet.setSelected(true);
        return true;
    }
    return false;
  }

  public boolean changeReason(char c) {
    c = Character.toUpperCase(c);
    for (final JRadioButton rb : rdReasons) {
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
      if (changed()) {
        final int id = warehouseTable.getSelected().id;
        final char tipo = getMode();
        final int idmotivo = getReasonId();
        final float quantidade = txtQuantity.getValue(false).floatValue();
        final String observacoes = StringUtils.trim(txtComments.getText());
        Client
            .connection()
            .warehouse()
            .requestQuantityChange(
                new WarehouseQuantityChange(id, tipo, idmotivo, quantidade,
                    observacoes));
        Client.connection().warehouse().requestChangeLogByItem(id);
      }
      cancelEditing();
    } catch (final IOException e) {
      logger.warn(e, e);
    }
  }

  @Override
  public void delayedAction(final WarehouseItem actionItem) {
    try {
      final WarehouseItem selectedItem = warehouseTable.getSelected();
      if ((selectedItem == null) || (actionItem == null)) {
        return;
      }
      if (selectedItem.id == actionItem.id) {
        Client.connection().warehouse().requestChangeLogByItem(actionItem.id);
      }
    } catch (final IOException e) {
      logger.warn(e, e);
    }
  }

  private char getMode() {
    if (rdIncrease.isSelected()) {
      return '+';
    }
    if (rdDecrease.isSelected()) {
      return '-';
    }
    return '=';
  }

  public String getReason(final char tipo, final int idmotivo) {
    return reasons.get(tipo, idmotivo);
  }

  private int getReasonId() {
    for (int i = 0; i < rdReasons.length; i++) {
      if (rdReasons[i].isSelected()) {
        return i + 1;
      }
    }
    return 0;
  }

  @Override
  public void itemStateChanged(final ItemEvent e) {
    updateReasons();
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
    warehouseTable.selectNext();
  }

  private void moveUp() {
    commitEditing();
    warehouseTable.selectPrevious();
  }

  public boolean quantityTyped(final float qtd) {
    final WarehouseItem ci = warehouseTable.getSelected();
    if ((getMode() != '-') || (ci == null)) {
      panelNegativeError.setVisible(false);
      return true;
    }
    final float qtd_item = ci.getQuantity();
    final boolean negativo = (qtd_item - qtd) < 0;
    panelNegativeError.setVisible(negativo);
    return !negativo;
  }

  private void report() {
    try {
      Client.connection().warehouse()
          .requestItemReport(warehouseTable.getSelected().id);
    } catch (final IOException e) {
      logger.warn(e, e);
    }
  }

  public void setChangeLog(final WarehouseChangeLog log) {
    final WarehouseItem selectedItem = warehouseTable.getSelected();
    if ((log == null) || (selectedItem == null)
        || (log.iditem != selectedItem.id)) {
      changeLogTable.set(new WarehouseChangeLog(0, 0));
    } else {
      changeLogTable.set(log);
    }
  }

  public void setItem(final WarehouseItem i) {
    synchronized (this) {
      actionDelay.prepare(i);
      panelNegativeError.setVisible(false);
      final boolean ok = i != null;
      setEnabled(ok);
      lblTitle.setText(ok ? i.name : S.WAREHOUSE_SELECT_ITEM.s());
      rdSet.setSelected(true);
      rdReasons[3].setSelected(true);
      txtQuantity.setValue(null);
      txtComments.setText("");

      rdSet.setEnabled(ok);
      rdIncrease.setEnabled(ok);
      rdDecrease.setEnabled(ok);
      for (final JRadioButton rb : rdReasons) {
        rb.setEnabled(ok && !rb.getText().equals(BLANK_REASON));
      }
      txtQuantity.setEnabled(ok);
      txtComments.setEnabled(ok);

      txtQuantity.requestFocus();
      changeLogTable.getModel().setData(null);
    }
  }

  public void setReasons(final WarehouseChangeReasons reasons) {
    this.reasons = reasons;
    rdSet.setSelected(true);
    updateReasons();
  }

  private void updateReasons() {
    final WarehouseChangeReason[] ms = reasons.getReasonsForMode(getMode());
    for (int i = 0; i < WarehouseChangeReasons.MAX_REASONS_PER_TYPE; i++) {
      final WarehouseChangeReason m = ms[i];
      rdReasons[i].setText(m == null ? BLANK_REASON : m.description);
      rdReasons[i].setEnabled(isEnabled() && (m != null));
    }
    rdReasons[rdReasons.length - 1].setSelected(true);
  }
}
