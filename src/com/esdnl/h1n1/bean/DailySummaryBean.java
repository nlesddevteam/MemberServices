package com.esdnl.h1n1.bean;

import java.util.Date;

public class DailySummaryBean {

	private Date summaryDate;
	private double teacherSummary;
	private double supportStaffSummary;
	private double studentSummary;

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

	public double getTotal() {

		return this.getStudentSummary() + this.getSupportStaffSummary() + this.getTeacherSummary();
	}

}
