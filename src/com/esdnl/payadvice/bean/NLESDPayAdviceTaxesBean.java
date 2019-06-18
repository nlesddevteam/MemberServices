package com.esdnl.payadvice.bean;

import java.io.Serializable;

public class NLESDPayAdviceTaxesBean  implements Serializable {

	private static final long serialVersionUID = -8771605504141293333L;
	private Integer id;
	private String txDesc;
	private double txCur;
	private double txYtd;
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
	public String getTxDesc() {
		return txDesc;
	}
	public void setTxDesc(String txDesc) {
		this.txDesc = txDesc;
	}
	public double getTxCur() {
		return txCur;
	}
	public void setTxCur(double txCur) {
		this.txCur = txCur;
	}
	public double getTxYtd() {
		return txYtd;
	}
	public void setTxYtd(double txYtd) {
		this.txYtd = txYtd;
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
