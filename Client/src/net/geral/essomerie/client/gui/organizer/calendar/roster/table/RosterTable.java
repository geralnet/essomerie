package net.geral.essomerie.client.gui.organizer.calendar.roster.table;

import net.geral.essomerie.client.core.Client;
import net.geral.essomerie.client.core.configuration.CoreConfiguration;
import net.geral.essomerie.client.gui.organizer.calendar.roster.RosterPanel;
import net.geral.essomerie.client.resources.S;
import net.geral.essomerie.shared.roster.Roster;
import net.geral.lib.table.GNTable;

public class RosterTable extends GNTable<RosterModel> {
  private static final long serialVersionUID = 1L;

  public RosterTable(final RosterPanel panel) {
    super(new RosterModel(panel));

    initialSort(0, false);
  }

  @Override
  protected void createColumns() {
    final CoreConfiguration c = Client.config();
    createColumn(S.ORGANIZER_CALENDAR_ROSTER_HEADER_ASSIGNMENT.s(),
        c.TableColumnWidth_Organizer_Roster_Asssignment,
        c.TableColumnWidth_Default);
    createColumn(S.ORGANIZER_CALENDAR_ROSTER_HEADER_EMPLOYEES.s(),
        c.TableColumnWidth_Organizer_Roster_Employees,
        c.TableColumnWidth_Default);
  }

  @Override
  protected boolean deleteClicked(final int viewRow, final int tableColumn) {
    return true;
  }

  @Override
  public String getNewEntryText(final int columnIndex) {
    if (columnIndex != 1) {
      return null;
    }
    return S.ORGANIZER_CALENDAR_ROSTER_NEW.s();
  }

  public void setRoster(final Roster ri) {
    if (isEditing()) {
      getCellEditor().cancelCellEditing();
    }
    getModel().setRoster(ri);
  }
}
