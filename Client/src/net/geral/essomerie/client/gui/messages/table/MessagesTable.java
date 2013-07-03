package net.geral.essomerie.client.gui.messages.table;

import java.util.Comparator;

import javax.swing.ListSelectionModel;

import net.geral.essomerie._shared.User;
import net.geral.essomerie.client.core.Client;
import net.geral.essomerie.client.core.configuration.CoreConfiguration;
import net.geral.essomerie.client.gui.messages.MessagesTabPanel;
import net.geral.essomerie.client.resources.S;
import net.geral.essomerie.shared.messages.Message;
import net.geral.lib.strings.GNStrings;
import net.geral.lib.table.GNTable;

public class MessagesTable extends GNTable<MessagesModel> {
  private static final long      serialVersionUID = 1L;
  private final MessagesTabPanel messagesPanel;

  public MessagesTable(final MessagesTabPanel panel) {
    super(new MessagesModel(), new MessagesRenderer());
    getSelectionModel().setSelectionMode(
        ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    messagesPanel = panel;
    initialSort(0, true);
  }

  @Override
  protected void createColumns() {
    final CoreConfiguration c = Client.config();
    createColumn(S.MESSAGES_HEADER_DATETIME.s(),
        c.TableColumnWidth_Messages_DateTime,
        c.TableColumnWidth_Default_DateTimeNoSeconds);
    createColumn(S.MESSAGES_HEADER_FROM.s(), c.TableColumnWidth_Messages_From,
        c.TableColumnWidth_Default_Username);
    createColumn(S.MESSAGES_HEADER_MESSAGE.s(),
        c.TableColumnWidth_Messages_Message, c.TableColumnWidth_Default);

    // setDefaultSort(0, true);
  }

  @Override
  protected Comparator<?> createRowSorter(final int columnIndex) {
    switch (columnIndex) {
      case 0:
        return new Comparator<Message>() {
          @Override
          public int compare(final Message o1, final Message o2) {
            return o1.getSent().compareTo(o2.getSent());
          }
        };
      case 1:
        return new Comparator<Message>() {
          @Override
          public int compare(final Message o1, final Message o2) {
            final User u1 = Client.cache().users().get(o1.getFrom());
            final User u2 = Client.cache().users().get(o2.getFrom());
            return GNStrings.compare(u1.getUsername(), u2.getUsername());
          }
        };
      case 2:
        return new Comparator<Message>() {
          @Override
          public int compare(final Message o1, final Message o2) {
            return GNStrings.compare(o1.getMessage(), o2.getMessage());
          }
        };
      default:
        return null;
    }
  }

  @Override
  protected boolean deleteClicked(final int viewRow, final int tableColumn) {
    messagesPanel.actionDelete();
    return false;
  }

  @Override
  public MessagesModel getModel() {
    return super.getModel();
  }

  @Override
  public String getNewEntryText(final int columnIndex) {
    return null;
  }

  public Message getSelected() {
    final int selected = getSelectedRow();
    if (selected == -1) {
      return null;
    }
    return getModel().get(convertRowIndexToModel(selected));
  }

  public int[] getSelectedIds() {
    final int[] rows = getSelectedRows();
    for (int i = 0; i < rows.length; i++) {
      rows[i] = convertRowIndexToModel(rows[i]);
    }
    return getModel().row2id(rows);
  }
}
