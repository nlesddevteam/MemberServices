package com.esdnl.payadvice.bean;

import java.io.Serializable;

public class NLESDPayAdviceCompanyInfoBean implements Serializable {

	private static final long serialVersionUID = -8771605504141293333L;
	private Integer id;
	private String company;
	private String coAddrL1;
	private String coAddrL2;
	private Integer payGroup;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getCoAddrL1() {
		return coAddrL1;
	}
	public void setCoAddrL1(String coAddrL1) {
		this.coAddrL1 = coAddrL1;
	}
	public String getCoAddrL2() {
		return coAddrL2;
	}
	public void setCoAddrL2(String coAddrL2) {
		this.coAddrL2 = coAddrL2;
	}
	public Integer getPayGroup() {
		return payGroup;
	}
	public void setPayGroup(Integer payGroup) {
		this.payGroup = payGroup;
	}
	
}
