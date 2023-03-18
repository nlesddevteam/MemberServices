package com.nlesd.msauditlog.bean;

import java.io.Serializable;
import java.util.Date;

public class MsAuditLogBean implements Serializable {
	private static final long serialVersionUID = -844016122427000813L;
	private int malAuditId;
	private String malAppName;
	private String malAction;
	private String malNotes;
	private int malBy;
	private Date malDate;
	private int malObjectKey;
	public int getMalAuditId() {
		return malAuditId;
	}
	public void setMalAuditId(int malAuditId) {
		this.malAuditId = malAuditId;
	}
	public String getMalAppName() {
		return malAppName;
	}
	public void setMalAppName(String malAppName) {
		this.malAppName = malAppName;
	}
	public String getMalAction() {
		return malAction;
	}
	public void setMalAction(String malAction) {
		this.malAction = malAction;
	}
	public String getMalNotes() {
		return malNotes;
	}
	public void setMalNotes(String malNotes) {
		this.malNotes = malNotes;
	}
	public int getMalBy() {
		return malBy;
	}
	public void setMalBy(int malBy) {
		this.malBy = malBy;
	}
	public Date getMalDate() {
		return malDate;
	}
	public void setMalDate(Date malDate) {
		this.malDate = malDate;
	}
	public int getMalObjectKey() {
		return malObjectKey;
	}
	public void setMalObjectKey(int malObjectKey) {
		this.malObjectKey = malObjectKey;
	}
}
