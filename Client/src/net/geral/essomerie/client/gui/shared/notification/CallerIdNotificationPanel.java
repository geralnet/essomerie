package net.geral.essomerie.client.gui.shared.notification;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import net.geral.essomerie.client.gui.shared.label.TitleLabel;
import net.geral.essomerie.client.resources.IMG;
import net.geral.essomerie.client.resources.S;
import net.geral.essomerie.shared.person.PersonData;
import net.geral.essomerie.shared.person.Telephone;

public class CallerIdNotificationPanel extends NotificationWindowPanel
    implements MouseListener {
  private static final long  serialVersionUID = 1L;
  private final TitleLabel   lblClose;
  private NotificationWindow closeListener    = null;

  public CallerIdNotificationPanel(final String line,
      final Telephone telephone, final PersonData person) {
    setBorder(new CompoundBorder(new LineBorder(new Color(0, 0, 0), 2),
        new CompoundBorder(new LineBorder(new Color(255, 0, 0)),
            new LineBorder(new Color(0, 0, 0)))));
    setLayout(new BorderLayout(0, 0));

    final JPanel mainPanel = new JPanel();
    mainPanel.setBorder(new EmptyBorder(2, 2, 2, 2));
    add(mainPanel, BorderLayout.CENTER);
    final GridBagLayout gbl_mainPanel = new GridBagLayout();
    gbl_mainPanel.columnWidths = new int[] { 1, 1, 0 };
    gbl_mainPanel.rowHeights = new int[] { 1, 1, 1, 0 };
    gbl_mainPanel.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
    gbl_mainPanel.rowWeights = new double[] { 0.0, 0.0, 1.0, Double.MIN_VALUE };
    mainPanel.setLayout(gbl_mainPanel);

    final JLabel lblTLine = new JLabel(S.CALLERID_LINE.s());
    lblTLine.setFont(lblTLine.getFont().deriveFont(
        lblTLine.getFont().getStyle() | Font.BOLD));
    final GridBagConstraints gbc_lblTLine = new GridBagConstraints();
    gbc_lblTLine.anchor = GridBagConstraints.EAST;
    gbc_lblTLine.fill = GridBagConstraints.VERTICAL;
    gbc_lblTLine.insets = new Insets(0, 0, 5, 5);
    gbc_lblTLine.gridx = 0;
    gbc_lblTLine.gridy = 0;
    mainPanel.add(lblTLine, gbc_lblTLine);

    final JLabel lblLine = new JLabel(line);
    final GridBagConstraints gbc_lblLine = new GridBagConstraints();
    gbc_lblLine.fill = GridBagConstraints.BOTH;
    gbc_lblLine.insets = new Insets(0, 0, 5, 0);
    gbc_lblLine.gridx = 1;
    gbc_lblLine.gridy = 0;
    mainPanel.add(lblLine, gbc_lblLine);

    final JLabel lblTNumber = new JLabel(S.CALLERID_TELEPHONE.s());
    lblTNumber.setFont(lblTNumber.getFont().deriveFont(
        lblTNumber.getFont().getStyle() | Font.BOLD));
    final GridBagConstraints gbc_lblTNumber = new GridBagConstraints();
    gbc_lblTNumber.anchor = GridBagConstraints.EAST;
    gbc_lblTNumber.fill = GridBagConstraints.VERTICAL;
    gbc_lblTNumber.insets = new Insets(0, 0, 5, 5);
    gbc_lblTNumber.gridx = 0;
    gbc_lblTNumber.gridy = 1;
    mainPanel.add(lblTNumber, gbc_lblTNumber);

    final JLabel lblNumber = new JLabel(telephone.toString(false));
    final GridBagConstraints gbc_lblNumber = new GridBagConstraints();
    gbc_lblNumber.fill = GridBagConstraints.BOTH;
    gbc_lblNumber.insets = new Insets(0, 0, 5, 0);
    gbc_lblNumber.gridx = 1;
    gbc_lblNumber.gridy = 1;
    mainPanel.add(lblNumber, gbc_lblNumber);

    final JLabel lblTPerson = new JLabel(S.CALLERID_PERSON.s());
    lblTPerson.setFont(lblTPerson.getFont().deriveFont(
        lblTPerson.getFont().getStyle() | Font.BOLD));
    final GridBagConstraints gbc_lblTPerson = new GridBagConstraints();
    gbc_lblTPerson.anchor = GridBagConstraints.EAST;
    gbc_lblTPerson.fill = GridBagConstraints.VERTICAL;
    gbc_lblTPerson.insets = new Insets(0, 0, 0, 5);
    gbc_lblTPerson.gridx = 0;
    gbc_lblTPerson.gridy = 2;
    mainPanel.add(lblTPerson, gbc_lblTPerson);

    final JLabel lblPerson = new JLabel(person == null ? "n/a"
        : person.getNameAlias());
    final GridBagConstraints gbc_lblPerson = new GridBagConstraints();
    gbc_lblPerson.fill = GridBagConstraints.BOTH;
    gbc_lblPerson.gridx = 1;
    gbc_lblPerson.gridy = 2;
    mainPanel.add(lblPerson, gbc_lblPerson);

    final JPanel titlePanel = new JPanel();
    add(titlePanel, BorderLayout.NORTH);
    titlePanel.setLayout(new BorderLayout(0, 0));

    final TitleLabel lblTitle = new TitleLabel(
        " " + S.CALLERID_TITLE.s() + " ", false);
    titlePanel.add(lblTitle);

    lblClose = new TitleLabel("");
    lblClose.addMouseListener(this);
    lblClose.setHorizontalTextPosition(SwingConstants.CENTER);
    lblClose.setIcon(IMG.ICON__CLOSE_NORMAL.icon());
    titlePanel.add(lblClose, BorderLayout.EAST);
  }

  @Override
  public void mouseClicked(final MouseEvent e) {
    if (closeListener != null) {
      closeListener.closePressed();
    }
  }

  @Override
  public void mouseEntered(final MouseEvent e) {
    lblClose.setIcon(IMG.ICON__CLOSE_OVER.icon());
  }

  @Override
  public void mouseExited(final MouseEvent e) {
    lblClose.setIcon(IMG.ICON__CLOSE_NORMAL.icon());
  }

  @Override
  public void mousePressed(final MouseEvent e) {
    lblClose.setIcon(IMG.ICON__CLOSE_PRESSED.icon());
  }

  @Override
  public void mouseReleased(final MouseEvent e) {
    lblClose.setIcon(IMG.ICON__CLOSE_NORMAL.icon());
  }

  @Override
  public void setCloseListener(final NotificationWindow notificationWindow) {
    closeListener = notificationWindow;
  }
}
