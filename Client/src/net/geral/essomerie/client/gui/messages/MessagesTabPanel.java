package net.geral.essomerie.client.gui.messages;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.print.PrinterException;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.geral.essomerie._shared.mensagens.Message;
import net.geral.essomerie.client._gui.shared.label.ErroLabel;
import net.geral.essomerie.client._gui.shared.label.TitleLabel;
import net.geral.essomerie.client._printing.messages_MensagemPrint;
import net.geral.essomerie.client.core.Client;
import net.geral.essomerie.client.core.events.Events;
import net.geral.essomerie.client.core.events.listeners.MessagesListener;
import net.geral.essomerie.client.gui.main.TabPanel;
import net.geral.essomerie.client.gui.messages.table.MessagesTable;
import net.geral.essomerie.client.resources.S;
import net.geral.gui.button.ActionButton;
import net.geral.printing.BematechDocument;
import net.geral.printing.PrintSupport;

import org.apache.log4j.Logger;
import org.joda.time.format.DateTimeFormat;

public class MessagesTabPanel extends TabPanel implements MessagesListener,
    ActionListener, ListSelectionListener {
  private static final long   serialVersionUID = 1L;
  private static final Logger logger           = Logger
                                                   .getLogger(MessagesTabPanel.class);

  private final JLabel        lblFrom;
  private final JTextArea     txtMessage;
  private final ActionButton  btnDelete;
  private final ActionButton  btnNew;
  private final JLabel        lblDate;
  private final JPanel        panelHeader;
  private final JPanel        panelButtonsLeft;
  private final JPanel        panelButtonsRight;
  private final JLabel        lblLegend;
  private final JLabel        lblInRedBroadcasts;
  private final MessagesTable messagesTable    = new MessagesTable(this);
  private final TitleLabel    lblMessages;
  private final ErroLabel     lblError;
  private final JPanel        panel;
  private final ActionButton  btnPrint;

  public MessagesTabPanel() {
    setLayout(new BorderLayout(0, 0));

    lblMessages = new TitleLabel(S.MESSAGES.s());
    lblMessages.setText(S.MESSAGES_LOADING.s());
    add(lblMessages, BorderLayout.NORTH);

    lblError = new ErroLabel("[...]");

    final JSplitPane splitPane = new JSplitPane();
    splitPane.setResizeWeight(0.5);
    splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
    add(splitPane, BorderLayout.CENTER);

    messagesTable.getSelectionModel().addListSelectionListener(this);
    splitPane.setLeftComponent(messagesTable.getScroll());

    final JPanel panelBottom = new JPanel();
    splitPane.setRightComponent(panelBottom);
    panelBottom.setLayout(new BorderLayout(0, 0));

    final JScrollPane scrollMessage = new JScrollPane();
    panelBottom.add(scrollMessage, BorderLayout.CENTER);

    txtMessage = new JTextArea();
    txtMessage.setEditable(false);
    txtMessage.setLineWrap(true);
    scrollMessage.setViewportView(txtMessage);

    panelHeader = new JPanel();
    final FlowLayout fl_panelHeader = (FlowLayout) panelHeader.getLayout();
    fl_panelHeader.setAlignment(FlowLayout.LEADING);
    fl_panelHeader.setVgap(0);
    fl_panelHeader.setHgap(4);
    scrollMessage.setColumnHeaderView(panelHeader);

    lblFrom = new JLabel("...");
    lblFrom.setFont(lblFrom.getFont().deriveFont(
        lblFrom.getFont().getStyle() | Font.BOLD));
    panelHeader.add(lblFrom);

    final JLabel lblAt = new JLabel(S.MESSAGES_AT.s());
    panelHeader.add(lblAt);

    lblDate = new JLabel("...");
    lblDate.setFont(lblDate.getFont().deriveFont(
        lblDate.getFont().getStyle() | Font.BOLD));
    panelHeader.add(lblDate);

    final JPanel panelButtons = new JPanel();
    panelBottom.add(panelButtons, BorderLayout.SOUTH);
    panelButtons.setLayout(new BorderLayout(0, 0));

    panelButtonsLeft = new JPanel();
    panelButtonsLeft.setBorder(new EmptyBorder(0, 2, 0, 0));
    panelButtons.add(panelButtonsLeft, BorderLayout.WEST);
    panelButtonsLeft.setLayout(new BorderLayout(0, 0));

    lblLegend = new JLabel(S.MESSAGES_LEGEND.s());
    lblLegend.setFont(lblLegend.getFont().deriveFont(
        lblLegend.getFont().getStyle() | Font.BOLD));
    panelButtonsLeft.add(lblLegend, BorderLayout.NORTH);

    lblInRedBroadcasts = new JLabel(S.MESSAGES_LEGEND_CONTENTS.s());
    panelButtonsLeft.add(lblInRedBroadcasts, BorderLayout.CENTER);

    panelButtonsRight = new JPanel();
    panelButtons.add(panelButtonsRight, BorderLayout.EAST);
    panelButtonsRight.setLayout(new BorderLayout(0, 0));

    panel = new JPanel();
    panelButtonsRight.add(panel, BorderLayout.SOUTH);
    panel.setLayout(new GridLayout(1, 0, 0, 0));

    btnDelete = new ActionButton(S.MESSAGES_BUTTON_DELETE.s(),
        KeyEvent.VK_DELETE, 0, "delete");
    btnDelete.addActionListener(this);

    btnPrint = new ActionButton(S.MESSAGES_BUTTON_PRINT.s(), 'P', "print");
    btnPrint.setEnabled(false);
    btnPrint.addActionListener(this);
    panel.add(btnPrint);
    panel.add(btnDelete);

    btnNew = new ActionButton(S.MESSAGES_BUTTON_NEW.s(), 'N', "new");
    btnNew.addActionListener(this);
    panel.add(btnNew);
    selectionChanged();
  }

  public void actionDelete() {
    final int n = messagesTable.getSelectedRowCount();
    if (n == 0) {
      return;
    }
    if (n > 1) {
      final int i = JOptionPane.showConfirmDialog(this,
          S.MESSAGES_CONFIRM.s(n), S.TITLE_CONFIRM.s(),
          JOptionPane.YES_NO_OPTION);
      if (i != JOptionPane.YES_OPTION) {
        return;
      }
    }
    final int[] ids = messagesTable.getSelectedIds();
    try {
      Client.connection().messages().requestDelete(ids);
    } catch (final IOException e) {
      logger.warn("Cannot request delete.", e);
    }
  }

  public void actionNew() {
    Client.window().openTab(NewMessageTabPanel.class);
  }

  @Override
  public void actionPerformed(final ActionEvent e) {
    final String cmd = e.getActionCommand();
    if ("new".equals(cmd)) {
      actionNew();
    } else if ("delete".equals(cmd)) {
      actionDelete();
    } else if ("print".equals(cmd)) {
      actionPrint();
    } else {
      logger.warn("Invalid Command: " + cmd, new Exception());
    }
  }

  private void actionPrint() {
    final Message m = messagesTable.getSelected();
    if (m == null) {
      return;
    }
    final BematechDocument mp = new messages_MensagemPrint(m);
    try {
      PrintSupport.print(mp);
    } catch (final PrinterException e) {
      logger.warn("Cannot request print.", e);
    }
  }

  @Override
  public String getTabText() {
    return S.MESSAGES.s();
  }

  @Override
  public void messageCacheReloaded(final boolean hasUnread) {
    updateTitle();
    messagesTable.getModel().refresh();
  }

  @Override
  public void messageDeleted(final int[] ids, final boolean hasMoreUnread) {
    messagesTable.getModel().remove(ids);
    updateTitle();
  }

  @Override
  public void messageRead(final int idmsg, final boolean hasMoreUnread) {
    messagesTable.getModel().read(idmsg);
  }

  @Override
  public void messageReceived(final Message m) {
    messagesTable.getModel().add(m);
    updateTitle();
  }

  @Override
  public void messageSent() {
  }

  private void selectedMany(final int n) {
    lblFrom.setText("");
    lblDate.setText("");
    panelHeader.setVisible(false);
    txtMessage.setText(String.format(S.MESSAGE_SELECTED_N.s(), n));
  }

  private void selectedNone() {
    lblFrom.setText("");
    lblDate.setText("");
    panelHeader.setVisible(false);
    txtMessage.setText(S.MESSAGE_SELECTED_NONE.s());
  }

  private void selectedOne(final Message m) {
    if (m == null) {
      selectedNone();
      return;
    }
    lblFrom.setText(" " + Client.cache().users().get(m.getFrom()).getName());
    txtMessage.setText(m.getMessage());
    lblDate.setText(" "
        + m.getSent().toString(
            DateTimeFormat.forPattern(S.FORMAT_DATETIME_SIMPLE.s())));
    panelHeader.setVisible(true);

    if (!m.isRead(Client.cache().users().getLogged().getId())) {
      try {
        Client.connection().messages().requestRead(m.getId());
      } catch (final IOException e) {
        logger.warn("Cannot request to set read.", e);
      }
    }
  }

  public void selectionChanged() {
    final int n = messagesTable.getSelectedRowCount();
    btnDelete.setEnabled(n > 0);
    btnPrint.setEnabled(n == 1);

    if (n == 0) {
      selectedNone();
    } else if (n == 1) {
      selectedOne(messagesTable.getSelected());
    } else {
      selectedMany(n);
    }
  }

  private void showTitle(final JLabel show, final JLabel hide) {
    remove(hide);
    add(show, BorderLayout.NORTH);
    revalidate();
    repaint();
  }

  @Override
  public void tabClosed() {
    Events.messages().removeListener(this);
  }

  @Override
  public boolean tabCloseRequest() {
    return true;
  }

  @Override
  public void tabCreated() {
    Events.messages().addListener(this);
    try {
      Client.connection().messages().requestMessagesToUser();
    } catch (final IOException e) {
      logger.warn("Cannot request messages.", e);
    }
  }

  private void updateTitle() {
    final int n = Client.cache().messages().count();
    if (n < Client.config().MessagesInboxLimit) {
      if (n == 0) {
        lblMessages.setText(S.MESSAGES_NO_MESSAGES.s());
      } else if (n == 1) {
        lblMessages.setText(S.MESSAGES_ONE_MESSAGE.s());
      } else {
        lblMessages.setText(String.format(S.MESSAGE_N_MESSAGES.s(), n));
      }
      showTitle(lblMessages, lblError);
    } else {
      lblError.setText(String.format(S.MESSAGE_FULL.s(), n,
          Client.config().MessagesInboxLimit));
      showTitle(lblError, lblMessages);
    }
  }

  @Override
  public void valueChanged(final ListSelectionEvent e) {
    if (!e.getValueIsAdjusting()) {
      selectionChanged();
    }
  }
}
