package net.geral.essomerie.client.core.configuration;

import java.awt.Color;
import java.awt.Dimension;

import net.geral.configuration.ConfigurationBase;
import net.geral.essomerie._shared.User;
import net.geral.lib.table.GNTable;
import net.geral.lib.table.GNTableColumnWidth;

public class CoreConfiguration extends ConfigurationBase {
  /**
   * The name of your company (will be shown in the title bar).
   */
  public String             CompanyName                                         = "";

  /**
   * Look and Feel to use. If you change it, test the software thoroughly to
   * ensure the UI is not broken.
   */
  public String             LookAndFeel                                         = "javax.swing.plaf.nimbus.NimbusLookAndFeel";

  /**
   * Minimum size of the main window. If you change it, test the software
   * thoroughly to ensure the UI is not broken at the minimum size.
   */
  public Dimension          MainWindowMinimumSize                               = new Dimension(
                                                                                    800,
                                                                                    550);

  /**
   * Where to save per-computer preferences. If blank, will not be saved. Per
   * computer preferences are saved automatically independently from which user
   * uses the software.
   */
  public String             PreferencesComputerAutoSaveFile                     = "config/preferences.autosave";

  /**
   * Where to save enforced preferences information. If blank, nothing will be
   * enforced. Any preference that is enforced will be set when the software
   * starts, but it may be changed afterwards.
   */
  public String             PreferencesComputerEnforceFile                      = "config/preferences.enforce";

  /**
   * Try to login using AutoLoginUsername and AutoLoginPassword.
   */
  public boolean            AutoLogin                                           = false;

  /**
   * Username to use if AutoLogin is true.
   */
  public String             AutoLoginUsername                                   = null;

  /**
   * Password to use if AutoLogin is true.
   */
  public String             AutoLoginPassword                                   = null;

  /**
   * Hostname or IP Address of the server.
   */
  public String             ServerAddress                                       = "127.0.0.1";

  /**
   * TCP Port number of the server.
   */
  public int                ServerPort                                          = 2727;

  /**
   * <b>Do not manually set!</b><br>
   * Will be set to the logged user once authentication is complete.
   */
  public User               User                                                = null;

  /**
   * Ammount of spaces to include in both sides of the Main Menu's menus.
   */
  public int                MainMenuSpacing                                     = 2;

  /**
   * Time (hour) at which the roster changes from day shift to night shift.
   */
  public int                TimeRosterShiftChanges                              = 16;

  /**
   * When failed to connect, it will try again. The delay between each try will
   * increase one second every time it fails but will not go higher than this
   * number.
   */
  public int                ConnectionTryAgainDelayMaxSeconds                   = 10;

  /**
   * Delay (milliseconds) to use as the blinking rate of the unread messages
   * icon in the toolbar
   */
  public long               ToolbarHasMessageBlinkRate                          = 500;

  /**
   * Color of the main toolbar. If set, will change the background color --
   * useful to identify specific configurations. E.g. Set to yellow for a test
   * configuration.
   */
  public Color              MainToolbarBackground                               = null;

  /**
   * Maximum number of messages to display.
   */
  public int                MessagesInboxLimit                                  = 100;

  /**
   * Time to show the caller id notification.
   */
  public int                CallerIdNotificationSeconds                         = 10;

  /**
   * Language to use
   */
  public String             Language                                            = null;

  public GNTableColumnWidth TableColumnWidth_Default                            = new GNTableColumnWidth(
                                                                                    0,
                                                                                    GNTable.MAX_WIDTH,
                                                                                    GNTable.MAX_WIDTH);

  public GNTableColumnWidth TableColumnWidth_Default_Date                       = new GNTableColumnWidth(
                                                                                    80);
  public GNTableColumnWidth TableColumnWidth_Default_DateTimeNoSeconds          = new GNTableColumnWidth(
                                                                                    110);
  public GNTableColumnWidth TableColumnWidth_Default_Username                   = TableColumnWidth_Default
                                                                                    .withMinPref(30);
  public GNTableColumnWidth TableColumnWidth_Default_RemoveIcon                 = new GNTableColumnWidth(
                                                                                    16);

  public GNTableColumnWidth TableColumnWidth_Messages_DateTime                  = null;
  public GNTableColumnWidth TableColumnWidth_Messages_From                      = TableColumnWidth_Default_Username
                                                                                    .withMinPref(60);
  public GNTableColumnWidth TableColumnWidth_Messages_Message                   = null;

  public GNTableColumnWidth TableColumnWidth_Organizer_Roster_Asssignment       = TableColumnWidth_Default
                                                                                    .withPref(GNTable.MAX_WIDTH / 2);
  public GNTableColumnWidth TableColumnWidth_Organizer_Roster_Employees         = null;

  public GNTableColumnWidth TableColumnWidth_Organizer_Calendar_Date            = null;

  public GNTableColumnWidth TableColumnWidth_Organizer_Calendar_Message         = null;

  public GNTableColumnWidth TableColumnWidth_Organizer_Calendar_Username        = null;

  public GNTableColumnWidth TableColumnWidth_Inventory_Product                  = null;

  public GNTableColumnWidth TableColumnWidth_Inventory_Quantity                 = new GNTableColumnWidth(
                                                                                    80);
  public GNTableColumnWidth TableColumnWidth_Inventory_Unit                     = new GNTableColumnWidth(
                                                                                    70);
  public GNTableColumnWidth TableColumnWidth_Inventory_Log_Date                 = null;

  public GNTableColumnWidth TableColumnWidth_Inventory_Log_By                   = null;

  public GNTableColumnWidth TableColumnWidth_Inventory_Log_Change               = null;

  public GNTableColumnWidth TableColumnWidth_Inventory_Log_Comments             = null;

  public GNTableColumnWidth TableColumnWidth_Telephones_Type                    = null;

  public GNTableColumnWidth TableColumnWidth_Telephones_Number                  = null;

  public GNTableColumnWidth TableColumnWidth_Addresses_PostalCode               = null;

  public GNTableColumnWidth TableColumnWidth_Addresses_Country                  = null;

  public GNTableColumnWidth TableColumnWidth_Addresses_State                    = null;

  public GNTableColumnWidth TableColumnWidth_Addresses_Suburb                   = null;

  public GNTableColumnWidth TableColumnWidth_Addresses_Address                  = null;

  public GNTableColumnWidth TableColumnWidth_Addresses_Comments                 = null;

  public GNTableColumnWidth TableColumnWidth_Addresses_Class                    = null;

  public int                DeleteIconSize                                      = 16;

  public GNTableColumnWidth TableColumnWidth_Organizer_Persons_Documents_Type   = null;

  public GNTableColumnWidth TableColumnWidth_Organizer_Persons_Documents_Number = null;
}
