package com.nlesd.bcs.bean;

import java.io.Serializable;
import java.util.Date;

public class BussingContractorSecurityArcBean implements Serializable {

	private static final long serialVersionUID = -844016122427000813L;
	private int id;
	private int securityId;
	private String oldPassword;
	private String newPassword;
	private Date dateChanged;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getSecurityId() {
		return securityId;
	}
	public void setSecurityId(int securityId) {
		this.securityId = securityId;
	}
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public Date getDateChanged() {
		return dateChanged;
	}
	public void setDateChanged(Date dateChanged) {
		this.dateChanged = dateChanged;
	}
	
}
