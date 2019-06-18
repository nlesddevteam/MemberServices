package com.nlesd.school.bean;

import java.io.Serializable;

public class SchoolStreamSelectListBean implements Serializable {

	private static final long serialVersionUID = -3914355675665585056L;
	private String schoolName;
	private Integer schoolId;
	private String selected="";
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	public Integer getSchoolId() {
		return schoolId;
	}
	public void setSchoolId(Integer schoolId) {
		this.schoolId = schoolId;
	}
	public String getSelected() {
		return selected;
	}
	public void setSelected(String selected) {
		this.selected = selected;
	}
	

}
