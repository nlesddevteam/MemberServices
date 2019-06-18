package com.esdnl.school.registration.kindergarten.bean;

import com.awsd.school.School;

public class SchoolRegistrantsSummaryBean {

	private String schoolYear;
	private School school;
	private int totalRegistrants;
	private int englishRegistrants;
	private int frenchRegistrants;

	public SchoolRegistrantsSummaryBean() {

		super();
	}

	public String getSchoolYear() {

		return schoolYear;
	}

	public void setSchoolYear(String schoolYear) {

		this.schoolYear = schoolYear;
	}

	public School getSchool() {

		return school;
	}

	public void setSchool(School school) {

		this.school = school;
	}

	public int getTotalRegistrants() {

		return totalRegistrants;
	}

	public void setTotalRegistrants(int totalRegistrants) {

		this.totalRegistrants = totalRegistrants;
	}

	public int getEnglishRegistrants() {

		return englishRegistrants;
	}

	public void setEnglishRegistrants(int englishRegistrants) {

		this.englishRegistrants = englishRegistrants;
	}

	public int getFrenchRegistrants() {

		return frenchRegistrants;
	}

	public void setFrenchRegistrants(int frenchRegistrants) {

		this.frenchRegistrants = frenchRegistrants;
	}

}
