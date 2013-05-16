package net.geral.essomerie._shared;

import java.io.Serializable;

public class UserPermissions implements Serializable {
	private static final long serialVersionUID = 1L;
	private final boolean[] permissions;

	public UserPermissions() {
		// set all to false anyway
		final int n = UserPermission.values().length;
		permissions = new boolean[n];
		for (int i = 0; i < n; i++) {
			permissions[i] = false;
		}
	}

	public boolean get(final UserPermission p) {
		if (p == null) {
			return true;
		}
		return permissions[p.ordinal()];
	}

	public void set(final UserPermission up, final boolean yn) {
		permissions[up.ordinal()] = yn;
	}
}
