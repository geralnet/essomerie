package net.geral.essomerie.client.gui.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import net.geral.essomerie.client.core.Client;
import net.geral.essomerie.client.core.events.Events;
import net.geral.essomerie.client.core.events.listeners.CommConfirmationListener;
import net.geral.essomerie.client.core.events.listeners.SystemListener;
import net.geral.essomerie.client.resources.S;
import net.geral.essomerie.shared.BuildInfo;
import net.geral.essomerie.shared.communication.MessageData;

import org.apache.log4j.Logger;

public class StatusBar extends JPanel implements SystemListener,
    CommConfirmationListener {
  private static final long            serialVersionUID = 1L;
  private static final int             BORDER_SPACING   = 5;

  private static final Logger          logger           = Logger
                                                            .getLogger(StatusBar.class);

  private final JLabel                 lblUser;
  private final JLabel                 lblStatus;
  private final JLabel                 lblLag;
  private final ArrayList<MessageData> status           = new ArrayList<>();

  public StatusBar() {
    setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
    setLayout(new BorderLayout(0, 0));

    lblUser = new JLabel("-");
    lblUser.setBorder(new CompoundBorder(new MatteBorder(0, 0, 0, 1, new Color(
        0, 0, 0)), new EmptyBorder(0, BORDER_SPACING, 0, BORDER_SPACING)));
    add(lblUser, BorderLayout.WEST);

    lblStatus = new JLabel();
    lblStatus.setBorder(new EmptyBorder(0, BORDER_SPACING, 0, BORDER_SPACING));
    lblStatus.setForeground(Color.RED);
    add(lblStatus);

    lblLag = new JLabel("-");
    lblLag.setBorder(new CompoundBorder(new MatteBorder(0, 1, 0, 0, new Color(
        0, 0, 0)), new EmptyBorder(0, BORDER_SPACING, 0, BORDER_SPACING)));
    add(lblLag, BorderLayout.EAST);

    Events.system().addListener(this);
    Events.comm().addListener(this);
  }

  private void clear() {
    status.clear();
    updateStatus();
  }

  @Override
  public void commConfirm(final long messageId) {
    if (status.size() > 0) {
      final MessageData md = status.remove(0);
      if (md.getId() != messageId) {
        logger.warn("Invalid id of confirmation (expected=" + md.getId()
            + ";received=" + messageId + ")");
        status.clear(); // inconsistent, clear all
      }
    } else {
      logger.warn("System Processed, no message in queue.");
    }
    updateStatus();
  }

  private void connected() {
    lblUser.setText(Client.cache().users().getLogged().getUsername()
        .toUpperCase());
  }

  private void disconnected() {
    clear();
    lblUser.setText("-");
  }

  @Override
  public void systemConnected() {
  }

  @Override
  public void systemConnecting(final String serverAddress, final int serverPort) {
  }

  @Override
  public void systemConnectionFailed(final boolean willTryAgain) {
    disconnected();
  }

  @Override
  public void systemConnectionTryAgainCountdown(final int tryAgainCountdown) {
  }

  @Override
  public void systemInformSent(final MessageData md) {
    status.add(md);
    updateStatus();
  }

  @Override
  public void systemLoggedOut() {
    disconnected();
  }

  @Override
  public void systemLoginAccepted() {
    connected();
  }

  @Override
  public void systemLoginFailed() {
    disconnected();
  }

  @Override
  public void systemPongReceived(final long lag) {
    lblLag.setText(lag + "ms");
  }

  @Override
  public void systemVersionReceived(final BuildInfo version) {
  }

  private void updateStatus() {
    final int n = status.size();
    if (n == 0) {
      lblStatus.setText("");
    } else {
      final String prefix = (n > 1) ? "(" + n + ") " : "";
      final MessageData first = status.get(0);
      lblStatus.setText(prefix + S.msg(first));
    }
  }
}
