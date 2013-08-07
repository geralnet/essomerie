package net.geral.essomerie.client.gui.shared;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import net.geral.essomerie.client.resources.S;

public class PinInputDialog extends JPanel {
  private static final long serialVersionUID = 1L;

  public static char[] check() {
    final PinInputDialog panel = new PinInputDialog();
    final int res = JOptionPane.showOptionDialog(null, panel,
        S.TITLE_CONFIRM.s(), JOptionPane.OK_CANCEL_OPTION,
        JOptionPane.QUESTION_MESSAGE, null, null, null);
    if (res != 0) {
      return null; // did not click OK
    }

    return panel.getPIN();
  }

  public static void notPinAlert() {
    JOptionPane.showMessageDialog(null, S.SYSOP_PIN_MISSING, S.TITLE_ERROR.s(),
        JOptionPane.ERROR_MESSAGE);
  }

  public static void wrongPinAlert() {
    JOptionPane.showMessageDialog(null, S.SYSOP_PIN_WRONG, S.TITLE_ERROR.s(),
        JOptionPane.ERROR_MESSAGE);
  }

  private final JPasswordField passwordField;

  public PinInputDialog() {
    setLayout(new BorderLayout(0, 0));

    final JLabel lblPleaseInputYour = new JLabel(S.SYSOP_PIN_REQUEST.s());
    add(lblPleaseInputYour, BorderLayout.NORTH);

    passwordField = new JPasswordField();
    passwordField.addAncestorListener(new AncestorListener() {
      @Override
      public void ancestorAdded(final AncestorEvent event) {
        passwordField.requestFocusInWindow();
      }

      @Override
      public void ancestorMoved(final AncestorEvent event) {
      }

      @Override
      public void ancestorRemoved(final AncestorEvent event) {
      }
    });
    add(passwordField, BorderLayout.CENTER);
  }

  private char[] getPIN() {
    return passwordField.getPassword();
  }
}
