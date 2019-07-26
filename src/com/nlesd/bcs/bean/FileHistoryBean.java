package com.nlesd.bcs.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileHistoryBean implements Serializable {
	private static final long serialVersionUID = -844016122427000813L;
	private int id;
	private int fileType;
	private String fileName;
	private String fileAction;
	private String actionBy;
	private Date actionDate;
	private int parentObjectId;
	private int parentObjectType;
	private String pathType;
	public String getPathType() {
		return pathType;
	}
	public void setPathType(String pathType) {
		this.pathType = pathType;
	}
	public int getParentObjectType() {
		return parentObjectType;
	}
	public void setParentObjectType(int parentObjectType) {
		this.parentObjectType = parentObjectType;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getFileType() {
		return fileType;
	}
	public void setFileType(int fileType) {
		this.fileType = fileType;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileAction() {
		return fileAction;
	}
	public void setFileAction(String fileAction) {
		this.fileAction = fileAction;
	}
	public String getActionBy() {
		return actionBy;
	}
	public void setActionBy(String actionBy) {
		this.actionBy = actionBy;
	}
	public Date getActionDate() {
		return actionDate;
	}
	public void setActionDate(Date actionDate) {
		this.actionDate = actionDate;
	}
	public int getParentObjectId() {
		return parentObjectId;
	}
	public void setParentObjectId(int parentObjectId) {
		this.parentObjectId = parentObjectId;
	}
	public String getActionDateFormatted() {
		if(this.actionDate != null){
			return new SimpleDateFormat("MM/dd/yyyy").format(this.actionDate);
		}else{
			return "";
		}
	}
	

}
