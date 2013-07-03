package net.geral.essomerie.client.gui.messages.table;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JTable;

import net.geral.essomerie.client.core.Client;
import net.geral.essomerie.client.resources.S;
import net.geral.essomerie.shared.messages.Message;
import net.geral.lib.table.GNTableRenderer;

import org.joda.time.format.DateTimeFormat;

//TODO create configurations
public class MessagesRenderer extends GNTableRenderer {
  private static Color[]       COLOR_MESSAGE    = { Color.WHITE,
      new Color(230, 230, 230)                 };
  private static final Color[] COLOR_BROADCAST  = { new Color(255, 200, 200),
      new Color(255, 170, 170)                 };

  private static Font          fontRead         = null;
  private static Font          fontNew          = null;

  private static final long    serialVersionUID = 1L;

  public MessagesRenderer() {
    if (fontRead == null) {
      fontRead = getFont();
    }
    if (fontNew == null) {
      fontNew = fontRead.deriveFont(Font.BOLD);
    }
  }

  @Override
  public Component getTableCellRendererComponent(final JTable table,
      final Object value, final boolean isSelected, final boolean hasFocus,
      final int row, final int column) {
    super.getTableCellRendererComponent(table, value, isSelected, hasFocus,
        row, column);

    if (value instanceof Message) {
      final Message msg = (Message) value;
      if (!isSelected) {
        if (msg.isBroadcast()) {
          setBackground(COLOR_BROADCAST[row % COLOR_MESSAGE.length]);
        } else {
          setBackground(COLOR_MESSAGE[row % COLOR_MESSAGE.length]);
        }
      }
      setFont(msg.isRead(Client.cache().users().getLogged().getId()) ? fontRead
          : fontNew);
      setText(getText(msg, table.convertColumnIndexToModel(column)));
    }

    return this;
  }

  private String getText(final Message msg, final int column) {
    switch (column) {
      case 0:
        return msg.getSent().toString(
            DateTimeFormat.forPattern(S.FORMAT_DATETIME_SIMPLE_NO_SECONDS.s()));
      case 1:
        return Client.cache().users().get(msg.getFrom()).getUsername()
            .toUpperCase();
      case 2:
        return msg.getMessage();
      default:
        return "C" + column;
    }
  }
}
