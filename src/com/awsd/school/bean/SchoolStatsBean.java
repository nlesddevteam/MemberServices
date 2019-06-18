package com.awsd.school.bean;

import com.awsd.school.School;

public class SchoolStatsBean {

	private School school;
	private double numberTeachers;
	private double numberSupportStaff;
	private double numberStudents;
	private double grade0Total;
	private double grade1Total;
	private double grade2Total;
	private double grade3Total;
	private double grade4Total;
	private double grade5Total;
	private double grade6Total;
	private double grade7Total;
	private double grade8Total;
	private double grade9Total;
	private double grade10Total;
	private double grade11Total;
	private double grade12Total;

	public School getSchool() {

		return school;
	}

	public void setSchool(School school) {

		this.school = school;
	}

	public double getNumberTeachers() {

		return numberTeachers;
	}

	public void setNumberTeachers(double numberTeachers) {

		this.numberTeachers = numberTeachers;
	}

	public double getNumberSupportStaff() {

		return numberSupportStaff;
	}

	public void setNumberSupportStaff(double numberSupportStaff) {

		this.numberSupportStaff = numberSupportStaff;
	}

	public double getNumberStudents() {

		return numberStudents;
	}

	public void setNumberStudents(double numberStudents) {

		this.numberStudents = numberStudents;
	}

	public double getTotal() {

		return this.getNumberStudents() + this.getNumberSupportStaff() + this.getNumberTeachers();
	}

	public double getGrade0Total() {

		return grade0Total;
	}

	public void setGrade0Total(double grade0Total) {

		this.grade0Total = grade0Total;
	}

	public double getGrade1Total() {

		return grade1Total;
	}

	public void setGrade1Total(double grade1Total) {

		this.grade1Total = grade1Total;
	}

	public double getGrade2Total() {

		return grade2Total;
	}

	public void setGrade2Total(double grade2Total) {

		this.grade2Total = grade2Total;
	}

	public double getGrade3Total() {

		return grade3Total;
	}

	public void setGrade3Total(double grade3Total) {

		this.grade3Total = grade3Total;
	}

	public double getGrade4Total() {

		return grade4Total;
	}

	public void setGrade4Total(double grade4Total) {

		this.grade4Total = grade4Total;
	}

	public double getGrade5Total() {

		return grade5Total;
	}

	public void setGrade5Total(double grade5Total) {

		this.grade5Total = grade5Total;
	}

	public double getGrade6Total() {

		return grade6Total;
	}

	public void setGrade6Total(double grade6Total) {

		this.grade6Total = grade6Total;
	}

	public double getGrade7Total() {

		return grade7Total;
	}

	public void setGrade7Total(double grade7Total) {

		this.grade7Total = grade7Total;
	}

	public double getGrade8Total() {

		return grade8Total;
	}

	public void setGrade8Total(double grade8Total) {

		this.grade8Total = grade8Total;
	}

	public double getGrade9Total() {

		return grade9Total;
	}

	public void setGrade9Total(double grade9Total) {

		this.grade9Total = grade9Total;
	}

	public double getGrade10Total() {

		return grade10Total;
	}

	public void setGrade10Total(double grade10Total) {

		this.grade10Total = grade10Total;
	}

	public double getGrade11Total() {

		return grade11Total;
	}

	public void setGrade11Total(double grade11Total) {

		this.grade11Total = grade11Total;
	}

	public double getGrade12Total() {

		return grade12Total;
	}

	public void setGrade12Total(double grade12Total) {

		this.grade12Total = grade12Total;
	}

	public double getGradeTotal() {

		return this.grade0Total + this.grade1Total + this.grade2Total + this.grade3Total + this.grade4Total
				+ this.grade5Total + this.grade6Total + this.grade7Total + this.grade8Total + this.grade9Total
				+ this.grade10Total + this.grade11Total + this.grade12Total;
	}

	public String toXML() {

		StringBuffer xml = new StringBuffer();

		xml.append("<SCHOOL-STATS>");
		xml.append(school.toXML());
		xml.append("<TEACHERS>" + this.getNumberTeachers() + "</TEACHERS>");
		xml.append("<SUPPORT-STAFF>" + this.getNumberSupportStaff() + "</SUPPORT-STAFF>");
		xml.append("<STUDENTS>" + this.getNumberStudents() + "</STUDENTS>");
		xml.append("<TOTAL>" + this.getTotal() + "</TOTAL>");
		xml.append("</SCHOOL-STATS>");

		return xml.toString();

	}

}
