package net.geral.essomerie.client.gui.main;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;

import net.geral.essomerie.client.core.Client;
import net.geral.essomerie.client.core.events.Events;
import net.geral.essomerie.client.core.events.listeners.CallerIdListener;
import net.geral.essomerie.client.gui.bulletinboard.BulletinBoardEditorTabPanel;
import net.geral.essomerie.client.gui.bulletinboard.BulletinBoardTabPanel;
import net.geral.essomerie.client.gui.connection.ConnectionDialog;
import net.geral.essomerie.client.gui.inventory.InventoryManagementTabPanel;
import net.geral.essomerie.client.gui.messages.MessagesTabPanel;
import net.geral.essomerie.client.gui.organizer.calendar.CalendarTabPanel;
import net.geral.essomerie.client.gui.organizer.persons.OrganizerPersonsTabPanel;
import net.geral.essomerie.client.gui.shared.notification.CallerIdNotificationPanel;
import net.geral.essomerie.client.gui.shared.notification.NotificationWindow;
import net.geral.essomerie.client.gui.tools.salesregister.ToolsSalesRegister;
import net.geral.essomerie.client.resources.IMG;
import net.geral.essomerie.client.resources.S;
import net.geral.essomerie.shared.person.PersonData;
import net.geral.essomerie.shared.person.Telephone;

import org.apache.log4j.Logger;

/**
 * Essomerie Main Client Window
 * 
 * @author Daniel Thee Roperto
 */
