package com.nlesd.eecd.bean;
import java.io.Serializable;
import java.util.Date;
public class EECDAreaBean implements Serializable {
	private static final long serialVersionUID = -844016122427000813L;
	private int id;
	private String areaDescription;
	private String addedBy;
	private Date dateAdded;
	private boolean isDeleted;
	private String additionalText;
	private int currentStatus;
	private boolean shortlistCompleted;
	private Date dateCompleted;
	private String completedBy;
	private String eligibleTeachers;
	private String required;
	private String schoolYear;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAreaDescription() {
		return areaDescription;
	}
	public void setAreaDescription(String areaDescription) {
		this.areaDescription = areaDescription;
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
	public boolean isDeleted() {
		return isDeleted;
	}
	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	public String getAdditionalText() {
		return additionalText;
	}
	public void setAdditionalText(String additionalText) {
		this.additionalText = additionalText;
	}
	public int getCurrentStatus() {
		return currentStatus;
	}
	public void setCurrentStatus(int currentStatus) {
		this.currentStatus = currentStatus;
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
	public String getEligibleTeachers() {
		return eligibleTeachers;
	}
	public void setEligibleTeachers(String eligibleTeachers) {
		this.eligibleTeachers = eligibleTeachers;
	}
	public String getRequired() {
		return required;
	}
	public void setRequired(String required) {
		this.required = required;
	}
	public String getSchoolYear() {
		return schoolYear;
	}
	public void setSchoolYear(String schoolYear) {
		this.schoolYear = schoolYear;
	}
}
