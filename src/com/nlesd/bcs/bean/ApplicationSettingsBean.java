package com.nlesd.bcs.bean;

public class ApplicationSettingsBean {
	private int id;
	private boolean runWeeklyReport;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public boolean isRunWeeklyReport() {
		return runWeeklyReport;
	}
	public void setRunWeeklyReport(boolean runWeeklyReport) {
		this.runWeeklyReport = runWeeklyReport;
	}
}
