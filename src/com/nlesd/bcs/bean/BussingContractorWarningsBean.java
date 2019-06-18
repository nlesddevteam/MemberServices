package com.nlesd.bcs.bean;

import java.io.Serializable;

public class BussingContractorWarningsBean implements Serializable {

	private static final long serialVersionUID = -844016122427000813L;
	private int id;
	private String tableName;
	private String fieldName;
	private int warningDays;
	private String warningNotes;
	private String isActive;
	private String warningName;
	private String warningException;
	private String mainPageSql;
	private String automatedSql;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public int getWarningDays() {
		return warningDays;
	}
	public void setWarningDays(int warningDays) {
		this.warningDays = warningDays;
	}
	public String getWarningNotes() {
		return warningNotes;
	}
	public void setWarningNotes(String warningNotes) {
		this.warningNotes = warningNotes;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public String getWarningName() {
		return warningName;
	}
	public void setWarningName(String warningName) {
		this.warningName = warningName;
	}
	public String getWarningException() {
		return warningException;
	}
	public void setWarningException(String warningException) {
		this.warningException = warningException;
	}
	public String getMainPageSql() {
		return mainPageSql;
	}
	public void setMainPageSql(String mainPageSql) {
		this.mainPageSql = mainPageSql;
	}
	public String getAutomatedSql() {
		return automatedSql;
	}
	public void setAutomatedSql(String automatedSql) {
		this.automatedSql = automatedSql;
	}
	
}
