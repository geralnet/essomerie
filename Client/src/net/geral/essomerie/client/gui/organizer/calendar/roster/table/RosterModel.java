package net.geral.essomerie.client.gui.organizer.calendar.roster.table;

import java.util.ArrayList;

import net.geral.essomerie.client.gui.organizer.calendar.roster.RosterPanel;
import net.geral.essomerie.shared.roster.Roster;
import net.geral.essomerie.shared.roster.RosterEntry;
import net.geral.lib.table.GNTableModel;

import org.apache.log4j.Logger;

public class RosterModel extends GNTableModel<RosterEntry> {
  private static final long   serialVersionUID = 1L;
  private static final Logger logger           = Logger
                                                   .getLogger(RosterModel.class);
  private Roster              showing          = new Roster();

  private final RosterPanel   rosterPanel;

  public RosterModel(final RosterPanel panel) {
    super(true, true, true);
    rosterPanel = panel;
  }

  @Override
  protected RosterEntry changeEntry(final RosterEntry ria,
      final int columnIndex, final Object aValue) {
    logger.debug("Changed: " + ria + " (C" + columnIndex + ") to "
        + aValue.getClass() + " " + aValue);
    rosterPanel.rosterChanged();
    switch (columnIndex) {
      case 0:
        return new RosterEntry(0, (String) aValue, ria.getNamesString());
      case 1:
        return new RosterEntry(0, ria.getAssignment(), (String) aValue);
      default:
        return null;
    }
  }

  @Override
  public RosterEntry createNewEntry() {
    return new RosterEntry();
  }

  public Roster getRoster() {
    final Roster ri = new Roster(showing.getDate(), showing.isDayShift());

    final ArrayList<RosterEntry> all = getAll();
    for (final RosterEntry ria : all) {
      ri.addEntry(ria);
    }

    return ri;
  }

  @Override
  protected Object getValueFor(final RosterEntry ria, final int columnIndex) {
    return (columnIndex == 0) ? ria.getAssignment() : ria.getNamesString();
  }

  @Override
  public synchronized void remove(final int index) {
    super.remove(index);
    rosterPanel.rosterChanged();
  }

  @Override
  public synchronized void remove(final RosterEntry entry) {
    super.remove(entry);
    rosterPanel.rosterChanged();
  }

  public void setRoster(Roster ri) {
    if (ri == null) {
      ri = new Roster();
    }
    showing = ri;
    setData(ri.getEntries());
  }
}
