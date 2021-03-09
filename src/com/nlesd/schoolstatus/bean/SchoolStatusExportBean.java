package com.nlesd.schoolstatus.bean;

public class SchoolStatusExportBean {
	private String schoolNumber;
	private String schoolStatus;
	private String statusNotes;
	public String getSchoolNumber() {
		return schoolNumber;
	}
	public void setSchoolNumber(String schoolNumber) {
		this.schoolNumber = schoolNumber;
	}
	public String getSchoolStatus() {
		return schoolStatus;
	}
	public void setSchoolStatus(String schoolStatus) {
		this.schoolStatus = schoolStatus;
	}
	public String getStatusNotes() {
		return statusNotes;
	}
	public void setStatusNotes(String statusNotes) {
		this.statusNotes = statusNotes;
	}
	@Override
    public String toString() { 
        return "\"" + this.schoolNumber + "\",\"" + this.schoolStatus + "\",\"" + this.statusNotes + "\"\n";
    }
	public String toStringTab() { 
		return "\"" + this.schoolNumber + "\"\t\"" + this.schoolStatus + "\"\t\"" + this.statusNotes + "\"\n";
    }
	public static String headerString() { 
        return "SCHOOL_NUMBER" + "," + "STATUS" + "," + "NOTES" + "\n";
    }
	public static String headerStringTab() { 
        return "SCHOOL_NUMBER" + "\t" + "STATUS" + "\t" + "NOTES" + "\n";
    }
}
