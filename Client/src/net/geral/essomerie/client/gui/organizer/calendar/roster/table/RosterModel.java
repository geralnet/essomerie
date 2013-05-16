package net.geral.essomerie.client.gui.organizer.calendar.roster.table;

import java.util.ArrayList;

import net.geral.essomerie._shared.roster.RosterInfo;
import net.geral.essomerie._shared.roster.RosterInfoAssignments;
import net.geral.essomerie.client.gui.organizer.calendar.roster.RosterPanel;
import net.geral.lib.table.GNTableModel;

import org.apache.log4j.Logger;

public class RosterModel extends GNTableModel<RosterInfoAssignments> {
  private static final long   serialVersionUID = 1L;
  private static final Logger logger           = Logger
                                                   .getLogger(RosterModel.class);
  private RosterInfo          showing          = new RosterInfo();

  private final RosterPanel   rosterPanel;

  public RosterModel(final RosterPanel panel) {
    super(true, true, true);
    rosterPanel = panel;
  }

  @Override
  protected RosterInfoAssignments changeEntry(final RosterInfoAssignments ria,
      final int columnIndex, final Object aValue) {
    logger.debug("Changed: " + ria + " (C" + columnIndex + ") to "
        + aValue.getClass() + " " + aValue);
    rosterPanel.rosterChanged();
    switch (columnIndex) {
      case 0:
        return new RosterInfoAssignments(0, (String) aValue,
            ria.getFuncionariosString());
      case 1:
        return new RosterInfoAssignments(0, ria.getFuncao(), (String) aValue);
      default:
        return null;
    }
  }

  @Override
  public RosterInfoAssignments createNewEntry() {
    return new RosterInfoAssignments();
  }

  public RosterInfo getRoster() {
    final RosterInfo ri = new RosterInfo(showing.getDate(),
        showing.getDayShift());

    final ArrayList<RosterInfoAssignments> all = getAll();
    for (final RosterInfoAssignments ria : all) {
      ri.addFuncaoFuncionarios(ria);
    }

    return ri;
  }

  @Override
  protected Object getValueFor(final RosterInfoAssignments ria,
      final int columnIndex) {
    return (columnIndex == 0) ? ria.getFuncao() : ria.getFuncionariosString();
  }

  public void setRoster(RosterInfo ri) {
    if (ri == null) {
      ri = new RosterInfo();
    }
    showing = ri;
    setData(ri.getAllAssignments());
  }
}
