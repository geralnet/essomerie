package net.geral.essomerie.client.gui.connection;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import net.geral.essomerie._shared.BuildInfo;
import net.geral.essomerie._shared.User;
import net.geral.essomerie._shared.communication.MessageData;
import net.geral.essomerie.client.core.Client;
import net.geral.essomerie.client.core.events.Events;
import net.geral.essomerie.client.core.events.listeners.SystemListener;
import net.geral.essomerie.client.core.events.listeners.UsersListener;
import net.geral.essomerie.client.resources.IMG;
import net.geral.essomerie.client.resources.S;
import net.geral.lib.edt.Edt;

import org.apache.log4j.Logger;

public class ConnectionDialog extends JDialog implements ActionListener,
    UsersListener, SystemListener {
  private static final Logger logger           = Logger
                                                   .getLogger(ConnectionDialog.class);
  private static final long   serialVersionUID = 1L;
  private final JPanel        contentPanel     = new JPanel();
  private JComboBox<User>     cbUsername;
  private JPasswordField      txtPassword;
  private JTextArea           textInfo;
  private final CardLayout    cl_centralPanel  = new CardLayout(0, 0);
  private JPanel              centralPanel;
  private JButton             btnEnter;
  private JButton             btnCancel;
  private JPanel              buttonPaneRight;

  /**
   * Create the dialog.
   */
  public ConnectionDialog(final JFrame owner) {
    super(owner);
    Events.users().addListener(this);
    Events.system().addListener(this);
    setModalityType(ModalityType.APPLICATION_MODAL);
    setTitle(S.WINDOW_AUTH_TITLE.s());
    addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(final WindowEvent e) {
        cancel();
      }
    });
    setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    setResizable(false);
    setIconImage(IMG.ICON__APPLICATION.image());
    setBounds(100, 100, 264, 200);
    setLocationRelativeTo(null);
    contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
    setContentPane(contentPanel);
    contentPanel.setLayout(new BorderLayout(0, 0));
    {
      centralPanel = new JPanel();
      contentPanel.add(centralPanel, BorderLayout.CENTER);
      centralPanel.setLayout(cl_centralPanel);
      {
        final JScrollPane scrollPane = new JScrollPane();
        centralPanel.add(scrollPane, "message");
        {
          textInfo = new JTextArea();
          textInfo.setFont(new Font("SansSerif", Font.PLAIN, 10));
          textInfo.setEditable(false);
          scrollPane.setViewportView(textInfo);
        }
      }
      final JPanel credentialsPanel = new JPanel();
      centralPanel.add(credentialsPanel, "credentials");
      final GridBagLayout gbl_credentialsPanel = new GridBagLayout();
      gbl_credentialsPanel.columnWidths = new int[] { 0, 0 };
      gbl_credentialsPanel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0 };
      gbl_credentialsPanel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
      gbl_credentialsPanel.rowWeights = new double[] { 1.0, 0.0, 0.0, 0.0, 0.0,
          1.0, Double.MIN_VALUE };
      credentialsPanel.setLayout(gbl_credentialsPanel);
      {
        final JLabel lblUsername = new JLabel(S.YOUR_NAME.s());
        final GridBagConstraints gbc_lblUsername = new GridBagConstraints();
        gbc_lblUsername.fill = GridBagConstraints.BOTH;
        gbc_lblUsername.insets = new Insets(0, 0, 5, 0);
        gbc_lblUsername.gridx = 0;
        gbc_lblUsername.gridy = 1;
        credentialsPanel.add(lblUsername, gbc_lblUsername);
      }
      {
        cbUsername = new JComboBox<User>();
        final GridBagConstraints gbc_cbUsername = new GridBagConstraints();
        gbc_cbUsername.fill = GridBagConstraints.BOTH;
        gbc_cbUsername.insets = new Insets(0, 0, 5, 0);
        gbc_cbUsername.gridx = 0;
        gbc_cbUsername.gridy = 2;
        credentialsPanel.add(cbUsername, gbc_cbUsername);
      }
      {
        final JLabel lblPassword = new JLabel(S.YOUR_PASSWORD.s());
        final GridBagConstraints gbc_lblPassword = new GridBagConstraints();
        gbc_lblPassword.fill = GridBagConstraints.BOTH;
        gbc_lblPassword.insets = new Insets(0, 0, 5, 0);
        gbc_lblPassword.gridx = 0;
        gbc_lblPassword.gridy = 3;
        credentialsPanel.add(lblPassword, gbc_lblPassword);
      }
      txtPassword = new JPasswordField();
      final GridBagConstraints gbc_txtPassword = new GridBagConstraints();
      gbc_txtPassword.insets = new Insets(0, 0, 5, 0);
      gbc_txtPassword.fill = GridBagConstraints.BOTH;
      gbc_txtPassword.gridx = 0;
      gbc_txtPassword.gridy = 4;
      credentialsPanel.add(txtPassword, gbc_txtPassword);
      txtPassword.addKeyListener(new KeyAdapter() {
        @Override
        public void keyReleased(final KeyEvent e) {
          checkUsernameInPassword();
        }
      });
    }
    {
      {
        {
          final JPanel buttonPane = new JPanel();
          contentPanel.add(buttonPane, BorderLayout.SOUTH);
          final FlowLayout fl_buttonPane = new FlowLayout(FlowLayout.RIGHT);
          fl_buttonPane.setHgap(0);
          buttonPane.setLayout(fl_buttonPane);
          {
            buttonPaneRight = new JPanel();
            buttonPane.add(buttonPaneRight);
            buttonPaneRight.setLayout(new GridLayout(1, 0, 2, 0));
            {
              btnEnter = new JButton(S.BUTTON_LOGIN.s());
              buttonPaneRight.add(btnEnter);
              btnEnter.addActionListener(this);
              btnEnter.setActionCommand("OK");
              getRootPane().setDefaultButton(btnEnter);
            }
            {
              btnCancel = new JButton(S.BUTTON_CANCEL.s());
              buttonPaneRight.add(btnCancel);
              btnCancel.addActionListener(this);
              btnCancel.setActionCommand("CANCEL");
            }
          }
        }
      }
    }
  }

  @Override
  public void actionPerformed(final ActionEvent e) {
    final String action = e.getActionCommand();
    if ("CANCEL".equals(action)) {
      cancel();
      return;
    }
    if ("OK".equals(action)) {
      ok();
      return;
    }
    logger.warn("Invalid action: " + action);
  }

  public void addMessage(final String msg, final boolean newLine) {
    logger.debug("addMessage: " + msg);
    Edt.run(false, new Runnable() {
      @Override
      public void run() {
        final String text = textInfo.getText() + msg + (newLine ? "\n" : " ");
        textInfo.setText(text);
        textInfo.setCaretPosition(text.length());
      }
    });
  }

  private void cancel() {
    final int res = JOptionPane.showConfirmDialog(this,
        S.WINDOW_MAIN_CLOSE_PROMPT, S.TITLE_CONFIRM.s(),
        JOptionPane.YES_NO_OPTION);
    if (res == JOptionPane.YES_OPTION) {
      logger.debug("Exiting at auth screen...");
      System.exit(0);
    }
  }

  private void checkUsernameInPassword() {
    final char[] pw = txtPassword.getPassword();
    if (pw == null) {
      return;
    }

    for (int i = 0; i < pw.length; i++) {
      if (pw[i] == '.') { // found dot, split username part
        selectUser(String.copyValueOf(pw, 0, i));
        if (i == (pw.length - 1)) {
          txtPassword.setText(null);
        } else {
          txtPassword.setText(String.copyValueOf(pw, i + 1, pw.length - i - 1));
        }
        return;
      }
    }
  }

  private void invokeSetVisible(final boolean b) {
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        setVisible(b);
      }
    });
  }

  private void ok() {
    final User login_user = (User) cbUsername.getSelectedItem();
    if (login_user.getId() == -1) {
      JOptionPane.showMessageDialog(this, S.WINDOW_AUTH_INVALID_USER,
          S.TITLE_ERROR.s(), JOptionPane.ERROR_MESSAGE);
      return;
    }

    final char[] password = txtPassword.getPassword();
    if ((password == null) || (password.length == 0)) {
      JOptionPane.showMessageDialog(this, S.WINDOW_AUTH_INVALID_PASSWORD,
          S.TITLE_ERROR.s(), JOptionPane.ERROR_MESSAGE);
      return;
    }

    txtPassword.setText(null);

    addMessage(S.WINDOW_AUTH_LOGGING_IN.s(), true);
    try {
      Client.connection().system().requestLogin(login_user.getId(), password);
    } catch (final IOException e) {
      logger.warn("Cannot authenticate...", e);
    }
    showMessages();
  }

  private void selectUser(final String username) {
    for (int i = 0; i < cbUsername.getItemCount(); i++) {
      final User item = cbUsername.getItemAt(i);
      if (username.equals(item.getUsername())) {
        cbUsername.setSelectedIndex(i);
        return;
      }
    }
  }

  private void showCredentialsInput() {
    cl_centralPanel.show(centralPanel, "credentials");
    txtPassword.requestFocusInWindow();
    btnEnter.setVisible(true);
  }

  private void showMessages() {
    cl_centralPanel.show(centralPanel, "message");
    btnEnter.setVisible(false);
  }

  @Override
  public void systemConnected() {
    addMessage(" " + S.WINDOW_AUTH_CONNECTED, true);
    addMessage(S.WINDOW_AUTH_REQUESTING_USERS.s(), true);
    try {
      Client.connection().system().informVersion();
      Client.connection().users().requestList();
    } catch (final IOException e) {
      logger.warn("Could not inform version and/or request user list.", e);
    }
  }

  @Override
  public void systemConnecting(final String server, final int port) {
    addMessage(String.format(S.WINDOW_AUTH_CONNECTING.s(), server, port), false);
  }

  @Override
  public void systemConnectionFailed(final boolean willTryAgain) {
    addMessage(" " + S.WINDOW_AUTH_CONNECTION_FAILED, true);
    if (willTryAgain) {
      addMessage(S.WINDOW_AUTH_TRYING_AGAIN_IN.s(), true);
    }
  }

  @Override
  public void systemConnectionTryAgainCountdown(final int tryAgainCountdown) {
    if (tryAgainCountdown == 0) {
      addMessage("", true);
    } else {
      addMessage(tryAgainCountdown + "... ", false);
    }
  }

  @Override
  public void systemInformSent(final MessageData md) {
  }

  @Override
  public void systemLoggedOut() {
    logger.debug("connectionLoggedOut()");
    setLocationRelativeTo(null);
    cl_centralPanel.show(centralPanel, "message");
    btnEnter.setVisible(false);
    textInfo.setText("");
    txtPassword.requestFocus();
    invokeSetVisible(true);
  }

  @Override
  public void systemLoginAccepted() {
    Client.cache().makeInicialRequests();
    setVisible(false);
  }

  @Override
  public void systemLoginFailed() {
    addMessage(S.WINDOW_AUTH_WRONG_PASSWORD.s(), true);
    JOptionPane.showMessageDialog(this, S.WINDOW_AUTH_WRONG_PASSWORD,
        S.TITLE_ERROR.s(), JOptionPane.WARNING_MESSAGE);
    showCredentialsInput();
    txtPassword.requestFocus();
  }

  @Override
  public void systemPongReceived(final long lag) {
  }

  @Override
  public void systemVersionReceived(final BuildInfo serverVersion) {
    final BuildInfo clientVersion = BuildInfo.CURRENT;

    if (serverVersion.absoluteBuild > clientVersion.absoluteBuild) {
      logger.error("Client (" + clientVersion + ") outdated. Server: "
          + serverVersion);
    } else if (serverVersion.absoluteBuild < clientVersion.absoluteBuild) {
      logger.debug("Server (" + serverVersion + ") outdated. Client: "
          + clientVersion);
    } else {
      addMessage("Version checked and correct.", true);
    }
  }

  private void updateUserList() {
    final User[] users = Client.cache().users().getAll();
    User selected = (User) cbUsername.getSelectedItem();
    cbUsername.removeAllItems();

    cbUsername.addItem(new User(-1, "", ""));
    for (final User u : users) {
      if ((selected != null) && (selected.getId() == u.getId())) {
        selected = u;
      }
      cbUsername.addItem(u);
    }

    cbUsername.setSelectedItem(selected);
  }

  @Override
  public void usersCacheReloaded() {
    updateUserList();
    cbUsername.setSelectedIndex(0);
    txtPassword.requestFocusInWindow();
    // try to auto login or request credentials
    if (Client.config().AutoLogin) {
      selectUser(Client.config().AutoLoginUsername);
      txtPassword.setText(Client.config().AutoLoginPassword);
      ok();
    } else {
      showCredentialsInput();
    }
  }

  @Override
  public void usersChanged(final User u) {
    updateUserList();
  }

  @Override
  public void usersCreated(final User u) {
    updateUserList();
  }

  @Override
  public void usersDeleted(final int iduser) {
    updateUserList();
  }
}
