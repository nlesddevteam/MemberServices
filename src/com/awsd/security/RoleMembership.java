package com.awsd.security;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Set;

import com.awsd.personnel.Personnel;
import com.awsd.personnel.PersonnelDB;
import com.awsd.personnel.PersonnelException;

public class RoleMembership extends AbstractMap<Integer, Personnel> {

	private Map<Integer, Personnel> membership;
	private Role r;

	public RoleMembership(Role r) throws PersonnelException {

		this.r = r;
		membership = PersonnelDB.getPersonnel(r);
	}

	public Set<Map.Entry<Integer, Personnel>> entrySet() {

		return membership.entrySet();
	}

	public Role getRole() {

		return r;
	}

	public boolean add(Personnel p) throws RoleException {

		boolean check = RoleDB.addRoleMembership(r, p);
		membership.put(new Integer(p.getPersonnelID()), p);

		return check;
	}

	public boolean delete(Personnel p) throws RoleException {

		boolean check = RoleDB.deleteRoleMembership(r, p);
		membership.remove(new Integer(p.getPersonnelID()));

		return check;
	}
}