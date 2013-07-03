package net.geral.essomerie.client.gui.main;

import java.awt.Component;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;

import net.geral.essomerie._shared.User;
import net.geral.essomerie.client.core.Client;
import net.geral.essomerie.client.core.events.Events;
import net.geral.essomerie.client.core.events.listeners.MessagesListener;
import net.geral.essomerie.client.resources.IMG;
import net.geral.essomerie.client.resources.S;
import net.geral.essomerie.shared.messages.Message;
import net.geral.lib.edt.Edt;

import org.apache.log4j.Logger;

public class MainToolBar extends JToolBar implements Runnable,
    KeyEventDispatcher, MessagesListener {
  private static final int    ICON_SIZE         = 32;

  private static final long   serialVersionUID  = 1L;
  private static final Logger logger            = Logger
                                                    .getLogger(MainToolBar.class);

  private boolean             running           = true;
  private final JButton       btnMessages;

  private boolean             hasUnreadMessages = false;

  public MainToolBar(final ActionListener listener) {
    setFloatable(false);

    btnMessages = createButton(S.MENU_USER_MESSAGES,
        IMG.ICON__TOOLBAR__MESSAGE.icon(ICON_SIZE), listener);

    add(btnMessages);
    add(createButton(S.MENU_ORGANIZER_CALENDAR,
        IMG.ICON__TOOLBAR__ORGANIZER_CALENDAR.icon(ICON_SIZE), listener));

    add(createButton(S.MENU_INVENTORY_MANAGEMENT,
        IMG.ICON__TOOLBAR__INVENTORY.icon(ICON_SIZE), listener));

    add(createButton(S.MENU_ORGANIZER_PERSONS,
        IMG.ICON__TOOLBAR__ORGANIZER_PERSONS.icon(ICON_SIZE), listener));
    // addSeparator();
    // add(createButton(S.MENU_DELIVERY_CUSTOMERS,
    // IMG.ICON__TOOLBAR__DELIVERY_CUSTOMERS.icon(ICON_SIZE), listener));

    KeyboardFocusManager.getCurrentKeyboardFocusManager()
        .addKeyEventDispatcher(this);

    if (Client.config().MainToolbarBackground != null) {
      setOpaque(true);
      setBackground(Client.config().MainToolbarBackground);
    }

    Events.messages().addListener(this);
  }

  private JButton createButton(final S s, final ImageIcon icon,
      final ActionListener listener) {
    return createButton(s.name(), icon, listener);
  }

  private JButton createButton(final String actionCommand,
      final ImageIcon icon, final ActionListener listener) {
    final JButton btn = new JButton(icon);
    btn.setActionCommand(actionCommand);
    btn.addActionListener(listener);
    btn.setFocusable(false);
    return btn;
  }

  @Override
  public boolean dispatchKeyEvent(final KeyEvent e) {
    if (e.getID() != KeyEvent.KEY_RELEASED) {
      return false;
    }
    int k = e.getKeyCode();
    if ((k < KeyEvent.VK_F1) || (k > KeyEvent.VK_F12)) {
      return false;
    }
    k = (k - KeyEvent.VK_F1) + 1;
    fireAction(k);
    return true;
  }

  private void fireAction(final int k) {
    // if window not active and focused, ignore (modal might be active)
    if (!Client.window().isActive()) {
      return;
    }
    int i = 0;
    for (final Component c : getComponents()) {
      if (c instanceof JButton) {
        if (++i == k) {
          ((JButton) c).doClick();
        }
      }
    }
  }

  @Override
  public void messageCacheReloaded(final boolean hasUnread) {
    hasUnreadMessages = hasUnread;
  }

  @Override
  public void messageDeleted(final int[] ids, final boolean hasMoreUnread) {
    hasUnreadMessages = hasMoreUnread;
  }

  @Override
  public void messageRead(final int idmsg, final boolean hasMoreUnread) {
    hasUnreadMessages = hasMoreUnread;
  }

  @Override
  public void messageReceived(final Message m) {
    hasUnreadMessages = true;
  }

  @Override
  public void messageSent() {
  }

  @Override
  public void run() {
    Thread.currentThread().setName("MainToolBar");
    logger.debug("MainToolBar.run() Started...");
    final ImageIcon[] icons = new ImageIcon[] {
        IMG.ICON__TOOLBAR__MESSAGE.icon(ICON_SIZE),
        IMG.ICON__TOOLBAR__MESSAGE_HASUNREAD.icon(ICON_SIZE) };
    int toIcon = 0;
    int activeIcon = 0;
    while (running) {
      final User logged = Client.cache().users().getLogged();
      if ((logged != null) && hasUnreadMessages) {
        toIcon = activeIcon == 0 ? 1 : 0;
      } else {
        toIcon = 0;
      }
      final int finalToIcon = toIcon;
      if (toIcon != activeIcon) {
        activeIcon = toIcon;
        Edt.run(false, new Runnable() {
          @Override
          public void run() {
            btnMessages.setIcon(icons[finalToIcon]);
          }
        });
      }
      try {
        Thread.sleep(Client.config().ToolbarHasMessageBlinkRate);
      } catch (final InterruptedException e) {
        logger.warn(e, e);
        running = false;
      }
    }
    logger.debug("MainToolBar.run() Finished!");
  }

  public void start() {
    new Thread(this).start();
  }

  public void stop() {
    running = false;
  }
}