package com.esdnl.scrs.domain;

import java.util.Date;

import com.awsd.school.School;

public class BullyingNoIncidentReportedBean {

	private int reportId;
	private School school;
	private Date reportingDate;

	public BullyingNoIncidentReportedBean() {

		super();

		reportId = 0;
		reportingDate = null;
		school = null;
	}

	public int getReportId() {

		return reportId;
	}

	public void setReportId(int reportId) {

		this.reportId = reportId;
	}

	public School getSchool() {

		return school;
	}

	public void setSchool(School school) {

		this.school = school;
	}

	public Date getReportingDate() {

		return reportingDate;
	}

	public void setReportingDate(Date reportingDate) {

		this.reportingDate = reportingDate;
	}

}
