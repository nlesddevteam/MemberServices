package com.nlesd.bcs.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.nlesd.bcs.dao.DropdownManager;

public class BussingContractorSystemEmployeeTrainingBean implements Serializable {

	private static final long serialVersionUID = -844016122427000813L;
	private int pk;
	private int trainingType;
	private Date trainingDate;
	private Date expiryDate;
	private String notes;
	private int fkEmployee;
	private String isDeleted;
	private String tDocument;
	private int trainingLength;
	private String providedBy;
	private String location;
	public int getPk() {
		return pk;
	}
	public void setPk(int pk) {
		this.pk = pk;
	}
	public int getTrainingType() {
		return trainingType;
	}
	public void setTrainingType(int trainingType) {
		this.trainingType = trainingType;
	}
	public Date getTrainingDate() {
		return trainingDate;
	}
	public void setTrainingDate(Date trainingDate) {
		this.trainingDate = trainingDate;
	}
	public Date getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public int getFkEmployee() {
		return fkEmployee;
	}
	public void setFkEmployee(int fkEmployee) {
		this.fkEmployee = fkEmployee;
	}
	public String getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}
	public String gettDocument() {
		return tDocument;
	}
	public void settDocument(String tDocument) {
		this.tDocument = tDocument;
	}
	public String getExpiryDateFormatted() {
		if(this.expiryDate != null){
			return new SimpleDateFormat("MM/dd/yyyy").format(this.expiryDate);
		}else{
			return "";
		}
	}
	public String getTrainingDateFormatted() {
		if(this.trainingDate != null){
			return new SimpleDateFormat("MM/dd/yyyy").format(this.trainingDate);
		}else{
			return "";
		}
	}
	public String getViewPath(){
		
		return "/BCS/documents/employeedocs/" + this.gettDocument();
	}
	public String getTrainingTypeString() {
		return DropdownManager.getDropdownItemText(this.trainingType);
	}
	public int getTrainingLength() {
		return trainingLength;
	}
	public void setTrainingLength(int trainingLength) {
		this.trainingLength = trainingLength;
	}
	public String getProvidedBy() {
		return providedBy;
	}
	public void setProvidedBy(String providedBy) {
		this.providedBy = providedBy;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
}
