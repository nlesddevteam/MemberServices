package com.nlesd.school.bean;

import com.awsd.school.School;

public class SchoolSpecialReportBean {

	private int reportId;
	private School school;
	private String title;
	private String reportFilename;
	private boolean archived;
	private boolean deleted;

	public SchoolSpecialReportBean() {

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

	public String getTitle() {

		return title;
	}

	public void setTitle(String title) {

		this.title = title;
	}

	public String getReportFilename() {

		return reportFilename;
	}

	public void setReportFilename(String reportFilename) {

		this.reportFilename = reportFilename;
	}

	public boolean isArchived() {

		return archived;
	}

	public void setArchived(boolean archived) {

		this.archived = archived;
	}

	public boolean isDeleted() {

		return deleted;
	}

	public void setDeleted(boolean deleted) {

		this.deleted = deleted;
	}

}
