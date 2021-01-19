package com.esdnl.webupdatesystem.schoolreviews.bean;

import java.io.Serializable;

public class SchoolReviewSchoolBean implements Serializable {
	private static final long serialVersionUID = -8771605504141293333L;
	private int id;
	private int reviewId;
	private int schoolId;
	private String schoolName;
	private boolean isSelected=false;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getReviewId() {
		return reviewId;
	}
	public void setReviewId(int reviewId) {
		this.reviewId = reviewId;
	}
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
	public boolean isSelected() {
		return isSelected;
	}
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
}
