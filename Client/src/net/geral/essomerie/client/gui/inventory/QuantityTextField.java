package net.geral.essomerie.client.gui.inventory;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import net.geral.gui.textfield.DoubleTextField;

//TODO check & translate
public class QuantityTextField extends DoubleTextField implements KeyListener {
  private static final long          serialVersionUID = 1L;
  private final InventoryChangePanel panel;

  public QuantityTextField(final InventoryChangePanel alterarPanel) {
    super(false, 2);
    panel = alterarPanel;
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
    return panel.quantidadeDigitada((float) value.doubleValue());
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
    if (panel.changeTipo(c) || panel.changeMotivo(c)) {
      checkColor();
      e.consume();
    }
  }
}
