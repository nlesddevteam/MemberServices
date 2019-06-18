package com.esdnl.webupdatesystem.meetingminutes.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import com.esdnl.webupdatesystem.meetingminutes.constants.MeetingCategory;
public class MeetingMinutesBean implements Serializable {
	private static final long serialVersionUID = -8771605504141293333L;
	private Integer id;
	private String mMTitle;
	private Date mMDate;
	private String mMDoc;
	private String mMRelPreTitle;
	private String mMRelPreDoc;
	private String mMRelDocTitle;
	private String mMRelDoc;
	private String addedBy;
	private Date dateAdded;
	private MeetingCategory meetingCategory;
	private ArrayList<MeetingMinutesFileBean> otherMMFiles;
	private String mMMeetingVideo;
	public MeetingMinutesBean()
	{
		otherMMFiles = new ArrayList<MeetingMinutesFileBean>();
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getmMTitle() {
		return mMTitle;
	}
	public void setmMTitle(String mMTitle) {
		this.mMTitle = mMTitle;
	}
	public Date getmMDate() {
		return mMDate;
	}
	public void setmMDate(Date mMDate) {
		this.mMDate = mMDate;
	}
	public String getmMDoc() {
		return mMDoc;
	}
	public void setmMDoc(String mMDoc) {
		this.mMDoc = mMDoc;
	}
	public String getmMRelPreTitle() {
		return mMRelPreTitle;
	}
	public void setmMRelPreTitle(String mMRelPreTitle) {
		this.mMRelPreTitle = mMRelPreTitle;
	}
	public String getmMRelPreDoc() {
		return mMRelPreDoc;
	}
	public void setmMRelPreDoc(String mMRelPreDoc) {
		this.mMRelPreDoc = mMRelPreDoc;
	}
	public String getmMRelDocTitle() {
		return mMRelDocTitle;
	}
	public void setmMRelDocTitle(String mMRelDocTitle) {
		this.mMRelDocTitle = mMRelDocTitle;
	}
	public String getmMRelDoc() {
		return mMRelDoc;
	}
	public void setmMRelDoc(String mMRelDoc) {
		this.mMRelDoc = mMRelDoc;
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
	public String getmMDateFormatted() {

		return new SimpleDateFormat("dd/MM/yyyy").format(this.mMDate);
	}
	public String getDateAddedFormatted() {

		return new SimpleDateFormat("dd/MM/yyyy").format(this.dateAdded);
	}
	public MeetingCategory getMeetingCategory() {
		return meetingCategory;
	}
	public void setMeetingCategory(MeetingCategory meetingCategory) {
		this.meetingCategory = meetingCategory;
	}
	public ArrayList<MeetingMinutesFileBean> getOtherMMFiles() {
		return otherMMFiles;
	}
	public void setOtherMMFiles(ArrayList<MeetingMinutesFileBean> otherMMFiles) {
		this.otherMMFiles = otherMMFiles;
	}
	public String getmMMeetingVideo() {
		return mMMeetingVideo;
	}
	public void setmMMeetingVideo(String mMMeetingVideo) {
		this.mMMeetingVideo = mMMeetingVideo;
	}
	
	
}
