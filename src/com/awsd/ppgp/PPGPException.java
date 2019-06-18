package com.awsd.ppgp;

import javax.servlet.ServletException;

public class PPGPException extends ServletException {

	private static final long serialVersionUID = 1L;
	private int pid;

	public PPGPException(int pid, String msg) {

		super(msg);

		this.pid = pid;
	}

	PPGPException(String msg, Throwable e) {

		super(msg, e);

		this.pid = -1;
	}

	public PPGPException(String msg) {

		super(msg);

		this.pid = -1;
	}

	public int getPersonnelID() {

		return pid;
	}
}