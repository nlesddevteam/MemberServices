package com.esdnl.criticalissues.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.awsd.school.School;
import com.esdnl.criticalissues.constant.ReportTypeConstant;
import com.esdnl.servlet.FormElementFormat;

public class ReportBean {

	private int reportId;
	private Date reportDate;
	private School school;
	private ReportTypeConstant reportType;
	private Date dateAdded;
	private String filename;
	private int outstandingItems;

	public ReportBean() {

		this.reportId = 0;
		this.reportDate = null;
		this.school = null;
		this.reportType = null;
		this.dateAdded = null;
		this.outstandingItems = 0;
	}

	public ReportBean(int reportId, Date reportDate, School school, ReportTypeConstant reportType, Date dateAdded) {

		super();
		this.reportId = reportId;
		this.reportDate = reportDate;
		this.school = school;
		this.reportType = reportType;
		this.dateAdded = dateAdded;
		this.outstandingItems = 0;
	}

	public int getOutstandingItems() {

		return outstandingItems;
	}

	public void setOutstandingItems(int outstandingItems) {

		this.outstandingItems = outstandingItems;
	}

	public boolean equals(Object obj) {

		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final ReportBean other = (ReportBean) obj;
		if (dateAdded == null) {
			if (other.dateAdded != null)
				return false;
		}
		else if (!dateAdded.equals(other.dateAdded))
			return false;
		if (filename == null) {
			if (other.filename != null)
				return false;
		}
		else if (!filename.equals(other.filename))
			return false;
		if (reportDate == null) {
			if (other.reportDate != null)
				return false;
		}
		else if (!reportDate.equals(other.reportDate))
			return false;
		if (reportId != other.reportId)
			return false;
		if (reportType == null) {
			if (other.reportType != null)
				return false;
		}
		else if (!reportType.equals(other.reportType))
			return false;
		if (school == null) {
			if (other.school != null)
				return false;
		}
		else if (!school.equals(other.school))
			return false;
		return true;
	}

	public Date getDateAdded() {

		return dateAdded;
	}

	public String getFilename() {

		return filename;
	}

	public Date getReportDate() {

		return reportDate;
	}

	public String getReportDateFormatted() {

		return new SimpleDateFormat(FormElementFormat.DATE_FORMAT).format(this.getReportDate());
	}

	public int getReportId() {

		return reportId;
	}

	public ReportTypeConstant getReportType() {

		return reportType;
	}

	public School getSchool() {

		return school;
	}

	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + ((dateAdded == null) ? 0 : dateAdded.hashCode());
		result = prime * result + ((filename == null) ? 0 : filename.hashCode());
		result = prime * result + ((reportDate == null) ? 0 : reportDate.hashCode());
		result = prime * result + reportId;
		result = prime * result + ((reportType == null) ? 0 : reportType.hashCode());
		result = prime * result + ((school == null) ? 0 : school.hashCode());
		return result;
	}

	public void setDateAdded(Date dateAdded) {

		this.dateAdded = dateAdded;
	}

	public void setFilename(String filename) {

		this.filename = filename;
	}

	public void setReportDate(Date reportDate) {

		this.reportDate = reportDate;
	}

	public void setReportId(int reportId) {

		this.reportId = reportId;
	}

	public void setReportType(ReportTypeConstant reportType) {

		this.reportType = reportType;
	}

	public void setSchool(School school) {

		this.school = school;
	}

}
