package com.nlesd.bcs.bean;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class BussingContractorDocumentBean implements Serializable {

	private static final long serialVersionUID = -844016122427000813L;
	private int id;
	private int contractorId;
	private int documentType;
	private String documentTitle;
	private String documentPath;
	private String isDeleted;
	private Date dateUploaded;
	private Date expiryDate;
	private String typeString;
	private String warningNotes;//used with main screen warnings for contractors and automated ones
	private String companyName;//used with main screen warnings for contractors and automated ones
	private String companyEmail;//used with main screen warnings for contractors and automated ones
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getContractorId() {
		return contractorId;
	}
	public void setContractorId(int contractorId) {
		this.contractorId = contractorId;
	}
	public int getDocumentType() {
		return documentType;
	}
	public void setDocumentType(int documentType) {
		this.documentType = documentType;
	}
	public String getDocumentTitle() {
		return documentTitle;
	}
	public void setDocumentTitle(String documentTitle) {
		this.documentTitle = documentTitle;
	}
	public String getDocumentPath() {
		return documentPath;
	}
	public void setDocumentPath(String documentPath) {
		this.documentPath = documentPath;
	}
	public String getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}
	public Date getDateUploaded() {
		return dateUploaded;
	}
	public void setDateUploaded(Date dateUploaded) {
		this.dateUploaded = dateUploaded;
	}
	public String getDateUploadedFormatted() {
		if(this.dateUploaded != null){
			return new SimpleDateFormat("MM/dd/yyyy").format(this.dateUploaded);
		}else{
			return "";
		}
	}
	public String getTypeString() {
		return typeString;
	}
	public void setTypeString(String typeString) {
		this.typeString = typeString;
	}
	public String getViewPath(){
		
		return "/BCS/documents/contractordocs/" + this.documentPath;
	}
	public Date getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
	public String getExpiryDateFormatted() {
		if(this.expiryDate != null){
			return new SimpleDateFormat("MM/dd/yyyy").format(this.expiryDate);
		}else{
			return "";
		}
	}
	public Date getDocExpiryDate(int numofdays) {
		Date rdate = null;
		if(this.expiryDate == null) {
			rdate=null;
		}else {
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		    Calendar c = Calendar.getInstance();
		    try {
				c.setTime(sdf.parse(this.getExpiryDateFormatted()));
				c.add(Calendar.DATE, numofdays);
				rdate =  c.getTime();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    
		}
		return rdate;
		
	}
	public String getWarningNotes() {
		return warningNotes;
	}
	public void setWarningNotes(String warningNotes) {
		this.warningNotes = warningNotes;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCompanyEmail() {
		return companyEmail;
	}
	public void setCompanyEmail(String companyEmail) {
		this.companyEmail = companyEmail;
	}
}