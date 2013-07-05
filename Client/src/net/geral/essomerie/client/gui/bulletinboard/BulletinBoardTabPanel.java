package net.geral.essomerie.client.gui.bulletinboard;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EtchedBorder;

import net.geral.essomerie.client._gui.shared.label.TitleLabel;
import net.geral.essomerie.client._gui.shared.rtf.RTFPane;
import net.geral.essomerie.client.core.Client;
import net.geral.essomerie.client.core.events.Events;
import net.geral.essomerie.client.core.events.listeners.BulletionBoardListener;
import net.geral.essomerie.client.gui.main.TabPanel;
import net.geral.essomerie.client.resources.S;
import net.geral.essomerie.shared.bulletinboard.BulletinBoardEntry;
import net.geral.essomerie.shared.bulletinboard.BulletinBoardTitle;
import net.geral.lib.gui.button.ActionButton;

import org.apache.log4j.Logger;
import org.joda.time.format.DateTimeFormat;

public class BulletinBoardTabPanel extends TabPanel implements ActionListener,
    BulletionBoardListener {
  private static final Logger logger           = Logger
                                                   .getLogger(BulletinBoardTabPanel.class);
  private static final long   serialVersionUID = 1L;
  private BulletinBoardEntry  showing;
  private int                 showingId;
  private final RTFPane       rtfPane;
  private final JButton       btnEdit;
  private final JButton       btnDelete;
  private final TitleLabel    lblTitle;
  private final JLabel        lblCreatedBy     = new JLabel("[criado por] ");
  private final JButton       btnPrint;

  public BulletinBoardTabPanel() {
    this(0);
  }

  public BulletinBoardTabPanel(final int id) {
    showingId = id;
    setLayout(new BorderLayout(0, 0));

    lblTitle = new TitleLabel(S.BULLETINBOARD_LOADING.s(id));
    add(lblTitle, BorderLayout.NORTH);

    final JScrollPane scrollPane = new JScrollPane();
    add(scrollPane, BorderLayout.CENTER);

    rtfPane = new RTFPane();
    scrollPane.setViewportView(rtfPane);

    final JPanel panelS = new JPanel();
    add(panelS, BorderLayout.SOUTH);
    panelS.setLayout(new BorderLayout(0, 0));

    panelS.add(lblCreatedBy);
    lblCreatedBy.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
    lblCreatedBy.setText("");

    final JPanel panelButtons = new JPanel();
    panelS.add(panelButtons, BorderLayout.EAST);

    btnEdit = new ActionButton(S.BUTTON_EDIT.s(), 'E', "edit");
    btnEdit.addActionListener(this);
    panelButtons.setLayout(new GridLayout(1, 0, 0, 0));
    panelButtons.add(btnEdit);

    btnDelete = new ActionButton(S.BUTTON_DELETE.s(), KeyEvent.VK_DELETE,
        InputEvent.CTRL_MASK, "delete");
    btnDelete.addActionListener(this);

    btnPrint = new ActionButton(S.BUTTON_PRINT.s(), 'P', "print");
    btnPrint.addActionListener(this);
    panelButtons.add(btnPrint);
    panelButtons.add(btnDelete);
  }

  @Override
  public void actionPerformed(final ActionEvent e) {
    final Object o = e.getSource();
    if (o == btnEdit) {
      edit();
    } else if (o == btnDelete) {
      delete();
    } else if (o == btnPrint) {
      print();
    }
  }

  @Override
  public void bulletinBoardEntryAdded(final BulletinBoardTitle title) {
  }

  @Override
  public void bulletinBoardEntryChanged(final int from,
      final BulletinBoardTitle to) {
    if (showingId == from) {
      // if the entry was changed, replace it
      showingId = to.getId();
      try {
        Client.connection().bulletinBoard().requestFullContents(showingId);
      } catch (final IOException e) {
        logger.warn(e, e);
      }
    }
  }

  @Override
  public void bulletinBoardEntryDeleted(final int idbbe) {
    if (showingId == idbbe) {
      close(true);
    }
  }

  @Override
  public void bulletinBoardEntryReceived(final BulletinBoardEntry bbe) {
    if (showingId == bbe.getId()) {
      setShowing(bbe);
    }
  }

  @Override
  public void bulletinBoardSaveSuccessful(final int oldId, final int newId) {
  }

  @Override
  public void bulletinBoardTitleListReceived(
      final BulletinBoardTitle[] newTitles) {
  }

  private void delete() {
    final int res = JOptionPane.showConfirmDialog(this, S.BULLETINBOARD_DELETE,
        S.TITLE_CONFIRM.s(), JOptionPane.YES_NO_OPTION);
    if (res != JOptionPane.YES_OPTION) {
      return;
    }

    try {
      Client.connection().bulletinBoard().requestDelete(showingId);
    } catch (final Exception e) {
      logger.warn(e, e);
    }
  }

  private void edit() {
    if (showing != null) {
      Client.window().openTab(new BulletinBoardEditorTabPanel(showing));
      Client.window().closeTab(this);
    }
  }

  public int getShowingId() {
    return showingId;
  }

  @Override
  public String getTabText() {
    return Client.cache().bulletinBoard().getTitle(showingId);
  }

  private void print() {
    rtfPane.imprimir();
  }

  private void setShowing(final BulletinBoardEntry bbe) {
    if (bbe == null) {
      return; // not loaded yet
    }
    showingId = bbe.getId();
    showing = bbe;
    Client.window().setTabTitle(this, showing.getTitle());
    lblTitle.setText(showing.getTitle());
    rtfPane.setRTF(showing.getRtfContents());
    lblCreatedBy.setText(" "
        + S.BULLETINBOARD_CREATED_BY.s(
            Client.cache().users().get(showing.getCreatedBy()).getName(),
            showing.getCreatedWhen().toString(
                DateTimeFormat.forPattern(S.FORMAT_DATETIME_SIMPLE_NO_SECONDS
                    .s()))) + " ");
  }

  @Override
  public void tabClosed() {
    Events.bulletinBoard().removeListener(this);
  }

  @Override
  public boolean tabCloseRequest() {
    return true;
  }

  @Override
  public void tabCreated() {
    Events.bulletinBoard().addListener(this);
    try {
      Client.connection().bulletinBoard().requestFullContents(showingId);
    } catch (final IOException e) {
      logger.warn(e, e);
    }
  }
}
