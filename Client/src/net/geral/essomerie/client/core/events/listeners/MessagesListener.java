package net.geral.essomerie.client.core.events.listeners;

import java.util.EventListener;

import net.geral.essomerie.shared.messages.Message;

public interface MessagesListener extends EventListener {
	public void messageCacheReloaded(boolean hasUnread);

	public void messageDeleted(int[] ids, boolean hasMoreUnread);

	public void messageRead(int idmsg, boolean hasMoreUnread);

	public void messageReceived(Message m);

	public void messageSent();
}
