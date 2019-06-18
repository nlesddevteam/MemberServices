package com.esdnl.payadvice.bean;

import java.io.Serializable;

public class NLESDPayAdviceEmployeeInfoBean implements Serializable {

	private static final long serialVersionUID = -8771605504141293333L;
	private Integer id;
	private String empName;
	private String empAddrL1;
	private String empAddrL2;
	private String empDept;
	private String locnCode;
	private String jobTitle;
	private String payRt;
	private double netClmAmt;
	private String empProv;
	private double provNclmAmt;
	private String empNumber;
	private Integer payGroupId;
	private String fedAddlAmt;
	
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getEmpAddrL1() {
		return empAddrL1;
	}
	public void setEmpAddrL1(String empAddrL1) {
		this.empAddrL1 = empAddrL1;
	}
	public String getEmpAddrL2() {
		return empAddrL2;
	}
	public void setEmpAddrL2(String empAddrL2) {
		this.empAddrL2 = empAddrL2;
	}
	public String getEmpDept() {
		return empDept;
	}
	public void setEmpDept(String empDept) {
		this.empDept = empDept;
	}
	public String getLocnCode() {
		return locnCode;
	}
	public void setLocnCode(String locnCode) {
		this.locnCode = locnCode;
	}
	public String getJobTitle() {
		return jobTitle;
	}
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
	public String getPayRt() {
		return payRt;
	}
	public void setPayRt(String payRt) {
		this.payRt = payRt;
	}
	public double getNetClmAmt() {
		return netClmAmt;
	}
	public void setNetClmAmt(double netClmAmt) {
		this.netClmAmt = netClmAmt;
	}
	public String getEmpProv() {
		return empProv;
	}
	public void setEmpProv(String empProv) {
		this.empProv = empProv;
	}
	public double getProvNclmAmt() {
		return provNclmAmt;
	}
	public void setProvNclmAmt(double provNclmAmt) {
		this.provNclmAmt = provNclmAmt;
	}
	public String getEmpNumber() {
		return empNumber;
	}
	public void setEmpNumber(String empNumber) {
		this.empNumber = empNumber;
	}
	public Integer getPayGroupId() {
		return payGroupId;
	}
	public void setPayGroupId(Integer payGroupId) {
		this.payGroupId = payGroupId;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFedAddlAmt() {
		return fedAddlAmt;
	}
	public void setFedAddlAmt(String fedAddlAmt) {
		this.fedAddlAmt = fedAddlAmt;
	}
	

}
