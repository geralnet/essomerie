package net.geral.essomerie.client.gui.organizer.catalog.panels.editor.tables;

import net.geral.essomerie.shared.catalog.CatalogItem;
import net.geral.lib.strings.GNStrings;
import net.geral.lib.table.GNTableModel;

public class ItemTitlesModel extends GNTableModel<String[]> {
  private static final long serialVersionUID = 1L;

  public ItemTitlesModel() {
    super(true, true, true);
  }

  @Override
  protected String[] changeEntry(final String[] s, final int columnIndex,
      final Object aValue) {
    final String to = GNStrings.trim(aValue.toString());
    if (s[columnIndex].equals(to)) {
      return null;
    }
    s[columnIndex] = to;
    return s;
  }

  @Override
  public String[] createNewEntry() {
    return new String[] { "", "", "" };
  }

  @Override
  protected Object getValueFor(final String[] obj, final int columnIndex) {
    return obj[columnIndex];
  }

  public void setItem(final CatalogItem item) {
    clear();
    for (final String language : item.getLanguages()) {
      final String[] s = new String[3];
      s[0] = language;
      s[1] = item.getTitle(language);
      s[2] = item.getDescription(language);
      add(s);
    }
  }
}
