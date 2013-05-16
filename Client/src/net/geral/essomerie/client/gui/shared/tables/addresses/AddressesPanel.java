package net.geral.essomerie.client.gui.shared.tables.addresses;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

import net.geral.essomerie.shared.person.Addresses;

public class AddressesPanel extends JPanel {
  private static final long    serialVersionUID = 1L;

  private final AddressesTable table            = new AddressesTable();

  public AddressesPanel() {
    setLayout(new BorderLayout(0, 0));

    final JLabel lblTitle = new JLabel("Addresses:");
    lblTitle.setFont(lblTitle.getFont().deriveFont(
        lblTitle.getFont().getStyle() | Font.BOLD));
    add(lblTitle, BorderLayout.NORTH);

    add(table.getScroll(), BorderLayout.CENTER);
  }

  public Addresses getAddresses(final int idperson) {
    return new Addresses(idperson, table.getModel().getAddresses());
  }

  public AddressesTable getTable() {
    return table;
  }

  public void setEditable(final boolean yn) {
    table.setEditable(yn);
  }
}
