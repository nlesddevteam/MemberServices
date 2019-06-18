package com.esdnl.webupdatesystem.tenders.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TendersFileBean implements Serializable 
{
	private static final long serialVersionUID = -8771605504141293333L;
	private Integer id;
	private String tfTitle;
	private String tfDoc;
	private String addedBy;
	private Date dateAdded;
	private Integer tenderId;
	private Date addendumDate;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTfTitle() {
		return tfTitle;
	}
	public void setTfTitle(String tfTitle) {
		this.tfTitle = tfTitle;
	}
	public String getTfDoc() {
		return tfDoc;
	}
	public void setTfDoc(String tfDoc) {
		this.tfDoc = tfDoc;
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
	public Integer getTenderId() {
		return tenderId;
	}
	public void setTenderId(Integer tenderId) {
		this.tenderId = tenderId;
	}
	public String getDateAddedFormatted() {

		return new SimpleDateFormat("dd/MM/yyyy").format(this.dateAdded);
	}
	public Date getAddendumDate() {
		return addendumDate;
	}
	public void setAddendumDate(Date addendumDate) {
		this.addendumDate = addendumDate;
	}
	public String getAddendumDateFormatted() {

		return new SimpleDateFormat("dd/MM/yyyy").format(this.addendumDate);
	}
}
