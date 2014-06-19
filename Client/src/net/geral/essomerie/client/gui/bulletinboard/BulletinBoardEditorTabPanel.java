package net.geral.essomerie.client.gui.bulletinboard;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import net.geral.essomerie.client.core.Client;
import net.geral.essomerie.client.core.events.Events;
import net.geral.essomerie.client.core.events.listeners.BulletionBoardListener;
import net.geral.essomerie.client.gui.main.TabPanel;
import net.geral.essomerie.client.gui.shared.label.ErroLabel;
import net.geral.essomerie.client.gui.shared.rtf.RTFEditorPanel;
import net.geral.essomerie.client.resources.S;
import net.geral.essomerie.shared.bulletinboard.BulletinBoardEntry;
import net.geral.essomerie.shared.bulletinboard.BulletinBoardTitle;
import net.geral.lib.gui.button.ActionButton;
import net.geral.lib.util.StringUtils;

import org.apache.log4j.Logger;

public class BulletinBoardEditorTabPanel extends TabPanel implements
    ActionListener, BulletionBoardListener, KeyListener {
  private static final Logger      logger           = Logger
                                                        .getLogger(BulletinBoardEditorTabPanel.class);
  private static final long        serialVersionUID = 1L;
  private final JTextField         txtTitle;
  private final BulletinBoardEntry editing;
  private final RTFEditorPanel     editor;
  private final ErroLabel          lblErrorLabel;
  private final JButton            btnSave;

  public BulletinBoardEditorTabPanel() {
    this(null);
  }

  public BulletinBoardEditorTabPanel(final BulletinBoardEntry info) {
    editing = info;
    setLayout(new BorderLayout(0, 0));

    final JPanel panelN = new JPanel();
    panelN.setBorder(new EmptyBorder(5, 5, 5, 5));
    add(panelN, BorderLayout.NORTH);
    panelN.setLayout(new BorderLayout(0, 0));

    txtTitle = new JTextField();
    txtTitle.addKeyListener(this);

    final JPanel panelNTitle = new JPanel();
    panelN.add(panelNTitle, BorderLayout.NORTH);
    panelNTitle.setLayout(new BorderLayout(0, 0));

    final JLabel lblTitle = new JLabel(S.BULLETINBOARD_EDIT_TITLE.s());
    panelNTitle.add(lblTitle, BorderLayout.WEST);
    lblTitle.setFont(lblTitle.getFont().deriveFont(
        lblTitle.getFont().getStyle() | Font.BOLD));

    final JLabel lblTitleInstructions = new JLabel(
        S.BULLETINBOARD_EDIT_TITLE_INSTRUCTIONS.s(String
            .valueOf(BulletinBoardTitle.PATH_SEPARATOR)));
    panelNTitle.add(lblTitleInstructions, BorderLayout.EAST);
    panelN.add(txtTitle, BorderLayout.CENTER);

    editor = new RTFEditorPanel();
    add(editor, BorderLayout.CENTER);

    final JPanel panelS = new JPanel();
    add(panelS, BorderLayout.SOUTH);
    panelS.setLayout(new BorderLayout(0, 0));

    final JPanel panelSW = new JPanel();
    panelS.add(panelSW, BorderLayout.WEST);

    lblErrorLabel = new ErroLabel("*");
    panelSW.add(lblErrorLabel);

    final JPanel panelSE = new JPanel();
    panelS.add(panelSE, BorderLayout.EAST);
    panelSE.setLayout(new GridLayout(1, 0, 0, 0));

    btnSave = new ActionButton(S.BUTTON_SAVE.s(), 'S', "save");
    panelSE.add(btnSave);
    btnSave.addActionListener(this);

    final JButton btnCancel = new ActionButton(S.BUTTON_CANCEL.s(),
        KeyEvent.VK_ESCAPE, 0, "cancel");
    panelSE.add(btnCancel);
    btnCancel.addActionListener(this);
  }

  @Override
  public void actionPerformed(final ActionEvent e) {
    if (!checkAction(e.getActionCommand(), e.getSource())) {
      logger.warn("Invalid action: " + e.getActionCommand(), new Exception());
    }
  }

  @Override
  public void bulletinBoardEntryAdded(final BulletinBoardTitle title) {
  }

  @Override
  public void bulletinBoardEntryChanged(final int from,
      final BulletinBoardTitle to) {
    if ((editing == null) || (editing.getId() != from)) {
      return;
    }

    lblErrorLabel.setText(S.BULLETINBOARD_OUTDATED.s());
    lblErrorLabel.setVisible(true);
  }

  @Override
  public void bulletinBoardEntryDeleted(final int idinformacao) {
    if ((editing == null) || (editing.getId() != idinformacao)) {
      return;
    }

    lblErrorLabel.setVisible(true);
    lblErrorLabel.setText(S.BULLETINBOARD_DELETED.s());
  }

  @Override
  public void bulletinBoardEntryReceived(final BulletinBoardEntry bbe) {
  }

  @Override
  public void bulletinBoardSaveSuccessful(final int oldId, final int newId) {
    // FIXME if more than one editing, will close all
    // make some ID to identify the save request
    Client.window().openTab(new BulletinBoardTabPanel(newId));
    close(true);
  }

  @Override
  public void bulletinBoardTitleListReceived(
      final BulletinBoardTitle[] newTitles) {
  }

  private boolean cancel() {
    if (Client.window().closeTab(this, false)) {
      Client.window().openTab(new BulletinBoardTabPanel(editing.getId()));
    }
    return true;
  }

  private boolean checkAction(final String cmd, final Object source) {
    if ("save".equals(cmd)) {
      return save();
    }
    if ("cancel".equals(cmd)) {
      return cancel();
    }
    return false;
  }

  @Override
  public String getTabText() {
    return editing == null ? S.BULLETINBOARD_NEW.s()
        : S.BULLETINBOARD_EDIT_TAB_TITLE.s(txtTitle.getText());
  }

  private boolean hasChanged() {
    // new
    if (editing == null) {
      if (txtTitle.getText().length() > 0) {
        return true;
      }
      if (editor.hasChanged()) {
        return true;
      }
      return false;
    }
    // edit
    if (!editing.getFullTitle().equals(txtTitle.getText())) {
      return true;
    }
    if (editor.hasChanged()) {
      return true;
    }
    return false;
  }

  @Override
  public void keyPressed(final KeyEvent e) {
  }

  @Override
  public void keyReleased(final KeyEvent e) {
    // TODO send event that requests its title again
    Client.window().setTabTitle(this,
        S.BULLETINBOARD_EDIT_TAB_TITLE.s(txtTitle.getText()));
  }

  @Override
  public void keyTyped(final KeyEvent e) {
  }

  private boolean save() {
    try {
      // prepare & check title
      String title = txtTitle.getText();
      title = StringUtils.trim(title);
      txtTitle.setText(title);
      if (title.length() == 0) {
        JOptionPane.showMessageDialog(this, S.BULLETINBOARD_INVALID_TITLE,
            S.TITLE_ERROR.s(), JOptionPane.ERROR_MESSAGE);
        return true;
      }
      // save
      final byte[] rtf = editor.getRTF();
      if (editing != null) {
        Client.connection().bulletinBoard()
            .requestSave(new BulletinBoardEntry(editing.getId(), title, rtf));
        // TODO Client.comm().requestInformacaoSubstituta(editando.id);
      } else {
        Client.connection().bulletinBoard()
            .requestSave(new BulletinBoardEntry(title, rtf));
      }
    } catch (final Exception e) {
      logger.warn(e, e);
    }
    return true;
  }

  @Override
  public void tabClosed() {
    Events.bulletinBoard().removeListener(this);
  }

  @Override
  public boolean tabCloseRequest() {
    if (!hasChanged()) {
      return true;
    }

    final int res = JOptionPane.showConfirmDialog(this, S.BULLETINBOARD_CANCEL,
        S.TITLE_CONFIRM.s(), JOptionPane.YES_NO_OPTION,
        JOptionPane.WARNING_MESSAGE);
    return res == JOptionPane.YES_OPTION;
  }

  @Override
  public void tabCreated() {
    lblErrorLabel.setVisible(false);
    Events.bulletinBoard().addListener(this);
    if (editing != null) {
      txtTitle.setText(editing.getFullTitle().substring(1));
      editor.setRTF(editing.getRtfContents());
    }
    txtTitle.requestFocus();
  }
}
