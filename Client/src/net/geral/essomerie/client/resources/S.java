package net.geral.essomerie.client.resources;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;

import net.geral.essomerie.shared.communication.IMessageType;
import net.geral.essomerie.shared.communication.MessageData;
import net.geral.essomerie.shared.communication.MessageSubSystem;
import net.geral.lib.filechooser.GNFileChooser;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

public enum S {
  BULLETINBOARD_CANCEL("Discard changes and cancel?"),
  BULLETINBOARD_CREATED_BY("Created by %s at %s."),
  BULLETINBOARD_DELETE("This cannot be undone. Delete?"),
  BULLETINBOARD_DELETED("This entry was deleted."),
  BULLETINBOARD_EDIT_TAB_TITLE("Edit: %1$s"),
  BULLETINBOARD_EDIT_TITLE("Title:"),
  BULLETINBOARD_EDIT_TITLE_INSTRUCTIONS("Use the character '%s' to create a new submenu."),
  BULLETINBOARD_INVALID_TITLE("Invalid title."),
  BULLETINBOARD_LOADING("Load Bulletin Board Entry #%1$d..."),
  BULLETINBOARD_NEW("Bulletin Board Entry"),
  BULLETINBOARD_OUTDATED("This entry was changed and is outdated."),
  BUTTON_ADD("Add"),
  BUTTON_CANCEL("Cancel"),
  BUTTON_CHANGE("Change"),
  BUTTON_CLEAR("Clear"),
  BUTTON_DELETE("Delete"),
  BUTTON_DETAILS("Details"),
  BUTTON_EDIT("Edit"),
  BUTTON_LOGIN("Login"),
  BUTTON_PRINT("Print"),
  BUTTON_REMOVE("Remove"),
  BUTTON_SAVE("Save"),
  BUTTON_SEND("Send"),
  BUTTON_TODAY("today"),
  CALLERID_LINE("Line:"),
  CALLERID_PERSON("Person"),
  CALLERID_TELEPHONE("Telephone:"),
  CALLERID_TITLE("Caller ID"),
  ERROR_INVALID_FIELD("A field contains invalid data."), // FIXME create DateTimeFormat field
                                           ERROR_SAVING_FILE("Error saving file:"),
  // instead
  FORMAT_DATE_SIMPLE("dd/MM/yyyy"),
  FORMAT_DATETIME_SIMPLE("dd/MM/yyyy HH:mm:ss"),
  FORMAT_DATETIME_SIMPLE_NO_SECONDS("dd/MM/yyyy HH:mm"),
  FORMAT_TIME_NO_SECONDS("HH:mm"),
  GNFILECHOOSER_ONLYIMAGES(GNFileChooser.getTextOnlyImages()),
  MENU_BULLETINBOARD("Bulletin Board"),
  MENU_BULLETINBOARD_ADD("Create New Entry..."),
  MENU_ORGANIZER("Organizer"),
  MENU_ORGANIZER_CALENDAR("Calendar"),
  MENU_ORGANIZER_CATALOG("Catalog"),
  MENU_ORGANIZER_PERSONS("Persons"),
  MENU_ORGANIZER_TOOLS("Tools"),
  MENU_ORGANIZER_TOOLS_CATALOGPUBLISH("Publish Catalog"),
  MENU_ORGANIZER_TOOLS_SALESREGISTER("Sales Register"),
  MENU_SYSOP("SysOp"),
  MENU_SYSOP_DEVICES("Devices"),
  MENU_USER("User"),
  MENU_USER_LOGOUT("Logout"),
  MENU_USER_MESSAGES("Messages"),
  MENU_WAREHOUSE("Warehouse"),
  MENU_WAREHOUSE_MANAGEMENT("Management"),
  MESSAGE_FULL("Inbox Full: %1$d of %2$d"),
  MESSAGE_N_MESSAGES("%1$d Messages"),
  MESSAGE_SELECTED_N("%1$d messages selected."),
  MESSAGE_SELECTED_NONE("No messages selected."),
  MESSAGES("Messages"),
  MESSAGES_AT("at"),
  MESSAGES_BUTTON_DELETE("Delete"),
  MESSAGES_BUTTON_NEW("New"),
  MESSAGES_BUTTON_PRINT("Print"),
  MESSAGES_CONFIRM("Delete %1$d messages?"),
  MESSAGES_HEADER_DATETIME("When"),
  MESSAGES_HEADER_FROM("From"),
  MESSAGES_HEADER_MESSAGE("Message"),
  MESSAGES_LEGEND("Legend:"),
  MESSAGES_LEGEND_CONTENTS("Red: broadcasts  -  Bold: unread."),
  MESSAGES_LOADING("Downloading messages, please wait..."),
  MESSAGES_NEW_CANCEL("Discard message?"),
  MESSAGES_NEW_TITLE("New Message"),
  MESSAGES_NEW_TO("To:"),
  MESSAGES_NEW_TO_BROADCAST("Everyone (broadcast)"),
  MESSAGES_NO_MESSAGES("No Messages"),
  MESSAGES_ONE_MESSAGE("1 Message"),
  ORGANIZER_CALENDAR_DELETE_EVENT("Delete selected event?"),
  ORGANIZER_CALENDAR_DETAILS_CHANGED("changed to"),
  ORGANIZER_CALENDAR_DETAILS_CREATED("created the"),
  ORGANIZER_CALENDAR_DETAILS_ENTRY_LINE_1("In %1$s, %2$s (%3$s) %4$s Event #%5$d"),
  ORGANIZER_CALENDAR_DETAILS_ENTRY_LINE_2("Date: %1$s"),
  ORGANIZER_CALENDAR_DETAILS_ENTRY_LINE_3("%1$s"),
  ORGANIZER_CALENDAR_DETAILS_LOADING("Loading event #%d, please wait..."),
  ORGANIZER_CALENDAR_DETAILS_TITLE_LONG("Event #%d - Complete Log"),
  ORGANIZER_CALENDAR_DETAILS_TITLE_SHORT("Event #%d"),
  ORGANIZER_CALENDAR_HEADER_DATE("Date"),
  ORGANIZER_CALENDAR_HEADER_MESSAGE("Event"),
  ORGANIZER_CALENDAR_HEADER_USERNAME("By"),
  ORGANIZER_CALENDAR_NEW_EVENT_MESSAGE("[new event]"),
  ORGANIZER_CALENDAR_ROSTER_DAY("Day Shift"),
  ORGANIZER_CALENDAR_ROSTER_HEADER_ASSIGNMENT("Assignment"),
  ORGANIZER_CALENDAR_ROSTER_HEADER_EMPLOYEES("Employee(s)"),
  ORGANIZER_CALENDAR_ROSTER_NEW("[new assignment]"),
  ORGANIZER_CALENDAR_ROSTER_NIGHT("Night Shift"),
  ORGANIZER_CALENDAR_ROSTER_SAVE("Save changes on roster?"),
  ORGANIZER_CALENDAR_TITLE("Calendar"),
  ORGANIZER_CATALOG("Catalog"),
  ORGANIZER_CATALOG_CHANGED("Save changed catalog information?"),
  ORGANIZER_CATALOG_CONFIRM_REMOVE_GROUP("Permanently delete this group, with all its subgroups and items?"),
  ORGANIZER_CATALOG_CONFIRM_REMOVE_ITEM("Permanently delete this item?"),
  ORGANIZER_CATALOG_EDITOR_ADDGROUP("Add Group"),
  ORGANIZER_CATALOG_EDITOR_ADDITEM("Add Item"),
  ORGANIZER_CATALOG_EDITOR_GROUPDETAILS("Group Details:"),
  ORGANIZER_CATALOG_EDITOR_NOSELECTION("Please, select an item or group before making changes."),
  ORGANIZER_CATALOG_EDITOR_PRICES("Prices"),
  ORGANIZER_CATALOG_EDITOR_TITLESDESCRIPTIONS("Titles & Descriptions:"),
  ORGANIZER_CATALOG_GROUP_DEFAULT_TITLE("New Group"),
  ORGANIZER_CATALOG_GROUP_LANGUAGE("Language"),
  ORGANIZER_CATALOG_GROUP_SUBTITLE("Subtitle"),
  ORGANIZER_CATALOG_GROUP_TITLE("Title"),
  ORGANIZER_CATALOG_GROUP_TITLE_DEFAULT("[add title]"),
  ORGANIZER_CATALOG_ITEM_DEFAULT_TITLE("New Item"),
  ORGANIZER_CATALOG_ITEM_DESCRIPTION("Description"),
  ORGANIZER_CATALOG_ITEM_LANGUAGE("Language"),
  ORGANIZER_CATALOG_ITEM_PRICE("Price"),
  ORGANIZER_CATALOG_ITEM_PRICE_DEFAULT("[new price]"),
  ORGANIZER_CATALOG_ITEM_PRICECODE("Price Code"),
  ORGANIZER_CATALOG_ITEM_TITLE("Title"),
  ORGANIZER_CATALOG_ITEM_TITLE_DEFAULT("[add title]"),
  ORGANIZER_CATALOG_LATEST("Latest Publication"),
  ORGANIZER_CATALOG_LOADING("Loading..."),
  ORGANIZER_CATALOG_PUBLISH_CANNOT_REVERT("You cannot revert the catalog once published!"),
  ORGANIZER_CATALOG_PUBLISH_COMMENTS("Comments:"),
  ORGANIZER_CATALOG_PUBLISH_GO("Publish Now!"),
  ORGANIZER_CATALOG_PUBLISH_SUCCESS("Catalog #%1$d Published!"),
  ORGANIZER_CATALOG_PUBLISH_TITLE("Publish Catalog"),
  ORGANIZER_CATALOG_PUBLISH_WARNING("*** Warning ***"),
  ORGANIZER_CATALOG_SPECIFY("Specify:"),
  ORGANIZER_CATALOG_UNPUBLISHED("Unpublished"),
  ORGANIZER_PERSONS_ADDRESS_ADDRESS("Address"),
  ORGANIZER_PERSONS_ADDRESS_CLASSIFICATION("Class"),
  ORGANIZER_PERSONS_ADDRESS_COMMENTS("Comments"),
  ORGANIZER_PERSONS_ADDRESS_COUNTRY("Country"),
  ORGANIZER_PERSONS_ADDRESS_POSTALCODE("Postal Code"),
  ORGANIZER_PERSONS_ADDRESS_STATE("State"),
  ORGANIZER_PERSONS_ADDRESS_SUBURB("Suburb"),
  ORGANIZER_PERSONS_DELETE_PERSON("Delete %1$s?"),
  ORGANIZER_PERSONS_DISCARD_CHANGES("Discard changes?"),
  ORGANIZER_PERSONS_DOCUMENTS("Documents"),
  ORGANIZER_PERSONS_DOCUMENTS_NUMBER("Number"),
  ORGANIZER_PERSONS_DOCUMENTS_NUMBER_NEW(""),
  ORGANIZER_PERSONS_DOCUMENTS_SAVEPNG("Save PNG"),
  ORGANIZER_PERSONS_DOCUMENTS_TYPE("Type"),
  ORGANIZER_PERSONS_DOCUMENTS_TYPE_NEW("[add new]"),
  ORGANIZER_PERSONS_FILTER_ADDRESS("Address"),
  ORGANIZER_PERSONS_FILTER_ID("ID"),
  ORGANIZER_PERSONS_FILTER_NAME("Name"),
  ORGANIZER_PERSONS_FILTER_SEARCH("Search:"),
  ORGANIZER_PERSONS_FILTER_TELEPHONE("Telephone"),
  ORGANIZER_PERSONS_GENERAL("General"),
  ORGANIZER_PERSONS_GENERAL_ALIAS("Alias:"),
  ORGANIZER_PERSONS_GENERAL_ALIAS_LEGAL("Fantasy Name:"),
  ORGANIZER_PERSONS_GENERAL_ALIAS_NATURAL("Nickname:"),
  ORGANIZER_PERSONS_GENERAL_COMMENTS("Comments:"),
  ORGANIZER_PERSONS_GENERAL_DELETED("Deleted:"),
  ORGANIZER_PERSONS_GENERAL_ID("ID"),
  ORGANIZER_PERSONS_GENERAL_NAME("Name:"),
  ORGANIZER_PERSONS_GENERAL_REGISTERED("Registered:"),
  ORGANIZER_PERSONS_GENERAL_UPDATED("Updated:"),
  ORGANIZER_PERSONS_LEGAL("Legal Person"),
  ORGANIZER_PERSONS_NATURAL("Natural Person"),
  ORGANIZER_PERSONS_SALES("Sales"),
  ORGANIZER_PERSONS_SALES_AVERAGE("Average:"),
  ORGANIZER_PERSONS_SALES_COMMENTS("Comments"),
  ORGANIZER_PERSONS_SALES_FIRSTORDER("First Order:"),
  ORGANIZER_PERSONS_SALES_LASTORDER("Last Order:"),
  ORGANIZER_PERSONS_SALES_ORDERS("Orders:"),
  ORGANIZER_PERSONS_SALES_PRICE("Price"),
  ORGANIZER_PERSONS_SALES_SPENT("Spent:"),
  ORGANIZER_PERSONS_SALES_WHEN("When"),
  ORGANIZER_PERSONS_USER_CALLERID("User Caller ID"),
  SOFTWARE_NAME("Essomerie"),
  SYSOP_DEVICES("SysOp - Devices"),
  SYSOP_DEVICES_CPU("CPU"),
  SYSOP_DEVICES_HOSTNAME("Hostname"),
  SYSOP_DEVICES_INTERFACES_HOSTNAMES("Hostnames"),
  SYSOP_DEVICES_INTERFACES_IP("IP"),
  SYSOP_DEVICES_INTERFACES_MAC("MAC"),
  SYSOP_DEVICES_INTERFACES_NAME("Name"),
  SYSOP_DEVICES_INTERFACES_TYPE("Type"),
  SYSOP_DEVICES_IP("IP"),
  SYSOP_DEVICES_MAC("MAC"),
  SYSOP_DEVICES_MEMORY("Memory"),
  SYSOP_DEVICES_NAME("Name"),
  SYSOP_DEVICES_OS("OS"),
  SYSOP_DEVICES_ROLE("Role"),
  SYSOP_DEVICES_TYPE("Type"),
  SYSOP_DEVICES_VERSION("Version"),
  SYSOP_PIN_MISSING("You need a SysOp PIN to use this feature."),
  SYSOP_PIN_REQUEST("Please enter your SysOp PIN:"),
  SYSOP_PIN_WRONG("Invalid SysOp PIN."),
  TITLE_CONFIRM("Confirm"),
  TITLE_ERROR("Error"),
  TITLE_FATAL_ERROR("Fatal Error"),
  TITLE_RENAME("Rename"),
  TITLE_SAVE("Save"),
  TITLE_SUCCESS("Success!"),
  TOOLS_SALESREGISTER("Sales Register"),
  TOOLS_SALESREGISTER_ADDRESS("Address (use in comment):"),
  TOOLS_SALESREGISTER_AUTOCOMMENT("Sent to: %s"),
  TOOLS_SALESREGISTER_COMMENTS("Comments:"),
  TOOLS_SALESREGISTER_DATETIME("Date/Time:"),
  TOOLS_SALESREGISTER_ID("Person ID:"),
  TOOLS_SALESREGISTER_LOG("Log:"),
  TOOLS_SALESREGISTER_LOG_BYUSER("User"),
  TOOLS_SALESREGISTER_LOG_COMMENTS("Comments"),
  TOOLS_SALESREGISTER_LOG_DATETIME("Sale Date"),
  TOOLS_SALESREGISTER_LOG_PERSON("Person"),
  TOOLS_SALESREGISTER_LOG_PRICE("Price"),
  TOOLS_SALESREGISTER_LOG_REGISTERED("Registered"),
  TOOLS_SALESREGISTER_PERSON("Person:"),
  TOOLS_SALESREGISTER_PRICE("Price:"),
  TOOLS_SALESREGISTER_TIME_CUSTOM("Specify%s"),
  TOOLS_SALESREGISTER_TIME_DAY("Day"),
  TOOLS_SALESREGISTER_TIME_NIGHT("Night"),
  VERSION_ERROR("This software is outdated, please update!\n\nClient: v%1$s\nServer: v%2$s"),
  WAREHOUSE_ADJUSTMENT_TITLE("Warehouse Management"),
  WAREHOUSE_ADMIN_DELETE_CONFIRM("*** This action cannot be undone! ***\n\nDelete '%s'?"),
  WAREHOUSE_ADMIN_NEWITEM_NAME("New Warehouse Item"),
  WAREHOUSE_ADMIN_NEWITEM_TEXT("[add new item]"),
  WAREHOUSE_ADMIN_SAVING("[Saving '%s' ...]"),
  WAREHOUSE_ALLOW_ITEM_ADMIN("Allow Item Administration"),
  WAREHOUSE_ALLOW_ITEM_ADMIN_CONFIRM("This option allows you to change the groups and items of the WAREHOUSE.\nMany changes cannot be undone.\n\nContinue?"),
  WAREHOUSE_CHANGE_BUTTON_CANCEL("Cancel (Esc)"),
  WAREHOUSE_CHANGE_BUTTON_DOWN("Down ( \u2193 )"),
  WAREHOUSE_CHANGE_BUTTON_SAVE("Save (Enter)"),
  WAREHOUSE_CHANGE_BUTTON_UP("Up ( \u2191 )"),
  WAREHOUSE_CHANGE_COMMENTS("Comments"),
  WAREHOUSE_CHANGE_ERROR_NEGATIVE_1("Quantity is negative!"),
  WAREHOUSE_CHANGE_ERROR_NEGATIVE_2("Will be set to 0 (zero)."),
  WAREHOUSE_CHANGE_MODE_DECREASE("Decrease"),
  WAREHOUSE_CHANGE_MODE_INCREASE("Increase"),
  WAREHOUSE_CHANGE_MODE_SET("Set"),
  WAREHOUSE_CHANGE_MODE_TITLE("Mode:"),
  WAREHOUSE_CHANGE_QUANTITY("Quantity"),
  WAREHOUSE_CHANGE_REASON("Reason:"),
  WAREHOUSE_GROUP("Group:"),
  WAREHOUSE_GROUP_POPUP_ADD("Add Group"),
  WAREHOUSE_GROUP_POPUP_ADD_TEXT("New group name:"),
  WAREHOUSE_GROUP_POPUP_ADD_TITLE("New Group"),
  WAREHOUSE_GROUP_POPUP_REMOVE("Remove Group"),
  WAREHOUSE_GROUP_POPUP_REMOVE_CONFIRM("*** This action cannot be undone. ***\n\nRemove the group \"%1$s\"?"),
  WAREHOUSE_GROUP_POPUP_RENAME("Rename Group"),
  WAREHOUSE_GROUP_POPUP_RENAME_TEXT("Rename \"%1$s\" to:"),
  WAREHOUSE_HISTORY("History Log"),
  WAREHOUSE_ITEM_REPORT_DATE("Date"),
  WAREHOUSE_ITEM_REPORT_DELTA("Delta"),
  WAREHOUSE_ITEM_REPORT_INITIAL("Initial"),
  WAREHOUSE_LOGTABLE_BY("By"),
  WAREHOUSE_LOGTABLE_CHANGE("Change"),
  WAREHOUSE_LOGTABLE_COMMENTS("Comments"),
  WAREHOUSE_LOGTABLE_WHEN("When"),
  WAREHOUSE_PRINT_ADJUSTMENTS("Print Adjustments"),
  WAREHOUSE_PRINT_CHECKLIST("Imprimir Checklist"),
  WAREHOUSE_REPORT("Report"),
  WAREHOUSE_REPORT_ITEM_TAB("Report: %s"),
  WAREHOUSE_REPORT_ITEM_TITLE("Item Report: %1$s (%2$s)"),
  WAREHOUSE_SELECT_ITEM("[select an item]"),
  WAREHOUSE_SUBGROUP("Subgroup:"),
  WAREHOUSE_TABLE_MAXIMUM("Max"),
  WAREHOUSE_TABLE_MINIMUM("Min"),
  WAREHOUSE_TABLE_PRODUCT("Product"),
  WAREHOUSE_TABLE_QUANTITY("Qty"),
  WAREHOUSE_TABLE_UNIT("Un"),
  WAREHOUSE_TITLE("Warehouse"),
  WINDOW_AUTH_CONNECTED("connected!"),
  WINDOW_AUTH_CONNECTING("Connecting to %1$s:%2$d..."),
  WINDOW_AUTH_CONNECTION_FAILED("failed!"),
  WINDOW_AUTH_INVALID_PASSWORD("Invalid Password!"),
  WINDOW_AUTH_INVALID_USER("Invalid User!"),
  WINDOW_AUTH_LOGGING_IN("Logging in..."),
  WINDOW_AUTH_REQUESTING_USERS("Requesting user list..."),
  WINDOW_AUTH_TITLE("Authentication"),
  WINDOW_AUTH_TRYING_AGAIN_IN("Trying again in..."),
  WINDOW_AUTH_WRONG_PASSWORD("Incorrect Password!"),
  WINDOW_MAIN_CLOSE_PROMPT("Do you want to close the application?"),
  YOUR_NAME("Your Name:"),
  YOUR_PASSWORD("Your Password:");

