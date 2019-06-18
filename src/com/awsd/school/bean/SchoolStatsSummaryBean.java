package com.awsd.school.bean;

public class SchoolStatsSummaryBean {

	private int reportCount;
	private double totalTeachers;
	private double totalSupportStaff;
	private double totalStudents;

	public int getReportCount() {

		return reportCount;
	}

	public void setReportCount(int reportCount) {

		this.reportCount = reportCount;
	}

	public double getTotalTeachers() {

		return totalTeachers;
	}

	public void setTotalTeachers(double totalTeachers) {

		this.totalTeachers = totalTeachers;
	}

	public double getTotalSupportStaff() {

		return totalSupportStaff;
	}

	public void setTotalSupportStaff(double totalSupportStaff) {

		this.totalSupportStaff = totalSupportStaff;
	}

	public double getTotalStudents() {

		return totalStudents;
	}

	public void setTotalStudents(double totalStudents) {

		this.totalStudents = totalStudents;
	}

	public double getTotal() {

		return this.getTotalStudents() + this.getTotalSupportStaff() + this.getTotalTeachers();
	}
}
