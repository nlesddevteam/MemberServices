package com.esdnl.personnel.sss.domain.bean;

public class SSSException extends Exception {

	private static final long serialVersionUID = -8734486362865967567L;

	public SSSException(String msg) {

		super(msg);
	}

	public SSSException(String msg, Throwable e) {

		super(msg, e);
	}

}
