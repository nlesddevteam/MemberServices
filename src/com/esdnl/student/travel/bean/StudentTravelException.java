package com.esdnl.student.travel.bean;

public class StudentTravelException extends Exception {

	private static final long serialVersionUID = 7861470855152767415L;

	public StudentTravelException(String msg) {

		super(msg);
	}

	public StudentTravelException(String msg, Throwable cause) {

		super(msg, cause);
	}

	public StudentTravelException(Throwable cause) {

		super(cause);
	}
}