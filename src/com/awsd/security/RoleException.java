package com.awsd.security;

import javax.servlet.ServletException;

public class RoleException extends ServletException {

	private static final long serialVersionUID = -8544641848311612732L;
	private Role r;

	public RoleException(String msg) {

		this(null, msg);
	}

	public RoleException(Role r, String msg) {

		super(msg);

		this.r = r;
	}

	public Role getRole() {

		return this.r;
	}
}