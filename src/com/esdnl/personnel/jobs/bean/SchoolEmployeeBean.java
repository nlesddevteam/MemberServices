package com.esdnl.personnel.jobs.bean;

public class SchoolEmployeeBean {
	private String schoolYear;
	private String locationId;
	private String firstName;
	private String lastName;
	private String currentAssignment;
	private String currentFTE;
	
	public String getFullName() {
		return this.firstName + " " + this.lastName;
	}

	public String getSchoolYear() {
		return schoolYear;
	}

	public void setSchoolYear(String schoolYear) {
		this.schoolYear = schoolYear;
	}

	public String getLocationId() {
		return locationId;
	}

	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getCurrentAssignment() {
		return currentAssignment;
	}

	public void setCurrentAssignment(String currentAssignment) {
		this.currentAssignment = currentAssignment;
	}

	public String getCurrentFTE() {
		return currentFTE;
	}

	public void setCurrentFTE(String currentFTE) {
		this.currentFTE = currentFTE;
	}
	public String toXmlPerm() {
		StringBuilder sb = new StringBuilder();
		sb.append("<PEMPLOYEE>");
		sb.append("<SYEAR>" + this.schoolYear + "</SYEAR>");
		sb.append("<LOCID>" + this.locationId + "</LOCID>");
		sb.append("<FULLNAME>" + this.getLastName() + ", "+ this.getFirstName() + "</FULLNAME>");
		sb.append("<CASS>" + this.currentAssignment + "</CASS>");
		sb.append("<CUNIT>" + this.currentFTE + "</CUNIT>");
		sb.append("</PEMPLOYEE>");
		return sb.toString();
		
	}
	public String toXmlVac() {
		StringBuilder sb = new StringBuilder();
		sb.append("<VEMPLOYEE>");
		sb.append("<SYEAR>" + this.schoolYear + "</SYEAR>");
		sb.append("<LOCID>" + this.locationId + "</LOCID>");
		sb.append("<FULLNAME>" + this.getLastName() + ", "+ this.getFirstName() + "</FULLNAME>");
		sb.append("<CASS>" + this.currentAssignment + "</CASS>");
		sb.append("<CUNIT>" + this.currentFTE + "</CUNIT>");
		sb.append("</VEMPLOYEE>");
		return sb.toString();
		
	}
}
