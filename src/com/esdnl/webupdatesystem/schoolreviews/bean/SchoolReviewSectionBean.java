package com.esdnl.webupdatesystem.schoolreviews.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class SchoolReviewSectionBean implements Serializable {
	private static final long serialVersionUID = -8771605504141293333L;
	private int secId;
	private int secReviewId;
	private int secType;
	private String secTitle;
	private int secStatus;
	private String secDescription;
	private String secAddedBy;
	private Date secDateAdded;
	private String  secTypeText;
	private ArrayList<SchoolReviewFileBean> secFiles;
	private ArrayList<SchoolReviewSectionOptionBean> secOptions;
	private int secSortId;
	private int fileCount;
	public int getSecId() {
		return secId;
	}
	public void setSecId(int secId) {
		this.secId = secId;
	}
	public int getSecReviewId() {
		return secReviewId;
	}
	public void setSecReviewId(int secReviewId) {
		this.secReviewId = secReviewId;
	}
	public int getSecType() {
		return secType;
	}
	public void setSecType(int secType) {
		this.secType = secType;
	}
	public String getSecTitle() {
		return secTitle;
	}
	public void setSecTitle(String secTitle) {
		this.secTitle = secTitle;
	}
	public int getSecStatus() {
		return secStatus;
	}
	public void setSecStatus(int secStatus) {
		this.secStatus = secStatus;
	}
	public String getSecDescription() {
		return secDescription;
	}
	public void setSecDescription(String secDescription) {
		this.secDescription = secDescription;
	}
	public String getSecAddedBy() {
		return secAddedBy;
	}
	public void setSecAddedBy(String secAddedBy) {
		this.secAddedBy = secAddedBy;
	}
	public Date getSecDateAdded() {
		return secDateAdded;
	}
	public void setSecDateAdded(Date secDateAdded) {
		this.secDateAdded = secDateAdded;
	}
	public String getSecTypeText() {
		return secTypeText;
	}
	public void setSecTypeText(String secTypeText) {
		this.secTypeText = secTypeText;
	}
	public ArrayList<SchoolReviewFileBean> getSecFiles() {
		return secFiles;
	}
	public void setSecFiles(ArrayList<SchoolReviewFileBean> secFiles) {
		this.secFiles = secFiles;
	}
	public ArrayList<SchoolReviewSectionOptionBean> getSecOptions() {
		return secOptions;
	}
	public void setSecOptions(ArrayList<SchoolReviewSectionOptionBean> secOptions) {
		this.secOptions = secOptions;
	}
	public int getSecSortId() {
		return secSortId;
	}
	public void setSecSortId(int secSortId) {
		this.secSortId = secSortId;
	}
	public int getFileCount() {
		return fileCount;
	}
	public void setFileCount(int fileCount) {
		this.fileCount = fileCount;
	}
}
