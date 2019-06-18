package com.nlesd.bcs.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BussingContractorSystemDocumentBean implements Serializable {

	private static final long serialVersionUID = -844016122427000813L;
	private int id;
	private int documentType;
	private String documentTitle;
	private String documentPath;
	private String isDeleted;
	private Date dateUploaded;
	private String uploadedBy;
	private String vInternal;
	private String vExternal;
	private String showMessage;
	private int messageDays;
	private String isActive;
	private String typeString;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getUploadedBy() {
		return uploadedBy;
	}
	public void setUploadedBy(String uploadedBy) {
		this.uploadedBy = uploadedBy;
	}
	public String getvInternal() {
		return vInternal;
	}
	public void setvInternal(String vInternal) {
		this.vInternal = vInternal;
	}
	public String getvExternal() {
		return vExternal;
	}
	public void setvExternal(String vExternal) {
		this.vExternal = vExternal;
	}
	public String getShowMessage() {
		return showMessage;
	}
	public void setShowMessage(String showMessage) {
		this.showMessage = showMessage;
	}
	public int getMessageDays() {
		return messageDays;
	}
	public void setMessageDays(int messageDays) {
		this.messageDays = messageDays;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public String getTypeString() {
		return typeString;
	}
	public void setTypeString(String typeString) {
		this.typeString = typeString;
	}
	public String getDateUploadedFormatted() {
		if(this.dateUploaded != null){
			return new SimpleDateFormat("MM/dd/yyyy").format(this.dateUploaded);
		}else{
			return "";
		}
	}
	public String getViewPath(){
		
		return "/BCS/documents/system/" + this.documentPath;
	}
}
