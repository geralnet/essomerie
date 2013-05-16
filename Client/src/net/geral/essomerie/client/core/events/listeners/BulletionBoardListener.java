package net.geral.essomerie.client.core.events.listeners;

import java.util.EventListener;

import net.geral.essomerie.shared.bulletinboard.BulletinBoardEntry;
import net.geral.essomerie.shared.bulletinboard.BulletinBoardTitle;

public interface BulletionBoardListener extends EventListener {
    public void bulletinBoardEntryAdded(BulletinBoardTitle title);

    public void bulletinBoardEntryChanged(int from, BulletinBoardTitle to);

    public void bulletinBoardEntryDeleted(int identry);

    public void bulletinBoardEntryReceived(BulletinBoardEntry entry);

    public void bulletinBoardSaveSuccessful(int oldId, int newId);

    public void bulletinBoardTitleListReceived(BulletinBoardTitle[] newTitles);
}
