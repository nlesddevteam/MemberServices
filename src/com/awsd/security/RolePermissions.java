package com.awsd.security;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Set;

public class RolePermissions extends AbstractMap<String, Permission> {

	private Map<String, Permission> permissions;
	private Role r;

	public RolePermissions(Role r) throws PermissionException {

		this.r = r;
		permissions = PermissionDB.getPermissions(r);
	}

	public Set<Map.Entry<String, Permission>> entrySet() {

		return permissions.entrySet();
	}

	public Role getRole() {

		return r;
	}

	public boolean add(Permission p) throws RoleException {

		boolean check = RoleDB.addRolePermission(r, p);
		permissions.put(p.getPermissionUID(), p);

		return check;
	}

	public boolean delete(Permission p) throws RoleException {

		boolean check = RoleDB.deleteRolePermission(r, p);
		permissions.remove(p.getPermissionUID());

		return check;
	}
}