package net.geral.essomerie.client.core.cache.caches;

import java.util.HashMap;

import net.geral.essomerie._shared.User;
import net.geral.essomerie._shared.UserPermission;
import net.geral.essomerie._shared.UserPermissions;
import net.geral.essomerie.client.core.events.Events;
import net.geral.essomerie.client.gui.shared.PinInputDialog;

import org.apache.log4j.Logger;

public class UsersCache {
  private static final Logger          logger            = Logger
                                                             .getLogger(UsersCache.class);
  private final HashMap<Integer, User> users             = new HashMap<>();
  private int                          logged            = 0;
  private UserPermissions              loggedPermissions = new UserPermissions();

  public synchronized boolean checkLoggedPermission(final UserPermission p,
      final boolean withPin) {
    if (loggedPermissions == null) {
      return false;
    }
    if (!loggedPermissions.get(p)) {
      return false;
    }
    // from here on only if has the permission
    if (!withPin) {
      return true;
    }

    if (!getLogged().hasPIN()) {
      PinInputDialog.notPinAlert();
      return false;
    }

    final char[] pin = PinInputDialog.check();
    if (pin == null) {
      return false;
    }

    // check pin
    if (!getLogged().checkPIN(pin)) {
      PinInputDialog.wrongPinAlert();
      return false;
    }
    return true;
  }

  public synchronized boolean contains(final int iduser) {
    return users.containsKey(iduser);
  }

  public synchronized int count() {
    return users.size();
  }

  public synchronized User get(final int iduser) {
    final User u = users.get(iduser);
    if (u != null) {
      return u;
    }
    return new User(iduser, "U#" + iduser, "[U#" + iduser + "]", null);
  }

  public synchronized User[] getAll() {
    final User[] us = new User[users.size()];
    int i = 0;
    for (final User u : users.values()) {
      us[i++] = u;
    }
    return us;
  }

  public synchronized User getLogged() {
    final User u = get(logged);
    return u;
  }

  public synchronized void informChanged(final User u) {
    // remove
    if (users.remove(u.getId()) == null) {
      logger.warn("Could not remove old value when changing, id: " + u.getId());
    }
    // add
    users.put(u.getId(), u);
    // fire
    Events.users().fireChanged(u);
  }

  public synchronized void informCreated(final User u) {
    // remove (should not happen)
    if (users.remove(u.getId()) != null) {
      logger.warn("Removed (but not expected to) old value when adding, id: "
          + u.getId());
    }
    // add
    users.put(u.getId(), u);
    // fire
    Events.users().fireCreated(u);
  }

  public synchronized void informDeleted(final int iduser) {
    // remove
    if (users.remove(iduser) == null) {
      logger.warn("Could not remove, id: " + iduser);
    }
    // fire
    Events.users().fireDeleted(iduser);
  }

  public synchronized void informList(final User[] list) {
    users.clear();
    for (final User u : list) {
      users.put(u.getId(), u);
    }
    Events.users().fireCacheReloaded();
  }

  public void setLogged(final int id, final UserPermissions permissions) {
    logged = id;
    loggedPermissions = permissions;
  }
}
