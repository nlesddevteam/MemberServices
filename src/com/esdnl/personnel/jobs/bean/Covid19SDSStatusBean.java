package com.esdnl.personnel.jobs.bean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Covid19SDSStatusBean {
	private int id;
	private String applicantId;
	private String addedBy;
	private Date dateAdded;
	private String sdsId;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getApplicantId() {
		return applicantId;
	}
	public void setApplicantId(String applicantId) {
		this.applicantId = applicantId;
	}
	public String getAddedBy() {
		return addedBy;
	}
	public void setAddedBy(String addedBy) {
		this.addedBy = addedBy;
	}
	public Date getDateAdded() {
		return dateAdded;
	}
	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}
	public String getSdsId() {
		return sdsId;
	}
	public void setSdsId(String sdsId) {
		this.sdsId = sdsId;
	}
	public String getStatusText() {
		StringBuilder sb = new StringBuilder();
		DateFormat dt = new SimpleDateFormat("dd/MM/yyyy"); 
		sb.append("Special Status added on ");
		sb.append(dt.format(this.dateAdded));
		sb.append(" by ");
		sb.append(this.addedBy);
		return sb.toString();
	}
}
