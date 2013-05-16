package net.geral.essomerie.client.gui.messages;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import net.geral.essomerie._shared.User;
import net.geral.essomerie.client._gui.shared.label.TitleLabel;
import net.geral.essomerie.client.core.Client;
import net.geral.essomerie.client.gui.main.TabPanel;
import net.geral.essomerie.client.resources.S;
import net.geral.gui.button.ActionButton;
import net.geral.lib.strings.GNStrings;

import org.apache.log4j.Logger;

public class NewMessageTabPanel extends TabPanel implements ActionListener,
    DocumentListener {
  private static final Logger logger           = Logger
                                                   .getLogger(NewMessageTabPanel.class);
  private static final long   serialVersionUID = 1L;
  private final JPanel        panelToGrid;
  private final JCheckBox     cbBroadcast;
  private JCheckBox[]         cbTos;
  private User[]              users;
  private final ActionButton  btnCancel;
  private final ActionButton  btnSend;
  private final JTextArea     txtMessage;

  public NewMessageTabPanel() {
    setLayout(new BorderLayout(0, 0));
    final TitleLabel lblTitle = new TitleLabel(S.MESSAGES_NEW_TITLE.s());
    add(lblTitle, BorderLayout.NORTH);

    final JPanel panelToFlow = new JPanel();
    final JScrollPane scrollTo = new JScrollPane(panelToFlow);
    final FlowLayout fl_panelToFlow = new FlowLayout(FlowLayout.CENTER, 5, 5);
    panelToFlow.setLayout(fl_panelToFlow);

    panelToGrid = new JPanel();
    panelToFlow.add(panelToGrid);
    panelToGrid.setLayout(new GridLayout(0, 1, 0, 0));

    final JLabel lblTo = new JLabel(S.MESSAGES_NEW_TO.s());
    lblTo.setHorizontalAlignment(SwingConstants.CENTER);
    lblTo
        .setFont(lblTo.getFont().deriveFont(
            lblTo.getFont().getStyle() | Font.BOLD,
            lblTo.getFont().getSize() + 1f));
    panelToGrid.add(lblTo);

    cbBroadcast = new JCheckBox(S.MESSAGES_NEW_TO_BROADCAST.s());
    cbBroadcast.addActionListener(this);

    final JLabel lblSpacer1 = new JLabel("");
    panelToGrid.add(lblSpacer1);
    cbBroadcast.setFont(cbBroadcast.getFont().deriveFont(
        cbBroadcast.getFont().getStyle() | Font.BOLD));
    panelToGrid.add(cbBroadcast);

    final JLabel lblSpacer2 = new JLabel("");
    panelToGrid.add(lblSpacer2);

    add(scrollTo, BorderLayout.WEST);

    final JScrollPane scrollMessage = new JScrollPane();
    add(scrollMessage, BorderLayout.CENTER);

    txtMessage = new JTextArea();
    txtMessage.getDocument().addDocumentListener(this);
    scrollMessage.setViewportView(txtMessage);

    final JPanel panelButtons = new JPanel();
    final FlowLayout flowLayout = (FlowLayout) panelButtons.getLayout();
    flowLayout.setAlignment(FlowLayout.TRAILING);
    add(panelButtons, BorderLayout.SOUTH);

    final JPanel panel = new JPanel();
    panelButtons.add(panel);
    panel.setLayout(new GridLayout(1, 0, 0, 0));

    btnSend = new ActionButton(S.BUTTON_SEND.s(), KeyEvent.VK_ENTER,
        InputEvent.CTRL_MASK, S.BUTTON_SEND.name());
    panel.add(btnSend);

    btnCancel = new ActionButton(S.BUTTON_CANCEL.s(), KeyEvent.VK_ESCAPE, 0,
        S.BUTTON_CANCEL.name());
    panel.add(btnCancel);
    btnCancel.addActionListener(this);
    btnSend.addActionListener(this);

    btnSend.setEnabled(false); // message starts empty, cannot send
  }

  private void actionBroadcast() {
    final boolean sel = !cbBroadcast.isSelected();
    for (final JCheckBox cb : cbTos) {
      cb.setEnabled(sel);
    }
    updateSendEnabled();
  }

  @Override
  public void actionPerformed(final ActionEvent e) {
    final Object src = e.getSource();

    if (src == cbBroadcast) {
      actionBroadcast();
    } else if (src == btnCancel) {
      close(false);
    } else if (src == btnSend) {
      send();
    } else if (src instanceof JCheckBox) {
      updateSendEnabled();
    } else {
      logger.warn("Invalid source: " + src, new Exception());
    }
  }

  @Override
  public void changedUpdate(final DocumentEvent e) {
    updateSendEnabled();
  }

  @Override
  public String getTabText() {
    return S.MESSAGES_NEW_TITLE.s();
  }

  private int[] getTos() {
    if (cbBroadcast.isSelected()) {
      return new int[] { 0 };
    }

    int n = 0;
    for (final JCheckBox cb : cbTos) {
      if (cb.isSelected()) {
        n++;
      }
    }

    final int[] ds = new int[n];
    int d = 0;
    for (int i = 0; i < cbTos.length; i++) {
      if (cbTos[i].isSelected()) {
        ds[d] = users[i].getId();
        d++;
      }
    }

    return ds;
  }

  private boolean hasTo() {
    if (cbBroadcast.isSelected()) {
      return true;
    }

    for (final JCheckBox d : cbTos) {
      if (d.isSelected()) {
        return true;
      }
    }

    return false;
  }

  @Override
  public void insertUpdate(final DocumentEvent e) {
    updateSendEnabled();
  }

  private boolean messageIsEmpty() {
    return (GNStrings.trim(txtMessage.getText()).length() == 0);
  }

  private boolean podeEnviar() {
    if (!hasTo()) {
      return false;
    }
    if (messageIsEmpty()) {
      return false;
    }
    return true;
  }

  @Override
  public void removeUpdate(final DocumentEvent e) {
    updateSendEnabled();
  }

  private void send() {
    try {
      final int[] to = getTos();
      final String msg = GNStrings.trim(txtMessage.getText());
      Client.connection().messages().requestSend(to, msg);
    } catch (final Exception e) {
      logger.warn(e, e);
    }
    close(true);
  }

  @Override
  public void tabClosed() {
  }

  @Override
  public boolean tabCloseRequest() {
    if (messageIsEmpty()) {
      return true;
    }

    final int i = JOptionPane.showConfirmDialog(this, S.MESSAGES_NEW_CANCEL,
        S.TITLE_CONFIRM.s(), JOptionPane.YES_NO_OPTION);
    return (i == JOptionPane.YES_OPTION);
  }

  @Override
  public void tabCreated() {
    users = Client.cache().users().getAll();
    cbTos = new JCheckBox[users.length];
    for (int i = 0; i < cbTos.length; i++) {
      cbTos[i] = new JCheckBox(users[i].getName());
      cbTos[i].addActionListener(this);
      panelToGrid.add(cbTos[i]);
    }
  }

  private void updateSendEnabled() {
    btnSend.setEnabled(podeEnviar());
  }
}
