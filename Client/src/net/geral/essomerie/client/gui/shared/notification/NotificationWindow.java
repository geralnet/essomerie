package net.geral.essomerie.client.gui.shared.notification;

import java.awt.BorderLayout;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;

import javax.swing.JComponent;
import javax.swing.JWindow;

import net.geral.lib.edt.Edt;

import org.apache.log4j.Logger;

public class NotificationWindow extends JWindow implements Runnable {
  private static final long         serialVersionUID = 1L;
  private static final Logger       logger           = Logger
                                                         .getLogger(NotificationWindow.class);
  private static NotificationWindow showing          = null;
  private final int                 delay;

  public NotificationWindow(final JComponent component, final int timer) {
    setType(Type.UTILITY);
    setAlwaysOnTop(true);
    getContentPane().add(component, BorderLayout.CENTER);
    pack();
    delay = timer * 1000;
    new Thread(this).start();
  }

  @Override
  public void run() {
    try {
      Thread.sleep(delay);
    } catch (final InterruptedException e) {
      logger.warn(e, e);
    }
    Edt.run(false, new Runnable() {
      @Override
      public void run() {
        setVisible(false);
      }
    });
  }

  @Override
  public void setVisible(final boolean b) {
    Edt.required();
    if (b) {
      if (showing != null) {
        showing.setVisible(false);
      }
      showing = this;
      final Rectangle size = GraphicsEnvironment.getLocalGraphicsEnvironment()
          .getMaximumWindowBounds();
      final int x = (size.width + size.x) - getWidth();
      final int y = (size.height + size.y) - getHeight();
      setLocation(x, y);
      super.setVisible(true);
    } else {
      dispose();
    }
  }
}
