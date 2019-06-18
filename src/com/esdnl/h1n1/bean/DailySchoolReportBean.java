package com.esdnl.h1n1.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.awsd.personnel.Personnel;
import com.awsd.personnel.PersonnelDB;
import com.awsd.personnel.PersonnelException;
import com.awsd.school.School;
import com.awsd.school.SchoolDB;
import com.awsd.school.SchoolException;
import com.esdnl.util.StringUtils;

public class DailySchoolReportBean {

	private final double ALERT_THRESHOLD = 10;

	private int reportId;
	private Date dateAdded;
	private int reportedById;
	private Personnel reportedBy;
	private int schoolId;
	private School school;
	private double teacherTotal;
	private double teacherAverage;
	private double supportStaffTotal;
	private double supportStaffAverage;
	private double studentTotal;
	private double studentAverage;
	private double total;
	private double overallAverage;
	private String additionalComments;

	public int getReportId() {

		return reportId;
	}

	public void setReportId(int reportId) {

		this.reportId = reportId;
	}

	public Date getDateAdded() {

		return dateAdded;
	}

	public void setDateAdded(Date dateAdded) {

		this.dateAdded = dateAdded;
	}

	public School getSchool() {

		if (this.school == null) {
			try {
				this.school = SchoolDB.getSchool(getSchoolId());
			}
			catch (SchoolException e) {
				e.printStackTrace();
				this.school = null;
			}
		}

		return school;
	}

	public Personnel getReportedBy() {

		if (this.reportedBy == null) {
			try {
				this.reportedBy = PersonnelDB.getPersonnel(this.getReportedById());
			}
			catch (PersonnelException e) {
				e.printStackTrace();
				this.reportedBy = null;
			}
		}

		return reportedBy;
	}

	public double getTeacherTotal() {

		return teacherTotal;
	}

	public void setTeacherTotal(double teacherTotal) {

		this.teacherTotal = teacherTotal;
	}

	public double getSupportStaffTotal() {

		return supportStaffTotal;
	}

	public void setSupportStaffTotal(double supportStaffTotal) {

		this.supportStaffTotal = supportStaffTotal;
	}

	public double getStudentTotal() {

		return studentTotal;
	}

	public void setStudentTotal(double studentTotal) {

		this.studentTotal = studentTotal;
	}

	public String getAdditionalComments() {

		return additionalComments;
	}

	public void setAdditionalComments(String additionalComments) {

		this.additionalComments = additionalComments;
	}

	public int getReportedById() {

		return reportedById;
	}

	public void setReportedById(int reportedById) {

		this.reportedById = reportedById;
	}

	public int getSchoolId() {

		return schoolId;
	}

	public void setSchoolId(int schoolId) {

		this.schoolId = schoolId;
	}

	public double getTeacherAverage() {

		return teacherAverage;
	}

	public boolean isTeacherAlert() {

		return (getTeacherAverage() > ALERT_THRESHOLD);
	}

	public void setTeacherAverage(double teacherAverage) {

		this.teacherAverage = teacherAverage;
	}

	public double getSupportStaffAverage() {

		return supportStaffAverage;
	}

	public boolean isSupportStaffAlert() {

		return (getSupportStaffAverage() > ALERT_THRESHOLD);
	}

	public void setSupportStaffAverage(double suportStaffAverage) {

		this.supportStaffAverage = suportStaffAverage;
	}

	public double getStudentAverage() {

		return studentAverage;
	}

	public boolean isStudentAlert() {

		return (getStudentAverage() > ALERT_THRESHOLD);
	}

	public void setStudentAverage(double studentAverage) {

		this.studentAverage = studentAverage;
	}

	public double getTotal() {

		return total;
	}

	public void setTotal(double total) {

		this.total = total;
	}

	public double getOverallAverage() {

		return overallAverage;
	}

	public void setOverallAverage(double overallAverage) {

		this.overallAverage = overallAverage;
	}

	public void setReportedBy(Personnel reportedBy) {

		this.reportedBy = reportedBy;
	}

	public void setSchool(School school) {

		this.school = school;
	}

	public String toXML() {

		StringBuffer xml = new StringBuffer();

		xml.append("<DAILY-REPORT-BEAN>");
		xml.append("<ID>" + this.reportId + "</ID>");
		if (this.getDateAdded() != null)
			xml.append("<DATE-ADDED>" + new SimpleDateFormat("dd/MM/yyyy").format(this.getDateAdded()) + "</DATE-ADDED>");
		xml.append("<REPORTED-BY>" + this.getReportedBy().toXML() + "</REPORTED-BY>");
		xml.append(this.getSchool().toXML());
		xml.append("<TEACHER-TOTAL>" + this.getTeacherTotal() + "</TEACHER-TOTAL>");
		xml.append("<SUPPORT-STAFF-TOTAL>" + this.getSupportStaffTotal() + "</SUPPORT-STAFF-TOTAL>");
		xml.append("<STUDENT-TOTAL>" + this.getStudentTotal() + "</STUDENT-TOTAL>");
		if (!StringUtils.isEmpty(this.getAdditionalComments()))
			xml.append("<ADDITIONAL-COMMENTS>" + this.getAdditionalComments() + "</ADDITIONAL-COMMENTS>");
		xml.append("</DAILY-REPORT-BEAN>");

		return xml.toString();

	}

}