  private static final Logger            logger   = Logger.getLogger(S.class);
  private static HashMap<String, String> messages = new HashMap<>();

  static {
    loadOriginal();
  }

  private static String addSlashes(final String txt) {
    return txt.replaceAll("\n", "\\\\n");
  }

  private static void check() {
    boolean found = false;
    for (final S s : values()) {
      if (s.text == null) {
        if (!found) {
          found = true;
          logger.warn("Missing text for language, see below:");
        }
        logger.warn(s.name() + "=" + addSlashes(s.original));
      }
    }
  }

  public static void load(final String language) {
    logger.debug("Loading language: " + language);
    reset();
    if (language == null) {
      loadOriginal();
    } else {
      loadFile(language);
    }
    check();
  }

  private static void loadFile(final String language) {
    final String file = "/res/txt/" + language + ".txt";
    final URL url = S.class.getResource(file);
    if (url == null) {
      logger.warn("Cannot find image resource: " + file);
    }

    try (BufferedReader reader = new BufferedReader(new InputStreamReader(
        url.openStream()))) {
      String line;
      while ((line = reader.readLine()) != null) {
        set(line);
      }
    } catch (final Exception e) {
      logger.warn(e, e);
    }
  }

  private static void loadOriginal() {
    for (final S s : values()) {
      s.text = s.original;
    }
    messages.clear();
  }

