package com.esdnl.personnel.jobs.bean;

import java.io.Serializable;

public class ApplicantRecListBean implements Serializable {
	private static final long serialVersionUID = 7479014654525485539L;
	private int recId;
	private String compNumber;
	private String jobType;
	private String jobUnit;
	private String recStatus;
	private String recDate;
	public int getRecId() {
		return recId;
	}
	public void setRecId(int recId) {
		this.recId = recId;
	}
	public String getCompNumber() {
		return compNumber;
	}
	public void setCompNumber(String compNumber) {
		this.compNumber = compNumber;
	}
	public String getJobType() {
		return jobType;
	}
	public void setJobType(String jobType) {
		this.jobType = jobType;
	}
	public String getJobUnit() {
		return jobUnit;
	}
	public void setJobUnit(String jobUnit) {
		this.jobUnit = jobUnit;
	}
	public String getRecStatus() {
		return recStatus;
	}
	public void setRecStatus(String recStatus) {
		this.recStatus = recStatus;
	}
	public String getRecDate() {
		return recDate;
	}
	public void setRecDate(String recDate) {
		this.recDate = recDate;
	}

}
