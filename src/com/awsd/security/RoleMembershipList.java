package com.awsd.security;

import java.util.AbstractCollection;
import java.util.Iterator;
import java.util.Vector;

import com.awsd.personnel.Personnel;
import com.awsd.personnel.PersonnelDB;
import com.awsd.personnel.PersonnelException;

public class RoleMembershipList extends AbstractCollection<Personnel> {

	private Vector<Personnel> membership;
	private Role r;

	public RoleMembershipList(Role r) throws PersonnelException {

		this.r = r;
		membership = PersonnelDB.getPersonnelList(r);
	}

	public boolean add(Personnel o) {

		if (o instanceof Personnel) {
			// boolean check = RoleDB.addRoleMembership(r, p);
			membership.add(o);
		}
		else {
			throw new ClassCastException("RoleMembershipList collections can only contain Personnel objects.");
		}

		return true;
	}

	public Iterator<Personnel> iterator() {

		return membership.iterator();
	}

	public int size() {

		return membership.size();
	}

	public Role getRole() {

		return r;
	}
}