package com.esdnl.personnel.jobs.bean;

public class Covid19EmailListBean {
	private String firstName;
	private String lastName;
	private String emailAddress;
	private long documentId;
	private String dateVerified;
	private String dateRejected;
	private boolean exemptionDoc;
	private String location;
	private boolean isSpecialStatus=false;
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public long getDocumentId() {
		return documentId;
	}
	public void setDocumentId(long documentId) {
		this.documentId = documentId;
	}
	public String getDateVerified() {
		return dateVerified;
	}
	public void setDateVerified(String dateVerified) {
		this.dateVerified = dateVerified;
	}
	public String getDateRejected() {
		return dateRejected;
	}
	public void setDateRejected(String dateRejected) {
		this.dateRejected = dateRejected;
	}
	public boolean isExemptionDoc() {
		return exemptionDoc;
	}
	public void setExemptionDoc(boolean exemptionDoc) {
		this.exemptionDoc = exemptionDoc;
	}
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.firstName + ",");
		sb.append(this.lastName + ",");
		sb.append(this.emailAddress + ",");
		sb.append(String.valueOf(this.documentId));
		if(this.dateVerified == null) {
			sb.append(",");
		}else {
			sb.append(this.dateVerified + ",");
		}
		if(this.dateRejected== null) {
			sb.append(",");
		}else {
			sb.append(this.dateRejected + ",");
		}
		sb.append("\r\n");
		return sb.toString();
	}
	public String toStringShort() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.firstName + ",");
		sb.append(this.lastName + ",");
		sb.append(this.emailAddress + ",");
		sb.append(this.location);
		sb.append("\r\n");
		return sb.toString();
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getFullName() {
		return this.firstName + " " + this.lastName;
	}
	public boolean isSpecialStatus() {
		return isSpecialStatus;
	}
	public void setSpecialStatus(boolean isSpecialStatus) {
		this.isSpecialStatus = isSpecialStatus;
	}
}
