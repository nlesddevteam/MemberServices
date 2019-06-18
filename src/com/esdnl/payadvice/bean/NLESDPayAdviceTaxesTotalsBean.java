package com.esdnl.payadvice.bean;

import java.io.Serializable;

public class NLESDPayAdviceTaxesTotalsBean implements Serializable {

	private static final long serialVersionUID = -8771605504141293333L;
	private Integer id;
	private double txTtlCur;
	private double txTtlYtd;
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
	public double getTxTtlCur() {
		return txTtlCur;
	}
	public void setTxTtlCur(double txTtlCur) {
		this.txTtlCur = txTtlCur;
	}
	public double getTxTtlYtd() {
		return txTtlYtd;
	}
	public void setTxTtlYtd(double txTtlYtd) {
		this.txTtlYtd = txTtlYtd;
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
