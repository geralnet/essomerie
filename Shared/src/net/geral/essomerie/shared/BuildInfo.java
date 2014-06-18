package net.geral.essomerie.shared;

import java.io.Serializable;
import java.util.Comparator;

import org.joda.time.LocalDateTime;

public final class BuildInfo implements Serializable, Comparable<BuildInfo>,
    Comparator<BuildInfo> {
  private static final long     serialVersionUID = 1L;

  public static final BuildInfo CURRENT          = new BuildInfo();

  public final boolean          debug;
  public final LocalDateTime    date;
  public final int              major;
  public final int              minor;
  public final int              revision;
  public final int              build;
  public final int              absoluteBuild;

  private BuildInfo() {
    debug =false; // [BUILDINFO:DEBUG]
    date =new LocalDateTime(1403013951835L); // [BUILDINFO:NOW]
    major = 0;
    minor = 3;
    revision = 0;
    build =104; // [BUILDINFO:INCREMENT]
    absoluteBuild =111; // [BUILDINFO:INCREMENT]
  }

  public BuildInfo(final boolean _debug, final LocalDateTime _date,
      final int _major, final int _minor, final int _revision,
      final int _build, final int _absoluteBuild) {
    debug = _debug;
    date = _date;
    major = _major;
    minor = _minor;
    revision = _revision;
    build = _build;
    absoluteBuild = _absoluteBuild;
  }

  @Override
  public int compare(final BuildInfo o1, final BuildInfo o2) {
    int i;

    i = Integer.compare(o1.major, o2.major);
    if (i != 0) {
      return i;
    }
    i = Integer.compare(o1.minor, o2.minor);
    if (i != 0) {
      return i;
    }
    i = Integer.compare(o1.revision, o2.revision);
    if (i != 0) {
      return i;
    }
    i = Integer.compare(o1.build, o2.build);
    if (i != 0) {
      return i;
    }

    return 0;
  }

  @Override
  public int compareTo(final BuildInfo o) {
    return compare(this, o);
  }

  public String getVersion() {
    return major + "." + minor + "." + revision + "." + build;
  }

  @Override
  public String toString() {
    return getVersion() + ":" + absoluteBuild + (debug ? " [DEBUG]" : "");
  }
}
