package com.awsd.security;

public class PersonnelApplicantSecurityException extends SecurityException {

	private static final long serialVersionUID = -6649330098433894729L;

	public PersonnelApplicantSecurityException(String msg) {

		super("UNKNOWN APPLICANT", msg);
	}

}
