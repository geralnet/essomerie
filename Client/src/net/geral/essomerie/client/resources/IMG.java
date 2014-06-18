package net.geral.essomerie.client.resources;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Hashtable;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

public enum IMG {
  ICON__APPLICATION,
  ICON__CLOSE_NORMAL,
  ICON__CLOSE_OVER,
  ICON__CLOSE_PRESSED,
  ICON__TOOLBAR__MESSAGE,
  ICON__TOOLBAR__MESSAGE_HASUNREAD,
  ICON__TOOLBAR__ORGANIZER_CALENDAR,
  ICON__TOOLBAR__WAREHOUSE,
  ICON__UP,
  ICON__DOWN,
  ICON__DELETE,
  ICON__TOOLBAR__ORGANIZER_PERSONS,
  ICON__PERSONS__TYPE_NATURAL,
  ICON__PERSONS__TYPE_LEGAL,
  ICON__PERSONS__TYPE_UNKNOWN,
  ICON__CATALOG__DOCUMENT_DETAILS,
  ICON__CATALOG__DOCUMENT,
  ICON__CATALOG__FOLDER_CLOSED,
  ICON__CATALOG__FOLDER_DETAILS,
  ICON__CATALOG__FOLDER_OPEN,
  ICON__WAREHOUSE__CLOSED,
  ICON__WAREHOUSE__OPEN;

  private static URL createURL(final IMG img) {
    final String file = "/res/img/"
        + img.name().toLowerCase().replaceAll("__", "/").replaceAll("_", "-")
        + ".png";
    final URL url = img.getClass().getResource(file);
    if (url == null) {
      Logger.getLogger(IMG.class).warn("Cannot find image resource: " + file);
    }
    return url;
  }

  public static void main(final String[] args) {
    BasicConfigurator.configure();
    for (final IMG img : values()) {
      IMG.createURL(img);
      img.image();
    }
  }

  public static void preload() {
    for (final IMG img : values()) {
      img.image(); // force image loading
    }
  }

  private final URL                               url;
  private final Hashtable<Integer, BufferedImage> images    = new Hashtable<>();

  private final Hashtable<Integer, ImageIcon>     icons     = new Hashtable<>();

  private BufferedImage                           baseImage = null;

  private ImageIcon                               baseIcon  = null;

  private IMG() {
    url = createURL(this);
  }

  private void createEmptyImage() {
    baseImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
    final Graphics g = baseImage.getGraphics();
    g.setColor(Color.MAGENTA);
    g.drawRect(0, 0, 1, 1);
  }

  public ImageIcon icon() {
    if (baseIcon != null) {
      return baseIcon;
    }
    baseIcon = new ImageIcon(image());
    return baseIcon;
  }

  public ImageIcon icon(final int pixels) {
    ImageIcon ico = icons.get(pixels);
    if (ico != null) {
      return ico;
    }
    ico = new ImageIcon(image(pixels));
    icons.put(pixels, ico);
    return ico;
  }

  public BufferedImage image() {
    if (baseImage == null) {
      if (url == null) {
        createEmptyImage();
      } else {
        try {
          baseImage = ImageIO.read(url);
        } catch (final IOException e) {
          Logger.getLogger(IMG.class).warn(
              "Cannot load image resource: " + url, e);
          createEmptyImage();
        }
      }
    }
    return baseImage;
  }

  public BufferedImage image(final int pixels) {
    // if cached, use it
    BufferedImage img = images.get(pixels);
    if (img != null) {
      return img;
    }
    // not cached, create it (if different size)
    final BufferedImage from = image(); // make base
    final int from_width = from.getWidth();
    final int from_height = from.getHeight();
    if ((from_width == pixels) && (from_height == pixels)) {
      img = from;
    } else {
      img = new BufferedImage(pixels, pixels, BufferedImage.TYPE_INT_ARGB);
      img.getGraphics().drawImage(from, 0, 0, pixels, pixels, 0, 0, from_width,
          from_height, null);
    }
    // cache and return it
    images.put(pixels, img);
    return img;
  }
}
