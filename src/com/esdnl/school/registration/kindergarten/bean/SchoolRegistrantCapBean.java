package com.esdnl.school.registration.kindergarten.bean;

import com.awsd.school.School;

public class SchoolRegistrantCapBean {

	private int capId;
	private String schoolYear;
	private School school;
	private int englishCap;
	private int frenchCap;
	private SchoolRegistrantsSummaryBean summary;

	public SchoolRegistrantCapBean() {

		super();
	}

	public int getCapId() {

		return capId;
	}

	public void setCapId(int capId) {

		this.capId = capId;
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

	public int getEnglishCap() {

		return englishCap;
	}

	public void setEnglishCap(int englishCap) {

		this.englishCap = englishCap;
	}

	public int getFrenchCap() {

		return frenchCap;
	}

	public void setFrenchCap(int frenchCap) {

		this.frenchCap = frenchCap;
	}

	public SchoolRegistrantsSummaryBean getSummary() {

		return summary;
	}

	public void setSummary(SchoolRegistrantsSummaryBean summary) {

		this.summary = summary;
	}

}
