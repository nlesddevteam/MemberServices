package com.esdnl.personnel.jobs.bean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ApplicantCovid19LogBean {
	private int aclId;
	private int documentId;
	private Date dateVerified;
	private String verifiedBy;
	public int getAclId() {
		return aclId;
	}
	public void setAclId(int aclId) {
		this.aclId = aclId;
	}
	public int getDocumentId() {
		return documentId;
	}
	public void setDocumentId(int documentId) {
		this.documentId = documentId;
	}
	public Date getDateVerified() {
		return dateVerified;
	}
	public void setDateVerified(Date dateVerified) {
		this.dateVerified = dateVerified;
	}
	public String getVerifiedBy() {
		return verifiedBy;
	}
	public void setVerifiedBy(String verifiedBy) {
		this.verifiedBy = verifiedBy;
	}
	public String getDateVerifiedFormatted() {
		if(this.dateVerified == null) {
			return "";
		}else {
			DateFormat dt = new SimpleDateFormat("dd-MMMM-yyyy"); 
			return dt.format(this.dateVerified);
		}
	}
}
