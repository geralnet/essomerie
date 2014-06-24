package net.geral.essomerie.client.gui.shared.notification;

import javax.swing.JPanel;

public abstract class NotificationWindowPanel extends JPanel {
  private static final long serialVersionUID = 1L;

  public abstract void setCloseListener(NotificationWindow notificationWindow);
}
