package com.esdnl.payadvice.bean;

import java.io.Serializable;

public class NLESDPayAdviceAftTaxesDedBean implements Serializable {

	private static final long serialVersionUID = -8771605504141293333L;
	private Integer id;
	private String atxdDescr;
	private double atxdCur;
	private double atxdYtd;
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
	public String getAtxdDescr() {
		return atxdDescr;
	}
	public void setAtxdDescr(String atxdDescr) {
		this.atxdDescr = atxdDescr;
	}
	public double getAtxdCur() {
		return atxdCur;
	}
	public void setAtxdCur(double atxdCur) {
		this.atxdCur = atxdCur;
	}
	public double getAtxdYtd() {
		return atxdYtd;
	}
	public void setAtxdYtd(double atxdYtd) {
		this.atxdYtd = atxdYtd;
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
