package com.esdnl.scrs.domain;

import com.awsd.school.School;

public class SchoolBullyingIncidentCount {

	private School school;
	private int overallIncidentCount;
	private int weeklyIncidentCount;
	private int monthlyIncidentCount;
	private boolean noIncidentsReported;

	public SchoolBullyingIncidentCount() {

		super();
		this.school = null;
		this.overallIncidentCount = 0;
		this.weeklyIncidentCount = 0;
		this.monthlyIncidentCount = 0;
	}

	public School getSchool() {

		return school;
	}

	public void setSchool(School school) {

		this.school = school;
	}

	public int getOverallIncidentCount() {

		return overallIncidentCount;
	}

	public void setOverallIncidentCount(int incidentCount) {

		this.overallIncidentCount = incidentCount;
	}

	public int getWeeklyIncidentCount() {

		return weeklyIncidentCount;
	}

	public void setWeeklyIncidentCount(int weeklyIncidentCount) {

		this.weeklyIncidentCount = weeklyIncidentCount;
	}

	public int getMonthlyIncidentCount() {

		return monthlyIncidentCount;
	}

	public void setMonthlyIncidentCount(int monthlyIncidentCount) {

		this.monthlyIncidentCount = monthlyIncidentCount;
	}

	public boolean isNoIncidentsReported() {

		return noIncidentsReported;
	}

	public void setNoIncidentsReported(boolean noIncidentsReported) {

		this.noIncidentsReported = noIncidentsReported;
	}

}