  public static void main(final String[] args) {
    BasicConfigurator.configure();
    if (args.length < 1) {
      System.err.println("Missing language to compare with.");
      System.exit(1);
    }
    load(args[0]);

    main_dumpTranslated();
    main_dumpMissing();
  }

  private static void main_dumpMissing() {
    System.out.println();
    System.out.println("********** MISSING **********");
    for (final S s : values()) {
      if (s.text == null) {
        System.out.println(s.name() + "=" + addSlashes(s.original));
      }
    }

    for (final MessageSubSystem mss : MessageSubSystem.values()) {
      final IMessageType[] msgs = MessageSubSystem.getMessages(mss);
      if (msgs == null) {
        logger.warn("No messages for: " + mss.name());
        System.exit(1);
      }
      for (final IMessageType imt : msgs) {
        final String name = ((Enum<?>) imt).name();
        final String key = mss.name() + ":" + name;
        String msg = messages.get(key);
        if (msg == null) {
          msg = imt.toEnglish();
          if (msg != null) {
            System.out.println(key + "=" + imt.toEnglish());
          }
        }
      }
    }
    System.out.println();
  }

  private static void main_dumpTranslated() {
    System.out.println();
    System.out.println("********** TRANSLATED **********");
    for (final S s : values()) {
      if (s.text != null) {
        System.out.println(s.name() + "=" + s.text);
      }
    }

    for (final MessageSubSystem mss : MessageSubSystem.values()) {
      final IMessageType[] msgs = MessageSubSystem.getMessages(mss);
      if (msgs == null) {
        logger.warn("No messages for: " + mss.name());
        System.exit(1);
      }
      for (final IMessageType imt : msgs) {
        final String name = ((Enum<?>) imt).name();
        final String key = mss.name() + ":" + name;
        final String msg = messages.get(key);
        if (msg != null) {
          System.out.println(key + "=" + msg);
        }
      }
    }
    System.out.println();
  }

