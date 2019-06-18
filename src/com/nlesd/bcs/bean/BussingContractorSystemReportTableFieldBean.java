package com.nlesd.bcs.bean;

import java.io.Serializable;

public class BussingContractorSystemReportTableFieldBean implements Serializable {

	private static final long serialVersionUID = -844016122427000813L;
	private int id;
	private String fieldName;
	private String fieldType;
	private String fieldTitle;
	private int relatedField;
	private String isActive;
	private int tableId;
	private String constantField;
	private String colAlias;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getFieldType() {
		return fieldType;
	}
	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}
	public String getFieldTitle() {
		return fieldTitle;
	}
	public void setFieldTitle(String fieldTitle) {
		this.fieldTitle = fieldTitle;
	}
	public int getRelatedField() {
		return relatedField;
	}
	public void setRelatedField(int relatedField) {
		this.relatedField = relatedField;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public int getTableId() {
		return tableId;
	}
	public void setTableId(int tableId) {
		this.tableId = tableId;
	}
	public String getConstantField() {
		return constantField;
	}
	public void setConstantField(String constantField) {
		this.constantField = constantField;
	}
	public String getColAlias() {
		return colAlias;
	}
	public void setColAlias(String colAlias) {
		this.colAlias = colAlias;
	}
}
