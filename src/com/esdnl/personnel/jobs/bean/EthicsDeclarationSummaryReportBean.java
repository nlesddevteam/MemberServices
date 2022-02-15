package com.esdnl.personnel.jobs.bean;

public class EthicsDeclarationSummaryReportBean {
	private int totalUploaded;
	private int totalNLESD;
	private int totalLatest;
	private int testCount;
	public int getTotalUploaded() {
		return totalUploaded;
	}
	public void setTotalUploaded(int totalUploaded) {
		this.totalUploaded = totalUploaded;
	}
	public int getTotalNLESD() {
		return totalNLESD;
	}
	public void setTotalNLESD(int totalNLESD) {
		this.totalNLESD = totalNLESD;
	}
	public int getTotalLatest() {
		return totalLatest;
	}
	public void setTotalLatest(int totalLatest) {
		this.totalLatest = totalLatest;
	}
	public int getTotalNonNLESD() {
		return this.totalUploaded-this.totalNLESD;
	}
	public int getTestCount() {
		return testCount;
	}
	public void setTestCount(int testCount) {
		this.testCount = testCount;
	}
}
