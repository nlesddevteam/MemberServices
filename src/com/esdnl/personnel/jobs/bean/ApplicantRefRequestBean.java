package com.esdnl.personnel.jobs.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ApplicantRefRequestBean {
	private int id;
	private String emailAddress;
	private Date dateRequested;
	private Date dateStatus;
	private String requestStatus;
	private int fkReference;
	private int fkAppSup;
	private String referenceType;
	private String applicantId;
	public String getReferenceType() {
		return referenceType;
	}
	public void setReferenceType(String referenceType) {
		this.referenceType = referenceType;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public Date getDateRequested() {
		return dateRequested;
	}
	public void setDateRequested(Date dateRequested) {
		this.dateRequested = dateRequested;
	}
	public Date getDateStatus() {
		return dateStatus;
	}
	public void setDateStatus(Date dateStatus) {
		this.dateStatus = dateStatus;
	}
	public String getRequestStatus() {
		return requestStatus;
	}
	public void setRequestStatus(String requestStatus) {
		this.requestStatus = requestStatus;
	}
	public int getFkReference() {
		return fkReference;
	}
	public void setFkReference(int fkReference) {
		this.fkReference = fkReference;
	}
	public int getFkAppSup() {
		return fkAppSup;
	}
	public void setFkAppSup(int fkAppSup) {
		this.fkAppSup = fkAppSup;
	}
	public String getApplicantId() {
		return applicantId;
	}
	public void setApplicantId(String applicantId) {
		this.applicantId = applicantId;
	}
	public String getDateStatusFormatted()
	{
		return new SimpleDateFormat("dd/MM/yyyy").format(this.dateStatus);
	}
}
