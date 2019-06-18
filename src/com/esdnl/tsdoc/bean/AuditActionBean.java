package com.esdnl.tsdoc.bean;

public class AuditActionBean {

	public static final AuditActionBean ADD = new AuditActionBean(1, "ADDED");
	public static final AuditActionBean READ = new AuditActionBean(2, "READ");
	public static final AuditActionBean ACCESSGRANTED = new AuditActionBean(3, "ACCESS GRANTED");
	public static final AuditActionBean EMAILNOTIFICATION = new AuditActionBean(4, "EMAIL NOTIFICATION");

	public static final AuditActionBean[] ALL = new AuditActionBean[] {
			ADD, READ, ACCESSGRANTED, EMAILNOTIFICATION
	};

	private int value;
	private String description;

	private AuditActionBean(int value, String description) {

		this.value = value;
		this.description = description;
	}

	public int getValue() {

		return value;
	}

	public String getDescription() {

		return description;
	}

	public String toString() {

		return this.description;
	}

	public static AuditActionBean get(int value) {

		AuditActionBean tmp = null;

		for (AuditActionBean bean : AuditActionBean.ALL) {
			if (bean.getValue() == value)
				tmp = bean;
		}

		return tmp;
	}
}
