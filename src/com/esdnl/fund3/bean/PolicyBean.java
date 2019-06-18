package com.esdnl.fund3.bean;

import java.io.Serializable;
import java.util.Date;

public class PolicyBean implements Serializable {
	private static final long serialVersionUID = -8771605504141293333L;
	public static final String DOCUMENT_BASEPATH = "FUND3/uploads/docs/";
	private Integer id;
	private String linkText;
	private String fileName;
	private Integer isActive;
	private String addedBy;
	private Date dateAdded;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getLinkText() {
		return linkText;
	}
	public void setLinkText(String linkText) {
		this.linkText = linkText;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public Integer getIsActive() {
		return isActive;
	}
	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
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
	public String getFileLink()
	{
		return  "uploads/docs/" +  this.fileName;
	}
	public String getIsActiveString()
	{
		if(isActive == 1)
		{
			return "Active";
		}else{
			return "InActive";
		}
	}	
	

}
