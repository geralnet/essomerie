package net.geral.essomerie.client.gui.organizer.calendar.roster;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import net.geral.essomerie._shared.roster.RosterInfo;
import net.geral.essomerie.client.core.Client;
import net.geral.essomerie.client.gui.organizer.calendar.roster.table.RosterTable;
import net.geral.essomerie.client.resources.S;

import org.apache.log4j.Logger;

public class RosterPanel extends JPanel implements ActionListener {
  private static final Logger logger           = Logger
                                                   .getLogger(RosterPanel.class);
  private static final long   serialVersionUID = 1L;
  private final RosterTable   table            = new RosterTable(this);
  private final JPanel        panelButtons;

  public RosterPanel() {
    setLayout(new BorderLayout(0, 0));
    setPreferredSize(new Dimension(284, 152));

    add(table.getScroll(), BorderLayout.CENTER);

    panelButtons = new JPanel();
    panelButtons.setVisible(false);
    final FlowLayout fl_panelBotoes = (FlowLayout) panelButtons.getLayout();
    fl_panelBotoes.setAlignment(FlowLayout.TRAILING);
    add(panelButtons, BorderLayout.SOUTH);

    final JButton btnSave = new JButton(S.BUTTON_SAVE.s());
    btnSave.setActionCommand("save");
    btnSave.addActionListener(this);
    panelButtons.add(btnSave);

    final JButton btnCancel = new JButton(S.BUTTON_CANCEL.s());
    btnCancel.setActionCommand("cancel");
    btnCancel.addActionListener(this);
    panelButtons.add(btnCancel);
  }

  @Override
  public void actionPerformed(final ActionEvent e) {
    if ("save".equals(e.getActionCommand())) {
      save();
    } else if ("cancel".equals(e.getActionCommand())) {
      cancel();
    }
  }

  private void cancel() {
    panelButtons.setVisible(false);
    final RosterInfo ri = table.getModel().getRoster();
    try {
      Client.connection().calendar()
          .requestRoster(ri.getDate(), ri.getDayShift());
    } catch (final IOException e) {
      logger.warn(e, e);
    }
  }

  public void rosterChanged() {
    panelButtons.setVisible(true);
  }

  private void save() {
    try {
      Client.connection().calendar()
          .requestRosterSave(table.getModel().getRoster());
      panelButtons.setVisible(false);
    } catch (final IOException e) {
      logger.warn(e, e);
    }
  }

  public void set(final RosterInfo re) {
    if (panelButtons.isVisible()) {
      final int i = JOptionPane.showConfirmDialog(this,
          S.ORGANIZER_CALENDAR_ROSTER_SAVE, S.TITLE_CONFIRM.s(),
          JOptionPane.YES_NO_OPTION);
      if (i == JOptionPane.YES_OPTION) {
        save();
      }
      panelButtons.setVisible(false);
    }
    table.setRoster(re);
  }
}