  public static String msg(final MessageData md) {
    if (md == null) {
      logger.warn("Invalid Message Data: null");
      return "-";
    }
    final String key = md.getSubSystem().name() + ":" + md.getType().name();
    String s = messages.get(key);
    if (s == null) {
      logger.warn("No message text for " + key);
      s = ((IMessageType) md.getType()).toEnglish();
      if (s == null) {
        logger.warn("No english text for " + key);
        s = key;
      }
    }
    return s;
  }

  private static String prepareText(String s) {
    s = s.replaceAll("\\n", "\n");
    return s;
  }

  private static void reset() {
    for (final S s : values()) {
      s.text = null;
    }
  }

  private static void set(final String line) {
    if (line.length() == 0) {
      return;
    }
    final String[] parts = line.split("=", 2);
    if (parts.length != 2) {
      logger.warn("Invalid line: " + line);
      return;
    }
    try {
      final String text = prepareText(parts[1]);
      if (parts[0].contains(":")) {
        // translation for message
        messages.put(parts[0], text);
      } else {
        final S s = valueOf(parts[0]);
        s.text = text;
      }
    } catch (final IllegalArgumentException e) {
      logger.warn("Invalid language expression: " + parts[0]);
    }
  }

  public static void set(final String name, final String value) {
    valueOf(name).text = value;
  }

  private final String original;
  private String       text;

  private S(final String s) {
    original = s;
    text = s;
  }

  public String s() {
    if (text == null) {
      logger.warn("No language text for: " + name());
      return original;
    }
    return text;
  }

  public String s(final Object... params) {
    return String.format(s(), params);
  }

  @Override
  public String toString() {
    return s();
  }
}
