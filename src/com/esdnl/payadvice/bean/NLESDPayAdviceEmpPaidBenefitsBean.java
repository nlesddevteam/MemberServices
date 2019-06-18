package com.esdnl.payadvice.bean;

import java.io.Serializable;

public class NLESDPayAdviceEmpPaidBenefitsBean implements Serializable {

	private static final long serialVersionUID = -8771605504141293333L;
	private Integer id;
	private String epbDesc;
	private double epbCur;
	private double epbYtd;
	private String empNumber;
	private int payGroupId;
	private int sortOrder;
	private Integer empInfoId;
	
	public Integer getEmpInfoId() {
		return empInfoId;
	}
	public void setEmpInfoId(Integer empInfoId) {
		this.empInfoId = empInfoId;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getEpbDesc() {
		return epbDesc;
	}
	public void setEpbDesc(String epbDesc) {
		this.epbDesc = epbDesc;
	}
	public double getEpbCur() {
		return epbCur;
	}
	public void setEpbCur(double epbCur) {
		this.epbCur = epbCur;
	}
	public double getEpbYtd() {
		return epbYtd;
	}
	public void setEpbYtd(double epbYtd) {
		this.epbYtd = epbYtd;
	}
	public String getEmpNumber() {
		return empNumber;
	}
	public void setEmpNumber(String empNumber) {
		this.empNumber = empNumber;
	}
	public int getPayGroupId() {
		return payGroupId;
	}
	public void setPayGroupId(int payGroupId) {
		this.payGroupId = payGroupId;
	}
	public int getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}
}
