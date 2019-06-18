package com.esdnl.payadvice.bean;

import java.io.Serializable;

public class NLESDPayAdviceEarningsBean implements Serializable {

	private static final long serialVersionUID = -8771605504141293333L;
	private Integer id;
	private String heDesc;
	private double curErn;
	private double ytdHrs;
	private double ytdErn;
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
	public String getHeDesc() {
		return heDesc;
	}
	public void setHeDesc(String heDesc) {
		this.heDesc = heDesc;
	}
	public double getCurErn() {
		return curErn;
	}
	public void setCurErn(double curErn) {
		this.curErn = curErn;
	}
	public double getYtdHrs() {
		return ytdHrs;
	}
	public void setYtdHrs(double ytdHrs) {
		this.ytdHrs = ytdHrs;
	}
	public double getYtdErn() {
		return ytdErn;
	}
	public void setYtdErn(double ytdErn) {
		this.ytdErn = ytdErn;
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
