package com.esdnl.payadvice.bean;

import java.io.Serializable;

public class NLESDPayAdviceEmpPaidBenefitsTotalsBean implements Serializable {

	private static final long serialVersionUID = -8771605504141293333L;
	private Integer id;
	private double epbTtlCur;
	private double epbTtlYtd;
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
	public double getEpbTtlCur() {
		return epbTtlCur;
	}
	public void setEpbTtlCur(double epbTtlCur) {
		this.epbTtlCur = epbTtlCur;
	}
	public double getEpbTtlYtd() {
		return epbTtlYtd;
	}
	public void setEpbTtlYtd(double epbTtlYtd) {
		this.epbTtlYtd = epbTtlYtd;
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
