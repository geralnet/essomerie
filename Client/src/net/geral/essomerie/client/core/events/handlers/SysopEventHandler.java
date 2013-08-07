package net.geral.essomerie.client.core.events.handlers;

import javax.swing.event.EventListenerList;

import net.geral.essomerie.client.core.events.listeners.SysopListener;
import net.geral.essomerie.shared.system.DevicesInfo;
import net.geral.lib.edt.Edt;

import org.apache.log4j.Logger;

public class SysopEventHandler extends EventListenerList {
  private static final Logger logger           = Logger
                                                   .getLogger(SysopEventHandler.class);
  private static final long   serialVersionUID = 1L;

  public void addListener(final SysopListener l) {
    add(SysopListener.class, l);
  }

  public void fireInformedDevicesInfo(final DevicesInfo dev) {
    Edt.run(false, new Runnable() {
      @Override
      public void run() {
        logger.debug("Fired: fireInformedDevicesInfo");
        for (final SysopListener l : getListeners(SysopListener.class)) {
          l.sysopInformedDevicesInfo(dev);
        }
      }
    });
  }

  public void removeListener(final SysopListener l) {
    remove(SysopListener.class, l);
  }
}
