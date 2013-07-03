package net.geral.essomerie.client._gui.shared.rtf;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import net.geral.essomerie.shared.bulletinboard.BulletinBoardEntry;

import org.apache.log4j.Logger;

public class RTFEditorPanel extends JPanel implements ActionListener {
  private static final long      serialVersionUID    = 1L;
  private static final Dimension TOOLBAR_BUTTON_SIZE = new Dimension(32, 32);
  private static final Logger    logger              = Logger
                                                         .getLogger(RTFEditorPanel.class);

  private final JToolBar         toolBar;
  private final RTFPane          rtfPane             = new RTFPane(true);

  public RTFEditorPanel() {
    this(null);
  }

  public RTFEditorPanel(final BulletinBoardEntry info) {
    setLayout(new BorderLayout(0, 0));

    toolBar = new JToolBar();
    add(toolBar, BorderLayout.NORTH);
    toolBar.setFloatable(false);

    final JButton btnSansSerif = new JButton("Aa");
    btnSansSerif.setIgnoreRepaint(true);
    btnSansSerif.setFocusable(false);
    btnSansSerif.setActionCommand("sansserif");
    btnSansSerif.addActionListener(this);
    btnSansSerif.setFont(new Font("SansSerif", Font.PLAIN, 12));
    toolBar.add(btnSansSerif);

    final JButton btnMonospaced = new JButton("Aa");
    btnMonospaced.setIgnoreRepaint(true);
    btnMonospaced.setFocusable(false);
    btnMonospaced.setActionCommand("monospaced");
    btnMonospaced.addActionListener(this);
    btnMonospaced.setFont(new Font("Monospaced", Font.PLAIN, 12));
    toolBar.add(btnMonospaced);

    toolBar.addSeparator();

    final JButton btnPlain = new JButton("Aa");
    btnPlain.setIgnoreRepaint(true);
    btnPlain.setFocusable(false);
    btnPlain.addActionListener(this);
    btnPlain.setActionCommand("plain");
    btnPlain.setFont(new Font("SansSerif", Font.PLAIN, 12));
    toolBar.add(btnPlain);

    final JButton btnItalic = new JButton("Aa");
    btnItalic.setIgnoreRepaint(true);
    btnItalic.setFocusable(false);
    btnItalic.addActionListener(this);
    btnItalic.setActionCommand("italic");
    btnItalic.setFont(new Font("SansSerif", Font.ITALIC, 12));
    toolBar.add(btnItalic);

    final JButton btnBold = new JButton("Aa");
    btnBold.setIgnoreRepaint(true);
    btnBold.setFocusable(false);
    btnBold.addActionListener(this);
    btnBold.setActionCommand("bold");
    btnBold.setFont(new Font("SansSerif", Font.BOLD, 12));
    toolBar.add(btnBold);

    final JButton btnBoldItalic = new JButton("Aa");
    btnBoldItalic.setIgnoreRepaint(true);
    btnBoldItalic.setFocusable(false);
    btnBoldItalic.addActionListener(this);
    btnBoldItalic.setActionCommand("bolditalic");
    btnBoldItalic.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 12));
    toolBar.add(btnBoldItalic);

    toolBar.addSeparator();

    final JButton btnSize1 = new JButton("Aa");
    btnSize1.setIgnoreRepaint(true);
    btnSize1.setFocusable(false);
    btnSize1.addActionListener(this);
    btnSize1.setActionCommand("size1");
    btnSize1.setFont(new Font("SansSerif", Font.PLAIN, 7));
    toolBar.add(btnSize1);

    final JButton btnSize2 = new JButton("Aa");
    btnSize2.setIgnoreRepaint(true);
    btnSize2.setFocusable(false);
    btnSize2.addActionListener(this);
    btnSize2.setActionCommand("size2");
    btnSize2.setFont(new Font("SansSerif", Font.PLAIN, 9));
    toolBar.add(btnSize2);

    final JButton btnSize3 = new JButton("Aa");
    btnSize3.setIgnoreRepaint(true);
    btnSize3.setFocusable(false);
    btnSize3.addActionListener(this);
    btnSize3.setActionCommand("size3");
    btnSize3.setFont(new Font("SansSerif", Font.PLAIN, 11));
    toolBar.add(btnSize3);

    final JButton btnSize4 = new JButton("Aa");
    btnSize4.setIgnoreRepaint(true);
    btnSize4.setFocusable(false);
    btnSize4.addActionListener(this);
    btnSize4.setActionCommand("size4");
    btnSize4.setFont(new Font("SansSerif", Font.PLAIN, 13));
    toolBar.add(btnSize4);

    toolBar.addSeparator();

    final JButton btnBlack = new JButton("Aa");
    btnBlack.setIgnoreRepaint(true);
    btnBlack.setFocusable(false);
    btnBlack.addActionListener(this);
    btnBlack.setActionCommand("color");
    btnBlack.setFont(new Font("SansSerif", Font.BOLD, 12));
    toolBar.add(btnBlack);

    final JButton btnBlue = new JButton("Aa");
    btnBlue.setIgnoreRepaint(true);
    btnBlue.setFocusable(false);
    btnBlue.addActionListener(this);
    btnBlue.setActionCommand("color");
    btnBlue.setForeground(new Color(0, 0, 255));
    btnBlue.setFont(new Font("SansSerif", Font.BOLD, 12));
    toolBar.add(btnBlue);

    final JButton btnGreen = new JButton("Aa");
    btnGreen.setIgnoreRepaint(true);
    btnGreen.setFocusable(false);
    btnGreen.addActionListener(this);
    btnGreen.setActionCommand("color");
    btnGreen.setForeground(new Color(0, 128, 0));
    btnGreen.setFont(new Font("SansSerif", Font.BOLD, 12));
    toolBar.add(btnGreen);

    final JButton btnMaroon = new JButton("Aa");
    btnMaroon.setIgnoreRepaint(true);
    btnMaroon.setFocusable(false);
    btnMaroon.addActionListener(this);
    btnMaroon.setActionCommand("color");
    btnMaroon.setForeground(new Color(128, 0, 0));
    btnMaroon.setFont(new Font("SansSerif", Font.BOLD, 12));
    toolBar.add(btnMaroon);

    final JButton btnPurple = new JButton("Aa");
    btnPurple.setIgnoreRepaint(true);
    btnPurple.setFocusable(false);
    btnPurple.addActionListener(this);
    btnPurple.setActionCommand("color");
    btnPurple.setForeground(new Color(128, 0, 128));
    btnPurple.setFont(new Font("SansSerif", Font.BOLD, 12));
    toolBar.add(btnPurple);

    final JButton btnYellow = new JButton("Aa");
    btnYellow.setIgnoreRepaint(true);
    btnYellow.setFocusable(false);
    btnYellow.addActionListener(this);
    btnYellow.setActionCommand("color");
    btnYellow.setForeground(new Color(128, 128, 0));
    btnYellow.setFont(new Font("SansSerif", Font.BOLD, 12));
    toolBar.add(btnYellow);

    final JButton btnRed = new JButton("Aa");
    btnRed.setIgnoreRepaint(true);
    btnRed.setFocusable(false);
    btnRed.addActionListener(this);
    btnRed.setActionCommand("color");
    btnRed.setForeground(new Color(255, 0, 0));
    btnRed.setFont(new Font("SansSerif", Font.BOLD, 12));
    toolBar.add(btnRed);

    final JScrollPane scrollPane = new JScrollPane();
    add(scrollPane, BorderLayout.CENTER);

    scrollPane.setViewportView(rtfPane);

    setToolbarButtonsSize();
    removeFocusableToolbar();
  }

  @Override
  public void actionPerformed(final ActionEvent e) {
    if (!checkAction(e.getActionCommand(), e.getSource())) {
      logger.warn("Invalid action: " + e.getActionCommand());
    }
  }

  private boolean applyAttributeSet(final AttributeSet as) {
    final int offset = rtfPane.getSelectionStart();
    final int length = rtfPane.getSelectionEnd() - offset;
    if (length <= 0) {
      return true;
    }

    rtfPane.getStyleDocument()
        .setCharacterAttributes(offset, length, as, false);
    rtfPane.requestFocus();
    return true;
  }

  private boolean applyColor(final Object source) {
    if (!(source instanceof JButton)) {
      return false;
    }
    final JButton btn = (JButton) source;

    final SimpleAttributeSet as = new SimpleAttributeSet();
    StyleConstants.setForeground(as, btn.getForeground());
    return applyAttributeSet(as);
  }

  private boolean applyFont(final String font) {
    final SimpleAttributeSet as = new SimpleAttributeSet();
    StyleConstants.setFontFamily(as, font);
    return applyAttributeSet(as);
  }

  private boolean applySize(final int size) {
    final SimpleAttributeSet as = new SimpleAttributeSet();
    StyleConstants.setFontSize(as, size);
    return applyAttributeSet(as);
  }

  private boolean applyStyle(final boolean italic, final boolean bold) {
    final SimpleAttributeSet as = new SimpleAttributeSet();
    StyleConstants.setItalic(as, italic);
    StyleConstants.setBold(as, bold);
    return applyAttributeSet(as);
  }

  private boolean checkAction(final String cmd, final Object source) {
    if ("sansserif".equals(cmd)) {
      return applyFont("sansserif");
    }
    if ("monospaced".equals(cmd)) {
      return applyFont("monospaced");
    }
    if ("plain".equals(cmd)) {
      return applyStyle(false, false);
    }
    if ("italic".equals(cmd)) {
      return applyStyle(true, false);
    }
    if ("bold".equals(cmd)) {
      return applyStyle(false, true);
    }
    if ("bolditalic".equals(cmd)) {
      return applyStyle(true, true);
    }
    if ("size1".equals(cmd)) {
      return applySize(12);
    }
    if ("size2".equals(cmd)) {
      return applySize(14);
    }
    if ("size3".equals(cmd)) {
      return applySize(16);
    }
    if ("size4".equals(cmd)) {
      return applySize(20);
    }
    if ("color".equals(cmd)) {
      return applyColor(source);
    }
    return false;
  }

  public byte[] getRTF() {
    return rtfPane.getRTF();
  }

  public boolean hasChanged() {
    return rtfPane.hasChanged();
  }

  private void removeFocusableToolbar() {
    toolBar.setFocusable(false);
    for (final Component c : toolBar.getComponents()) {
      c.setFocusable(false);
    }
  }

  public void setRTF(final byte[] rtf) {
    rtfPane.setRTF(rtf);
  }

  private void setToolbarButtonsSize() {
    for (final Component c : toolBar.getComponents()) {
      if (c instanceof JButton) {
        c.setPreferredSize(TOOLBAR_BUTTON_SIZE);
      }
    }
  }
}
