package com.esdnl.webupdatesystem.newspostings.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NewsPostingFileBean implements Serializable 
{
	private static final long serialVersionUID = -8771605504141293333L;
	private Integer id;
	private String nfTitle;
	private String nfDoc;
	private String addedBy;
	private Date dateAdded;
	private Integer newId;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNfTitle() {
		return nfTitle;
	}
	public void setNfTitle(String pfTitle) {
		this.nfTitle = pfTitle;
	}
	public String getNfDoc() {
		return nfDoc;
	}
	public void setNfDoc(String pfDoc) {
		this.nfDoc = pfDoc;
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
	public Integer getNewId() {
		return newId;
	}
	public void setNewId(Integer newId) {
		this.newId = newId;
	}
}
