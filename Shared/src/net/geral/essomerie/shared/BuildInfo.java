package net.geral.essomerie.shared;

import java.io.Serializable;

import org.joda.time.LocalDateTime;

public final class BuildInfo implements Serializable {
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
    debug = false; // [BUILDINFO:DEBUG]
    date = new LocalDateTime(1372870966050L); // [BUILDINFO:NOW]
    major = 0;
    minor = 0;
    revision = 1;
    build = 99; // [BUILDINFO:INCREMENT]
    absoluteBuild = 106; // [BUILDINFO:INCREMENT]
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

  public String getVersion() {
    return major + "." + minor + "." + revision + "." + build;
  }

  @Override
  public String toString() {
    return getVersion() + ":" + absoluteBuild + (debug ? " [DEBUG]" : "");
  }
}
