package com.esdnl.school.registration.bean;

public class SchoolRegistrationException extends Exception {

	private static final long serialVersionUID = 5879279303181507981L;

	public SchoolRegistrationException(String msg) {

		super(msg);
	}

	public SchoolRegistrationException(String msg, Throwable cause) {

		super(msg, cause);
	}

	public SchoolRegistrationException(Throwable cause) {

		super(cause);
	}
}