package com.esdnl.webupdatesystem.newspostings.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import com.esdnl.personnel.v2.model.sds.bean.LocationBean;
import com.esdnl.webupdatesystem.newspostings.constants.NewsCategory;
import com.esdnl.webupdatesystem.newspostings.constants.NewsStatus;

public class NewsPostingsBean implements Serializable {
	private static final long serialVersionUID = -8771605504141293333L;
	private Integer id;
	private NewsCategory newsCategory;
	private LocationBean newsLocation;
	private String newsTitle;
	private String newsDescription;
	private String newsPhoto;
	private String newsDocumentation;
	private String newsExternalLink;
	private String newsExternalLinkTitle;
	private NewsStatus newsStatus;
	private Date newsDate;
	private String addedBy;
	private Date dateAdded;
	private ArrayList<NewsPostingFileBean> otherNewsFiles;
	private String newsPhotoCaption;
	public NewsPostingsBean()
	{
		otherNewsFiles = new ArrayList<NewsPostingFileBean>();
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public NewsCategory getNewsCategory() {
		return newsCategory;
	}
	public void setNewsCategory(NewsCategory newsCategory) {
		this.newsCategory = newsCategory;
	}
	public LocationBean getNewsLocation() {
		return newsLocation;
	}
	public void setNewsLocation(LocationBean newsLocation) {
		this.newsLocation = newsLocation;
	}
	public String getNewsTitle() {
		return newsTitle;
	}
	public void setNewsTitle(String newsTitle) {
		this.newsTitle = newsTitle;
	}
	public String getNewsDescription() {
		return newsDescription;
	}
	public void setNewsDescription(String newsDescription) {
		this.newsDescription = newsDescription;
	}
	public String getNewsPhoto() {
		return newsPhoto;
	}
	public void setNewsPhoto(String newsPhoto) {
		this.newsPhoto = newsPhoto;
	}
	public String getNewsDocumentation() {
		return newsDocumentation;
	}
	public void setNewsDocumentation(String newsDocumentation) {
		this.newsDocumentation = newsDocumentation;
	}
	public String getNewsExternalLink() {
		return newsExternalLink;
	}
	public void setNewsExternalLink(String newsExternalLink) {
		this.newsExternalLink = newsExternalLink;
	}
	public String getNewsExternalLinkTitle() {
		return newsExternalLinkTitle;
	}
	public void setNewsExternalLinkTitle(String newsExternalLinkTitle) {
		this.newsExternalLinkTitle = newsExternalLinkTitle;
	}
	public NewsStatus getNewsStatus() {
		return newsStatus;
	}
	public void setNewsStatus(NewsStatus newsStatus) {
		this.newsStatus = newsStatus;
	}
	public Date getNewsDate() {
		return newsDate;
	}
	public void setNewsDate(Date newsDate) {
		this.newsDate = newsDate;
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
	public String getNewsDateFormatted() {

		return new SimpleDateFormat("dd/MM/yyyy").format(this.newsDate);
	}
	public String getDateAddedFormatted() {

		return new SimpleDateFormat("dd/MM/yyyy").format(this.dateAdded);
	}
	public ArrayList<NewsPostingFileBean> getOtherNewsFiles() {
		return otherNewsFiles;
	}
	public void setOtherNewsFiles(ArrayList<NewsPostingFileBean> otherNewsFiles) {
		this.otherNewsFiles = otherNewsFiles;
	}
	public String getNewsPhotoCaption() {
		return newsPhotoCaption;
	}
	public void setNewsPhotoCaption(String newsPhotoCaption) {
		this.newsPhotoCaption = newsPhotoCaption;
	}
	
}
