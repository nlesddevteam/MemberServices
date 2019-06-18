package com.esdnl.personnel.v2.model.sds.bean;

import com.esdnl.personnel.v2.model.bean.PersonnelException;

public class EmployeeException extends PersonnelException {

	private static final long serialVersionUID = -2093680683810416684L;

	public EmployeeException(String msg) {

		super(msg);
	}

	public EmployeeException(String msg, Throwable e) {

		super(msg, e);
	}
}