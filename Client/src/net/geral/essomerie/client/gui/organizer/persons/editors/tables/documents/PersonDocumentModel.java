package net.geral.essomerie.client.gui.organizer.persons.editors.tables.documents;

import java.util.ArrayList;

import net.geral.essomerie.shared.person.PersonDocument;
import net.geral.lib.strings.GNStrings;
import net.geral.lib.table.GNTableModel;

public class PersonDocumentModel extends GNTableModel<PersonDocument> {
  private static final long serialVersionUID = 1L;

  public PersonDocumentModel() {
    super(true, true, true);
  }

  @Override
  protected PersonDocument changeEntry(final PersonDocument t,
      final int columnIndex, final Object aValue) {
    final String value = GNStrings.trim(aValue.toString());
    if (columnIndex == 0) {
      return t.withType(value);
    }
    return t.withNumber(value);
  }

  @Override
  public PersonDocument createNewEntry() {
    return new PersonDocument();
  }

  public ArrayList<PersonDocument> getDocuments() {
    final ArrayList<PersonDocument> documents = new ArrayList<>(getAll());
    for (int i = 0; i < documents.size(); i++) {
      final PersonDocument d = documents.get(i);
      if (!d.hasDocument()) {
        documents.remove(i);
        i--;
      }
    }
    return documents;
  }

  @Override
  protected Object getValueFor(final PersonDocument doc, final int columnIndex) {
    if (columnIndex == 0) {
      return doc.getType();
    }
    return doc.getNumber();
  }
}
