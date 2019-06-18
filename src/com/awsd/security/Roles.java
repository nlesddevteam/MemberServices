package com.awsd.security;

import java.util.AbstractCollection;
import java.util.Iterator;
import java.util.Vector;

import com.awsd.personnel.PersonnelException;

public class Roles extends AbstractCollection<Role> {

	private Vector<Role> roles;

	@SuppressWarnings("unchecked")
	public Roles() throws RoleException, PermissionException, PersonnelException {

		roles = (Vector<Role>) (RoleDB.getRoles()).clone();
	}

	public boolean add(Role o) {

		if (o instanceof Role) {
			roles.add(o);
		}
		else {
			throw new ClassCastException("Roles collections can only contain Role objects.");
		}

		return true;
	}

	public Iterator<Role> iterator() {

		return roles.iterator();
	}

	public int size() {

		return roles.size();
	}
}