package com.nlesd.school.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SchoolDirectoryDetailsOtherBean implements Serializable {

	private static final long serialVersionUID = -3914355675665585056L;
	private Integer id=0;
	private String googleMapEmbed;
	private String schoolCatchmentEmbed;
	private String description;
	private String instagramLink;
	private String schoolEmail;
	private String schoolGuidanceSupport;
	private String twitterFeedWidgetId;
	private String twitterFeedScreenName;
	private String importantNotice;
	private String schoolEnrollment;
	private Integer schoolDirectory;
	private String addedBy;
	private Date dateAdded;
	private String twitterEmbed;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getGoogleMapEmbed() {
		return googleMapEmbed;
	}
	public void setGoogleMapEmbed(String googleMapEmbed) {
		this.googleMapEmbed = googleMapEmbed;
	}
	public String getSchoolCatchmentEmbed() {
		return schoolCatchmentEmbed;
	}
	public void setSchoolCatchmentEmbed(String schoolCatchmentEmbed) {
		this.schoolCatchmentEmbed = schoolCatchmentEmbed;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getInstagramLink() {
		return instagramLink;
	}
	public void setInstagramLink(String instagramLink) {
		this.instagramLink = instagramLink;
	}
	public String getSchoolEmail() {
		return schoolEmail;
	}
	public void setSchoolEmail(String schoolEmail) {
		this.schoolEmail = schoolEmail;
	}
	public String getSchoolGuidanceSupport() {
		return schoolGuidanceSupport;
	}
	public void setSchoolGuidanceSupport(String schoolGuidanceSupport) {
		this.schoolGuidanceSupport = schoolGuidanceSupport;
	}
	public String getTwitterFeedWidgetId() {
		return twitterFeedWidgetId;
	}
	public void setTwitterFeedWidgetId(String twitterFeedWidgetId) {
		this.twitterFeedWidgetId = twitterFeedWidgetId;
	}
	public String getTwitterFeedScreenName() {
		return twitterFeedScreenName;
	}
	public void setTwitterFeedScreenName(String twitterFeedScreenName) {
		this.twitterFeedScreenName = twitterFeedScreenName;
	}
	public String getImportantNotice() {
		return importantNotice;
	}
	public void setImportantNotice(String importantNotice) {
		this.importantNotice = importantNotice;
	}
	public String getSchoolEnrollment() {
		return schoolEnrollment;
	}
	public void setSchoolEnrollment(String schoolEnrollment) {
		this.schoolEnrollment = schoolEnrollment;
	}
	public Integer getSchoolDirectory() {
		return schoolDirectory;
	}
	public void setSchoolDirectory(Integer schoolDirectory) {
		this.schoolDirectory = schoolDirectory;
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
	public String getTwitterEmbed() {
		return twitterEmbed;
	}
	public void setTwitterEmbed(String twitterEmbed) {
		this.twitterEmbed = twitterEmbed;
	}

}
