package com.esdnl.payadvice.bean;

import java.io.Serializable;

public class NLESDPayAdviceCurrentTotalsBean implements Serializable {

	private static final long serialVersionUID = -8771605504141293333L;
	private Integer id;
	private double curTtlGrs;
	private double curTtlTxbl;
	private double curTtlTx;
	private double curTtlDed;
	private double curTtlNet;
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
	public double getCurTtlGrs() {
		return curTtlGrs;
	}
	public void setCurTtlGrs(double curTtlGrs) {
		this.curTtlGrs = curTtlGrs;
	}
	public double getCurTtlTxbl() {
		return curTtlTxbl;
	}
	public void setCurTtlTxbl(double curTtlTxbl) {
		this.curTtlTxbl = curTtlTxbl;
	}
	public double getCurTtlTx() {
		return curTtlTx;
	}
	public void setCurTtlTx(double curTtlTx) {
		this.curTtlTx = curTtlTx;
	}
	public double getCurTtlDed() {
		return curTtlDed;
	}
	public void setCurTtlDed(double curTtlDed) {
		this.curTtlDed = curTtlDed;
	}
	public double getCurTtlNet() {
		return curTtlNet;
	}
	public void setCurTtlNet(double curTtlNet) {
		this.curTtlNet = curTtlNet;
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
