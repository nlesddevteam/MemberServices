package com.nlesd.bcs.bean;

import java.io.Serializable;

public class FileTypeBean implements Serializable {
	private static final long serialVersionUID = -844016122427000813L;
	private int id;
	private String fileName;
	private String fileCategory;
	private String fieldName;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileCategory() {
		return fileCategory;
	}
	public void setFileCategory(String fileCategory) {
		this.fileCategory = fileCategory;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

}
