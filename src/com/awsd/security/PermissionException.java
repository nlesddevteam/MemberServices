package com.awsd.security;

import javax.servlet.ServletException;

public class PermissionException extends ServletException {

	private static final long serialVersionUID = -3042671927521668902L;

	private Permission p;

	public PermissionException(String msg) {

		this(null, msg);
	}

	public PermissionException(Permission p, String msg) {

		super(msg);

		this.p = p;
	}

	public Permission getPermission() {

		return this.p;
	}
}