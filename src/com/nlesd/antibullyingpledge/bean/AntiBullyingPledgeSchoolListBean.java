package com.nlesd.antibullyingpledge.bean;

import java.io.Serializable;

public class AntiBullyingPledgeSchoolListBean implements Serializable{
	private static final long serialVersionUID = 7479014654525485539L;
	private int schoolId;
	private String schoolName;
	private String schoolCrest;
	private String schoolPhoto;
	public int getSchoolId() {
		return schoolId;
	}
	public void setSchoolId(int schoolId) {
		this.schoolId = schoolId;
	}
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	public String getSchoolCrest() {
		return schoolCrest;
	}
	public void setSchoolCrest(String schoolCrest) {
		this.schoolCrest = schoolCrest;
	}
	public String getSchoolPhoto() {
		return schoolPhoto;
	}
	public void setSchoolPhoto(String schoolPhoto) {
		this.schoolPhoto = schoolPhoto;
	}
}
