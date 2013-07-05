package net.geral.essomerie.client.gui.organizer.persons.editors;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.geral.essomerie.client.gui.organizer.persons.PersonEditorPanel;
import net.geral.essomerie.client.gui.organizer.persons.editors.tables.documents.PersonDocumentTable;
import net.geral.essomerie.client.resources.S;
import net.geral.essomerie.shared.person.Person;
import net.geral.essomerie.shared.person.PersonData;
import net.geral.essomerie.shared.person.PersonDocument;
import net.geral.essomerie.shared.person.PersonDocuments;
import net.geral.lib.filechooser.GNFileChooser;
import net.geral.lib.gui.button.ActionButton;
import net.geral.lib.imagepanel.GNImagePanel;

import org.apache.log4j.Logger;

public class DocumentsPersonEditor extends PersonEditorPanel implements
    ActionListener, ListSelectionListener {
  private static final Logger       logger           = Logger
                                                         .getLogger(DocumentsPersonEditor.class);
  private static final long         serialVersionUID = 1L;
  private final JSplitPane          splitPane;
  private final JPanel              listPanel;
  private final PersonDocumentTable table;
  private final GNFileChooser       chooser          = GNFileChooser
                                                         .makeImageChooser();
  private final GNImagePanel        imagePanel;
  private final ActionButton        btnClear;
  private final ActionButton        btnAdd;
  private final ActionButton        btnSaveImage;
  private boolean                   editable         = false;

  public DocumentsPersonEditor() {
    setLayout(new BorderLayout(0, 0));

    splitPane = new JSplitPane();
    splitPane.setResizeWeight(0.5);
    add(splitPane, BorderLayout.CENTER);

    listPanel = new JPanel();
    splitPane.setLeftComponent(listPanel);
    listPanel.setLayout(new BorderLayout(0, 0));

    table = new PersonDocumentTable();
    table.getSelectionModel().addListSelectionListener(this);
    listPanel.add(table.getScroll());

    final JPanel rightPanel = new JPanel();
    splitPane.setRightComponent(rightPanel);
    rightPanel.setLayout(new BorderLayout(0, 0));

    final JScrollPane imageScroll = new JScrollPane();
    rightPanel.add(imageScroll);

    imagePanel = new GNImagePanel();
    imagePanel.setBackground(Color.WHITE);
    imageScroll.setViewportView(imagePanel);

    final JPanel imageButtons = new JPanel();
    rightPanel.add(imageButtons, BorderLayout.SOUTH);
    imageButtons.setLayout(new BorderLayout(0, 0));

    final JPanel imageButtonsGrid = new JPanel();
    imageButtons.add(imageButtonsGrid, BorderLayout.EAST);
    imageButtonsGrid.setLayout(new GridLayout(1, 0, 0, 0));

    btnSaveImage = new ActionButton(S.ORGANIZER_PERSONS_DOCUMENTS_SAVEPNG.s(),
        'M', "image_save", this);
    imageButtonsGrid.add(btnSaveImage);

    btnClear = new ActionButton(S.BUTTON_CLEAR.s(), 'L', "image_clear", this);
    imageButtonsGrid.add(btnClear);

    btnAdd = new ActionButton(S.BUTTON_ADD.s(), 'I', "image_add", this);
    imageButtonsGrid.add(btnAdd);

    updateButtons();
  }

  @Override
  public void actionPerformed(final ActionEvent e) {
    final String cmd = e.getActionCommand();
    if ("image_clear".equals(cmd)) {
      imageClear();
    } else if ("image_add".equals(cmd)) {
      imageAdd();
    } else if ("image_save".equals(cmd)) {
      imageSave();
    } else {
      logger.warn("Invalid action: " + cmd);
    }
  }

  public PersonDocuments getDocuments(final int idperson) {
    return new PersonDocuments(idperson, table.getModel().getDocuments());
  }

  private void imageAdd() {
    final PersonDocument doc = table.getSelected();
    if (doc == null) {
      logger.warn("Cannot add image, no document selected.");
      return;
    }
    try {
      final File file = chooser.open(this);
      if (file == null) {
        return;
      }
      logger.debug("Adding file: " + file);
      final PersonDocument newDoc = doc.withImage(ImageIO.read(file));
      table.getModel().remove(doc);
      table.addAndSelect(newDoc);
    } catch (final IOException e) {
      logger.warn(e, e);
    }
  }

  private void imageClear() {
    final PersonDocument doc = table.getSelected();
    if (doc == null) {
      logger.warn("Cannot clear image, no document selected.");
      return;
    }
    table.getModel().remove(doc);
    table.addAndSelect(doc.withoutImage());
  }

  private void imageSave() {
    final PersonDocument doc = table.getSelected();
    if ((doc == null) || (!doc.hasImage())) {
      logger.warn("Invalid document to save image.");
      return;
    }
    File file = chooser.save(this);
    if (file == null) {
      return;
    }
    if (!file.getName().toLowerCase().endsWith(".png")) {
      logger.debug("Not .png, appending .png suffix.");
      file = new File(file.getPath() + ".png");
    }
    logger.debug("Saving file: " + file);
    try (FileOutputStream output = new FileOutputStream(file)) {
      output.write(doc.getImageBytes());
    } catch (final IOException e) {
      JOptionPane.showMessageDialog(this,
          S.ERROR_SAVING_FILE.s() + "\n\n" + e.getMessage(), S.TITLE_ERROR.s(),
          JOptionPane.ERROR_MESSAGE);
      logger.warn(e, e);
    }
  }

  @Override
  public void setEditable(final boolean yn) {
    editable = yn;
    table.setEditable(editable);
    updateButtons();
  }

  @Override
  public void setPerson(final Person p) {
    if (p instanceof PersonData) {
      table.getModel().setData(((PersonData) p).getDocuments().getAll());
    } else {
      table.getModel().setData(new PersonDocument[0]);
    }
  }

  private void updateButtons() {
    final boolean hasSelected = (table.getSelected() != null);
    final boolean hasImage = (hasSelected && (table.getSelected().hasImage()));
    btnAdd.setEnabled(editable && hasSelected);
    btnClear.setEnabled(editable && hasImage);
    btnSaveImage.setEnabled(hasImage);
  }

  private void updateImage() {
    final PersonDocument doc = table.getSelected();
    imagePanel.setImage((doc == null) ? null : doc.getBufferedImage());
  }

  @Override
  public void valueChanged(final ListSelectionEvent e) {
    if (e.getValueIsAdjusting()) {
      return;
    }
    updateButtons();
    updateImage();
  }
}
