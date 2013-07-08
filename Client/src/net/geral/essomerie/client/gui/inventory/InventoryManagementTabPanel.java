package net.geral.essomerie.client.gui.inventory;

import java.awt.BorderLayout;
import java.awt.print.PrinterException;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.geral.essomerie._shared.contagem.Inventory;
import net.geral.essomerie._shared.contagem.InventoryGroup;
import net.geral.essomerie._shared.contagem.InventoryItem;
import net.geral.essomerie._shared.contagem.InventoryLog;
import net.geral.essomerie._shared.contagem.InventoryLogEntry;
import net.geral.essomerie.client._printing.ResumoAcertosPrint;
import net.geral.essomerie.client.core.Client;
import net.geral.essomerie.client.core.events.Events;
import net.geral.essomerie.client.core.events.listeners.InventoryListener;
import net.geral.essomerie.client.gui.inventory.items.InventoryItemsPanel;
import net.geral.essomerie.client.gui.inventory.table.InventoryTable;
import net.geral.essomerie.client.gui.main.TabPanel;
import net.geral.essomerie.client.gui.shared.label.TitleLabel;
import net.geral.essomerie.client.resources.S;
import net.geral.lib.printing.PrintSupport;

import org.apache.log4j.Logger;
import org.joda.time.LocalDate;

public class InventoryManagementTabPanel extends TabPanel implements
    InventoryListener, ListSelectionListener {

  private static final Logger        logger           = Logger
                                                          .getLogger(InventoryManagementTabPanel.class);
  private static final long          serialVersionUID = 1L;

  private final InventoryTable       inventoryTable   = new InventoryTable();
  private final InventoryChangePanel changePanel;
  private final InventoryItemsPanel  itemsPanel;
  private Inventory                  inventory        = new Inventory();
  private InventoryGroup             lastGroup        = null;

  public InventoryManagementTabPanel() {
    setLayout(new BorderLayout(0, 0));

    final JLabel lblTitle = new TitleLabel(S.INVENTORY_ADJUSTMENT_TITLE.s());
    add(lblTitle, BorderLayout.NORTH);

    final JPanel mainPanel = new JPanel();
    add(mainPanel, BorderLayout.CENTER);
    mainPanel.setLayout(new BorderLayout(0, 0));

    itemsPanel = new InventoryItemsPanel(this);
    mainPanel.add(itemsPanel, BorderLayout.WEST);

    changePanel = new InventoryChangePanel(inventoryTable);
    inventoryTable.getSelectionModel().addListSelectionListener(this);

    final JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
        inventoryTable.getScroll(), changePanel);
    splitPane.setResizeWeight(1.0);
    mainPanel.add(splitPane, BorderLayout.CENTER);
  }

  //
  // @Override
  // public void contagemHistoricoRecebido(final LocalDate de, final LocalDate
  // ate,
  // final ContagemHistoricoRegistro[] registros) {
  // if (de.equals(imprimir_de) && ate.equals(imprimir_ate)) {
  // imprimirAcertosGo(registros);
  // }
  // }

  public Inventory getInventory() {
    return inventory;
  }

  @Override
  public String getTabText() {
    return "Contagem";
  }

  public void groupChanged() {
    final InventoryGroup group = itemsPanel.getSelectedGroup();
    if (lastGroup == group) {
      return;
    }
    lastGroup = group;

    final int selected = inventoryTable.getSelectedRow();
    inventoryTable.getSelectionModel().setSelectionInterval(selected, selected);
    inventoryTable.getModel().setData(inventory.getItens(group));
  }

  @Override
  public void inventoryFullDataReceived(final Inventory c) {
    setInventory(c);
  }

  @Override
  public void inventoryLogByDateReceived(final LocalDate from,
      final LocalDate until, final InventoryLogEntry[] entries) {
    final ResumoAcertosPrint p = new ResumoAcertosPrint(entries, inventory);
    try {
      PrintSupport.print(p);
    } catch (final PrinterException e) {
      logger.warn(e, e);
    }
  }

  @Override
  public void inventoryLogByItemReceived(final InventoryLog log) {
    final InventoryItem ii = inventoryTable.getSelected();
    if (ii == null) {
      return;
    }
    if (ii.id == log.iditem) {
      changePanel.setHistorico(log);
    }
  }

  @Override
  public void inventoryQuantityChanged(final int iditem, final float newQuantity) {
    inventory.setNovaQuantidade(iditem, newQuantity);
    inventoryTable.getModel().refreshItem(iditem);
  }

  public void printLog() {
    final LocalDate printFrom = LocalDate.now().minusDays(1);
    final LocalDate printUntil = LocalDate.now();
    try {
      Client.connection().inventory()
          .requestChangeLogByDate(printFrom, printUntil);
    } catch (final IOException e) {
      logger.warn(e, e);
    }
  }

  public void setInventory(final Inventory c) {
    inventory = c;
    itemsPanel.updateGroups();
    changePanel.setMotivos(c.getMotivos());
  }

  @Override
  public void tabClosed() {
    Events.inventory().removeListener(this);
    changePanel.stop();
  }

  @Override
  public boolean tabCloseRequest() {
    return true;
  }

  @Override
  public void tabCreated() {
    Events.inventory().addListener(this);
    try {
      Client.connection().inventory().requestInventoryData();
    } catch (final IOException e) {
      logger.warn(e, e);
    }
  }

  @Override
  public void valueChanged(final ListSelectionEvent e) {
    if (e.getValueIsAdjusting()) {
      return;
    }
    changePanel.setItem(inventoryTable.getSelected());
    inventoryTable.scrollToSelected();
  }
}
