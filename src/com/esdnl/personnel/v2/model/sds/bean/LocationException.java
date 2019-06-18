package com.esdnl.personnel.v2.model.sds.bean;

import com.esdnl.personnel.v2.model.bean.PersonnelException;

public class LocationException extends PersonnelException {

	private static final long serialVersionUID = 7530768370736021809L;

	public LocationException(String msg) {

		super(msg);
	}

	public LocationException(String msg, Throwable e) {

		super(msg, e);
	}
}