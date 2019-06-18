package com.esdnl.audit.bean;

public class AuditException extends Exception {

	private static final long serialVersionUID = 3569847714739653767L;

	public AuditException(String msg) {

		super(msg);
	}

	public AuditException(String msg, Throwable cause) {

		super(msg, cause);
	}

	public AuditException(Throwable cause) {

		super(cause);
	}

}
