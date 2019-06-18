package com.esdnl.fund3.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProjectDocumentsBean implements Serializable {
	private static final long serialVersionUID = -8771605504141293333L;
	private int id;
	private int projectId;
	private String fileName;
	private String oFileName;
	private String addedBy;
	private Date dateAdded;
	private String fileDeleted;
	private String deletedBy;
	private Date dateDeleted;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getProjectId() {
		return projectId;
	}
	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getoFileName() {
		return oFileName;
	}
	public void setoFileName(String oFileName) {
		this.oFileName = oFileName;
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
	public String getFileDeleted() {
		return fileDeleted;
	}
	public void setFileDeleted(String fileDeleted) {
		this.fileDeleted = fileDeleted;
	}
	public String getDeletedBy() {
		return deletedBy;
	}
	public void setDeletedBy(String deletedBy) {
		this.deletedBy = deletedBy;
	}
	public Date getDateDeleted() {
		return dateDeleted;
	}
	public void setDateDeleted(Date dateDeleted) {
		this.dateDeleted = dateDeleted;
	}
	public String getDateAddedFormatted() {
		if(this.dateAdded != null){
			return new SimpleDateFormat("dd/MM/yyyy").format(this.dateAdded);
		}else{
			return "";
		}
	}
}
