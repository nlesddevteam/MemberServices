package com.esdnl.personnel.jobs.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ApplicantVerificationBean implements Serializable {

	private static final long serialVersionUID = -660787786507967304L;
	private int avid;
	private Date dateVerified;
	private long verifiedBy;
	private String applicantId;
	private String verifiedByName;
	public int getAvid() {
		return avid;
	}
	public void setAvid(int avid) {
		this.avid = avid;
	}
	public Date getDateVerified() {
		return dateVerified;
	}
	public void setDateVerified(Date dateVerified) {
		this.dateVerified = dateVerified;
	}
	public long getVerifiedBy() {
		return verifiedBy;
	}
	public void setVerifiedBy(long verifiedBy) {
		this.verifiedBy = verifiedBy;
	}
	public String getApplicantId() {
		return applicantId;
	}
	public void setApplicantId(String applicantId) {
		this.applicantId = applicantId;
	}
	public String getVerifiedByName() {
		return verifiedByName;
	}
	public void setVerifiedByName(String verifiedByName) {
		this.verifiedByName = verifiedByName;
	}
	public String getDateVerifiedFormatted() {
		if(this.dateVerified != null) {
			return new SimpleDateFormat("dd/MM/yyyy").format(this.dateVerified);
		}else {
			return "";
		}
	}
}
