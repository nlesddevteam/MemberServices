package com.awsd.security;

import java.io.Serializable;

import com.awsd.personnel.PersonnelException;

public class Role implements Serializable {

	private static final long serialVersionUID = 7281343416530400800L;

	private String uid;
	private String desc;
	private RoleMembership members;
	private RolePermissions permissions;

	public Role(String uid, String desc) throws PersonnelException, PermissionException {

		this.uid = uid;
		this.desc = desc;
		members = null;
		permissions = null;
	}

	public String getRoleUID() {

		return this.uid;
	}

	public String getRoleDescription() {

		return this.desc;
	}

	public RoleMembership getRoleMembership() throws PersonnelException {

		if (this.members == null) {
			this.members = new RoleMembership(this);
		}
		return this.members;
	}

	public RoleMembershipList getRoleMembershipList() throws PersonnelException {

		return (new RoleMembershipList(this));
	}

	public RolePermissions getRolePermissions() throws PermissionException {

		if (this.permissions == null) {
			this.permissions = new RolePermissions(this);
		}
		return this.permissions;
	}

	public String toXML() {

		StringBuffer xml = new StringBuffer("<ROLE>");
		xml.append("<ID>" + this.uid + "</ID>");
		xml.append("<DISPLAY>" + this.uid + "</DISPLAY>");
		xml.append("<DESCRIPTION>" + this.desc + "</DESCRIPTION>");
		xml.append("</ROLE>");

		return xml.toString();
	}
}