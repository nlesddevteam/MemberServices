package com.esdnl.h1n1.bean;

import java.util.Date;

import com.awsd.school.School;

public class MonthlySchoolSummaryBean {

	private School school;
	private Date summaryDate;
	private int recordCount;
	private double teacherSummary;
	private double teacherAverage;
	private double supportStaffSummary;
	private double supportStaffAverage;
	private double studentSummary;
	private double studentAverage;
	private double totalAverage;

	public Date getSummaryDate() {

		return summaryDate;
	}

	public void setSummaryDate(Date summaryDate) {

		this.summaryDate = summaryDate;
	}

	public double getTeacherSummary() {

		return teacherSummary;
	}

	public void setTeacherSummary(double teacherSummary) {

		this.teacherSummary = teacherSummary;
	}

	public double getSupportStaffSummary() {

		return supportStaffSummary;
	}

	public void setSupportStaffSummary(double supportStaffSummary) {

		this.supportStaffSummary = supportStaffSummary;
	}

	public double getStudentSummary() {

		return studentSummary;
	}

	public void setStudentSummary(double studentSummary) {

		this.studentSummary = studentSummary;
	}

	public int getRecordCount() {

		return recordCount;
	}

	public void setRecordCount(int recordCount) {

		this.recordCount = recordCount;
	}

	public double getTeacherAverage() {

		return teacherAverage;
	}

	public void setTeacherAverage(double teacherAverage) {

		this.teacherAverage = teacherAverage;
	}

	public double getSupportStaffAverage() {

		return supportStaffAverage;
	}

	public void setSupportStaffAverage(double supportStaffAverage) {

		this.supportStaffAverage = supportStaffAverage;
	}

	public double getStudentAverage() {

		return studentAverage;
	}

	public void setStudentAverage(double studentAverage) {

		this.studentAverage = studentAverage;
	}

	public double getTotal() {

		return this.getStudentSummary() + this.getSupportStaffSummary() + this.getTeacherSummary();
	}

	public double getTotalAverage() {

		return totalAverage;
	}

	public void setTotalAverage(double totalAverage) {

		this.totalAverage = totalAverage;
	}

	public School getSchool() {

		return school;
	}

	public void setSchool(School school) {

		this.school = school;
	}

}
