package net.geral.essomerie.client.gui.organizer.persons.editors.tables.sales;

import net.geral.essomerie.client.core.Client;
import net.geral.essomerie.client.core.configuration.CoreConfiguration;
import net.geral.essomerie.client.resources.S;
import net.geral.essomerie.shared.person.PersonSale;
import net.geral.lib.table.GNTable;

import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;

public class SalesTable extends GNTable<SalesModel> {
  private static final long serialVersionUID = 1L;

  public SalesTable() {
    super(new SalesModel(), new SalesRenderer());
    initialSort(0, true);
  }

  @Override
  protected void createColumns() {
    final CoreConfiguration c = Client.config();
    createColumn(S.ORGANIZER_PERSONS_SALES_WHEN.s(),
        c.TableColumnWidth_Organizer_Persons_Sales_DateTime,
        c.TableColumnWidth_Default_DateTimeNoSeconds,
        c.TableColumnWidth_Default);
    createColumn(S.ORGANIZER_PERSONS_SALES_PRICE.s(),
        c.TableColumnWidth_Organizer_Persons_Sales_Price,
        c.TableColumnWidth_Default);
    createColumn(S.ORGANIZER_PERSONS_SALES_COMMENTS.s(),
        c.TableColumnWidth_Organizer_Persons_Sales_Comments,
        c.TableColumnWidth_Default);
  }

  @Override
  protected boolean deleteClicked(final int viewRow, final int tableColumn) {
    return true;
  }

  @Override
  public String getDefaultEditingString(final int row, final int column) {
    if (column == 0) {
      // only change for datetime
      final LocalDateTime dt = (LocalDateTime) getValueAt(row, column);
      return dt.toString(DateTimeFormat
          .forPattern(S.FORMAT_DATETIME_SIMPLE_NO_SECONDS.s()));
    }
    return super.getDefaultEditingString(row, column);
  }

  @Override
  public String getNewEntryText(final int columnIndex) {
    if (columnIndex == 0) {
      return S.ORGANIZER_PERSONS_DOCUMENTS_TYPE_NEW.s();
    }
    return S.ORGANIZER_PERSONS_DOCUMENTS_NUMBER_NEW.s();
  }

  public PersonSale getSelected() {
    final int s = getSelectedRow();
    if (s == -1) {
      return null;
    }
    return getModel().get(convertRowIndexToModel(s));
  }

  public void selectNext() {
    if (getModel().getRowCount() == 0) {
      return;
    }

    int s = getSelectedRow() + 1;
    if (s >= getModel().getRowCount()) {
      s = 0;
    }
    getSelectionModel().setSelectionInterval(s, s);
  }

  public void selectPrevious() {
    if (getModel().getRowCount() == 0) {
      return;
    }

    int s = getSelectedRow() - 1;
    if (s < 0) {
      s = getModel().getRowCount() - 1;
    }
    getSelectionModel().setSelectionInterval(s, s);
  }
}
