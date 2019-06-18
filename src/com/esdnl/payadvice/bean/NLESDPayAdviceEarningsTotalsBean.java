package com.esdnl.payadvice.bean;

import java.io.Serializable;

public class NLESDPayAdviceEarningsTotalsBean implements Serializable {

	private static final long serialVersionUID = -8771605504141293333L;
	private Integer id;
	private double ttlCurHrs;
	private double ttlCurErn;
	private double ttlYtdHrs;
	private double ttlYtdErn;
	private String empNumber;
	private int payGroupId;
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
	public double getTtlCurHrs() {
		return ttlCurHrs;
	}
	public void setTtlCurHrs(double ttlCurHrs) {
		this.ttlCurHrs = ttlCurHrs;
	}
	public double getTtlCurErn() {
		return ttlCurErn;
	}
	public void setTtlCurErn(double ttlCurErn) {
		this.ttlCurErn = ttlCurErn;
	}
	public double getTtlYtdHrs() {
		return ttlYtdHrs;
	}
	public void setTtlYtdHrs(double ttlYtdHrs) {
		this.ttlYtdHrs = ttlYtdHrs;
	}
	public double getTtlYtdErn() {
		return ttlYtdErn;
	}
	public void setTtlYtdErn(double ttlYtdErn) {
		this.ttlYtdErn = ttlYtdErn;
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


}
