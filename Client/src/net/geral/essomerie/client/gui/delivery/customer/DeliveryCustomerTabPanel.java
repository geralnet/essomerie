package net.geral.essomerie.client.gui.delivery.customer;

import net.geral.essomerie.client.gui.main.TabPanel;

public class DeliveryCustomerTabPanel extends TabPanel {
  private static final long serialVersionUID = 1L;

  @Override
  public String getTabText() {
    return "Delivery";
  }

  @Override
  public void tabClosed() {
  }

  @Override
  public boolean tabCloseRequest() {
    return true;
  }

  @Override
  public void tabCreated() {
  }
}
