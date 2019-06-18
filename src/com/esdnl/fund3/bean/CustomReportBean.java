package com.esdnl.fund3.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomReportBean implements Serializable {
	private static final long serialVersionUID = -8771605504141293333L;
	private Integer id;
	private String reportTitle;
	private Date dateCreated;
	private Date dateLastUsed;
	private String reportType;
	private String userId;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getReportTitle() {
		return reportTitle;
	}
	public void setReportTitle(String reportTitle) {
		this.reportTitle = reportTitle;
	}
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	public Date getDateLastUsed() {
		return dateLastUsed;
	}
	public void setDateLastUsed(Date dateLastUsed) {
		this.dateLastUsed = dateLastUsed;
	}
	public String getReportType() {
		return reportType;
	}
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getDateCreatedFormatted() {
		if(this.dateCreated != null){
			return new SimpleDateFormat("dd/MM/yyyy").format(this.dateCreated);
		}else{
			return "";
		}
	}
	public String getDateLastUsedFormatted() {
		if(this.dateLastUsed != null){
			return new SimpleDateFormat("dd/MM/yyyy").format(this.dateLastUsed);
		}else{
			return "";
		}
	}	
}
