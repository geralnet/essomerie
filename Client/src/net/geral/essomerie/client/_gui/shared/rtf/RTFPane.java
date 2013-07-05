package net.geral.essomerie.client._gui.shared.rtf;

import java.awt.print.PrinterException;

import javax.swing.JTextPane;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Document;
import javax.swing.text.rtf.RTFEditorKit;

import net.geral.essomerie._shared.ByteReader;
import net.geral.essomerie._shared.ByteWriter;
import net.geral.lib.printing.PrintSupport;

import org.apache.log4j.Logger;

//TODO translate
public class RTFPane extends JTextPane {
  private static final long   serialVersionUID = 1L;
  private static final Logger logger           = Logger
                                                   .getLogger(RTFPane.class);

  private final RTFEditorKit  editor           = new RTFEditorKit();
  private byte[]              initialContents;

  public RTFPane() {
    this(null, false);
  }

  public RTFPane(final boolean editable) {
    this(null, editable);
  }

  public RTFPane(final byte[] rtf) {
    this(rtf, false);
  }

  public RTFPane(final byte[] rtf, final boolean editable) {
    setEditable(editable);
    setEditorKit(editor);
    initialContents = (rtf == null) ? getRTF() : rtf;
    if (rtf != null) {
      setRTF(rtf);
    }
  }

  public byte[] getRTF() {
    final Document doc = getDocument();
    final ByteWriter bw = new ByteWriter();
    try {
      editor
          .write(bw, doc, doc.getStartPosition().getOffset(), doc.getLength());
    } catch (final Exception e) {
      logger.warn(e, e);
    }
    return bw.getBytes();
  }

  public DefaultStyledDocument getStyleDocument() {
    return (DefaultStyledDocument) getDocument();
  }

  public boolean hasChanged() {
    // check if size differs
    final byte[] current = getRTF();
    final int size = current.length;
    if (initialContents.length != size) {
      return true;
    }

    // check if any byte differs
    for (int i = 0; i < size; i++) {
      if (current[i] != initialContents[i]) {
        return true;
      }
    }

    // no difference spotted
    return false;
  }

  public void imprimir() {
    try {
      final RTFPrint print = new RTFPrint(this);
      PrintSupport.print(print);
    } catch (final PrinterException e) {
      logger.warn(e, e);
    }
  }

  public void setRTF(final byte[] conteudo) {
    final DefaultStyledDocument newDocument = new DefaultStyledDocument();
    final ByteReader br = new ByteReader(conteudo);
    try {
      editor.read(br, newDocument, 0);
    } catch (final Exception e) {
      logger.warn(e, e);
    }
    initialContents = conteudo;
    setDocument(newDocument);
  }
}
