package net.geral.essomerie.client.gui.organizer.persons.editors.tables.sales;

import java.util.ArrayList;

import net.geral.essomerie.client.resources.S;
import net.geral.essomerie.shared.money.Money;
import net.geral.essomerie.shared.person.PersonSale;
import net.geral.lib.table.GNTableModel;

import org.apache.log4j.Logger;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;

public class SalesModel extends GNTableModel<PersonSale> {
  private static final long   serialVersionUID = 1L;
  private static final Logger logger           = Logger
                                                   .getLogger(SalesModel.class);

  public SalesModel() {
    super(true, true, true);
  }

  @Override
  protected PersonSale changeEntry(final PersonSale s, final int columnIndex,
      final Object aValue) {
    switch (columnIndex) {
      case 0:
        final LocalDateTime when = LocalDateTime.parse(aValue.toString(),
            DateTimeFormat.forPattern(S.FORMAT_DATETIME_SIMPLE_NO_SECONDS.s()));
        return s.withWhen(when);
      case 1:
        final Money price = Money.fromString(aValue.toString());
        return s.withPrice(price);
      case 2:
        return s.withComments(aValue.toString());
      default:
        logger.warn("Invalid column to change: " + columnIndex);
        return null;
    }
  }

  @Override
  public PersonSale createNewEntry() {
    return new PersonSale();
  }

  public ArrayList<PersonSale> getSales() {
    return new ArrayList<>(getAll());
  }

  @Override
  protected Object getValueFor(final PersonSale doc, final int columnIndex) {
    switch (columnIndex) {
      case 0:
        return doc.getWhen();
      case 1:
        return doc.getPrice();
      case 2:
        return doc.getComments();
      default:
        logger.warn("Invalid column: " + columnIndex);
        return "C" + columnIndex;
    }
  }
}
