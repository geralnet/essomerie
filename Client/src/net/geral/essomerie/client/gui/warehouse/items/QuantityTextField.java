package net.geral.essomerie.client.gui.warehouse.items;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import net.geral.lib.gui.textfield.DoubleTextField;

public class QuantityTextField extends DoubleTextField implements KeyListener {
  private static final long          serialVersionUID = 1L;
  private final WarehouseItemsPanel panel;

  public QuantityTextField() {
    // used for WindowBuilder
    this(null);
  }

  public QuantityTextField(final WarehouseItemsPanel changePanel) {
    super(false, 2);
    panel = changePanel;
    addKeyListener(this);
  }

  @Override
  protected boolean evaluate() {
    if (!super.evaluate()) {
      return false; // check double evaluation
    }
    if (value == null) {
      return true; // ok (no change)
    }
    return panel.quantityTyped((float) value.doubleValue());
  }

  @Override
  public void keyPressed(final KeyEvent e) {
  }

  @Override
  public void keyReleased(final KeyEvent e) {
  }

  @Override
  public void keyTyped(final KeyEvent e) {
    final char c = e.getKeyChar();
    if (panel.changeMode(c) || panel.changeReason(c)) {
      checkColor();
      e.consume();
    }
  }
}
