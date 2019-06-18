package com.esdnl.webupdatesystem.meetinghighlights.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MeetingHighlightsFileBean implements Serializable 
{
	private static final long serialVersionUID = -8771605504141293333L;
	private Integer id;
	private String mhfTitle;
	private String mhfDoc;
	private String addedBy;
	private Date dateAdded;
	private Integer mhId;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getMHfTitle() {
		return mhfTitle;
	}
	public void setMHfTitle(String pfTitle) {
		this.mhfTitle = pfTitle;
	}
	public String getMHfDoc() {
		return mhfDoc;
	}
	public void setMHfDoc(String pfDoc) {
		this.mhfDoc = pfDoc;
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
	public Integer getMHId() {
		return mhId;
	}
	public void setMHId(Integer mhId) {
		this.mhId = mhId;
	}
}
