package com.nlesd.eecd.bean;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
public class EECDTAApprovalBean implements Serializable {
	private static final long serialVersionUID = -844016122427000813L;
	private int teacherAreaId;
	private int teacherPersonnelId;
	private int areaId;
	private String teacherName;
	private String areaDescription;
	private Date dateSubmitted;
	public int getTeacherAreaId() {
		return teacherAreaId;
	}
	public void setTeacherAreaId(int teacherAreaId) {
		this.teacherAreaId = teacherAreaId;
	}
	public int getTeacherPersonnelId() {
		return teacherPersonnelId;
	}
	public void setTeacherPersonnelId(int teacherPersonnelId) {
		this.teacherPersonnelId = teacherPersonnelId;
	}
	public int getAreaId() {
		return areaId;
	}
	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	public String getAreaDescription() {
		return areaDescription;
	}
	public void setAreaDescription(String areaDescription) {
		this.areaDescription = areaDescription;
	}
	public Date getDateSubmitted() {
		return dateSubmitted;
	}
	public void setDateSubmitted(Date dateSubmitted) {
		this.dateSubmitted = dateSubmitted;
	}
	public String getDateSubmittedFormatted()
	{
		return new SimpleDateFormat("MM/dd/yyyy").format(this.dateSubmitted);
	}
}
