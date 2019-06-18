package com.esdnl.payadvice.bean;

import java.io.Serializable;

public class NLESDPayrollDocumentPreviewBean implements Serializable {

	private static final long serialVersionUID = -8771605504141292444L;
	private String company;
	private String payrollGroup;
	private String payrollStartDate;
	private String payrollEndDate;
	private Integer payrollRecordsCount;
	
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getPayrollGroup() {
		return payrollGroup;
	}
	public void setPayrollGroup(String payrollGroup) {
		this.payrollGroup = payrollGroup;
	}
	public String getPayrollStartDate() {
		return payrollStartDate;
	}
	public void setPayrollStartDate(String payrollStartDate) {
		this.payrollStartDate = payrollStartDate;
	}
	public String getPayrollEndDate() {
		return payrollEndDate;
	}
	public void setPayrollEndDate(String payrollEndDate) {
		this.payrollEndDate = payrollEndDate;
	}
	public Integer getPayrollRecordsCount() {
		return payrollRecordsCount;
	}
	public void setPayrollRecordsCount(Integer payrollRecordsCount) {
		this.payrollRecordsCount = payrollRecordsCount;
	}
}
