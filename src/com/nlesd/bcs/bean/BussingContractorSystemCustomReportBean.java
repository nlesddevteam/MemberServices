package com.nlesd.bcs.bean;

import java.io.Serializable;

public class BussingContractorSystemCustomReportBean implements Serializable {

	private static final long serialVersionUID = -844016122427000813L;
	private int id;
	private String reportUser;
	private String reportName;
	private String reportTables;
	private String reportTableFields;
	private String reportSql;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getReportName() {
		return reportName;
	}
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	public String getReportTables() {
		return reportTables;
	}
	public void setReportTables(String reportTables) {
		this.reportTables = reportTables;
	}
	public String getReportTableFields() {
		return reportTableFields;
	}
	public void setReportTableFields(String reportTableFields) {
		this.reportTableFields = reportTableFields;
	}
	public String getReportSql() {
		return reportSql;
	}
	public void setReportSql(String reportSql) {
		this.reportSql = reportSql;
	}
	public String getReportUser() {
		return reportUser;
	}
	public void setReportUser(String reportUser) {
		this.reportUser = reportUser;
	}

	
}
