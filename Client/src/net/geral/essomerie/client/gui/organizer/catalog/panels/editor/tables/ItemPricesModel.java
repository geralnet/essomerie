package net.geral.essomerie.client.gui.organizer.catalog.panels.editor.tables;

import net.geral.essomerie.shared.catalog.CatalogItem;
import net.geral.essomerie.shared.money.Money;
import net.geral.lib.table.GNTableModel;
import net.geral.lib.util.StringUtils;

import org.apache.log4j.Logger;

public class ItemPricesModel extends GNTableModel<Object[]> {
  private static final long   serialVersionUID = 1L;
  private static final Logger logger           = Logger
                                                   .getLogger(ItemPricesModel.class);

  public ItemPricesModel() {
    super(true, true, true);
  }

  @Override
  protected Object[] changeEntry(final Object[] t, final int columnIndex,
      final Object aValue) {
    if (columnIndex == 0) {
      final String to = StringUtils.trim(aValue.toString());
      if (t[0].equals(to)) {
        return null;
      }
      t[0] = to;
      return t;
    }
    if (columnIndex == 1) {
      try {
        final Money m = Money.fromString(aValue.toString());
        if (t[1].equals(m)) {
          return null;
        }
        t[1] = m;
        return t;
      } catch (final NumberFormatException e) {
        logger.debug(e, e);
        return null;
      }
    }
    logger.warn("Invalid column: " + columnIndex);
    return null;
  }

  @Override
  public Object[] createNewEntry() {
    return new Object[] { "", Money.zero() };
  }

  @Override
  protected Object getValueFor(final Object[] obj, final int columnIndex) {
    return obj[columnIndex];
  }

  public void setItem(final CatalogItem item) {
    clear();
    for (final String code : item.getPriceCodes()) {
      final Object[] o = new Object[2];
      o[0] = code;
      o[1] = item.getPrice(code);
      add(o);
    }
  }
}
