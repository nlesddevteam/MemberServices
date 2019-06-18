package com.esdnl.payadvice.bean;

import java.io.Serializable;

public class NLESDPayAdvicePayStubBean implements Serializable {

	private static final long serialVersionUID = -8771605504141293333L;
	private Integer id;
	private String payrollId;
	private Integer paygroupId;
	private String fileName;
	private Integer emailed;
	private String stubError;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPayrollId() {
		return payrollId;
	}
	public void setPayrollId(String payrollId) {
		this.payrollId = payrollId;
	}
	public Integer getPaygroupId() {
		return paygroupId;
	}
	public void setPaygroupId(Integer paygroupId) {
		this.paygroupId = paygroupId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public Integer getEmailed() {
		return emailed;
	}
	public void setEmailed(Integer emailed) {
		this.emailed = emailed;
	}
	public String getStubError() {
		return stubError;
	}
	public void setStubError(String stubError) {
		this.stubError = stubError;
	}
	
}
