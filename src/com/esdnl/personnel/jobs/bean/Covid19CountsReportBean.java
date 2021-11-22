package com.esdnl.personnel.jobs.bean;

public class Covid19CountsReportBean {
	private String location;
	private int locationCount;
	private int documentCount;
	private int notVerifiedCount;
	private int verifiedCount;
	private int rejectCount;
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public int getLocationCount() {
		return locationCount;
	}
	public void setLocationCount(int locationCount) {
		this.locationCount = locationCount;
	}
	public int getDocumentCount() {
		return documentCount;
	}
	public void setDocumentCount(int documentCount) {
		this.documentCount = documentCount;
	}
	public int getNotVerifiedCount() {
		return notVerifiedCount;
	}
	public void setNotVerifiedCount(int notVerifiedCount) {
		this.notVerifiedCount = notVerifiedCount;
	}
	public int getVerifiedCount() {
		return verifiedCount;
	}
	public void setVerifiedCount(int verifiedCount) {
		this.verifiedCount = verifiedCount;
	}
	public int getRejectCount() {
		return rejectCount;
	}
	public void setRejectCount(int rejectCount) {
		this.rejectCount = rejectCount;
	}
}
