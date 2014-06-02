package net.geral.essomerie.client.gui.organizer.persons.editors.tables.documents;

import net.geral.essomerie.client.core.Client;
import net.geral.essomerie.client.core.configuration.CoreConfiguration;
import net.geral.essomerie.client.resources.S;
import net.geral.essomerie.shared.person.PersonDocument;
import net.geral.lib.table.GNTable;

public class PersonDocumentTable extends GNTable<PersonDocumentModel> {
  private static final long serialVersionUID = 1L;

  public PersonDocumentTable() {
    super(new PersonDocumentModel(), new PersonDocumentRenderer());
    initialSort(0, false);
  }

  public void addAndSelect(final PersonDocument doc) {
    // TODO send to GNTable (?)
    getModel().add(doc);
    final int modelIndex = getModel().indexOf(doc);
    final int viewIndex = convertRowIndexToView(modelIndex);
    getSelectionModel().setSelectionInterval(viewIndex, viewIndex);
  }

  @Override
  protected void createColumns(final Object... params) {
    final CoreConfiguration c = Client.config();
    createColumn(S.ORGANIZER_PERSONS_DOCUMENTS_TYPE.s(),
        c.TableColumnWidth_Organizer_Persons_Documents_Type,
        c.TableColumnWidth_Default);
    createColumn(S.ORGANIZER_PERSONS_DOCUMENTS_NUMBER.s(),
        c.TableColumnWidth_Organizer_Persons_Documents_Number,
        c.TableColumnWidth_Default);
  }

  @Override
  protected boolean deleteClicked(final int viewRow, final int tableColumn) {
    return true;
  }

  @Override
  public String getNewEntryText(final int columnIndex) {
    if (columnIndex == 0) {
      return S.ORGANIZER_PERSONS_DOCUMENTS_TYPE_NEW.s();
    }
    return S.ORGANIZER_PERSONS_DOCUMENTS_NUMBER_NEW.s();
  }

  @Override
  public PersonDocument getSelected() {
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
