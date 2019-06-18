package com.esdnl.payadvice.bean;

import java.io.Serializable;

public class NLESDPayAdviceEmployeeSecurityBean implements Serializable {

	private static final long serialVersionUID = -8771605504141293333L;
	private Integer Id;
	private String payrollId;
	private String employeeId;
	private String password;
	private int wasEmailed;
	
	public Integer getId() {
		return Id;
	}
	public void setId(Integer id) {
		Id = id;
	}
	public String getPayrollId() {
		return payrollId;
	}
	public void setPayrollId(String payrollId) {
		this.payrollId = payrollId;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getWasEmailed() {
		return wasEmailed;
	}
	public void setWasEmailed(int wasEmailed) {
		this.wasEmailed = wasEmailed;
	}
	
}
