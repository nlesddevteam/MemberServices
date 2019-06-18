package com.awsd.security;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.Map;
import java.util.Set;

import com.awsd.personnel.Personnel;
import com.awsd.personnel.PersonnelException;

public class UserRoles extends AbstractMap<String, Role> implements Serializable {

	private static final long serialVersionUID = 11941837359211508L;

	private Map<String, Role> roles;

	public UserRoles(User usr) throws RoleException, PermissionException, PersonnelException {

		roles = RoleDB.getRoles(usr);
	}

	public UserRoles(Personnel p) throws RoleException, PermissionException, PersonnelException {

		roles = RoleDB.getRoles(p);
	}

	public Set<Map.Entry<String, Role>> entrySet() {

		return roles.entrySet();
	}
}