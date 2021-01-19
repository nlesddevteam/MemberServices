package com.esdnl.webupdatesystem.schoolreviews.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TreeMap;

public class SchoolReviewBean implements Serializable {
	private static final long serialVersionUID = -8771605504141293333L;
	private int id;
	private String srName;
	private String srDescription;
	private String srPhoto;
	private String srSchoolYear;
	private int srStatus;
	private String addedBy;
	private Date dateAdded;
	private TreeMap<Integer,SchoolReviewSectionBean> sectionList;
	private String schoolList;
	
	public String getDateAddedFormatted() {
		if(this.dateAdded != null) {
			return new SimpleDateFormat("dd/MM/yyyy").format(this.dateAdded);
		}else {
			return "";
		}
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSrName() {
		return srName;
	}

	public void setSrName(String srName) {
		this.srName = srName;
	}

	public String getSrDescription() {
		return srDescription;
	}

	public void setSrDescription(String srDescription) {
		this.srDescription = srDescription;
	}

	public String getSrPhoto() {
		return srPhoto;
	}

	public void setSrPhoto(String srPhoto) {
		this.srPhoto = srPhoto;
	}

	public String getSrSchoolYear() {
		return srSchoolYear;
	}

	public void setSrSchoolYear(String srSchoolYear) {
		this.srSchoolYear = srSchoolYear;
	}

	public int getSrStatus() {
		return srStatus;
	}

	public void setSrStatus(int srStatus) {
		this.srStatus = srStatus;
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
	public String getFileDateAddedFormatted() {
		if(this.dateAdded != null) {
			return new SimpleDateFormat("dd/MM/yyyy").format(this.dateAdded);
		}else {
			return "";
		}
		
	}

	public TreeMap<Integer, SchoolReviewSectionBean> getSectionList() {
		return sectionList;
	}

	public void setSectionList(TreeMap<Integer, SchoolReviewSectionBean> sectionList) {
		this.sectionList = sectionList;
	}

	public String getSchoolList() {
		return schoolList;
	}

	public void setSchoolList(String schoolList) {
		this.schoolList = schoolList;
	}
}
