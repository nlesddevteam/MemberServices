package com.esdnl.payadvice.bean;

import java.io.Serializable;

public class NLESDPayAdviceBefTaxesDedBean implements Serializable {

	private static final long serialVersionUID = -8771605504141293333L;
	private Integer id;
	private String btxdDescr;
	private double btxdCur;
	private double btxdYtd;
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
	public String getBtxdDescr() {
		return btxdDescr;
	}
	public void setBtxdDescr(String btxdDescr) {
		this.btxdDescr = btxdDescr;
	}
	public double getBtxdCur() {
		return btxdCur;
	}
	public void setBtxdCur(double btxdCur) {
		this.btxdCur = btxdCur;
	}
	public double getBtxdYtd() {
		return btxdYtd;
	}
	public void setBtxdYtd(double btxdYtd) {
		this.btxdYtd = btxdYtd;
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
