package net.geral.essomerie.client.core.cache.caches;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import net.geral.essomerie.client.core.events.Events;
import net.geral.essomerie.shared.bulletinboard.BulletinBoardEntry;
import net.geral.essomerie.shared.bulletinboard.BulletinBoardTitle;

import org.apache.log4j.Logger;

public class BulletinBoardCache {
  private static final Logger                        logger  = Logger
                                                                 .getLogger(BulletinBoardCache.class);
  private final ArrayList<BulletinBoardTitle>        titles  = new ArrayList<>();
  private final HashMap<Integer, BulletinBoardEntry> entries = new HashMap<>();

  public synchronized void changed(final int from, final BulletinBoardTitle to) {
    // remove from titles if exists (will add again later)
    removeFromTitles(from);
    // remove from entries (get new copy of contents if needed)
    entries.remove(from);
    // add to and sort titles
    titles.add(to);
    Collections.sort(titles);
    // fire event
    Events.bulletinBoard().fireChanged(from, to);
  }

  public synchronized void deleted(final int identry) {
    // remove from titles
    removeFromTitles(identry);
    // remove from entries
    entries.remove(identry);
    // fire event
    Events.bulletinBoard().fireDeleted(identry);
  }

  public String[] getSubPaths(final String path) {
    final ArrayList<String> subs = new ArrayList<>();
    for (final BulletinBoardTitle t : titles) {
      if (t.inPath(path)) {
        String p = t.getFullTitle().substring(path.length());
        final int index = p.indexOf(BulletinBoardTitle.PATH_SEPARATOR);
        if (index > -1) {
          p = p.substring(0, index + 1);
          if (!subs.contains(p)) {
            subs.add(p);
          }
        }
      }
    }
    return subs.toArray(new String[subs.size()]);
  }

  public synchronized String getTitleArrow(final int id) {
    // has entry? (faster than checking titles)
    final BulletinBoardEntry entry = entries.get(id);
    if (entry != null) {
      return entry.getTitleArrow();
    }
    // no entry, check titles vector
    for (final BulletinBoardTitle t : titles) {
      if (t.getId() == id) {
        return t.getTitleArrow();
      }
    }
    // not found
    logger.warn("Could not find title for id: " + id);
    return "#" + id;
  }

  public synchronized BulletinBoardTitle[] getTitles(final String path) {
    final ArrayList<BulletinBoardTitle> res = new ArrayList<>();
    for (final BulletinBoardTitle t : titles) {
      if (path.equals(t.getPath())) {
        res.add(t);
      }
    }
    return res.toArray(new BulletinBoardTitle[res.size()]);
  }

  public void informSaveSuccessful(final int oldId, final int newId) {
    // no changes, broadcast (inform changed) will make changes in cache
    // fire
    Events.bulletinBoard().fireSaveSuccessful(oldId, newId);
  }

  private boolean removeFromTitles(final int id) {
    boolean foundAtLeastOne = false;
    for (int i = 0; i < titles.size(); i++) {
      if (titles.get(i).getId() == id) {
        titles.remove(i);
        foundAtLeastOne = true;
      }
    }
    return foundAtLeastOne;
  }

  public synchronized void setFullContents(final BulletinBoardEntry entry) {
    // check
    if (entry == null) {
      throw new IllegalArgumentException("bulletin board entry cannot be null.");
    }
    final int id = entry.getId();
    // remove from titles (will add again later)
    removeFromTitles(id);
    // put into entries (will override if already had something)
    entries.put(id, entry);
    // add to and sort titles
    titles.add(entry.getBulletinBoardTitle());
    Collections.sort(titles);
    // fire event
    Events.bulletinBoard().fireContentsReceived(entry);
  }

  public synchronized void setTitleList(final BulletinBoardTitle[] newTitles) {
    // set
    titles.clear();
    Collections.addAll(titles, newTitles);
    Collections.sort(titles);
    // fire
    Events.bulletinBoard().fireTitleListReceived(newTitles);
  }
}
