package com.esdnl.payadvice.bean;

import java.io.Serializable;

public class NLESDPayAdviceYTDTotalsBean implements Serializable {

	private static final long serialVersionUID = -8771605504141293333L;
	private Integer id;
	private double ytdTtlGrs;
	private double ytdtlTxbl;
	private double ytdTtlTx;
	private double ytdTtlDed;
	private double ytdTtlNet;
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
	public double getYtdTtlGrs() {
		return ytdTtlGrs;
	}
	public void setYtdTtlGrs(double ytdTtlGrs) {
		this.ytdTtlGrs = ytdTtlGrs;
	}
	public double getYtdtlTxbl() {
		return ytdtlTxbl;
	}
	public void setYtdtlTxbl(double ytdtlTxbl) {
		this.ytdtlTxbl = ytdtlTxbl;
	}
	public double getYtdTtlTx() {
		return ytdTtlTx;
	}
	public void setYtdTtlTx(double ytdTtlTx) {
		this.ytdTtlTx = ytdTtlTx;
	}
	public double getYtdTtlDed() {
		return ytdTtlDed;
	}
	public void setYtdTtlDed(double ytdTtlDed) {
		this.ytdTtlDed = ytdTtlDed;
	}
	public double getYtdTtlNet() {
		return ytdTtlNet;
	}
	public void setYtdTtlNet(double ytdTtlNet) {
		this.ytdTtlNet = ytdTtlNet;
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
