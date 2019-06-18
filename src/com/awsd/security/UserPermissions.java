package com.awsd.security;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.Map;
import java.util.Set;

public class UserPermissions extends AbstractMap<String, Permission> implements Serializable {

	private static final long serialVersionUID = 4476394024908767417L;
	private Map<String, Permission> permissions;

	public UserPermissions(User usr) throws PermissionException {

		permissions = PermissionDB.getPermissions(usr);
	}

	public Set<Entry<String, Permission>> entrySet() {

		return permissions.entrySet();
	}
}