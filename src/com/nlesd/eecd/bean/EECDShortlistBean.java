package com.nlesd.eecd.bean;

import java.io.Serializable;
import java.util.Date;

public class EECDShortlistBean  implements Serializable {
	private static final long serialVersionUID = -844016122427000813L;
	private int id;
	private boolean shortlistCompleted;
	private Date dateCompleted;
	private String completedBy;
	private String schoolYear;
	private int areaId;
	public int getAreaId() {
		return areaId;
	}
	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public boolean getShortlistCompleted() {
		return shortlistCompleted;
	}
	public void setShortlistCompleted(boolean shortlistCompleted) {
		this.shortlistCompleted = shortlistCompleted;
	}
	public Date getDateCompleted() {
		return dateCompleted;
	}
	public void setDateCompleted(Date dateCompleted) {
		this.dateCompleted = dateCompleted;
	}
	public String getCompletedBy() {
		return completedBy;
	}
	public void setCompletedBy(String completedBy) {
		this.completedBy = completedBy;
	}
	public String getSchoolYear() {
		return schoolYear;
	}
	public void setSchoolYear(String schoolYear) {
		this.schoolYear = schoolYear;
	}
}
