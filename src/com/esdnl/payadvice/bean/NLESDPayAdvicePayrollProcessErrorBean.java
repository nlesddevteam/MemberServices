package com.esdnl.payadvice.bean;

import java.io.Serializable;

public class NLESDPayAdvicePayrollProcessErrorBean implements Serializable {

	private static final long serialVersionUID = -8771605504141293333L;
	private String empName;
	private String locnCode;
	private String error;
	
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getLocnCode() {
		return locnCode;
	}
	public void setLocnCode(String locnCode) {
		this.locnCode = locnCode;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	

}
