package net.geral.essomerie.client.gui.tools.salesregister.log;

import net.geral.essomerie.client.core.Client;
import net.geral.essomerie.client.core.configuration.CoreConfiguration;
import net.geral.essomerie.client.resources.S;
import net.geral.essomerie.shared.person.PersonSaleExtended;
import net.geral.lib.table.GNTable;

public class LogTable extends GNTable<LogModel> {
  private static final long serialVersionUID = 1L;

  public LogTable() {
    super(new LogModel(), new LogRenderer());
  }

  public void addAndSelect(final PersonSaleExtended sale) {
    getModel().add(sale);
    final int row = getModel().getRowCount() - 1;
    final int n = convertRowIndexToView(row);
    System.err.println(row + "//" + n);
    getSelectionModel().setSelectionInterval(n, n);
    scrollToSelected();
  }

  @Override
  protected void createColumns(final Object... params) {
    final CoreConfiguration c = Client.config();
    createColumn(S.TOOLS_SALESREGISTER_LOG_REGISTERED.s(),
        c.TableColumnWidth_Tools_SalesRegister_Log_Registered,
        c.TableColumnWidth_Default_DateTimeWithSeconds);
    createColumn(S.TOOLS_SALESREGISTER_LOG_BYUSER.s(),
        c.TableColumnWidth_Tools_SalesRegister_Log_ByUser,
        c.TableColumnWidth_Default_Username);
    createColumn(S.TOOLS_SALESREGISTER_LOG_DATETIME.s(),
        c.TableColumnWidth_Tools_SalesRegister_Log_DateTime,
        c.TableColumnWidth_Default_DateTimeNoSeconds);
    createColumn(S.TOOLS_SALESREGISTER_LOG_PRICE.s(),
        c.TableColumnWidth_Tools_SalesRegister_Log_Price,
        c.TableColumnWidth_Default);
    createColumn(S.TOOLS_SALESREGISTER_LOG_PERSON.s(),
        c.TableColumnWidth_Tools_SalesRegister_Log_Person,
        c.TableColumnWidth_Default);
    createColumn(S.TOOLS_SALESREGISTER_LOG_COMMENTS.s(),
        c.TableColumnWidth_Tools_SalesRegister_Log_Comments,
        c.TableColumnWidth_Default);
  }

  @Override
  protected boolean deleteClicked(final int viewRow, final int tableColumn) {
    return false;
  }

  @Override
  public String getNewEntryText(final int columnIndex) {
    return null;
  }
}
