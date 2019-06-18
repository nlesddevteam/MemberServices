package com.esdnl.webmaint.esdweb.bean;

import javax.servlet.ServletException;

public class EsdWebException extends ServletException {

	private static final long serialVersionUID = 2327274028051189401L;

	public EsdWebException(String msg) {

		super(msg);
	}

	public EsdWebException(String msg, Throwable e) {

		super(msg, e);
	}
}