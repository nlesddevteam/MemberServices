package com.nlesd.eecd.bean;
import java.io.Serializable;
import java.util.Date;
import com.nlesd.eecd.constants.TeacherAreaStatus;
public class EECDTeacherAreaStatusBean implements Serializable {
	private static final long serialVersionUID = -844016122427000813L;
	private TeacherAreaStatus status;
	private int statusBy;
	private String statusNotes;
	private Date statusDate;
	private int teacherAreaId;
	private int personnelId;
	public TeacherAreaStatus getStatus() {
		return status;
	}
	public void setStatus(TeacherAreaStatus status) {
		this.status = status;
	}
	public int getStatusBy() {
		return statusBy;
	}
	public void setStatusBy(int statusBy) {
		this.statusBy = statusBy;
	}
	public String getStatusNotes() {
		return statusNotes;
	}
	public void setStatusNotes(String statusNotes) {
		this.statusNotes = statusNotes;
	}
	public Date getStatusDate() {
		return statusDate;
	}
	public void setStatusDate(Date statusDate) {
		this.statusDate = statusDate;
	}
	public int getTeacherAreaId() {
		return teacherAreaId;
	}
	public void setTeacherAreaId(int teacherAreaId) {
		this.teacherAreaId = teacherAreaId;
	}
	public int getPersonnelId() {
		return personnelId;
	}
	public void setPersonnelId(int personnelId) {
		this.personnelId = personnelId;
	}
}
