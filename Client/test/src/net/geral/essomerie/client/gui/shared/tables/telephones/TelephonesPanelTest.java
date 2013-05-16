package net.geral.essomerie.client.gui.shared.tables.telephones;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class TelephonesPanelTest extends JFrame {
  private static final long serialVersionUID = 1L;

  public static void main(final String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        new TelephonesPanelTest();
      }
    });
  }

  public TelephonesPanelTest() {
    super(TelephonesPanel.class.toString());
    setSize(800, 600);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setLayout(new BorderLayout());
    add(new TelephonesTable().getScroll(), BorderLayout.CENTER);
    setVisible(true);
  }
}
