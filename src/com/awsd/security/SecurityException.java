package com.awsd.security;

import javax.servlet.ServletException;

public class SecurityException extends ServletException {

	private static final long serialVersionUID = 1233601309976008370L;
	private String username;

	public SecurityException(String msg) {

		this("UNKNOWN", msg);
	}

	public SecurityException(String username, String msg) {

		super(msg);
		this.username = username;
	}

	public String getUsername() {

		return username;
	}
}