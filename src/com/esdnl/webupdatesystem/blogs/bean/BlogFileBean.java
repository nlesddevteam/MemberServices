package com.esdnl.webupdatesystem.blogs.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BlogFileBean implements Serializable 
{
	private static final long serialVersionUID = -8771605504141293333L;
	private Integer id;
	private String bfTitle;
	private String bfDoc;
	private String addedBy;
	private Date dateAdded;
	private Integer blogId;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getBfTitle() {
		return bfTitle;
	}
	public void setBfTitle(String pfTitle) {
		this.bfTitle = pfTitle;
	}
	public String getBfDoc() {
		return bfDoc;
	}
	public void setBfDoc(String pfDoc) {
		this.bfDoc = pfDoc;
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
	public Integer getBlogId() {
		return blogId;
	}
	public void setBlogId(Integer blogId) {
		this.blogId = blogId;
	}
}
