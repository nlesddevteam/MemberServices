package com.esdnl.webupdatesystem.schoolreviews.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SchoolReviewFileBean implements Serializable {

	private static final long serialVersionUID = -844016122427000813L;
	private int id;
	private String fileTitle;
	private String fileType;
	private String filePath;
	private Date fileDate;
	private String fileAddedBy;
	private Date fileDateAdded;
	private int isActive;
	private int fileReviewId;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFileTitle() {
		return fileTitle;
	}
	public void setFileTitle(String fileTitle) {
		this.fileTitle = fileTitle;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public Date getFileDate() {
		return fileDate;
	}
	public void setFileDate(Date fileDate) {
		this.fileDate = fileDate;
	}
	public String getFileAddedBy() {
		return fileAddedBy;
	}
	public void setFileAddedBy(String fileAddedBy) {
		this.fileAddedBy = fileAddedBy;
	}
	public Date getFileDateAdded() {
		return fileDateAdded;
	}
	public void setFileDateAdded(Date fileDateAdded) {
		this.fileDateAdded = fileDateAdded;
	}
	public int getIsActive() {
		return isActive;
	}
	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}
	public int getFileReviewId() {
		return fileReviewId;
	}
	public void setFileReviewId(int fileReviewId) {
		this.fileReviewId = fileReviewId;
	}
	public String getFileDateFormatted() {
		if(this.fileDate != null) {
			return new SimpleDateFormat("dd/MM/yyyy").format(this.fileDate);
		}else {
			return "";
		}
		
	}
	public String getFileDateAddedFormatted() {
		if(this.fileDateAdded != null) {
			return new SimpleDateFormat("dd/MM/yyyy").format(this.fileDateAdded);
		}else {
			return "";
		}
		
	}
	public String getFileDateFormattedHTML() {
		if(this.fileDate != null) {
			return new SimpleDateFormat("YYYY-MM-dd").format(this.fileDate);
		}else {
			return "";
		}
		
	}
	public String toXml() {
		StringBuilder sb = new StringBuilder();
		sb.append("<SRFILE>");
		sb.append("<ID>" + this.id + "</ID>");
		sb.append("<FILETITLE>" + this.fileTitle + "</FILETITLE>");
		sb.append("<FILETYPE>" + this.fileType + "</FILETYPE>");
		sb.append("<FILEPATH>" + this.filePath + "</FILEPATH>");
		sb.append("<FILEDATE>" + this.getFileDateFormatted() + "</FILEDATE>");
		sb.append("<FILEDATEFORMATTED>" + this.getFileDateFormattedHTML() + "</FILEDATEFORMATTED>");
		sb.append("<FILEADDEDBY>" + this.fileAddedBy + "</FILEADDEDBY>");
		sb.append("<FILEDATEADDED>" + this.getFileDateFormatted() + "</FILEDATEADDED>");
		sb.append("<MESSAGE>SUCCESS</MESSAGE>");
		sb.append("</SRFILE>");
		return sb.toString();
	}
}
