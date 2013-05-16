package net.geral.essomerie.client.gui.shared.tables.addresses;

import net.geral.essomerie.client.core.Client;
import net.geral.essomerie.client.core.configuration.CoreConfiguration;
import net.geral.essomerie.client.resources.S;
import net.geral.lib.table.GNTable;

public class AddressesTable extends GNTable<AddressesModel> {
  private static final long serialVersionUID = 1L;

  public AddressesTable() {
    super(new AddressesModel());
    initialSort(4, false);
  }

  @Override
  protected void createColumns() {
    final CoreConfiguration c = Client.config();
    createColumn(S.ORGANIZER_PERSONS_ADDRESS_POSTALCODE.s(),
        c.TableColumnWidth_Addresses_PostalCode, c.TableColumnWidth_Default);
    createColumn(S.ORGANIZER_PERSONS_ADDRESS_COUNTRY.s(),
        c.TableColumnWidth_Addresses_Country, c.TableColumnWidth_Default);
    createColumn(S.ORGANIZER_PERSONS_ADDRESS_STATE.s(),
        c.TableColumnWidth_Addresses_State, c.TableColumnWidth_Default);
    createColumn(S.ORGANIZER_PERSONS_ADDRESS_SUBURB.s(),
        c.TableColumnWidth_Addresses_Suburb, c.TableColumnWidth_Default);
    createColumn(S.ORGANIZER_PERSONS_ADDRESS_ADDRESS.s(),
        c.TableColumnWidth_Addresses_Address, c.TableColumnWidth_Default);
    createColumn(S.ORGANIZER_PERSONS_ADDRESS_COMMENTS.s(),
        c.TableColumnWidth_Addresses_Comments, c.TableColumnWidth_Default);
    createColumn(S.ORGANIZER_PERSONS_ADDRESS_CLASSIFICATION.s(),
        c.TableColumnWidth_Addresses_Class, c.TableColumnWidth_Default);
  }

  @Override
  protected boolean deleteClicked(final int viewRow, final int tableColumn) {
    return true;
  }

  @Override
  public String getNewEntryText(final int columnIndex) {
    return columnIndex == 4 ? "[add new addreess]" : null;
  }
}
