package net.geral.essomerie.client.core.events.listeners;

import java.util.EventListener;

import net.geral.essomerie._shared.User;

public interface UsersListener extends EventListener {
	public void usersCacheReloaded();

	public void usersChanged(User u);

	public void usersCreated(User u);

	public void usersDeleted(int iduser);
}