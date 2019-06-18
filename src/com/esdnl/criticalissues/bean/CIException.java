package com.esdnl.criticalissues.bean;

public class CIException extends Exception {

	private static final long serialVersionUID = 3569847714739653767L;

	public CIException(String msg) {

		super(msg);
	}

	public CIException(String msg, Throwable cause) {

		super(msg, cause);
	}

	public CIException(Throwable cause) {

		super(cause);
	}

}
