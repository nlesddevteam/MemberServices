package com.esdnl.webupdatesystem.meetinghighlights.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
public class MeetingHighlightsBean implements Serializable {
	private static final long serialVersionUID = -8771605504141293333L;
	private Integer id;
	private String mHTitle;
	private Date mHDate;
	private String mHDoc;
	private String mHRelPreTitle;
	private String mHRelPreDoc;
	private String mHRelDocTitle;
	private String mHRelDoc;
	private String addedBy;
	private Date dateAdded;
	private ArrayList<MeetingHighlightsFileBean> otherMHFiles;
	private String mHMeetingVideo;
	public MeetingHighlightsBean()
	{
		otherMHFiles = new ArrayList<MeetingHighlightsFileBean>();
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getmHTitle() {
		return mHTitle;
	}
	public void setmHTitle(String mHTitle) {
		this.mHTitle = mHTitle;
	}
	public Date getmHDate() {
		return mHDate;
	}
	public void setmHDate(Date mHDate) {
		this.mHDate = mHDate;
	}
	public String getmHDoc() {
		return mHDoc;
	}
	public void setmHDoc(String mHDoc) {
		this.mHDoc = mHDoc;
	}
	public String getmHRelPreTitle() {
		return mHRelPreTitle;
	}
	public void setmHRelPreTitle(String mHRelPreTitle) {
		this.mHRelPreTitle = mHRelPreTitle;
	}
	public String getmHRelPreDoc() {
		return mHRelPreDoc;
	}
	public void setmHRelPreDoc(String mHRelPreDoc) {
		this.mHRelPreDoc = mHRelPreDoc;
	}
	public String getmHRelDocTitle() {
		return mHRelDocTitle;
	}
	public void setmHRelDocTitle(String mHRelDocTitle) {
		this.mHRelDocTitle = mHRelDocTitle;
	}
	public String getmHRelDoc() {
		return mHRelDoc;
	}
	public void setmHRelDoc(String mHRelDoc) {
		this.mHRelDoc = mHRelDoc;
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
	public String getmHDateFormatted() {

		return new SimpleDateFormat("dd/MM/yyyy").format(this.mHDate);
	}
	public String getDateAddedFormatted() {

		return new SimpleDateFormat("dd/MM/yyyy").format(this.dateAdded);
	}
	public ArrayList<MeetingHighlightsFileBean> getOtherMHFiles() {
		return otherMHFiles;
	}
	public void setOtherMHFiles(ArrayList<MeetingHighlightsFileBean> otherMHFiles) {
		this.otherMHFiles = otherMHFiles;
	}
	public String getmHMeetingVideo() {
		return mHMeetingVideo;
	}
	public void setmHMeetingVideo(String mHMeetingVideo) {
		this.mHMeetingVideo = mHMeetingVideo;
	}
	
	
}
