package net.geral.essomerie.client.gui.organizer.catalog.panels.editor.tables;

import net.geral.essomerie.shared.catalog.CatalogGroup;
import net.geral.lib.table.GNTableModel;
import net.geral.lib.util.StringUtils;

public class GroupTitlesModel extends GNTableModel<String[]> {
  private static final long serialVersionUID = 1L;

  public GroupTitlesModel() {
    super(true, true, true);
  }

  @Override
  protected String[] changeEntry(final String[] s, final int columnIndex,
      final Object aValue) {
    final String to = StringUtils.trim(aValue.toString());
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

  public void setGroup(final CatalogGroup group) {
    clear();
    for (final String language : group.getLanguages()) {
      final String[] s = new String[3];
      s[0] = language;
      s[1] = group.getTitle(language);
      s[2] = group.getSubtitle(language);
      add(s);
    }
  }
}
