package com.nlesd.bcs.bean;

import java.io.Serializable;

public class BussingContractorSystemCustomReportConditionsBean implements Serializable {

	private static final long serialVersionUID = -844016122427000813L;
	private int id;
	private int fieldId;
	private String operatorId;
	private String cText;
	private int reportId;
	private int selectId;
	private String startDate;
	private String endDate;
	private String fieldName;
	private String fieldType;
	private String selectText;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getFieldId() {
		return fieldId;
	}
	public void setFieldId(int fieldId) {
		this.fieldId = fieldId;
	}
	public String getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}
	public String getcText() {
		return cText;
	}
	public void setcText(String cText) {
		this.cText = cText;
	}
	public int getReportId() {
		return reportId;
	}
	public void setReportId(int reportId) {
		this.reportId = reportId;
	}
	public int getSelectId() {
		return selectId;
	}
	public void setSelectId(int selectId) {
		this.selectId = selectId;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
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
	public String getOperatorString(){
		String optext="";
		if(this.operatorId.equals("EQ")){
			optext="EQUALS";
		}else if(this.operatorId.equals("CO")){
			optext="CONTAINS";
		}else if(this.operatorId.equals("GT")){
			optext="GREATER THAN";
		}else if(this.operatorId.equals("LT")){
			optext="LESS THAN";
		}else if(this.operatorId.equals("BT")){
			optext="BETWEEN";
		}
		return optext;
	}
	public String getSelectText() {
		return selectText;
	}
	public void setSelectText(String selectText) {
		this.selectText = selectText;
	}
}
