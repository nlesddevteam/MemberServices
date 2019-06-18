package com.esdnl.payadvice.bean;

import java.io.Serializable;

public class NLESDPayAdvicePayStubWorkerBean implements Serializable {

	private static final long serialVersionUID = -8771605504141293333L;
	private String empId;
	private String sin;
	private String lastName;
	private String firstName;
	private String email;
	private String empNumber;
	private String employeeId;
	private Integer payGroupId;
	
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public String getSin() {
		return sin;
	}
	public void setSin(String sin) {
		this.sin = sin;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEmpNumber() {
		return empNumber;
	}
	public void setEmpNumber(String empNumber) {
		this.empNumber = empNumber;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public Integer getPayGroupId() {
		return payGroupId;
	}
	public void setPayGroupId(Integer payGroupId) {
		this.payGroupId = payGroupId;
	}
	
}
