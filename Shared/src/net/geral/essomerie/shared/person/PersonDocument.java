package net.geral.essomerie.shared.person;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;

import net.geral.lib.edt.Edt;
import net.geral.lib.image.GNImages;
import net.geral.lib.strings.GNStrings;

import org.apache.log4j.Logger;

public class PersonDocument implements Serializable {
  private static final long   serialVersionUID = 1L;
  private static final int    IMAGE_SPACING    = 5;
  private static final Logger logger           = Logger
                                                   .getLogger(PersonDocument.class);

  private final int           id;
  private final int           idperson;
  private final String        type;
  private final String        number;

  // image null: unknown
  // image byte[0]: no image
  private final byte[]        image;

  public PersonDocument() {
    this(0, 0, "", "", null);
  }

  public PersonDocument(final int id, final int idperson, final String type,
      final String number, final byte[] image) {
    this.id = id;
    this.idperson = idperson;
    this.type = type;
    this.number = number;
    this.image = image;
  }

  public PersonDocument(final String type, final String number) {
    this(0, 0, type, number, null);
  }

  private Image addedImage(final BufferedImage img) {
    Edt.required();
    final BufferedImage bImage = getBufferedImage();
    int w = (bImage == null) ? 0 : bImage.getWidth();
    int h = (bImage == null) ? -IMAGE_SPACING : bImage.getHeight();
    w = Math.max(w, img.getWidth());
    h += IMAGE_SPACING + img.getHeight();
    // create new
    final BufferedImage newImage = new BufferedImage(w, h,
        BufferedImage.TYPE_INT_RGB);
    final Graphics2D g = newImage.createGraphics();
    g.setBackground(Color.WHITE);
    g.clearRect(0, 0, w, h);
    g.drawImage(bImage, 0, 0, null);
    g.drawImage(img, 0, h - img.getHeight(), null);
    return newImage;
  }

  public BufferedImage getBufferedImage() {
    if (image == null) {
      return null;
    }
    if (image.length == 0) {
      return null;
    }

    try (ByteArrayInputStream in = new ByteArrayInputStream(image)) {
      return ImageIO.read(in);
    } catch (final IOException e) {
      logger.warn(e, e);
      return null;
    }
  }

  public int getId() {
    return id;
  }

  public int getIdPerson() {
    return idperson;
  }

  public byte[] getImageBytes() {
    return image;
  }

  public String getNumber() {
    return number;
  }

  public String getType() {
    return type;
  }

  public boolean hasDocument() {
    if (GNStrings.trim(type).length() > 0) {
      return true;
    }
    if (GNStrings.trim(number).length() > 0) {
      return true;
    }
    return false;
  }

  public boolean hasImage() {
    if (image == null) {
      return false;
    }
    if (image.length == 0) {
      return false;
    }
    return true;
  }

  public PersonDocument withImage(final BufferedImage img) {
    return withImage(GNImages.toByteArray(addedImage(img)));
  }

  private PersonDocument withImage(final byte[] newImage) {
    return new PersonDocument(0, idperson, type, number, newImage);
  }

  public PersonDocument withNumber(final String newNumber) {
    return new PersonDocument(0, idperson, type, newNumber, image);
  }

  public PersonDocument withoutImage() {
    return withImage(new byte[0]);
  }

  public PersonDocument withType(final String newType) {
    return new PersonDocument(0, idperson, newType, number, image);
  }
}
