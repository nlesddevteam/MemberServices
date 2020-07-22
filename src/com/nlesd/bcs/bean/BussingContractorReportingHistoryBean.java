package com.nlesd.bcs.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BussingContractorReportingHistoryBean {
	private int rhId;
	private String reportTitle;
	private Date lastRunDate;
	private String ranBy;
	public int getRhId() {
		return rhId;
	}
	public void setRhId(int rhId) {
		this.rhId = rhId;
	}
	public String getReportTitle() {
		return reportTitle;
	}
	public void setReportTitle(String reportTitle) {
		this.reportTitle = reportTitle;
	}
	public Date getLastRunDate() {
		return lastRunDate;
	}
	public void setLastRunDate(Date lastRunDate) {
		this.lastRunDate = lastRunDate;
	}
	public String getRanBy() {
		return ranBy;
	}
	public void setRanBy(String ranBy) {
		this.ranBy = ranBy;
	}
	public String getLastRunDateFormatted()
	{
		return new SimpleDateFormat("MM/dd/yyyy hh:mm:ss").format(this.lastRunDate);
	}
}
