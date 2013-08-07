package net.geral.essomerie.client.gui.main;

import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import net.geral.essomerie._shared.UserPermission;
import net.geral.essomerie.client.core.Client;
import net.geral.essomerie.client.core.cache.caches.BulletinBoardCache;
import net.geral.essomerie.client.core.events.Events;
import net.geral.essomerie.client.core.events.listeners.BulletionBoardListener;
import net.geral.essomerie.client.core.events.listeners.SystemListener;
import net.geral.essomerie.client.resources.S;
import net.geral.essomerie.shared.BuildInfo;
import net.geral.essomerie.shared.bulletinboard.BulletinBoardEntry;
import net.geral.essomerie.shared.bulletinboard.BulletinBoardTitle;
import net.geral.essomerie.shared.communication.MessageData;

//TODO what if bulletin board changes while the bulletin board menu is open?
public class MainMenu extends JMenuBar implements BulletionBoardListener,
    SystemListener {
  private static final long serialVersionUID = 1L;

  private static String createSpacedLabel(final String lbl) {
    final int repeat = Client.config().MainMenuSpacing;
    final StringBuilder label = new StringBuilder(lbl.length() + (2 * repeat));
    for (int i = 0; i < repeat; i++) {
      label.append(' ');
    }
    label.append(lbl);
    for (int i = 0; i < repeat; i++) {
      label.append(' ');
    }
    return label.toString();
  }

  private final ActionListener listener;

  private JMenu                mBulletinBoard;
  private JMenu                mSysOp;

  public MainMenu(final ActionListener listener) {
    this.listener = listener;

    createUser();
    createBulletinBoard();
    createOrganizer();
    createInventory();
    // criarCaixa();
    // createDelivery();
    // criarCardapio();
    createSysOp();
    // criarExibir();

    Events.bulletinBoard().addListener(this);
    Events.system().addListener(this);
  }

  private void addItem(final JMenu menu, final S s) {
    addItem(menu, s.s(), s.name());
  }

  private JMenuItem addItem(final JMenu menu, final String title,
      final String action) {
    final JMenuItem mi = new JMenuItem(title);
    mi.setActionCommand(action);
    mi.addActionListener(listener);
    menu.add(mi);
    return mi;
  }

  private void addSeparator(final JMenu menu) {
    menu.addSeparator();
  }

  @Override
  public void bulletinBoardEntryAdded(final BulletinBoardTitle title) {
    createBulletinBoard();
  }

  @Override
  public void bulletinBoardEntryChanged(final int from,
      final BulletinBoardTitle to) {
    createBulletinBoard();
  }

  @Override
  public void bulletinBoardEntryDeleted(final int idinformacao) {
    createBulletinBoard();
  }

  @Override
  public void bulletinBoardEntryReceived(final BulletinBoardEntry informacao) {
    createBulletinBoard();
  }

  @Override
  public void bulletinBoardSaveSuccessful(final int oldId, final int newId) {
    // TODO Auto-generated method stub
  }

  @Override
  public void bulletinBoardTitleListReceived(
      final BulletinBoardTitle[] newTitles) {
    createBulletinBoard();
  }

  private void createBulletinBoard() {
    if (mBulletinBoard == null) {
      mBulletinBoard = createMenu(S.MENU_BULLETINBOARD);
    }
    mBulletinBoard.removeAll();

    final BulletinBoardCache cache = Client.cache().bulletinBoard();
    final BulletinBoardTitle[] es = cache.getTitles();
    for (final BulletinBoardTitle e : es) {
      addItem(mBulletinBoard, e.getTitle(), "bulletinboard_show:" + e.getId());
    }

    addSeparator(mBulletinBoard);
    addItem(mBulletinBoard, S.MENU_BULLETINBOARD_ADD);
  }

  private void createInventory() {
    final JMenu menu = createMenu(S.MENU_INVENTORY);
    addItem(menu, S.MENU_INVENTORY_MANAGEMENT);
    // criarItem(menu, "Relatório", "contagem_relatorio");
    // criarItem(menu, "Cadastro", "contagem_cadastro");
  }

  private JMenu createMenu(final S s) {
    return createMenu(s.s());
  }

  private JMenu createMenu(final String titulo) {
    final JMenu menu = new JMenu(createSpacedLabel(titulo));
    add(menu);
    return menu;
  }

  private void createOrganizer() {
    final JMenu menu = createMenu(S.MENU_ORGANIZER);
    addItem(menu, S.MENU_ORGANIZER_CALENDAR);
    addItem(menu, S.MENU_ORGANIZER_PERSONS);
    addSeparator(menu);
    addItem(menu, S.MENU_ORGANIZER_CATALOG);
    // criarItem(menu, "Reservas", "agenda_reservas");
    // criarItem(menu, "Escala", "agenda_escala");
    // criarItem(menu, "Telefones", "agenda_telefones");
    // addItem(menu, "Funcionários", "agenda_funcionarios");
    addSeparator(menu);
    final JMenu tools = new JMenu(S.MENU_ORGANIZER_TOOLS.s());
    addItem(tools, S.MENU_ORGANIZER_TOOLS_SALESREGISTER);
    addItem(tools, S.MENU_ORGANIZER_TOOLS_CATALOGPUBLISH);
    menu.add(tools);
  }

  private void createSysOp() {
    mSysOp = createMenu(S.MENU_SYSOP);
    addItem(mSysOp, S.MENU_SYSOP_DEVICES);
    mSysOp.setVisible(false);
  }

  private void createUser() {
    final JMenu menu = createMenu(S.MENU_USER);
    addItem(menu, S.MENU_USER_MESSAGES);
    // TODO criarItem(mUser, "Avisos", "user_avisos");
    addSeparator(menu);
    // TODO criarItem(mUser, "Alterar Senha", "user_alterarSenha");
    addItem(menu, S.MENU_USER_LOGOUT);
  }

  @Override
  public void systemConnected() {
    mSysOp.setVisible(false);
  }

  @Override
  public void systemConnecting(final String serverAddress, final int serverPort) {
    mSysOp.setVisible(false);
  }

  @Override
  public void systemConnectionFailed(final boolean willTryAgain) {
    mSysOp.setVisible(false);
  }

  @Override
  public void systemConnectionTryAgainCountdown(final int tryAgainCountdown) {
    mSysOp.setVisible(false);
  }

  @Override
  public void systemInformSent(final MessageData md) {
  }

  @Override
  public void systemLoggedOut() {
    mSysOp.setVisible(false);
  }

  @Override
  public void systemLoginAccepted() {
    final boolean b = Client.cache().users()
        .checkLoggedPermission(UserPermission.SYSOP, false);
    mSysOp.setVisible(b);
  }

  @Override
  public void systemLoginFailed() {
    mSysOp.setVisible(false);
  }

  @Override
  public void systemPongReceived(final long lag) {
  }

  @Override
  public void systemVersionReceived(final BuildInfo version) {
  }
}

// private void criarCaixa() {
// final JMenu menu = criarMenu("Caixa");
// addItem(menu, "Abrir Caixa", "caixa_abrir");
// addItem(menu, "Fechar Caixa", "caixa_fechar");
// addSeparador(menu);
// addItem(menu, "Fechar Mesa", "caixa_mesa_fechar");
// addItem(menu, "Cobrar Mesa", "caixa_mesa_cobrar");
// }
//
// private void criarCardapio() {
// final JMenu menu = criarMenu("Cardápio");
// addItem(menu, "Interativo", "cardapio_interativo");
// addItem(menu, "Editar", "cardapio_editar");
// addItem(menu, "Atualizar", "cardapio_atualizar");
// }

//
// private void criarExibir() {
// final JMenu menu = createMenu("Exibir");
// final JMenu resolucao = criarSubMenu(menu, "Resolução");
// addItem(resolucao, "1000x700", "exibir_resolucao_1000x700");
// }
// private JMenu criarSubMenu(final JMenu menu, final String titulo) {
// final JMenu submenu = new JMenu(titulo);
// menu.add(submenu);
// return submenu;
// }
//
