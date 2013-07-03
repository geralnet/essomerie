package net.geral.essomerie.client._gui.sysadmin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.geral.essomerie._shared.dispositivos.DispositivoMonitor;
import net.geral.essomerie.client.gui.main.TabPanel;

import org.apache.log4j.Logger;

public class ScreenLogTabPanel extends TabPanel implements
    ListSelectionListener {
  private static final long                               serialVersionUID = 1L;
  private static final Logger                             logger           = Logger
                                                                               .getLogger(ScreenLogTabPanel.class);

  private final DefaultListModel<DispositivoMonitorEntry> listDispositivosModel;
  private final JList<DispositivoMonitorEntry>            listDispositivos;
  private final DefaultListModel<DispositivoMonitor>      listDataHoraModel;
  private final JList<DispositivoMonitor>                 listDataHora;
  // private final ScreenLogCache cache = new ScreenLogCache();
  private final JLabel                                    lblScreen;
  private final JLabel                                    lblLegenda;
  private final JCheckBox                                 cbReduzir;

  public ScreenLogTabPanel() {
    setLayout(new BorderLayout(0, 0));

    final JSplitPane splitRight = new JSplitPane();
    add(splitRight);

    final JSplitPane splitLeft = new JSplitPane();
    splitRight.setLeftComponent(splitLeft);

    listDispositivosModel = new DefaultListModel<DispositivoMonitorEntry>();
    listDispositivos = new JList<DispositivoMonitorEntry>(listDispositivosModel);
    listDispositivos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    listDispositivos.getSelectionModel().addListSelectionListener(this);

    final JScrollPane scrollDispositivos = new JScrollPane();
    scrollDispositivos
        .setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    scrollDispositivos.setViewportView(listDispositivos);
    splitLeft.setLeftComponent(scrollDispositivos);

    final JScrollPane scrollDataHora = new JScrollPane();
    scrollDataHora
        .setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    splitLeft.setRightComponent(scrollDataHora);

    listDataHoraModel = new DefaultListModel<DispositivoMonitor>();
    listDataHora = new JList<>(listDataHoraModel);
    listDataHora.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    listDataHora.getSelectionModel().addListSelectionListener(this);
    scrollDataHora.setViewportView(listDataHora);

    splitRight.setDividerLocation(400);
    splitLeft.setDividerLocation(180);

    final JPanel panelRight = new JPanel();
    splitRight.setRightComponent(panelRight);
    panelRight.setLayout(new BorderLayout(0, 0));

    final JScrollPane scrollScreen = new JScrollPane();
    panelRight.add(scrollScreen, BorderLayout.CENTER);

    lblScreen = new JLabel("");
    lblScreen.setHorizontalAlignment(SwingConstants.CENTER);
    lblScreen.setBackground(Color.WHITE);
    lblScreen.setOpaque(true);
    scrollScreen.setViewportView(lblScreen);

    final JPanel panelRightBottom = new JPanel();
    panelRight.add(panelRightBottom, BorderLayout.SOUTH);
    panelRightBottom.setLayout(new BorderLayout(0, 0));

    lblLegenda = new JLabel("[Selecionar um dispositivo e registro]");
    panelRightBottom.add(lblLegenda);

    cbReduzir = new JCheckBox("Reduzir");
    cbReduzir.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(final ActionEvent e) {
        datahoraValueChanged();
      }
    });
    panelRightBottom.add(cbReduzir, BorderLayout.EAST);
  }

  // private Icon createIcon(final byte[] bytes) {
  // if (bytes == null) {
  // return null;
  // }
  // final ImageIcon icon = new ImageIcon(bytes);
  // if (cbReduzir.isSelected()) {
  // final int w = icon.getIconWidth() / 2;
  // final int h = icon.getIconHeight() / 2;
  // Image img;
  // img = icon.getImage().getScaledInstance(w, h, 1);
  // icon.setImage(img);
  // }
  // return icon;
  // }

  private void datahoraValueChanged() {
    // final DispositivoMonitor m = listDataHora.getSelectedValue();
    // if (m == null) return;
    // if (!m.hasScreen) {
    // showScreen(null);
    // }
    // else {
    // final byte[] imagem = cache.get(m.id);
    // if (imagem == null) {
    // try {
    // Client.connection().requestSysAdminDispositivosMonitorScreen(m.id);
    // }
    // catch (final IOException e) {
    // Log4J.warning(e);
    // }
    // showScreen(null);
    // }
    // else {
    // showScreen(imagem);
    // }
    // }
  }

  private void dispositivosValueChanged() {
    final DispositivoMonitorEntry e = listDispositivos.getSelectedValue();
    listDataHoraModel.clear();
    if (!e.hasDetails()) {
      return;
    }

    for (final DispositivoMonitor m : e.monitor) {
      listDataHoraModel.addElement(m);
    }
  }

  @Override
  public String getTabText() {
    return "Screen Log";
  }

  // private void setDispositivoMonitor(final int iddispositivo,
  // final DispositivoMonitor[] monitor) {
  // final int n = listDispositivosModel.getSize();
  // for (int i = 0; i < n; i++) {
  // final DispositivoMonitorEntry e = listDispositivosModel.get(i);
  // if (e.checkId(iddispositivo)) {
  // e.monitor = monitor;
  // listDispositivosModel.setElementAt(e, i);// fire changed
  // return;
  // }
  // }
  // }

  // private void showScreen(final byte[] screen) {
  // final DispositivoMonitorEntry e = listDispositivos.getSelectedValue();
  // final DispositivoMonitor dm = listDataHora.getSelectedValue();
  // final String desc = "ScreenLog de " + e.dispositivo.nome + " ("
  // + e.dispositivo.id + ")" + " em " + dm.datahora.toString() + " ("
  // + dm.id + "), uptime: " + dm.uptime;
  // lblLegenda.setText(desc);
  // lblScreen.setIcon(createIcon(screen));
  // if (screen == null) {
  // if (dm.hasScreen) {
  // lblScreen.setText("[carregando imagem]");
  // } else {
  // lblScreen.setText("[sem imagem]");
  // }
  // } else {
  // lblScreen.setText("");
  // }
  // }

  // @Override
  // public void sysadminScreenlogDispositivoMonitor(final int iddispositivo,
  // final DispositivoMonitor[] monitor) {
  // USE EDT.run SwingUtilities.invokeLater(new Runnable() {
  // @Override
  // public void run() {
  // setDispositivoMonitor(iddispositivo, monitor);
  // updateDispositivosNext();
  // }
  // });
  // }
  //
  // @Override
  // public void sysadminScreenlogDispositivos(final Dispositivo[] dispositivos)
  // {
  // USE EDT.run SwingUtilities.invokeLater(new Runnable() {
  // @Override
  // public void run() {
  // listDispositivos.removeAll();
  // listDispositivosModel.clear();
  // listDispositivosModel.addElement(new DispositivoMonitorEntry());
  // for (final Dispositivo d : dispositivos) {
  // listDispositivosModel.addElement(new DispositivoMonitorEntry(d));
  // }
  // updateDispositivosNext();
  // }
  // });
  // }
  //
  // @Override
  // public void sysadminScreenlogDispositivosMonitorScreen(final int
  // idDispositivoMonitor, final byte[] screen) {
  // cache.add(idDispositivoMonitor, screen);
  // final DispositivoMonitor dm = listDataHora.getSelectedValue();
  // if (dm == null) { return; }
  // if (dm.id != idDispositivoMonitor) { return; }
  // showScreen(screen);
  // }

  @Override
  public void tabClosed() {
    // Events.SysAdmin.addListener(this);
  }

  @Override
  public boolean tabCloseRequest() {
    return true;
  }

  @Override
  public void tabCreated() {
    // Events.SysAdmin.addListener(this);
    // try {
    // Client.connection().requestSysAdminDispositivos();
    // }
    // catch (final IOException e) {
    // Log4J.warning(e);
    // }
  }

  // private void updateDispositivosNext() {
  // // try {
  // // final int n = listDispositivosModel.getSize();
  // // for (int i = 0; i < n; i++) {
  // // final DispositivoMonitorEntry d = listDispositivosModel.getElementAt(i);
  // // if (!d.hasDetails()) {
  // //
  // Client.connection().requestSysAdminDispositivosMonitor(d.dispositivo.id);
  // // return; //get one and wait
  // // }
  // // }
  // // }
  // // catch (final IOException e) {
  // // Log4J.warning(e);
  // // }
  // }

  @Override
  public void valueChanged(final ListSelectionEvent e) {
    if (e.getValueIsAdjusting()) {
      return;
    }
    final Object src = e.getSource();
    if (src == listDispositivos.getSelectionModel()) {
      dispositivosValueChanged();
    } else if (src == listDataHora.getSelectionModel()) {
      datahoraValueChanged();
    } else {
      logger.warn("Invalid source: " + src);
    }
  }
}
