package net.geral.essomerie.client.gui.sysop.devices;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import net.geral.essomerie.client.gui.shared.label.TitleLabel;
import net.geral.essomerie.client.gui.sysop.devices.tables.InterfacesTable;
import net.geral.essomerie.shared.system.Device;

public class DeviceEditorPanel extends JPanel {
  private static final long     serialVersionUID = 1L;
  private final InterfacesTable interfacesTable;
  private final JTextArea       txtComments;
  private final JTextArea       txtInstructions;
  private final JTextArea       txtConfiguration;

  public DeviceEditorPanel() {
    setLayout(new BorderLayout(0, 0));

    final JPanel topPanel = new JPanel();
    add(topPanel, BorderLayout.NORTH);
    topPanel.setLayout(new BorderLayout(0, 0));

    final TitleLabel lblTitle = new TitleLabel("");
    topPanel.add(lblTitle);
    interfacesTable = new InterfacesTable();

    final JTabbedPane tabbedPane = new JTabbedPane(SwingConstants.TOP);
    add(tabbedPane, BorderLayout.CENTER);

    final JPanel interfacesPanel = new JPanel();
    tabbedPane.addTab("Interfaces", null, interfacesPanel, null);
    interfacesPanel.setLayout(new BorderLayout(0, 0));
    interfacesPanel.add(interfacesTable.getScroll());

    final JPanel commentsPanel = new JPanel();
    tabbedPane.addTab("Comments", null, commentsPanel, null);
    commentsPanel.setLayout(new BorderLayout(0, 0));

    final JScrollPane scrollComments = new JScrollPane();
    commentsPanel.add(scrollComments);

    txtComments = new JTextArea();
    scrollComments.setViewportView(txtComments);

    final JPanel instructionsPanel = new JPanel();
    tabbedPane.addTab("Instructions", null, instructionsPanel, null);
    instructionsPanel.setLayout(new BorderLayout(0, 0));

    final JScrollPane scrollInstructions = new JScrollPane();
    instructionsPanel.add(scrollInstructions);

    txtInstructions = new JTextArea();
    scrollInstructions.setViewportView(txtInstructions);

    final JPanel configurationPanel = new JPanel();
    tabbedPane.addTab("Configuration", null, configurationPanel, null);
    configurationPanel.setLayout(new BorderLayout(0, 0));

    final JScrollPane scrollConfiguration = new JScrollPane();
    configurationPanel.add(scrollConfiguration, BorderLayout.CENTER);

    txtConfiguration = new JTextArea();
    scrollConfiguration.setViewportView(txtConfiguration);

    final JPanel bottomPanel = new JPanel();
    final FlowLayout flowLayout = (FlowLayout) bottomPanel.getLayout();
    flowLayout.setAlignment(FlowLayout.RIGHT);
    flowLayout.setVgap(0);
    flowLayout.setHgap(0);
    add(bottomPanel, BorderLayout.SOUTH);

    final JPanel buttonsPanel = new JPanel();
    bottomPanel.add(buttonsPanel);
    buttonsPanel.setLayout(new GridLayout(1, 0, 0, 0));

    final JButton btnSave = new JButton("Save");
    buttonsPanel.add(btnSave);

    final JButton btnCancel = new JButton("Cancel");
    buttonsPanel.add(btnCancel);
  }

  public void setDevice(final Device d) {
    interfacesTable.getModel().setData(d == null ? null : d.getInterfaces());
    txtComments.setText(d == null ? "" : d.getComments());
    txtInstructions.setText(d == null ? "" : d.getInstructions());
    txtConfiguration.setText(d == null ? "" : d.getConfiguration());
  }
}
