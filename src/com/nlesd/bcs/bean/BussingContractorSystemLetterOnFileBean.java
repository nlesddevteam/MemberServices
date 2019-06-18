package com.nlesd.bcs.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BussingContractorSystemLetterOnFileBean implements Serializable {

	private static final long serialVersionUID = -844016122427000813L;
	private int id;
	private String lDocument;
	private String lName;
	private String notes;
	private int fkType;
	private String isDeleted;
	private String lType;
	private String addedBy;
	private Date dateAdded;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getlDocument() {
		return lDocument;
	}
	public void setlDocument(String lDocument) {
		this.lDocument = lDocument;
	}
	public String getlName() {
		return lName;
	}
	public void setlName(String lName) {
		this.lName = lName;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public int getFkType() {
		return fkType;
	}
	public void setFkType(int fkType) {
		this.fkType = fkType;
	}
	public String getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}
	public String getlType() {
		return lType;
	}
	public void setlType(String lType) {
		this.lType = lType;
	}
	public String getAddedBy() {
		return addedBy;
	}
	public void setAddedBy(String addedBy) {
		this.addedBy = addedBy;
	}
	public Date getDateAdded() {
		return dateAdded;
	}
	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}
	public String getDateAddedFormatted() {
		if(this.dateAdded != null){
			return new SimpleDateFormat("MM/dd/yyyy").format(this.dateAdded);
		}else{
			return "";
		}
	}
	public String getViewPath(){
		String url="";
		if(this.lType == "E"){
			url="/BCS/documents/employeeletters/" + this.getlDocument();
		}else{
			url="/BCS/documents/contractorletters/" + this.getlDocument();
		}
		return url;
	}	
}
