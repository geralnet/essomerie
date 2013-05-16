package net.geral.essomerie.client.gui.messages.table;

import java.util.ArrayList;
import java.util.HashMap;

import net.geral.essomerie._shared.mensagens.Message;
import net.geral.essomerie.client.core.Client;
import net.geral.lib.table.GNTableModel;

public class MessagesModel extends GNTableModel<Message> {
  private static final long serialVersionUID = 1L;

  public MessagesModel() {
    super(false, false, true);
  }

  @Override
  protected Message changeEntry(final Message t, final int columnIndex,
      final Object aValue) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Message createNewEntry() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  protected Object getValueFor(final Message m, final int columnIndex) {
    return m;
  }

  public int id2row(final int idmsg) {
    final ArrayList<Message> messages = getAll();
    for (int i = 0; i < messages.size(); i++) {
      if (messages.get(i).getId() == idmsg) {
        return i;
      }
    }
    return -1;
  }

  public int[] id2row(final int[] ids) {
    final ArrayList<Message> messages = getAll();
    final HashMap<Integer, Integer> id2row = new HashMap<Integer, Integer>(
        messages.size());
    // create map
    for (int i = 0; i < messages.size(); i++) {
      id2row.put(messages.get(i).getId(), i);
    }
    // create result
    final int[] res = new int[ids.length];
    for (int i = 0; i < ids.length; i++) {
      final Integer id = id2row.get(ids[i]);
      res[i] = (id == null) ? -1 : id.intValue();
    }
    return res;
  }

  public void read(final int idmsg) {
    // cache should have set to read already, just update row
    final int row = id2row(idmsg);
    fireTableRowsUpdated(row, row);
  }

  public void refresh() {
    setData(Client.cache().messages().getAll());
    // remove extras
    while (getRowCount() > Client.config().MessagesInboxLimit) {
      remove(Client.config().MessagesInboxLimit);
    }
    fireTableDataChanged();
  }

  public void remove(final int[] ids) {
    // if inbox is full, refresh it instead of removing
    // TODO instead of refreshing, add more messages so table doesnt lose
    // selection
    if ((getRowCount() >= Client.config().MessagesInboxLimit)) {
      refresh();
    } else {
      for (final int id : ids) {
        final int row = id2row(id);
        if (row >= 0) {
          remove(row);
        }
      }
    }
  }

  public int[] row2id(final int[] rows) {
    final ArrayList<Message> messages = getAll();
    final int[] res = new int[rows.length];
    for (int i = 0; i < rows.length; i++) {
      final int row = rows[i];
      res[i] = (row < messages.size()) ? messages.get(row).getId() : 0;
    }
    return res;
  }
}
