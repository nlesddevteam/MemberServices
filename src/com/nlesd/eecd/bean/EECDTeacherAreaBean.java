package com.nlesd.eecd.bean;
import java.io.Serializable;
import java.util.Date;
import com.nlesd.eecd.constants.TeacherAreaStatus;
public class EECDTeacherAreaBean implements Serializable {
	private static final long serialVersionUID = -844016122427000813L;
	private int id;
	private int personnelId;
	private int areaId;
	private Date dateSubmitted;
	private TeacherAreaStatus currentStatus;
	private boolean isSelected;
	private String areaDescription;
	private String teacherName;
	private String schoolName;
	private String teacherEmail;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPersonnelId() {
		return personnelId;
	}
	public void setPersonnelId(int personnelId) {
		this.personnelId = personnelId;
	}
	public int getAreaId() {
		return areaId;
	}
	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}
	public Date getDateSubmitted() {
		return dateSubmitted;
	}
	public void setDateSubmitted(Date dateSubmitted) {
		this.dateSubmitted = dateSubmitted;
	}
	public TeacherAreaStatus getCurrentStatus() {
		return currentStatus;
	}
	public void setCurrentStatus(TeacherAreaStatus currentStatus) {
		this.currentStatus = currentStatus;
	}
	public boolean isSelected() {
		return isSelected;
	}
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
	public String getAreaDescription() {
		return areaDescription;
	}
	public void setAreaDescription(String areaDescription) {
		this.areaDescription = areaDescription;
	}
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	public String getTeacherEmail() {
		return teacherEmail;
	}
	public void setTeacherEmail(String teacherEmail) {
		this.teacherEmail = teacherEmail;
	}
}
