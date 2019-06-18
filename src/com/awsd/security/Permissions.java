package com.awsd.security;

import java.util.AbstractCollection;
import java.util.Iterator;
import java.util.Vector;

public class Permissions extends AbstractCollection<Permission> {

	private Vector<Permission> permissions;

	@SuppressWarnings("unchecked")
	public Permissions() throws PermissionException {

		permissions = (Vector<Permission>) (PermissionDB.getPermissions()).clone();
	}

	public boolean add(Permission o) {

		if (o instanceof Permission) {
			permissions.add(o);
		}
		else {
			throw new ClassCastException("Permissions collections can only contain Permission objects.");
		}

		return true;
	}

	public Iterator<Permission> iterator() {

		return permissions.iterator();
	}

	public int size() {

		return permissions.size();
	}
}