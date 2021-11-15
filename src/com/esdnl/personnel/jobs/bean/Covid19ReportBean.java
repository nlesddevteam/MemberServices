package com.esdnl.personnel.jobs.bean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Covid19ReportBean {
	private String employeeName;
	private String employeeEmail;
	private String employeeLocation;
	private String employeeSin;
	private int documentId;
	private String fileName;
	private String documentType;
	private Date createdDate;
	private Date verifiedDate;
	private String verifiedBy;
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getEmployeeEmail() {
		return employeeEmail;
	}
	public void setEmployeeEmail(String employeeEmail) {
		this.employeeEmail = employeeEmail;
	}
	public String getEmployeeLocation() {
		if(this.employeeLocation == null) {
			return "";
		}else {
			return employeeLocation;
		}
	}
	public void setEmployeeLocation(String employeeLocation) {
		this.employeeLocation = employeeLocation;
	}
	public String getEmployeeSin() {
		return employeeSin;
	}
	public void setEmployeeSin(String employeeSin) {
		this.employeeSin = employeeSin;
	}
	public int getDocumentId() {
		return documentId;
	}
	public void setDocumentId(int documentId) {
		this.documentId = documentId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getDocumentType() {
		return documentType;
	}
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getVerifiedDate() {
		return verifiedDate;
	}
	public void setVerifiedDate(Date verifiedDate) {
		this.verifiedDate = verifiedDate;
	}
	public String getVerifiedBy() {
		return verifiedBy;
	}
	public void setVerifiedBy(String verifiedBy) {
		this.verifiedBy = verifiedBy;
	}
	public String getStatusString() {
		String status="";
		if(this.employeeSin == null) {
			status="<span style='color:Red;'><i class=\"fas fa-times\"></i> No Profile/Not Linked</span>";
		}else if(this.documentId <1) {
			status="<span style='color:Red;'><i class=\"fas fa-times\"></i> No COVID19 Document Uploaded</span>";
		}else if(this.verifiedDate == null) {
			status="<span style='color:Green;'><i class=\"fas fa-check\"></i> Vaccination Proof Document Uploaded</span> - <span style='color:Red;'><i class=\"fas fa-times\"></i> Not Verified</span>";
		}else if(this.verifiedBy != null) {
			status="<span style='color:Green;'><i class=\"fas fa-check\"></i> Verified By " + this.verifiedBy + " on " + getDateVerifiedFormatted() +"</span>";
		}
		return status;
	}
	public int getStatus() {
		int status=0;
		if(this.employeeSin == null) {
			status=1;
		}else if(this.documentId <1) {
			status=1;
		}else if(this.verifiedDate == null) {
			status=2;
		}else if(this.verifiedBy != null) {
			status=3;
		}
		return status;
	}
	public String getDateVerifiedFormatted() {
		if(this.verifiedDate == null) {
			return "";
		}else {
			DateFormat dt = new SimpleDateFormat("dd/MM/yyyy"); 
			return dt.format(this.verifiedDate);
		}
	}
	public String getCreatedDateFormatted() {
		if(this.createdDate == null) {
			return "";
		}else {
			DateFormat dt = new SimpleDateFormat("dd/MM/yyyy"); 
			return dt.format(this.createdDate);
		}
	}
	public String toXml() {
		StringBuilder sb = new StringBuilder();
		sb.append("<PEMPLOYEE>");
		sb.append("<NAME>" + this.employeeName + "</NAME>");
		sb.append("<SIN>" + this.employeeSin + "</SIN>");
		sb.append("<STATUS>" + this.getStatusString() + "</STATUS>");
		sb.append("<CDATE>" + this.getCreatedDateFormatted() + "</CDATE>");
		sb.append("<FTYPE>" + this.getDocumentType() + "</FTYPE>");
		sb.append("<FILENAME>" + this.getDocumentType() + "</FILENAME>");
		sb.append("<DOCUMENTID>" + this.getDocumentId() + "</DOCUMENTID>");
		sb.append("<LOCATION>" + this.getEmployeeLocation()+ "</LOCATION>");
		sb.append("<STATUSCODE>" + this.getStatus() + "</STATUSCODE>");
		sb.append("</PEMPLOYEE>");
		return sb.toString();
		
	}
}