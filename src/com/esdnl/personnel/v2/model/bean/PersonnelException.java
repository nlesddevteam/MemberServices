package com.esdnl.personnel.v2.model.bean;

public class PersonnelException extends Exception {

	private static final long serialVersionUID = -1115386211726706055L;

	public PersonnelException(String msg) {

		super(msg);
	}

	public PersonnelException(String msg, Throwable e) {

		super(msg, e);
	}
}