public class MainWindow extends JFrame implements ActionListener,
    CallerIdListener {
  private static final long   serialVersionUID = 1L;
  private static final Logger logger           = Logger
                                                   .getLogger(MainWindow.class);
  private final JPanel        contentPane      = new JPanel();
  private final MainMenu      mainMenu         = new MainMenu(this);
  private final MainToolBar   toolBar          = new MainToolBar(this);
  private final JTabbedPane   mainTabs         = new JTabbedPane(
                                                   JTabbedPane.TOP);
  private final StatusBar     statusBar        = new StatusBar();

  /**
   * Creates a new Main Window. It is not expected that the software creates
   * more than one window.
   */
  public MainWindow() {
    super();
    setTitle();

    setIconImage(IMG.ICON__APPLICATION.image());
    setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    setMinimumSize(Client.config().MainWindowMinimumSize);

    final Dimension size = Client.preferences().MainWindowSize;
    if (size != null) {
      setSize(size);
    }

    final Point location = Client.preferences().MainWindowLocation;
    if (location == null) {
      setLocationRelativeTo(null);
    } else {
      setLocation(location);
    }

    setExtendedState(Client.preferences().MainWindowMaximized ? MAXIMIZED_BOTH
        : NORMAL);

    contentPane.setBorder(null);
    contentPane.setLayout(new BorderLayout());
    setContentPane(contentPane);
    setJMenuBar(mainMenu);
    contentPane.add(toolBar, BorderLayout.NORTH);
    contentPane.add(mainTabs, BorderLayout.CENTER);

    contentPane.add(statusBar, BorderLayout.SOUTH);

    addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(final java.awt.event.WindowEvent e) {
        closeWindowRequest();
      };
    });

    addWindowStateListener(new WindowStateListener() {
      @Override
      public void windowStateChanged(final WindowEvent e) {
        changeMaximizedPreferences(e.getNewState() == MAXIMIZED_BOTH,
            e.getNewState() == NORMAL);
      }
    });

    Events.callerid().addListener(this);
  }

  @Override
  public void actionPerformed(final ActionEvent e) {
    try {
      execute(e.getActionCommand());
    } catch (final Exception ex) {
      logger.warn("Cannot perform action: " + e.getActionCommand(), ex);
    }
  }

  @Override
  public void callerIdCallReceived(final String line,
      final Telephone telephone, final PersonData person) {
    final NotificationWindow nw = new NotificationWindow(
        new CallerIdNotificationPanel(line, telephone, person),
        Client.config().CallerIdNotificationSeconds);
    nw.setVisible(true);
  }

  private void changeMaximizedPreferences(final boolean maximized,
      final boolean normal) {
    // should never be maximized AND normal, unless JAVA makes a huge crazy
    // change on its internals! :p
    if (!(maximized || normal)) {
      // not a valid option (maybe is minimized?)
      return;
    }
    Client.preferences().MainWindowMaximized = maximized;
    Client.preferences().autosave();
  }

  /**
   * Calls the 'close(true)' method in all existing tabs, requesting them to
   * close without any confirmation.
   */
  public void closeAllTabs() {
    final int n = mainTabs.getComponentCount();
    for (int i = n - 1; i >= n; i--) {
      final Component c = mainTabs.getComponent(i);
      if (c instanceof TabPanel) {
        ((TabPanel) c).close(true);
      }
    }
    // enforce that all are closed
    mainTabs.removeAll();
  }

  /**
   * Requests the tab to close without forcing it (it may show a confirmation or
   * even reject closing).
   * 
   * @param tab
   *          Tab to close.
   */
  public void closeTab(final TabPanel tab) {
    closeTab(tab, false);
  }

  /**
   * Requests the tab to close.
   * 
   * @param tp
   *          Tab to close.
   * @param force
   *          If true, it will ignore if the tab does not want to close and
   *          close it anyway.
   * @return True if closed, false otherwise.
   */
  public boolean closeTab(final TabPanel tp, final boolean force) {
    if (force || tp.tabCloseRequest()) {
      mainTabs.remove(tp);
      tp.tabClosed();
      return true;
    }
    return false;
  }

  private boolean closeWindowRequest() {
    final int res = JOptionPane.showConfirmDialog(this,
        S.WINDOW_MAIN_CLOSE_PROMPT, S.TITLE_CONFIRM.s(),
        JOptionPane.YES_NO_OPTION);
    if (res == JOptionPane.YES_OPTION) {
      try {
        saveSizeAndLocation();
        toolBar.stop();
        Client.connection().close();
      } finally {
        System.exit(0);
      }
    }
    return true;
  }

  private boolean execute(String cmd) throws IOException {
    String param = "";
    if (cmd.indexOf(':') != -1) {
      final String[] parts = cmd.split(":", 2);
      cmd = parts[0];
      param = parts[1];
    }
    logger.debug("execute(" + cmd + "," + param + ")");

    if (S.MENU_USER_MESSAGES.name().equals(cmd)) {
      return openTab(MessagesTabPanel.class);
    }
    if (S.MENU_USER_LOGOUT.name().equals(cmd)) {
      return closeWindowRequest();
    }
    if ("bulletinboard_show".equals(cmd)) {
      return openTab(new BulletinBoardTabPanel(Integer.parseInt(param)));
    }
    if (S.MENU_BULLETINBOARD_ADD.name().equals(cmd)) {
      return openTab(new BulletinBoardEditorTabPanel());
    }
    if (S.MENU_ORGANIZER_CALENDAR.name().equals(cmd)) {
      return openTab(CalendarTabPanel.class);
    }
    if (S.MENU_ORGANIZER_PERSONS.name().equals(cmd)) {
      return openTab(OrganizerPersonsTabPanel.class);
    }
    if (S.MENU_ORGANIZER_TOOLS_SALESREGISTER.name().equals(cmd)) {
      return openTab(ToolsSalesRegister.class);
    }
    // if ("agenda_funcionarios".equals(cmd)) { return
    // openTab(OrganizerPersonsTabPanel.class); }
    if (S.MENU_INVENTORY_MANAGEMENT.name().equals(cmd)) {
      return openTab(InventoryManagementTabPanel.class);
    }
    // if ("cardapio_editar".equals(cmd)) { return
    // openTab(CardapioEditorPanel.class); }
    // if ("cardapio_interativo".equals(cmd)) { return
    // openTab(CardapioInterativoPanel.class); }
    // if ("sysadmin_screenlog".equals(cmd)) { return
    // openTab(ScreenLogTabPanel.class); }

    // if ("caixa_abrir".equals(cmd)) return caixaAbrir();
    // private boolean caixaAbrir() { //TODO translate?
    // final CaixaDetalhesDialog cdt = new CaixaDetalhesDialog();
    // cdt.setLocationRelativeTo(null);
    // cdt.setVisible(true);
    // openTab(new CaixaCompletoPanel(cdt.getDetalhes()));
    // return true;
    // }

    // if ("cardapio_atualizar".equals(cmd)) return
    // atualizarPrecosCardapio();
    // private boolean atualizarPrecosCardapio() throws IOException {
    // Client.comm().atualizarCardapio();
    // return true;
    // }

    logger.warn("Invalid Command: " + cmd, new Exception());
    return false;
  }

  /**
   * Creates a new instance of the tabPanelClass. If there is already a tab for
   * that class, just set it as the active tab.
   * 
   * @param tabPanelClass
   *          Tab Panel to Open
   * @return true if new tab opened or activated, false if any error occured
   */
  public boolean openTab(final Class<? extends TabPanel> tabPanelClass) {
    for (int i = 0; i < mainTabs.getTabCount(); i++) {
      final Component c = mainTabs.getComponentAt(i);
      if (c.getClass() == tabPanelClass) {
        mainTabs.setSelectedIndex(i);
        return true;
      }
    }
    try {
      return openTab(tabPanelClass.newInstance());
    } catch (final Exception e) {
      logger.warn("Cannot open tab", e);
      return false;
    }
  }

  /**
   * Creates a new tab with the 'tp' component and set it as the active tab.
   * 
   * @param tp
   *          Tab to create and add.
   * @return always true
   */
  public boolean openTab(final TabPanel tp) {
    mainTabs.addTab("", tp);
    final int i = mainTabs.getTabCount() - 1;
    mainTabs.setTabComponentAt(i,
        new AdvancedTab(tp.getTabText(), mainTabs, tp));
    mainTabs.setSelectedIndex(i);
    tp.tabCreated();
    return true;
  }

  private void saveSizeAndLocation() {
    Client.preferences().MainWindowSize = getSize();
    Client.preferences().MainWindowLocation = getLocation();
    Client.preferences().autosave();
  }

  /**
   * Changes the tab label.
   * 
   * @param tab
   *          Tab to change label.
   * @param label
   *          New label.
   */
  public void setTabTitle(final TabPanel tab, final String label) {
    final int i = mainTabs.indexOfComponent(tab);
    final Component c = mainTabs.getTabComponentAt(i);
    if (c instanceof AdvancedTab) {
      ((AdvancedTab) c).setTitle(label);
    }
  }

  private void setTitle() {
    final String a = Client.config().CompanyName;
    final String b = S.SOFTWARE_NAME.s();
    final boolean na = a.length() == 0;
    final boolean nb = b.length() == 0;

    if (na && nb) {
      setTitle("");
    } else if (na) {
      setTitle(b);
    } else if (nb) {
      setTitle(a);
    } else {
      setTitle(a + " - " + b);
    }
  }

  /**
   * Makes the window visible and starts any dependencies required.
   */
  public void start() {
    new ConnectionDialog(this);
    setVisible(true);
    toolBar.start();
  }
}
