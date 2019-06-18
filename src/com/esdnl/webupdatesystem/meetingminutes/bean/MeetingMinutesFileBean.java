package com.esdnl.webupdatesystem.meetingminutes.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MeetingMinutesFileBean implements Serializable 
{
	private static final long serialVersionUID = -8771605504141293333L;
	private Integer id;
	private String mmfTitle;
	private String mmfDoc;
	private String addedBy;
	private Date dateAdded;
	private Integer mmId;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getMMfTitle() {
		return mmfTitle;
	}
	public void setMMfTitle(String pfTitle) {
		this.mmfTitle = pfTitle;
	}
	public String getMMfDoc() {
		return mmfDoc;
	}
	public void setMMfDoc(String pfDoc) {
		this.mmfDoc = pfDoc;
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

		return new SimpleDateFormat("dd/MM/yyyy").format(this.dateAdded);
	}
	public Integer getMMId() {
		return mmId;
	}
	public void setMMId(Integer mmId) {
		this.mmId = mmId;
	}
}
