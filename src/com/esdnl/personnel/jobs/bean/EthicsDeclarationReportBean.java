package com.esdnl.personnel.jobs.bean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EthicsDeclarationReportBean {
	private String employeeName;
	private String employeeLocation;
	private Date createdDate;
	private int documentId;
	private String employeeSin;
	private String employeeEmail;
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getEmployeeLocation() {
		return employeeLocation;
	}
	public void setEmployeeLocation(String employeeLocation) {
		this.employeeLocation = employeeLocation;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public int getDocumentId() {
		return documentId;
	}
	public void setDocumentId(int documentId) {
		this.documentId = documentId;
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
		sb.append("<CDATE>" + this.getCreatedDateFormatted() + "</CDATE>");
		sb.append("<DOCUMENTID>" + this.getDocumentId() + "</DOCUMENTID>");
		sb.append("<LOCATION>" + this.getEmployeeLocation()+ "</LOCATION>");
		sb.append("<SIN>" + this.getEmployeeSin()+ "</SIN>");
		sb.append("<APPEMAIL>" + this.getEmployeeEmail()+ "</APPEMAIL>");
		sb.append("</PEMPLOYEE>");
		return sb.toString();
		
	}
	public String getEmployeeSin() {
		return employeeSin;
	}
	public void setEmployeeSin(String employeeSin) {
		this.employeeSin = employeeSin;
	}
	public String getEmployeeEmail() {
		return employeeEmail;
	}
	public void setEmployeeEmail(String employeeEmail) {
		this.employeeEmail = employeeEmail;
	}
}
