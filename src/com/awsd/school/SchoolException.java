package com.awsd.school;

import javax.servlet.ServletException;

public class SchoolException extends ServletException {

	private static final long serialVersionUID = 6763023021983210940L;

	private School s;

	public SchoolException(String msg) {

		this(null, msg);
	}

	public SchoolException(School s, String msg) {

		super(msg);

		this.s = s;
	}

	public School getSchool() {

		return this.s;
	}
}