package com.esdnl.fund3.bean;

import java.io.Serializable;

public class CustomReportFieldBean implements Serializable {
	private static final long serialVersionUID = -8771605504141293333L;
	private Integer id;
	private Integer reportId;
	private String fieldName;
	private String fieldCriteria;
	private Integer fieldUsed;
	public Integer getFieldUsed() {
		return fieldUsed;
	}
	public void setFieldUsed(Integer fieldUsed) {
		this.fieldUsed = fieldUsed;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getReportId() {
		return reportId;
	}
	public void setReportId(Integer reportId) {
		this.reportId = reportId;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getFieldCriteria() {
		return fieldCriteria;
	}
	public void setFieldCriteria(String fieldCriteria) {
		this.fieldCriteria = fieldCriteria;
	}
	

}
