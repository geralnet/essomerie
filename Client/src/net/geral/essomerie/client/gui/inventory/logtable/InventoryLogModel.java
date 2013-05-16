package net.geral.essomerie.client.gui.inventory.logtable;

import net.geral.essomerie._shared.contagem.InventoryLogEntry;
import net.geral.essomerie.client.core.Client;
import net.geral.essomerie.client.gui.inventory.InventoryChangePanel;
import net.geral.essomerie.client.resources.S;
import net.geral.lib.table.GNTableModel;

import org.joda.time.format.DateTimeFormat;

public class InventoryLogModel extends GNTableModel<InventoryLogEntry> {
  private static final long          serialVersionUID = 1L;
  private final InventoryChangePanel changePanel;

  public InventoryLogModel(final InventoryChangePanel panel) {
    super(false, false, false);
    changePanel = panel;
  }

  @Override
  protected InventoryLogEntry changeEntry(final InventoryLogEntry t,
      final int columnIndex, final Object aValue) {
    return null;
  }

  @Override
  public InventoryLogEntry createNewEntry() {
    return null;
  }

  @Override
  protected Object getValueFor(final InventoryLogEntry log,
      final int columnIndex) {
    switch (columnIndex) {
      case 0:
        return log.datahora.toString(DateTimeFormat
            .forPattern(S.FORMAT_DATETIME_SIMPLE_NO_SECONDS.s()));
      case 1:
        return Client.cache().users().get(log.idusuario).getUsername()
            .toUpperCase();
      case 2:
        return log.getAlteracaoString();
      case 3:
        final String motivo = changePanel.getMotivo(log.tipo, log.idmotivo);
        if (log.observacoes.length() == 0) {
          return motivo;
        }
        return motivo + ": " + log.observacoes;
      default:
        return "[" + columnIndex + "]";
    }
  }
}
