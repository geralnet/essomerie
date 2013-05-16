package net.geral.essomerie.client.resources;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

import net.geral.essomerie._shared.communication.IMessageType;
import net.geral.essomerie._shared.communication.MessageData;
import net.geral.essomerie._shared.communication.MessageSubSystem;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

public enum S {
  BULLETINBOARD_CANCEL("Discard changes and cancel?"),
  BULLETINBOARD_CREATED_BY("Created by %s at %s."),
  BULLETINBOARD_DELETE("This cannot be undone. Delete?"),
  BULLETINBOARD_DELETED("This entry was deleted."),
  BULLETINBOARD_EDIT_TAB_TITLE("Edit: %1$s"),
  BULLETINBOARD_EDIT_TITLE("Title:"),
  BULLETINBOARD_INVALID_TITLE("Invalid title."),
  BULLETINBOARD_LOADING("Load Bulletin Board Entry #%1$d..."),
  BULLETINBOARD_NEW("Bulletin Board Entry"),
  BULLETINBOARD_OUTDATED("This entry was changed and is outdated."),
  BUTTON_CANCEL("Cancel"),
  BUTTON_DELETE("Delete"),
  BUTTON_DETAILS("Details"),
  BUTTON_EDIT("Edit"),
  BUTTON_LOGIN("Login"),
  BUTTON_PRINT("Print"),
  BUTTON_SAVE("Save"),
  BUTTON_SEND("Send"),
  FORMAT_DATE_SIMPLE("dd/MM/yyyy"),
  FORMAT_DATETIME_SIMPLE("dd/MM/yyyy HH:mm:ss"),
  FORMAT_DATETIME_SIMPLE_NO_SECONDS("dd/MM/yyyy HH:mm"),
  INVENTORY_ADJUSTMENT_TITLE("Inventory Adjustment"),
  INVENTORY_GROUP("Group:"),
  INVENTORY_SUBGROUP("Subgroup:"),
  INVENTORY_TITLE("Inventory"),
  MENU_BULLETINBOARD("Bulletin Board"),
  MENU_BULLETINBOARD_ADD("Create New Entry..."),
  MENU_INVENTORY("Inventory"),
  MENU_INVENTORY_MANAGEMENT("Management"),
  MENU_ORGANIZER("Organizer"),
  MENU_ORGANIZER_CALENDAR("Calendar"),
  MENU_ORGANIZER_PERSONS("Persons"),
  MENU_USER_LOGOUT("Logout"),
  MENU_USER_MESSAGES("Messages"),
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
  SOFTWARE_NAME("Essomerie"),
  TITLE_CONFIRM("Confirm"),
  TITLE_ERROR("Error"),
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
  YOUR_PASSWORD("Your Password:"),
  MENU_DELIVERY_CUSTOMERS("Customers"),
  BUTTON_TODAY("today"),
  ORGANIZER_PERSONS_FILTER_ID("ID"),
  ORGANIZER_PERSONS_FILTER_NAME("Name"),
  ORGANIZER_PERSONS_FILTER_TELEPHONE("Telephone"),
  ORGANIZER_PERSONS_FILTER_ADDRESS("Address"),
  ORGANIZER_PERSONS_USER_CALLERID("User Caller ID"),
  ORGANIZER_PERSONS_GENERAL_ID("ID"),
  ORGANIZER_PERSONS_GENERAL_ALIAS_LEGAL("Fantasy Name:"),
  ORGANIZER_PERSONS_GENERAL_ALIAS_NATURAL("Nickname:"),
  ORGANIZER_PERSONS_GENERAL_REGISTERED("Registered:"),
  ORGANIZER_PERSONS_GENERAL_UPDATED("Updated:"),
  ORGANIZER_PERSONS_NATURAL("Natural Person"),
  ORGANIZER_PERSONS_LEGAL("Legal Person"),
  ORGANIZER_PERSONS_GENERAL_NAME("Name:"),
  ORGANIZER_PERSONS_GENERAL_ALIAS("Alias:"),
  ORGANIZER_PERSONS_GENERAL_COMMENTS("Comments:"),
  ORGANIZER_PERSONS_GENERAL_DELETED("Deleted:"),
  ORGANIZER_PERSONS_DELETE_PERSON("Delete %1$s?"),
  ORGANIZER_PERSONS_GENERAL("General"),
  ORGANIZER_PERSONS_DISCARD_CHANGES("Discard changes?"),
  ORGANIZER_PERSONS_GENERAL_UNKNOWN("Unknown"),
  ORGANIZER_PERSONS_FILTER_SEARCH("Search:"),
  BUTTON_ADD("Add"),
  BUTTON_CHANGE("Change"),
  CALLERID_TITLE("Caller ID"),
  CALLERID_LINE("Line:"),
  CALLERID_TELEPHONE("Telephone:"),
  CALLERID_PERSON("Person"),
  ORGANIZER_PERSONS_ADDRESS_POSTALCODE("Postal Code"),
  ORGANIZER_PERSONS_ADDRESS_COUNTRY("Country"),
  ORGANIZER_PERSONS_ADDRESS_STATE("State"),
  ORGANIZER_PERSONS_ADDRESS_SUBURB("Suburb"),
  ORGANIZER_PERSONS_ADDRESS_ADDRESS("Address"),
  ORGANIZER_PERSONS_ADDRESS_COMMENTS("Comments"),
  ORGANIZER_PERSONS_ADDRESS_CLASSIFICATION("Class"),
  MENU_USER("User");

  private static final Logger            logger   = Logger.getLogger(S.class);
  private static HashMap<String, String> messages = new HashMap<>();

  static {
    loadOriginal();
  }

  private static void check() {
    boolean found = false;
    for (final S s : values()) {
      if (s.text == null) {
        if (!found) {
          found = true;
          logger.warn("Missing text for language, see below:");
        }
        logger.warn(s.name() + "=" + s.original);
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
    if (args.length > 0) {
      if ("file".equals(args[0])) {
        main_file(args);
        return;
      }
    }
    System.err.println("Missing parameter, use 'file' or 'enum'.");
  }

  private static void main_file(final String[] args) {
    if (args.length < 2) {
      System.err.println("Missing language to compare with.");
      System.exit(1);
    }
    final S[] ss = S.values();
    Arrays.sort(ss, new Comparator<S>() {
      @Override
      public int compare(final S s1, final S s2) {
        return s1.name().compareTo(s2.name());
      }
    });

    load(args[1]);
    final StringBuilder sb = new StringBuilder();
    for (final S s : ss) {
      if (s.text == null) {
        sb.append(s.name());
        sb.append('=');
        sb.append(s.s());
        sb.append('\n');
      }
    }
    System.out.println(sb.toString());

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
          if (msg == null) {
            continue;
          }
        }
        System.out.println(key + "=" + msg);
      }
    }
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
      if (parts[0].contains(":")) {
        // translation for message
        messages.put(parts[0], parts[1]);
      } else {
        final S s = valueOf(parts[0]);
        s.text = parts[1];
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
