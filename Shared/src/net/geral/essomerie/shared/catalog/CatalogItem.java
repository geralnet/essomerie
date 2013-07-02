package net.geral.essomerie.shared.catalog;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;

import net.geral.essomerie.shared.money.Money;

public class CatalogItem extends CatalogGroupItemBase {
  private static final long serialVersionUID = 1L;
  private static String     defaultTitle     = "New Item";

  public static void setDefaultTitle(final String title) {
    defaultTitle = title;
  }

  private final int                     idgroup;
  private final HashMap<String, String> titles;
  private final HashMap<String, String> descriptions;

  private final HashMap<String, Money>  prices;

  private transient String              fullTitle = null;

  public CatalogItem(final int id, final int idgroup, final int order,
      final int idpublication, final ArrayList<String> languages,
      final ArrayList<String> titles, final ArrayList<String> descriptions,
      final ArrayList<String> priceCodes, final ArrayList<Money> priceValues) {
    super(true, id, order, idpublication);
    if ((languages.size() != titles.size())
        || (languages.size() != descriptions.size())) {
      throw new InvalidParameterException(
          "languages, titles and descriptions must have the same length.");
    }
    if (priceCodes.size() != priceValues.size()) {
      throw new InvalidParameterException(
          "priceCodes and priceValues must have the same length.");
    }

    this.idgroup = idgroup;
    this.titles = new HashMap<>();
    this.descriptions = new HashMap<>();
    this.prices = new HashMap<>();

    for (int i = 0; i < languages.size(); i++) {
      final String language = languages.get(i);
      this.titles.put(language, titles.get(i));
      this.descriptions.put(language, descriptions.get(i));
    }

    for (int i = 0; i < priceCodes.size(); i++) {
      this.prices.put(priceCodes.get(i), priceValues.get(i));
    }
  }

  private CatalogItem(final int id, final int idgroup, final int order,
      final int idpublication, final HashMap<String, String> titles,
      final HashMap<String, String> descriptions,
      final HashMap<String, Money> prices) {
    super(true, id, order, idpublication);
    this.idgroup = idgroup;
    this.titles = titles;
    this.descriptions = descriptions;
    this.prices = prices;
  }

  public synchronized String getDescription(final String language) {
    final String s = descriptions.get(language);
    return (s == null) ? "" : s;
  }

  public int getIdGroup() {
    return idgroup;
  }

  public synchronized String[] getLanguages() {
    return titles.keySet().toArray(new String[titles.size()]);
  }

  public synchronized Money getPrice(final String code) {
    return prices.get(code);
  }

  public synchronized String[] getPriceCodes() {
    return prices.keySet().toArray(new String[prices.size()]);
  }

  public String getTitle() {
    if (fullTitle == null) {
      synchronized (this) {
        if (titles.size() == 0) {
          fullTitle = defaultTitle;
        } else {
          final StringBuilder sb = new StringBuilder();
          boolean slash = false;
          for (final String t : titles.values()) {
            if (slash) {
              sb.append(" / ");
            }
            sb.append(t);
            slash = true;
          }
          fullTitle = sb.toString();
        }
      }
    }
    return fullTitle;
  }

  public synchronized String getTitle(final String language) {
    final String s = titles.get(language);
    return (s == null) ? "" : s;
  }

  public CatalogItem withIdGroup(final int idgroup) {
    return new CatalogItem(getId(), idgroup, getOrder(), getIdPublication(),
        titles, descriptions, prices);
  }

  public CatalogItem withTitlesPrices(final ArrayList<String> languages,
      final ArrayList<String> titles, final ArrayList<String> descriptions,
      final ArrayList<String> priceCodes, final ArrayList<Money> priceValues) {
    return new CatalogItem(getId(), idgroup, getOrder(), getIdPublication(),
        languages, titles, descriptions, priceCodes, priceValues);
  }
}
