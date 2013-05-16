package net.geral.essomerie.client.gui.shared.tables.telephones;

import java.util.ArrayList;

import net.geral.essomerie.shared.person.Telephone;
import net.geral.essomerie.shared.person.Telephones;
import net.geral.lib.table.GNTableModel;

import org.apache.log4j.Logger;

public class TelephonesModel extends GNTableModel<Telephone> {
  private static final long   serialVersionUID = 1L;
  private static final Logger logger           = Logger
                                                   .getLogger(TelephonesModel.class);

  public TelephonesModel() {
    super(true, true, true);
  }

  @Override
  protected Telephone changeEntry(final Telephone t, final int columnIndex,
      final Object aValue) {
    switch (columnIndex) {
      case 0:
        return t.withType((String) aValue);
      case 1:
        final Telephone newT = Telephone.fromFormatted((String) aValue);
        return (newT == null) ? t : newT.withType(t.getType());
      default:
        logger.warn("Invalid column to change: " + columnIndex);
        return null;
    }
  }

  @Override
  public Telephone createNewEntry() {
    return new Telephone();
  }

  public ArrayList<Telephone> getTelephones() {
    final ArrayList<Telephone> phones = new ArrayList<Telephone>(getAll());
    for (int i = 0; i < phones.size(); i++) {
      final Telephone t = phones.get(i);
      if (!t.hasNumber()) {
        phones.remove(i);
        i--;
      }
    }
    return phones;
  }

  @Override
  protected String getValueFor(final Telephone t, final int columnIndex) {
    switch (columnIndex) {
      case 0:
        return t.getType();
      case 1:
        return t.toString(false);
      default:
        return null;
    }
  }

  public void setTelephones(final Telephones newTelephones) {
    setData(newTelephones == null ? new Telephone[0] : newTelephones.getAll());
  }
}
