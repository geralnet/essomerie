package net.geral.essomerie.client.gui.shared.tables.addresses;

import java.util.ArrayList;

import net.geral.essomerie.shared.person.Address;
import net.geral.essomerie.shared.person.Addresses;
import net.geral.lib.table.GNTableModel;

import org.apache.log4j.Logger;

public class AddressesModel extends GNTableModel<Address> {
  private static final long   serialVersionUID = 1L;
  private static final Logger logger           = Logger
                                                   .getLogger(AddressesModel.class);

  public AddressesModel() {
    super(true, true, true);
  }

  @Override
  protected Address changeEntry(final Address t, final int columnIndex,
      final Object aValue) {
    switch (columnIndex) {
      case 0:
        return t.withPostalCode((String) aValue);
      case 1:
        return t.withCountry((String) aValue);
      case 2:
        return t.withState((String) aValue);
      case 3:
        return t.withSuburb((String) aValue);
      case 4:
        return t.withAddress((String) aValue);
      case 5:
        return t.withComments((String) aValue);
      case 6:
        try {
          return t.withClassification(Integer.parseInt((String) aValue));
        } catch (final NumberFormatException e) {
          logger.warn(e, e);
          return t;
        }
      default:
        logger.warn("Invalid column to change: " + columnIndex);
        return null;
    }
  }

  @Override
  public Address createNewEntry() {
    return new Address();
  }

  public ArrayList<Address> getAddresses() {
    final ArrayList<Address> addresses = new ArrayList<>(getAll());
    for (int i = 0; i < addresses.size(); i++) {
      final Address a = addresses.get(i);
      if (!a.hasAddress()) {
        addresses.remove(i);
        i--;
      }
    }
    return addresses;
  }

  @Override
  protected String getValueFor(final Address a, final int columnIndex) {
    switch (columnIndex) {
      case 0:
        return a.getPostalCode();
      case 1:
        return a.getCountry();
      case 2:
        return a.getState();
      case 3:
        return a.getSuburb();
      case 4:
        return a.getAddress();
      case 5:
        return a.getComments();
      case 6:
        return String.valueOf(a.getClassification());
      default:
        return null;
    }
  }

  public void setAddresses(final Addresses addresses) {
    setData(addresses == null ? new Address[0] : addresses.getAll());
  }
}
