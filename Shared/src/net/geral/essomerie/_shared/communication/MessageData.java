package net.geral.essomerie._shared.communication;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class MessageData implements Serializable {
  private static final long                  serialVersionUID = 1L;
  private static long                        nextId           = 0;

  private final long                         id               = nextId++;
  private final MessageSubSystem             subsystem;
  private final Enum<? extends IMessageType> type;
  private final ArrayList<Object>            objects          = new ArrayList<Object>();
  private int                                next             = 0;

  public MessageData(final MessageSubSystem subsystem,
      final Enum<? extends IMessageType> type, final Object... objects) {
    if (subsystem == null) {
      throw new IllegalArgumentException("subsystem cannot be null");
    }
    if (type == null) {
      throw new IllegalArgumentException("type cannot be null");
    }

    this.subsystem = subsystem;
    this.type = type;
    for (final Object o : objects) {
      add(o);
    }
  }

  private void add(final Object o) {
    objects.add(o);
  }

  public Object get() {
    if (objects.size() >= next) {
      return objects.get(next++);
    }
    return null;
  }

  public boolean getBoolean() {
    return ((Boolean) get()).booleanValue();
  }

  public float getFloat() {
    return ((Float) get()).floatValue();
  }

  public long getId() {
    return id;
  }

  public int getInt() {
    return ((Integer) get()).intValue();
  }

  public long getLong() {
    return ((Long) get()).longValue();
  }

  public String getString() {
    return (String) get();
  }

  public MessageSubSystem getSubSystem() {
    return subsystem;
  }

  public Enum<? extends IMessageType> getType() {
    return type;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append(subsystem.name());
    sb.append(".");
    sb.append(type.name());
    sb.append('[');
    if (objects.size() > 0) {
      for (final Object o : objects) {
        if (o == null) {
          sb.append("null");
        } else if (o.getClass().isArray()) {
          sb.append(o.getClass().getComponentType());
          sb.append("[");
          sb.append(Array.getLength(o));
          sb.append("]");
        } else {
          sb.append(o.toString());
        }
        sb.append(';');
      }
      sb.deleteCharAt(sb.length() - 1);
    }
    sb.append(']');
    return sb.toString();
  }
}
