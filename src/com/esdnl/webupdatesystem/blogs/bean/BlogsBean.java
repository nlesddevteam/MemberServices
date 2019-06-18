package com.esdnl.webupdatesystem.blogs.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import com.esdnl.webupdatesystem.blogs.constants.BlogStatus;

public class BlogsBean implements Serializable 
{
	private static final long serialVersionUID = -8771605504141293333L;
	private Integer id;
	private String blogTitle;
	private Date blogDate;
	private String blogContent;
	private String blogPhoto;
	private String blogDocument;
	private BlogStatus blogStatus;
	private String addedBy;
	private Date dateAdded;
	private ArrayList<BlogFileBean> otherBlogFiles;
	private String blogPhotoCaption;
	public BlogsBean()
	{
		otherBlogFiles = new ArrayList<BlogFileBean>();
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getBlogTitle() {
		return blogTitle;
	}
	public void setBlogTitle(String blogTitle) {
		this.blogTitle = blogTitle;
	}
	public Date getBlogDate() {
		return blogDate;
	}
	public void setBlogDate(Date blogDate) {
		this.blogDate = blogDate;
	}
	public String getBlogContent() {
		return blogContent;
	}
	public void setBlogContent(String blogContent) {
		this.blogContent = blogContent;
	}
	public String getBlogPhoto() {
		return blogPhoto;
	}
	public void setBlogPhoto(String blogPhoto) {
		this.blogPhoto = blogPhoto;
	}
	public String getBlogDocument() {
		return blogDocument;
	}
	public void setBlogDocument(String blogDocument) {
		this.blogDocument = blogDocument;
	}
	public BlogStatus getBlogStatus() {
		return blogStatus;
	}
	public void setBlogStatus(BlogStatus blogStatus) {
		this.blogStatus = blogStatus;
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
	public String getBlogDateFormatted() {

		return new SimpleDateFormat("dd/MM/yyyy").format(this.blogDate);
	}
	public ArrayList<BlogFileBean> getOtherBlogFiles() {
		return otherBlogFiles;
	}
	public void setOtherBlogFiles(ArrayList<BlogFileBean> otherBlogFiles) {
		this.otherBlogFiles = otherBlogFiles;
	}
	public String getBlogPhotoCaption() {
		return blogPhotoCaption;
	}
	public void setBlogPhotoCaption(String blogPhotoCaption) {
		this.blogPhotoCaption = blogPhotoCaption;
	}
}
