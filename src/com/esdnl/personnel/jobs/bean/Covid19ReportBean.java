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
	private String rejectedBy;
	private Date rejectedDate;
	private String rejectedNotes;
	private boolean exemptionDoc=false;
	private Covid19SDSStatusBean stBean;
	private String ssText;//used for a report

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
			status="<span style='color:Silver;'><i class=\"fas fa-exclamation-circle\"></i> No Profile/Not Linked <i class=\"fas fa-exclamation-circle\"></i></span>";
		}else if(this.documentId <1) {
			status="<span style='color:Orange;'><i class=\"fas fa-times\"></i> No Document Uploaded</span>";
		}else if(this.exemptionDoc) {
			status="<span style='color:Green;'><i class=\"fas fa-check\"></i> Exemption Uploaded By " + this.verifiedBy + " on " + getDateVerifiedFormatted() +"</span>";
		}else if(this.verifiedDate == null && this.rejectedDate ==  null) {
			status="<span style='color:#6495ED;'><i class=\"fas fa-check\"></i> Document Uploaded</span> - <span style='color:Red;'><i class=\"fas fa-times\"></i> Not Verified</span>";
		}else if(this.rejectedDate != null && this.verifiedDate ==  null) {
			status="<span style='color:Red;'><i class=\"fas fa-ban\"></i> Rejected By " + this.rejectedBy + " on " + getDateRejectedFormatted();
			status= status + "<br />" + this.rejectedNotes + "</span>";
		}else if(this.stBean != null){
			status="<span style='color:Green;'><i class=\"fas fa-check\"></i> " + this.stBean.getStatusText() +"</span>";
		}else if(this.verifiedBy != null) {
			status="<span style='color:Green;'><i class=\"fas fa-check\"></i> Verified By " + this.verifiedBy + " on " + getDateVerifiedFormatted() +"</span>";
		}
		return status;
	}
	public int getStatus() {
		int status=0;
		if(this.employeeSin == null) {//no profile
			status=1;
		}else if(this.documentId <1) {//no document
			status=1;
		}else if(this.verifiedDate == null && this.rejectedDate ==  null) {//document not approved and not rejected
			status=2;
		}else if(this.rejectedDate != null && this.verifiedDate ==  null) {//document rejected
			status=4;
		}else if(this.verifiedBy != null) {//document approved
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
	public String getDateRejectedFormatted() {
		if(this.rejectedDate == null) {
			return "";
		}else {
			DateFormat dt = new SimpleDateFormat("dd/MM/yyyy"); 
			return dt.format(this.rejectedDate);
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
	public String getRejectedBy() {
		return rejectedBy;
	}
	public void setRejectedBy(String rejectedBy) {
		this.rejectedBy = rejectedBy;
	}
	public Date getRejectedDate() {
		return rejectedDate;
	}
	public void setRejectedDate(Date rejectedDate) {
		this.rejectedDate = rejectedDate;
	}
	public String getRejectedNotes() {
		return rejectedNotes;
	}
	public void setRejectedNotes(String rejectedNotes) {
		this.rejectedNotes = rejectedNotes;
	}
	public boolean isExemptionDoc() {
		return exemptionDoc;
	}
	public void setExemptionDoc(boolean exemptionDoc) {
		this.exemptionDoc = exemptionDoc;
	}
	public Covid19SDSStatusBean getStBean() {
		return stBean;
	}
	public void setStBean(Covid19SDSStatusBean stBean) {
		this.stBean = stBean;
	}
	public String getSsText() {
		return ssText;
	}
	public void setSsText(String ssText) {
		this.ssText = ssText;
	}
}
