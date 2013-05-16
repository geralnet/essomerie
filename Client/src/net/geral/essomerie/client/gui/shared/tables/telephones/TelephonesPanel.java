package net.geral.essomerie.client.gui.shared.tables.telephones;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

import net.geral.essomerie.shared.person.Telephones;

public class TelephonesPanel extends JPanel {
  private static final long     serialVersionUID = 1L;

  private final TelephonesTable table            = new TelephonesTable();

  public TelephonesPanel() {
    setLayout(new BorderLayout(0, 0));

    final JLabel lblTitle = new JLabel("Telephones:");
    lblTitle.setFont(lblTitle.getFont().deriveFont(
        lblTitle.getFont().getStyle() | Font.BOLD));
    add(lblTitle, BorderLayout.NORTH);

    add(table.getScroll(), BorderLayout.CENTER);
  }

  public TelephonesTable getTable() {
    return table;
  }

  public Telephones getTelephones(final int idperson) {
    return new Telephones(idperson, table.getModel().getTelephones());
  }

  public void setEditable(final boolean yn) {
    table.setEditable(yn);
  }
}
