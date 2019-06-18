package com.esdnl.personnel.jobs.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ApplicantJobAppliedBean implements Serializable {

	private static final long serialVersionUID = -660787786507967304L;
	public static final String DATE_FORMAT = "dd/MM/yyyy";
	private String compNum;
	private String posTitle;
	private Date appliedDate;
	private String schoolName;
	public String getCompNum() {
		return compNum;
	}
	public void setCompNum(String compNum) {
		this.compNum = compNum;
	}
	public String getPosTitle() {
		return posTitle;
	}
	public void setPosTitle(String posTitle) {
		this.posTitle = posTitle;
	}
	public Date getAppliedDate() {
		return appliedDate;
	}
	public void setAppliedDate(Date appliedDate) {
		this.appliedDate = appliedDate;
	}
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	public String getFormattedAppliedDate() {

		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);

		return sdf.format(this.appliedDate);
	}

}
