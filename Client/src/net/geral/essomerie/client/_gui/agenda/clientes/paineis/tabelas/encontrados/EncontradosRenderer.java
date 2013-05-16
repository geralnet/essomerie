package net.geral.essomerie.client._gui.agenda.clientes.paineis.tabelas.encontrados;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;

import net.geral.essomerie._shared.cliente.Customer;
import net.geral.essomerie.client._gui.agenda.clientes.paineis.tabelas.encontrados.EncontradosModel.ClienteTableLinha;

public class EncontradosRenderer extends DefaultTableCellRenderer {
  private static final long   serialVersionUID = 1L;
  private static final Border topLine          = BorderFactory
                                                   .createMatteBorder(1, 0, 0,
                                                       0, Color.BLACK);
  private static final Color  FOUND_AND        = new Color(255, 200, 200);
  private static final Color  FOUND_OR         = new Color(255, 255, 200);
  private static final Color  FOUND_NONE       = new Color(255, 255, 255);

  private static Color getGradient(float f) {
    if (f <= 0.5) {
      f *= 2f; // 0 to 1
      final int b = 255 - (int) (55f * f);
      return new Color(255, 255, b);
    } else {
      f = (f - 0.5f) * 2; // 0 to 1
      final int g = 255 - (int) (55 * f);
      return new Color(255, g, 200);
    }
  }

  private static String getText(final Customer c, final int columnIndex,
      final int linha) {
    final boolean z = (linha == 0);
    switch (columnIndex) {
      case 0:
        return z ? String.valueOf(c.idcliente) : "";
      case 1:
        return z ? c.nome : "";
      case 2:
        return z ? c.notaPaulista ? "X" : "" : "";
      case 3:
        return z ? c.cpf.toString() : "";
        // case 4:
        // return (linha < c.telefones.length) ? c.telefones[linha].toString() :
        // "";
        // case 5:
        // return (linha < c.enderecos.length) ?
        // c.enderecos[linha].getEnderecoCompleto() : "";
      case 6:
        return z ? c.observacoes : "";
      default:
        return "n/a";
    }
  }

  @Override
  public Component getTableCellRendererComponent(final JTable table,
      final Object value, final boolean isSelected, final boolean hasFocus,
      final int row, final int column) {
    super.getTableCellRendererComponent(table, value, isSelected, hasFocus,
        row, column);

    final ClienteTableLinha ctl = (ClienteTableLinha) value;
    final Customer c = ctl.cliente;

    if (ctl.linhaCliente == 0) {
      final Border prev = getBorder();
      setBorder(BorderFactory.createCompoundBorder(topLine, prev));
    }

    if (!isSelected) {
      if (ctl.andFilter) {
        setBackground(FOUND_AND);
      } else if (ctl.orFilter) {
        setBackground(FOUND_OR);
      } else {
        if (ctl.match == 0) {
          setBackground(FOUND_NONE);
        } else {
          setBackground(getGradient(ctl.match));
        }
      }
    }

    final String text = getText(c, column, ctl.linhaCliente);
    setText(text);
    setHorizontalAlignment((column == 0) ? TRAILING : LEADING);

    return this;
  }
}
