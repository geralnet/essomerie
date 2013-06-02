package net.geral.essomerie.client.gui.tools.salesregister.log;

import net.geral.essomerie.client.core.Client;
import net.geral.essomerie.client.resources.S;
import net.geral.essomerie.shared.person.Person;
import net.geral.essomerie.shared.person.PersonSaleExtended;
import net.geral.lib.table.GNTableModel;

import org.joda.time.format.DateTimeFormat;

public class LogModel extends GNTableModel<PersonSaleExtended> {
  private static final long serialVersionUID = 1L;

  public LogModel() {
    super(false, false, false);
  }

  @Override
  protected PersonSaleExtended changeEntry(final PersonSaleExtended t,
      final int columnIndex, final Object aValue) {
    return null;
  }

  @Override
  public PersonSaleExtended createNewEntry() {
    return null;
  }

  @Override
  protected Object getValueFor(final PersonSaleExtended pse,
      final int columnIndex) {
    switch (columnIndex) {
      case 0:
        return pse.getDateTimeRegistered().toString(
            DateTimeFormat.forPattern(S.FORMAT_DATETIME_SIMPLE.s()));
      case 1:
        return Client.cache().users().get(pse.getIdUser()).getUsername()
            .toString().toUpperCase();
      case 2:
        return pse.getWhen().toString(
            DateTimeFormat.forPattern(S.FORMAT_DATETIME_SIMPLE_NO_SECONDS.s()));
      case 3:
        return pse.getPrice();
      case 4:
        final Person p = Client.cache().persons().get(pse.getIdPerson());
        return (p == null) ? "..." : p.getNameAlias();
      case 5:
        return pse.getComments();
      default:
        return "";
    }
  }

  public void refreshNames() {
    if (getRowCount() > 0) {
      fireTableRowsUpdated(0, getRowCount() - 1);
    }
  }
}
