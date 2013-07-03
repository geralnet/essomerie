package net.geral.essomerie.client.core.cache.caches;

import java.util.ArrayList;
import java.util.Collections;

import net.geral.essomerie.client.core.Client;
import net.geral.essomerie.client.core.events.Events;
import net.geral.essomerie.shared.messages.Message;

import org.apache.log4j.Logger;

public class MessagesCache {
  private static final Logger      logger   = Logger
                                                .getLogger(MessagesCache.class);
  private final ArrayList<Message> messages = new ArrayList<>();

  public synchronized int count() {
    return messages.size();
  }

  public synchronized Message[] getAll() {
    return messages.toArray(new Message[messages.size()]);
  }

  public synchronized Message getByIndex(final int index) {
    return messages.get(index);
  }

  private boolean hasUnread() {
    final int iduser = Client.getLoggerUser().getId();
    for (final Message m : messages) {
      if (!m.isRead(iduser)) {
        return true;
      }
    }
    return false;
  }

  public synchronized void informDeleted(final int[] ids) {
    // remove message
    for (int i = 0; i < messages.size(); i++) {
      for (final int id : ids) {
        if (messages.get(i).getId() == id) {
          messages.remove(i);
        }
      }
    }
    // fire event
    Events.messages().fireDeleted(ids, hasUnread());
  }

  public synchronized void informMessagesToUser(final Message[] msgs) {
    logger.debug("Messages: " + msgs.length);
    // set messages
    messages.clear();
    Collections.addAll(messages, msgs);
    // fire event
    Events.messages().fireCacheReloaded(hasUnread());
  }

  public synchronized void informRead(final int idmsg,
      final boolean hasMoreUnread) {
    // mark message
    for (int i = 0; i < messages.size(); i++) {
      final Message m = messages.get(i);
      if (m.getId() == idmsg) {
        m.read(Client.getLoggerUser().getId());
        break;
      }
    }
    // fire event
    Events.messages().fireRead(idmsg, hasMoreUnread);
  }

  public synchronized void informReceived(final Message m) {
    // remove message (if exists, but it should not)
    for (int i = 0; i < messages.size(); i++) {
      if (messages.get(i).getId() == m.getId()) {
        messages.remove(i);
        logger.warn("Received message was already present, id: " + m.getId());
        break;
      }
    }
    // add message
    messages.add(m);
    // fire event
    Events.messages().fireReceived(m);
  }

  public void informSent(final int idmsg) {
    // fire event
    Events.messages().fireSent(idmsg);
  }
}
