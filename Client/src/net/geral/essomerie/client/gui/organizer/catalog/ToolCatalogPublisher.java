package net.geral.essomerie.client.gui.organizer.catalog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import net.geral.essomerie.client.core.Client;
import net.geral.essomerie.client.core.events.Events;
import net.geral.essomerie.client.core.events.listeners.CatalogListener;
import net.geral.essomerie.client.gui.main.TabPanel;
import net.geral.essomerie.client.resources.S;
import net.geral.essomerie.shared.catalog.CatalogGroup;
import net.geral.essomerie.shared.catalog.CatalogItem;
import net.geral.essomerie.shared.catalog.CatalogPublication;
import net.geral.lib.strings.GNStrings;

import org.apache.log4j.Logger;

public class ToolCatalogPublisher extends TabPanel implements CatalogListener,
    DocumentListener {
  private static final long   serialVersionUID = 1L;
  private static final Logger logger           = Logger
                                                   .getLogger(ToolCatalogPublisher.class);

  private final JTextField    txtComments;
  private final JButton       btnPublish;
  private final JButton       btnCancel;

  public ToolCatalogPublisher() {
    final GridBagLayout gridBagLayout = new GridBagLayout();
    gridBagLayout.columnWidths = new int[] { 0, 0, 0, 0 };
    gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0 };
    gridBagLayout.columnWeights = new double[] { 1.0, 0.0, 1.0,
        Double.MIN_VALUE };
    gridBagLayout.rowWeights = new double[] { 1.0, 0.0, 1.0, Double.MIN_VALUE };
    setLayout(gridBagLayout);

    final JPanel panelCentered = new JPanel();
    final GridBagConstraints gbc_panelCentered = new GridBagConstraints();
    gbc_panelCentered.insets = new Insets(0, 0, 5, 5);
    gbc_panelCentered.fill = GridBagConstraints.BOTH;
    gbc_panelCentered.gridx = 1;
    gbc_panelCentered.gridy = 1;
    add(panelCentered, gbc_panelCentered);
    panelCentered.setLayout(new BorderLayout(0, 0));

    final JPanel panelWarning = new JPanel();
    panelCentered.add(panelWarning, BorderLayout.NORTH);
    panelWarning.setLayout(new BorderLayout(0, 0));

    final JLabel lblWarning = new JLabel(
        S.ORGANIZER_CATALOG_PUBLISH_WARNING.s());
    lblWarning.setForeground(Color.RED);
    lblWarning.setHorizontalAlignment(SwingConstants.CENTER);
    lblWarning.setFont(lblWarning.getFont().deriveFont(
        lblWarning.getFont().getStyle() | Font.BOLD, 40f));
    panelWarning.add(lblWarning, BorderLayout.NORTH);

    final JLabel lblUndone = new JLabel(
        S.ORGANIZER_CATALOG_PUBLISH_CANNOT_REVERT.s());
    lblUndone.setFont(lblUndone.getFont().deriveFont(
        lblUndone.getFont().getStyle() | Font.BOLD,
        lblUndone.getFont().getSize() + 2f));
    lblUndone.setHorizontalAlignment(SwingConstants.CENTER);
    lblUndone.setBorder(new EmptyBorder(5, 0, 10, 0));
    panelWarning.add(lblUndone, BorderLayout.SOUTH);

    final JPanel panelComments = new JPanel();
    panelComments.setBorder(new EmptyBorder(5, 0, 5, 0));
    panelCentered.add(panelComments, BorderLayout.CENTER);
    panelComments.setLayout(new BorderLayout(0, 0));

    final JLabel lblComments = new JLabel(
        S.ORGANIZER_CATALOG_PUBLISH_COMMENTS.s());
    panelComments.add(lblComments, BorderLayout.NORTH);

    txtComments = new JTextField();
    panelComments.add(txtComments, BorderLayout.SOUTH);
    txtComments.setColumns(50);
    txtComments.getDocument().addDocumentListener(this);

    final JPanel panelButtons = new JPanel();
    panelCentered.add(panelButtons, BorderLayout.SOUTH);
    panelButtons.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));

    final JPanel panelButtonsRight = new JPanel();
    panelButtons.add(panelButtonsRight);
    panelButtonsRight.setLayout(new GridLayout(1, 0, 5, 0));

    btnPublish = new JButton(S.ORGANIZER_CATALOG_PUBLISH_GO.s());
    panelButtonsRight.add(btnPublish);
    btnPublish.setEnabled(false);

    btnCancel = new JButton(S.BUTTON_CANCEL.s());
    panelButtonsRight.add(btnCancel);
    btnCancel.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(final ActionEvent e) {
        close(true);
      }
    });
    btnPublish.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(final ActionEvent e) {
        publish();
      }
    });
  }

  @Override
  public void catalogCreateGroupSuccessful(final int idgroup) {
  }

  @Override
  public void catalogCreateItemSuccessful(final int iditem) {
  }

  @Override
  public void catalogGroupAdded(final CatalogGroup group) {
  }

  @Override
  public void catalogGroupSaved(final int oldId, final CatalogGroup group) {
  }

  @Override
  public void catalogItemAdded(final CatalogItem item) {
  }

  @Override
  public void catalogItemSaved(final int oldId, final CatalogItem item) {
  }

  @Override
  public void catalogLatestPublishedIdChanged(final int oldId, final int newId) {
  }

  @Override
  public void catalogPublicationListReceived() {
  }

  @Override
  public void catalogPublicationReceived(final boolean latest,
      final int idpublication) {
  }

  @Override
  public void catalogPublished(final CatalogPublication publication) {
  }

  @Override
  public void catalogPublishSuccessful(final int idpublication) {
    JOptionPane.showMessageDialog(this,
        S.ORGANIZER_CATALOG_PUBLISH_SUCCESS.s(idpublication),
        S.TITLE_SUCCESS.s(), JOptionPane.INFORMATION_MESSAGE);
    close(false);
  }

  @Override
  public void catalogRemovedGroups(final int idpublication,
      final ArrayList<Integer> arrayList) {
  }

  @Override
  public void catalogRemovedItems(final int idpublication,
      final ArrayList<Integer> arrayList) {
  }

  @Override
  public void changedUpdate(final DocumentEvent e) {
    commentsChanged();
  }

  private void commentsChanged() {
    final boolean empty = (GNStrings.trim(txtComments.getText()).length() == 0);
    btnPublish.setEnabled(txtComments.isEnabled() && (!empty));
  }

  @Override
  public String getTabText() {
    return S.ORGANIZER_CATALOG_PUBLISH_TITLE.s();
  }

  @Override
  public void insertUpdate(final DocumentEvent e) {
    commentsChanged();
  }

  private void publish() {
    try {
      Client.connection().catalog()
          .requestPublish(GNStrings.trim(txtComments.getText()));
      txtComments.setEnabled(false);
      btnPublish.setEnabled(false);
      btnPublish.setEnabled(false);
    } catch (final IOException e) {
      logger.warn(e, e);
    }
  }

  @Override
  public void removeUpdate(final DocumentEvent e) {
    commentsChanged();
  }

  @Override
  public void tabClosed() {
    Events.catalog().removeListener(this);
  }

  @Override
  public boolean tabCloseRequest() {
    return true;
  }

  @Override
  public void tabCreated() {
    Events.catalog().addListener(this);
  }
}
