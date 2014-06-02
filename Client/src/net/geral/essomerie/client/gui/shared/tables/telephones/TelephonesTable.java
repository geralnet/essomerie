package net.geral.essomerie.client.gui.shared.tables.telephones;

import net.geral.essomerie.client.core.Client;
import net.geral.essomerie.client.core.configuration.CoreConfiguration;
import net.geral.lib.table.GNTable;

public class TelephonesTable extends GNTable<TelephonesModel> {
  private static final long serialVersionUID = 1L;

  public TelephonesTable() {
    super(new TelephonesModel());
    initialSort(0, false);
  }

  @Override
  protected void createColumns(final Object... params) {
    CoreConfiguration c;
    try {
      c = Client.config();
    } catch (final NullPointerException e) {
      // windows builder or test, use defaults
      c = new CoreConfiguration();
    }
    createColumn("Type", c.TableColumnWidth_Telephones_Type,
        c.TableColumnWidth_Default);
    createColumn("Number", c.TableColumnWidth_Telephones_Number,
        c.TableColumnWidth_Default);
  }

  @Override
  protected boolean deleteClicked(final int viewRow, final int tableColumn) {
    return true;
  }

  @Override
  public String getNewEntryText(final int columnIndex) {
    return (columnIndex == 1) ? "[add new number]" : null;
  }
}
